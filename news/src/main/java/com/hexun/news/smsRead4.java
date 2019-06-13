package com.hexun.news;

import java.sql.Date;
import java.text.SimpleDateFormat;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

/**
 * 读取手机短信, 先保存到SQLite数据，然后再读取数据库显示
 * 
 * @author sunboy_2050
 * @since  http://blog.csdn.net/sunboy_2050
 * @date   2012.03.06
 */
public class smsRead4 extends Activity {

	TableLayout tableLayout;
	int index = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		tableLayout = (TableLayout) findViewById(R.id.tableLayout);
		showSMS();
	}

	private void showSMS() {
		SmsHander smsHander = new SmsHander(this);

		smsHander.createSMSDatabase();							// 创建SQLite数据库
		smsHander.insertSMSToDatabase();						// 读取手机短信，插入SQLite数据库
		Cursor cursor = smsHander.querySMSInDatabase(100);		// 获取前100条短信（日期排序）

		cursor.moveToPosition(-1);
		while (cursor.moveToNext()) {
			String strAddress = cursor.getString(cursor.getColumnIndex("address"));
			String strDate = cursor.getString(cursor.getColumnIndex("date"));
			String strBody = cursor.getString(cursor.getColumnIndex("body"));

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(Long.parseLong(strDate));
			strDate = dateFormat.format(date);

			String smsTitle = strAddress + "\t\t" + strDate;
			String smsBody = strBody + "\n";
			Log.i("tableRow", smsTitle + smsBody);
			TextView tline = new TextView(this);
			tline.setText("_______________________________________");
			tableLayout.addView(tline, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// title Row
			TableRow trTitle = new TableRow(this);
			trTitle.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			TextView tvTitle = new TextView(this);
			tvTitle.setText(smsTitle);
			tvTitle.getPaint().setFakeBoldText(true); // 加粗字体
			tvTitle.setTextColor(Color.RED);
			tvTitle.setTextSize(16.0f);
			tvTitle.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			trTitle.addView(tvTitle);
			tableLayout.addView(trTitle, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			// body Row
			TableRow trBody = new TableRow(this);
			trBody.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			TextView tvBody = new TextView(this);
			tvBody.setText(smsBody);
			tvBody.getPaint().setFakeBoldText(false);
			tvBody.setTextSize(14.0f);
			tvBody.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			trBody.addView(tvBody);
			tableLayout.addView(trBody, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}

		if (!cursor.isClosed()) {
			cursor.close();
			cursor = null;
		}

		smsHander.closeSMSDatabase();
		index = 0;
	}

	public class SmsHander {

		SQLiteDatabase db;
		Context context;

		public SmsHander(Context context) {
			this.context = context;
		}

		public void createSMSDatabase() {
			String sql = "create table if not exists sms("
					+ "_id integer primary key autoincrement,"
					+ "address varchar(255)," + "person varchar(255),"
					+ "body varchar(1024)," + "date varchar(255),"
					+ "type integer)";
			db = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir().toString() + "/data.db3", null);			// 创建数据库
			db.execSQL(sql);
		}

		// 获取手机短信
		private Cursor getSMSInPhone() {
			Uri SMS_CONTENT = Uri.parse("content://sms/");
			String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
			Cursor cursor = context.getContentResolver().query(SMS_CONTENT, projection, null, null, "date desc");	// 获取手机短信

			while (cursor.moveToNext()) {
				System.out.println("--sms-- : " + cursor.getString(cursor.getColumnIndex("body")));
			}

			return cursor;
		}

		// 保存手机短信到 SQLite 数据库
		public void insertSMSToDatabase() {
			Long lastTime;
			Cursor dbCount = db.rawQuery("select count(*) from sms", null);
			dbCount.moveToFirst();
			if (dbCount.getInt(0) > 0) {
				Cursor dbcur = db.rawQuery("select * from sms order by date desc limit 1", null);
				dbcur.moveToFirst();
				lastTime = Long.parseLong(dbcur.getString(dbcur.getColumnIndex("date")));
			} else {
				lastTime = new Long(0);
			}
			dbCount.close();
			dbCount = null;

			Cursor cur = getSMSInPhone(); // 获取短信（游标）
			db.beginTransaction(); // 开始事务处理
			if (cur.moveToFirst()) {
				String address;
				String person;
				String body;
				String date;
				int type;

				int iAddress = cur.getColumnIndex("address");
				int iPerson = cur.getColumnIndex("person");
				int iBody = cur.getColumnIndex("body");
				int iDate = cur.getColumnIndex("date");
				int iType = cur.getColumnIndex("type");

				do {
					address = cur.getString(iAddress);
					person = cur.getString(iPerson);
					body = cur.getString(iBody);
					date = cur.getString(iDate);
					type = cur.getInt(iType);

					if (Long.parseLong(date) > lastTime) {
						String sql = "insert into sms values(null, ?, ?, ?, ?, ?)";
						Object[] bindArgs = new Object[] { address, person, body, date, type };
						db.execSQL(sql, bindArgs);
					} else {
						break;
					}
				} while (cur.moveToNext());

				cur.close();
				cur = null;
				db.setTransactionSuccessful(); 	// 设置事务处理成功，不设置会自动回滚不提交
				db.endTransaction(); 			// 结束事务处理
			}

		}

		// 获取 SQLite 数据库中的全部短信
		public Cursor querySMSFromDatabase() {
			String sql = "select * from sms order by date desc";
			return db.rawQuery(sql, null);
		}

		// 获取 SQLite 数据库中的最新 size 条短信
		public Cursor querySMSInDatabase(int size) {
			String sql;

			Cursor dbCount = db.rawQuery("select count(*) from sms", null);
			dbCount.moveToFirst();
			if (size < dbCount.getInt(0)) { // 不足 size 条短信，则取前 size 条
				sql = "select * from sms order by date desc limit " + size;
			} else {
				sql = "select * from sms order by date desc";
			}
			dbCount.close();
			dbCount = null;

			return db.rawQuery(sql, null);
		}

		// 获取 SQLite数据库的前 second秒短信
		public Cursor getSMSInDatabaseFrom(long second) {
			long time = System.currentTimeMillis() / 1000 - second;
			String sql = "select * from sms order by date desc where date > " + time;
			return db.rawQuery(sql, null);
		}

		// 关闭数据库
		public void closeSMSDatabase() {
			if (db != null && db.isOpen()) {
				db.close();
				db = null;
			}
		}

	}
}
