package duviwin.compudocapp.OpdrList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import duviwin.compudocapp.OpdrachtDetails.Opdracht;
import duviwin.compudocapp.R;

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
            final TextView opdrNr = ((TextView) view.findViewById(R.id.opdracht_item_opdrNr));
            final TextView plaats = ((TextView) view.findViewById(R.id.opdracht_item_plaats));
            final TextView korteUitleg = ((TextView) view.findViewById(R.id.opdracht_item_korteUitleg));
            final TextView hBod = ((TextView) view.findViewById(R.id.opdracht_item_hBod));
            final TextView tijdVoorBod = ((TextView) view.findViewById(R.id.opdracht_item_tijdVoorBod));
            holder = new Holder(opdrNr, plaats, korteUitleg, hBod, tijdVoorBod);
            view.setTag(holder);
//        } else {
//            holder = (Holder) convertView.getTag();
//        }

        holder.opdrNr.setText(opdr.opdrachtNr);
        Log.d("", "opdr.numberClr:" + opdr.numberClr);
        holder.opdrNr.setBackgroundColor(Color.parseColor(opdr.numberClr));
        holder.plaats.setBackgroundColor(Color.parseColor(opdr.numberClr));
        holder.plaats.setText(opdr.plaats);
        holder.korteUitleg.setText(opdr.korteUitleg);
        holder.korteUitleg.setBackgroundColor(Color.parseColor(opdr.uitlegClr));
        holder.hBod.setText(opdr.huidigBod);
        holder.tijdVoorBod.setText(opdr.tijdVoorBod);
        if(opdr.isDummy){
            holder.plaats.setHeight(0);
            holder.opdrNr.setHeight(0);
            holder.hBod.setHeight(0);
            holder.tijdVoorBod.setHeight(0);
            ((LinearLayout) holder.tijdVoorBod.getParent().getParent()).setBackgroundColor(Color.parseColor("#000000"));
        }

        return view;

    }

    class Holder{
        final TextView opdrNr;
        final TextView plaats;
        final TextView korteUitleg;
        final TextView hBod;
        final TextView tijdVoorBod;
        public Holder(TextView opdrNr, TextView plaats, TextView korteUitleg,TextView hBod,  TextView tijdVoorBod){
            this.opdrNr=opdrNr;
            this.plaats=plaats;
            this.korteUitleg=korteUitleg;
            this.hBod=hBod;
            this.tijdVoorBod=tijdVoorBod;
        }
    }
}
