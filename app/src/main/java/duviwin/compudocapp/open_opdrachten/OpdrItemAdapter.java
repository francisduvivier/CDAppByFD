package duviwin.compudocapp.open_opdrachten;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import duviwin.compudocapp.OpdrachtDetails.Opdracht;
import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.open_opdrachten.OpdrListHtmlInfo.Nms;
/**
 * Created by Duviwin on 6/15/2015.
 */
public class OpdrItemAdapter extends AbstrOpdrItemAdapter {

    public OpdrItemAdapter(HtmlInfo htmlInfo, Context context, int resId, List<Opdracht> opdrList) {
        super(htmlInfo,context, resId, opdrList);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        //the parent method fills in the textvalues
        View view=super.getView(pos,convertView,parent);
        Opdracht opdr = (Opdracht) getItem(pos);
        Holder viewHolder=((Holder) view.getTag());
        ((LinearLayout) viewHolder.tvs[Nms.opdrachtNr.index].getParent()).setBackgroundColor(Color.parseColor(opdr.numberClr));
        ((LinearLayout) viewHolder.tvs[Nms.korteUitleg.index].getParent()).setBackgroundColor(Color.parseColor(opdr.uitlegClr));

        return view;

    }
}
