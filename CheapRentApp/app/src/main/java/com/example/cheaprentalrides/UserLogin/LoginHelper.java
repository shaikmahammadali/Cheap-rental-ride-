package com.example.cheaprentalrides.UserLogin;

// login class data
public class LoginHelper
{
    String name,phone,otp;

    public LoginHelper(String name, String phone, String otp) {
        this.name = name;
        this.phone = phone;
        this.otp = otp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
