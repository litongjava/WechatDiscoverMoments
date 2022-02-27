package com.litongjava.wechatdiscovermoments.listener;

import android.Manifest;
import android.app.Activity;
import android.view.View;

import com.litongjava.wechatdiscovermoments.R;
import com.litongjava.wechatdiscovermoments.adapter.RecyclerViewPhotosAdapter;
import com.litongjava.wechatdiscovermoments.adapter.RecyclerViewPhotosViewHolder;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022/2/26
 */
public class RecyclerViewPhotosOnClickListener implements View.OnClickListener {
  private Logger log = LoggerFactory.getLogger(this.getClass());

  private ArrayList<String> imagesUri = new ArrayList<>();
  private Activity activity;
  private RecyclerViewPhotosAdapter adapter;
  private RecyclerViewPhotosViewHolder viewHolder;


  public RecyclerViewPhotosOnClickListener(Activity activity, RecyclerViewPhotosViewHolder viewHolder, RecyclerViewPhotosAdapter adapter) {
    this.adapter = adapter;
    this.viewHolder = viewHolder;
    this.activity = activity;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_add:
        addImage();
        break;
      case R.id.iv_del:
        delImage();
        break;
      case R.id.iv_img:
        previewImgage();
        break;
    }

  }

  /**
   * 从图片选择器中选择图片
   */
  private void addImage() {
    //builder
    AcpOptions.Builder builder = new AcpOptions.Builder();
    //acpOptions
    AcpOptions acpOptions = builder.setPermissions(
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
      Manifest.permission.CAMERA,
      Manifest.permission.READ_EXTERNAL_STORAGE
    ).build();

    Acp acp = Acp.getInstance(activity);
    AcpListener acpListener = new AcpListener() {
      @Override
      public void onGranted() {
        log.info("权限通过");
        PhotoPicker.builder()
          //可以选择的最大图片数量
          .setPhotoCount(9)
          //是否显示相机
          .setShowCamera(true)
          //保存选择好的照片，下次选择照片在此集合的基础上添加
          .setSelected(imagesUri)
          //启动的Activity
          .start(activity);
        //上面的代码执行完成后不必等待activity响应,下面的代码会立即执行,此时imagesUri的值为null
        //log.info("activity 执行完成");
      }

      @Override
      public void onDenied(List<String> permissions) {
        log.info("权限拒绝:{}", permissions.toString());
      }
    };

    //请求权限,执行操作
    acp.request(acpOptions, acpListener);
  }

  /**
   * 查看图片
   */
  private void previewImgage() {
    imagesUri.clear();
    ArrayList<String> images = adapter.getDatas();
    for (int i = 0; i < images.size() - 1; i++) {
      imagesUri.add(images.get(i).toString());
    }

    int position = viewHolder.getAdapterPosition();

    PhotoPreview.builder()
      .setPhotos(imagesUri)
      .setCurrentItem(position)
      .setShowDeleteButton(true)  //是否显示删除按钮
      .start(activity);

  }

  private void delImage() {
    int pos = viewHolder.getAdapterPosition();
    adapter.getDatas().remove(pos);
    adapter.notifyItemRemoved(pos);
  }
}
