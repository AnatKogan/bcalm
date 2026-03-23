package com.example.bcalm;

public class User {
    private String fullName;
    private String id; // ה-UID של פיירבייס
    private String phone;
    private String email;
    private int age;
    private String idNumber;
    private String idCardUrl;
    private boolean isApproved;


    public User() {}


    public User(String email, String phone, String id, String fullName, int age, String idNumber) {
        this.email = email;
        this.phone = phone;
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.idNumber = idNumber;
        this.isApproved = false;

        this.idCardUrl = null;
    }

    // Getters and Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }

    public String getIdCardUrl() { return idCardUrl; }
    public void setIdCardUrl(String idCardUrl) { this.idCardUrl = idCardUrl; }
}
