package com.sfan.imagedemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * Created by zzy on 2017/11/6.
 * 1、Drawable就是一个可画的对象，其可能是一张位图（BitmapDrawable），也可能是一个图形（ShapeDrawable），还有可能是一个图层（LayerDrawable），我们根据画图的需求，创建相应的可画对象
 * 2、Canvas画布，绘图的目的区域，用于绘图
 * 3、Bitmap位图，用于图的处理
 * 4、Matrix矩阵
 */

public class BitmapDrawableUtils {

    /* Bitmap */

    /**
     * 1、从资源中获取Bitmap
     */
    public static Bitmap getBitmap(Context context, int id) {
        Resources res = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, id);
        return bitmap;
    }

    /**
     * 2、Bitmap → byte[]
     */
    public static byte[] Bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        return output.toByteArray();
    }

    /**
     * 3、byte[] → Bitmap
     */
    public static Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * 4、Bitmap缩放
     */
    public static Bitmap zoomBitmap(Bitmap bmpOld, int width, int height) {
        int w = bmpOld.getWidth();
        int h = bmpOld.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bmpNew = Bitmap.createBitmap(bmpOld, 0, 0, w, h, matrix, true);
        return bmpNew;
    }

    /**
     * 5、将Drawable转化为Bitmap
     */
    public static Bitmap Drawable2Bitmap(Drawable drawable) {
        // 取drawable的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        // 建立对应bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应bitmap的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把drawable内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 6、获得圆角图片
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 7、获得带倒影的图片
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, h * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        //倒影
        final int reflectionGap = 10;//倒影间距
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, false);
        // 添加间隔线
        Paint defaultPaint = new Paint();
        defaultPaint.setColor(0x70ffffff);//颜色
        canvas.drawRect(0, h, w, h + reflectionGap, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);
        // 添加倒影朦板
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight() + reflectionGap, paint);
        return bitmapWithReflection;
    }

    /* Drawable */

    /**
     * 1、Bitmap转换成Drawable
     */
    public static Drawable Bitmap2Drawable(Context context, Bitmap bitmap) {
        BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        //因为BtimapDrawable是Drawable的子类，最终直接使用drawable对象即可。
        return drawable;
    }

    /**
     * 2、Drawable缩放
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable转换成bitmap
        Bitmap bmpOld = Drawable2Bitmap(drawable);
        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.postScale(sx, sy);
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        Bitmap bmpNew = Bitmap.createBitmap(bmpOld, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(bmpNew);
    }
}
