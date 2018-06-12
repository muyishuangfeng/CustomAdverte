package com.yk.adverte.view.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;


/**
 * Created by Silence on 2018/1/5.
 */
@GlideModule
public class MyGlideModule extends AppGlideModule {

    public static final int DISK_CACHE_SIZE = 500 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context,DISK_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {

    }
}
