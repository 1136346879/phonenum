package com.hexun.discovery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

public class ShowXinActivity extends Activity {

    private TextView content;
    private TextView reply;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contentshow);
        content = findViewById(R.id.content);
        reply = findViewById(R.id.reply);
        String  con = getIntent().getStringExtra("cont");
        content.setText(con);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentFinalMessage = new Intent(Intent.ACTION_VIEW);


                intentFinalMessage.setType("vnd.android-dir/mms-sms");

                startActivity(intentFinalMessage);
            }
        });
    }
}
