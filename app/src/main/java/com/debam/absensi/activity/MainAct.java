package com.debam.absensi.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.debam.absensi.BaseActivity;
import com.debam.absensi.R;
import com.debam.absensi.fragment.About;
import com.debam.absensi.fragment.CheckAbsen;
import com.debam.absensi.fragment.Help;
import com.debam.absensi.fragment.Home;
import com.debam.absensi.fragment.Qrcode;
import com.debam.absensi.service.response.BaseResponse;
import com.debam.absensi.service.response.MatkulResponse;
import com.debam.absensi.utils.Constant;
import com.debam.absensi.utils.Util;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainAct extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String loginAs;

    Fragment hm =new Home();
    Fragment qr =new AbsenActivity();
    Fragment hl =new Help();
    Fragment ab =new About();
    Fragment ca =new CheckAbsen();

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ctx = this;

        loginAs = Util.LoginAs(ctx);

        changefragment(hm);
        

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(loginAs.equalsIgnoreCase("mahasiswa")) {
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }else{
            navigationView.inflateMenu(R.menu.activity_main_drawer_dosen);
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Log.e("Home",Util.getUsername(ctx));
            changefragment(hm);
        } else if (id == R.id.nav_qrcode) {

            changefragment(qr);
        } else if (id == R.id.nav_help) {

            changefragment(hl);
        } else if (id == R.id.nav_about) {

            changefragment(ab);
        } else if (id == R.id.nav_checkAbsen) {

            changefragment(ca);
        } else if (id == R.id.nav_exit) {
            final Call<BaseResponse> call;

            call = getService().logout(Util.getUsername(ctx).toString(), Util.LoginAs(ctx).toString());
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                    if(response.isSuccess()){
                        if(response.body().getStatus().equalsIgnoreCase("OK")){
                            Util.saveString(ctx, Constant.SP.Login, "logout");
                            Log.e("Logout",Util.getUsername(ctx));
                            Intent i = new Intent(MainAct.this, LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        } else{
                            Toast.makeText(ctx,"Mohon Clear Data Apps",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(ctx,"Mohon periksa koneksi anda",Toast.LENGTH_LONG).show();
                }
            });



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void changefragment(Fragment fr) {
        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.content, fr).commit();

    }
}
