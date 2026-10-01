package com.example.diarioderede;

public class Note {
    private String text;
    private String dateTime;
    private String networkType;

    public Note(String text, String dateTime, String networkType) {
        this.text = text;
        this.dateTime = dateTime;
        this.networkType = networkType;
    }

    public String getText() { return text; }
    public String getDateTime() { return dateTime; }
    public String getNetworkType() { return networkType; }
}