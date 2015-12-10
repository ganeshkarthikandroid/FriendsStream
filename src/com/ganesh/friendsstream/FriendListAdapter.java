package com.ganesh.friendsstream;

import java.util.ArrayList;
import java.util.HashMap;

import com.ganesh.friendsstream.R.string;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendListAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflator;
	ArrayList<HashMap<String, String>> friendList = new ArrayList<HashMap<String, String>>();
	private DisplayImageOptions options;
	String location_url, image_url;

	public FriendListAdapter(Context listScreen,
			ArrayList<HashMap<String, String>> frienlistdata) {
		// TODO Auto-generated constructor stub
		this.mContext = listScreen;
		inflator = LayoutInflater.from(mContext);
		friendList = frienlistdata;
		System.out.println("friendList---------------->>>" + friendList.size());

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return friendList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return friendList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		return (friendList.get(position).get("type").equals("simple_card")) ? 0
				: 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = new ViewHolder();
		int type = getItemViewType(position);
		if (type == 0) {
			// Inflate the layout with image
			convertView = inflator.inflate(R.layout.list_item, parent, false);
		} else {
			convertView = inflator.inflate(R.layout.list_item1, parent, false);
		}

		if (type == 0) {
			holder.title = (TextView) convertView.findViewById(R.id.title_txt);
			holder.story = (TextView) convertView.findViewById(R.id.story_txt);
			holder.story.setText(friendList.get(position).get("content"));
			holder.title.setText(friendList.get(position).get("title"));

		} else {

			holder.address = (TextView) convertView
					.findViewById(R.id.address_txt);
			holder.address_photo = (ImageView) convertView
					.findViewById(R.id.address_photo);
			holder.map_icon = (ImageView) convertView
					.findViewById(R.id.map_icon);
			holder.address.setText(friendList.get(position).get("title"));
			image_url = friendList.get(position).get("image_url");
			image_url = image_url.replace(" ", "");
			location_url = friendList.get(position).get("location_url");
			location_url = location_url.replace(" ", "");
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
					mContext).defaultDisplayImageOptions(options).build();
			ImageLoader.getInstance().init(config);
			ImageLoader.getInstance().displayImage(image_url,
					holder.address_photo, options);
			holder.address.setText(friendList.get(position).get("title"));

			holder.map_icon.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(location_url));
					mContext.startActivity(i);

				}
			});

		}

		return convertView;
	}

	static class ViewHolder {
		TextView title, story, address;
		ImageView address_photo, map_icon;
		LinearLayout item_layout1, item_layout2;
	}

}
