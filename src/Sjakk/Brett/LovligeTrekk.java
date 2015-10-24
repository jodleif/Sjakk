package Sjakk.Brett;

import Sjakk.Brikker.Brikke;
import Sjakk.Regler.Farge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jo Øivind Gjernes on 24.10.2015.
 */
public class LovligeTrekk
{
	private Map<Brikke, ArrayList<String>> lovligeTrekkSort;
	private Map<Brikke, ArrayList<String>> lovligeTrekkHvit;
	private Map<String, ArrayList<Brikke>> lovligeTrekkHvitReverse;
	private Map<String, ArrayList<Brikke>> lovligeTrekkSortReverse;
	private Brikke hvitKonge;
	private Brikke sortKonge;

	public LovligeTrekk(Brett brett)
	{
		lovligeTrekkSort = new HashMap<>();
		lovligeTrekkHvit = new HashMap<>();
		lovligeTrekkHvitReverse = new HashMap<>();
		lovligeTrekkSortReverse = new HashMap<>();
		genererTrekk(brett);
	}

	private void genererTrekk(Brett brett)
	{
		Brikke[] brikker = brett.getAlleBrikker();
		for (Brikke b : brikker) {

		}
		for (Brikke b : brikker) {
			String[] trekk = b.gyldigeTrekk();
			ArrayList<String> trekkList = new ArrayList<String>(Arrays.asList(trekk));
			trekkList.forEach((string) -> kopleBrikkeMotTrekk(b, string)); // Utfør for alle trekk
			if (b.brikkenavn().equals("K")) {
				if (b.getFarge() == Farge.HVIT) {
					hvitKonge = b;
				} else {
					sortKonge = b;
				}
			}
		}
	}

	private void kopleBrikkeMotTrekk(Brikke brikke, String trekk)
	{
		Map<Brikke, ArrayList<String>> lovligeTrekk = getLovligeTrekk(brikke.getFarge());
		Map<String, ArrayList<Brikke>> lovligeTrekkR = getBrikker(brikke.getFarge());
		ArrayList<String> listeOverTrekk = lovligeTrekk.get(brikke);
		ArrayList<Brikke> listeOverBrikker = lovligeTrekkR.get(trekk);
		if (listeOverTrekk == null) {
			lovligeTrekk.put(brikke,
				new ArrayList<String>(Arrays.asList(new String[]{trekk}))); // Oppretter liste over mulige trekk for brikken og legger til trekket.
			lovligeTrekkR.put(trekk,
				new ArrayList<Brikke>(Arrays.asList(new Brikke[]{brikke})));
		} else {
			listeOverTrekk.add(trekk);
			listeOverBrikker.add(brikke);
		}
	}

	private Map<Brikke, ArrayList<String>> getLovligeTrekk(Farge f)
	{
		if (f == Farge.HVIT)
			return lovligeTrekkHvit;
		return lovligeTrekkSort;
	}

	private Map<String, ArrayList<Brikke>> getBrikker(Farge f)
	{
		if (f == Farge.HVIT) {
			return lovligeTrekkHvitReverse;
		}
		return lovligeTrekkSortReverse;
	}

	private boolean sjekkTrekk(ArrayList<String> lovligeTrekk, String trekk)
	{
		if (lovligeTrekk == null) return false;
		for (String s : lovligeTrekk) {
			if (s.equals(trekk)) return true;
		}
		return false;
	}

	public String[] getTrekk(Brikke br)
	{
		ArrayList<String> liste = getLovligeTrekk(br.getFarge()).get(br);
		if (liste != null) return liste.toArray(new String[liste.size()]);
		return null;
	}

	public void renskUtSjakk()
	{
		ArrayList<String> hvitKongeListe = lovligeTrekkHvit.get(hvitKonge);
		ArrayList<String> sortKongeListe = lovligeTrekkSort.get(sortKonge);
		hvitKongeListe.forEach((trekk) -> {

		});
	}

	public boolean erLovlig(Brikke br, String tilPos)
	{
		return sjekkTrekk(getLovligeTrekk(br.getFarge()).get(br), tilPos);
	}

	public Brikke[] kanFlytteTil(String sluttPos, Farge f)
	{
		Map<Brikke, ArrayList<String>> lovligeTrekk = getLovligeTrekk(f);
		ArrayList<Brikke> liste = new ArrayList<>();
		lovligeTrekk.forEach((br, strL) -> strL.forEach((muligPosisjon) -> {
			if (muligPosisjon.equals(sluttPos)) {
				liste.add(br);
				return;
			}
		}));
		if (liste.size() == 0) return null;
		return liste.toArray(new Brikke[liste.size()]);
	}

	public boolean sjakk(Farge f, String kongePos)
	{
		Brikke[] brikker = kanFlytteTil(kongePos, (f == Farge.HVIT) ? Farge.SVART : Farge.HVIT);
		return brikker != null;
	}

}
