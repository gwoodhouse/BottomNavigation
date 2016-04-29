package ca.gcastle.bottomnavigation.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment that appears inside of the ViewPager.
 *
 * Created by adammcneilly on 4/28/16.
 */
public class MyFragment extends Fragment {
    private String title;

    public static final String ARG_TITLE = "titleArg";

    public static MyFragment newInstance(String title) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = getArguments().getString(ARG_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        TextView titleView = (TextView) view.findViewById(R.id.fragment_title);
        titleView.setText(title);

        return view;
    }
}
