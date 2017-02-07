package com.debam.absensi.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debam.absensi.R;
import com.debam.absensi.fragment.Home;
import com.debam.absensi.utils.object.MataKuliah;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private ArrayList<MataKuliah> mainData;

    public MainAdapter(ArrayList<MataKuliah> site) {
        this.mainData = site;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        v.setOnClickListener(Home.mainOnClickListener);

        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, final int position) {


//        TextView textNrp=holder.textnrp;
        TextView textIdmatkul =holder.textidmatkul;
        TextView textMatkul =holder.textmatkul;
        TextView textStatus =holder.textstatus;
        TextView textHari =holder.texthari;


//        textNrp.setText(mainData.get(position).getNrp());
        textIdmatkul.setText("Mata kuliah : "+mainData.get(position).getNama_matkul());
        textMatkul.setText("SKS : "+mainData.get(position).getSks());
        textStatus.setText("Kelas : "+mainData.get(position).getKelas());
        textHari.setText("Hari : "+mainData.get(position).getHari()+ " "+mainData.get(position).getJam());
    }

    @Override
    public int getItemCount() {
        return mainData.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {


        TextView textnrp;
        TextView textidmatkul;
        TextView textmatkul;
        TextView textstatus;
        TextView texthari;

        public MainViewHolder(View v) {
            super(v);
            this.textnrp = (TextView) v.findViewById(R.id.nrp);
            this.textidmatkul = (TextView) v.findViewById(R.id.card_idmatkul);
            this.textmatkul = (TextView) v.findViewById(R.id.card_matkul);
            this.textstatus = (TextView) v.findViewById(R.id.card_status);
            this.texthari = (TextView) v.findViewById(R.id.card_hari);

        }
    }
}
