package com.javarush.task.task39.task3913;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class User {

    private String ip;
    private String userName;
    private Date date;
    private Event event;
    private Integer numberTask = -1;
    private Status status;

    public User(String ip, String userName, Date date, Event event, Integer numberTask, Status status) {
        this.ip = ip;
        this.userName = userName;
        this.date = date;
        this.event = event;
        this.numberTask = numberTask;
        this.status = status;
    }

    public User(String string) {
        String[] masStr = string.split("\t");
        ip = masStr[0];
        userName = masStr[1];
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            date = format.parse(masStr[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String eventStr = masStr[3];
        if(eventStr.startsWith("SOLVE_TASK") || eventStr.startsWith("DONE_TASK")){
            String[] masDop = eventStr.split(" ");
            event = Event.valueOf(masDop[0]);
            numberTask = Integer.valueOf(masDop[1]);
        }else {
            event = Event.valueOf(eventStr);
        }
        status = Status.valueOf(masStr[4]);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getNumberTask() {
        return numberTask;
    }

    public void setNumberTask(Integer numberTask) {
        this.numberTask = numberTask;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(ip, user.ip) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(date, user.date) &&
                event == user.event &&
                Objects.equals(numberTask, user.numberTask) &&
                status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, userName, date, event, numberTask, status);
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        if(numberTask != -1){
            return ip + '\t' + userName + '\t' +
                   format.format(date) + '\t' +
                    event + " " +
                    numberTask + '\t' +
                    status;
        }else {
            return ip + '\t' + userName + '\t' +
                    format.format(date) + '\t' +
                    event + '\t' +
                    status;
        }
    }
}
