package com.e.smartdoks;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e.smartdoks.models.FileUploadStatus;
import com.e.smartdoks.utils.FilePath;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;

public class SendFragment extends Fragment {

  Button shareFiles;
  TextView txt_showPath;
  ProgressBar loadingimage;



  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_send, container, false);
    SharedPreferences.Editor send = this.getActivity().getSharedPreferences("Send",  MODE_PRIVATE).edit();
    send.apply();
    txt_showPath = v.findViewById(R.id.txt_path);
    shareFiles = v.findViewById(R.id.sharekolang);
    loadingimage= v.findViewById(R.id.loading);

    ActivityCompat.requestPermissions(getActivity(),
            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
            1);
    shareFiles.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d("Main", "Button Clicked");
        String[] mimeTypes = {"application/msword", "application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};
        Intent sendIntent = new Intent(Intent.ACTION_GET_CONTENT);
        sendIntent.setType("*/*");
        sendIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        sendIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(sendIntent, 10);
      }
    });
    return v;
  }
  @Override
  public void onActivityResult(int requestCode, int resultCode, final Intent data) {
    try {

      Uri uri = data.getData();
//            Toast toast =Toast.makeText(getContext(), FileUtils.getUriRealPathAboveKitkat(this.getContext(), uri), Toast.LENGTH_SHORT);
//            toast.show();

      String realPath =  FilePath.getPath(getContext(), uri);
//            String realPath = getContext().getFilesDir().getAbsolutePath();
//      String serverURL = "http://192.168.43.214/file/upload"; // RPI LAN
//      String serverURL = "http://192.168.1.6/user/upload"; // RPI LAN
      String serverURL = "http://192.168.43.234/user/upload"; // RPI LAN
//            String serverURL = "http://192.168.254.105/user/upload"; // RPI LAN
//            String serverURL = "http://192.168.254.110/file/upload"; // RPI Mark WIFI
//            String serverURL = "http://192.168.1.7/user/upload"; // Paul's House
//            String serverURL = "http://192.168.254.103/file/upload"; // Mark's House
//            String serverURL = "http://192.168.254.112/user/upload"; // Mark's House (Me)
//            String serverURL = "http://192.168.100.23/file/upload"; // Cha's House
//            String serverURL = "http://192.168.0.100/user/upload"; //   Router
//            String serverURL = "http://192.168.43.125/user/upload"; //   Presentation
      loadingimage.setVisibility(View.VISIBLE);
      Ion.with(getContext())
              .load(serverURL)
              .uploadProgress(new ProgressCallback() {

                @Override
                public void onProgress(long downloaded, long total) {

                  Log.d("PROGRESS", String.valueOf(downloaded) + " of " + String.valueOf(total));
//                            Toast.makeText(getActivity(), "UPLOADING!",Toast.LENGTH_LONG).show();

                }
              })

              .setMultipartFile("file", new File(realPath))
              .as(new TypeToken<FileUploadStatus>(){})
              .setCallback(new FutureCallback<FileUploadStatus>() {
                @Override
                public void onCompleted(Exception e, FileUploadStatus status) {
                  if(status != null){
                    if(status.getStatus().equals("FILE_UPLOADED")){
                      loadingimage.setVisibility(View.INVISIBLE);
                      String input = "File Uploaded";
//                                    Intent serviceIntent = new Intent(getActivity(),ExampleService.class);
//                                    Intent inputExtra = serviceIntent.putExtra("InputExtra", input);
//                                    startService(serviceIntent);

                      Toast.makeText(getActivity(), "File successfully transferred!", Toast.LENGTH_LONG).show();

                    }
                  }

                  if(e != null){
                    e.printStackTrace();
                  }

                }
              });
    }catch(NullPointerException e){
      Toast.makeText(this.getActivity(), "No File Selected", Toast.LENGTH_LONG).show();
      e.printStackTrace();
    }
  }

  private void startService(Intent serviceIntent) {

  }
}


