package edu.umkc.burrise.luckycount;

// References:
//   http://developer.android.com/tools/testing/activity_testing.html
//

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

// Note, you need to specify activity class name as generic type to
//   ActivityInstrumentationTestCase2
public class LuckyCountUITest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;

    public LuckyCountUITest() {
        super(MainActivity.class);
    }

    // If you want to send key events via your test, you have to turn off
    // the touch mode in the emulator via setActivityInitialTouchMode(false)
    // in your setup() method of the test.
    public void setUp(  )  {
        // setActivityInitialTouchMode(false) must be called before the
        //   activity is created.
        setActivityInitialTouchMode(false);
        activity = (MainActivity) getActivity();
    }

    public void testIncrement() throws Exception {

        Button incrementButton;
        incrementButton = (Button) activity.findViewById(R.id.inc_button);
        assertNotNull(incrementButton);

        TextView countValueView;
        countValueView = (TextView) activity.findViewById(R.id.count_value);
        assertNotNull(countValueView);

        assertEquals("initial count incorrect","0",countValueView.getText().toString());

        // TouchUtils handles the sync with the main thread internally
        TouchUtils.clickView(this, incrementButton);

        // I think you have to do this after sending input to UI
        getInstrumentation().waitForIdleSync();

        // displayed value should e 1 now.
        assertEquals("count after increment is incorrect","1",countValueView.getText().toString());
    }
}
