package com.debam.absensi.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.debam.absensi.BaseActivity;
import com.debam.absensi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Debam on 10/12/2016.
 */

public class GagalActivity extends BaseActivity {

    @Bind(R.id.textView7)
    TextView errorMessage;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_gagal);

        ctx = getApplication();
        ButterKnife.bind(this);

        errorMessage.setText(getIntent().getStringExtra("err"));

    }
}
