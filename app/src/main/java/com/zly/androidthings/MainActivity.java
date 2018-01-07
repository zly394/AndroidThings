package com.zly.androidthings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.zly.android.things.contrib.driver.hc595.DateTimeDisplay;
import com.zly.android.things.contrib.driver.hc595.NumericDisplay;

import java.io.IOException;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {
    private static final String TAG = "HC595";
    private NumericDisplay mSegmentDisplay;
    private DateTimeDisplay mDateTimeDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Starting MainActivity");
        mSegmentDisplay = new NumericDisplay("BCM22", "BCM17", "BCM27");
//        mSegmentDisplay.display("12.34");
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mSegmentDisplay.display(i);
        }
//        mDateTimeDisplay = new DateTimeDisplay("BCM22", "BCM17", "BCM27");
//        mDateTimeDisplay.displayCurrentDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSegmentDisplay != null) {
            Log.i(TAG, "Closing segmentDisplay");
            try {
                mSegmentDisplay.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing segmentDisplay", e);
            } finally {
                mSegmentDisplay = null;
            }
        }
        if (mDateTimeDisplay != null) {
            Log.i(TAG, "Closing dateTimeDisplay");
            try {
                mDateTimeDisplay.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing dateTimeDisplay", e);
            } finally {
                mDateTimeDisplay = null;
            }
        }
    }
}
