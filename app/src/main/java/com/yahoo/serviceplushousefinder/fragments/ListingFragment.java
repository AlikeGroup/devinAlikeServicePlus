package com.yahoo.serviceplushousefinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.serviceplushousefinder.R;
import com.yahoo.serviceplushousefinder.adapters.ItemsArrayAdapter;
import com.yahoo.serviceplushousefinder.models.Item;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListingFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private ArrayList<Item> items;
    private ItemsArrayAdapter itemsAdapter;
    private SwipeRefreshLayout swipeContainer;

    private int mPage;
    private LayoutInflater inflater;
    private View rootView;

    public static ListingFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ListingFragment fragment = new ListingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        rootView = inflater.inflate(R.layout.fragment_listing, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView lvListing = (ListView) rootView.findViewById(R.id.lvListing);
        items = new ArrayList<>();
        itemsAdapter = new ItemsArrayAdapter(getActivity(), items);
        lvListing.setAdapter(itemsAdapter);
        //client = RestApplication.getRestClient(); // singleton client
        itemsAdapter.clear();

        populateListing(1);
/*
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline(page);
            }
        });

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                tweetAdapter.clear();
                populateTimeline(1);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
                */
    }

    private void populateListing(int i) {
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20xml%20where%20url%3D%22https%3A%2F%2Fwww.dropbox.com%2Fs%2Fnjnfgjlg40kam78%2Fqrs10.xml%3Fdl%3D1%22%20and%20itemPath%20%3D%20%22result.hit%22&format=json&callback=";

        // /https://www.dropbox.com/s/njnfgjlg40kam78/qrs10.xml?dl=1";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
                    // SUCCESS
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // parse data and save into adapter
                        handleListingResponse(response);
                        //Log.d("success", "number of listing: " + response.length());
                        //hideProgressBar();
                    }

                    // FAILURE
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.e("fail", "get listing API failed, " + errorResponse.toString());
                        //hideProgressBar();
                    }
                }

        );
    }

    private void handleListingResponse(JSONObject json) {
        ArrayList<Item> items = Item.fromJSONObject(json);
        itemsAdapter.addAll(items);
        itemsAdapter.notifyDataSetChanged();

        // Now we call setRefreshing(false) to signal refresh has finished
        //swipeContainer.setRefreshing(false);
    }

    public interface OnFragmentInteractionListener {
    }
}
