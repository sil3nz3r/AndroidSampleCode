package edu.umkc.burrise.luckycount;

import android.test.AndroidTestCase;

/*
JUnit follows a very specific sequence of events when invoking tests.
  First, it constructs a new instance of the test case for each test
  method. Thus, if you have five test methods, JUnit constructs five
  instances of your test case. After constructing all of the test case
  objects, JUnit calls (1) the test case's setUp( ) method, (2) the
  test method, (3) the test case's tearDown( ) method.
 */

// The naming convention for test classes is
//   <class under test>Test
// The class here under test is CountModel so the
//   test class is CountModelTest
public class CountModelTest extends AndroidTestCase {

    private CountModel model;

    public void setUp(  )  {
        model = new CountModel();
    }

    public void tearDown(  ) {
        // If class under test had any cleanup methods, we would call
        //   them here.

    }

    // Notice, you have separate instances of CountModelTest
    //   for each test method.
    public void testIncrement() throws Exception {
        model.increment();
        assertEquals("CountModel had wrong value after increment",1, model.getValue());
    }

    public void testIncrementAgain() throws Exception {
        model.increment();
        model.increment();
        assertEquals("CountModel had wrong value after increment",2, model.getValue());
    }

    public void testIsLucky() throws Exception {
        model.increment();
        model.increment();
        model.increment();
        model.increment();
        assertFalse("CountModel failed to recognize unlucky number",model.isLucky());
    }
}
