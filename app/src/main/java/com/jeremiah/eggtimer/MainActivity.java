package com.jeremiah.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    boolean isStopped;
    SeekBar timeSetter;
    TextView timer;
    MediaPlayer mediaPlayer;
    Button button;
    CountDownTimer eggTimer;



    public void startOrStop(View view){
        if (isStopped){
            isStopped = false;
            button.setText("Stop");
            timeSetter.setEnabled(false);

             eggTimer = new CountDownTimer(timeSetter.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String timeText = String.format(Locale.getDefault(), "%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) %60,
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) %60);

                    timer.setText(timeText);
                }

                @Override
                public void onFinish() {
                    timeSetter.setProgress(30);
                    timeSetter.setEnabled(true);
                    button.setText("Go");
                    mediaPlayer.start();
                    isStopped = true;
                }
            }.start();

        } else{
            timeSetter.setProgress(30);
            timeSetter.setEnabled(true);
            timer.setText("00:30");
            button.setText("Go");
            isStopped = true;
            eggTimer.cancel();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStopped = true;
         timer = (TextView) findViewById(R.id.timer);
        timeSetter = (SeekBar) findViewById(R.id.timeSetter);
        timeSetter.setMax(600);
        timeSetter.setProgress(30);
        button = (Button) findViewById(R.id.button);


        mediaPlayer = this.mediaPlayer.create(this, R.raw.airhorn);

        timeSetter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String timeText = String.format(Locale.getDefault(), "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(progress * 1000) %60,
                        TimeUnit.MILLISECONDS.toSeconds(progress * 1000) %60);
                timer.setText(timeText);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){
                button.setVisibility(View.VISIBLE);
            }
        });

    }
}
