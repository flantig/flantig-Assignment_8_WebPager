package edu.temple.browseractivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PagerFragment extends Fragment {
    ViewPager viewPager;
    ArrayList<PageViewerFragment> pageviewlist;
    float thresholdOffset;
    FragmentAdapter fragAdapter ;
    fragmentFetch parentActivity;

    public PagerFragment() {
        // Required empty public constructor
    }

    public static PagerFragment newInstance(String param1, String param2) {
        PagerFragment newPager = new PagerFragment();

        return newPager;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("fragments_key", pageviewlist);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            pageviewlist = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable("fragments_key");
        } else {
            pageviewlist = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        viewPager = view.findViewById(R.id.pager);

        fragAdapter = new FragmentAdapter(getChildFragmentManager(), pageviewlist);
        viewPager.setAdapter(fragAdapter);

        if (savedInstanceState != null) {
            viewPager.setAdapter(fragAdapter);
            viewPager.getAdapter().notifyDataSetChanged();
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                parentActivity.updateText(pageviewlist.get(position).webber.getUrl());
                parentActivity.updatingPageView(pageviewlist.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        parentActivity.intentListener();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PagerFragment.fragmentFetch) {
            parentActivity = (PagerFragment.fragmentFetch) context;

        } else {
            throw new RuntimeException("You must implement ButtonClickInterface to attach this fragment");
        }
    }

    public void addingPageView(PageViewerFragment fragment) {

            pageviewlist.add(fragment);
            fragAdapter.notifyDataSetChanged();

    }

    public interface fragmentFetch {
        void addingPageView(PageViewerFragment fragment);
        void updatingPageView(PageViewerFragment newCurrentView);
        void updateText(String url);
        void intentListener();
    }

}
