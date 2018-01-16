package com.example.root.sdsu_gmap;

import com.example.root.sdsu_gmap.models.Course;

import java.util.List;

/**
 * Created by root on 12/12/17.
 */

public class StudentInformation {

    private String FirstName = "";
    private String MiddleName = "";
    private String LastName = "";

    private List<Course> Courses;

    public StudentInformation(String FirstName, String MiddleName, String LastName, List<Course> Courses)
    {
        this.FirstName = FirstName;
        this.MiddleName = MiddleName;
        this.LastName = LastName;
        this.Courses = Courses;
    }

    public String getFirstName()
    {
        return this.FirstName;
    }

    public String getMiddleName()
    {
        return this.MiddleName;
    }

    public String getLastName()
    {
        return this.LastName;
    }

    public List<Course> getCourses()
    {
        return this.Courses;
    }

}
