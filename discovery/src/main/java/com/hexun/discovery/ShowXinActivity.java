package com.hexun.discovery;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class ShowXinActivity extends Activity {

    private TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contentshow);
        content = findViewById(R.id.content);
        String  con = getIntent().getStringExtra("cont");
        content.setText(con);
    }
}
