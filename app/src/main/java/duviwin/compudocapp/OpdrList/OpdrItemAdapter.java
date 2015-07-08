package duviwin.compudocapp.OpdrList;

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
import duviwin.compudocapp.html_info.OpdrListHtmlInfo.Nms;
/**
 * Created by Duviwin on 6/15/2015.
 */
public class OpdrItemAdapter extends ArrayAdapter {
    private LayoutInflater li;

    public OpdrItemAdapter(Context context, int resId, List<Opdracht> opdrList) {
        super(context, resId, opdrList);
        li = LayoutInflater.from(context);

    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder;
        Opdracht opdr = (Opdracht) getItem(pos);

//        if (view == null) {

            view = li.inflate(R.layout.opdracht_item, null);
            final TextView[] tvs=new TextView[Nms.values().length];
            for(Nms enumVal:Nms.values()){
                    tvs[enumVal.i] = ((TextView) view.findViewById(enumVal.resId));
            }

            holder = new Holder(tvs);
            view.setTag(holder);
//        } else {
//            holder = (Holder) convertView.getTag();
//        }


        holder.tvs[Nms.opdrachtNr.i].setBackgroundColor(Color.parseColor(opdr.numberClr));
        holder.tvs[Nms.plaats.i].setBackgroundColor(Color.parseColor(opdr.numberClr));
        holder.tvs[Nms.korteUitleg.i].setBackgroundColor(Color.parseColor(opdr.uitlegClr));
        for(Nms enumVal:Nms.values()){
            if(enumVal.resId!=null){
                holder.tvs[enumVal.i].setText(opdr.shrtInfo[enumVal.i]);
            }
        }

        if(opdr.isDummy){
            holder.tvs[Nms.plaats.i].setHeight(0);
            holder.tvs[Nms.opdrachtNr.i].setHeight(0);
            holder.tvs[Nms.huidigBod.i].setHeight(0);
            holder.tvs[Nms.tijdVoorBod.i].setHeight(0);
            ((LinearLayout) holder.tvs[Nms.tijdVoorBod.i].getParent().getParent()).setBackgroundColor(Color.parseColor("#000000"));
        }

        return view;

    }

    class Holder{
        final TextView[] tvs;
        public Holder(TextView[] tvs){
            this.tvs=tvs;
        }
    }
}
