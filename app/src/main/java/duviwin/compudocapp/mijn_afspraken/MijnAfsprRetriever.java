package duviwin.compudocapp.mijn_afspraken;

import duviwin.compudocapp.abstract_opdr_list.AbstrListRetriever;

public class MijnAfsprRetriever extends AbstrListRetriever {

	public MijnAfsprRetriever(){
		super(new MijnAfsprHtmlInfo());
	}
	@Override
	public String getUrl() {
		return "https://www.compudoc.be/index.php?page=appointment/modify&cat=informatie&ond=114&head_menu=11";
	}
}
