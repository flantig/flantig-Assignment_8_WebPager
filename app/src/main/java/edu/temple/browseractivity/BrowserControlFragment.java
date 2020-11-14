package edu.temple.browseractivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class BrowserControlFragment extends Fragment {
    Button newPage;
    makeNew parentActivityInterface;


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

        newPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivityInterface.createFragment();
            }
        });

        return browControl;
    }

    interface makeNew {
        void createFragment();
    }
}
