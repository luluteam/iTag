package com.example.itagxml;
import java.util.ArrayList;
import java.util.HashMap;

import com.lovedayluk.database.DataBaseHelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter { 
    private class buttonViewHolder { 
        TextView tagName; 
        EditText newtag; 
        CheckBox ischange;
        TextView appId;
    } 

	DataBaseHelper tagdb;
    private ArrayList < HashMap < String , Object > > mAppList; 
    private LayoutInflater mInflater; 
    private Context mContext; 
    private String [ ] keyString; 
    private int [ ] valueViewID; 
    private buttonViewHolder holder; 
    
    public ListViewAdapter( Context c, ArrayList < HashMap < String , Object > > appList, int resource, 
        String [ ] from , int [ ] to) { 
		//dbHelper = new MyDataBaseHelper(c, "agenda.db2", null,1);
    	tagdb = new DataBaseHelper(c);
 
    	
    	
    	
        mAppList = appList; 
        mContext = c; 
        mInflater = ( LayoutInflater) mContext. getSystemService( Context . LAYOUT_INFLATER_SERVICE) ; 
        keyString = new String [ from . length ] ; 
        valueViewID = new int [ to. length ] ; 
        System . arraycopy ( from , 0, keyString, 0, from . length ) ; 
        System . arraycopy ( to, 0, valueViewID, 0, to. length ) ; 
    } 
    
    public int getCount( ) { 
        return mAppList. size ( ) ; 
    } 

    public Object getItem( int position ) { 
        return mAppList. get ( position ) ; 
    } 

    public long getItemId( int position ) { 
        return position ; 
    } 
    
    //Ó¦¸Ã·µ»ØID
    public long getId(int position){
    	return position;
    }
      
    public View getView ( int position , View convertView, ViewGroup parent ) { 
        if ( convertView != null ) { 
            holder = ( buttonViewHolder) convertView. getTag ( ) ; 
        } else { 
           // convertView = mInflater. inflate ( R.layout.listview1, null ) ; 
            holder = new buttonViewHolder( ) ; 
           // holder. appId = ( ImageView ) convertView. findViewById( valueViewID[ 0] ) ; 
            holder. tagName = ( TextView) convertView. findViewById( valueViewID[ 1] ) ; 
          //  holder. buttonClose = ( ImageButton) convertView. findViewById( valueViewID[ 2] ) ; 
            holder. appId = (TextView) convertView. findViewById(valueViewID[3]);
            convertView. setTag( holder) ; 
        } 
        
        HashMap < String , Object > appInfo = mAppList. get ( position ) ; 
        if ( appInfo != null ) { 
            String aname = ( String ) appInfo. get ( keyString[ 1] ) ; 
            int mid = ( Integer ) appInfo. get ( keyString[ 0] ) ; 
            int bid = ( Integer ) appInfo. get ( keyString[ 2] ) ; 
            String id = (String) appInfo.get(keyString[3]);
            holder. tagName. setText ( aname) ; 
            //holder. appId. setImageDrawable( holder. appId. getResources ( ) . getDrawable( mid) ) ; 
           // holder. buttonClose. setImageDrawable( holder. buttonClose. getResources ( ) . getDrawable( bid) ) ; 
           // holder. buttonClose. setOnClickListener( new lvButtonListener( position ) ) ; 
            holder.appId.setText(id);
        }         
        return convertView; 
    } 
    
    public void removeItem ( int position ) { 
    	Log.v("ListViewAdapter", Integer.toString(position));
        HashMap < String , Object > appInfo = mAppList. get ( position) ; 
        String id = appInfo.get("ItemId").toString();
       // dbHelper.getWritableDatabase().execSQL("delete from t_agenda where _id = " + id);
        Log.v("ListViewAdapter",id);
        //String title = appInfo.get("ItemWinName").toString();
        //String s[] = title.split(" ");//holder.appName.getText().toString().split(" ");
        //Log.v("ListViewAdapter", title+" "+Integer.toString(Integer.parseInt(s[0])));//holder.appName.getText().toString());Integer.toString(Integer.parseInt(s[0])));
        //dbHelper.getWritableDatabase().execSQL("delete from t_agenda where _id = " + s[0]);//Integer.parseInt(s[0]) );
        
		//if(dbHelper != null) {
		//	dbHelper.close();
		//}        
       // mAppList. remove ( position ) ; 

        this . notifyDataSetChanged( ) ; 
    } 
    
    class lvButtonListener implements OnClickListener { 
        private int position ; 

        lvButtonListener( int pos) { 
            position = pos; 
        } 
        
        public void onClick( View v) { 
            int vid= v. getId ( ) ; 
            //if ( vid == holder. buttonClose. getId ( ) ) 
           // {
                removeItem ( position ) ;          
				Log.i("List", "I delete an item");				
           // }
        } 
    }
}