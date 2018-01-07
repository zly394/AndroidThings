package com.zly.android.things.contrib.driver.hc595;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by zhuleiyue on 2017/12/22.
 */

public class NumericDisplay extends HC595 {
    private ByteBuffer mBuffer = ByteBuffer.wrap(new byte[]{Font.CLEAR, Font.CLEAR, Font.CLEAR, Font.CLEAR});

    public NumericDisplay(String pioData, String pioSClock, String pioRClock) {
        super(pioData, pioSClock, pioRClock);
        start();
    }

    public void clear() {
        mBuffer.put(new byte[]{Font.CLEAR, Font.CLEAR, Font.CLEAR, Font.CLEAR});
    }

    /**
     * Display an integer number
     *
     * @param n number value
     */
    public void display(int n) {
        display(String.format("%4s", n));
    }

    /**
     * Display a string containing only numeric characters, spaces, and hyphens.
     *
     * @param s string value
     */
    public void display(String s) {
        if (TextUtils.isEmpty(s)) {
            clear();
            return;
        }

        mBuffer.clear();
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            // truncate string to the size of the display
            if (mBuffer.position() == mBuffer.limit()) {
                break;
            }
            if (c == ' ') {
                mBuffer.put(Font.CLEAR);
            } else if (c == '-') {
                mBuffer.put(Font.HYPHEN);
            } else if (c >= '0' && c <= '9') {
                // extract character data from font.
                mBuffer.put(Font.DATA[c - '0']);
            } else if (c == '.') {
                // show decimal point
                if (i != 0) {
                    decimalPoint &= ~(1 << (i - 1));
                }
            } else {
                throw new IllegalArgumentException("unsupported character: " + c);
            }
        }
        // clear the rest of the buffer
        while (mBuffer.position() < mBuffer.capacity()) {
            mBuffer.put(Font.CLEAR);
        }
        mBuffer.flip();
    }

    private void start() {
        new Thread(() -> {
            while (true) {
                try {
                    writeData(mBuffer.array());
                } catch (IOException e) {
                    Log.e(TAG, "Error configuring display", e);
                    break;
                }
            }
        }).start();
    }
}
