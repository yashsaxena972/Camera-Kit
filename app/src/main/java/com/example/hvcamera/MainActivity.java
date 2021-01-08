package com.example.hvcamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        cameraKitView.setSensorPreset(CameraKit.SENSOR_PRESET_LANDSCAPE);
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                Toast.makeText(MainActivity.this, "Image Captured", Toast.LENGTH_SHORT).show();
                    File pictureFileDir = new File(getCacheDir()+File.separator+"images");
                    Log.d("path",""+pictureFileDir);

                pictureFileDir.mkdirs();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                    String date = dateFormat.format(new Date());
                    String photoFile = "Picture_" + date + ".jpg";

                    String filename = pictureFileDir.getPath() + File.separator + photoFile;

                    File pictureFile = new File(filename);
                Log.d("path",""+pictureFileDir);

                try {
                        FileOutputStream fos = new FileOutputStream(pictureFile);
                        fos.write(capturedImage);
                        fos.close();
                        Toast.makeText(MainActivity.this, "New Image saved:" + photoFile,
                                Toast.LENGTH_LONG).show();
                    } catch (Exception error) {
                        Log.d("tag", "File" + filename + "not saved: "
                                + error.getMessage());
                        Toast.makeText(MainActivity.this, "Image could not be saved.",
                                Toast.LENGTH_LONG).show();
                    }


                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                intent.putExtra("imagePath", pictureFile.getPath());
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