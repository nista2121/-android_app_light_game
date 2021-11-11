package com.zybooks.lightsout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
    }

    public static final String EXTRA_COLOR = "com.zybooks.lightsout.color";

    public void onColorSelected(View view) {
        int colorId = R.color.black;
        switch (view.getId()) {
            case R.id.radio_red:
                colorId = R.color.red;
                break;
            case R.id.radio_orange:
                colorId = R.color.orange;
                break;
            case R.id.radio_yellow:
                colorId = R.color.yellow;
                break;
            case R.id.radio_green:
                colorId = R.color.green;
                break;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_COLOR, colorId);
        setResult(RESULT_OK, intent);
        finish();
    }

}