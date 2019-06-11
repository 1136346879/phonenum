package com.hexun.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hexun.base.router.RouterSheet;
import com.hexun.base.ui.BaseMvpFragment;

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

    @Override
    public void initFragmentData() {

    }

    @Override
    public void initFragmentView() {
        if (getView() != null) {
            nameText = (TextView) getView().findViewById(R.id.nameText);
            wx = (Button) getView().findViewById(R.id.wx);
            swipe = (Button) getView().findViewById(R.id.swipe);
            meituanCityList = (Button) getView().findViewById(R.id.meituanCityList);
            waveSideBarList = (Button) getView().findViewById(R.id.WaveSideBarList);
            sortList = (Button) getView().findViewById(R.id.sortList);
            nameText.setText(name);
            wx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), WeChatActivity.class));


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
                }
            });
            sortList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), SortListActivity.class));
                }
            });

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

}
