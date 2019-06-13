package com.hexun.news;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

public class phoneRead2 extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView tv = new TextView(this);
		tv.setText(getSmsInPhone());

		ScrollView sv = new ScrollView(this);
		sv.addView(tv);
		
		setContentView(sv);
	}

	public String getSmsInPhone() {
		final String SMS_URI_ALL = "content://sms/";
		final String SMS_URI_INBOX = "content://sms/inbox";
		final String SMS_URI_SEND = "content://sms/sent";
		final String SMS_URI_DRAFT = "content://sms/draft";
		final String SMS_URI_OUTBOX = "content://sms/outbox";
		final String SMS_URI_FAILED = "content://sms/failed";
		final String SMS_URI_QUEUED = "content://sms/queued";

		StringBuilder smsBuilder = new StringBuilder();

		try {
			Uri uri = Uri.parse(SMS_URI_ALL);
			String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
			Cursor cur = getContentResolver().query(uri, projection, null, null, "date desc");		// 获取手机内部短信

			if (cur.moveToFirst()) {
				int index_Address = cur.getColumnIndex("address");
				int index_Person = cur.getColumnIndex("person");
				int index_Body = cur.getColumnIndex("body");
				int index_Date = cur.getColumnIndex("date");
				int index_Type = cur.getColumnIndex("type");

				do {
					String strAddress = cur.getString(index_Address);
					int intPerson = cur.getInt(index_Person);
					String strbody = cur.getString(index_Body);
					long longDate = cur.getLong(index_Date);
					int intType = cur.getInt(index_Type);

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date d = new Date(longDate);
					String strDate = dateFormat.format(d);

					String strType = "";
					if (intType == 1) {
						strType = "接收";
					} else if (intType == 2) {
						strType = "发送";
					} else {
						strType = "null";
					}

					String strAddress2 = getPeopleNameFromPerson(strAddress);
					
					smsBuilder.append("[ ");
//					smsBuilder.append(strAddress + ", ");
					smsBuilder.append(strAddress + " : " + strAddress2 + ", ");
					smsBuilder.append(intPerson + ", ");
					smsBuilder.append(strbody + ", ");
					smsBuilder.append(strDate + ", ");
					smsBuilder.append(strType);
					smsBuilder.append(" ]\n\n");
				} while (cur.moveToNext());

				if (!cur.isClosed()) {
					cur.close();
					cur = null;
				}
			} else {
				smsBuilder.append("no result!");
			} // end if

			smsBuilder.append("getSmsInPhone has executed!");

		} catch (SQLiteException ex) {
			Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
		}

		return smsBuilder.toString();
	}
	
	// 通过address手机号关联Contacts联系人的显示名字
	private String getPeopleNameFromPerson(String address){
		if(address == null || address == ""){
			return "( no address )\n";
		}
		
		String strPerson = "null";
		String[] projection = new String[] {Phone.DISPLAY_NAME, Phone.NUMBER};
		
		Uri uri_Person = Uri.withAppendedPath(Phone.CONTENT_FILTER_URI, address);	// address 手机号过滤
		Cursor cursor = getContentResolver().query(uri_Person, projection, null, null, null);
		
		if(cursor.moveToFirst()){
			int index_PeopleName = cursor.getColumnIndex(Phone.DISPLAY_NAME);
			int index_PhoneNum = cursor.getColumnIndex(Phone.NUMBER);
			
			String strPeopleName = cursor.getString(index_PeopleName);
			String strPhoneNum = cursor.getString(index_PhoneNum);
			strPerson = "( " + strPeopleName + " : " + strPhoneNum + " )\n";
			strPerson = strPeopleName;
		}
		cursor.close();
		
		return strPerson;
	}
	
}
