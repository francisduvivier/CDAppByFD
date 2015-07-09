package duviwin.compudocapp.abstract_opdr_list;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import duviwin.compudocapp.OpdrachtDetails.Opdracht;
import duviwin.compudocapp.R;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.html_info.HtmlInfoEnum;
import duviwin.compudocapp.open_opdrachten.OpdrListHtmlInfo;

/**
 * Created by Duviwin on 6/15/2015.
 */
public abstract class AbstrOpdrItemAdapter extends ArrayAdapter {
    protected final LayoutInflater li;
    protected final HtmlInfo htmlInfo;
    public AbstrOpdrItemAdapter(HtmlInfo htmlInfo,Context context, int resId, List<Opdracht> opdrList) {
        super(context, resId, opdrList);
        li = LayoutInflater.from(context);
        this.htmlInfo=htmlInfo;

    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder viewHolder;
        Opdracht opdr = (Opdracht) getItem(pos);
//        if (view == null) {

            view = li.inflate(R.layout.opdracht_item, null);
            final TextView[] tvs=new TextView[htmlInfo.getVals().length];
            //First we put the views in a holder
            for(HtmlInfoEnum enumVal:htmlInfo.getVals()){
                if(enumVal.getResId()!=null){
                    tvs[enumVal.getIndex()] = ((TextView) view.findViewById(enumVal.getResId()));
                }
            }
            viewHolder = new Holder(tvs);
            view.setTag(viewHolder);
//        } else {
//            holder = (Holder) convertView.getTag();
//        }
        //this for loop fills in the textFields
        for(HtmlInfoEnum enumVal:htmlInfo.getVals()) {
            if (enumVal.getResId() != null) {
                viewHolder.tvs[enumVal.getIndex()].setText(opdr.shrtInfo[enumVal.getIndex()]);
            }
        }
        if(opdr.isDummy){
            viewHolder.tvs[OpdrListHtmlInfo.Nms.plaats.index].setHeight(0);
            viewHolder.tvs[OpdrListHtmlInfo.Nms.opdrachtNr.index].setHeight(0);
            viewHolder.tvs[OpdrListHtmlInfo.Nms.huidigBod.index].setHeight(0);
            viewHolder.tvs[OpdrListHtmlInfo.Nms.tijdVoorBod.index].setHeight(0);
            ((LinearLayout) viewHolder.tvs[OpdrListHtmlInfo.Nms.tijdVoorBod.index].getParent().getParent()).setBackgroundColor(Color.parseColor("#000000"));
        }
        return view;

    }

    protected class Holder{
        public final TextView[] tvs;
        public Holder(TextView[] tvs){
            this.tvs=tvs;
        }
    }
}