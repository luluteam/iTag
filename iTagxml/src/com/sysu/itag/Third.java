package com.sysu.itag;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.lovedayluk.database.DataBaseHelper;

public class Third extends BaseActivity{
	
	private Button b1 = null;
	String tag = "";
	private DataBaseHelper database;
	private Cursor cursor;
	ArrayList<HashMap<String, Object>> listData;
	String path,filemanagerstring;
	String[] ImageNameArr;
	String filePath= null;	   
	String ImageName ;	   
	Bitmap bitmap;

	String sub_list;
	String[] pathArr;
	Bitmap[] bmp;
	Uri uri;

	ArrayList<String> items= new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
        final Bundle bundle = this.getIntent().getExtras();
        if(!bundle.isEmpty())
        	tag = bundle.getString("tag");

        database = new DataBaseHelper(this);
        
		setContentView(R.layout.third);
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Third.this, Second.class);
				startActivity(intent);
				finish();
			}
		});
		
		//Log.v("tag", "begin tag"+tag);
		getUris();
		GridView gridview = (GridView) findViewById(R.id.gridview);
		
		putUris();
		
		gridview.setAdapter(new ImageApdater(getApplicationContext(),bmp));

		//gridview.setOnItemClickListener(this);  
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(Third.this, Four.class);
	        	Bundle bundle=new Bundle();
	        	bundle.putString("tag", tag);
	        	bundle.putInt("SelectedPicture", arg2);
	        	bundle.putString("purl", getUri(arg2));
	        	intent.putExtras(bundle);
				startActivity(intent);
				//finish();
			}
		});
	}
	
	/*
	 * 通过listData拿到所有uri,放入pathArr中
	 */
	private void putUris() {
		// TODO Auto-generated method stub
		bmp= new Bitmap[listData.size()];
		int loc=0;
		for(HashMap<String, Object> obj : listData){
			Log.v("uri:", String.valueOf(obj.get("uri")));
			ContentResolver cr = this.getContentResolver();
			uri = Uri.parse(String.valueOf(obj.get("uri")));
			Bitmap temp_bm = null;
			try {
				temp_bm = BitmapFactory.decodeStream(cr.openInputStream(uri));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			bmp[loc]=temp_bm;
			loc++;
		}
	}
	
	public String getUri(int position){
		Log.v("pos",Integer.toString(position));
		return listData.get(position).get("uri").toString();
	}

	
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



/*
 * 根据uri显示图片,返回bitmap对象
 */
public Bitmap decodeFile(String filePath) 

{

  // System.out.println("filepath in decode file .. "+filePath);

   BitmapFactory.Options o= new BitmapFactory.Options();

   o.inJustDecodeBounds= true;

   BitmapFactory.decodeFile(filePath, o);



   // The new size we want to scale to

   final int REQUIRED_SIZE= 100;

   final int H= 50;



   // Find the correct scale value. It should be the power of 2.

   int width_tmp= o.outWidth, height_tmp= o.outHeight;

   int scale= 1;

   while (true) {

       if (width_tmp< REQUIRED_SIZE&& height_tmp< H)

           break;

        width_tmp/= 2;

        height_tmp/= 2;

        scale*= 2;

   }

   // Decode with inSampleSize

   BitmapFactory.Options o2= new BitmapFactory.Options();

   o2.inSampleSize= scale;

   //System.out.println("decode file ........... "+filePath);

   bitmap= BitmapFactory.decodeFile(filePath, o2);    

   return bitmap;

}


class ImageApdater extends BaseAdapter{

	 private Context mContext;      

	  Bitmap[] mImageArray;  



	    public ImageApdater(Context c, Bitmap[] imgArray) 

	   {

	          mContext= c;

	          mImageArray= imgArray;            

	    }

	   public int getCount() 

	   {

	         return mImageArray.length;

	   }

	   public Object getItem(int position)

	   {

	         return position;

	   }

	   public long getItemId(int position)

	   {

	         return position;

	   }



	   public View getView(int position,View convertView,ViewGroup parent)

	   {

	       System.gc();

	       ImageView i= null ;



	       if (convertView== null ) 

	       {              

	             i= new ImageView(mContext);

	            i.setLayoutParams(new GridView.LayoutParams(92,92));

	            i.setScaleType(ImageView.ScaleType.CENTER_CROP);              

	             i.setImageBitmap(mImageArray[position]);                          

	       }

	         else 

	             i= (ImageView) convertView;          

	          return i;

	   }  
	}
              
}
