package duviwin.compudocapp.mijn_afspraken;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.abstract_opdr_list.GenericOpdracht;
import duviwin.compudocapp.html_info.HtmlInfo;
/**
 * Created by Duviwin on 6/15/2015.
 */
public class MijnAfsprAdapter extends AbstrOpdrItemAdapter {

    public MijnAfsprAdapter(HtmlInfo htmlInfo, Context context, int resId, List<GenericOpdracht> opdrList) {
        super(context, resId, opdrList);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        //the parent method fills in the textvalues
        View view=super.getView(pos,convertView,parent);
        //TODO
        return view;

    }
}
