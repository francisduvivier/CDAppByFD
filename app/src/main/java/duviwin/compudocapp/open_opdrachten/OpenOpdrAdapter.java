package duviwin.compudocapp.open_opdrachten;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.abstract_opdr_list.GenericOpdracht;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.open_opdrachten.OpenOpdrHtmlInfo.Nms;
/**
 * Created by Duviwin on 6/15/2015.
 */
public class OpenOpdrAdapter extends AbstrOpdrItemAdapter {

    public OpenOpdrAdapter(Context context, int resId, List opdrList) {
        super(context, resId, opdrList);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        //the parent method fills in the textvalues
        View view=super.getView(pos,convertView,parent);
        ShortOpdracht opdr;
        try{
        opdr = (ShortOpdracht) getItem(pos);

        Holder viewHolder=((Holder) view.getTag());
        ((LinearLayout) viewHolder.tvs[Nms.opdrachtNr.index].getParent()).setBackgroundColor(opdr.getNumberClr());
        ((LinearLayout) viewHolder.tvs[Nms.korteUitleg.index].getParent()).setBackgroundColor(opdr.getUitlegClr());

        }catch (ClassCastException e){
            GenericOpdracht opdr2=(GenericOpdracht) getItem(pos);
            Log.d("ShortOpdrachtError","It went wrong with:");
            int i=0;
            for(String s:opdr2.shrtInfo){

                Log.d("ShortOpdrachtError",i++ +": "+s);}
        }
        return view;

    }
}
