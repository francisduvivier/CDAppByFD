package duviwin.compudocapp.abstract_opdr_list;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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

import duviwin.compudocapp.AppSettings;
import duviwin.compudocapp.R;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.opdracht_details.ShowDetailsActivity;
import duviwin.compudocapp.usage_stats.MyTracker;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public abstract class AbstrOpdrListFragment<E extends GenericOpdracht> extends Fragment implements AbsListView.OnItemClickListener
//        , MyEventListener
{

//    @Override
//    public void handleMsg(String msg){
//        opdrachten.add(GenericOpdracht.getDummy(msg));
//        mAdapter.notifyDataSetChanged();
//
//    }
    protected final int listResId;
    protected final AbstrListRetriever listRetriever;
    //    protected OnFragmentInteractionListener mListener;
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
    public void fillAdapter(List<GenericOpdracht> result){

// mAdapter = new MijnOpdrAdapter(getActivity(),R.layout.opdracht_item,result);
        opdrachten.clear();
        opdrachten.addAll(result);

        mAdapter.notifyDataSetChanged();
        System.out.println("We notified the adapter");


    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AbstrOpdrListFragment(int listResId,AbstrListRetriever lr, HtmlInfo htmlInfo) {
        this.listRetriever =lr;
        this.htmlInfo=htmlInfo;
        this.listResId = listResId;
    }
    protected List<GenericOpdracht> opdrachten=new ArrayList<GenericOpdracht>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Connection.getConnection().opdrListFrgmt =this;
        opdrachten.add(E.getDummy(htmlInfo,"Loading..."));
        mAdapter = createAdapter();
        AppSettings.refreshPrefs(getActivity().getBaseContext());
        refreshList();
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //FDCODE
        li=inflater;
        View view = inflater.inflate(listResId, container, false);

        // Set the adapter

        mListView = (AbsListView) view.findViewById(R.id.my_opdr_list);
        mListView.setAdapter(mAdapter);


        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        new AlertDialog.Builder(activity)
//                .setTitle("An attach happened")
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        new AlertDialog.Builder(getActivity())
//                .setTitle("A detach happened")
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (
//                null != mListener&&
                        !opdrachten.get(position).isDummy) {
           Intent intent=new Intent(getActivity(),ShowDetailsActivity.class);
            intent.putExtra("opdracht", Integer.parseInt(opdrachten.get(position).getOpdrNr()));
            startActivity(intent);
            trackClick(opdrachten.get(position).getOpdrNr());
    }
    }

    protected void trackClick(String opdrNrStr){
        long opdrNr;
        //this try catch code tries to get the opdrnr from the opdracht,
        // but if it fails because it is a dummy or something, this will be reported to statistics by the -1 or -2 value.
        try {
            opdrNr = Integer.parseInt(opdrNrStr);
        } catch (Exception nfe) {
            if (nfe.getClass().equals(NumberFormatException.class)) {
                opdrNr = -1;
            } else {
                opdrNr = -2;
            }
        }
        MyTracker.send(getString(R.string.list_item_click),getActionString(),"OpdrNr:" + opdrNr);
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

    public AbstrListRetriever getListRetriever() {
        return listRetriever;
    }

    public abstract String getActionString();

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(String id);
//    }
    private class HttpAsyncTask extends AsyncTask<AbstrOpdrListFragment, Void, List<GenericOpdracht>> {
        private AbstrOpdrListFragment f;

        @Override
        protected List<GenericOpdracht> doInBackground(AbstrOpdrListFragment... fragments) {
            this.f = fragments[0];
            AbstrListRetriever oi = f.getListRetriever();
            oi.downloadOpdrachten();
            return oi.opdrachten;
        }
        @Override
        protected void onPostExecute(List<GenericOpdracht> result) {
            f.fillAdapter(result);
        }
    }

}
