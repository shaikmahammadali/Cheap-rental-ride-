package com.example.cheaprentalrides.UserLogin;

public class LoginHelper {
    String name , Phone ;

    public LoginHelper(String name, String phone) {
        this.name = name;
        Phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
