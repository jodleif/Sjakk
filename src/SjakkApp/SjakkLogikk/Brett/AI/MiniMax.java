package SjakkApp.SjakkLogikk.Brett.AI;

import SjakkApp.SjakkLogikk.Brett.Brett;
import SjakkApp.SjakkLogikk.Regler.Farge;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Jo Øivind Gjernes on 25.10.2015.
 * <p>
 * AI for spillet. Basert på minimax algoritmen
 * https://en.wikipedia.org/wiki/Minimax
 */
public class MiniMax
{
	private int aiDybde = 3;
	private Farge aiFarge;
	private int trekkVurdert = 0;

	/**
	 * Konstruktør for "aien"
	 *
	 * @param dybde   angir hvor mange trekk AIen skal "se frem"
	 * @param aiFarge angir AI-spillers farge.
	 */
	public MiniMax(int dybde, Farge aiFarge)
	{
		this.aiDybde = dybde;
		this.aiFarge = aiFarge;
	}

	/**
	 * AI-algoritme, rekursiv.
	 *
	 * @param dybde   dybde den skal søke (antall trekk fremover)
	 * @param spiller gjeldene spiller for algoritmen, (fargen for spiller som blir simulert)
	 * @param brett   spillbrettet, som AIen "spiller" på.
	 * @return Det beste trekket AIen fant.
	 */
	SpillTrekk minimax(int dybde, Farge spiller, Brett brett)
	{
		++trekkVurdert;
		int bestePoeng = (spiller == aiFarge) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

		SpillTrekk besteTrekk = new SpillTrekk(bestePoeng);

		if (dybde == 0) {
			return new SpillTrekk(brett.getPoeng(aiFarge));
		}
		LinkedHashMap<Integer, ArrayList<Integer>> muligeTrekk = brett.getAlleGyldigeTrekk(spiller);
		if (muligeTrekk.size() == 0) {
			return new SpillTrekk(brett.getPoeng(aiFarge));
		}

		if (spiller == aiFarge) { // AIen sin tur trekket blir maksimert
			for (Map.Entry<Integer, ArrayList<Integer>> brikke : muligeTrekk.entrySet())
				if (!brikke.getValue().isEmpty()) {
					int fraPos = brikke.getKey();
					for (int tilPos : brikke.getValue()) {
						if (tilPos == fraPos) continue;
						if (brett.flyttBrikke(fraPos, tilPos)) {
							SpillTrekk trekk = minimax(dybde - 1, spiller.motsatt(), brett);
							if (trekk.getPoeng() > bestePoeng) {
								bestePoeng = trekk.getPoeng();
								besteTrekk.setPoeng(bestePoeng);
								besteTrekk.setTrekk(fraPos, tilPos);
							}
							brett.angre();
						}
					}
				}
		} else { // Ikke AIen sin tur velger minimert trekk
			for (Map.Entry<Integer, ArrayList<Integer>> brikke : muligeTrekk.entrySet())
				if (!brikke.getValue().isEmpty()) {
					int fraPos = brikke.getKey();
					for (int tilPos : brikke.getValue()) {
						if (brett.flyttBrikke(fraPos, tilPos)) {
							SpillTrekk trekk = minimax(dybde - 1, spiller.motsatt(), brett);
							if (trekk.getPoeng() < bestePoeng) {
								bestePoeng = trekk.getPoeng();
								besteTrekk.setPoeng(bestePoeng);
								besteTrekk.setTrekk(fraPos, tilPos);
							}
							brett.angre();
						}
					}
				}
		}

		return besteTrekk; // Poeng går oppover fra bunnen. Trekk blir "påført" nærmere toppen av treet.
	}

	/**
	 * @return antall trekk AIen har simulert.
	 */
	public int getAntallTrekk()
	{
		return trekkVurdert;
	}

	/**
	 * Finn neste trekk for AI-spilleren, samt utfør trekket på brettet.
	 *
	 * @param b
	 */
	public void nesteAiTrekk(Brett b)
	{
		trekkVurdert = 0; // Reset antall trekk vurdert.
		SpillTrekk nesteTrekk = minimax(aiDybde, aiFarge, b);
		if (!nesteTrekk.utfør(b)) {
			System.out.println("AIen greide ikke utføre trekket!");
		}
	}


}
