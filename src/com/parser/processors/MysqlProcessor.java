package com.parser.processors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlProcessor {
    private boolean connected = false;
    private Connection connection;

    public MysqlProcessor() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/parser", "root", "9111314");
            this.connected = true;
        } catch (SQLException exception) {

        }
    }

    public boolean isConnected()
    {
        return  this.connected;
    }
}
