package com.hexun.news;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 读取手机短信, ListView 列表显示
 * 
 * @author sunboy_2050
 * @since  http://blog.csdn.net/sunboy_2050
 * @date   2012.03.06
 */
public class smsRead3 extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		ListView listView = new ListView(this);
		showListView(listView);
		
		setContentView(listView);
	}
	
	private void showListView(ListView listView){
		ArrayList<HashMap<String, String>> list = readAllSMS();
		SimpleAdapter listItemAdapter = new SimpleAdapter(
				this, 
				list, 
				android.R.layout.simple_list_item_2, 
				new String[] { "person", "data" }, 
				new int[] { android.R.id.text1, android.R.id.text2 }
			);
		listView.setAdapter(listItemAdapter);
	}

	private ArrayList<HashMap<String, String>> readAllSMS() {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		SMSReader smsReader = new SMSReader();
		int smsCount = smsReader.readSMSCount(this);
		
		for (int i = 0; i < smsCount; ++i) {
			HashMap<String, String> item = new HashMap<String, String>();
			
			SMSItem sms = smsReader.get(i);
			ContactItem contact = smsReader.getContact(this, sms);
			if (contact == null) {
				item.put("person", sms.mAddress);
			} else {
				item.put("person", contact.mName);
			}
			item.put("data", sms.toString());
			list.add(item);
		}
		
		return list;
	}
	
	
	
	
	
	
	public class SMSReader {
//		public Uri SMS_INBOX = Uri.parse("content://sms/inbox");
		public Uri SMS_INBOX = Uri.parse("content://sms/");
		private ArrayList<SMSItem> mSmsList = new ArrayList<SMSItem>();

		public SMSReader() {
		}

		SMSItem get(int idx) {
			return mSmsList.get(idx);
		}

		int readSMSCount(Activity activity) {
			Cursor cursor = activity.managedQuery(SMS_INBOX, null, null, null, null);	// 获取手机内短信
			
			if (cursor != null && cursor.moveToFirst()) {
				do {
					SMSItem item = new SMSItem(cursor);		// 初始化 SMSItem 实例
					mSmsList.add(item);
				} while (cursor.moveToNext());
			}
			
			return mSmsList.size();
		}

		ContactItem getContact(Activity activity, final SMSItem sms) {
			if (sms.mPerson == 0){
				return null;
			}
			
			Cursor cursor = activity.managedQuery(
					ContactsContract.Contacts.CONTENT_URI,
					new String[] { PhoneLookup.DISPLAY_NAME }, 
					" _id=?",
					new String[] { String.valueOf(sms.mPerson) }, 
					null
				);
			
			if (cursor != null && cursor.moveToFirst()) {
				int idx = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
				ContactItem item = new ContactItem();
				item.mName = cursor.getString(idx);
				return item;
			}
			
			return null;
		}
	}

	class SMSItem {
		public static final String ID = "_id";
		public static final String THREAD = "thread_id";
		public static final String ADDRESS = "address";
		public static final String PERSON = "person";
		public static final String SUBJECT = "subject";
		public static final String BODY = "body";
		public static final String READ = "read";
		public static final String DATE = "date";

		private int mIdIdx;
		private int mThreadIdx;
		private int mAddrIdx;
		private int mPersonIdx;
		private int mSubjectIdx;
		private int mBodyIdx;
		private int mReadIdx;
		private int mDateIdx;

		public long mID;
		public long mThreadID;
		public String mAddress;
		public long mPerson;
		public String mSubject;
		public String mBody;
		public long mRead;
		public long mDate;

		public SMSItem(Cursor cursor) {
			mIdIdx = cursor.getColumnIndex(ID);
			mThreadIdx = cursor.getColumnIndex(THREAD);
			mAddrIdx = cursor.getColumnIndex(ADDRESS);
			mPersonIdx = cursor.getColumnIndex(PERSON);
			mDateIdx = cursor.getColumnIndex(DATE);
			mReadIdx = cursor.getColumnIndex(READ);
			mBodyIdx = cursor.getColumnIndex(BODY);
			mSubjectIdx = cursor.getColumnIndex(SUBJECT);
			
			mID = cursor.getLong(mIdIdx);
			mThreadID = cursor.getLong(mThreadIdx);
			mAddress = cursor.getString(mAddrIdx);
			mPerson = cursor.getLong(mPersonIdx);
			mDate = cursor.getLong(mDateIdx);
			mRead = cursor.getLong(mReadIdx);
			mBody = cursor.getString(mBodyIdx);
			mSubject = cursor.getString(mSubjectIdx);
		}

		public String toString() {
			String ret = ID + ":" + String.valueOf(mID) + " " 
						+ THREAD + ":" + String.valueOf(mThreadID) + " " 
						+ ADDRESS + ":" + mAddress + " " 
						+ PERSON + ":" + String.valueOf(mPerson) + " "
						+ SUBJECT + ":" + mSubject + " " 
						+ BODY + ":" + mBody + " " 
						+ READ + ":" + String.valueOf(mRead) + " " 
						+ DATE + ":" + String.valueOf(mDate);
			return ret;
		}
	}

	class ContactItem {
		public String mName;
	}

}
