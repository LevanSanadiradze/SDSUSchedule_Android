package com.example.root.sdsu_gmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ge.sanapps.jsonparser.JSONParser;

/**
 * Created by root on 12/12/17.
 */

public class ScheduleFileParser {

    public static StudentInformation parseScheduleFile(String content)
    {
        HashMap<String, Object> parsed = JSONParser.Parse(content);

        List<Course> Courses = new ArrayList<>();
        HashMap<String, Object> parsedCourses = (HashMap<String, Object>) parsed.get("Courses");

        for(int i = 0; i < parsedCourses.size(); i++)
        {
            HashMap<String, Object> thisCourse = (HashMap<String, Object>) parsedCourses.get(parsedCourses.keySet().toArray()[i]);

            List<Lecture> Lectures = new ArrayList<>();
            HashMap<String, Object> parsedLectures = ((HashMap<String, Object>) thisCourse.get("Lectures"));

            for(int l = 0; l < parsedLectures.size(); l++)
            {
                HashMap<String, Object> thisLecture = (HashMap<String, Object>) parsedLectures.get(parsedLectures.keySet().toArray()[l]);

                Lecture tempLecture = new Lecture(
                        (String) thisLecture.get("Format"),
                        (String) thisLecture.get("Day"),
                        (String) thisLecture.get("Start"),
                        (String) thisLecture.get("End"),
                        (String) thisLecture.get("Partner"),
                        (String) thisLecture.get("Building"),
                        (String) thisLecture.get("Class")
                );

                Lectures.add(tempLecture);
            }

            Course tempCourse = new Course(
                    (String) thisCourse.get("Course"),
                    (String) thisCourse.get("CourseDescription"),
                    (String) thisCourse.get("CourseFormat"),
                    Integer.parseInt((String) thisCourse.get("Units")),
                    (String) thisCourse.get("ScheduleID"),
                    (String) thisCourse.get("Instructor"),
                    Lectures
            );

            Courses.add(tempCourse);
        }

        return new StudentInformation(
                (String) parsed.get("FirstName"),
                (String) parsed.get("MiddleName"),
                (String) parsed.get("LastName"),
                Courses
        );

    }

}
