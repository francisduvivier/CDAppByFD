package duviwin.compudocapp.open_opdrachten;

import duviwin.compudocapp.R;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrListFragment;

/**
 * A fragment representing a list of Items.
 */
public class OpenOpdrFragment extends AbstrOpdrListFragment<ShortOpdracht> {
    public OpenOpdrFragment() {
        super(R.layout.fragment_mijn_opdr, new OpenOpdrRetriever(), new OpenOpdrHtmlInfo());
    }

    @Override
    protected AbstrOpdrItemAdapter createAdapter() {
        return new OpenOpdrAdapter(getActivity(), R.layout.opdracht_item, opdrachten);
    }
    @Override
    public String getActionString() {
        return getString(R.string.open_from_open_opdr);
    }
}
