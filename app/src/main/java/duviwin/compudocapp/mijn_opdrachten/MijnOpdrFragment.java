package duviwin.compudocapp.mijn_opdrachten;

import duviwin.compudocapp.R;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrListFragment;
import duviwin.compudocapp.abstract_opdr_list.GenericOpdracht;

/**
 * A fragment representing a list of Items.
 */
public class MijnOpdrFragment extends AbstrOpdrListFragment<GenericOpdracht> {
    public MijnOpdrFragment(){
        super(R.layout.fragment_mijn_opdr,new MijnOpdrRetriever(),new MijnOpdrHtmlInfo());
    }

    @Override
    protected AbstrOpdrItemAdapter createAdapter(){
        return new MijnOpdrAdapter(new MijnOpdrHtmlInfo(),getActivity(), R.layout.opdracht_item,opdrachten);
    }

    @Override
    public String getActionString() {
        return getString(R.string.open_from_mijn_opdr);
    }
}
