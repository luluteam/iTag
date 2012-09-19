package com.example.itagxml;

import java.io.FileNotFoundException;

import com.lovedayluk.database.DataBaseHelper;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends BaseActivity {

	private Button btn_camera = null;
	private Button btn_liulan = null;
	private Button btn_tianjia = null;
	private Button btn_exit = null;
	private DataBaseHelper tagdb;
	protected final static int quit=Menu.FIRST;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagdb=new DataBaseHelper(this);
        
        btn_camera = (Button) findViewById(R.id.button1);
        btn_liulan = (Button) findViewById(R.id.button2);
        btn_tianjia = (Button) findViewById(R.id.button3);
        btn_exit = (Button)findViewById(R.id.tcbtn);
     
        btn_liulan.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Second.class);
				startActivity(intent);
				finish();
			}
		});
        
        btn_tianjia.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
	        	Bundle bundle=new Bundle();
	        	bundle.putBoolean("flag", true);
	        	intent.putExtras(bundle);
				intent.setClass(MainActivity.this, Seventh.class);
				startActivity(intent);
//				Intent intent = new Intent();
//		        intent.setType("image/*");
//		        intent.setAction(Intent.ACTION_GET_CONTENT); 
//		        startActivityForResult(intent, 1);
				//Intent intent = new Intent();
				//intent.setClass(MainActivity.this, Seventh.class);
				//startActivity(intent);
				finish();
			}
		});
        
        btn_exit.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				exitApp();
			}
        	
        });
        
        
    }
    
    @Override
   	protected void onDestroy() {
   		// TODO Auto-generated method stub
       	Log.v("Test", "Detory");
   		super.onDestroy();
   		if (tagdb != null) {
   			tagdb.close();
   			tagdb = null;
   		}
   			
   	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		return false;
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        //return true;
    	//menu.add(0,quit,0,"退出");  
    	//return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	//int item_id=item.getItemId();//得到当前选中MenuItem的ID
	//switch(item_id){
		//case 0:{
		//	Toast toast=Toast.makeText(this, "正在退出", Toast.LENGTH_SHORT);
		//	toast.show();
			//MainActivity.
		//}
	//}
	return true;
    }
    
   
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Log.e("uri", uri.toString());
			ContentResolver cr = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				ImageView imageView = (ImageView) findViewById(R.id.imageView2);
				
				imageView.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(),e);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    
}
