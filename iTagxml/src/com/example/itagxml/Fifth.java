package com.example.itagxml;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import com.lovedayluk.database.DataBaseHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Fifth extends BaseActivity{
	
	private Button back = null;
	private Button find = null;
	private Button b3 = null;
	private Button b4 = null;
	private Button b6 = null;
	private String purl = null;
	private ImageView image = null;
	
	String tag = " ";
	private DataBaseHelper database;
	private Cursor cursor;
	ArrayList<HashMap<String, Object>> listData;
	private GridView gv;  
	private boolean flag2 = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fifth);
		
		Bundle bundle = getIntent().getExtras();
		//上一张照片的uri
		purl=bundle.getString("purl");
		
		//tag
		tag = bundle.getString("tag");
		Log.v("tag", tag);
		
		
		database = new DataBaseHelper(this);
		cursor = database.select_uri(purl);
		getTags();
		back = (Button) findViewById(R.id.button1);
		find = (Button) findViewById(R.id.button2);
//		b3 = (Button) findViewById(R.id.button3);
//		b4 = (Button) findViewById(R.id.button4);
//		b6 = (Button) findViewById(R.id.button6);
		image = (ImageView)findViewById(R.id.imageView2);
		image.setImageBitmap(getPic(purl));
		gv = (GridView)findViewById(R.id.gridView1);
		
		 SimpleAdapter sa=new SimpleAdapter(
	                this, //上下文环境
	               listData,     //数据源
	                R.layout.gridrow,  //内容布局
	                new String[]{"tag"},   //数据源的arrayName
	                new int[]{R.id.textView1}  //装载数据的控件
	     );       
	    gv.setAdapter(sa);    //与gridview绑定
	    
	    /**
	     * GridView中的元素点击事件监听
	     * 点击后弹出确认框,确认删除
	     * @param null
	     * @return null
	     */
	    gv.setOnItemClickListener(new OnItemClickListener()
	    {

	    	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    		
				// TODO Auto-generated method stub
//	    		Toast.makeText(Fifth.this, "你选择了" + (position + 1), Toast.LENGTH_SHORT).show();
	    		cursor.moveToPosition(position);
	    		//出现提示
	    		AlertDialog.Builder builder = new AlertDialog.Builder(Fifth.this);

	    		  builder.setMessage("是否删除?")

	    		  .setCancelable(false)

	    		  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

	    		   public void onClick(DialogInterface dialog, int id) {
//
	    			   String _tag = cursor.getString(1);
	   	    		if(_tag == tag)
	   	    		{
	   	    			flag2=true;
	   	    			cursor.moveToNext();
	   	    			tag = cursor.getString(1);
	   	    		}
	    			   database.deleteByTag(_tag);
	    			   Toast.makeText(Fifth.this, "成功删除" , Toast.LENGTH_SHORT).show();

	    		   }

	    		  })

	    		  .setNegativeButton("No", new DialogInterface.OnClickListener() {

	    		   public void onClick(DialogInterface dialog, int id) {

	    		    dialog.cancel();

	    		   }

	    		  })
	    		  .show();

//	    		  AlertDialog alert = builder.create();
//
//	    		  return alert;
	    		
	    		
	    		
	    	}
	    });

		
		back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("purl", purl);
				bundle.putString("tag", tag);
				Log.v("purl",purl);
				intent.putExtras(bundle);
				intent.setClass(Fifth.this, Four.class);
				startActivity(intent);
				finish();
			}
		});
		
		
//		b6.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				// TODO 自动生成的方法存根
//				Intent intent = new Intent();
//				intent.setClass(Fifth.this, Fifth2.class);
//				startActivity(intent);
//				finish();
//			}
//		});
		
	}
	


	/**
	 * 通过tag获取全部的uri
	 * @param null
	 * @return string[]
	 */
	public String[] getUris(){
			Cursor c = database.select_tag(tag);
			startManagingCursor(c);
			listData = new ArrayList<HashMap<String, Object>>();
			while (c.moveToNext()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("_id", c.getInt(c.getColumnIndex("_id")));
					map.put("tag", c.getString(c.getColumnIndex("tag")));
					map.put("uri", c.getString(c.getColumnIndex("uri")));
				//Log.v("uri", c.getString(c.getColumnIndex("uri")));
				listData.add(map);
			}  
		return null;
	}
	
	
	/**
	 * 通过purl获取全部的tag
	 * @param null
	 * @return string
	 */
	public String[] getTags(){
		Cursor c = database.select_uri(purl);
		startManagingCursor(c);
		listData = new ArrayList<HashMap<String, Object>>();
		String temp_tags[]=new String[10];
		String temp ="";
		int i=0;
		while (c.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("_id", c.getInt(c.getColumnIndex("_id")));
				map.put("tag", c.getString(c.getColumnIndex("tag")));
				map.put("uri", c.getString(c.getColumnIndex("uri")));
			//Log.v("uri", c.getString(c.getColumnIndex("uri")));
			listData.add(map);
			temp_tags[i] = c.getString(c.getColumnIndex("tag"));
			i++;
		}
//		String s = temp_tags.toString();
//		Log.v("tags", s);
//		return temp_tags.toString();
		return temp_tags;	
	}
	
	/**
	 * 通过purl获得图片
	 * @param purl
	 * @return
	 */
	protected Bitmap getPic(String purl) {
		//Log.v("purl:", purl);
		Uri uri = Uri.parse(purl);
		ContentResolver cr= this.getContentResolver();
		Bitmap temp_bm = null;
		try {
			temp_bm = BitmapFactory.decodeStream(cr.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return temp_bm;
	}
	
}
