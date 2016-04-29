package ca.gcastle.bottomnavigation.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Fragment that appears inside of the ViewPager.
 *
 * Created by adammcneilly on 4/28/16.
 */
public class BlankFragment extends Fragment {

    public static final String ARG_TITLE = "titleArg";
    public static final String ARG_INDEX = "indexArg";

    public static BlankFragment newInstance(String title, int index) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getArguments().getString(ARG_TITLE));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerViewAdapter(getArguments().getInt(ARG_INDEX)));

        return view;
    }

    private static class FakeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public FakeViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    private static class RecyclerViewAdapter extends RecyclerView.Adapter<FakeViewHolder> {

        int[] drawables;
        public RecyclerViewAdapter(int index) {
            if(index == 0) {
                drawables = new int[] {
                        R.drawable.animal1,
                        R.drawable.animal2,
                        R.drawable.animal3
                };
            } else if(index == 1) {
                drawables = new int[] {
                        R.drawable.car1,
                        R.drawable.car2,
                        R.drawable.car3
                };
            } else {
                drawables = new int[] {
                        R.drawable.plant1,
                        R.drawable.plant2,
                        R.drawable.plant3
                };
            }
        }

        @Override
        public FakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FakeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_cardedimage, parent, false));
        }

        @Override
        public void onBindViewHolder(FakeViewHolder holder, int position) {
            holder.imageView.setImageResource(drawables[position % 3]);
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }
}
