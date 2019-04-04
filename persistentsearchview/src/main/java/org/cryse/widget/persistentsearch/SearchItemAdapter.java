package org.cryse.widget.persistentsearch;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchItemAdapter extends ArrayAdapter<SearchItem> {

    private ArrayList<SearchItem> items;
    private ArrayList<SearchItem> options;

    public SearchItemAdapter(Context context, ArrayList<SearchItem> options) {
        super(context, 0, options);
        this.items = options;
        this.options = new ArrayList<>(options);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchItem searchItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.layout_searchitem, parent, false);
        }

        View border = convertView.findViewById(R.id.view_border);
        if (position == 0) {
            border.setVisibility(View.VISIBLE);
        } else {
            border.setVisibility(View.GONE);
        }
        final TextView title = convertView.findViewById(R.id.textview_title);
        title.setText(searchItem.getTitle());
        ImageView icon = convertView.findViewById(R.id.imageview_icon);
        if(searchItem.getIcon() == null) {
            switch (searchItem.getType()) {
                case SearchItem.TYPE_SEARCH_ITEM_HISTORY:
                    icon.setImageResource(R.drawable.ic_history_black);
                    break;
                default:
                case SearchItem.TYPE_SEARCH_ITEM_SUGGESTION:
                    icon.setImageResource(R.drawable.ic_search_black);
                    break;
            }
        } else {
            icon.setImageDrawable(searchItem.getIcon());
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public SearchItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new SearchFilter(options, new SearchFilter.ResultsCallback() {
            @Override
            public void onResult(ArrayList<SearchItem> items) {
                for (SearchItem item :
                        items) {
                    Log.e("Suggestion",item.getTitle());
                }
                clear();
                addAll(items);
                notifyDataSetChanged();
            }
        });
    }
}