package com.lingjuan.app.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jaeger.library.StatusBarUtil;
import com.lingjuan.app.R;
import com.lingjuan.app.base.BaseActivity;
import com.lingjuan.app.constant.Constant;
import com.lingjuan.app.utils.DialogUtil;
import com.lingjuan.app.utils.ToastManage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 抽奖页面
 * @author: TaoHui
 * data: 2019/2/26 15:04
 */
public class DrawActivity extends BaseActivity {

    @BindView(R.id.mWebView)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private JsInterface jsInterface;
    private boolean conduct = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageView(this, ContextCompat.getColor(this, R.color.error_color_material_dark), null);
        setContentView(R.layout.activity_draw);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        jsInterface = new JsInterface();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebSettings webSettings = mWebView.getSettings();
        // 设置android下容许执行js的脚本
        webSettings.setJavaScriptEnabled(true);
        //允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //添加JS交互 第一个参数是类  里面写上回调的方法  第二个参数是js调用的用户名
        mWebView.addJavascriptInterface(jsInterface, "jsApp");
        //加载地址
        mWebView.loadUrl(Constant.HTTP_DRAW);
        toolbar.setNavigationOnClickListener(v -> {
            if (!conduct) {
                ToastManage.showText(getActivity(), "大转盘正在进行,请停止后在关闭");
                return;
            }
            finish();
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if(title != null){
                    //获取网页标题
                    toolbar.setTitle(String.valueOf(title));
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_draw;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }


    private class JsInterface {
        @JavascriptInterface
        public void getdrawResults(String str) {
            conduct = true;
            DialogUtil.showDialog(getActivity(), str, "知道了", null);
        }

        @JavascriptInterface
        public void getStare() {
            conduct = false;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!conduct) {
                ToastManage.showText(getActivity(), "大转盘正在进行,请停止后在关闭");
            }else {
                finish();
            }
        }
        return false;
    }
}
