package cn.edu.tsinghua.iotdb.kmxrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("query")
public class MyResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyResource.class);
    public static Statement statement = null;
    public long statementID = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");//yyyy-MM-dd HH:mm:ss.SSS

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET

    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@QueryParam("db") String db,
                        @QueryParam("q") String q,
                        @DefaultValue("root") @QueryParam("u") String u,
                        @DefaultValue("root") @QueryParam("p") String p,
                        @DefaultValue("ms") @QueryParam("epoch") String epoch,
                        @DefaultValue("false") @QueryParam("pretty") boolean pretty,
                        @DefaultValue("false") @QueryParam("chunked") boolean chunked,
                        @DefaultValue("0") @QueryParam("chunk_size") String chunk_size) {
        String decodeQuery = null;
        Map<String, List> queryMap = new HashMap();
        List<Object> resultList = new LinkedList<>();

        Map<String, Object> resultsMap = new HashMap<>();
        resultsMap.put("statement_id", statementID);
        List<Map> seriesList = new LinkedList<>();
        Map<String, Object> seriesMap = new HashMap<>();

        System.out.println("raw q: " + q);

        try {
            decodeQuery = URLDecoder.decode(q, "utf-8");

            System.out.println("decoded q: " + decodeQuery);

            try {
                statement = Main.connection.createStatement();
                boolean hasResult = statement.execute(decodeQuery);
                List<List> values = new LinkedList<>();
                List<String> columns = new LinkedList<>();
                if(hasResult) {
                    ResultSet resultSet = statement.getResultSet();
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    int colNum = resultSetMetaData.getColumnCount();
                    columns.add("time");
                    for (int i = 2; i <= colNum; i++) {
                        columns.add(resultSetMetaData.getColumnLabel(i));
                    }
                    while (resultSet.next()) {
                        List<Object> items = new LinkedList<>();
                        long time = Long.parseLong(resultSet.getString("Time"));
                        String dateTime = dateFormat.format(new Date(time));
                        items.add(dateTime);
                        for (int i = 2; i <= colNum; i++) {
                            String name = resultSetMetaData.getColumnLabel(i);
                            String value = resultSet.getString(name);
                            items.add(value);
                        }
                        values.add(items);
                    }
                    seriesMap.put("name", getStorageGroup(q));
                    seriesMap.put("columns", columns);
                    seriesMap.put("values", values);
                }

                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Execute query failed, sql: {}, error: {}", decodeQuery, e.getMessage());
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("Decode query url failed, error: {}", e.getMessage());
        }
        seriesList.add(seriesMap);
        resultsMap.put("series", seriesList);

        resultList.add(resultsMap);
        queryMap.put("results", resultList);
        statementID++;
        return JSON.toJSONString(queryMap);
    }

    private String getStorageGroup(String q) {
        String[] split = q.split("\\.");
        return split[1] + "." + split[2];
    }
}
