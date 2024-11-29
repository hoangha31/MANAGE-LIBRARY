package com.jetbrains.demo1.dao;

public class AdminDao extends BaseDao{
    public boolean checkLogin(String username, String password) {
        String sql = "Select * from admin where username = ? and password = ?";
        connect();
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }
            return false;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }
}