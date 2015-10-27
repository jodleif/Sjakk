package Sjakk.Regler;

import Sjakk.Brett.Brett;
import Sjakk.Brett.Brikker.Brikke;

/**
 * Created by Jo Øivind Gjernes on 21.10.2015.
 * <p>
 * Utvidet klasse spesifikt for bønder. De angriper kun diagonalt
 * TODO: Fiks bug - bonde kan angripe skrått "bakover"
 */
public class BondeFlytteRegel extends FlytteRegel
{
	/**
	 * BondeFlytteRegel - konstruktør
	 *
	 * @param maxAvstand maxavstand bonden kan bevege seg
	 */
	public BondeFlytteRegel(int maxAvstand)
	{
		super(false, true, false, false, maxAvstand);
	}

	/**
	 * Egen sjekk for bønder - de kan angripe til venstre
	 *
	 * @param fraPos brikkens utgangspunkt
	 * @param tilPos brikkens mål
	 * @param br     brikken som flyttes
	 * @param brett  spillbrettet
	 * @return sant hvis gyldig trekk
	 */
	@Override
	public boolean gyldigTrekk(int fraPos, int tilPos, Brikke br, Brett brett)
	{
		int fraRutey = fraPos / Brett.BRETTSTØRRELSE;
		int fraRutex = fraPos - fraRutey * Brett.BRETTSTØRRELSE;
		int tilRutey = tilPos / Brett.BRETTSTØRRELSE;
		int tilRutex = tilPos - tilRutey * Brett.BRETTSTØRRELSE;
		int diffx = tilRutex - fraRutex;
		int diffy = tilRutey - fraRutey;

		Retning retning = finnRetning(diffx, diffy, br.getFarge());
		if (br.getAntallTrekk() < 1) {
			maxAvstand = 2;
		} else {
			maxAvstand = 1;
		}
		if (retning == null) return false;

		if (!gyldigAvstand(diffx, diffy)) {
			return false;
		}

		if (kollisjonsSjekk(fraRutex, fraRutey, diffx, diffy, br, brett)) return false;

		Brikke tmp = brett.getBrikke(tilPos);
		if (tmp != null && brett.getBrikke(tilPos).getFarge() != br.getFarge()) {
			if (retning == Retning.FREM) {
				return false; // KAN ikke angripe fremover
			} else if (retning == Retning.DIAGONALT && Koordinater.avstand(diffx, diffy) == 1 && Koordinater.radRetning(diffy, br.getFarge()) == Retning.FREM) {
				// Angriper kun diagonalt og frem
				return true;
			}
		}

		return retning == Retning.FREM;

	}

	@Override
	public boolean gyldigAngrep(int fraPos, int tilPos, Brikke brikke, Brett brett)
	{
		int fraRutey = fraPos / Brett.BRETTSTØRRELSE;
		int fraRutex = fraPos - fraRutey * Brett.BRETTSTØRRELSE;
		int tilRutey = tilPos / Brett.BRETTSTØRRELSE;
		int tilRutex = tilPos - tilRutey * Brett.BRETTSTØRRELSE;
		int diffx = tilRutex - fraRutex;
		int diffy = tilRutey - fraRutey;

		return Math.abs(diffx) == Math.abs(diffy) && (Koordinater.avstand(diffx, diffy) == 1) && (Koordinater.radRetning(diffy, brikke.getFarge()) == Retning.FREM);

	}
}
