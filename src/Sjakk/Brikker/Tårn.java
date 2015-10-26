package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import Sjakk.Regler.FlytteRegel;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Sjakkbrikken tårn.
 */
public class Tårn extends Offiser
{
	private static final String BRIKKENAVN = "T";

	public Tårn(Brett brett, int ruteid, Farge farge) throws IllegalArgumentException
	{
		super(brett, ruteid, farge);
		flytteRegel = new FlytteRegel(true, true, true, false);
		poeng = 5;
	}

	@Override
	public String brikkenavn()
	{
		return BRIKKENAVN;
	}

	@Override
	public Brikke kopierBrikken()
	{
		Brikke b = new Tårn(getBrett(), getRuteid(), getFarge());
		b.setAntTrekk(getAntallTrekk());
		return b;
	}

	@Override
	public ArrayList<Integer> gyldigeTrekk()
	{
		int ruteid = getRuteid();
		int rutey = ruteid / Brett.BRETTSTØRRELSE;
		int rutex = ruteid - (rutey << 3);
		ArrayList<Integer> gyldigeTrekk = super.gyldigeTrekk(rutex, rutex, 0, 7);
		gyldigeTrekk.addAll(super.gyldigeTrekk(0, 7, rutey, rutey));
		return gyldigeTrekk;
	}
}
