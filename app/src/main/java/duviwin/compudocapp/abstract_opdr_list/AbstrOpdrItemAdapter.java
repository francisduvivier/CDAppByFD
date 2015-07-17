package duviwin.compudocapp.abstract_opdr_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import duviwin.compudocapp.R;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.html_info.HtmlInfoEnum;

/**
 * Created by Duviwin on 6/15/2015.
 */
public abstract class AbstrOpdrItemAdapter extends ArrayAdapter {
    protected final LayoutInflater li;
    protected final HtmlInfo htmlInfo;
    public AbstrOpdrItemAdapter(HtmlInfo htmlInfo,Context context, int resId, List<ShortOpdracht> opdrList) {
        super(context, resId, opdrList);
        li = LayoutInflater.from(context);
        this.htmlInfo=htmlInfo;

    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder viewHolder;
        ShortOpdracht opdr = (ShortOpdracht) getItem(pos);
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

        return view;

    }

    protected class Holder{
        public final TextView[] tvs;
        public Holder(TextView[] tvs){
            this.tvs=tvs;
        }
    }
}
