package com.lovedayluk.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	private final static String name = "itagxml.db2";
	private final static int version = 1;
	private final String CREATE_TABLE_SQL_TAGDB = "create table if not exists tagdb (_id integer primary key autoincrement, tag text, uri text)";
	private final String CREATE_TABLE_SQL_PICDB = "create table if not exists picdb (_id integer primary key autoincrement, uri text)";
	public final String tag = "tag";
	public final String uri = "uri";
	public final static String File_id = "_id";

	public DataBaseHelper(Context context) {
		super(context, name,null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL_TAGDB);
		db.execSQL(CREATE_TABLE_SQL_PICDB);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

	public void insert_tag(String tag, String uri, String table){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("tag", tag);
		values.put("uri", uri);
		db.insert(table, null, values);
	}
	
	public Cursor select_all() {
		SQLiteDatabase db=this.getReadableDatabase();
		//SQL
		String sql = "select * from tagdb where _id in(Select min(_id) FROM tagdb group by tag)";
		Cursor cursor = db.rawQuery(sql,null);
		return cursor;
	}

	public Cursor select_uri(String uri) {
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from tagdb where uri='"+uri+"' group by tag", null);  
		return cursor;
	}
	
	public Cursor select_tag(String tag) {
		
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from tagdb where tag='"+tag+"' group by uri", null);  
		return cursor;
	
	}
	
	public void deleteByTag(String _tag){
		SQLiteDatabase db= getWritableDatabase();
		String sql = "DELETE from tagdb where tag ='"+_tag+"'";
		db.execSQL(sql);
	}
	
	public void changeTag(String old_tag,String new_tag){
		
		SQLiteDatabase db= getWritableDatabase();
		String sql = "update tagdb set tag ='"+new_tag+"' where tag ='"+old_tag+"'";
		db.execSQL(sql);
	}

}
