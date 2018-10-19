package cn.edu.tsinghua.iotdb.kmxrest.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    public Config() {

    }

    public String host = "localhost";
    public String port = "6666";


    public static void main(String[] args) {
        // Config config = Config.newInstance();

    }
}
