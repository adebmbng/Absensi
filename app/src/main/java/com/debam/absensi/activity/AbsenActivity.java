package com.debam.absensi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.debam.absensi.BaseActivity;
import com.debam.absensi.BaseFragment;
import com.debam.absensi.R;
import com.debam.absensi.barcode.BarcodeCaptureActivity;
import com.debam.absensi.service.response.BaseResponse;
import com.debam.absensi.utils.Util;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Debam on 9/7/2016.
 */

public class AbsenActivity extends BaseFragment {

    private Context ctx;
    private static final int RC_BARCODE_CAPTURE = 9001;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    private String code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.absen_activity,container, false);
        ctx = getActivity();
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @OnClick(R.id.btnAbsen) void goAbsen(){
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        } catch (ActivityNotFoundException anfe) {
            showDialog(getActivity(), "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }

//        Intent intent = new Intent(ctx, BarcodeCaptureActivity.class);
//        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
//
//        startActivityForResult(intent, RC_BARCODE_CAPTURE);

//        final Call<BaseResponse> call;
//        call = getService().absenv2(Util.getUsername(ctx), "23455","123456782345");
//        call.enqueue(new Callback<BaseResponse>() {
//            @Override
//            public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
//                if(response.isSuccess()){
//                    if(response.body().getStatus().equalsIgnoreCase("OK")){
//                        Intent i = new Intent(ctx, BerhasilActivity.class);
//                        startActivity(i);
//                        //Toast.makeText(ctx,"Berhasil Absen",Toast.LENGTH_LONG).show();
//                    } else if(response.body().getStatus().equalsIgnoreCase("TIME LIMIT")){
//                        Intent i = new Intent(ctx, GagalActivity.class);
//                        i.putExtra("err","time is over for this code");
//                        startActivity(i);
//                        //Toast.makeText(ctx,"Sesi Absensi Berakhir",Toast.LENGTH_LONG).show();
//                    } else if(response.body().getStatus().equalsIgnoreCase("NOT FOUND")){
//                        Intent i = new Intent(ctx, GagalActivity.class);
//                        i.putExtra("err","QR code not found");
//                        startActivity(i);
//                        //Toast.makeText(ctx,"QR COde not found",Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Toast.makeText(ctx,t.toString(),Toast.LENGTH_LONG).show();
//
//            }
//        });
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == RESULT_OK ) {
                code = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

//                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
//                toast.show();
//                if (data != null) {
//                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
//                    code = (barcode.displayValue);
//                    String jadwal = code.substring(0,6);
//                    String qrcode = code.substring(6);
//                    Log.e("Code", "Barcode read: " + barcode.displayValue+" "+jadwal+" "+qrcode);
//                  Log.e("Code", "Barcode read: " + barcode.displayValue);
                    Log.e("Code",code);
                    final Call<BaseResponse> call;
                    call = getService().absen(Util.getUsername(ctx), code);
                    call.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                            if(response.isSuccess()){
                                if(response.body().getStatus().equalsIgnoreCase("OK")){
                                    Intent i = new Intent(ctx, BerhasilActivity.class);
                                    startActivity(i);
                                    //Toast.makeText(ctx,"Berhasil Absen",Toast.LENGTH_LONG).show();
                                } else if(response.body().getStatus().equalsIgnoreCase("TIME LIMIT")){
                                    Intent i = new Intent(ctx, GagalActivity.class);
                                    i.putExtra("err","time is over for this code");
                                    startActivity(i);
                                    //Toast.makeText(ctx,"Sesi Absensi Berakhir",Toast.LENGTH_LONG).show();
                                } else if(response.body().getStatus().equalsIgnoreCase("NOT FOUND")){
                                    Intent i = new Intent(ctx, GagalActivity.class);
                                    i.putExtra("err","QR code not found");
                                    startActivity(i);
                                    //Toast.makeText(ctx,"QR COde not found",Toast.LENGTH_LONG).show();
                                } else if(response.body().getStatus().equalsIgnoreCase("DUPLICATE")){
                                    Intent i = new Intent(ctx, GagalActivity.class);
                                    i.putExtra("err","Telah melakukan Presensi");
                                    startActivity(i);
                                    //Toast.makeText(ctx,"QR COde not found",Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(ctx,t.toString(),Toast.LENGTH_LONG).show();

                        }
                    });
                } else {
                    //dialog failed
                }
            } else {
                //dialog error
            }
//        }
//        else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
    }
}
