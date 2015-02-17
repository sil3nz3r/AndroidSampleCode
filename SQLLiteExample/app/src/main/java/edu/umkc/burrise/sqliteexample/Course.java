package edu.umkc.burrise.sqliteexample;

import java.io.Serializable;
import java.util.Random;

public class Course implements Serializable {
	private String courseName;
	
	// private key. id will be set by the DB when the record
	// for this course is inserted into the Course table
	private long id;
	
	
	public Course(String courseName, long id) {
		this.courseName = courseName;
		this.id = id;
	}
	
	public String toString() {
		return courseName;
	}

	public String courseName() {
		return courseName;
	}

	public long courseID() {
		return id;
	}
}