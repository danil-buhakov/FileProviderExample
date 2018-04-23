package com.example.dan.fileprovider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

public class MainActivity extends AppCompatActivity {
  Button btTakePhoto;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setPermission();
    btTakePhoto = (Button) findViewById(R.id.bt_take_photo);
    btTakePhoto.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        launchCamera();
      }
    });
  }

  private void setPermission(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        requestPermissions(new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        },1);
    }
  }

  private void launchCamera(){
    String fileName = "cameraOutput" + System.currentTimeMillis() + ".jpg";
    File imagePath = new File(Environment.getExternalStorageDirectory(), "images");
    imagePath.mkdirs();
    File file = new File(imagePath, fileName);
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

    final Uri outputUri = FileProvider.getUriForFile(this, "com.example.dan", file);
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
    cameraIntent.setFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
    startActivityForResult(cameraIntent, 1);
  }
}
