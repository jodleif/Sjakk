package Sjakk.AI;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jo Øivind Gjernes on 25.10.2015.
 */
public class MiniMax
{
	private int aiDybde = 3;
	private Farge aiFarge;
	private int antallTrekk = 0;

	public MiniMax(int dybde, Farge aiFarge)
	{
		this.aiDybde = dybde;
		this.aiFarge = aiFarge;
	}

	public MiniMax()
	{
		this.aiFarge = Farge.SVART;
	}

	SpillTrekk minimax(int dybde, Farge spiller, Brett b)
	{
		++antallTrekk;
		int bestePoeng = (spiller == aiFarge) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		SpillTrekk besteTrekk = new SpillTrekk(null, null);
		if (dybde == 0) {
			return new SpillTrekk(b.getPoeng(aiFarge));
		}
		HashMap<String, ArrayList<String>> muligeTrekk = b.getAlleGyldigeTrekk(spiller);
		if (muligeTrekk.isEmpty()) {
			return new SpillTrekk(b.getPoeng(aiFarge));
		}
		if (b.getSpillerSinTur() == aiFarge) { // AIen sin tur
				for (Map.Entry<String, ArrayList<String>> brikke : muligeTrekk.entrySet())
					for (String tilPos : brikke.getValue()) {
						b.flyttBrikke(brikke.getKey(), tilPos);
						SpillTrekk trekk = minimax(dybde - 1, spiller.motsatt(), b);
						if (trekk.getPoeng() > bestePoeng) {
							bestePoeng = trekk.getPoeng();
							besteTrekk.setPoeng(bestePoeng);
							besteTrekk.setTrekk(brikke.getKey(), tilPos);
						}
						b.angre();
					}
			} else { // Ikke AIen sin tur
				for (Map.Entry<String, ArrayList<String>> brikke : muligeTrekk.entrySet())
					for (String tilPos : brikke.getValue()) {
						b.flyttBrikke(brikke.getKey(), tilPos);
						SpillTrekk trekk = minimax(dybde - 1, spiller.motsatt(), b);
						if (trekk.getPoeng() < bestePoeng) {
							bestePoeng = trekk.getPoeng();
							besteTrekk.setPoeng(bestePoeng);
							besteTrekk.setTrekk(brikke.getKey(), tilPos);
						}
						b.angre();
					}
			}


		return besteTrekk; // Poeng går oppover fra bunnen. Trekk blir "påført" nærmere toppen av treet.
	}

	public void nesteAiTrekk(Brett b)
	{
		antallTrekk = 0;
		SpillTrekk nesteTrekk = minimax(aiDybde, aiFarge, b);
		if (nesteTrekk.utfør(b)) ;
	}

	public int getAntallTrekk()
	{
		return antallTrekk;
	}
}
