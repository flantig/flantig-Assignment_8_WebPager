package edu.temple.browseractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Intent receivedIntent = getIntent();


        ArrayList<String> titles = receivedIntent.getStringArrayListExtra("titles");
        ArrayList<String> urls = receivedIntent.getStringArrayListExtra("urls");
        BookmarkAdapter adapter = new BookmarkAdapter(titles, this);

        ListView lView = (ListView)findViewById(R.id.bookmark_listview);


        lView.setAdapter(adapter);

    }
}
