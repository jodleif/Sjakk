package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.BondeFlytteRegel;
import Sjakk.Regler.Farge;

import java.util.ArrayList;

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
	 * @param ruteid rutenavnet der brikken er plassert
	 * @param farge brikkens farge
	 * @throws IllegalArgumentException
	 */
	public Bonde(Brett brett, int ruteid, Farge farge) throws IllegalArgumentException
	{
		super(brett, ruteid, farge);
		flytteRegel = new BondeFlytteRegel(2); // Kan kun flytte fremover. 2 i første trekk
		poeng = 1;
	}

	@Override
	public boolean erLovligAngrep(int ruteid)
	{
		return flytteRegel.gyldigAngrep(getRuteid(), ruteid, this, getBrett());
	}


	@Override
	public ArrayList<Integer> gyldigeTrekk()
	{
		int rutey = getRuteid() / Brett.BRETTSTØRRELSE;
		int rutex = getRuteid() - rutey * Brett.BRETTSTØRRELSE;
		int maxY = (getAntallTrekk() == 0) ? 2 : 1;
		if (getFarge() == Farge.HVIT) {
			return gyldigeTrekk(Math.max(rutex - 1, 0), Math.min(rutex + 1, 7), Math.min(rutey + 1, 7), Math.min(rutey + maxY, 7));
		} else {
			return gyldigeTrekk(Math.max(rutex - 1, 0), Math.min(rutex + 1, 7), Math.max(rutey - maxY, 0), Math.max(rutey - 1, 0));
		}
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
		Brikke b = new Bonde(getBrett(), getRuteid(), getFarge());
		b.setAntTrekk(getAntallTrekk());
		return b;
	}
}
