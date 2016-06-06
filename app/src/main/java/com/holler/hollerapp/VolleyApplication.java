package com.holler.hollerapp;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Base64;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Utilities.LruBitmapCache;

public class VolleyApplication extends Application {

  private static VolleyApplication sInstance;

  public static final String TAG = VolleyApplication.class.getSimpleName();

  private ImageLoader mImageLoader;

  private RequestQueue mRequestQueue;

  @Override
  public void onCreate() {
    super.onCreate();

    mRequestQueue = Volley.newRequestQueue(this);

    mImageLoader = new ImageLoader(mRequestQueue,
            new ImageLoader.ImageCache() {
              private final LruCache<String, Bitmap>
                      cache = new LruCache<String, Bitmap>(20);

              @Override
              public Bitmap getBitmap(String url) {
                return cache.get(url);
              }

              @Override
              public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
              }
            });

    printHashKey();

    sInstance = this;
  }

  public synchronized static VolleyApplication getInstance() {
    return sInstance;
  }

  public static synchronized Context getContext() {
    return getInstance().getBaseContext() ;
  }

  public RequestQueue getRequestQueue() {
    if (mRequestQueue == null) {
      Cache cache = new DiskBasedCache(this.getCacheDir(), 10 * 1024 * 1024);
      Network network = new BasicNetwork(new HurlStack());
      mRequestQueue = new RequestQueue(cache, network);
      mRequestQueue.start();
    }
    return mRequestQueue;
  }

  public ImageLoader getImageLoader() {
    return mImageLoader;
  }

  public <T> void addToRequestQueue(Request req, String tag) {
    // set the default tag if tag is empty
    getRequestQueue().add(req);
  }

  public <T> void addToRequestQueue(Request<T> req) {
    req.setTag(TAG);
    getRequestQueue().add(req);
  }

  public void cancelPendingRequests(Object tag) {
    if (mRequestQueue != null) {
      mRequestQueue.cancelAll(tag);
    }
  }


  public void printHashKey(){
    // Add code to print out the key hash
    try {
      PackageInfo info = getPackageManager().getPackageInfo(
              "com.holler.hollerapp",
              PackageManager.GET_SIGNATURES);
      for (Signature signature : info.signatures) {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(signature.toByteArray());
        Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
      }
    } catch (PackageManager.NameNotFoundException e) {

    } catch (NoSuchAlgorithmException e) {

    }
  }

}