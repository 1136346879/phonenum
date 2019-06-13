package com.hexun.news;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * 读取手机短信
 * 
 * @author sunboy_2050
 * @since  http://blog.csdn.net/sunboy_2050
 * @date   2012.03.06
 */
public class smsRead2 extends Activity {
	
	private static final String LOG_TAG = "smsRead";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		TextView tv = new TextView(this);
		tv.setText("Hello Adnroid");
		tv.setText(getSMSinPhone());
		
		setContentView(tv);
	}
	
	public String getSMSinPhone(){
		final String SMS_URI_ALL =  "content://sms/"; 			//0  
		final String SMS_URI_INBOX = "content://sms/inbox";		//1  
		final String SMS_URI_SEND = "content://sms/sent";		//2  
		final String SMS_URI_DRAFT = "content://sms/draft";		//3  
		final String SMS_URI_OUTBOX = "content://sms/outbox";	//4  
		final String SMS_URI_FAILED = "content://sms/failed";	//5  
		final String SMS_URI_QUEUED = "content://sms/queued";	//6  
		
		StringBuilder strBuilder = new StringBuilder();
		String[] projection = new String[]{"_id", "address", "person", "body", "date" };
		
		try {
//			Cursor cursor = managedQuery(Uri.parse(SMS_URI_ALL), projection, null, null, "date desc");	
			Cursor cursor = getContentResolver().query(Uri.parse("content://sms/"), projection, null, null, "date desc");	// 读取手机内部短信
			
			if(cursor.moveToFirst()){
				int index_Address = cursor.getColumnIndex("address");
				int index_Person = cursor.getColumnIndex("person");
				int index_Body = cursor.getColumnIndex("body");
				int index_Date = cursor.getColumnIndex("date");
				
				do{
					String strAddress = cursor.getString(index_Address);
					String strPerson = cursor.getString(index_Person);
					String strBody = cursor.getString(index_Body);
					long longDate = cursor.getLong(index_Date);
					
					strBuilder.append("{ " + strAddress);
					strBuilder.append(", " + strPerson);
					strBuilder.append(", " + strBody);
					strBuilder.append(", " + longDate + " }\n\n");
					
					Log.i(LOG_TAG, strBuilder.toString());
					
				}while(cursor.moveToNext());
				
				if(!cursor.isClosed()){		// 关闭游标
					cursor.close();
					cursor = null;
				}
				
			} else {
				strBuilder.append("no result!\n");
			}
			
			
		} catch (Exception e) {
			Log.d(LOG_TAG, "Exception: " + e.getMessage());
		}
		
		return strBuilder.toString();
	}
	
}
