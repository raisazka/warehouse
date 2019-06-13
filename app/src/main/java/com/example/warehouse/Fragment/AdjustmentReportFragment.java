package com.example.warehouse.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.warehouse.Network.ReportService;
import com.example.warehouse.Network.RetrofitBuilder;
import com.example.warehouse.R;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdjustmentReportFragment extends Fragment {
    View v;

    ReportService service;
    Call<ResponseBody> call;
    Button generateBtn;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "AdjustmentReport";
    public AdjustmentReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_adjustment_report, container, false);
        generateBtn = v.findViewById(R.id.btn_generate_adjust);
        service = RetrofitBuilder.getRetrofit().create(ReportService.class);
        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked");
                if(checkPermission()){
                    call = service.getAdjustmentReport();
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                            Log.w(TAG, "onResponse: " + response);
                            final String filename = System.currentTimeMillis() /1000L+".csv";
                            if(response.isSuccessful()){
                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        try{
                                            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                                            File file = new File(path+File.separator+"adjustment"+File.separator,filename);
                                            file.getParentFile().mkdirs();
                                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                                            IOUtils.write(response.body().bytes(), fileOutputStream);
                                        }catch (IOException e){
                                            Log.e(TAG, "Erorr While Writing Files");
                                            Log.e(TAG, e.toString());
                                        }
                                        return null;
                                    }
                                    @Override
                                    protected void onPostExecute(Void aVoid) {
                                        super.onPostExecute(aVoid);
                                        Toast.makeText(getActivity(), "Download Success", Toast.LENGTH_SHORT).show();
                                    }
                                }.execute();
                                Log.d(TAG, "onResponse2: " +response);
                            }else{
                                Toast.makeText(getActivity(), "Download Fail", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse3: " +response);
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.getMessage());
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    requestPermission();
                }
            }
        });
        return v;
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return  false;
        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getActivity(), "Starting....", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(), "Please Grant Permission", Toast.LENGTH_LONG).show();
                }
        }
    }

}
