package duviwin.compudocapp.mijn_afspraken;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrItemAdapter;
import duviwin.compudocapp.abstract_opdr_list.GenericOpdracht;
import duviwin.compudocapp.mijn_afspraken.MijnAfsprHtmlInfo.Nms;
/**
 * Created by Duviwin on 6/15/2015.
 */
public class MijnAfsprAdapter extends AbstrOpdrItemAdapter {

    public MijnAfsprAdapter(Context context, int resId, List<GenericOpdracht> opdrList) {
        super(context, resId, opdrList);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        //the parent method fills in the textvalues
        View view=super.getView(pos,convertView,parent);
        GenericOpdracht opdr=(GenericOpdracht) getItem(pos);
//        if(!opdr.isDummy)Log.d("MijnAfsprAdapter", "opmerkview:"+opdr.shrtInfo[Nms.opmerking.getIndex()].replaceAll("[\\t ]","")+";");

        if(!opdr.isDummy&&opdr.shrtInfo[Nms.opmerking.getIndex()].replaceAll("[\\t ]","").length()==0){
            Holder h=(Holder) view.getTag();
            View opmerkView=h.tvs[MijnAfsprHtmlInfo.Nms.opmerking.getIndex()];
            ((LinearLayout) opmerkView.getParent()).removeView(opmerkView);
            ((TextView)opmerkView).setText("geen opmerking");
//            Log.d("MijnAfsprAdapter", "removing opmerkview");
        }
        return view;
    }
}
