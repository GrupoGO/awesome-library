package es.grupogo.awesomelibraryexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jorge_cmata on 26/1/17.
 */

public class MyAdapter extends RecyclerView.Adapter{

    List<?> items;

    public MyAdapter(List<?> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
