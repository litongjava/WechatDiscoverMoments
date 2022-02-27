package com.litongjava.android.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * dp与px互相转化
 */
public class DensityUtils {

  /**
   * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
   */
  public static int dp2px(Context context, float dpValue) {
    if (context != null) {
      final float scale = context.getResources().getDisplayMetrics().density;
      return (int) (dpValue * scale + 0.5f);
    }
    return 0;
  }

  /**
   * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
   */
  public static int dipTopx(Context context, float dpValue) {

    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * 2 + 0.5f);
  }


  /**
   * 将px值转换为sp值，保证文字大小不变
   */
  public static int px2sp(Context context, float pxValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (pxValue / fontScale + 0.5f);
  }

  /**
   * 将sp值转换为px值，保证文字大小不变
   *
   * @return
   */
  public static int sp2px(Context context, float spValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (spValue * fontScale + 0.5f);
  }

  /**
   * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
   */
  public static int px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }


  public static int getScreenWidth(Context context) {
    WindowManager windowManager = (WindowManager) context
      .getSystemService(Context.WINDOW_SERVICE);
    int screenWidth = windowManager.getDefaultDisplay().getWidth();
    return screenWidth;
  }

  /**
   * 获取屏幕高度
   *
   * @param context
   * @return
   */
  public static int screenHeight = -1;

  public static int getScreenHeight(Context context) {
    if (screenHeight == -1) {
      WindowManager windowManager = (WindowManager) context
        .getSystemService(Context.WINDOW_SERVICE);
      screenHeight = windowManager.getDefaultDisplay().getHeight();
    }
    return screenHeight;
  }

}  