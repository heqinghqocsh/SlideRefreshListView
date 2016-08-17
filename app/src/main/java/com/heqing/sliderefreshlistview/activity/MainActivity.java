package com.heqing.sliderefreshlistview.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


import com.heqing.sliderefreshlistview.R;
import com.heqing.sliderefreshlistview.adapter.MyAdapter;
import com.heqing.sliderefreshlistview.listener.RefreshLoadMoreListener;
import com.heqing.sliderefreshlistview.model.Entity;
import com.heqing.sliderefreshlistview.widget.RefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainActivity extends FragmentActivity
        implements RefreshLoadMoreListener,AdapterView.OnItemClickListener{

    private RefreshListView listView;
    List<Entity> dataList = new ArrayList<>();
    private MyAdapter adapter;
    private int counter = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    dataList.clear();
                    counter = 0;
                    for (int i = 0;i<20;i++){
                        Entity entity = new Entity("标题#"+counter
                                ,"内容#"+counter,"星期"+(counter % 7 + 1));
                        dataList.add(entity);
                        counter++;
                    }
                    adapter.notifyDataSetChanged();

                    break;
                case 2:
                    for (int i = 0;i<10;i++){
                        Entity entity = new Entity("标题#"+counter
                                ,"内容#"+counter,"星期"+(counter % 7 + 1));
                        dataList.add(entity);
                        counter++;
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
            listView.completeRefreshOrLoadMore();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        listView = (RefreshListView)findViewById(R.id.refresh_list_view);
//        for (int i = 0;i<10;i++){
//            Entity entity = new Entity("标题#"+counter
//                    ,"内容#"+counter,"星期"+(counter % 7 + 1));
//            dataList.add(entity);
//            counter++;
//        }
        adapter = new MyAdapter(this,dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setRefreshLoadMoreListener(this);
        listView.startRefresh();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"点击了："+position,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void refresh() {
        Message msg = handler.obtainMessage(1,null);
        handler.sendMessageDelayed(msg,3000);
    }
    @Override
    public void loadMore() {
        Message msg = handler.obtainMessage(2,null);
        handler.sendMessageDelayed(msg,4000);
    }


}
