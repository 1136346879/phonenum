package com.hexun.discovery;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Override
    public void initFragmentData() {

    }

    @Override
    public void initFragmentView() {
        if (getView() != null) {
            nameText = (TextView) getView().findViewById(R.id.nameText);
            nameText.setText(name);
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

}
