package edu.umkc.burrise.luckycount;


import android.content.Context;
import android.test.AndroidTestCase;

public class ExampleContextTest extends AndroidTestCase {

    public void testComponentThatNeedsContext() throws Exception {
        Context c = getContext();
        assertEquals(1, 1);
    }
}
