package edu.umkc.burrise.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "school.db" ;
	// version can be any number. A change in version number
	// give the program the opportunity to recreate or
	// update the db.
	private static final int DATABASE_VERSION = 1;

	public static final String COURSES_TABLE = "Courses";
	public static final String ASSIGNMENTS_TABLE = "Assignments";

	// Both tables have an _id field.
	// By convention, the first column is always called _id.
	public static final String FIELD_ID = "_id";

	// Fields defined for table Courses
	public static final String FIELD_COURSE_NAME = "course_name";
	
	// Fields defined for table Assignments
	// course_id is a foreign key
	public static final String FIELD_COURSE = "course_id";
	public static final String FIELD_ASSIGNMENT_NAME = "assignment_name";

	// SQL Statement to create a new database tables  
	private static final String DATABASE_CREATE_COURSES = "create table " +
			COURSES_TABLE + " (" + FIELD_ID + " integer primary key autoincrement, " +
			FIELD_COURSE_NAME + " text not null);";

	private static final String DATABASE_CREATE_ASSIGNMENTS = "create table " +
			ASSIGNMENTS_TABLE + " (" + FIELD_ID + " integer primary key autoincrement, " +
			FIELD_COURSE + " integer, " + 
			FIELD_ASSIGNMENT_NAME + " text not null);";

	  
	public DatabaseHelper(Context ctx){
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_COURSES);
		database.execSQL(DATABASE_CREATE_ASSIGNMENTS);
	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(DatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion);
		database.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE);
		database.execSQL("DROP TABLE IF EXISTS " + ASSIGNMENTS_TABLE);
		onCreate(database);
	}
	
}
