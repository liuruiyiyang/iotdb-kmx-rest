package cn.edu.tsinghua.iotdb.kmxrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Path("query")
public class MyResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyResource.class);
    public static Statement statement = null;
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

        System.out.println("raw q: " + q);
        String decodeQuery = null;

        try {
            decodeQuery = URLDecoder.decode(q,"utf-8");
            System.out.println("decodeQuery: " + decodeQuery);

//            try {
//                statement = Main.connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(query);
//
//
//                statement.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("Decode query url failed, url: {} , error: {}", q, e.getMessage());
        }

        return "Got it!";
    }
}
