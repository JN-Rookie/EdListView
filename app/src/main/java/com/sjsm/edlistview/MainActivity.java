package com.sjsm.edlistview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView                  mLv_list;
    private  List<Map<String, String>> mList;
    private HashMap<Integer, String> hashMap = new HashMap<Integer, String>();//存放EditText内容的map
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        mLv_list = (ListView) findViewById(R.id.lv_list);
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            Map<String, String> item = new HashMap<>();
            item.put("Code", ""+i);
            item.put("Name", "名称"+i);
            item.put("Problem", "");
            mList.add(item);
        }
        MyAdapter adapter=new MyAdapter(mContext,mList);
        mLv_list.setAdapter(adapter);
    }

    private class MyAdapter extends BaseAdapter {

        private Context                   mContext;
        private List<Map<String, String>> mData;

        public MyAdapter(Context context, List<Map<String, String>> data) {
            mContext = context;
            mData = data;
            hashMap.clear();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        int mFocusPosition = -1;

        View.OnFocusChangeListener mListener = new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int position = (Integer) v.getTag();
                if (hasFocus) {
                    mFocusPosition = position;
                }
            }
        };

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_lightsupervisecard, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.code = (TextView) convertView.findViewById(R.id.lightsupervies_code);
                viewHolder.roadName = (TextView) convertView.findViewById(R.id.lightsupervies_name);
                viewHolder.problem = (EditText) convertView.findViewById(R.id.lightsupervies_problem);
                viewHolder.problem.setOnFocusChangeListener(mListener);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.code.setText(mData.get(position).get("Code"));
            viewHolder.roadName.setText(mData.get(position).get("Name"));
            viewHolder.problem.setTag(position);
            if (mFocusPosition == position) {
                viewHolder.problem.requestFocus();
            } else {
                viewHolder.problem.clearFocus();
            }
            viewHolder.problem.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position= (int) viewHolder.problem.getTag();
                    hashMap.put(position,s.toString());
                }
            });
            if(hashMap.get(position)!=null){
                viewHolder.problem.setText(hashMap.get(position));
                viewHolder.problem.setSelection(hashMap.get(position).length());//设置光标默认在最右
            }else {
                if(mData.get(position).get("Problem")!=null){
                    viewHolder.problem.setText(mData.get(position).get("Problem"));
                }else {
                    viewHolder.problem.setText("");
                }

            }

            return convertView;
        }

        class ViewHolder {
            TextView code, roadName;
            EditText problem;
        }
    }
}
