package ca.gcastle.bottomnavigation.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by graeme.castle on 29/04/2016.
 */
public class ChooseImplementationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_implementation);

        findViewById(R.id.chooseCrossfade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseImplementationActivity.this, CrossFadeActivity.class));
            }
        });

        findViewById(R.id.chooseViewPager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseImplementationActivity.this, ViewPagerActivity.class));
            }
        });
    }
}
