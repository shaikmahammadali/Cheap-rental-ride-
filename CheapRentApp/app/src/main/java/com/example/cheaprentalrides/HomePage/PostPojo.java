package com.example.cheaprentalrides.HomePage;

public class PostPojo {

    String source,destination,vehicle_type,vehicle_name,vehicle_details;
    float vehicle_load;

    public PostPojo(String source, String destination, String vehicle_type, float vehicle_load, String vehicle_name, String vehicle_details) {
        this.source = source;
        this.destination = destination;
        this.vehicle_type = vehicle_type;
        this.vehicle_load = vehicle_load;
        this.vehicle_name = vehicle_name;
        this.vehicle_details = vehicle_details;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public float getVehicle_load() {
        return vehicle_load;
    }

    public void setVehicle_load(float vehicle_load) {
        this.vehicle_load = vehicle_load;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getVehicle_details() {
        return vehicle_details;
    }

    public void setVehicle_details(String vehicle_details) {
        this.vehicle_details = vehicle_details;
    }
}