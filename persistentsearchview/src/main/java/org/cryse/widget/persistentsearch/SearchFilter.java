package org.cryse.widget.persistentsearch;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class SearchFilter extends Filter {

    private ArrayList<SearchItem> allItems;
    private List<String> itemsTitles;
    private ResultsCallback callback;

    SearchFilter(ArrayList<SearchItem> allItems, ResultsCallback callback) {
        this.allItems = allItems;
        this.itemsTitles = SearchItem.getTitles(allItems);
        this.callback = callback;
    }

    private boolean inTitles(String query) {
        for (String title : itemsTitles)
            if (title.startsWith(query.toLowerCase()))
                return true;
        return false;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        if (constraint != null && !constraint.toString().trim().equals("") && (constraint.toString().startsWith(":") || inTitles(constraint.toString()))) {
            ArrayList<SearchItem> list = new ArrayList<>();
            for (SearchItem item : allItems)
                if (item.getValue().toLowerCase().startsWith(constraint.toString().toLowerCase()) ||
                        item.getTitle().toLowerCase().startsWith(constraint.toString().toLowerCase()))
                    list.add(item);
            FilterResults results = new FilterResults();
            results.values = list;
            results.count = list.size();
            return results;
        } else {
            return new FilterResults();
        }
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if (results != null && results.count > 0) {
            ArrayList<SearchItem> items = (ArrayList<SearchItem>) results.values;
            callback.onResult(items);
        } else {
            callback.onResult(new ArrayList<SearchItem>());
        }
    }

    interface ResultsCallback {
        void onResult(ArrayList<SearchItem> items);
    }
}
