package Sjakk.AI;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import Sjakk.Regler.Koordinater;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Jo Øivind Gjernes on 25.10.2015.
 */
public class MiniMax
{
	private int aiDybde = 3;
	private Farge aiFarge;
	private int trekkVurdert = 0;

	public MiniMax(int dybde, Farge aiFarge)
	{
		this.aiDybde = dybde;
		this.aiFarge = aiFarge;
	}

	SpillTrekk minimax(int dybde, Farge spiller, Brett b)
	{
		++trekkVurdert;
		int bestePoeng = (spiller == aiFarge) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

		SpillTrekk besteTrekk = new SpillTrekk(bestePoeng);

		if (dybde == 0) {
			return new SpillTrekk(b.getPoeng(aiFarge));
		}
		LinkedHashMap<Integer, ArrayList<Integer>> muligeTrekk = b.getAlleGyldigeTrekk(spiller);

		if (muligeTrekk.size() == 0) {
			return new SpillTrekk(b.getPoeng(aiFarge, true));
		}

		if (spiller == aiFarge) { // AIen sin tur trekket blir maksimert
			for (Map.Entry<Integer, ArrayList<Integer>> brikke : muligeTrekk.entrySet())
				if (!brikke.getValue().isEmpty()) {
					int fraPos = brikke.getKey();
					for (int tilPos : brikke.getValue()) {
						if (tilPos == fraPos) continue;
						if (b.flyttBrikke(fraPos, tilPos)) {
							SpillTrekk trekk = minimax(dybde - 1, spiller.motsatt(), b);
							if (trekk.getPoeng() > bestePoeng) {
								bestePoeng = trekk.getPoeng();
								besteTrekk.setPoeng(bestePoeng);
								besteTrekk.setTrekk(fraPos, tilPos);
							}
							b.angre();
						} else {
							System.out.println("Skal ikke skje");
						}
					}
				}
		} else { // Ikke AIen sin tur velger minimert trekk
			for (Map.Entry<Integer, ArrayList<Integer>> brikke : muligeTrekk.entrySet())
				if (!brikke.getValue().isEmpty()) {
					int fraPos = brikke.getKey();
				for (int tilPos : brikke.getValue()) {
					if (tilPos == fraPos) continue;
					if (b.flyttBrikke(fraPos, tilPos)) {
						SpillTrekk trekk = minimax(dybde - 1, spiller.motsatt(), b);
						if (trekk.getPoeng() < bestePoeng) {
							bestePoeng = trekk.getPoeng();
							besteTrekk.setPoeng(bestePoeng);
							besteTrekk.setTrekk(fraPos, tilPos);
						}
						b.angre();
					} else {
						System.out.println("Fra " + Koordinater.fraRuteid(brikke.getKey()) + " til " + Koordinater.fraRuteid(tilPos));
						System.out.println("Skal ikke skje");
					}
				}
				}
		}

		return besteTrekk; // Poeng går oppover fra bunnen. Trekk blir "påført" nærmere toppen av treet.
	}

	public int getAntallTrekk()
	{
		return trekkVurdert;
	}

	public void nesteAiTrekk(Brett b)
	{
		trekkVurdert = 0;
		SpillTrekk nesteTrekk = minimax(aiDybde, aiFarge, b);
		nesteTrekk.utfør(b);
	}


}
