package com.litongjava.wechatdiscovermoments.app;

import android.app.Application;

import com.hss01248.glidepicker.GlideIniter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.iwf.photopicker.PhotoPickUtils;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022/2/26
 */
public class WechatApplication extends Application {

  private Logger log= LoggerFactory.getLogger(this.getClass());

  public WechatApplication(){

  }

  @Override
  public void onCreate() {
    //初始化PhotoPicker
    PhotoPickUtils.init(getApplicationContext(), new GlideIniter());
    super.onCreate();
  }
}
