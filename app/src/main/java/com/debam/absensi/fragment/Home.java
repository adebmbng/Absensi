package com.debam.absensi.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.debam.absensi.BaseFragment;
import com.debam.absensi.R;
import com.debam.absensi.activity.LoginActivity;
import com.debam.absensi.activity.MainAct;
import com.debam.absensi.adapter.MainAdapter;
import com.debam.absensi.service.response.LoginResponse;
import com.debam.absensi.service.response.MatkulResponse;
import com.debam.absensi.utils.Constant;
import com.debam.absensi.utils.Util;
import com.debam.absensi.utils.object.MataKuliah;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by rustandiY on 9/7/2016.
 */
public class Home extends BaseFragment {
    public static int currentItem;
    private Context ctx;
    public static View.OnClickListener mainOnClickListener;
    private RecyclerView recyclerView;
    private ArrayList<MataKuliah> mt;
    String[] nrp={"152012043","152012047","152012043","152012047","152012043","152012047"};
    String[] id_matkul={"if-201","if-202","if-201","if-202","if-201","if-202"};
    String[] matkul={"oop","stukturdata","oop","stukturdata","oop","stukturdata"};
    String[] status={"berhasi","gagal","berhasi","gagal","berhasi","gagal"};
    String[] hari={"jumat 11.00","kamis 12.00","jumat 11.00","kamis 12.00","jumat 11.00","kamis 12.00"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.content_home,container, false);

        ctx = getActivity();

        mainOnClickListener = new MainOnClickListener(getActivity());

        recyclerView = (RecyclerView) rootview.findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);

//        ArrayList<MataKuliah> mainData = new ArrayList<MataKuliah>();
//        for (int i = 0; i < nrp.length; i++) {
//            MataKuliah a =new MataKuliah();
//            a.setId(id_matkul[i]);
//            a.setNama(matkul[i]);
//            a.setPersen(status[i]);
//            a.setJadwal(hari[i]);
//            mainData.add(a);
//        }

        requestMataKuliah();


        return rootview;
    }

    private void requestMataKuliah(){
        final Call<MatkulResponse> call;
        if(Util.LoginAs(ctx).equalsIgnoreCase("Mahasiswa")){
            call = getService().getMatkul(Util.getUsername(ctx));
        } else {
            call = getService().getMatkulDosen(Util.getUsername(ctx));
        }

        call.enqueue(new Callback<MatkulResponse>() {
            @Override
            public void onResponse(Response<MatkulResponse> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    mt = response.body().getMatkul();

                    RecyclerView.Adapter adapter = new MainAdapter(mt);
                    // this line is the one that shows the cards
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ctx,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public class MainOnClickListener implements View.OnClickListener {
        private final Context context;

        private MainOnClickListener(Context c) {
            this.context = c;
        }

        @Override
        public void onClick(View v) {
            currentItem = recyclerView.getChildPosition(v);
            //startActivity(new Intent(getApplicationContext(), DetailActivity.class));
            Toast.makeText(getActivity(),""+currentItem,Toast.LENGTH_LONG).show();
        }

    }
}
