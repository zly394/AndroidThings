package com.zly.android.things.contrib.driver.hc595;

import java.io.IOException;

/**
 * Created by zhuleiyue on 2017/12/21.
 */

public class HC595 implements AutoCloseable {
    static final String TAG = "HC595";
    /**
     * The maximum number of bytes that can be written at a time
     */
    private static final int MAX_DATA_LENGTH = 4;

    byte decimalPoint = (byte) 0xf;

    private GpioBitDevice mDevice;

    HC595(String pioData, String pioSClock, String pioRClock) {
        mDevice = new GpioBitDevice(pioData, pioSClock, pioRClock);
    }

    @Override
    public void close() throws IOException {
        if (mDevice != null) {
            try {
                mDevice.close();
            } finally {
                mDevice = null;
            }
        }
    }

    void writeData(byte[] data) throws IOException {
        if (mDevice == null) {
            throw new IllegalStateException("Device not opened");
        }
        if (data.length > MAX_DATA_LENGTH) {
            throw new IllegalArgumentException("data size should be less than " + MAX_DATA_LENGTH);
        }
        mDevice.write(data, data.length, decimalPoint);
    }

}
