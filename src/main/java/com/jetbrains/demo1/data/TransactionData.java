package com.jetbrains.demo1.data;


import java.sql.Date;

public class TransactionData {
    private int id;
    private int userId;
    private String documentId;
    private Date borrowDate;
    private Date returnDate;

    public TransactionData(int id, int userId, String documentId, Date borrowDate, Date returnDate) {
        this.id = id;
        this.userId = userId;
        this.documentId = documentId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
