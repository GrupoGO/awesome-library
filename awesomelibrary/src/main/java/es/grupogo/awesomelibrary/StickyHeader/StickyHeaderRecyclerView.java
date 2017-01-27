package es.grupogo.awesomelibrary.StickyHeader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jorge_cmata on 26/1/17.
 */

public class StickyHeaderRecyclerView extends RecyclerView {

    public StickyHeaderRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyHeaderRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);


    }
}