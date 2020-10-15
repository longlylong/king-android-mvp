package com.simga.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 *  listview 用于不需要滚动的listview
 * @author Administrator
 *
 */
public class NListView extends ListView {
	
	public NListView(Context context) {
		super(context);
	}

	public NListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public NListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
