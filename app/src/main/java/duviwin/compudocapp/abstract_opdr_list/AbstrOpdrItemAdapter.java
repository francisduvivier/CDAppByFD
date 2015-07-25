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
        Holder viewHolder;
        GenericOpdracht opdr = (GenericOpdracht) getItem(pos);
        HtmlInfo opdrHtmlInfo = opdr.htmlInfo;
//        View view = convertView;

//        if (view != null && !((Holder) view.getTag()).isDummy()) {
//            viewHolder = ((Holder) view.getTag());
//        } else {
        View view = li.inflate(resIdInflatable, null);
            final TextView[] tvs = new TextView[opdrHtmlInfo.getVals().length];
            //First we put the views in a holder
        int i=0;
        for (HtmlInfoEnum enumVal : opdrHtmlInfo.getVals()) {
            i++;
                if (enumVal.getResId() != null) {
                    TextView tv=((TextView) view.findViewById(enumVal.getResId()));
                    if(tv==null){
                        throw new RuntimeException("This should not happen, here is some debug info: i="+i+", view:"+view+" htmlclass"+opdrHtmlInfo.getClass());
                    }
                    tvs[enumVal.getIndex()] = tv;
                }
            }
            viewHolder = new Holder(tvs, opdr.isDummy);
            view.setTag(viewHolder);
//        }
        //this for loop fills in the textFields
        for (HtmlInfoEnum enumVal : opdrHtmlInfo.getVals()) {
            if (enumVal.getResId() != null) {
                viewHolder.tvs[enumVal.getIndex()].setText(opdr.shrtInfo[enumVal.getIndex()]);
            }
        }
        if (opdr.isDummy) {
            for (HtmlInfoEnum enumVal : opdrHtmlInfo.getVals()) {
                if (enumVal.getResId() != null) {
                    if (enumVal.getIndex() != opdrHtmlInfo.getLoadingIndex()) {
                        viewHolder.tvs[enumVal.getIndex()].setHeight(0);
                    }
                }
            }
            if(viewHolder.tvs[opdrHtmlInfo.getLoadingIndex()].getParent().getParent()!=null){
            ((LinearLayout) viewHolder.tvs[opdrHtmlInfo.getLoadingIndex()].getParent().getParent()).setBackgroundColor(Color.parseColor("#000000"));}
            viewHolder.tvs[opdrHtmlInfo.getLoadingIndex()].setBackgroundColor(CSSData.getKleur("Loading"));
//            viewHolder.tvs[htmlInfo.getLoadingIndex()].setText("Loading...");
        }

        return view;

    }

    protected class Holder {
        public final TextView[] tvs;
        private final boolean isDummy;

        public Holder(TextView[] tvs, boolean isDummy){
            this.isDummy=isDummy;
            this.tvs=tvs;
        }
        public boolean isDummy(){

            return isDummy;}
    }
}
