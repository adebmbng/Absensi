package com.debam.absensi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.debam.absensi.BaseActivity;
import com.debam.absensi.R;
import com.debam.absensi.service.response.LoginResponse;
import com.debam.absensi.utils.Constant;
import com.debam.absensi.utils.Util;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Debam on 9/7/2016.
 */

public class LoginActivity extends BaseActivity {

    private Context ctx;
    private String token;

    @Bind(R.id.etUsername)EditText etUsername;
    @Bind(R.id.etPassword)EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        ctx = this;
        ButterKnife.bind(this);
        if(Util.LoginAs(ctx)!= null) {
            if (Util.LoginAs(ctx).equalsIgnoreCase("mahasiswa") ||
                    Util.LoginAs(ctx).equalsIgnoreCase("dosen")) {
                Intent i = new Intent(LoginActivity.this, MainAct.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }

        token = FirebaseInstanceId.getInstance().getToken();

        Log.e("token", FirebaseInstanceId.getInstance().getToken()+"");
        Util.saveString(getApplicationContext(), Constant.SP.Token, token);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @OnClick(R.id.btnLogin) void goLogin(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("d:k:m");

        token = FirebaseInstanceId.getInstance().getToken();

        Log.e("token", FirebaseInstanceId.getInstance().getToken()+"");
        Util.saveString(getApplicationContext(), Constant.SP.Token, token);

        String currentDate = sd.format(c.getTime());

        if(currentDate.length()==7){
            currentDate = "0"+currentDate;
        }

        Log.e("date",currentDate);

        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());

        final Call<LoginResponse> call;
        call = getService().login(etUsername.getText().toString(),
                etPassword.getText().toString(),
                token,
                currentDate);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    if (response.body().getStatus().equals("Mahasiswa")){
                        Util.saveString(ctx,Constant.SP.Name,response.body().getName());
                        Intent i = new Intent(LoginActivity.this, MainAct.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        Toast.makeText(ctx,"Login as Mahasiswa",Toast.LENGTH_LONG).show();

                        Util.saveString(ctx, Constant.SP.Login, "Mahasiswa");
                    } else if (response.body().getStatus().equals("Dosen")){
                        Util.saveString(ctx,Constant.SP.Name,response.body().getName());
                        Intent i = new Intent(LoginActivity.this, MainAct.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        Toast.makeText(ctx,"Login as Dosen",Toast.LENGTH_LONG).show();
                        Util.saveString(ctx, Constant.SP.Login, "Dosen");
                    } else
                        Toast.makeText(ctx,response.body().getStatus(),Toast.LENGTH_LONG).show();

                    Util.saveString(ctx, Constant.SP.User, etUsername.getText().toString());

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ctx,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
