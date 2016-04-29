package ca.gcastle.bottomnavigation.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ca.gcastle.bottomnavigation.listeners.OnChildClickedListener;
import ca.gcastle.bottomnavigation.view.BottomNavigationView;

/**
 * Created by graeme.castle on 29/04/2016.
 */
public class CrossFadeActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private final String[] fragmentTitles = new String[] {
            "Home",
            "Search",
            "Help"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossfade);


        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnChildClickedListener(new OnChildClickedListener() {
            @Override
            public void onChildClicked(int child) {
                selectFragment(child);
            }
        });

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        if(savedInstanceState == null) {
            selectFragment(0);
            getSupportActionBar().setTitle("Home");
        }
    }

    private void selectFragment(int child) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content, BlankFragment.newInstance(fragmentTitles[child], child))
                .commit();
    }
}
