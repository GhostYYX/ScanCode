package com.yyx.scancode.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yyx.scancode.R;
import com.yyx.scancode.zxing.activity.CaptureActivity;

/**
 * Created by vimi8 on 2017/3/28.
 */

public class ScanCodeFragmen extends Fragment {

    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.fragment_scancode, container, false);

        //点击触发扫码功能
        ImageButton button = (ImageButton)mView.findViewById(R.id.core);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断Android版本是否为6.0以上，如果是则进行权限允许
                if (Build.VERSION.SDK_INT >= 23){
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
                    if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},222);
                        return;
                    }
                }
                startActivity(new Intent(getContext(), CaptureActivity.class));
            }
        });



        return mView;
    }

    //启用相机权限的回调函数
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getContext(), "很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
