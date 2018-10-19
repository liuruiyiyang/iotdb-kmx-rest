package cn.edu.tsinghua.iotdb.kmxrest;

import cn.edu.tsinghua.iotdb.jdbc.TsfileJDBCConfig;
import cn.edu.tsinghua.iotdb.kmxrest.conf.Config;
import cn.edu.tsinghua.iotdb.kmxrest.conf.ConfigDescriptor;
import cn.edu.tsinghua.tsfile.common.conf.TSFileConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:6666/";
    public static final String IOTDB_URI = "jdbc:tsfile://%s:%s/";
    public static Connection connection = null;
    public static Config config = ConfigDescriptor.getInstance().getConfig();
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in cn.edu.tsinghua.iotdb.kmxrest package
        final ResourceConfig rc = new ResourceConfig().packages("cn.edu.tsinghua.iotdb.kmxrest");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void init() {

        try {

            Class.forName(TsfileJDBCConfig.JDBC_DRIVER_NAME);
            connection = DriverManager.getConnection(String.format(IOTDB_URI, config.host, config.port), "root", "root");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        //init();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

