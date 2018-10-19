package cn.edu.tsinghua.iotdb.kmxrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("")
public class MyResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyResource.class);

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("{params}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@PathParam("params") String params) {
        System.out.println("params: " + params);
        String decodeQuery = null;
        LOGGER.debug("raw params: {}", params);

        try {
            decodeQuery = URLDecoder.decode(params,"utf-8");
            String[] split = decodeQuery.split("&");
            String query = null;
            for(String item: split){
                String[] splitItem = item.split("=");
                if(splitItem[0].equals("q")){
                    query = splitItem[1];
                }
            }
            System.out.println("query:" + query);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("Decode query url failed, url: {} , error: {}", params, e.getMessage());
        }
        LOGGER.debug("decode query: {}", decodeQuery);
        System.out.println("System.out.println(decodeQuery):" + decodeQuery);

        return "Got it!";
    }
}
