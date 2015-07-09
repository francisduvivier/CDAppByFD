package duviwin.compudocapp.mijn_gegevens;

import duviwin.compudocapp.abstract_opdr_list.AbstrOpdrachtenInfo;

public class OpdrachtenInfo extends AbstrOpdrachtenInfo{

	public OpdrachtenInfo(){
		super(new OpdrListHtmlInfo());
	}
	@Override
	public String getUrl() {
		return "http://compudoc.be/index.php?page=login/mijn%20gegevens";
	}
}
