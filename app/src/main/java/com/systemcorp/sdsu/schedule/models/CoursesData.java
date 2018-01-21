package com.systemcorp.sdsu.schedule.models;

/**
 * Created by giorgi on 1/13/18.
 */

public class CoursesData {
    private String name;
    private String professor;
    private String schedule;

    public CoursesData() {
    }

    public CoursesData(String name, String professor, String schedule) {
        this.name = name;
        this.professor = professor;
        this.schedule = schedule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
