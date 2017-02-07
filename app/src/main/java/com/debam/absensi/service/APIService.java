package com.debam.absensi.service;

import com.debam.absensi.BaseActivity;
import com.debam.absensi.service.response.BaseResponse;
import com.debam.absensi.service.response.LoginResponse;
import com.debam.absensi.service.response.MatkulResponse;
import com.debam.absensi.utils.Constant;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Debam on 9/7/2016.
 */

public interface APIService {

    @FormUrlEncoded
    @POST(Constant.URL.Login)
    public Call<LoginResponse> login    (@Field("username") String username,
                                         @Field("password") String password,
                                         @Field("token") String token,
                                         @Field("time") String time);

    @FormUrlEncoded
    @POST(Constant.URL.getMatkul)
    public Call<MatkulResponse> getMatkul   (@Field("username") String username);

    @FormUrlEncoded
    @POST(Constant.URL.Absen)
    public Call<BaseResponse> absen     (@Field("username") String username,
                                         @Field("code") String code);

    @FormUrlEncoded
    @POST(Constant.URL.Absen)
    public Call<BaseResponse> absenv2 (@Field("username") String username,
                                       @Field("jadwal") String jadwal,
                                       @Field("code") String code);

    @FormUrlEncoded
    @POST(Constant.URL.getMatkulDosen)
    public Call<MatkulResponse> getMatkulDosen  (@Field("username") String username);

    @FormUrlEncoded
    @POST(Constant.URL.logout)
    public Call<BaseResponse> logout  (@Field("username") String username,
                                       @Field("as") String as);
}
