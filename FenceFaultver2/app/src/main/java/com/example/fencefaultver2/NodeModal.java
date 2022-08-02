package com.example.fencefaultver2;

public class NodeModal {

    //variables for database
    private String Area;
    private String Node;
    private String Status;
    private String Time;
    private String Battery;
    private String Voltage;

    //Creating getters and setters methods
    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getNode() {
        return Node;
    }

    public void setNode(String node) {
        Node = node;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setBattery(String battery) {
        Battery = battery;
    }

    public String getBattery() {
        return Battery;
    }

    public String getVoltage() {
        return Voltage;
    }

    public void setVoltage(String voltage) {
        Voltage = voltage;
    }

    //constructor
    public NodeModal( String area, String node, String status, String time, String battery, String voltage) {
        this.Area = area;
        this.Node = node;
        this.Status = status;
        this.Time = time;
        this.Battery = battery;
        this.Voltage = voltage;
    }
}
