package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import Sjakk.Regler.FlytteRegel;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Sjakk-brikke av type "Dronning"
 */
public class Dronning extends Offiser
{
	private static final FlytteRegel flytteRegel = new FlytteRegel(true, true, true, true);

	/**
	 * Lag en ny dronning
	 * @param brett spillbrettet dronningen er på
	 * @param rutenavn ruten dronningen står i
	 * @param farge dronningens farge
	 * @throws IllegalArgumentException
	 */
	public Dronning(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
	{
		super(brett, rutenavn, farge);
	}

	/**
	 * Implementasjon av erLovligTrekk. Sjekker om trekket er lovlig (i brikkens spillregler)
	 * @param rutenavn navnet på ruten brikken skal flyttes til. på sjakkformat i.e. a1
	 * @return true hvis trekket er tillat
	 */
	@Override
	public boolean erLovligTrekk(String rutenavn)
	{
		return flytteRegel.gyldigTrekk(getRuteNavn(), rutenavn, this, getBrett());
	}

	/**
	 * Brikkens type
	 * @return D for dronning.
	 */
	@Override
	public String brikkenavn()
	{
		return "D";
	}
}
