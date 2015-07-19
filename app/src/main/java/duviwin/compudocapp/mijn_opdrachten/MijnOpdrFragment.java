package duviwin.compudocapp.mijn_opdrachten;

import duviwin.compudocapp.R;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrListFragment;
import duviwin.compudocapp.abstract_opdr_list.GenericOpdracht;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MijnOpdrFragment extends AbstrOpdrListFragment<GenericOpdracht> {
    public MijnOpdrFragment(){
        super(R.layout.fragment_mijn_opdr,new MijnOpdrRetriever(),new MijnOpdrHtmlInfo());
    }

    @Override
    protected AbstrOpdrItemAdapter createAdapter(){
        return new MijnOpdrAdapter(new MijnOpdrHtmlInfo(),getActivity(), R.layout.opdracht_item,opdrachten);
    }
}
