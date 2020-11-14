package edu.temple.browseractivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<PageViewerFragment> myfraglist;

    public FragmentAdapter(@NonNull FragmentManager fm, ArrayList<PageViewerFragment> arrayList){
        super(fm);
        myfraglist = arrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int pos){
        return myfraglist.get(pos);
    }

    @Override
    public int getCount() {
        return myfraglist.size();
    }
}
