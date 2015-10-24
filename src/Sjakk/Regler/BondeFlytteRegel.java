package Sjakk.Regler;

import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;

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
	public boolean gyldigTrekk(String fraPos, String tilPos, Brikke br, Brett brett)
	{
		Retning retning = finnRetning(fraPos, tilPos, br.getFarge());
		if (br.getAntallTrekk() < 1) {
			maxAvstand = 2;
		} else {
			maxAvstand = 1;
		}
		if (retning == null) return false;

		if (!gyldigAvstand(fraPos, tilPos)) {
			return false;
		}

		if (kollisjonsSjekk(fraPos, tilPos, br, brett)) return false;

		Brikke tmp = brett.getBrikke(tilPos);
		if (tmp != null && brett.getBrikke(tilPos).getFarge() != br.getFarge()) {
			if (retning == Retning.FREM) {
				return false; // KAN ikke angripe fremover
			} else if (retning == Retning.DIAGONALT && Koordinater.avstand(fraPos, tilPos) == 1 && Koordinater.radRetning(fraPos, tilPos, br.getFarge()) == Retning.FREM) {
				// Angriper kun diagonalt og frem
				return true;
			}
		}

		return retning == Retning.FREM;

	}

	@Override
	public boolean gyldigAngrep(String fraPos, String tilPos, Brikke brikke, Brett brett)
	{
		Retning retning = finnRetning(fraPos, tilPos, brikke.getFarge());
		if (retning == null) return false;
		return retning == Retning.DIAGONALT && Koordinater.avstand(fraPos, tilPos) == 1 && Koordinater.radRetning(fraPos, tilPos, brikke.getFarge()) == Retning.FREM;

	}
}
