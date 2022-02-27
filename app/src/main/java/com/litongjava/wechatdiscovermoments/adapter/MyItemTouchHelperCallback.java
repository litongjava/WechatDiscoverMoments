package com.litongjava.wechatdiscovermoments.adapter;

import android.graphics.Canvas;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

  private RecyclerViewPhotosAdapter mAdapter;  //适配器传过来
  private boolean isLongDrag = true;
  private boolean isMoveSwipe = true;

  public MyItemTouchHelperCallback(RecyclerViewPhotosAdapter adapter) {
    this.mAdapter = adapter;
  }

  @Override
  public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
    int dragFlags = 0;  //拖拽的标志
    int swipeFlags = 0; //删除的标志
    int position = viewHolder.getAdapterPosition();
    if (position == mAdapter.getDatas().size() - 1) {  // 如果是最后一个条目 则既不能拖拽也不能删除
      dragFlags = 0;
      swipeFlags = 0;
    } else if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {//网格布局 则支持上下左右拖拽 不支持删除
      dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;  //允许上下左右的拖动
      swipeFlags = 0;  //不能删除
    } else if (layoutManager instanceof LinearLayoutManager) {  //线性布局分为垂直和水平

      LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
      int orientation = manager.getOrientation();
      if (orientation == LinearLayoutManager.HORIZONTAL) {  //横向listview列表

        dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;  //允许左右的拖动
        swipeFlags = ItemTouchHelper.DOWN;  //只允许从上向下侧滑

      } else if (orientation == LinearLayoutManager.VERTICAL) {  //竖向listview列表

        dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;  //允许上下的拖动
        swipeFlags = ItemTouchHelper.LEFT;  //只允许从右向左侧滑删除
      }

    } else {   //最后一个条目既不能拖拽也不能删除 相当于更多
      dragFlags = 0;
      swipeFlags = 0;
    }
    return makeMovementFlags(dragFlags, swipeFlags);
  }

  @Override
  public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    return true;
  }

  @Override
  public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    mAdapter.onItemDel(viewHolder.getAdapterPosition());
  }

  // 该方法返回true时，表示支持长按拖动
  @Override
  public boolean isLongPressDragEnabled() {
    return isLongDrag;
  }

  //该方法返回true时，表示支持滑动删除
  @Override
  public boolean isItemViewSwipeEnabled() {
    return isMoveSwipe;
  }

  //从静止状态变为拖拽或者滑动的时候会回调该方法，参数actionState表示当前的状态。 开始拖拽的时候给item添加一个背景色，然后在拖拽完成的时候还原
  @Override
  public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
    //拖拽的时候才设置背景颜色
//        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && actionState != ItemTouchHelper.ACTION_STATE_SWIPE) {
//            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
//        }
    super.onSelectedChanged(viewHolder, actionState);
  }

  //当用户操作完毕某个item并且其动画也结束后会调用该方法
  @Override
  public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    //重置改变，防止由于复用而导致的显示问题
    super.clearView(recyclerView, viewHolder);
    viewHolder.itemView.setBackgroundColor(0);
  }

  //我们可以在这个方法内实现我们自定义的交互规则或者自定义的动画效果
  @Override
  public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
  }

  public void setLongDrag(boolean longDrag) {
    isLongDrag = longDrag;
  }

  public void setMoveSwipe(boolean moveSwipe) {
    isMoveSwipe = moveSwipe;
  }
}