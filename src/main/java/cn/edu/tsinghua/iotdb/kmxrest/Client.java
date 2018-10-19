package cn.edu.tsinghua.iotdb.kmxrest;

        import org.glassfish.grizzly.http.server.HttpServer;

        import javax.ws.rs.client.ClientBuilder;
        import javax.ws.rs.client.WebTarget;
        import java.io.IOException;

public class Client {
    private static HttpServer server;
    private static WebTarget target;

    Client() {
        //server = Main.startServer();
        // create the client
        javax.ws.rs.client.Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());
        target = c.target("http://192.168.130.165:6666/");
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        //SELECT+*+FROM+%22mymeas%22+
        //SELECT+s_0+FROM+root.performf.group_0.d_0
        String q = "SELECT s_0, s_1 FROM root.performf.group_0.d_0";
        String s = target.path("query")
                .queryParam("db", "tsdb")
                .queryParam("q", q)
                .getUriBuilder().toString();
        System.out.println("url=" + s);
        String responseMsg = target.path("query")
                .queryParam("db", "tsdb")
                .queryParam("q", q)
                .request().get(String.class);
        System.out.println("responseMsg=" + responseMsg);
    }
}
