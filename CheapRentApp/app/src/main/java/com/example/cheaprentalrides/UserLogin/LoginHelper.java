package com.example.cheaprentalrides.UserLogin;

public class LoginHelper {
    public LoginHelper(String fullname, String phone) {
        this.fullname = fullname;
        this.phone = phone;
    }

    static String fullname,phone;

    public static String getFullname() {
        return fullname;
    }

    public static void setFullname(String fullname) {
        LoginHelper.fullname = fullname;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        LoginHelper.phone = phone;
    }
}
