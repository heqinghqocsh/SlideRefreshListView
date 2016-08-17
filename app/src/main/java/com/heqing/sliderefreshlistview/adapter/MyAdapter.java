package com.heqing.sliderefreshlistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.heqing.sliderefreshlistview.R;
import com.heqing.sliderefreshlistview.model.Entity;

import java.util.List;


/**
 * Created by 何清 on 2016/7/22.
 *
 * @description
 */
public class MyAdapter extends BaseAdapter {

    private List<Entity> entityList;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, List<Entity> entityList) {
        this.context = context;
        this.entityList = entityList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return entityList.size();
    }

    @Override
    public Object getItem(int position) {
        return entityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.left_drag_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Entity entity = entityList.get(position);
        holder.content.setText(entity.getContent());
        holder.title.setText(entity.getTitle());
        holder.time.setText(entity.getTime());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "删除按钮", Toast.LENGTH_SHORT).show();
                entityList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击条目："+position, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    class ViewHolder {
        public View rootView;
        public TextView title;
        public TextView content;
        public TextView time;
        public TextView delete;

        public ViewHolder(View view) {
            rootView = view.findViewById(R.id.rootView);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            delete = (TextView)view.findViewById(R.id.delete);
        }
    }
}
