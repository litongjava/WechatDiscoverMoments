package com.litongjava.wechatdiscovermoments.adapter;

public interface ItemTouchHelperAdapter {

  //长按拖拽
  void onItemMove(int fromPos, int toPos);

  //滑动删除
  void onItemDel(int pos);
}
