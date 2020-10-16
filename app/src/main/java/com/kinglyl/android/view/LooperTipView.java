package com.kinglyl.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.kinglyl.android.R;

import java.util.List;

/**
 * 上下循环的view
 * Created by Adminis on 2016/8/14.
 */
public class LooperTipView extends FrameLayout {

    private static final int ANIM_DELAYED_MILLIONS = 2 * 1000;
    private static final int ANIM_DURATION = 555;
    private List<String> tipList;
    private int curTipIndex = 0;
    private long lastTimeMillis;
    private View outTipView, inTipView;
    private Animation animOut, animIn;

    public LooperTipView(Context context) {
        super(context);
        initTipFrame();
        initAnimation();
    }

    public LooperTipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTipFrame();
        initAnimation();
    }

    public LooperTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTipFrame();
        initAnimation();
    }

    private void initTipFrame() {
        inTipView = newView();
        outTipView = newView();
        addView(inTipView);
        addView(outTipView);
    }

    private View newView() {
        View view = View.inflate(getContext(), R.layout.view_home_tips, null);
        return view;
    }

    private void initAnimation() {
        animOut = newAnimation(0, -1);
        animIn = newAnimation(1, 0);
        animIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                updateTipAndPlayAnimationWithCheck();
            }
        });
    }

    private Animation newAnimation(float fromYValue, float toYValue) {
        Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF, toYValue);
        anim.setDuration(ANIM_DURATION);
        anim.setStartOffset(ANIM_DELAYED_MILLIONS);
        anim.setInterpolator(new DecelerateInterpolator());
        return anim;
    }

    private void updateTipAndPlayAnimationWithCheck() {
        if (System.currentTimeMillis() - lastTimeMillis < 1000) {
            return;
        }
        lastTimeMillis = System.currentTimeMillis();
        updateTipAndPlayAnimation();
    }

    private void updateTipAndPlayAnimation() {
        if (curTipIndex % 2 == 0) {
            updateTipView(outTipView);
            inTipView.startAnimation(animOut);
            outTipView.startAnimation(animIn);
            this.bringChildToFront(inTipView);
        } else {
            updateTipView(inTipView);
            outTipView.startAnimation(animOut);
            inTipView.startAnimation(animIn);
            this.bringChildToFront(outTipView);
        }
    }

    private void updateTipView(View tipView) {
        String msgData = getNextTip();
        if (msgData == null) {
            return;
        }

//        BabushkaText babushkaText = tipView.findViewById(R.id.item_home_tips_text);
//
//        WImageView imageView = tipView.findViewById(R.id.item_home_tips_icon);
//        imageView.display(msgData.avatar);
//
//        ActionUtil.setTypeface(babushkaText);
//
//        babushkaText.reset();
//        BabushkaText.Piece.Builder p1 = new BabushkaText.Piece.Builder("用户" + msgData.nickname + " 跟单\"" + msgData.analyst + "\"共收益");
//        BabushkaText.Piece.Builder p2 = new BabushkaText.Piece.Builder("$" + msgData.income);
//        p1.textColor(getResources().getColor(R.color.text_color_secondary_666));
//        p2.textColor(getResources().getColor(R.color.wave_red));
//
//        babushkaText.addPiece(p1.build());
//        babushkaText.addPiece(p2.build());
//        babushkaText.display();

    }

    /**
     * 获取下一条消息
     */
    private String getNextTip() {
        if (isListEmpty(tipList)) return null;
        return tipList.get(curTipIndex++ % tipList.size());
    }

    public boolean isListEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public void setTipList(List<String> tipList) {
        this.tipList = tipList;
        curTipIndex = 0;
        updateTipView(outTipView);
        updateTipAndPlayAnimation();
    }
}