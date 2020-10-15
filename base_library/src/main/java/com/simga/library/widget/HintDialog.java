package com.simga.library.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.simga.library.R;
import com.zzhoujay.richtext.RichText;

/**
 * PackageName : com.example.defaultproject.ui.find
 * Author : Simga
 * Date : 2018/01/30
 * Time : 15:30
 */
public class HintDialog extends Dialog {

    TextView tvTitle;
    TextView tvContent;
    BGButton cancel;
    BGButton commit;
    View view1;

    private Callback callback;

    /**
     * @param context 上下文
     * @param content 提示的文字
     * @param btnText 按钮显示的文字，最多长度为两个
     */
    public HintDialog(Context context, String title, String content, String[] btnText) {
        super(context, R.style.Dialog);
        this.setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_hint);
        initView();
        initListener();
        tvTitle.setText(title);
        if (content != null) {
            RichText.from(content)
                    .clickable(false)
                    .into(tvContent);
        }
        if (btnText.length > 0) {
            if (btnText.length > 1) {
                cancel.setText(btnText[0]);
                commit.setText(btnText[1]);
            } else {
                view1.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                commit.setText(btnText[0]);
            }
        } else {
            cancel.setVisibility(View.GONE);
            commit.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.callback();
                }
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.cancle();
                }
                dismiss();
            }
        });
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        cancel = (BGButton) findViewById(R.id.cancel);
        commit = (BGButton) findViewById(R.id.commit);
        view1 = (View) findViewById(R.id.view1);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void callback();

        void cancle();
    }

}
