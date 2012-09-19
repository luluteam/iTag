package com.sysu.itag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Fifth2 extends BaseActivity{
	
	private CheckBox m_checkbox = null;
	private Button b1 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.fifth2);
		
		m_checkbox = (CheckBox) findViewById(R.id.checkBox1);
		b1 = (Button) findViewById(R.id.button1);
		
		m_checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO 自动生成的方法存根
				if ( m_checkbox.isChecked() ) {
					
				}
				else {
					
				}
				
			}
		});
		
		b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent();
				intent.setClass(Fifth2.this, Fifth.class);
				startActivity(intent);
				finish();
			}
		});
		
	}
	
}
