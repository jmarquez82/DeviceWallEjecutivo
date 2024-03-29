package cl.rinno.newdevicewall.cls;

import android.os.CountDownTimer;

import java.util.Random;

import cl.rinno.newdevicewall.MainActivity;

/**
 * Created by chinodoge on 30-03-2017.
 */

public class TimerDestacado extends CountDownTimer {

    MainActivity mainActivity;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimerDestacado(long millisInFuture, long countDownInterval, MainActivity mainActivity) {
        super(millisInFuture, countDownInterval);
        this.mainActivity = mainActivity;
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {
        mainActivity.moveGrid();
        this.start();
    }
}
