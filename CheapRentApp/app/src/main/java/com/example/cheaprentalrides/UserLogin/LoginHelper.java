package com.example.cheaprentalrides.UserLogin;

// login class data
public class LoginHelper
{
    String name,phone/*email,vehicle_Number*/;

    /*public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehicle_Number() {
        return vehicle_Number;
    }

    public void setVehicle_Number(String vehicle_Number) {
        this.vehicle_Number = vehicle_Number;
    }*/

    public LoginHelper(String name, String phone) {
        this.name = name;
        this.phone = phone;
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

}
