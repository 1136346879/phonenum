package com.hexun.news;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hexun.base.phonenum.PhoneHistory;
import com.hexun.base.router.RouterSheet;
import com.hexun.base.ui.BaseMvpFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.RECEIVE_WAP_PUSH;
import static android.Manifest.permission.SEND_SMS;

/**
 * @author yangyi 2017年10月19日13:37:01
 */
@Route(path = RouterSheet.NEWS)
public class NewsMvpFragment extends BaseMvpFragment {

    @Autowired
    String name;

    TextView nameText;
    private Button wx;
    private Button swipe;
    private Button meituanCityList;
    private Button waveSideBarList;
    private Button sortList;
    List list_path =new ArrayList();


    List  list_title = new ArrayList();
    private Banner banner;
    private WrapContentListView listview;
    private List listNum;

    @Override
    public void initFragmentData() {

    }

    @Override
    public void initFragmentView() {
//        list_path.add("http://h.hiphotos.baidu.com/image/pic/item/b999a9014c086e064a76b12f0f087bf40bd1cbfc.jpg");
        list_path.add("http://img3.imgtn.bdimg.com/it/u=2758743658,581437775&fm=15&gp=0.jpg");
        list_path.add("https://pics4.baidu.com/feed/0df3d7ca7bcb0a4691b736ae34be0f206960aff6.jpeg?token=118f77eb93c1af7308fcfe1742eaf8a8&s=390A26DB4C103C4D5C8E7E3903005054");
        list_path.add("https://pics1.baidu.com/feed/42a98226cffc1e176e69050522570f07728de979.jpeg?token=64511a785dd2bcfa21d0a8adada70b08&s=13E007AA5A32E2D6562964AD03007002");
//        list_path.add("http://h.hiphotos.baidu.com/image/pic/item/b999a9014c086e064a76b12f0f087bf40bd1cbfc.jpg");
//        list_path.add("http://h.hiphotos.baidu.com/image/pic/item/b999a9014c086e064a76b12f0f087bf40bd1cbfc.jpg");
        list_title.add("微信通讯录界面");
//        list_title.add("侧滑删除通讯录");
        list_title.add("波浪式选择通讯录");
        list_title.add("排序和顶部浮窗通讯录");
//        list_title.add("爱学习");
//        list_title.add("爱人生");
        if (getView() != null) {
            nameText = (TextView) getView().findViewById(R.id.nameText);
            wx = (Button) getView().findViewById(R.id.wx);
            swipe = (Button) getView().findViewById(R.id.swipe);
            listview = (WrapContentListView) getView().findViewById(R.id.list);
            meituanCityList = (Button) getView().findViewById(R.id.meituanCityList);
            waveSideBarList = (Button) getView().findViewById(R.id.WaveSideBarList);
            sortList = (Button) getView().findViewById(R.id.sortList);
            banner = (Banner) getView().findViewById(R.id.homeBanner);
            nameText.setText(name);
            wx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), WeChatActivity.class));
                }
            });   /**
             * 第三方的轮播图
             */
            banner//设置banner样式
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                    //设置图片加载器
                    .setImageLoader(new GlideImageLoader())
                    //设置图片集合
                    .setImages(list_path)
                    //设置banner动画效果
                    .setBannerAnimation(Transformer.Default)
                    //设置标题集合（当banner样式有显示title时）
                    .setBannerTitles(list_title)
                    //设置轮播时间
                    .setDelayTime(1500)
                    //设置指示器位置（当banner模式中有指示器时）
                    .setIndicatorGravity(BannerConfig.CENTER)
                    //banner设置方法全部调用完毕时最后调用
                    .start();
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if(0 == position){
                        startActivity(new Intent(NewsMvpFragment.this.getContext(), WeChatActivity.class));
                    }else  if(1 == position){
                        startActivity(new Intent(NewsMvpFragment.this.getContext(), SwipeDelMenuActivity.class));

                    }else  if(2 == position){
                        startActivity(new Intent(NewsMvpFragment.this.getContext(), WaveslideBarActivity.class));
                    }else  if(3 == position){
                        startActivity(new Intent(NewsMvpFragment.this
                                .getContext(), SortListActivity.class));
                    }
                }
            });
            swipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), SwipeDelMenuActivity.class));

                }
            });
            meituanCityList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), MeituanSelectCityActivity.class));

                }
            });
            waveSideBarList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), WaveslideBarActivity.class));
