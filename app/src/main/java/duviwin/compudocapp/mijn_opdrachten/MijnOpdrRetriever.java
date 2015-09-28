package duviwin.compudocapp.mijn_opdrachten;

import duviwin.compudocapp.abstract_opdr_list.AbstrListRetriever;

public class MijnOpdrRetriever extends AbstrListRetriever {

	public MijnOpdrRetriever(){
		super(new MijnOpdrHtmlInfo());
	}
	@Override
	public String getUrl() {
		return "https://www.compudoc.be/index.php?page=login/mijn%20gegevens";
	}
}
