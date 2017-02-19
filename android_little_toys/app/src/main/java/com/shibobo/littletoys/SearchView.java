package com.shibobo.littletoys;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class SearchView extends LinearLayout implements View.OnClickListener,TextWatcher {
    private EditText mEditText;
    private Button mClear;
    private Button mGo;
    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //加载布局文件
        LayoutInflater.from(context).inflate(R.layout.search,this,true);
        //找到各个控件
        mEditText= (EditText) findViewById(R.id.mEditText);
        mClear= (Button) findViewById(R.id.mClear);
        mGo= (Button) findViewById(R.id.mGo);
        mClear.setVisibility(View.GONE);
        mGo.setVisibility(View.GONE);
        mEditText.addTextChangedListener(this);
        mClear.setOnClickListener(this);
        mGo.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * 输入文字之后
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        String input=mEditText.getText().toString();
        if (!input.isEmpty()){
            mClear.setVisibility(View.VISIBLE);
            mGo.setVisibility(View.VISIBLE);
        }else{
            mClear.setVisibility(View.GONE);
            mGo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mGo:
                Toast.makeText(getContext(),"搜索:"+mEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                mEditText.setText("");
                break;
            case R.id.mClear:
                mEditText.setText("");
                break;
        }
    }
}
