package com.example.root.sdsu_gmap;

/**
 * Created by root on 12/12/17.
 */

public class Lecture {

    private String Format = "";
    private String Day = "";
    private int StartHour = 0;
    private int StartMinute = 0;
    private int EndHour = 0;
    private int EndMinute = 0;
    private String Partner = "";
    private String Building = "";
    private String Room = "";

    public Lecture(String Format, String Day, String StartTime, String EndTime, String Partner, String Building, String Room)
    {
        this.Format = Format;
        this.Day = Day;

        String[] Start = StartTime.split(":");
        this.StartHour = Integer.parseInt(Start[0]);
        this.StartMinute = Integer.parseInt(Start[1]);

        String[] End = EndTime.split(":");
        this.EndHour = Integer.parseInt(End[0]);
        this.EndMinute = Integer.parseInt(End[1]);

        this.Partner = Partner;
        this.Building = Building;
        this.Room = Room;
    }

    public String getFormat() {
        return Format;
    }

    public String getDay() {
        return Day;
    }

    public int getStartHour() {
        return StartHour;
    }

    public int getStartMinute() {
        return StartMinute;
    }

    public int getEndHour() {
        return EndHour;
    }

    public int getEndMinute() {
        return EndMinute;
    }

    public String getPartner() {
        return Partner;
    }

    public String getBuilding() {
        return Building;
    }

    public String getRoom() {
        return Room;
    }
}