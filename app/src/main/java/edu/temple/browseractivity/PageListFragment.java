package edu.temple.browseractivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_expandable_list_item_1;


public class PageListFragment extends Fragment {
    ArrayList<String> urlKeys = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView list;
    listJam parentActivityInterface;

    public PageListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PageListFragment.listJam) {
            parentActivityInterface = (PageListFragment.listJam) context;
        } else {
            throw new RuntimeException("You must implement ButtonClickInterface to attach this fragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View pagelistfrag = inflater.inflate(R.layout.fragment_page_list, container, false);
        ListView list = pagelistfrag.findViewById(R.id.taco);

        if (savedInstanceState != null) {
            urlKeys = (ArrayList<String>) savedInstanceState.getSerializable("list");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), simple_expandable_list_item_1, urlKeys);
            list.setAdapter(arrayAdapter);
        } else {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), simple_expandable_list_item_1, urlKeys);
            list.setAdapter(arrayAdapter);
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(getActivity(), "You clicked on page " + position, Toast.LENGTH_LONG);
                toast.show();
                parentActivityInterface.listerJam(position);
                parentActivityInterface.updatingPageViewList(position);
            }
        });
//        setRetainInstance(true);
        return pagelistfrag;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", urlKeys);
    }


    public void UpdateURL(ArrayList<String> newUrlKeys) {

        if (arrayAdapter != null) {
            arrayAdapter.clear();
            arrayAdapter.addAll(newUrlKeys);
            arrayAdapter.notifyDataSetChanged();
        } else {
            ListView list = (ListView) getView().findViewById(R.id.taco);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), simple_expandable_list_item_1, newUrlKeys);
            list.setAdapter(arrayAdapter);
        }
//        ListView list = (ListView) getView().findViewById(R.id.taco);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), simple_expandable_list_item_1, newUrlKeys);
//        arrayAdapter.notifyDataSetChanged();
//        list.setAdapter(arrayAdapter);
    }

    interface listJam {
        void listerJam(int index);
        void updatingPageViewList(int pos);
    }
}
