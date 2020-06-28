package com.downtail.wanandroid.widget;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class DragItemTouchHelper extends ItemTouchHelper.Callback {

    private DragListener dragListener;

    public DragItemTouchHelper(DragListener dragListener) {
        this.dragListener = dragListener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        if (dragListener != null) {
            dragListener.onItemDrag(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    public interface DragListener {
        void onItemDrag(int startIndex, int endIndex);
    }
}
