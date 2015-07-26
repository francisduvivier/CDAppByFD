package duviwin.compudocapp.mijn_afspraken;

import duviwin.compudocapp.R;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrListFragment;
import duviwin.compudocapp.abstract_opdr_list.GenericOpdracht;

/**
 * A fragment representing a list of Items.
 */
public class MijnAfsprFragment extends AbstrOpdrListFragment<GenericOpdracht> {
    public MijnAfsprFragment(){
        super(R.layout.fragment_mijn_afspr,new MijnAfsprRetriever(),new MijnAfsprHtmlInfo());
    }

    @Override
    protected AbstrOpdrItemAdapter createAdapter(){
        return new MijnAfsprAdapter(new MijnAfsprHtmlInfo(),getActivity(), R.layout.fragment_afspr_item,opdrachten);
    }
    @Override
    public String getActionString() {
        return getString(R.string.open_from_mijn_afspr);
    }
}
