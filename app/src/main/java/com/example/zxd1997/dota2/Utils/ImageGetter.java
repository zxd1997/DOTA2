package com.example.zxd1997.dota2.Utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


//实用Fresco的DataSource来获取Html中的Bitmap
public final class ImageGetter implements Html.ImageGetter {
    private final TextView textView;

    public ImageGetter(TextView textView) {
        this.textView = textView;
    }

    //Fresco获取Bitmap
    private static void loadToBitmap(String imageUrl, BaseBitmapDataSubscriber mDataSubscriber) {
        //ImageRequest指定获取的图片
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(imageUrl))
                .setProgressiveRenderingEnabled(true)
                .build();
        //获取ImagePipeline
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        //DataSource获取ImagePipeline中的Bitmap
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, MyApplication.getContext());
        //添加DataSource订阅者，并且再UI线程中执行回调 UiThreadImmediateExecutorService
//        dataSource.subscribe(mDataSubscriber, UiThreadImmediateExecutorService.getInstance());
        dataSource.subscribe(mDataSubscriber, CallerThreadExecutor.getInstance());

    }

    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        loadToBitmap(source, new BaseBitmapDataSubscriber() {
            //DataSource订阅者回调取出bitmap
            @Override
            protected void onNewResultImpl(@Nullable Bitmap bitmap) {
                if (bitmap != null) {
//                    Log.d("fresco", "onNewResultImpl: loaded");
                    BitmapDrawable d1 = new BitmapDrawable(bitmap);
                    d.addLevel(1, 1, d1);
                    d.setBounds(0, 0, bitmap.getWidth() * 2, bitmap.getHeight() * 2);//设置大小（初始大小过小）
                    d.setLevel(1);
                    CharSequence t = textView.getText();
                    textView.setText(t);//更新TextView，避免图片显示不全等问题
                }
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

            }
        });
        return d;
    }
}
