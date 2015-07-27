package duviwin.compudocapp.mijn_opdrachten;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.abstract_opdr_list.GenericOpdracht;

/**
 * Created by Duviwin on 6/15/2015.
 */
public class MijnOpdrAdapter extends AbstrOpdrItemAdapter {

    public MijnOpdrAdapter(Context context, int resId, List<GenericOpdracht> opdrList) {
        super(context, resId, opdrList);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        //the parent method fills in the textvalues
        View view=super.getView(pos,convertView,parent);
        GenericOpdracht opdr = (GenericOpdracht) getItem(pos);
        if(!opdr.isDummy){
        Holder viewHolder=((Holder) view.getTag());

        if(opdr.shrtInfo[MijnOpdrHtmlInfo.Nms.tijdVoorBod.getIndex()].contains("te laat")){
            viewHolder.tvs[MijnOpdrHtmlInfo.Nms.tijdVoorBod.getIndex()].setBackgroundColor(Color.parseColor("red"));
        }else if(!opdr.shrtInfo[MijnOpdrHtmlInfo.Nms.tijdVoorBod.getIndex()].contains("d")){
            viewHolder.tvs[MijnOpdrHtmlInfo.Nms.tijdVoorBod.getIndex()].setTextColor(Color.parseColor("blue"));
        }}
//        ((LinearLayout) viewHolder.tvs[Nms.opdrachtNr.index].getParent()).setBackgroundColor(opdr.getNumberClr());
//        ((LinearLayout) viewHolder.tvs[Nms.korteUitleg.index].getParent()).setBackgroundColor(opdr.getUitlegClr());
        return view;

    }
}
