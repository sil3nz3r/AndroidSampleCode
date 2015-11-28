package edu.umkc.burrise.sqliteexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnItemClickListener {

    private ArrayList<Course> courses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pass application context here. If you pass activity context
        //   I think it will keep your context around this preventing
        //   space allocated for the activity from being freed when the
        //   activity is no longer needed (e.g. screen rotation).
        Model model = Model.instance(getApplicationContext());

        ListView courseListView =
                (ListView)this.findViewById(R.id.listOfClasses);

        courses = model.getCourses();

        // If no courses, add some
        if (courses.size() == 0) {
            long SE1_course_id = model.insertCourse("CS449");
            long SE2_course_id = model.insertCourse("CS451");

            model.insertAssignment(SE1_course_id, "Iteration 1");
            model.insertAssignment(SE1_course_id, "Iteration 2");

            model.insertAssignment(SE2_course_id, "Lab 1");

            courses = model.getCourses();
        }


        /*
         * simple_list_item_1 is an Android defined resource.
         * For more info, see:
         *   http://stackoverflow.com/questions/3663745/what-is-android-r-layout-simple-list-item-1
         */
        //int layoutID = android.R.layout.simple_list_item_1;

        // Rather then reuse the Android resource, I'm
        // using one I defined. It is defined in
        // res/layout/listitem.xml.
        ArrayAdapter<Course> arrayAdapter =
                new ArrayAdapter<Course>(this, R.layout.listitem , courses);
        courseListView.setAdapter(arrayAdapter);

        courseListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {
        Course selectedCourse = courses.get(position);

        Model model = Model.instance(this);
        ArrayList<Assignment> assignments = model.getAssignments(selectedCourse);

        Toast.makeText(this,"Assignments: " + assignments.toString(),Toast.LENGTH_SHORT).show();
    }

    public void updateCourse(long courseID, String courseName) {
        Model model = Model.instance(this);
        try {
            model.updateCourse(courseID, courseName);
        } catch (Exception e) {
            Toast.makeText(this,"***Error. Course " + courseID + " doesn't exist.",Toast.LENGTH_SHORT).show();
        }

    }
}