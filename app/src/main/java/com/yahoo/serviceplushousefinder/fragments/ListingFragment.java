package com.yahoo.serviceplushousefinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yahoo.serviceplushousefinder.R;

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

    public interface OnFragmentInteractionListener {
    }
}
