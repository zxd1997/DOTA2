package com.example.zxd1997.dota2.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

public class OKhttp {
    public static void post(String url) {
//        Log.d("post", "post: " + url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(Util.EMPTY_REQUEST)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                Log.d("post", "onResponse: " + Objects.requireNonNull(response.body()).string());
            }
        });
    }

    public static void getFromService(String url, final Handler handler, final int what) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Message message = new Message();
                message.obj = Objects.requireNonNull(response.body()).string();
                message.what = what;
                handler.sendMessage(message);
            }
        });
    }

    public static void getZip(final String url, final Handler handler) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                FileOutputStream out = MyApplication.getContext().openFileOutput("master.zip", Context.MODE_PRIVATE);
                out.write(Objects.requireNonNull(response.body()).bytes());
                out.close();
                ZipFile zip = new ZipFile(MyApplication.getContext().getFileStreamPath("master.zip"));
                Enumeration list = zip.entries();
                while (list.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) list.nextElement();
//                    Log.d("name", "onResponse: " + entry.getName());
                    boolean f = false;
                    String name = entry.getName();
                    String filename = "";
                    if (name.contains("ability_ids.json")) {
                        f = true;
                        filename = "ability_ids.json";
                    } else if (name.contains("hero_abilities.json")) {
                        f = true;
                        filename = "hero_abilities.json";
                    } else if (name.contains("abilities.json")) {
                        if (!name.contains("neutral_abilities")) {
                            f = true;
                            filename = "abilities.json";
                        }
                    } else if (name.contains("hero_names.json")) {
                        f = true;
                        filename = "hero_names.json";
                    } else if (name.contains("items.json")) {
                        f = true;
                        filename = "items.json";
                    }
                    if (f) {
                        FileOutputStream fileOutputStream = MyApplication.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                        InputStream inputStream = zip.getInputStream(entry);
                        byte[] buf = new byte[16384];
                        int len;
                        while ((len = inputStream.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, len);
                        }
                        inputStream.close();
                        fileOutputStream.close();
                    }
                }
                zip.close();
                Message message = new Message();
                message.what = 8;
                handler.sendMessage(message);
            }
        });
    }
}
