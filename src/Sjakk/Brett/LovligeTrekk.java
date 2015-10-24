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

	public LovligeTrekk(Brett brett)
	{
		lovligeTrekkSort = new HashMap<>();
		lovligeTrekkHvit = new HashMap<>();
		genererTrekk(brett);
	}

	private void genererTrekk(Brett brett)
	{
		Brikke[] brikker = brett.getAlleBrikker();
		for (Brikke b : brikker) {
			String[] trekk = b.gyldigeTrekk();
			ArrayList<String> trekkList = new ArrayList<String>(Arrays.asList(trekk));
			trekkList.forEach((string) -> kopleBrikkeMotTrekk(b, string)); // Utfør for alle trekk
		}
	}

	private void kopleBrikkeMotTrekk(Brikke brikke, String trekk)
	{
		Map<Brikke, ArrayList<String>> lovligeTrekk = getLovligeTrekk(brikke.getFarge());
		ArrayList<String> listeOverTrekk = lovligeTrekk.get(brikke);
		if (listeOverTrekk == null) {
			lovligeTrekk.put(brikke,
				new ArrayList<String>(Arrays.asList(new String[]{trekk}))); // Oppretter liste over mulige trekk for brikken og legger til trekket.
		} else {
			listeOverTrekk.add(trekk);
		}
	}

	private Map<Brikke, ArrayList<String>> getLovligeTrekk(Farge f)
	{
		if (f == Farge.HVIT)
			return lovligeTrekkHvit;
		return lovligeTrekkSort;
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

	public boolean erLovlig(Brikke br, String tilPos)
	{
		return sjekkTrekk(getLovligeTrekk(br.getFarge()).get(br), tilPos);
	}
}
