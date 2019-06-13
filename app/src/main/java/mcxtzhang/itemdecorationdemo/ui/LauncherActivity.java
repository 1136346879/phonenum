package mcxtzhang.itemdecorationdemo.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.hexun.base.common.BaseConfig;
import com.hexun.base.common.CommonBase;
import com.hexun.base.common.NetConfig;
import com.hexun.base.common.RecoveryConfig;
import com.hexun.base.common.ThreadPoolConfig;
import com.hexun.base.router.RouterSheet;
import com.hexun.base.util.AppUtils;
import com.hexun.base.util.DeviceUtils;
import com.hexun.base.util.Utils;
import com.hexun.news.MeituanSelectCityActivity;
import com.hexun.news.SortListActivity;
import com.hexun.news.SwipeDelMenuActivity;
import com.hexun.news.WaveslideBarActivity;
import com.hexun.news.WeChatActivity;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.maning.updatelibrary.InstallUtils;
import com.maning.updatelibrary.InstallUtils.DownloadCallBack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import mcxtzhang.itemdecorationdemo.Constants;
import mcxtzhang.itemdecorationdemo.PermissionUtils;
import mcxtzhang.itemdecorationdemo.R;
import mcxtzhang.itemdecorationdemo.UrlEntity;

public class LauncherActivity extends AppCompatActivity {
    private RelativeLayout loading;
    private String url = "https://ws-oss-app.syrnight.com/bxvip/androidapk/hongcaizy.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);
        Utils.init(getApplication());
        //可选
        NetConfig netConfig = new NetConfig.Builder()
                .timeout(30)
                .retry(1)
                .cookieJar(new CookieJarImpl(new MemoryCookieStore()))
                .mode(CacheMode.NO_CACHE)
                .cacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .addCommonHeader("AppVersion", AppUtils.getAppVersionName())
                .addCommonHeader("deviceId", DeviceUtils.getAndroidID())
                .addCommonHeader("User-Agent", "")
                .build();

        //可选
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig.Builder()
                .corePoolSize(5)
                .maxPoolSize(10)
                .keepAliveTime(120)
                .enableOrderExecutor(true)
                .enableCancelExecutor(true)
                .build();


        //可选
        RecoveryConfig recoveryConfig = new RecoveryConfig.Builder()
                .rootPage(LauncherActivity.class)
                .build();
        //可选
        BaseConfig baseConfig = new BaseConfig.Builder()
                .netConfig(netConfig)
                .recoveryConfig(recoveryConfig)
                .threadPoolConfig(threadPoolConfig)
                .build();
        //必须
        CommonBase.init(getApplication(), baseConfig);

        loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        initViews();
        initCallBack();
        if (!PermissionUtils.isGrantSDCardReadPermission(this)) {
            PermissionUtils.requestSDCardReadPermission(this, 100);
        } else {
            doGet("http://www.1998002.com:8080/api/appinfo/getappinfo?appid=123456");

        }
        findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGet("http://www.1998002.com:8080/api/appinfo/getappinfo?appid=123456");
            }
        });
        findViewById(R.id.wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), WeChatActivity.class));

            }
        });

        findViewById(R.id.main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MainActivity.class));
            }
        });

        findViewById(R.id.swipe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SwipeDelMenuActivity.class));
            }
        });
        findViewById(R.id.select_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MeituanSelectCityActivity.class));
            }
        });
        findViewById(R.id.meituanCityList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MeituanSelectCityActivity.class));
            }
        });
        findViewById(R.id.WaveSideBarList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), WaveslideBarActivity.class));
            }
        });
        findViewById(R.id.sortList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SortListActivity.class));
            }
        });
    }

    private DownloadCallBack downloadCallBack;

    private TextView tv_progress;
    private TextView tv_info;
    private static final String TAG = "InstallUtils";
    private String apkDownloadPath;

    private void installApkUI() {
        InstallUtils.with(this)
                //必须-下载地址
                .setApkUrl(Constants.APK_URL_03)
                //非必须-下载保存的文件的完整路径+name.apk
                .setApkPath(Constants.APK_SAVE_PATH)
                //非必须-下载回调
                .setCallBack(downloadCallBack)
                //开始下载
                .startDownload();
    }

    private void initViews() {
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_info = (TextView) findViewById(R.id.tv_info);
    }

    private void initCallBack() {
        downloadCallBack = new DownloadCallBack() {
            @Override
            public void onStart() {
                loading.setClickable(false);
                Log.i(TAG, "InstallUtils---onStart");
                tv_progress.setText("0%");
                tv_info.setText("正在下载...");
            }

            @Override
            public void onComplete(String path) {
                Log.i(TAG, "InstallUtils---onComplete:" + path);
                apkDownloadPath = path;
                tv_progress.setText("100%");
                tv_info.setText("下载成功");

                //先判断有没有安装权限
                InstallUtils.checkInstallPermission(LauncherActivity.this, new InstallUtils.InstallPermissionCallBack() {
                    @Override
                    public void onGranted() {
                        //去安装APK
                        installApk(apkDownloadPath);
                    }

                    @Override
                    public void onDenied() {
                        //弹出弹框提醒用户
                        AlertDialog alertDialog = new AlertDialog.Builder(LauncherActivity.this)
                                .setTitle("温馨提示")
                                .setMessage("必须授权才能安装APK，请设置允许安装")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //打开设置页面
                                        InstallUtils.openInstallPermissionSetting(LauncherActivity.this, new InstallUtils.InstallPermissionCallBack() {
                                            @Override
                                            public void onGranted() {
                                                //去安装APK
                                                installApk(apkDownloadPath);
                                            }

                                            @Override
                                            public void onDenied() {
                                                //还是不允许咋搞？
                                                Toast.makeText(LauncherActivity.this, "不允许安装咋搞？强制更新就退出应用程序吧！", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .create();
                        alertDialog.show();
                    }
                });
            }


            @Override
            public void onLoading(long total, long current) {
                //内部做了处理，onLoading 进度转回progress必须是+1，防止频率过快
                Log.i(TAG, "InstallUtils----onLoading:-----total:" + total + ",current:" + current);
                int progress = (int) (current * 100 / total);
                tv_progress.setText(progress + "%");
            }

            @Override
            public void onFail(Exception e) {
                Log.i(TAG, "InstallUtils---onFail:" + e.getMessage());
                tv_info.setText("下载失败:" + e.toString());
            }

            @Override
            public void cancle() {
                Log.i(TAG, "InstallUtils---cancle");
                tv_info.setText("下载取消");
            }
        };
    }

    private void doGet(final String urlString) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);//樽到链培
//打开链接
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() == 200) {//若得到响应代码为200^不请求成功
                        InputStream is = httpURLConnection.getInputStream();//获故|艮务器返画的_入流
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                        StringBuffer sb = new StringBuffer();
                        String readaLine = "";
//读取输一
                        while ((readaLine = bufferedReader.readLine()) != null) {
                            sb.append(readaLine);
                        }
                        is.close();
                        bufferedReader.close();
                        httpURLConnection.disconnect();

                        UrlEntity urlEntity = new Gson().fromJson(sb.toString(), UrlEntity.class);
                        int status = urlEntity.getStatus();
                        show(status);
                    } else {
                    }
                } catch (MalformedURLException e) {
                } catch (IOException e) {
                }
            }

        }).start();
    }

    private void installApk(String path) {
        InstallUtils.installAPK(this, path, new InstallUtils.InstallCallBack() {
            @Override
            public void onSuccess() {
                //onSuccess：表示系统的安装界面被打开
                //防止用户取消安装，在这里可以关闭当前应用，以免出现安装被取消
                Toast.makeText(LauncherActivity.this, "正在安装程序", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Exception e) {
                tv_info.setText("安装失败:" + e.toString());
            }
        });
    }

    private void show(final int status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (status == 1) {
                    tv_progress.setVisibility(View.VISIBLE);
                    installApkUI();
                } else {
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            loading.setVisibility(View.GONE);
//                        }
//                    }, 2000);
                    finish();
                    ARouter.getInstance().build(RouterSheet.MAIN).navigation();
                }
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //禁止使用返回键返回到上一页,但是可以直接退出程序**
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK){
//            moveTaskToBack(true);
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

}
