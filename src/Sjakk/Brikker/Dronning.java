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
		flytteRegel = new FlytteRegel(true, true, true, true);
		poeng = 25;
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

	@Override
	public Brikke kopierBrikken()
	{
		Brikke b = new Dronning(getBrett(), getRuteNavn(), getFarge());
		b.setAntTrekk(getAntallTrekk());
		return b;
	}
}
