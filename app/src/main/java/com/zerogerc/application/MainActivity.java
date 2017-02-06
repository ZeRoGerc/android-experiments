package com.zerogerc.application;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setOnClickListener((view) -> {
            LayoutInflater inflater = LayoutInflater.from(this);
            View layout = inflater.inflate(R.layout.layout_popup, null);
            PopupWindow popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.popup_background_mtr));
            popupWindow.showAtLocation(view, Gravity.END, 0, 0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Fade fade = new Fade(Fade.IN);
                fade.setDuration(200);
                popupWindow.setEnterTransition(new ChangeBounds(this, null));
            }

            popupWindow.showAsDropDown(view);
        });

    }
}
