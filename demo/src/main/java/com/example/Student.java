package com.example;

import java.io.Serializable;

public class Student implements Serializable {
    private String stdNo;
    private String givenNames;
    private String lastName;
    private String passwordHash;
    private Double salt;

    // Constructors
    public Student() {}

    public  Student(String stdNo, String givenNames, String lastName, String passwordHash, Double salt) {
        this.stdNo = stdNo;
        this.givenNames = givenNames;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    // Getters and Setters
    public String getStdNo() { return stdNo; }
    public void setStdNo(String stdNo) { this.stdNo = stdNo; }

    public String getGivenNames() { return givenNames; }
    public void setGivenNames(String givenNames) { this.givenNames = givenNames; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Double getSalt() { return salt; }
    public void setSalt(Double salt) { this.salt = salt; }

}

