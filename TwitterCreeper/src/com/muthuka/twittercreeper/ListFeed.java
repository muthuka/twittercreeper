package com.muthuka.twittercreeper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ListFeed extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_feed);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_feed, menu);
        return true;
    }
}
