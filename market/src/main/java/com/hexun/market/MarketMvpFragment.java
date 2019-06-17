package com.hexun.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hexun.base.dateyearmonthday.AttendviewActivity;
import com.hexun.base.router.RouterSheet;
import com.hexun.base.ui.BaseMvpFragment;

/**
 * @author yangyi 2017年10月19日13:37:01
 */
@Route(path = RouterSheet.MARKET)
public class MarketMvpFragment extends BaseMvpFragment {

    @Autowired
    String name;

    TextView nameText;

    @Override
    public void initFragmentData() {

    }

    @Override
    public void initFragmentView() {
        if (getView() != null) {
            nameText = (TextView) getView().findViewById(R.id.nameText);
            nameText.setText(name);
            getView().findViewById(R.id.jianshe).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startWebviewAc("https://www.hiyd.com/","健身");
                }
            });  getView().findViewById(R.id.rili).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startWebviewAc("http://www.365rili.com/","日历");
                    startActivity(new Intent(MarketMvpFragment.this.getContext(), AttendviewActivity.class));
                }
            });  getView().findViewById(R.id.zanweikaitong).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MarketMvpFragment.this.getContext(),"暂未开通",1).show();
                }
            });  getView().findViewById(R.id.dingjiudin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startWebviewAc("http://hotel.elong.com/","酒店订阅");
                }
            });  getView().findViewById(R.id.shoujizhushou).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startWebviewAc("http://sj.360.cn/index.html","手机助手");
                }
            });  getView().findViewById(R.id.jiaoshui).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startWebviewAc("https://baike.baidu.com/item/%E4%BA%A4%E7%A8%8E/6626928?fr=aladdin","交税");
                }
            });  getView().findViewById(R.id.wifi).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startWebviewAc("http://www.bj-xscs.com/","wifi");
                }
            });  getView().findViewById(R.id.sougou).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startWebviewAc("https://pinyin.sogou.com/","搜狗");
                }
            });  getView().findViewById(R.id.chiji).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startWebviewAc("https://www.xunyou.com/sem/pubg/index.html?s=249680191&f=baidu","吃鸡游戏");
                }
            });  getView().findViewById(R.id.taobao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startWebviewAc("https://www.taobao.com/","淘宝");
                }
            });
        }
    }

    private void startWebviewAc(String url,String title){
        WebviewActivity.toWebActivity(this.getActivity(),url,title);
    }
    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_market;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }
}
