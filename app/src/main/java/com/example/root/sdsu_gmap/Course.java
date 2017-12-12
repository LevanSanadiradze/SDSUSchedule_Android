package com.example.root.sdsu_gmap;

import java.util.ArrayList;

/**
 * Created by root on 12/12/17.
 */

public class Course {

    private String Course = "";
    private String CourseDescription = "";
    private String CourseFormat = "";
    private int Units = 0;
    private String ScheduleID = "";
    private String Instructor = "";

    private ArrayList<Lecture> Lectures = new ArrayList<>();

    public Course(String Course, String CourseDescription, String CourseFormat, int Units, String ScheduleID, String Instructor, ArrayList<Lecture> Lectures) {
        this.Course = Course;
        this.CourseDescription = CourseDescription;
        this.CourseFormat = CourseFormat;
        this.Units = Units;
        this.ScheduleID = ScheduleID;
        this.Instructor = Instructor;
        this.Lectures = Lectures;
    }

    public String getCourse() {
        return Course;
    }

    public String getCourseDescription() {
        return CourseDescription;
    }

    public String getCourseFormat() {
        return CourseFormat;
    }

    public int getUnits() {
        return Units;
    }

    public String getScheduleID() {
        return ScheduleID;
    }

    public String getInstructor() {
        return Instructor;
    }

    public ArrayList<Lecture> getLectures() {
        return Lectures;
    }
}

