package com.scurab.android.uitorsample;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.scurab.android.uitor.extract2.DetailExtractor;
import com.scurab.android.uitorsample.extract.CustomTextViewExtractor;
import com.scurab.android.uitorsample.widget.CustomTextView;

public class MainActivity extends BaseActivity {

    private SlidingPaneLayout mPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //register own extractor for specific type
        DetailExtractor.registerExtractor(CustomTextView.class, new CustomTextViewExtractor());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mPaneLayout = findViewById(R.id.sliding_pane_layout);
        Resources res = getResources();
        mPaneLayout.setParallaxDistance(res.getDimensionPixelSize(R.dimen.left_menu_parallax_distance));
        mPaneLayout.setSliderFadeColor(Color.TRANSPARENT);
        //needs to be posted or invalid size
        mPaneLayout.post(() -> mPaneLayout.openPane());

        View v = findViewById(R.id.txt_sample);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        v.setAnimation(animation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sample_menu, menu);
        return true;
    }

    @Override
    public void openFragment(Fragment f, boolean add) {
        super.openFragment(f, add);
        mPaneLayout.closePane();
    }

    @Override
    public void onBackPressed() {
        if (!mPaneLayout.isOpen()) {
            mPaneLayout.openPane();
            return;
        }
        super.onBackPressed();
    }
}
