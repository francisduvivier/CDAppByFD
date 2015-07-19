package duviwin.compudocapp.open_opdrachten;

import java.util.List;

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
public class OpenOpdrFragment extends AbstrOpdrListFragment<ShortOpdracht> {
    public OpenOpdrFragment(){
        super(R.layout.fragment_mijn_opdr,new OpenOpdrRetriever(),new OpenOpdrHtmlInfo());
    }
    @Override
    protected AbstrOpdrItemAdapter createAdapter(){
        return new OpenOpdrAdapter(getActivity(), R.layout.opdracht_item, opdrachten);
    }
}
