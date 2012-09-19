package com.example.itagxml;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.lovedayluk.database.DataBaseHelper;

public class Second extends BaseActivity{

	private Button btn_back = null;
	private Button btn_view = null;
	private ImageView img_click = null;
//	private TextView tag;
//	private TextView number;
	private DataBaseHelper database;
	private Cursor cursor;
	ArrayList<HashMap<String, Object>> listData;
	SimpleAdapter adapter;
	private ListView listview=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		database = new DataBaseHelper(this);

		btn_back = (Button) findViewById(R.id.button1);
		btn_view = (Button) findViewById(R.id.button2);
		
//		tag = (TextView) findViewById(R.id.textView1);
//		number = (TextView) findViewById(R.id.textView2);
		
//
//		img_click = (ImageView) findViewById(R.id.imageView2);
		
//		cursor = tagdb.select();
		
		btn_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v("btn_back", "Click");
				Intent intent = new Intent();
				intent.setClass(Second.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		btn_view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v("btn_view", "Click");
				
				
				Log.v("btn_view", "Click Finish");
				
			}
		});
		getAllData();
		setListView();
		listview.setAdapter(adapter);
//		
//		img_click.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(Second.this, Third.class);
//				startActivity(intent);
//				finish();
//			}
//		});
	}
	
	public void setListView(){
		listview = (ListView) findViewById(R.id.listView1);
		adapter = new SimpleAdapter(Second.this,
			listData,// 数据源
			R.layout.list_items,// ListItem的XML实现
			// 动态数组与ImageItem对应的子项
			new String[] {"tag"},
			// ImageItem的XML文件里面的一个ImageView,两个TextView ID
			new int[] { R.id.tag});
		listview.setAdapter(adapter);
		
		//监听单击事件
		listview.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String str = arg0.getItemAtPosition(arg2).toString();
				String[] arr = str.split(",");
				String[] arr2 = arr[2].split("=");
				String tag = arr2[1].substring(0, arr2[1].length()-1);

				Intent intent = new Intent();
	        	Bundle bundle=new Bundle();
	        	bundle.putString("tag", tag);
	        	intent.putExtras(bundle);
				intent.setClass(Second.this, Third.class);
				startActivity(intent);
				finish();
			}
			
		});
	}

	
	public void getAllData() {
		Log.v("Function", "getAllData");
		//database.insert_tag("Luluku323", "mnt", "tagdb");
		//database.insert_tag("Luluku33", "mt", "tagdb");
		
		Cursor c = database.select_all();
		startManagingCursor(c);
		int columnsSize = c.getColumnCount();
		listData = new ArrayList<HashMap<String, Object>>();
		// 获取表的内容
		while (c.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < columnsSize; i++) {
				Log.v("tag table _id", Integer.toString(c.getColumnIndex("_id")));
				Log.v("tag table _id", c.getString(c.getColumnIndex("_id")));
				Log.v("tag table tag", c.getString(1));
				Log.v("tag table uri", c.getString(2));
				map.put("_id", c.getString(c.getColumnIndex("_id")));
				map.put("tag", c.getString(1));
				map.put("uri", c.getString(2));
			}
			listData.add(map);
		}          
	}

}
