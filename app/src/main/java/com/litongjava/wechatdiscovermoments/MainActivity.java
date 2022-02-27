package com.litongjava.wechatdiscovermoments;

import android.os.Bundle;
import android.view.View;

import com.litongjava.android.view.inject.annotation.FindViewByIdLayout;
import com.litongjava.android.view.inject.annotation.OnClick;
import com.litongjava.android.view.inject.utils.ViewInjectUtils;
import com.litongjava.wechatdiscovermoments.activity.SendDynamicActivity;
import com.litongjava.android.activity.BaseActivity;

@FindViewByIdLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ViewInjectUtils.injectActivity(this,this);
  }

  @OnClick(R.id.btnStartSendDynamicActivity)
  public void btnStartSendDynamicActivityOnClick(View view) {
    toActivity(SendDynamicActivity.class);
  }
}