package com.example.clockapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.clockapp.Object.Util;
import com.example.clockapp.R;
import com.example.clockapp.service.AlarmService;

import java.util.Random;

public class LockScreenActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    ImageView imgClock;
    EditText inputNum;
    TextView tvNumQuiz, tvErr;
    RelativeLayout layoutAlarm, layoutQuiz;
    ImageButton ivPause;
    int numQuiz = 0;
    int id = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        initView();
        setQuiz();
        //thiết lập cho activity có thể hiển thị trong màn hình khóa
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(this, null);
        } else {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
        boolean isQuiz = getIntent().getBooleanExtra("quiz", false);
        id = getIntent().getIntExtra("id",1234);
        //chuyển động cho img
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imgClock.startAnimation(anim);
        imgClock.setOnClickListener(v -> {
            if (isQuiz) {
                layoutQuiz.setVisibility(View.VISIBLE);
                layoutAlarm.setVisibility(View.GONE);
            } else {
                cancelAlarm();
            }
        });
        ivPause.setOnClickListener(v->pauseAlarm());
    }

    private void initView() {
        imgClock = findViewById(R.id.imgClock);
        inputNum = findViewById(R.id.input_num);
        tvNumQuiz = findViewById(R.id.tv_num_quiz);
        tvErr = findViewById(R.id.tv_err);
        layoutAlarm = findViewById(R.id.layout_alarm);
        layoutQuiz = findViewById(R.id.layout_quiz);
        ivPause = findViewById(R.id.iv_pause);
        //
        inputNum.setFocusable(true);
        inputNum.requestFocus();
        inputNum.setImeOptions(EditorInfo.IME_ACTION_DONE);
        inputNum.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (!inputNum.getText().toString().isEmpty()) {
                if (Integer.parseInt(inputNum.getText().toString()) == numQuiz) {
                    cancelAlarm();
                } else {
                    tvErr.setVisibility(View.VISIBLE);
                    inputNum.getText().clear();
                }
            }
            return true;
        }
        return false;
    }

    public void setQuiz() {
        Random random = new Random();
        int max = Integer.MAX_VALUE-1;
        int min = 1000000000;
        numQuiz = random.nextInt(max - min) + min;
        tvNumQuiz.setText(numQuiz + "");
    }

    public void cancelAlarm() {
        //hủy hẳn báo thức
        stopService(new Intent(this, AlarmService.class));
        Util.cancelAlarm(this,id);
        finishAffinity();
    }
    public void pauseAlarm(){
        //tạm dừng báo thức
        stopService(new Intent(this, AlarmService.class));
        finishAffinity();
    }
}