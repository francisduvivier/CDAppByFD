package duviwin.compudocapp.mijn_gegevens;

import duviwin.compudocapp.R;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrListFragment;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class OpdrListFragment extends AbstrOpdrListFragment {
    public OpdrListFragment(){
        super(R.layout.fragment_mijn_opdr,new ListRetriever(),new OpdrListHtmlInfo());
    }

    @Override
    protected AbstrOpdrItemAdapter createAdapter(){
        return new OpdrItemAdapter(new OpdrListHtmlInfo(),getActivity(), R.layout.opdracht_item,opdrachten);
    }
}
