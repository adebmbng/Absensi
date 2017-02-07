package com.debam.absensi.service.response;

import com.debam.absensi.utils.object.MataKuliah;

import java.util.ArrayList;

/**
 * Created by Debam on 9/7/2016.
 */
public class MatkulResponse extends BaseResponse{

    public ArrayList<MataKuliah> getMatkul() {
        return matkul;
    }

    ArrayList<MataKuliah> matkul;

}
