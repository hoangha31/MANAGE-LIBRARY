package com.jetbrains.demo1.controller;

import com.jetbrains.demo1.databaseconnection.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class BaseController {
    protected double x = 0;
    protected double y = 0;
}