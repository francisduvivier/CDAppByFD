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

import duviwin.compudocapp.CSSData;
import duviwin.compudocapp.R;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.html_info.HtmlInfoEnum;

/**
 * Created by Duviwin on 6/15/2015.
 */
public abstract class AbstrOpdrItemAdapter extends ArrayAdapter {
    protected final LayoutInflater li;
    private int resIdInflatable;

    public AbstrOpdrItemAdapter(Context context, int resId, List opdrList) {
        super(context, resId, opdrList);
        this.resIdInflatable=resId;
        li = LayoutInflater.from(context);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder viewHolder;
        GenericOpdracht opdr = (GenericOpdracht) getItem(pos);
        HtmlInfo opdrHtmlInfo=opdr.htmlInfo;
//        if (view == null) {

        
        view = li.inflate(resIdInflatable, null);
            final TextView[] tvs=new TextView[opdrHtmlInfo.getVals().length];
            //First we put the views in a holder
            for(HtmlInfoEnum enumVal:opdrHtmlInfo.getVals()){
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
        for(HtmlInfoEnum enumVal:opdrHtmlInfo.getVals()) {
            if (enumVal.getResId() != null) {
                viewHolder.tvs[enumVal.getIndex()].setText(opdr.shrtInfo[enumVal.getIndex()]);
            }
        }
        if(opdr.isDummy){
            for(HtmlInfoEnum enumVal:opdrHtmlInfo.getVals()){
                if (enumVal.getResId() != null) {
                if(enumVal.getIndex()!=opdrHtmlInfo.getLoadingIndex()){
                viewHolder.tvs[enumVal.getIndex()].setHeight(0);}}
            }
            ((LinearLayout) viewHolder.tvs[opdrHtmlInfo.getLoadingIndex()].getParent().getParent()).setBackgroundColor(Color.parseColor("#000000"));
            viewHolder.tvs[opdrHtmlInfo.getLoadingIndex()].setBackgroundColor(CSSData.getKleur("Loading"));
//            viewHolder.tvs[htmlInfo.getLoadingIndex()].setText("Loading...");
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
