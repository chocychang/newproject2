package com.example.newproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends BaseAdapter implements Filterable {
    List<String> item;
    List<String> originalitem;
    private LayoutInflater mLayout;
    public CustomerAdapter(Context context, List<String> mList) {
        mLayout = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.item = mList;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // R.layout.custom_layout 是你自己創的自訂layout
        View v = mLayout.inflate(R.layout.custom_layout,parent,false);
        TextView title = (TextView)v.findViewById(R.id.textView);
        title.setText(item.get(position));
        return v;
    }

    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString();
                FilterResults result = new FilterResults();
                if(originalitem == null){
                    synchronized (this){
                        originalitem = new ArrayList<String>(item);
                        // 若originalitem 沒有資料，會複製一份item的過來.
                    }
                }
                if(constraint != null && constraint.toString().length()>0){
                    ArrayList<String> filteredItem = new ArrayList<String>();
                    for(int i=0;i<originalitem.size();i++){
                        String title = originalitem.get(i).toString();
                        if(title.contains(constraint)){
                            filteredItem.add(title);
                        }
                    }
                    result.count = filteredItem.size();
                    result.values = filteredItem;
                }else{
                    synchronized (this){
                        ArrayList<String> list = new ArrayList<String>(originalitem);
                        result.values = list;
                        result.count = list.size();
                    }
                }
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                item = (ArrayList<String>)results.values;
                if(results.count>0){
                    notifyDataSetChanged();
                }else{
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}
