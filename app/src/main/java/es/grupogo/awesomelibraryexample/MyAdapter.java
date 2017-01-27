package es.grupogo.awesomelibraryexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import es.grupogo.awesomelibrary.StickyHeader.Section;
import es.grupogo.awesomelibrary.StickyHeader.SectionAdapter;
import es.grupogo.awesomelibrary.StickyHeader.StickyHeaderRecyclerView;

/**
 * Created by jorge_cmata on 26/1/17.
 */

public class MyAdapter extends SectionAdapter{

    private static final int ROW_ITEM = 0;
    private static final int ROW_HEADER = 1;

    List<Section> sections;

    public MyAdapter(List<Section> sections) {
        this.sections = sections;
        int i = 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType==ROW_ITEM) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ItemViewHolder(layout);
        } else {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new HeaderViewHolder(layout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder)holder).bind(sections.get(position).getObjects());
        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder)holder).bind(sections.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }


}
