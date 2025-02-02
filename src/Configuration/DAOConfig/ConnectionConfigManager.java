package Configuration.DAOConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public enum ConnectionConfigManager {
    INSTANCE;


    private String datasourceURL;
    private String datasourceUsername;
    private String datasourcePassword;

    ConnectionConfigManager() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/Configuration/database-config.properties"));
            datasourceURL = properties.getProperty("datasourceURL");
            datasourceUsername = properties.getProperty("datasourceUsername");
            datasourcePassword = properties.getProperty("datasourcePassword");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDatasourceURL() {
        return datasourceURL;
    }

    public String getDatasourceUsername() {
        return datasourceUsername;
    }

    public String getDatasourcePassword() {
        return datasourcePassword;
    }

    public static ConnectionConfigManager getInstance() {
        return INSTANCE;
    }
}
