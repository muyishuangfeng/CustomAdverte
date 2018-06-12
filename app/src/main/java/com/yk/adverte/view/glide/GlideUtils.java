package com.yk.adverte.view.glide;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.yk.adverte.R;
import com.yk.adverte.common.APP;

import java.io.File;


/**
 * Created by Silence on 2018/1/5.
 */

public class GlideUtils {

    /**
     * 加载网络图片
     * <p>
     * DiskCacheStrategy.NONE： 表示不缓存任何内容。
     * DiskCacheStrategy.DATA： 表示只缓存原始图片。
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
     *
     * @param context           上下文
     * @param url               url
     * @param resID             占位图
     * @param errorID           错误图片
     * @param diskCacheStrategy 缓存
     * @param scaleType         截取类型
     * @param imageView         图片
     */
    public static void loadUrlToImage(Context context, String url, int resID, int errorID,
                                      DiskCacheStrategy diskCacheStrategy, ImageView.ScaleType
                                              scaleType, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy);
        Glide.with(context)
                .load(new MyGlideUrl(url))
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载原始尺寸的网络图片
     *
     * @param context           上下文
     * @param url               url
     * @param resID             占位图
     * @param errorID           错误图片
     * @param diskCacheStrategy 缓存
     * @param imageView         图片
     */
    public static void loadOriginalToImage(Context context, String url, int resID, int errorID,
                                           DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .override(Target.SIZE_ORIGINAL)
                .diskCacheStrategy(diskCacheStrategy);
        Glide.with(context)
                .load(new MyGlideUrl(url))
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载网络图片
     *
     * @param context   上下文
     * @param url       网络图片地址
     * @param imageView imageview
     * @param options   加载参数设置
     */
    public static void loadImage(Context context, String url, ImageView imageView,
                                 RequestOptions options) {
        Glide.with(context)
                .load(new MyGlideUrl(url))
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载Gif图片
     *
     * @param context
     * @param url
     * @param resID
     * @param errorID
     * @param diskCacheStrategy
     * @param imageView
     */
    public static void loadGifFromUrl(Context context, String url, int resID, int errorID,
                                      DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy);
        Glide.with(context)
                .asGif()
                .load(new MyGlideUrl(url))
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载静态图片
     *
     * @param context
     * @param url
     * @param resID
     * @param errorID
     * @param diskCacheStrategy
     * @param imageView
     */
    public static void loadBitmapFromUrl(Context context, String url, int resID, int errorID,
                                         DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy);
        Glide.with(context)
                .asBitmap()
                .load(new MyGlideUrl(url))
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载缩略图
     *
     * @param context           上下文
     * @param url               地址
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param thumbnailSize     缩略图大小
     * @param imageView         资源文件
     */
    public static void loadThumbnails(Context context, String url, int resID, int errorID,
                                      DiskCacheStrategy diskCacheStrategy, float thumbnailSize,
                                      ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy);
        Glide.with(context)
                .load(new MyGlideUrl(url))
                .thumbnail(thumbnailSize)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param context           上下文
     * @param file              文件
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param imageView         资源文件
     */
    public static void loadLocalFile(Context context, File file, int resID, int errorID,
                                     DiskCacheStrategy diskCacheStrategy,
                                     ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy);
        Glide.with(context)
                .load(file)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载应用资源
     *
     * @param context           上下文
     * @param resource          资源文件图片
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param imageView         资源文件
     */
    public static void loadResource(Context context, int resource, int resID, int errorID,
                                    DiskCacheStrategy diskCacheStrategy, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy);
        Glide.with(context)
                .load(resource)
                .apply(options)
                .into(imageView);


    }

    /**
     * 加载流资源
     *
     * @param context           上下文
     * @param image             二进制流
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param thumbnailSize     缩略图大小
     * @param imageView         资源文件
     */
    public static void loadStream(Context context, byte[] image, int resID, int errorID,
                                  DiskCacheStrategy diskCacheStrategy, float thumbnailSize,
                                  ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy);
        Glide.with(context)
                .load(image)
                .thumbnail(thumbnailSize)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载Uri对象
     *
     * @param context           上下文
     * @param imageUri          uri
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param imageView         资源文件
     */
    public static void loadUri(Context context, Uri imageUri, int resID, int errorID,
                               DiskCacheStrategy diskCacheStrategy,
                               ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy);
        Glide.with(context)
                .load(imageUri)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载本地路径图片
     *
     * @param context           上下文
     * @param path              路径
     * @param errorView         占位符
     * @param diskCacheStrategy 缓存
     * @param imageView         目标对象
     */
    public static void loadPath(Context context, String path, int errorView, DiskCacheStrategy
            diskCacheStrategy, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(errorView)
                .diskCacheStrategy(diskCacheStrategy);
        Glide.with(context)
                .load(path)
                .apply(options)
                .into(imageView);
    }

    /**
     * 图片转换
     *
     * @param context           上下文
     * @param url               地址
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param transformation    转换
     * @param imageView         资源ID
     */
    public static void loadUrlWithTranslate(Context context, String url, int resID, int errorID,
                                            DiskCacheStrategy diskCacheStrategy,
                                            BitmapTransformation transformation, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .transform(transformation)
                .diskCacheStrategy(diskCacheStrategy);
        Glide.with(context)
                .load(new MyGlideUrl(url))
                .apply(options)
                .into(imageView);
    }

    /**
     * 下载图片并显示
     *
     * @param url
     * @param resID
     * @param errorID
     * @param context
     * @param view
     */
    public static void downloadImage(final String url, final int resID, final int errorID,
                                     final Activity context, final ImageView view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FutureTarget<File> target = Glide.with(APP.getAppContext())
                            .asFile()
                            .load(url)
                            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    final File imageFile = target.get();
                    RequestOptions options = new RequestOptions()
                            .placeholder(resID)
                            .error(errorID)
                            .diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(context)
                            .load(imageFile)
                            .apply(options)
                            .into(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
