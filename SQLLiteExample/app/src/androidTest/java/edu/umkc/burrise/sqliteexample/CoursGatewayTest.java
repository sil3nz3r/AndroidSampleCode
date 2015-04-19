package edu.umkc.burrise.sqliteexample;

import android.app.Application;
import android.content.Context;
import android.test.AndroidTestCase;

import java.util.ArrayList;

// Example of dependency injection enabling unit testing
public class CoursGatewayTest extends AndroidTestCase {
    private CourseGateway courseGateway;

    // Note, getContext() will return the context on the testing
    //   device. If you want a separate test context, see:
    //   http://stackoverflow.com/questions/4645461/isolatedcontext-vs-androidtestcase-getcontext
    public void setUp(  )  {
        // CourseGateway depends on application context.
        // The dependency here is being satisfied with dependency
        //   injection.
        Context c = getContext();
        courseGateway = new CourseGateway(c);
    }

    public void testInsertCourse() throws Exception {
        long id = courseGateway.insert("SE1");
        ArrayList<Course> courses = courseGateway.findAll();
        boolean found = false;
        for (Course c : courses) {
            if ("SE1".equalsIgnoreCase(c.courseName())) {
                found = true;
                break;
            }
        }
        assertTrue("Problem inserting course into course gateway", found);

        boolean deleteSuccessful = courseGateway.delete(id);
        assertTrue("Problem deleting inserted record", deleteSuccessful);
    }
}