package com.example.listoftasks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private Drawable binIcon;
    private ColorDrawable backgroundColor;
    private Context context;

    public SwipeToDeleteCallback(Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
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
        if (context instanceof SwipeListener){
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

        if (dX > 0) { //swiping right
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin +  binIcon.getIntrinsicWidth();
            binIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            backgroundColor.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
        } else if (dX < 0) { //swiping left
            int iconLeft = itemView.getRight() - iconMargin - binIcon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            binIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            backgroundColor.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { //no swipe
            backgroundColor.setBounds(0, 0, 0, 0);
        }
        backgroundColor.draw(c);
        binIcon.draw(c);
    }
}
