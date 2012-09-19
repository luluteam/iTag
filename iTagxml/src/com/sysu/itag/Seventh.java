package com.sysu.itag;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lovedayluk.database.DataBaseHelper;

public class Seventh extends BaseActivity{

	private Button b1 = null;
	private EditText et = null;
	private String purl=null;
	private String key=null;
	private TextView name=null;
	private Button sumit=null;
	
	private DataBaseHelper tagdb= new DataBaseHelper(this);
	private String[] info=null;
	private Button cancel;
	private Cursor myCursor;	
	private Button add;
	private String tag="";
	SimpleAdapter adapter;
	private ListView listview=null;
	ArrayList<HashMap<String, Object>> listData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
        final Bundle bundle = this.getIntent().getExtras();
//        String message = bundle.getString("message");
        tag=bundle.getString("tag");
		setContentView(R.layout.seventh);
		if(bundle.getBoolean("flag")){
			Intent intent = new Intent();
	        intent.setType("image/*");
	        intent.setAction(Intent.ACTION_GET_CONTENT); 
	        startActivityForResult(intent, 1);
	        
		}
		else
			purl=bundle.getString("purl");
		
        
		
		b1 = (Button) findViewById(R.id.button1);
		add = (Button) findViewById(R.id.button6);
		et = (EditText) findViewById(R.id.editText1);
		name=(TextView) findViewById(R.id.textView1);
		sumit=(Button)findViewById(R.id.button3);
		cancel=(Button)findViewById(R.id.button4);
		ImageView imageView = (ImageView) findViewById(R.id.imageView2);
		GridView gv = (GridView) findViewById(R.id.gridView1);
		
//		gv = (GridView)findViewById(R.id.gridView1);
		
		     
	   
		
		if(!bundle.getBoolean("flag")){
			imageView.setImageBitmap(getPic(purl));
			getTags();
			SimpleAdapter sa=new SimpleAdapter(
	                this, //上下文环境
	               listData,     //数据源
	                R.layout.sixth2,  //内容布局
	                new String[]{"tag"},   //数据源的arrayName
	                new int[]{R.id.textView1}  //装载数据的控件
			);   
			gv.setAdapter(sa);    //与gridview绑定
		}
		
		add.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				addTag();
//				LayoutParams laParams=(LayoutParams)imageView.getLayoutParams();
				
			}

				//Toast.makeText(this, myCursor.getColumnNames()[0], Toast.LENGTH_LONG).show();
			
		});
		
		
		
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent();
				if(!bundle.getBoolean("flag"))
				{
					bundle.putString("purl", purl);
					bundle.putString("tag", tag);
					intent.putExtras(bundle);
					intent.setClass(Seventh.this, Four.class);
				}
				else
					intent.setClass(Seventh.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		imageView.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent();
		        intent.setType("image/*");
		        intent.setAction(Intent.ACTION_GET_CONTENT); 
		        startActivityForResult(intent, 1);
			}
		});
		
		et.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO 自动生成的方法存根
				 key = et.getText().toString();
				return false;
			}
		});
		
		sumit.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				
//				if(info.length>0)
//				{
//					tag=info[0];
//					Log.v("tag from info", "tag");
//				}
					addTag();
				// TODO 自动生成的方法存根
				//if(bundle.getBoolean("flag"))
				Intent intent = new Intent();
	        	Bundle bundle=new Bundle();
				bundle.putString("purl", purl);
				bundle.putBoolean("flag", true);
				bundle.putString("tag", tag);
	        	intent.putExtras(bundle);
				intent.setClass( Seventh.this,Four.class);
				startActivity(intent);
				
		}});
		
		cancel.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v) {
				// TODO 自动生成的方法存根
				et.setText("");
			}
			
		});
		
	}
	

	/**
	 * 将数据加入database
	 * @param null
	 * @return null
	 */
	protected void addTag() {
		// TODO Auto-generated method stub
		key = et.getText().toString();
		info=key.split(" ");                 // 通过分割符号将字符串分割并保存到info数组中
		
		//tagdb.getWritableDatabase().execSQL("insert into tagdb values(null, 1, 1)");
			for(int i=0;i<info.length;i++){
				Log.v("info Message", info[i]);
		          tagdb.getWritableDatabase().execSQL("insert into tagdb values (null, ?, ?)", this.getStringArray(info[i],purl));
			}
		Toast.makeText(getBaseContext(), "添加成功", Toast.LENGTH_SHORT).show();
		Log.v("add tag", "added");
		
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			
			Log.e("uri7", uri.toString());
			purl=uri.toString();
			ContentResolver cr = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				ImageView imageView = (ImageView) findViewById(R.id.imageView2);
				imageView.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(),e);
			}
		}
		
	}
	
	/**
	 * 通过purl获取全部的tag
	 * @param null
	 * @return string
	 */
	public String[] getTags(){
		Cursor c = tagdb.select_uri(purl);
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
	
}
