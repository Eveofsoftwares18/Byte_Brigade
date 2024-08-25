package com.booking.beans;

public class MeetingRoom {
    private int id;
    private String name;
    private int seatingCapacity;
    private boolean projector;
    private boolean wifi;
    private boolean conferenceCall;
    private boolean whiteboard;
    private boolean waterDispenser;
    private boolean tv;
    private boolean coffeeMachine;
    private int perHourCost;

    public MeetingRoom() {
    }

    public MeetingRoom(int id, String name, int seatingCapacity, boolean projector, boolean wifi, boolean conferenceCall, boolean whiteboard, boolean waterDispenser, boolean tv, boolean coffeeMachine) {
        this.id = id;
        this.name = name;
        this.seatingCapacity = seatingCapacity;
        this.projector = projector;
        this.wifi = wifi;
        this.conferenceCall = conferenceCall;
        this.whiteboard = whiteboard;
        this.waterDispenser = waterDispenser;
        this.tv = tv;
        this.coffeeMachine = coffeeMachine;
    }

    public MeetingRoom(int id, String name, int seatingCapacity, boolean projector, boolean wifi, boolean conferenceCall, boolean whiteboard, boolean waterDispenser, boolean tv, boolean coffeeMachine, int perHourCost) {
        this.id = id;
        this.name = name;
        this.seatingCapacity = seatingCapacity;
        this.projector = projector;
        this.wifi = wifi;
        this.conferenceCall = conferenceCall;
        this.whiteboard = whiteboard;
        this.waterDispenser = waterDispenser;
        this.tv = tv;
        this.coffeeMachine = coffeeMachine;
        this.perHourCost = perHourCost;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getSeatingCapacity() { return seatingCapacity; }
    public void setSeatingCapacity(int seatingCapacity) { this.seatingCapacity = seatingCapacity; }
    public boolean isProjector() { return projector; }
    public void setProjector(boolean projector) { this.projector = projector; }
    public boolean isWifi() { return wifi; }
    public void setWifi(boolean wifi) { this.wifi = wifi; }
    public boolean isConferenceCall() { return conferenceCall; }
    public void setConferenceCall(boolean conferenceCall) { this.conferenceCall = conferenceCall; }
    public boolean isWhiteboard() { return whiteboard; }
    public void setWhiteboard(boolean whiteboard) { this.whiteboard = whiteboard; }
    public boolean isWaterDispenser() { return waterDispenser; }
    public void setWaterDispenser(boolean waterDispenser) { this.waterDispenser = waterDispenser; }
    public boolean isTv() { return tv; }
    public void setTv(boolean tv) { this.tv = tv; }
    public boolean isCoffeeMachine() { return coffeeMachine; }
    public void setCoffeeMachine(boolean coffeeMachine) { this.coffeeMachine = coffeeMachine; }
    public int getPerHourCost() { return perHourCost; }
    public void setPerHourCost(int perHourCost) { this.perHourCost = perHourCost; }

    @Override
    public String toString() {
        return "MeetingRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", seatingCapacity=" + seatingCapacity +
                ", projector=" + projector +
                ", wifi=" + wifi +
                ", conferenceCall=" + conferenceCall +
                ", whiteboard=" + whiteboard +
                ", waterDispenser=" + waterDispenser +
                ", tv=" + tv +
                ", coffeeMachine=" + coffeeMachine +
                ", perHourCost=" + perHourCost +
                '}';
    }
}

