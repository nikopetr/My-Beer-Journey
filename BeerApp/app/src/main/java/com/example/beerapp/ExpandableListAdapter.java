package com.example.beerapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import java.util.HashMap;
import java.util.List;

// This is a class that links an expandable list view with data
// It is used for the settings section for the FAQ
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    // Constructor
    public ExpandableListAdapter (Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    // All functions of BaseExpandableListAdapter
    // Gets the number of groups
    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    // Gets the number of children in a specified group
    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).size();
    }

    // Gets the data associated with the given group
    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    // Gets the data associated with the given child within the given group
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    // Gets the ID for the group at the given position
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // Gets the ID for the given child within the given group
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // Indicates whether the child and group IDs are stable across changes to the underlying data
    @Override
    public boolean hasStableIds() {
        return false;
    }

    // Gets a View that displays the given group
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String)getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        TextView listHeader = convertView.findViewById(R.id.listHeader);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(headerTitle);
        return convertView;
    }

    // Gets a View that displays the data for the given child within the given group
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String)getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        TextView listChild = convertView.findViewById(R.id.listItem);
        listChild.setText(childText);
        return convertView;
    }

    // Whether the child at the specified position is selectable
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
