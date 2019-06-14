package com.hexun.discovery;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hexun.base.router.RouterSheet;
import com.hexun.base.ui.BaseMvpFragment;
import com.hexun.base.util.ToastUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.RECEIVE_WAP_PUSH;
import static android.Manifest.permission.SEND_SMS;

/**
 * @author yangyi 2017年10月19日13:37:01
 */
@Route(path = RouterSheet.DISCOVERY)
public class DiscoveryMvpFragment extends BaseMvpFragment {
    List listcontent = new ArrayList();

    @Autowired
    String name;
    TableLayout tableLayout;
    int index = 0;
    TextView nameText;
    private TextView phone1;
    private TextView phone2;
    private TextView phone3;
    private TextView phone4;
    private TextView phone5;
    private RelativeLayout duanxin1;
    private RelativeLayout duanxin2;
    private RelativeLayout duanxin3;

    @Override
    public void initFragmentData() {

    }

    @Override
    public void initFragmentView() {
        if (getView() != null) {
            nameText = (TextView) getView().findViewById(R.id.nameText);
            phone1 = (TextView) getView().findViewById(R.id.phone1);
            phone2 = (TextView) getView().findViewById(R.id.phone2);
            phone3 = (TextView) getView().findViewById(R.id.phone3);
            phone4 = (TextView) getView().findViewById(R.id.phone4);
            phone5 = (TextView) getView().findViewById(R.id.phone5);
            duanxin1 = (RelativeLayout) getView().findViewById(R.id.duanxin1);
            duanxin2 = (RelativeLayout) getView().findViewById(R.id.duanxin2);
            duanxin3 = (RelativeLayout) getView().findViewById(R.id.duanxin3);
            nameText.setText(name);
            duanxin1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startAC("制造业是指机械工业时代对制造资源（物料、能源、设备、工具、资金、技术、信息和人力等），按照市场要求，通过制造过程，转化为可供人们使用和利用的大型工具、工业品与生活消费产品的行业。然而，制造业也有自己面临的难题，oa系统能为制造业解决什么难题？我们看一下制造业所面临");
                }
            });
            duanxin2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startAC("【铁路12306】用户注册或既有用户手机核验专用验证码：441368。如非本人直接访问12306，请停止操作，切勿将验证码提供给第三方。"
                            );
                }
            });  duanxin3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startAC("恭喜您成功参与北京移动APP”翻牌子”活动，获得10MB奖励，请您尽快登录北京移动APP，到“我的-我的卡券”领兑，过期则失效哦！http://dx.10086.cn/MBf6R7j"
                            );
                }
            });
            phone1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhone("15625669598");
                }
            });
            phone2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhone("16895896847");
                }
            });
            phone3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhone("18547958463");
                }
            });
            phone4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhone("15648975896");
                }
            });
            phone5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhone("14869325479");
                }
            });
            tableLayout = (TableLayout) getView().findViewById(R.id.tableLayout);
            showSMS();
            tableLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtils.showLong( ""+v.toString());
                    Random random = new Random();
                    int po = random.nextInt(listcontent.size());
                    startAC(listcontent.get(po).toString());
                }
            });
        }
    }
    private void showSMS() {
        SmsHander smsHander = new SmsHander(this.getContext());

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

            String smsTitle ="_______________________________________" +"\n"+ strAddress + "\t\t" + strDate;
            String smsBody = strBody + "\n";
            Log.i("tableRow", smsTitle + smsBody);
            TextView tline = new TextView(this.getContext());
            tline.setText("_______________________________________");
            tableLayout.addView(tline, new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            // title Row
            TableRow trTitle = new TableRow(this.getContext());
//            trTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView tvTitle = new TextView(this.getContext());
            tvTitle.setText(smsTitle);
            tvTitle.getPaint().setFakeBoldText(true); // 加粗字体
            tvTitle.setTextColor(Color.RED);
            tvTitle.setTextSize(16.0f);
            tvTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            trTitle.addView(tvTitle);
//            tableLayout.addView(trTitle, new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            // body Row
            TableRow trBody = new TableRow(this.getContext());
            trBody.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView tvBody = new TextView(this.getContext());
            tvBody.setText(smsTitle + "\n" +smsBody);
            tvBody.getPaint().setFakeBoldText(false);
            tvBody.setTextSize(14.0f);
            tvBody.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            trBody.addView(tvBody);
            tableLayout.addView(trBody, new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
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
                listcontent.add(cursor.getString(cursor.getColumnIndex("body")));
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
            Cursor cur = null;
            if (Build.VERSION.SDK_INT >= 23) {
                //1. 检测是否添加权限   PERMISSION_GRANTED  表示已经授权并可以使用
                if (ContextCompat.checkSelfPermission(this.context, READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    //手机为Android6.0的版本,权限未授权去i请授权
                    //2. 申请请求授权权限
                    //1. Activity
                    // 2. 申请的权限名称
                    // 3. 申请权限的 请求码
                    ActivityCompat.requestPermissions(DiscoveryMvpFragment.this.getActivity(), new String[]
                            {READ_SMS,RECEIVE_SMS,SEND_SMS,RECEIVE_WAP_PUSH//通话记录
                            }, 1005);
                } else {//手机为Android6.0的版本,权限已授权可以使用
                    // 执行下一步
                    cur = getSMSInPhone(); // 获取短信（游标）

                }
            } else {//手机为Android6.0以前的版本，可以使用
                cur = getSMSInPhone(); // 获取短信（游标）
            }
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
    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }
    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    private void getPersimmionInfoSms() {
        if (Build.VERSION.SDK_INT >= 23) {
            //1. 检测是否添加权限   PERMISSION_GRANTED  表示已经授权并可以使用
            if (ContextCompat.checkSelfPermission(this.getContext(), READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                //手机为Android6.0的版本,权限未授权去i请授权
                //2. 申请请求授权权限
                //1. Activity
                // 2. 申请的权限名称
                // 3. 申请权限的 请求码
                ActivityCompat.requestPermissions(this.getActivity(), new String[]
                        {READ_SMS,RECEIVE_SMS,SEND_SMS,RECEIVE_WAP_PUSH//通话记录
                        }, 1005);
            } else {//手机为Android6.0的版本,权限已授权可以使用
                // 执行下一步
//                startActivity(new Intent(this.getContext(), smsRead4.class));


            }
        } else {//手机为Android6.0以前的版本，可以使用
//            startActivity(new Intent(this.getContext(), smsRead4.class));

        }

    }
    private void startAC(String con){
        Intent intent = new Intent();
        intent.setClass(this.getContext(),ShowXinActivity.class);
        intent.putExtra("cont",con);
        startActivity(intent);
    }
}
