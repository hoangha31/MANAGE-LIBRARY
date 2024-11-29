package com.jetbrains.demo1.dao;

import com.jetbrains.demo1.data.DocumentData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class DocumentDao extends BaseDao {
    public static String urlImageCurrent;

    public boolean checkExsitingById(Long documentId) {
        connect();
        String sql = "SELECT * FROM document WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, documentId);
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

    public String getDocumentImage(String documentId) {
        String imagePath = new String();

        String sql = "SELECT image FROM document WHERE id = ?";
        connect();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, documentId);
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

    public void getAllDocuments(Consumer<DocumentData> documentConsumer) {
        connect();
        String sql = "SELECT * FROM document";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DocumentData documentData = new DocumentData(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("author"),
                        resultSet.getString("category"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("linkaddress"),
                        resultSet.getString("image")
                );
                documentConsumer.accept(documentData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    public void addDocument(String name, String author, String category, Integer quantity, String linkaddress, String image) {
        connect();
        String sql = "INSERT INTO document VALUES (null, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, category);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setString(5, linkaddress);
            preparedStatement.setString(6, image);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    public void updateDocument(Long id, String name, String author, String category, Integer quantity, String linkaddress, String image) {
        connect();
        String sql = "UPDATE document SET name = ?, author = ?, category = ?, quantity = ?, linkaddress = ?, image = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, category);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setString(5, linkaddress);
            preparedStatement.setString(6, image);
            preparedStatement.setLong(7, id);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

    }

    public void deleteDocument (Long documentId) {
        connect();
        String sql = "DELETE FROM document WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, documentId);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }
}
