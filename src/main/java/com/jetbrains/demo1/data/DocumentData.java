package com.jetbrains.demo1.data;

public class DocumentData {
    private String id;
    private String name;
    private String author;
    private String category;
    private Integer quantity;
    private String linkaddress;
    private String image;

    public DocumentData() {}

    public DocumentData(String id, String name, String author, String category, Integer quantity, String linkaddress,String image) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.quantity = quantity;
        this.linkaddress = linkaddress;
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLinkaddress(String linkaddress) {
        this.linkaddress = linkaddress;
    }

    public String getLinkaddress() {
        return linkaddress;
    }
}
