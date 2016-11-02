package ntpu_dmcl.ntpu_guide;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chenhaowei on 2016/8/1.
 */

public class ExpandListAdapter extends BaseExpandableListAdapter {
    private Activity activity;
    private ArrayList<Object> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;

    public ExpandListAdapter(ArrayList<String> parents, ArrayList<Object> childern) {
        this.parentItems = parents;
        this.childtems = childern;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        child = (ArrayList<String>) childtems.get(groupPosition);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.main_list_child, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.drawer_text);
        textView.setText(child.get(childPosition));


        //Log.e("text.width",String.valueOf(textView.getWidth()));
        //Log.e("text.maxwidth",String.valueOf(textView.getMaxWidth()));
        //Log.e("text.maxems",String.valueOf(textView.getMaxEms()));
        //Log.e("text.size",String.valueOf(textView.getTextSize()));
        //Log.e("text.maxline",String.valueOf(textView.getMaxLines()));
        //Log.e("text.minlune",String.valueOf(textView.getMinLines()));
        //Log.e("text.size",String.valueOf(textView.getLineCount()));
        //Log.e("text.length",String.valueOf(child.get(childPosition).length()));


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Log.e("expanable","click");
            }
        });

        Button btn_go = (Button) convertView.findViewById(R.id.main_list_go);
        Button btn_info = (Button) convertView.findViewById(R.id.main_list_info);
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("main","go.group:"+String.valueOf(groupPosition)+".child:"+String.valueOf(childPosition));
                ((MainActivity) activity).OnNavigationClick(((ArrayList<String>) childtems.get(groupPosition)).get(childPosition));
                ((MainActivity) activity).closeDrawer();
            }
        });
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("main","info.group:"+String.valueOf(groupPosition)+".child:"+String.valueOf(childPosition));
                ((MainActivity) activity).OnInfoClick(((ArrayList<String>) childtems.get(groupPosition)).get(childPosition));
                ((MainActivity) activity).closeDrawer();
            }
        });
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
           // Log.e("Expan","null");
            convertView = inflater.inflate(R.layout.main_list_group, null);
        }
        //Log.e("Expan","textset");
        TextView textView = null;
        textView = (TextView) convertView.findViewById(R.id.drawer_text);
        textView.setText(parentItems.get(groupPosition));


        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //Log.e("expan","getchild");
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childtems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        //Log.e("expan","getgroup");
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }



}
