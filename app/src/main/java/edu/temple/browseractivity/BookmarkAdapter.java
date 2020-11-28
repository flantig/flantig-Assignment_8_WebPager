package edu.temple.browseractivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BookmarkAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> urls;
    ArrayList<String> titles;
    FileInputStream stream;
    BufferedReader reader;
    File urlFile;
    File titleFile;

    public BookmarkAdapter(ArrayList<String> titles, Context context) {
        this.titles = titles;
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_boomark_list_item, parent, false);
        }

        //Handle TextView and display string from your list
        final TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
        listItemText.setText(titles.get(position));

        //Handle buttons and add onClickListeners
        Button delBtn = (Button) view.findViewById(R.id.del_btn);

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File url = new File(context.getFilesDir(), "url.txt");
                File title = new File(context.getFilesDir(), "title.txt");
                try {
                    deleteLine(position, url);
                    deleteLine(position, title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                titles.remove(position);
                notifyDataSetChanged();
            }
        });

        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("urlPOS", position);
                ((Activity) context).setResult(Activity.RESULT_OK, intent);
                ((Activity) context).finish();



            }
        });


        return view;
    }

    public void deleteLine(int pos, File file) throws IOException {
        stream = new FileInputStream(file);
        reader = new BufferedReader(new InputStreamReader(stream));
        FileWriter writer = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(writer);

        String currentLine;
        int count = 0;

        while ((currentLine = reader.readLine()) != null) {
            count++;
            continue;
//            if (count == pos) {
//                continue;
//            }
//            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
    }
}
