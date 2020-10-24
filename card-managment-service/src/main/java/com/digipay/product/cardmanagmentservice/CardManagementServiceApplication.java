package com.digipay.product.cardmanagmentservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.logging.Logger;

@SpringBootApplication
public class CardManagementServiceApplication implements CommandLineRunner {
    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(CardManagementServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        Logger.getGlobal().info(">>>> ################################################# <<<<");
        Logger.getGlobal().info(">>>> Mysql url: " + metaData.getURL());
        Logger.getGlobal().info(">>>> DriverName url: " + metaData.getDriverName());
        Logger.getGlobal().info(">>>> UserName url: " + metaData.getUserName());
    }
}
