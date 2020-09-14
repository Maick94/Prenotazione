package com.example.michele.lavoro.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Michele on 13/07/2019.
 */

public class ItemMoveChiamataRecycler extends ItemTouchHelper.Callback {

    private final ItemTouchHelperContract Adapter;

    public ItemMoveChiamataRecycler(ItemTouchHelperContract adapter) {
        Adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }



    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        Adapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder,
                                  int actionState) {


        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof RecyclerAdapterOrdinazioni.MyView) {
                RecyclerAdapterOrdinazioni.MyView griglia=
                        (RecyclerAdapterOrdinazioni.MyView) viewHolder;
                Adapter.onRowSelected(griglia);
            }

        }

        super.onSelectedChanged(viewHolder, actionState);
    }
    @Override
    public void clearView(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof RecyclerAdapterOrdinazioni.MyView) {
            RecyclerAdapterOrdinazioni.MyView griglia=
                    (RecyclerAdapterOrdinazioni.MyView) viewHolder;
            Adapter.onRowClear(griglia);
        }
    }

    public interface ItemTouchHelperContract {

        void onRowMoved(int inzio, int fine);
        void onRowSelected(RecyclerAdapterOrdinazioni.MyView griglia);
        void onRowClear(RecyclerAdapterOrdinazioni.MyView griglia);

    }

}

