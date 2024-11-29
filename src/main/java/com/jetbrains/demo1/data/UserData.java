package com.jetbrains.demo1.data;

import java.sql.Date;

public class UserData {
    private Integer userId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String image;

    public UserData(int id, String firstName, String lastName
            , Date dateOfBirth, String gender, String image ) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
