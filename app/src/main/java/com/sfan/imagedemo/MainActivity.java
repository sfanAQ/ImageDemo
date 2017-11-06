package com.sfan.imagedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imageView)
    ImageView imageView;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = MainActivity.this;
    }

    @OnClick({R.id.getBitmap, R.id.Bitmap2Bytes, R.id.Bytes2Bitmap, R.id.zoomBitmap, R.id.Drawable2Bitmap, R.id.getRoundedCornerBitmap, R.id.createReflectionImageWithOrigin, R.id.Bitmap2Drawable, R.id.zoomDrawable})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getBitmap:
                Bitmap bitmap1 = BitmapDrawableUtils.getBitmap(mContext, R.mipmap.ic_launcher_round);
                imageView.setImageBitmap(bitmap1);
                break;
            case R.id.Bitmap2Bytes:
                Bitmap bitmap2 = BitmapDrawableUtils.getBitmap(mContext, R.mipmap.ic_launcher_round);
                byte[] bytes2 = BitmapDrawableUtils.Bitmap2Bytes(bitmap2);
                textView.setText(bytes2.toString());
                break;
            case R.id.Bytes2Bitmap:
                Bitmap bitmap3 = BitmapDrawableUtils.getBitmap(mContext, R.mipmap.ic_launcher_round);
                byte[] bytes3 = BitmapDrawableUtils.Bitmap2Bytes(bitmap3);
                Bitmap bitmap4 = BitmapDrawableUtils.Bytes2Bitmap(bytes3);
                imageView.setImageBitmap(bitmap4);
                break;
            case R.id.zoomBitmap:
                Bitmap bitmap5 = BitmapDrawableUtils.getBitmap(mContext, R.mipmap.ic_launcher_round);
                Bitmap bitmap6 = BitmapDrawableUtils.zoomBitmap(bitmap5, 100, 100);
                imageView.setImageBitmap(bitmap6);
                break;
            case R.id.Drawable2Bitmap:
                Drawable drawable1 = ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher_round);
                Bitmap bitmap7 = BitmapDrawableUtils.Drawable2Bitmap(drawable1);
                imageView.setImageBitmap(bitmap7);
                break;
            case R.id.getRoundedCornerBitmap:
                Bitmap bitmap8 = BitmapDrawableUtils.getBitmap(mContext, R.mipmap.ic_launcher_round);
                Bitmap bitmap9 = BitmapDrawableUtils.getRoundedCornerBitmap(bitmap8, 20);
                imageView.setImageBitmap(bitmap9);
                break;
            case R.id.createReflectionImageWithOrigin:
                Bitmap bitmap10 = BitmapDrawableUtils.getBitmap(mContext, R.mipmap.ic_launcher_round);
                Bitmap bitmap11 = BitmapDrawableUtils.createReflectionImageWithOrigin(bitmap10);
                imageView.setImageBitmap(bitmap11);
                break;
            case R.id.Bitmap2Drawable:
                Bitmap bitmap12 = BitmapDrawableUtils.getBitmap(mContext, R.mipmap.ic_launcher_round);
                Drawable drawable2 = BitmapDrawableUtils.Bitmap2Drawable(mContext, bitmap12);
                imageView.setImageDrawable(drawable2);
                break;
            case R.id.zoomDrawable:
                Drawable drawable3 = ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher_round);
                Drawable drawable4 = BitmapDrawableUtils.zoomDrawable(drawable3, 100, 100);
                imageView.setImageDrawable(drawable4);
                break;
        }
    }

}
