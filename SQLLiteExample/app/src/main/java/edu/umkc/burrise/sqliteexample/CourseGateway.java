package edu.umkc.burrise.sqliteexample;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CourseGateway {
	
	private Context context;
	
	public CourseGateway(Context context) {
		this.context = context;
	}
	
	public ArrayList<Course> findAll() {
		ArrayList<Course> courses = new ArrayList<Course>();

		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] from = { DatabaseHelper.FIELD_ID, DatabaseHelper.FIELD_COURSE_NAME};
		Cursor cursor = db.query(DatabaseHelper.COURSES_TABLE, from, null, null, null,
		null, null);
		
		while (cursor.moveToNext()) {
			long id = cursor.getLong(0);
			String course_name = cursor.getString(1);
			courses.add(new Course(course_name,id));
		}
		dbHelper.close();
		return courses;
	}
	
	public long insert(String courseName) {
		// insert record into DB and get back primary
		// key for new record.
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_COURSE_NAME, courseName);
		long cs490_id = db.insertOrThrow(DatabaseHelper.COURSES_TABLE, null, values);

		// Query for the value of the autoincrement field
		String[] from = { DatabaseHelper.FIELD_ID,
				DatabaseHelper.FIELD_COURSE_NAME };

		Cursor cursor = db.query(DatabaseHelper.COURSES_TABLE,
				from,
				DatabaseHelper.FIELD_COURSE_NAME + "= '" + courseName + "'",
				null,
				null,
				null,
				null);
	
		cursor.moveToNext();
		long value_of_autoincrement_field = cursor.getLong(0);
		dbHelper.close();
		return value_of_autoincrement_field; // return the primary key created by the DB
	}

	// id is primary key for record to update
	public void update(int id, String courseName) {

	}

    // returns true if delete successful
	public boolean delete(long id) { // id is primary key for record
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(DatabaseHelper.COURSES_TABLE, DatabaseHelper.FIELD_ID + "=" + id, null) > 0;
	}
}
