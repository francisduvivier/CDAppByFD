package duviwin.compudocapp.mijn_gegevens;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.abstract_opdr_list.ShortOpdracht;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.mijn_gegevens.OpdrListHtmlInfo.Nms;

/**
 * Created by Duviwin on 6/15/2015.
 */
public class OpdrItemAdapter extends AbstrOpdrItemAdapter {

    public OpdrItemAdapter(HtmlInfo htmlInfo, Context context, int resId, List<ShortOpdracht> opdrList) {
        super(htmlInfo,context, resId, opdrList);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        //the parent method fills in the textvalues
        View view=super.getView(pos,convertView,parent);
        ShortOpdracht opdr = (ShortOpdracht) getItem(pos);
        Holder viewHolder=((Holder) view.getTag());
        ((LinearLayout) viewHolder.tvs[Nms.opdrachtNr.index].getParent()).setBackgroundColor(opdr.getNumberClr());
        ((LinearLayout) viewHolder.tvs[Nms.korteUitleg.index].getParent()).setBackgroundColor(opdr.getUitlegClr());

        return view;

    }
}
