package com.example.listoftasks.recyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listoftasks.R;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private Drawable binIcon;
    private ColorDrawable backgroundColor;
    private Context context;

    public SwipeToDeleteCallback(Context context) {
        super(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.context = context;
        binIcon = context.getDrawable(R.drawable.ic_delete);
        backgroundColor = new ColorDrawable(context.getResources().getColor(R.color.darkRed, null));
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (context instanceof SwipeListener) {
            ((SwipeListener) context).itemSwiped(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;
        int iconMargin = (itemView.getHeight() - binIcon.getIntrinsicHeight()) / 4;
        int iconTop = itemView.getTop() + (itemView.getHeight() - binIcon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + binIcon.getIntrinsicHeight();
        int noDrag = 0;
        int noBounds = 0;

        if (dX > noDrag) {
            swipingRight((int) dX, itemView, backgroundCornerOffset, iconMargin, iconTop, iconBottom);
        } else if (dX < noDrag) {
            swipingLeft((int) dX, itemView, backgroundCornerOffset, iconMargin, iconTop, iconBottom);
        } else {
            backgroundColor.setBounds(noBounds, noBounds, noBounds, noBounds);
        }
        backgroundColor.draw(c);
        binIcon.draw(c);
    }

    private void swipingLeft(int dX, View itemView, int backgroundCornerOffset, int iconMargin, int iconTop, int iconBottom) {
        int iconLeft = itemView.getRight() - iconMargin - binIcon.getIntrinsicWidth();
        int iconRight = itemView.getRight() - iconMargin;
        binIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        backgroundColor.setBounds(itemView.getRight() + dX - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());
    }

    private void swipingRight(int dX, View itemView, int backgroundCornerOffset, int iconMargin, int iconTop, int iconBottom) {
        int iconLeft = itemView.getLeft() + iconMargin;
        int iconRight = itemView.getLeft() + iconMargin + binIcon.getIntrinsicWidth();
        binIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        backgroundColor.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + dX + backgroundCornerOffset, itemView.getBottom());
    }
}
