package com.muthuka.twittercreeper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.*;

import java.io.*;
import java.net.*;
import java.util.*;
import android.widget.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.json.*;

public class ListFeed extends Activity {
	List<FeedItem> model = new ArrayList<FeedItem>();
	FeedListAdapter adapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_feed);

		// Fill model with data from Twitter
		// https://twitter.com/status/user_timeline/muthuka.json
		try {
			fillModel();
		} catch (Exception e) {
			// TODO: Need Toast to show this
		}

		// Fix the list with the adapter
		ListView list = (ListView) findViewById(R.id.feeditems);
		adapter = new FeedListAdapter();
		list.setAdapter(adapter);
	}

	private void fillModel() throws Exception {
		StringBuffer sb = new StringBuffer("");
		BufferedReader in = null;

		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(
					"https://twitter.com/status/user_timeline/muthuka.json"));

			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			String page = sb.toString();
			System.out.println(page);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		Log.v("test", sb.toString());
		
		// Parsing
		JSONArray jObject = new JSONArray(sb.toString());
		for (int i=0; i<jObject.length(); i++)
		{
			JSONObject dict = jObject.getJSONObject(i);
			JSONObject userDict = dict.getJSONObject("user");
			
			FeedItem anItem = new FeedItem();
			anItem.setAuthor("@" + userDict.getString("screen_name"));
			anItem.setMessage(dict.getString("text"));
			model.add(anItem);
			
			Log.v("test", dict.getString("text"));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_list_feed, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("Refresh"))
		{
			try {
				fillModel();
			} catch (Exception e) {
				// TODO: Need Toast to show this
			}
		}
		return true;
	}

	class FeedListAdapter extends ArrayAdapter<FeedItem> {
		FeedListAdapter() {
			super(ListFeed.this, R.layout.row, model);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			FeedItemHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();

				row = inflater.inflate(R.layout.row, parent, false);
				holder = new FeedItemHolder(row);
				row.setTag(holder);
			} else {
				holder = (FeedItemHolder) row.getTag();
			}

			holder.populateFrom(model.get(position));

			return (row);
		}
	}

	static class FeedItemHolder {
		private TextView author = null;
		private TextView message = null;
		private ImageView icon = null;

		FeedItemHolder(View row) {
			author = (TextView) row.findViewById(R.id.author);
			message = (TextView) row.findViewById(R.id.message);
			icon = (ImageView) row.findViewById(R.id.icon);
		}

		void populateFrom(FeedItem r) {
			author.setText(r.getAuthor());
			message.setText(r.getMessage());
		}
	}
}
