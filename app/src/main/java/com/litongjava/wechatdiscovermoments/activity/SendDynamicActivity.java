package com.litongjava.wechatdiscovermoments.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.litongjava.android.activity.BaseActivity;
import com.litongjava.android.view.CommonEditText;
import com.litongjava.android.view.inject.annotation.FindViewById;
import com.litongjava.android.view.inject.annotation.FindViewByIdLayout;
import com.litongjava.android.view.inject.annotation.OnClick;
import com.litongjava.android.view.inject.utils.ViewInjectUtils;
import com.litongjava.wechatdiscovermoments.R;
import com.litongjava.wechatdiscovermoments.adapter.MyItemTouchHelperCallback;
import com.litongjava.wechatdiscovermoments.adapter.RecyclerViewPhotosAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022/2/25
 */
@FindViewByIdLayout(R.layout.activity_send_dynamic)
public class SendDynamicActivity extends BaseActivity {

  private Logger log= LoggerFactory.getLogger(this.getClass());
  //最大选择几张照片
  public static final int maxPhoto = 9;
  /**
   * 三个头部组件
   */
  @FindViewById(R.id.imageViewBack)
  private ImageView imageViewBack;
  @FindViewById(R.id.textViewTitle)
  private TextView textViewTitle;
  @FindViewById(R.id.textViewSend)
  private TextView textViewSend;

  /**
   * 文字框,当前文字数量,总文字数量,图片
   */
  @FindViewById(R.id.commonEditTextContent)
  private CommonEditText commonEditTextContent;
  @FindViewById(R.id.textViewWordCount)
  private TextView textViewWordCount;
  @FindViewById(R.id.textViewWordMax)
  private TextView textViewWordMax;
  @FindViewById(R.id.recyclerViewPhotos)
  private RecyclerView recyclerViewPhotos;

  private RecyclerViewPhotosAdapter mPhotoAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ViewInjectUtils.injectActivity(this, this);
    initView();
    initData();
    initListener();
  }

  private void initView() {
    textViewTitle.setText("发布");
    textViewSend.setText("发送");

    commonEditTextContent.setHint("这一刻的想法......");
    commonEditTextContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(188)});
    textViewWordCount.setText("0");
    textViewWordMax.setText("/188");
  }


  private void initData() {
    //设置布局,最大显示三个,水平显示
    recyclerViewPhotos.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    //设置识别器
    mPhotoAdapter = new RecyclerViewPhotosAdapter(this);
    recyclerViewPhotos.setAdapter(mPhotoAdapter);

    //attachToRecyclerView
    MyItemTouchHelperCallback callBack = new MyItemTouchHelperCallback(mPhotoAdapter);
    ItemTouchHelper touchHelper = new ItemTouchHelper(callBack);
    touchHelper.attachToRecyclerView(recyclerViewPhotos);
  }

  private void initListener() {
    commonEditTextContent.setOnTextChaged(new CommonEditText.OnTextChaged() {
      @Override
      public void setText(String s) {
        //设置词的数量
        textViewWordCount.setText(s.length() + "");
      }
    });
  }

  @OnClick(R.id.imageViewBack)
  public void imageViewBackOnClick(View view) {
    finish();
  }

  @OnClick(R.id.textViewSend)
  public void textViewSendOnClick() {
    Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == PhotoPicker.REQUEST_CODE) {
        //用户新选择了图片
        if (data != null) {
          List<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
          //修改Datas的值
          ArrayList<String> datas = mPhotoAdapter.getDatas();
          datas.addAll(datas.size() - 1, photos);
          log.info("notifyDataSetChanged");
          mPhotoAdapter.notifyDataSetChanged();
        }
      }
      //预览返回
      else if (requestCode == PhotoPicker.REQUEST_CODE) {

      }
    }
  }
}