//                    getPersimmionInfoPhoneNUm();
                }
            });
            sortList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), SortListActivity.class));
//                    getPersimmionInfoPhoneNUm();
//                    getPersimmionInfoSms();

                }
            });

        }
//        getPersimmionInfoPhoneNUm();
        showLIteView();
        getPersimmionInfoLOgo();
    }

    private void showLIteView() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                  ArrayList<HashMap<String, String>> list = getPeopleInPhone2();
                NewsCallAdapter adapter = new NewsCallAdapter(NewsMvpFragment.this.getContext(),
                        list);
//                SimpleAdapter adapter = new SimpleAdapter(
//                       NewsMvpFragment. this.getContext(),
//                        list,
//                        android.R.layout.simple_list_item_2,
//                        new String[] {"peopleName", "phoneNum"},
//                        new int[]{android.R.id.text1, android.R.id.text2}
//                );
                listview.setAdapter(adapter);
            }}).start();


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callPhone(listNum.get(position).toString());
            }
        });
//        getPersimmionInfoSms();
    }

    public ArrayList<HashMap<String, String>> getPeopleInPhone2(){

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        listNum = new ArrayList();

        Cursor cursor = this.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);		// 获取手机联系人
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();

            int indexPeopleName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME); 	// people name
            int indexPhoneNum = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);	 		// phone number

            String strPeopleName = cursor.getString(indexPeopleName);
            String strPhoneNum = cursor.getString(indexPhoneNum);

            map.put("peopleName", strPeopleName);
            map.put("phoneNum", strPhoneNum);
            list.add(map);
            if(list.size()>=100){
                break;
            }
            listNum.add(strPhoneNum);
        }
        if(!cursor.isClosed()){
            cursor.close();
            cursor = null;
        }

        return list;
      }


//        private void getPersimmionInfoPhoneNUm() {
//            if (Build.VERSION.SDK_INT >= 23) {
//                //1. 检测是否添加权限   PERMISSION_GRANTED  表示已经授权并可以使用
//                if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                    //手机为Android6.0的版本,权限未授权去i请授权
//                    //2. 申请请求授权权限
//                    //1. Activity
//                    // 2. 申请的权限名称
//                    // 3. 申请权限的 请求码
//                    ActivityCompat.requestPermissions(this.getActivity(), new String[]
//                            {Manifest.permission.READ_CONTACTS//通话记录
//                            }, 1005);
//                } else {//手机为Android6.0的版本,权限已授权可以使用
//                    // 执行下一步
//                    showLIteView();
//                }
//            } else {//手机为Android6.0以前的版本，可以使用
//                showLIteView();
//            }
//
//        }
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
    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_news;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //**************授权信息
    private void getPersimmionInfoLOgo() {
        if (Build.VERSION.SDK_INT >= 23) {
            //1. 检测是否添加权限   PERMISSION_GRANTED  表示已经授权并可以使用
            if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                //手机为Android6.0的版本,权限未授权去i请授权
                //2. 申请请求授权权限
                //1. Activity
                // 2. 申请的权限名称
                // 3. 申请权限的 请求码
                ActivityCompat.requestPermissions(NewsMvpFragment.this.getActivity(), new String[]
                        {Manifest.permission.READ_CALL_LOG//通话记录
                        }, 1005);
            } else {//手机为Android6.0的版本,权限已授权可以使用
                // 执行下一步
            }
        } else {//手机为Android6.0以前的版本，可以使用
        }

    }
}
