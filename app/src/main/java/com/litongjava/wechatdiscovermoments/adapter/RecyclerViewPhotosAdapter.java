package com.litongjava.wechatdiscovermoments.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.litongjava.android.utils.DensityUtils;
import com.litongjava.android.utils.ScreenUtils;
import com.litongjava.wechatdiscovermoments.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;


public class RecyclerViewPhotosAdapter extends RecyclerView.Adapter<RecyclerViewPhotosViewHolder> implements ItemTouchHelperAdapter {

  private Logger log= LoggerFactory.getLogger(this.getClass());
  private Activity mActivity;
  private LayoutInflater mLayoutInflater;

  //已选择图片+1,是因为包含后面的选择图片框
  private ArrayList<String> datas=new ArrayList<>();

  public RecyclerViewPhotosAdapter(Activity activity) {
    this.mActivity = activity;
    mLayoutInflater = LayoutInflater.from(activity);
    datas.add("1");
  }

  public ArrayList<String> getDatas() {
    return datas;
  }

  public void setDatas(ArrayList<String> datas) {
    this.datas = datas;
  }

  @Override
  public RecyclerViewPhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = mLayoutInflater.inflate(R.layout.common_photos, parent, false);
    return new RecyclerViewPhotosViewHolder(mActivity, view, this);
  }

  @Override
  public void onBindViewHolder(RecyclerViewPhotosViewHolder viewHolder, int position) {
    String image = datas.get(position);
    //获取LayoutParams
    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewHolder.frameLayoutImage.getLayoutParams();
    int w = params.width = (int) ((ScreenUtils.getScreenWidth(mActivity) - DensityUtils.dp2px(mActivity, 38)) / 3.0);
    params.height = w;
    params.bottomMargin = DensityUtils.dp2px(mActivity, 7);
    params.rightMargin = DensityUtils.dp2px(mActivity, 7);
    viewHolder.frameLayoutImage.setLayoutParams(params);
    //if (image instanceof String) {
    if("1".equals(image)){
      log.info("显示添加照片的ImageView");
      viewHolder.ivDel.setVisibility(View.INVISIBLE);
      viewHolder.ivAdd.setVisibility(View.VISIBLE);
      viewHolder.ivImg.setVisibility(View.GONE);
      viewHolder.ivAdd.setImageResource(R.drawable.image_add);
    } //显示加载图片框
    else {
      log.info("加载图片到ImageView");
      viewHolder.ivDel.setVisibility(View.VISIBLE);
      viewHolder.ivAdd.setVisibility(View.GONE);
      viewHolder.ivImg.setVisibility(View.VISIBLE);
      //这里最好使用glide或者Picasso加载本地图片 bitmap容易造成内存溢出
      Bitmap bitmap = BitmapFactory.decodeFile(image.toString());
      viewHolder.ivImg.setImageBitmap(bitmap);
    }
  }

  @Override
  public int getItemCount() {
    if (datas == null) {
      return 0;
    }
    return datas.size();
  }


  //拖拽排序相关
  @Override
  public void onItemMove(int fromPos, int toPos) {
    if (fromPos == datas.size() - 1 || toPos == datas.size() - 1)
      return;
    if (fromPos < toPos)
      //向下拖动
      for (int i = fromPos; i < toPos; i++) {
        Collections.swap(datas, i, i + 1);
      }
    else {
      //向上拖动
      for (int i = fromPos; i > toPos; i--) {
        Collections.swap(datas, i, i - 1);
      }
    }
    notifyItemMoved(fromPos, toPos);
  }

  //滑动删除相关
  @Override
  public void onItemDel(int pos) {
    if (pos == datas.size() - 1)
      return;
    datas.remove(pos);
    notifyItemRemoved(pos);
  }
}