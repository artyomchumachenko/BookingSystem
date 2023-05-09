package config;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {
    private static BasicDataSource dataSource;

    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream("dbcp.properties");
            properties.load(inputStream);

            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("driverClassName"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            dataSource.setInitialSize(Integer.parseInt(properties.getProperty("initialSize")));
            dataSource.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("Get num active = " + dataSource.getNumActive());
        return dataSource.getConnection();
    }
}
