package com.jetbrains.demo1.dao;

import com.jetbrains.demo1.data.UserData;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.function.Consumer;

public class UserDao extends BaseDao {

    // Tạo ra để khi insert ảnh mà chưa update hay add vào database.
    public static String urlImageCurrent;

    public boolean checkExsitingById(Long userId) {
        connect();
        String sql = "SELECT * FROM user WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    public String getUserImage(int userId) {
        String imagePath = new String();

        String sql = "SELECT image FROM user WHERE userId = ?";
        connect();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                imagePath = resultSet.getString("image");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return imagePath;
    }

    public void getAllUsers(Consumer<UserData> userConsumer) {
        connect();
        String sql = "SELECT * FROM user";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserData userData = new UserData(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getDate("dateOfBirth"),
                        resultSet.getString("gender"),
                        resultSet.getString("image")
                );
                userConsumer.accept(userData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    public void addUser(String firstName, String lastName, LocalDate dateOfBirth, String gender, String image) {
        Date date = Date.valueOf(dateOfBirth);
        connect();
        String sql = "INSERT INTO user VALUES (null, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, date);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, image);
            preparedStatement.execute();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    public void updateUser(Long id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String image) {
        Date date = Date.valueOf(dateOfBirth);
        connect();
        String sql = "UPDATE user SET firstName = ?, lastName = ?, dateofBirth = ?, gender = ?, image = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, date);
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, image);
            preparedStatement.setLong(6, id);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

    }

    public void deleteUser(Long userId) {
        connect();
        String sql = "DELETE FROM user WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }
}

