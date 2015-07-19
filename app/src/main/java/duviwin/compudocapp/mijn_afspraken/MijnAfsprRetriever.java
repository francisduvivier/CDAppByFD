package duviwin.compudocapp.mijn_afspraken;

import duviwin.compudocapp.abstract_opdr_list.AbstrListRetriever;
import duviwin.compudocapp.mijn_opdrachten.MijnOpdrHtmlInfo;

public class MijnAfsprRetriever extends AbstrListRetriever {

	public MijnAfsprRetriever(){
		super(new MijnOpdrHtmlInfo());
	}
	@Override
	public String getUrl() {
		return "http://compudoc.be/index.php?page=login/mijn%20gegevens";
	}
}
