package com.ganesh.friendsstream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView friend_Name;
	ImageView friend_img, friend_call, friend_mail, friend_contact;
	String name, photo, phone, email, contact_url;
	private DisplayImageOptions options;
	private FriendListAdapter adapter;
	private ListView listView;
	public static ArrayList<HashMap<String, String>> friends_detail_List = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		friend_Name = (TextView) findViewById(R.id.frnd_name);
		friend_img = (ImageView) findViewById(R.id.frnd_img);
		friend_call = (ImageView) findViewById(R.id.call_img);
		friend_mail = (ImageView) findViewById(R.id.msg_img);
		friend_contact = (ImageView) findViewById(R.id.contact_img);
		listView = (ListView) findViewById(R.id.list);

		if (friends_detail_List.size() > 0) {
			friends_detail_List.clear();
		}

		/* Retrieves the data */
		try {
			JSONObject obj = new JSONObject(loadJSONFromAsset());

			name = obj.getString("name");
			photo = obj.getString("photo");
			phone = obj.getString("phone");
			email = obj.getString("email");
			contact_url = obj.getString("contact_url");

			friend_Name.setText(name);
			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(null)
					// resource
					// or
					// drawable
					.showImageForEmptyUri(null)
					// resource or drawable
					.showImageOnFail(null)
					// resource or drawable
					.resetViewBeforeLoading(false)
					// default
					.delayBeforeLoading(1000).cacheInMemory(true)
					// default
					.cacheOnDisc(true)
					// default
					.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
					// default
					.bitmapConfig(Bitmap.Config.RGB_565)
					// default
					.handler(new Handler()) // default
					// .showImageOnLoading(R.drawable.ic_stub)
					.build();
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					MainActivity.this).defaultDisplayImageOptions(options)
					.build();
			ImageLoader.getInstance().init(config);
			ImageLoader.getInstance().displayImage(photo, friend_img, options);

			JSONArray our_story = obj.getJSONArray("our_story");
			for (int i = 0; i < our_story.length(); i++) {
				JSONObject jo_inside = our_story.getJSONObject(i);
				String type = jo_inside.getString("type");
				String title = jo_inside.getString("title");

				String content = "";
				String image_url = "";
				String more_images = "";
				String location_url = "";
				HashMap<String, String> mMap = new HashMap<String, String>();
				if (type.equals("simple_card")) {
					content = jo_inside.getString("content");
				}

				if (type.equals("checkin_card")) {
					image_url = jo_inside.getString("image_url");
					location_url = jo_inside.getString("location_url");
					if (i == 1) {
						more_images = jo_inside.getString("more_images");
						mMap.put("more_images", more_images);
					}
				}

				
				mMap.put("title", title);
				mMap.put("content", content);
				mMap.put("image_url", image_url);
				
				mMap.put("location_url", location_url);
				mMap.put("type", type);
				friends_detail_List.add(mMap);
			}
			adapter = new FriendListAdapter(MainActivity.this,
					friends_detail_List);
			listView.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		friend_call.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ phone));
				startActivity(intent);

			}
		});
		friend_mail.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri
						.fromParts("mailto", email, null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
				emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
				startActivity(Intent
						.createChooser(emailIntent, "Send email..."));

			}
		});
		friend_contact.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(contact_url));
				startActivity(i);

			}
		});

	}

	/* Load Json data from asset folder */
	public String loadJSONFromAsset() {
		String json = null;
		try {
			InputStream is = MainActivity.this.getAssets().open(
					"sample_response.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}
}
