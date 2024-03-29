package SjakkApp.SjakkLogikk.Brett.Brikker;

import SjakkApp.SjakkLogikk.Brett.Brett;
import SjakkApp.SjakkLogikk.Regler.Farge;
import SjakkApp.SjakkLogikk.Regler.FlytteRegel;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Sjakkbrikken "konge"
 */
public class Konge extends Offiser
{
	public Konge(Brett brett, int ruteid, Farge farge) throws IllegalArgumentException
	{
		super(brett, ruteid, farge);
		flytteRegel = new FlytteRegel(true, true, true, true, 1);
		poeng = 1000;
	}


	/**
	 * Begrenser søkeområdet til gyldige trekk - basert på brikkens muligheter
	 *
	 * @return Liste over mulige / gyldige trekk.
	 */
	@Override
	public ArrayList<Integer> gyldigeTrekk()
	{
		int ruteid = getRuteid();
		int rutey = ruteid / Brett.BRETTSTØRRELSE;
		int rutex = ruteid - (rutey * Brett.BRETTSTØRRELSE);
		return gyldigeTrekk(Math.max(0, rutex - 1), Math.min(rutex + 1, 7), Math.max(rutey - 1, 0), Math.min(rutey + 1, 7));
	}

	/*
	@Override
	// Forsøkt å implementere sjakk - lagt på is
	//
	public boolean sjekkSjakk()
	{
		ArrayList<Brikke> brikker = getBrett().getAlleBrikker(getFarge().motsatt());
		int setterisjakk = 0;
		for (Brikke brikke : brikker) {
			if (brikke.erLovligAngrep(getRuteid())) {
				++setterisjakk;
			}
		}
		return setterisjakk != 0;
	}*/

	@Override
	public String brikkenavn()
	{
		return "K";
	}

	@Override
	public Brikke kopierBrikken()
	{
		Brikke b = new Konge(getBrett(), getRuteid(), getFarge());
		b.setAntTrekk(getAntallTrekk());
		return b;
	}
}
