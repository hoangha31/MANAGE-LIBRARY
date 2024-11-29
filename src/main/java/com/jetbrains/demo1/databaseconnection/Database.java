package com.jetbrains.demo1.databaseconnection;

import java.sql.*;

public class Database {
    public static Connection connectToDb() {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management","root","31032005");
            return connection;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
