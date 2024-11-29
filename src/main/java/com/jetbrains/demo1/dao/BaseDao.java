package com.jetbrains.demo1.dao;

import com.jetbrains.demo1.databaseconnection.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class BaseDao {
    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;

    protected void connect() {
        connection = Database.connectToDb();
    }

    protected void closeResources() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
