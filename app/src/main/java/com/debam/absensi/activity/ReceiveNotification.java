package com.debam.absensi.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.debam.absensi.BaseActivity;
import com.debam.absensi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Debam on 10/13/2016.
 */

public class ReceiveNotification extends BaseActivity {


    @Bind(R.id.tVmatkul)
    TextView tvmatkul;
    @Bind(R.id.tVwaktu)
    TextView tvwaktu;
    @Bind(R.id.tVruang)
    TextView tvruang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_notification);

        ButterKnife.bind(this);

        String waktu = getIntent().getStringExtra("waktu");
        String matkul = getIntent().getStringExtra("matkul");
        String ruang = getIntent().getStringExtra("ruang");

        tvwaktu.setText(waktu);
        tvmatkul.setText(matkul);
        tvruang.setText(ruang);
    }

    @OnClick(R.id.btnotif)void close(){
        finish();
    }
}
