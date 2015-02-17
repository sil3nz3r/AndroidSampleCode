package edu.umkc.burrise.sqliteexample;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AssignmentGateway {
	private Context context;

	public AssignmentGateway(Context context) {
		this.context = context;
	}
	
	public ArrayList<Assignment> findForCourse(Course c) {
		ArrayList<Assignment> assignments;
        assignments = new ArrayList<Assignment>();

		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] from = { DatabaseHelper.FIELD_ID,
				DatabaseHelper.FIELD_COURSE,
				DatabaseHelper.FIELD_ASSIGNMENT_NAME};
		Cursor cursor = db.query(DatabaseHelper.ASSIGNMENTS_TABLE,
				from,
				DatabaseHelper.FIELD_COURSE + "=" + c.courseID(),
				null,
				null,
				null,
				null);
		
		while (cursor.moveToNext()) {
			long id = cursor.getLong(0);
			// Note, we are skipping over the foreign key for
			// course.
			String assignment_name = cursor.getString(2);
			assignments.add(new Assignment(assignment_name));
		}

		dbHelper.close();
		return assignments;
	}
	
	public long insert(long courseID, // foreign key
			String assignmentName) {
		// insert record into DB and get back primary
		// key for new record.
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_COURSE, courseID);
		values.put(DatabaseHelper.FIELD_ASSIGNMENT_NAME, assignmentName);
		long cs490_id = db.insertOrThrow(DatabaseHelper.ASSIGNMENTS_TABLE, null, values);

		// Query for the value of the autoincrement field
		String[] from = { DatabaseHelper.FIELD_ID,
				DatabaseHelper.FIELD_COURSE,
				DatabaseHelper.FIELD_ASSIGNMENT_NAME};

		Cursor cursor = db.query(DatabaseHelper.ASSIGNMENTS_TABLE,
				from,
				DatabaseHelper.FIELD_COURSE + "=" + courseID + " AND " +
						DatabaseHelper.FIELD_ASSIGNMENT_NAME + "= '" + assignmentName + "'",
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
	public void update(int id, int courseID,
			String assignmentName, int percent,
			int pointsPossible,
			int pointsEarned) {

	}

	public void delete(int id) { // id is primary key for record
		
	}
}
