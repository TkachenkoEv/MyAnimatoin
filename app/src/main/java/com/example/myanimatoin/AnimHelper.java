package com.example.myanimatoin;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

public class AnimHelper extends Thread {

    private Context context;
    private final int rotate;
    private int count_rotate;
    private boolean isStop;
    private final ImageView current_iv;
    private final ImageView next_iv;

    private IEventEnd eventEnd;

    public void setEventEnd(IEventEnd eventEnd) {
        this.eventEnd = eventEnd;
    }

    private static int[] arrSlots = {
            R.drawable.cherry_done,
            R.drawable.sevent_done,
            R.drawable.bar_done,
            R.drawable.lemon_done,
            R.drawable.orange_done,
            R.drawable.waternelon_done,
            R.drawable.triple_done};

    private final Animation anim_current_img;
    private final Animation anim_next_img;

    public int getRotate() {
        return rotate;
    }

    public boolean isStop() {
        return isStop;
    }

    public AnimHelper(Context context, ImageView current_iv, ImageView next_iv, IEventEnd eventEnd) {
        this.rotate = getRandomRotate();
        this.count_rotate = 0;
        this.isStop = false;
        this.current_iv = current_iv;
        this.next_iv = next_iv;
        this.eventEnd = eventEnd;

        anim_current_img = AnimationUtils.loadAnimation(context, R.anim.trans_current_img);
        anim_next_img = AnimationUtils.loadAnimation(context, R.anim.trans_next_img);
    }

    public int getRandomRotate() {
        return new Random().nextInt(7);
    }

    public void doAnim() {

        current_iv.startAnimation(anim_current_img);
        next_iv.startAnimation(anim_next_img);

        anim_next_img.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (count_rotate != rotate) {
                    current_iv.setImageResource(arrSlots[count_rotate]);
                    count_rotate++;
                    doAnim();
                } else {
                    count_rotate = 0;
                    isStop = true;
                    next_iv.setImageResource(arrSlots[rotate]);
                    eventEnd.showResult();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void run() {
        doAnim();
    }
}
