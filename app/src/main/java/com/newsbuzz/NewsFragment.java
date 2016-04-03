package com.newsbuzz;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by sukhbir on 3/4/16.
 */
public class NewsFragment extends Fragment {
    public static final String EXTRA_NEWS_ID = "id";

    private NewsItem newsItem;

    private TextView title,date,description;
    private ImageView pic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsItem = (NewsItem)getArguments().getSerializable(EXTRA_NEWS_ID);
         setHasOptionsMenu(true);    // turn on menu handling
    }

    public static NewsFragment newInstance(NewsItem item) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_NEWS_ID, item);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_more, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {

                if(getActivity().getActionBar()!=null)
                    getActivity().getActionBar().setDisplayHomeAsUpEnabled(true); //for back button with icon
            }
        }

        title = (TextView)v.findViewById(R.id.title_more_activity);
        date = (TextView)v.findViewById(R.id.pubDate_more_activity);
        description = (TextView)v.findViewById(R.id.description_more_activity);
        pic = (ImageView)v.findViewById(R.id.image_more_activity);

        title.setText(newsItem.title);
        date.setText(newsItem.pubDate);
        description.setText(newsItem.description);
        Glide.with(getActivity()).load(newsItem.link_image).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.ic_error).into(pic);

        return v;
    }

}
