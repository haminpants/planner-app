package com.info3245.plannerapp;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacerItemDecoration extends RecyclerView.ItemDecoration {
    private final int spaceDp;

    public VerticalSpacerItemDecoration (int spaceDp) {
        this.spaceDp = spaceDp;
    }

    @Override
    public void getItemOffsets (@NonNull Rect outRect, @NonNull View view,
        @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.bottom = spaceDp;
    }
}