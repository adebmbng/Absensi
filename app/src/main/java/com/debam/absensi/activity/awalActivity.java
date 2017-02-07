package com.debam.absensi.activity;

import android.content.Intent;
import android.os.Bundle;

import com.debam.absensi.BaseActivity;
import com.debam.absensi.R;
import com.debam.absensi.utils.Constant;
import com.debam.absensi.utils.Util;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Muadz Nurhasan on 24/11/2016.
 */
public class awalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.halawal);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnawal)void tampilawal(){
//        Util.saveString(this, Constant.SP.Login, "dosen");
        Intent aw=new Intent(awalActivity.this,LoginActivity.class);
        startActivity(aw);
    }
}
