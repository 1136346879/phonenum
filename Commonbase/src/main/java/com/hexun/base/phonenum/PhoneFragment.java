package com.hexun.base.phonenum;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hexun.base.R;
import com.hexun.base.router.RouterSheet;
import com.hexun.base.ui.BaseMvpFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class PhoneFragment extends BaseMvpFragment {
    private ListView slvItem;
    private List<Map<String, String>> dataList;
    private ContentResolver resolver;
    private Uri callUri = CallLog.Calls.CONTENT_URI;
    private String[] columns = {CallLog.Calls.CACHED_NAME// 通话记录的联系人
            , CallLog.Calls.NUMBER// 通话记录的电话号码
            , CallLog.Calls.DATE// 通话记录的日期
            , CallLog.Calls.DURATION// 通话时长
            , CallLog.Calls.TYPE};// 通话类型}
    private ImpowerAdapter impowerAdapter;
    private String mobile;//被授权人电话号码

    @Override
    public void initFragmentData() {

    }

    @Override
    public void initFragmentView() {
        initView();
        getPersimmionInfo();
    }
    private void initView() {
        slvItem = (ListView)getView(). findViewById(R.id.slv_impower_item);
    }

    //**************授权信息
    private void getPersimmionInfo() {
        if (Build.VERSION.SDK_INT >= 23) {
            //1. 检测是否添加权限   PERMISSION_GRANTED  表示已经授权并可以使用
            if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                //手机为Android6.0的版本,权限未授权去i请授权
                //2. 申请请求授权权限
                //1. Activity
                // 2. 申请的权限名称
                // 3. 申请权限的 请求码
                ActivityCompat.requestPermissions(this.getActivity(), new String[]
                        {Manifest.permission.READ_CALL_LOG//通话记录
                        }, 1005);
            } else {//手机为Android6.0的版本,权限已授权可以使用
                // 执行下一步
                initContacts();
            }
        } else {//手机为Android6.0以前的版本，可以使用
            initContacts();
        }

    }

    private void initContacts() {
        dataList = getDataList();
        impowerAdapter = new ImpowerAdapter(this.getContext(), dataList);
        slvItem.setAdapter(impowerAdapter);
        slvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mobile = dataList.get(position).get("number")
                        .replaceAll(" ", "")
                        .replaceAll("\\+", "")
                        .replaceAll("-", "");
                String nameStr = dataList.get(position).get("name");
//                    Toast.makeText(PhoneHistory.this, "=mobile:" + mobile + "=nameStr:" + nameStr, Toast.LENGTH_SHORT).show();
                callPhone(mobile);
            }
        });
    }

    /**
     * 读取数据
     *
     * @return 读取到的数据
     */
    private List<Map<String, String>> getDataList() {
        // 1.获得ContentResolver
        resolver = this.getActivity().getContentResolver();
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
        }
        // 2.利用ContentResolver的query方法查询通话记录数据库
        /**
         * @param uri 需要查询的URI，（这个URI是ContentProvider提供的）
         * @param projection 需要查询的字段
         * @param selection sql语句where之后的语句
         * @param selectionArgs ?占位符代表的数据
         * @param sortOrder 排序方式
         */
        Cursor cursor = resolver.query(callUri, // 查询通话记录的URI
                columns
                , null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );
        // 3.通过Cursor获得数据
        List<Map<String, String>> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong));
            String time = new SimpleDateFormat("HH:mm").format(new Date(dateLong));
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            String dayCurrent = new SimpleDateFormat("dd").format(new Date());
            String dayRecord = new SimpleDateFormat("dd").format(new Date(dateLong));
            String typeString = "";
            switch (type) {
                case CallLog.Calls.INCOMING_TYPE:
                    //"打入"
                    typeString = "打入";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    //"打出"
                    typeString = "打出";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    //"未接"
                    typeString = "未接";
                    break;
                default:
                    break;
            }
            if (MobileUtil.isMobileNO(number)) {
                String dayString = "";
                if ((Integer.parseInt(dayCurrent)) == (Integer.parseInt(dayRecord))) {
                    //今天
                    dayString = "今天";
                } else if ((Integer.parseInt(dayCurrent) - 1) == (Integer.parseInt(dayRecord))) {
                    //昨天
                    dayString = "昨天";
                } else {
                    //前天
                    dayString = "前天及以前。。。";
                }
                long day_lead = TimeStampUtil.compareDayTime(date);
                if (day_lead < 100) {//只显示48小时以内通话记录，防止通     //话记录数据过多影响加载速度
                    Map<String, String> map = new HashMap<>();
                    //"未备注联系人"
                    map.put("name", (name == null) ? "未备注联系人" : name);//姓名
                    map.put("number", number);//手机号
                    map.put("date", date);//通话日期
                    // "分钟"
                    map.put("duration", (duration / 60) + "分钟");//时长
                    map.put("type", typeString);//类型
                    map.put("time", time);//通话时间
                    map.put("day", dayString);//
                    map.put("time_lead", TimeStampUtil.compareTime(date));//
                    list.add(map);
                } else {
                    return list;
                }
            }
        }
        return list;
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
    @Override
    public int getFragmentViewId() {
        return R.layout.activity_phonehisty;
    }
}
