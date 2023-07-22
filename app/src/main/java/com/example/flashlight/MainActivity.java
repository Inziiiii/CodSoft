package com.example.flashlight;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {


    SwitchCompat switch1;
    RelativeLayout background;
    TextView resultt;
    CameraManager cameraManager;

    private boolean flashoff = true;
    String camera_id,result;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switch1 = findViewById(R.id.swtch);
        background = findViewById(R.id.background);
        resultt = findViewById(R.id.result);

        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH )){
            switch1.setEnabled(true);

        }else{
            Toast.makeText(MainActivity.this,"This device has no flashlight available",Toast.LENGTH_SHORT).show();
        }

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    torch(true);
                    background.setBackgroundColor(Color.parseColor("#1EA1A1"));
                } else {
                    torch(false);
                    flashoff = true;
                    background.setBackgroundColor(Color.parseColor("#008080"));

                }
            }
        });
    }

    private void torch(boolean isChecked) {
        try {
            camera_id = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(camera_id,isChecked);
            result = isChecked?"ON":"OFF";
            resultt.setText(result);
            flashoff = false;
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(flashoff){
            Toast.makeText(this, "Flash is already turned off", Toast.LENGTH_SHORT).show();
        }else  {
            flashoff = false;
            torch(false);
            Toast.makeText(this, "Flash is  turned off", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onResume() {
        //on resume the flash is turning onn
        super.onResume();
        if(flashoff){
            Toast.makeText(this, "Please turn on flash", Toast.LENGTH_SHORT).show();
        }else {
            flashoff = false;
            torch(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Activity Destroyed", Toast.LENGTH_SHORT).show();
    }
}
