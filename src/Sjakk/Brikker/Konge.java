package Sjakk.Brikker;

import Sjakk.Brett.Brett;
import Sjakk.Regler.Farge;
import Sjakk.Regler.FlytteRegel;

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
	 * @param ruteid
	 * @return
	 */
	private boolean sjekkSjakk(int ruteid)
	{
		return false;
		/**
		 * Tungvindt metode- brikken "flyttes" på brettet hvis feltet er åpent, så sjekkes det om den (kongen) fortsatt står i sjakk.
		 */
		/*
		if (getBrett().getBrikke(ruteid) == null) {
			return getBrett().simulerSjakkTest(this, ruteid);
		} else {

			ArrayList<Brikke> brikker = getBrett().getAlleBrikker(getFarge().motsatt());
			ArrayList<Brikke> setterISjakk = new ArrayList<>();
			for (Brikke brikke : brikker) {
				if (brikke.erLovligAngrep(ruteid)) {
					setterISjakk.add(brikke);
				}
			}
			if (setterISjakk.size() > 1) {
				return true;
			} else if (setterISjakk.size() == 1) {
				return !(setterISjakk.get(0).getRuteid() == ruteid);
			}
			return false;
		}
		*/
	}

	@Override
	public ArrayList<Integer> gyldigeTrekk()
	{
		int ruteid = getRuteid();
		int rutey = ruteid / Brett.BRETTSTØRRELSE;
		int rutex = ruteid - (rutey << 3);
		return gyldigeTrekk(Math.max(0, rutex - 1), Math.min(rutex + 1, 7), Math.max(rutey - 1, 0), Math.min(rutey + 1, 7));
	}

	@Override
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
	}

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

	public boolean rokker(boolean kongeside)
	{
		getBrett().Rokker(kongeside, getFarge());
		return true; // TODO: implementer i spillet, legge til som gyldig trekk.
	}
}
