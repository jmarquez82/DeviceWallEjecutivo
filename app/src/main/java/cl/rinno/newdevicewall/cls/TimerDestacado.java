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

        Random random = new Random();
        switch (random.nextInt(11)){
            case 0:
                mainActivity.openImageHigh();
                break;
            case 1:
                mainActivity.moveGrid(-960);
                break;
            case 2:
                mainActivity.moveGrid(960);
                break;
            case 3:
                mainActivity.moveGrid(-960);
                break;
            case 4:
                mainActivity.openImageHigh();
                break;
            case 5:
                mainActivity.moveGrid(960);
                break;
            case 6:
                mainActivity.moveGrid(-960);
                break;
            case 7:
                mainActivity.moveGrid(960);
                break;
            case 8:
                mainActivity.moveGrid(-960);
                break;
            case 9:
                mainActivity.moveGrid(960);
                break;
            case 10:
                mainActivity.moveGrid(-960);
                break;
            default:
                mainActivity.openImageHigh();
                break;
        }
        this.start();
    }
}
