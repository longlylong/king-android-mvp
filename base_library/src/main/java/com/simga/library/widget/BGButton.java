package com.simga.library.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import com.simga.library.R;

import androidx.appcompat.widget.AppCompatTextView;


public class BGButton extends AppCompatTextView {

    private GradientDrawable gdp;
    private GradientDrawable gdn;
    private float topLeft;
    private float bottomLeft;
    private float topRight;
    private float bottomRight;
    private int pressedTextColor;
    private int normalTextColor;

    public BGButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        gdp = new GradientDrawable();
        gdn = new GradientDrawable();
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed}, gdp);
        bg.addState(new int[]{}, gdn);
        setBackgroundDrawable(bg);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BGButton);
        float radii = array.getDimension(R.styleable.BGButton_radii, -1);
        if (radii > -1)
            setRadii(radii);
        float topLeft = array.getDimension(R.styleable.BGButton_topLeftRadius, -1);
        if (topLeft != -1)
            setTopLeftRadius(topLeft);
        float topRight = array.getDimension(R.styleable.BGButton_topRightRadius, -1);
        if (topRight != -1)
            setTopRightRadius(topRight);
        float bottomLeft = array.getDimension(R.styleable.BGButton_bottomLeftRadius, -1);
        if (bottomLeft != -1)
            setBottomLeftRadius(bottomLeft);
        float bottomRight = array.getDimension(R.styleable.BGButton_bottomRightRadius, -1);
        if (bottomRight != -1)
            setBottomRightRadius(bottomRight);
        int normalSolid = array.getColor(R.styleable.BGButton_normalSolid, 0);
        setNormalSolid(normalSolid);
        int pressedSolid = array.getColor(R.styleable.BGButton_pressedSolid, normalSolid);
        setPressedSolid(pressedSolid);
        int normalStrokeWidth = (int) (array.getDimension(R.styleable.BGButton_normalStrokeWidth, 0) + .5f);
        int normalColor = array.getColor(R.styleable.BGButton_normalStrokeColor, 0);
        setNormalStroke(normalStrokeWidth, normalColor);
        int pressedStrokeWidth = (int) (array.getDimension(R.styleable.BGButton_pressedStrokeWidth, normalStrokeWidth) + .5f);
        int pressedColor = array.getColor(R.styleable.BGButton_pressedStrokeColor, normalColor);
        setPressedStroke(pressedStrokeWidth, pressedColor);
        this.normalTextColor = array.getColor(R.styleable.BGButton_normalTextColor, getCurrentTextColor());
        this.pressedTextColor = array.getColor(R.styleable.BGButton_pressedTextColor, normalTextColor);
        setTextSateListColor();
        array.recycle();

    }

    public void setTopLeftRadius(float topLeftRadius) {
        topLeft = topLeftRadius;
        resetRadius();
    }

    public void setTopRightRadius(float topRightRadius) {
        topRight = topRightRadius;
        resetRadius();
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        bottomLeft = bottomLeftRadius;
        resetRadius();
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        bottomRight = bottomRightRadius;
        resetRadius();
    }

    public void setRadii(float radii) {
        topLeft = topRight = bottomRight = bottomLeft = radii;
        resetRadius();
    }

    public void setNormalSolid(int color) {
        gdn.setColor(color);
    }

    public void setPressedSolid(int color) {
        gdp.setColor(color);
    }

    public void setNormalStroke(int width, int color) {
        gdn.setStroke(width, color);
    }

    public void setPressedStroke(int width, int color) {
        gdp.setStroke(width, color);
    }

    public void setPressedTextColor(int pressedTextColor) {
        this.pressedTextColor = pressedTextColor;
        setTextSateListColor();
    }

    @Override
    public void setTextColor(int color) {
        this.normalTextColor = color;
        setTextSateListColor();
    }

    private void resetRadius() {
        float[] radii = new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft};
        gdn.setCornerRadii(radii);
        gdp.setCornerRadii(radii);
    }

    private void setTextSateListColor() {
        int[][] state = new int[2][];
        state[0] = new int[]{android.R.attr.state_pressed};
        state[1] = new int[]{};
        int[] colors = new int[]{pressedTextColor, normalTextColor};
        ColorStateList colorStateList = new ColorStateList(state, colors);
        setTextColor(colorStateList);
    }
}
