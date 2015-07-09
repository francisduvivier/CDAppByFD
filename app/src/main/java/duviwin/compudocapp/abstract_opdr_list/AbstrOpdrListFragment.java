package duviwin.compudocapp.abstract_opdr_list;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import duviwin.compudocapp.Connection.Connection;
import duviwin.compudocapp.Events.MyEventListener;
import duviwin.compudocapp.OpdrachtDetails.Opdracht;
import duviwin.compudocapp.OpdrachtDetails.ShowDetailsActivity;
import duviwin.compudocapp.R;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.open_opdrachten.OpdrachtenInfo;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public abstract class AbstrOpdrListFragment extends Fragment implements AbsListView.OnItemClickListener, MyEventListener {

    @Override
    public void handleMsg(String msg){
        opdrachten.add(Opdracht.getDummy(msg));
        mAdapter.notifyDataSetChanged();

    }
    protected OnFragmentInteractionListener mListener;
   final protected HtmlInfo htmlInfo;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    protected AbstrOpdrItemAdapter mAdapter;
    protected LayoutInflater li;
    public void fillAdapter(List<Opdracht> result){

// mAdapter = new OpdrItemAdapter(getActivity(),R.layout.opdracht_item,result);
        opdrachten.clear();
        opdrachten.addAll(result);

        mAdapter.notifyDataSetChanged();
        System.out.println("We notified the adapter");


    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AbstrOpdrListFragment(HtmlInfo htmlInfo) {
        this.htmlInfo=htmlInfo;
    }
    protected List<Opdracht> opdrachten=new ArrayList<Opdracht>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Connection.getConnection().opdrListFrgmt =this;
        opdrachten.add(Opdracht.getDummy("Loading..."));
        mAdapter = createAdapter();
        Connection.refreshCredentials(getActivity().getBaseContext());
        refreshList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //FDCODE
        li=inflater;
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(R.id.my_opdr_list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener&&!opdrachten.get(position).isDummy) {
           Intent intent=new Intent(getActivity(),ShowDetailsActivity.class);
            intent.putExtra("opdracht",opdrachten.get(position));
            startActivity(intent);

    }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public void refreshList() {
        (new HttpAsyncTask()).execute(this);
    }

    //this method is made so that the subclass can determine the adapter, it should only be called in onCreate;
    protected abstract AbstrOpdrItemAdapter createAdapter();

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String id);
    }
    private class HttpAsyncTask extends AsyncTask<AbstrOpdrListFragment, Void, List<Opdracht>> {
        private AbstrOpdrListFragment f;
        @Override
        protected List<Opdracht> doInBackground(AbstrOpdrListFragment... fragments) {
            this.f=fragments[0];
            OpdrachtenInfo oi=new OpdrachtenInfo();
            oi.downloadOpdrachten();
            return oi.opdrachten;
        }
        @Override
        protected void onPostExecute(List<Opdracht> result) {
            f.fillAdapter(result);
        }
    }

}
