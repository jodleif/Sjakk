package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.BondeFlytteRegel;
import Sjakk.Regler.Farge;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Sjakk-brikken "bonde"
 */
public class Bonde extends Brikke
{
	public static final String BRIKKENAVN = "B";

	/**
	 * Konstruktør
	 * @param brett brettet brikken er plassert på
	 * @param rutenavn rutenavnet der brikken er plassert
	 * @param farge brikkens farge
	 * @throws IllegalArgumentException
	 */
	public Bonde(Brett brett, String rutenavn, Farge farge) throws IllegalArgumentException
	{
		super(brett, rutenavn, farge);
		flytteRegel = new BondeFlytteRegel(2); // Kan kun flytte fremover. 2 i første trekk
	}

	@Override
	public boolean erLovligAngrep(String rutenavn)
	{
		return flytteRegel.gyldigAngrep(getRuteNavn(), rutenavn, this, getBrett());
	}

	/**
	 * Sørger for at den flytter seg kun 2 på første trekk.
	 * @param ruteNavn ruten brikken skal flyttes til.
	 * @return
	 */
	@Override
	public boolean flyttTil(String ruteNavn)
	{
		boolean status = super.flyttTil(ruteNavn);
		return status;
	}

	/**
	 * Brikkens "type"
	 * @return alltid B. (brikken er av type Bonde)
	 */
	@Override
	public String brikkenavn()
	{
		return BRIKKENAVN; // Bonde
	}

	@Override
	public Brikke kopierBrikken()
	{
		Brikke b = new Bonde(getBrett(), getRuteNavn(), getFarge());
		b.setAntTrekk(getAntallTrekk());
		return b;
	}
}
