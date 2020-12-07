package edu.temple.browseractivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.GOBack, PageViewerFragment.Wave, PagerFragment.fragmentFetch, BrowserControlFragment.makeNew, PageListFragment.listJam {
    PageControlFragment pagec = new PageControlFragment();
    PageViewerFragment pagev = new PageViewerFragment();
    BrowserControlFragment pageb = new BrowserControlFragment();
    PageListFragment pagel = new PageListFragment();
    PagerFragment pager = new PagerFragment();
    ArrayList<String> titleKey = new ArrayList<>();
    FileInputStream stream;
    BufferedReader reader;
    Fragment tempFrag;
    String intentURL;
    File urlFile;
    File titleFile;
    private static final int REQ_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String action = intent.getAction();
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



    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                int urlPos = data.getIntExtra("urlPOS", 0);

                loadURL(pageb.bookmarkURLs.get(urlPos));


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share && pagev.webber != null) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, pagev.webber.getUrl());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
        return true;
    }

    @Override
    public void intentListener() {
        Intent intent = getIntent();
        String url = intent.getDataString();

        if (url != null) {
            if (url.startsWith("http://")) {
                url = url.replace("http://", "https://");
            }
            createFragment();
            intentURL = url;
            Toast toast = Toast.makeText(this, url, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void checkStoredIntent(int index){
        if(intentURL != null){
            pager.pageviewlist.get(index).webber.loadUrl(intentURL);
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
        pager.viewPager.setCurrentItem(titleKey.size() - 1);
    }

    @Override
    public void startBookmarkActivity() {
        Intent intent = new Intent(BrowserActivity.this, BookmarkActivity.class);
        intent.putExtra("titles", pageb.bookmarkTitles);
        intent.putExtra("urls", pageb.bookmarkURLs);
        startActivityForResult(intent, REQ_CODE);
    }

    public void writingToFile(String filename, String toBeWritten) throws IOException {
        File file = new File(getFilesDir(), filename);
        FileWriter writer = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.append(toBeWritten);
        bw.newLine();
        bw.flush();
        bw.close();
    }

    @Override
    public void addBookmark() throws IOException {
        if (pagev.webber != null) {

            writingToFile("url.txt", pagev.webber.getUrl());
            writingToFile("title.txt", pagev.webber.getTitle());
            pageb.UpdateURL("url.txt", "title.txt");


        }

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
    public void updatingPageView(PageViewerFragment newCurrentView) {
        pagev = newCurrentView;
    }

    @Override
    public void updatingPageViewList(int pos) {
        pagev = pager.pageviewlist.get(pos);
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
