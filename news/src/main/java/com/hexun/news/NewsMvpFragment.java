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
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void initFragmentData() {

    }

    @Override
    public void initFragmentView() {
        list_path.add("http://h.hiphotos.baidu.com/image/pic/item/b999a9014c086e064a76b12f0f087bf40bd1cbfc.jpg");
        list_path.add("http://h.hiphotos.baidu.com/image/pic/item/b999a9014c086e064a76b12f0f087bf40bd1cbfc.jpg");
        list_path.add("http://h.hiphotos.baidu.com/image/pic/item/b999a9014c086e064a76b12f0f087bf40bd1cbfc.jpg");;
        list_path.add("http://h.hiphotos.baidu.com/image/pic/item/b999a9014c086e064a76b12f0f087bf40bd1cbfc.jpg");
        list_path.add("http://h.hiphotos.baidu.com/image/pic/item/b999a9014c086e064a76b12f0f087bf40bd1cbfc.jpg");
        list_path.add("http://h.hiphotos.baidu.com/image/pic/item/b999a9014c086e064a76b12f0f087bf40bd1cbfc.jpg");
        list_title.add("爱党");
        list_title.add("爱祖国");
        list_title.add("爱人民");
        list_title.add("爱妹子");
        list_title.add("爱学习");
        list_title.add("爱人生");
        if (getView() != null) {
            nameText = (TextView) getView().findViewById(R.id.nameText);
            wx = (Button) getView().findViewById(R.id.wx);
            swipe = (Button) getView().findViewById(R.id.swipe);
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
