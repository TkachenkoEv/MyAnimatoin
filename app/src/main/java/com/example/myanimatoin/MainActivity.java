package com.example.myanimatoin;

import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity implements IEventEnd {

    private ImageView current_iv1;
    private ImageView current_iv2;
    private ImageView current_iv3;

    private ImageView next_iv1;
    private ImageView next_iv2;
    private ImageView next_iv3;

    private ImageView btn_up, btn_down;

    private TextView txt_score;
    private int score = 1000;

    private AnimHelper animHelper_1;
    private AnimHelper animHelper_2;
    private AnimHelper animHelper_3;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        current_iv1 = findViewById(R.id.current_iv1);
        current_iv2 = findViewById(R.id.current_iv2);
        current_iv3 = findViewById(R.id.current_iv3);

        next_iv1 = findViewById(R.id.next_iv1);
        next_iv2 = findViewById(R.id.next_iv2);
        next_iv3 = findViewById(R.id.next_iv3);

        btn_down = findViewById(R.id.btn_down);
        btn_up = findViewById(R.id.btn_up);

        txt_score = findViewById(R.id.txt_score);
    }

    public void onClickBtn(View view) {

        int min_cash = 50;
        if (score >= min_cash) {

            btn_up.setVisibility(View.GONE);
            btn_down.setVisibility(View.VISIBLE);

            score -= min_cash;
            txt_score.setText(String.valueOf(score));

            animHelper_1 = new AnimHelper(getApplicationContext(), current_iv1, next_iv1, MainActivity.this);
            animHelper_2 = new AnimHelper(getApplicationContext(), current_iv2, next_iv2, MainActivity.this);
            animHelper_3 = new AnimHelper(getApplicationContext(), current_iv3, next_iv3, MainActivity.this);

            animHelper_1.start();
            animHelper_2.start();
            animHelper_3.start();

        } else {
            toast("You not enought money").show();
        }
    }


    public void showResult() {

        if (animHelper_1.isStop() && animHelper_2.isStop() && animHelper_3.isStop()) {
            btn_up.setVisibility(View.VISIBLE);
            btn_down.setVisibility(View.GONE);

            if (animHelper_1.getRotate() == animHelper_2.getRotate() && animHelper_2.getRotate() == animHelper_3.getRotate()) {
                int max_win = 300;
                score += max_win;
                toast("+ " + max_win).show();
                txt_score.setText(String.valueOf(score));

            } else if (animHelper_1.getRotate() == animHelper_2.getRotate() ||
                    animHelper_2.getRotate() == animHelper_3.getRotate() ||
                    animHelper_1.getRotate() == animHelper_3.getRotate()) {
                int min_win = 150;
                score += min_win;
                toast("+ " + min_win).show();
                txt_score.setText(String.valueOf(score));

            } else {
                toast("You lose").show();
            }
        }


    }

    public Toast toast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);

        View view = toast.getView();
        view.getBackground().setColorFilter(getResources().getColor(R.color.fiolet), PorterDuff.Mode.SRC_IN);

        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(getResources().getColor(R.color.white));

        if (Build.VERSION.SDK_INT

                >= Build.VERSION_CODES.M) {

            text.setTextAppearance(

                    R.style.toastTextStyle);
        }

        return toast;
    }
}