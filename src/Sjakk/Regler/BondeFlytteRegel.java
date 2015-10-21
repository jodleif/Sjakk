package Sjakk.Regler;

import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;

/**
 * Created by Jo Øivind Gjernes on 21.10.2015.
 *
 * Utvidet klasse spesifikt for bønder. De angriper kun diagonalt
 * TODO: Fiks bug - bonde kan angripe skrått "bakover"
 */
public class BondeFlytteRegel extends FlytteRegel
{
	/**
	 * BondeFlytteRegel - konstruktør
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
		if (retning == null) return false;

		if (maxAvstand != 0 && (!gyldigAvstand(fraPos, tilPos))) {
			return false;
		}

		if (kollisjonsSjekk(fraPos, tilPos, brett)) return false;

		Brikke tmp = brett.getBrikke(tilPos);
		if (tmp != null && brett.getBrikke(tilPos).getFarge() != br.getFarge()) {
			if (retning == Retning.FREM) {
				return false; // KAN ikke angripe fremover
			} else if (retning == Retning.DIAGONALT) {
				if (Koordinater.avstand(fraPos, tilPos) == 1) {
					return true; // Angriper kun diagonalt!
				} else {
					return false;
				}
			}
		}

		if (retning == Retning.FREM) return true;

		return false;
	}
}
