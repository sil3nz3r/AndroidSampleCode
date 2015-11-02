package edu.umkc.burrise.sqliteexample;

import java.util.ArrayList;
import android.content.Context;

public class Model {

	// Singleton design pattern
	private static Model instance = null;

	private CourseGateway courseGateway;
	private AssignmentGateway assignmentGateway;
	
	private Model(Context context) {
		courseGateway = new CourseGateway(context);
		assignmentGateway = new AssignmentGateway(context);
	}

    public synchronized static Model instance(Context context) {
        if( instance == null ) {
            instance = new Model(context);
        }
        return instance;
    }
	
	public ArrayList<Course> getCourses() {
		return courseGateway.findAll();
	}
	
	public long insertCourse(String courseName) {
		return courseGateway.insert(courseName);
	}

	public long insertAssignment(long courseID, String assignmentName) {
		return assignmentGateway.insert(courseID, assignmentName);
	}

	public void updateCourse(long courseID, String courseName) throws Exception {
		courseGateway.update(courseID,courseName);
	}

	public ArrayList<Assignment> getAssignments(Course c) {
		return assignmentGateway.findForCourse(c);
	}
}
