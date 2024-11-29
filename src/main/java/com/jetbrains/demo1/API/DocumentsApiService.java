package com.jetbrains.demo1.API;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jetbrains.demo1.data.DocumentData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DocumentsApiService {
    private final String Header_api = "https://www.googleapis.com/books/v1/volumes?";
    private final String Querry_api = "q=";
    private final String Key_api = "&key=AIzaSyC2MkNeQhdp1s5GQmcc7y176tv5egxswj8&fbclid" +
            "=IwY2xjawFwv4dleHRuA2FlbQIxMAABHdyma8Iy9rUWIFaU7ThbBK1KYQr-RdX7uzOu3pdXgXbs3eoJLi48FOr_yA_aem_zetxgmzO6rQ1SqBqlsbY2A";
    private final String Max_result_api = "&maxResults=";

    public List<DocumentData> searchDocuments(String query, Integer maxResult) throws Exception {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
        String api_url = Header_api + Querry_api + encodedQuery + Key_api + Max_result_api + maxResult;
        URL url = new URL(api_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("Failed to search books: HTTP error code " + responseCode);

        } else {
            return parseDocuments(connection);
        }
    }

    public List<DocumentData> searchDocuments(String query) throws Exception {
        return searchDocuments(query, 30);
    }

    private List<DocumentData> parseDocuments(HttpURLConnection connection) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        List<DocumentData> documents = new ArrayList<DocumentData>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(content.toString());
        JsonNode itemsNode = root.path("items");
        for (JsonNode itemNode : itemsNode) {
            DocumentData documentData = new DocumentData();
            documentData.setId(itemNode.path("id").asText());
            documentData.setName(itemNode.path("volumeInfo").path("title").asText());
            documentData.setLinkaddress(itemNode.path("volumeInfo").path("infoLink").asText());

            JsonNode authorsNode = itemNode.path("volumeInfo").path("authors");
            if (authorsNode.isArray() && authorsNode.size() > 0) {
                documentData.setAuthor(authorsNode.get(0).asText());
            }

            JsonNode categoriesNode = itemNode.path("volumeInfo").path("categories");
            if (categoriesNode.isArray() && categoriesNode.size() > 0) {
                documentData.setCategory(categoriesNode.get(0).asText());
            }

            documentData.setImage(itemNode.path("volumeInfo").path("imageLinks").path("thumbnail").asText());
            documents.add(documentData);
        }
        return documents;
    }
}
