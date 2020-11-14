package edu.temple.browseractivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.GOBack, PageViewerFragment.Wave, PagerFragment.fragmentFetch, BrowserControlFragment.makeNew, PageListFragment.listJam {
    PageControlFragment pagec = new PageControlFragment();
    PageViewerFragment pagev = new PageViewerFragment();
    BrowserControlFragment pageb = new BrowserControlFragment();
    PageListFragment pagel = new PageListFragment();
    PagerFragment pager = new PagerFragment();
    ArrayList<String> titleKey = new ArrayList<>();
    Fragment tempFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState != null) {
            titleKey = (ArrayList<String>) savedInstanceState.getSerializable("key_titles");
        }

        if ((tempFrag = fm.findFragmentById(R.id.page_control)) instanceof PageControlFragment) {
            pagec = (PageControlFragment) tempFrag;
        } else {
            pagec = new PageControlFragment();
            fm.beginTransaction()
                    .add(R.id.page_control, pagec)
                    .commit();
        }


        if (findViewById(R.id.browser_control) != null) {
            if ((tempFrag = fm.findFragmentById(R.id.browser_control)) instanceof BrowserControlFragment) {
                pageb = (BrowserControlFragment) tempFrag;
            } else {
                pageb = new BrowserControlFragment();
                fm.beginTransaction()
                        .add(R.id.browser_control, pageb)
                        .commit();
            }
        }


        if ((tempFrag = fm.findFragmentById(R.id.page_viewer)) instanceof PagerFragment) {
            pager = (PagerFragment) tempFrag;
        } else {
            pager = new PagerFragment();
            fm.beginTransaction()
                    .add(R.id.page_viewer, pager)
                    .commit();
        }

        if (findViewById(R.id.activity_main_land) != null) {
            if ((tempFrag = fm.findFragmentById(R.id.page_list)) instanceof PageListFragment) {
                pagel = (PageListFragment) tempFrag;
            } else {
                pagel = new PageListFragment();
                fm.beginTransaction()
                        .add(R.id.page_list, pagel)
                        .commit();
            }
        }


    }

    @Override
    public void createFragment() {

        pagev = new PageViewerFragment();
        titleKey.add("New tab");
        pagev.index = titleKey.size() - 1;
        if (findViewById(R.id.activity_main_land) != null) {
            pagel.UpdateURL(titleKey);
        }
        addingPageView(pagev);
    }


    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("key_titles", titleKey);
    }

    @Override
    public void addingPageView(PageViewerFragment fragment) {
        pager.addingPageView(fragment);
    }

    @Override
    public void goBack() {
        if (pager.pageviewlist.size() > 0) {
            pagev.WebViewGoBack();
        }
    }

    @Override
    public void listerJam(int index) {
        pager.viewPager.setCurrentItem(index);
    }

    @Override
    public void goForward() {
        if (pager.pageviewlist.size() > 0) {
            pagev.WebViewGoForward();
        }
    }

    @Override
    public void loadURL(String url) {
        if (pager.pageviewlist.size() > 0) {
            pagev.WebViewLoad(url);
        }
    }

    @Override
    public void updateText(String url) {
        pagec.UpdateURL(url);
    }

    @Override
    public void updateTitle(String title, int index) {
        titleKey.set(index, title);
        if (findViewById(R.id.activity_main_land) != null) {
            pagel.UpdateURL(titleKey);
        }
    }
}
