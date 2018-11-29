package com.sly.baseframe.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sly.baseframe.R;


public class BaseSelectDialog extends Dialog implements View.OnClickListener {
    protected TextView mTextView;
    protected TextView mTextView2;
    protected TextView mTextView3;
    protected View.OnClickListener onClickListener;
    protected Context mContext;
    protected String mTitle;
    protected Object tag;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public BaseSelectDialog(Context ctx, int theme) {
        super(ctx, theme);
        this.setCanceledOnTouchOutside(false);
        this.mContext = ctx;
    }

    public BaseSelectDialog(Context ctx) {
        super(ctx, R.style.ListDialog);
        this.setCanceledOnTouchOutside(true);
        this.mContext = ctx;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Object getTag() {
        return this.tag;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.select_dialog);
        this.mTextView = (TextView)this.findViewById(R.id.tv_item1);
        this.mTextView.setOnClickListener(this);
        this.mTextView2 = (TextView)this.findViewById(R.id.tv_item2);
        this.mTextView2.setOnClickListener(this);
        this.mTextView3 = (TextView)this.findViewById(R.id.tv_cancel);
        this.mTextView3.setOnClickListener(this);
        this.resize();
    }


    public void show(int gravity) {
        this.getWindow().setGravity(gravity);
        this.show();
    }

    public void resize() {
        this.getWindow().setLayout(-1, -2);
    }

    public void onClick(View v) {
        if (this.onClickListener != null) {
            if (this.tag == null) {
                v.setTag(this.mTitle);
            } else {
                v.setTag(this.tag);
            }

            this.onClickListener.onClick(v);
        }

        this.dismiss();
    }
}
