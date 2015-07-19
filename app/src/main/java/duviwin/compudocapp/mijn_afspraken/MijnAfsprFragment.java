package duviwin.compudocapp.mijn_afspraken;

import duviwin.compudocapp.R;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrListFragment;
import duviwin.compudocapp.mijn_opdrachten.MijnOpdrHtmlInfo;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MijnAfsprFragment extends AbstrOpdrListFragment {
    public MijnAfsprFragment(){
        super(R.layout.fragment_mijn_opdr,new MijnAfsprRetriever(),new MijnOpdrHtmlInfo());
    }

    @Override
    protected AbstrOpdrItemAdapter createAdapter(){
        return new MijnAfsprAdapter(new MijnOpdrHtmlInfo(),getActivity(), R.layout.opdracht_item,opdrachten);
    }
}
