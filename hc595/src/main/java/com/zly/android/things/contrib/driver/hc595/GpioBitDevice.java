package com.zly.android.things.contrib.driver.hc595;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

/**
 * Created by zhuleiyue on 2017/12/21.
 */

public class GpioBitDevice implements AutoCloseable {
    private Gpio mData;
    private Gpio mSClock;
    private Gpio mRClock;

    GpioBitDevice(String pioData, String pioSClock, String pioRClock) {
        PeripheralManagerService pioService = new PeripheralManagerService();
        try {
            mData = pioService.openGpio(pioData);
            mData.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mSClock = pioService.openGpio(pioSClock);
            mSClock.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mRClock = pioService.openGpio(pioRClock);
            mRClock.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        if (mData != null) {
            try {
                mData.close();
            } finally {
                mData = null;
            }
        }
        if (mSClock != null) {
            try {
                mSClock.close();
            } finally {
                mSClock = null;
            }
        }
        if (mRClock != null) {
            try {
                mRClock.close();
            } finally {
                mRClock = null;
            }
        }
    }

    public void write(byte[] buffer, int size, byte point) throws IOException {
        for (int i = 0; i < size; i++) {
            writeNum(buffer[i], (point & (1 << i)) != 0);
            writeDigit(Font.START_DIGIT << i);
        }
    }

    private void writeNum(int num, boolean point) throws IOException {
        mData.setValue(point);
        mSClock.setValue(false);
        mSClock.setValue(true);
        for (int i = 0; i < 7; i++) {
            mData.setValue((num & (1 << i)) != 0);
            mSClock.setValue(false);
            mSClock.setValue(true);
        }
    }

    private void writeDigit(int digit) throws IOException {
        for (int i = 0; i < 8; i++) {
            mData.setValue((digit & (1 << i)) != 0);
            mSClock.setValue(false);
            mSClock.setValue(true);
        }
        mRClock.setValue(false);
        mRClock.setValue(true);
    }
}
