package com.booking.beans;

import java.util.Date;

public class Booking {
    private int id;
    private int roomId;
    private int managerId;
    private Date startTime;
    private Date endTime;
    private int cost;

    public Booking() {
    }

    public Booking(int roomId, int managerId, Date startTime, Date endTime) {
        this.roomId = roomId;
        this.managerId = managerId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Booking(int id, int roomId, int managerId, Date startTime, Date endTime) {
        this.id = id;
        this.roomId = roomId;
        this.managerId = managerId;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public int getManagerId() { return managerId; }
    public void setManagerId(int managerId) { this.managerId = managerId; }
    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }
    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", managerId=" + managerId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", cost=" + cost +
                '}';
    }
}
