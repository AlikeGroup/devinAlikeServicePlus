package com.yahoo.serviceplushousefinder.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.serviceplushousefinder.R;
import com.yahoo.serviceplushousefinder.adapters.ItemsArrayAdapter;
import com.yahoo.serviceplushousefinder.helpers.EndlessScrollListener;
import com.yahoo.serviceplushousefinder.models.Item;
import com.yahoo.serviceplushousefinder.models.SearchFilter;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListingFragment.OnItemLoadedListener} interface
 * to handle interaction events.
 * Use the {@link ListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListingFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private OnItemLoadedListener listener;

    private ArrayList<Item> items;
    private ItemsArrayAdapter itemsAdapter;
    private ListView lvListing;

    private int mPage; // tab
    private int pagination = 1;
    private LayoutInflater inflater;
    private View rootView;
    private ProgressBar progressBarFooter;
    private SwipeRefreshLayout swipeContainer;

    private Boolean isMapSearching = false;
    private SearchFilter filter;

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

        lvListing = (ListView) rootView.findViewById(R.id.lvListing);
        lvListing.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                pagination++;
                populateListing(pagination);
            }
        });

        filter = new SearchFilter();

        setUpItemAdapter();
        setUpSwipeAndRefreshLayout();
        setUpProgressBar();
        populateListing(1);

    }

    private void setUpSwipeAndRefreshLayout() {
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                itemsAdapter.clear();
                populateListing(0);
            }
        });
        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void setUpItemAdapter() {

        items = new ArrayList<>();
        itemsAdapter = new ItemsArrayAdapter(getActivity(), items);
        lvListing.setAdapter(itemsAdapter);
        //client = RestApplication.getRestClient(); // singleton client
        itemsAdapter.clear();
    }

    private void populateListing(int i) {

        if (i == 0) {
            // remove dulicated progress indicator when pullRefresh
            hideProgressBar();
            //Log.d("DEBUG", "hideProgressBar");
            i = 1;
        } else {
            if (swipeContainer.isRefreshing()) {
                // prevent from duplicated fetch
                return;
            }
            showProgressBar();
            //Log.d("DEBUG", "showProgressBar");
        }
        if (i == 1) {
            pagination = 1; // reset
        }
        String url = "http://f0.adp.tw1.yahoo.com/garden/alike?"
                +"sort="+mPage
                +"&offset="+String.valueOf((i-1)*10);
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String type = pref.getString("type", "buy");
        url = url+"&type="+type;
        String city = pref.getString("city", "");
        if (!city.equals("")) {
            url = url+"&city="+city;
        }
        String x1 = pref.getString("x1", "0.0");
        String x2 = pref.getString("x2", "0.0");
        if (!x1.equals("0.0")) {
            url = url+"&x1="+x1+"&x2="+x2;
        }
        String r1 = pref.getString("r1", "0");
        String r2 = pref.getString("r2", "0");
        if (!r1.equals("0") || !r2.equals("0")) {
            url = url+"&r1="+r1+"&r2="+r2;
        }
        String a1 = pref.getString("a1", "0");
        String a2 = pref.getString("a2", "0");
        if (!a1.equals("0") || !a2.equals("0")) {
            url = url+"&a1="+a1+"&a2="+a2;
        }
        String p1 = pref.getString("p1", "0");
        String p2 = pref.getString("p2", "0");
        if (!p1.equals("0") || !p2.equals("0")) {
            url = url+"&p1="+p1+"&p2="+p2;
        }

        // buy_map
        //String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20xml%20where%20url%3D%22https%3A%2F%2Fwww.dropbox.com%2Fs%2F6xz4gnc060w2x5h%2Fbuy_map20.xml%3Fdl%3D1%22%20and%20itemPath%20%3D%20%22result.hit%22&format=json&callback=";

        // listing10
        //String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20xml%20where%20url%3D%22https%3A%2F%2Fwww.dropbox.com%2Fs%2Fnjnfgjlg40kam78%2Fqrs10.xml%3Fdl%3D1%22%20and%20itemPath%20%3D%20%22result.hit%22&format=json&callback=";
        // /https://www.dropbox.com/s/njnfgjlg40kam78/qrs10.xml?dl=1";

        Log.d("DEBUG", "url: "+url);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
                    // SUCCESS
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // parse data and save into adapter
                        handleListingResponse(response);
                        //Log.d("success", "number of listing: " + response.length());
                        hideProgressBar();
                        isMapSearching = false;

                    }

                    // FAILURE
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getActivity(), "Error - Listing fetch fail", Toast.LENGTH_SHORT).show();
                        Log.e("fail", "get listing API failed, " + errorResponse.toString());
                        hideProgressBar();
                        isMapSearching = false;

                    }
                }

        );
    }

    private void handleListingResponse(JSONObject json) {
        items = Item.fromJSONObject(json);
        itemsAdapter.addAll(items);
        itemsAdapter.notifyDataSetChanged();

        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false);

        //lvListing.setSelection(3);
        listener.refreshMapMarker(items);

    }


    private void setUpProgressBar() {
        // Inflate the footer
        View footer = inflater.inflate(
                R.layout.footer_progress, null);
        // Find the progressbar within footer
        progressBarFooter = (ProgressBar) footer.findViewById(R.id.pbFooterLoading);
        // Add footer to ListView before setting adapter
        lvListing.addFooterView(footer);
    }

    private void showProgressBar() {
        progressBarFooter.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBarFooter.setVisibility(View.GONE);
    }

    public interface OnItemLoadedListener {
        public void refreshMapMarker(ArrayList<Item> newItems);
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemLoadedListener) {
            listener = (OnItemLoadedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemLoadedListener");
        }
    }

    public void searchGEO(String lattitude, String longitude, int zoom, int page, SearchFilter searchFilter) {
        // do something in fragment
        if (isMapSearching == true) {
            return;
        }
        itemsAdapter.clear();
        isMapSearching = true;

        filter = searchFilter;
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("x1", lattitude);
        edit.putString("x2", longitude);
        edit.putString("city", filter.getCity());
        if (filter.getBuyOrRent() == 0) {
            edit.putString("type", "buy");
        } else {
            edit.putString("type", "rent");
        }
        edit.putString("r1", String.valueOf(filter.getRoomMin()));
        edit.putString("r2", String.valueOf(filter.getRoomMax()));
        edit.putString("a1", String.valueOf(filter.getAgeMin()));
        edit.putString("a2", String.valueOf(filter.getAgeMax()));
        edit.putString("p1", String.valueOf(filter.getPriceMin()));
        edit.putString("p2", String.valueOf(filter.getPriceMax()));
        edit.commit();

        populateListing(page);

    }
}
