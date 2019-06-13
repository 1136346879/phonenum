package com.hexun.discovery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hexun.base.router.RouterSheet;
import com.hexun.base.ui.BaseMvpFragment;

/**
 * @author yangyi 2017年10月19日13:37:01
 */
@Route(path = RouterSheet.DISCOVERY)
public class DiscoveryMvpFragment extends BaseMvpFragment {

    @Autowired
    String name;

    TextView nameText;
    private TextView phone1;
    private TextView phone2;
    private TextView phone3;
    private TextView phone4;
    private TextView phone5;

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
            nameText.setText(name);
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
}
