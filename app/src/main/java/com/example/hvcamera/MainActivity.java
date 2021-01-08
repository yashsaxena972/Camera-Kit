package com.example.hvcamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private CameraKitView cameraKitView;
    private ImageButton flipCameraButton, captureButton, toggleFlashButton;
    private boolean flashStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraKitView = findViewById(R.id.camera);

        flipCameraButton = findViewById(R.id.flip_camera_button);
        captureButton = findViewById(R.id.capture_button);
        toggleFlashButton = findViewById(R.id.toggle_flash_button);
    }

    public void onCaptureClick(View view) {
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                Toast.makeText(MainActivity.this, "Image Captured", Toast.LENGTH_SHORT).show();
//                File savedPhoto = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
//                try {
//                    FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
//                    outputStream.write(capturedImage);
//                    outputStream.close();
//                } catch (java.io.IOException e) {
//                    e.printStackTrace();
//                }

                File file = new File(Environment.getExternalStorageDirectory(), "photo.jpg");

                try {
                    FileOutputStream out = new FileOutputStream(file.getPath());
                    out.write(capturedImage);
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                intent.putExtra("imagePath", file.getPath());
                startActivity(intent);

            }


        });
    }

    public void onFlipCamera(View view) {
        cameraKitView.toggleFacing();
    }

    public void onFlashButtonClick(View view){
        if(flashStatus){
            cameraKitView.setFlash(CameraKit.FLASH_ON);
            toggleFlashButton.setImageResource(R.drawable.ic_flash_off_black_24dp);
        }
        else{
            cameraKitView.setFlash(CameraKit.FLASH_OFF);
            toggleFlashButton.setImageResource(R.drawable.ic_flash_on_black_24dp);
        }
        flashStatus = !flashStatus;
    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}