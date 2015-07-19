package duviwin.compudocapp.opdracht_details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import duviwin.compudocapp.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class ShowDetailsActivityFragment extends Fragment {

    public ShowDetailsActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_details, container, false);

    }
}
