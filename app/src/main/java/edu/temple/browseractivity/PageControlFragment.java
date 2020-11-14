package edu.temple.browseractivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PageControlFragment extends Fragment {
    GOBack parentActivityInterface;
    EditText inp;

    public PageControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof GOBack) {
            parentActivityInterface = (GOBack) context;
        } else {
            throw new RuntimeException("You must implement ButtonClickInterface to attach this fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View control = inflater.inflate(R.layout.fragment_page_control, container, false);
        ImageButton back = control.findViewById(R.id.back);
        ImageButton forw = control.findViewById(R.id.forw);
        ImageButton go = control.findViewById(R.id.go);
        final EditText inp = control.findViewById(R.id.input);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivityInterface.goBack();
            }
        });

        forw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivityInterface.goForward();
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivityInterface.loadURL(inp.getText().toString());
            }
        });
        return control;
    }

    public void UpdateURL(String url) {
        EditText ip = (EditText) getView().findViewById(R.id.input);
        ip.setText(url);
    }

    interface GOBack { //POWER GEYSER!!!
        void goBack();

        void goForward();

        void loadURL(String url);
    }
}
