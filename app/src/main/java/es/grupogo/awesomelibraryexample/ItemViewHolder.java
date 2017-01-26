package es.grupogo.awesomelibraryexample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jorge_cmata on 26/1/17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    TextView itemString;

    public ItemViewHolder(View itemView) {
        super(itemView);

        itemString = (TextView) itemView.findViewById(R.id.textview_item);
    }

    public void bind(Object item) {

        itemString.setText(((String)item));
    }
}
