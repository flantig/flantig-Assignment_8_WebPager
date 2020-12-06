package edu.temple.browseractivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class BrowserControlFragment extends Fragment {

    makeNew parentActivityInterface;
    ArrayList<String> bookmarkTitles = new ArrayList<>();
    ArrayList<String> bookmarkURLs = new ArrayList<>();
    FileInputStream stream;
    BufferedReader reader;
    File urlFile;
    File titleFile;


    public BrowserControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PageViewerFragment.Wave) {
            parentActivityInterface = (BrowserControlFragment.makeNew) context;

        } else {
            throw new RuntimeException("You must implement ButtonClickInterface to attach this fragment");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View browControl = inflater.inflate(R.layout.fragment_browser_control, container, false);
        Button newPageButton = browControl.findViewById(R.id.new_page_but);
        Button bookmarkList = browControl.findViewById(R.id.openBookmarks);
        Button addBookmark = browControl.findViewById(R.id.savePage);
        urlFile = getActivity().getBaseContext().getFileStreamPath("url.txt");
        titleFile = getActivity().getBaseContext().getFileStreamPath("title.txt");

        newPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivityInterface.createFragment();
            }
        });

        bookmarkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File url = new File(getActivity().getFilesDir(), "url.txt");
                File title = new File(getActivity().getFilesDir(), "title.txt");
                readFromFile(bookmarkURLs, url);
                readFromFile(bookmarkTitles, title);

                parentActivityInterface.startBookmarkActivity();
            }
        });

        addBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    parentActivityInterface.addBookmark();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        File url = new File(getActivity().getFilesDir(), "url.txt");
        File title = new File(getActivity().getFilesDir(), "title.txt");
        readFromFile(bookmarkURLs, url);
        readFromFile(bookmarkTitles, title);

        return browControl;
    }

    public void readFromFile(ArrayList<String> dataHolder, File file) {
        if (file.exists()) {

            if (file.length() != 0) {
                try {
                    dataHolder.clear();
                    stream = new FileInputStream(file);
                    reader = new BufferedReader(new InputStreamReader(stream));
                    String line = reader.readLine();
                    dataHolder.add(line);
                    while (line != null) {
                        line = reader.readLine();
                        dataHolder.add(line);
                    }
                    reader.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void UpdateURL(String urlString, String titleString) {
        File url = new File(getActivity().getFilesDir(), urlString);
        File title = new File(getActivity().getFilesDir(), titleString);
        bookmarkTitles.clear();
        bookmarkURLs.clear();
        readFromFile(bookmarkURLs, url);
        readFromFile(bookmarkTitles, title);
//        Toast toast = Toast.makeText(getActivity(), bookmarkTitles.get(bookmarkTitles.size() - 1), Toast.LENGTH_LONG);
//        toast.show();
    }

    interface makeNew {
        void createFragment();

        void startBookmarkActivity();

        void addBookmark() throws IOException;
    }
}
