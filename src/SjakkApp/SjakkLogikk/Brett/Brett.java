package SjakkApp.SjakkLogikk.Brett;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */

import SjakkApp.SjakkLogikk.Brett.Brikker.*;
import SjakkApp.SjakkLogikk.Brett.Historikk.Trekk;
import SjakkApp.SjakkLogikk.Regler.Farge;
import SjakkApp.SjakkLogikk.Regler.Koordinater;
import SjakkApp.SjakkLogikk.Regler.StartPosisjonRegler;

import java.util.*;

/***
 * Klasse for sjakkbrettet i spill-logikk.
 */
public class Brett
{
	public static final int BRETTSTØRRELSE = 8; // Vil ha tilgang til denne fra SjakkApp
	private int spillNr;
	private Brikke[] brikkene;
	private Deque<Trekk> spillTrekk; // Lagrer trekkene i spillet.
	private Farge nesteTrekk = Farge.HVIT;
	private int poeng;


	/**
	 * Opprett et nytt spillbrett
	 *
	 * @param nyttSpillNr    nummeret på spillet.
	 * @param tomtSpillbrett skal vi lage et tomt brett?
	 */
	public Brett(int nyttSpillNr, boolean tomtSpillbrett)
	{
		this.spillNr = nyttSpillNr;
		brikkene = new Brikke[BRETTSTØRRELSE * BRETTSTØRRELSE];
		spillTrekk = new ArrayDeque<>(); // Spilltrekk.

		if (!tomtSpillbrett) {
			try {
				opprettSpillbrikker();
			} catch (IllegalArgumentException e) {
				System.err.println("[BRETT] Feil under konstruksjon!\n" + e.getMessage());
			}
		}
		lagreTrekk(); // Tomt snapshot - representerer startsbrettet.
	}

	/**
	 * "Standard konstruktør" - vanlig startposisjon for brikker
	 *
	 * @param nyttSpillNr spillnr
	 */
	public Brett(int nyttSpillNr)
	{
		this(nyttSpillNr, false);
	}

	/**
	 * Er rutenavnet et gyldig rutenavn?
	 * merk: flyttet ut i en separat "hjelpeklasse" metoden kan fjernes, men dette krever noen endringer i koden i andre metoder
	 *
	 * @param rutenummer er dette rutenavnet gyldig?
	 * @return true hvis det er et gyldig rutenavn (a1-h8)
	 */
	public static boolean erLovligRutenavn(int rutenummer)
	{
		return (rutenummer >= 0 && rutenummer < 64);
	}

	/**
	 * Opprett spillbrikker- og plasser dem på sine startsposisjoner
	 * Startposisjoner er beskrevet i klassen StartPosisjonsRegler.
	 *
	 * @throws IllegalArgumentException
	 */
	private void opprettSpillbrikker() throws IllegalArgumentException
	{

		for (int j = 1; j < BRETTSTØRRELSE; j += (BRETTSTØRRELSE - 3)) { // Andre rad og sjette rad.
			for (int i = 0; i < BRETTSTØRRELSE; ++i) {
				Farge farge = finnFargeFraRad(j);
				int ruteid = i + j * BRETTSTØRRELSE;
				brikkene[ruteid] = new Bonde(this, ruteid, farge);
			}
		}
		for (String tårnPos : StartPosisjonRegler.TÅRNPOSISJONER) {
			int ruteid = Koordinater.tilRuteid(tårnPos);
			brikkene[ruteid] = new Tårn(this, ruteid, finnFargeFraRad(ruteid / BRETTSTØRRELSE));
		}
		for (String springerPos : StartPosisjonRegler.SPRINGERPOSISJONER) {
			int ruteid = Koordinater.tilRuteid(springerPos);
			Farge farge = finnFargeFraRad(ruteid / BRETTSTØRRELSE);
			brikkene[ruteid] = new Springer(this, ruteid, farge);
		}
		for (String løperPos : StartPosisjonRegler.LØPERPOSISJONER) {
			int ruteid = Koordinater.tilRuteid(løperPos);
			Farge farge = finnFargeFraRad(ruteid / BRETTSTØRRELSE);
			brikkene[ruteid] = new Løper(this, ruteid, farge);
		}
		for (String kongePos : StartPosisjonRegler.KONGEPOSISJONER) {
			int ruteid = Koordinater.tilRuteid(kongePos);
			Farge farge = finnFargeFraRad(ruteid / BRETTSTØRRELSE);
			brikkene[ruteid] = new Konge(this, ruteid, farge);
		}
		for (String dronningPos : StartPosisjonRegler.DRONNINGPOSISJONER) {
			int ruteid = Koordinater.tilRuteid(dronningPos);
			Farge farge = finnFargeFraRad(ruteid / BRETTSTØRRELSE);
			brikkene[ruteid] = new Dronning(this, ruteid, farge);
		}

	}

	/**
	 * Finner farge fra rad-nummer, brukes kun under oppretting av spillbrikker
	 *
	 * @param rad radnummer
	 * @return farge på spillbrikken
	 */
	private Farge finnFargeFraRad(int rad)
	{
		return rad <= 1 ? Farge.HVIT : Farge.SVART;
	}

	/**
	 * Hent spillbrikken i den spesifiserte ruten.
	 *
	 * @param ruteid navnet på ruten
	 * @return brikken i rutenavn, null dersom ruten er tom.
	 */
	public Brikke getBrikke(int ruteid)
	{
		return brikkene[ruteid];
	}

	/**
	 * Hent spillbrikken i den spesifiserte ruten
	 * @param rutenavn rutenavn i.e. "e4"
	 * @return Brikken i rutenavn
	 */
	public Brikke getBrikke(String rutenavn)
	{
		if (rutenavn != null) {
			int ruteid = Koordinater.tilRuteid(rutenavn);
			return getBrikke(ruteid);
		}
		return null;
	}

	/**
	 * Set spillbrikke
	 *
	 * @param ruteid
	 * @param brikke
	 */
	private void setBrikke(int ruteid, Brikke brikke)
	{
		if (brikke != null) {
			Brikke tmp = getBrikke(ruteid);
			if (tmp != null) {
				if (tmp.getFarge() == Farge.SVART) { // Sjekker om hvit fjerner svart brikke = + poeng for hvit
					poeng += tmp.getPoeng();
				} else {
					poeng -= tmp.getPoeng(); // Svart dreper hvit, trekk fra poeng!
				}
			}
			brikke.setRuteid(ruteid); // Sørg for at brikken har korrekt informasjon
			brikke.økAntallTrekk();
			brikkene[ruteid] = brikke;
		} else {
			System.err.println("[Brett.setBrikke] Prøvde å sette null!");
		}
	}

	/**
	 * Grensesnitt for setting av brikker
	 * @param rutenavn navnet på ruten i.e. "e8"
	 * @param brikke brikken man skal sette
	 */
	public void setBrikke(String rutenavn, Brikke brikke)
	{
		if (rutenavn != null) {
			int ruteid = Koordinater.tilRuteid(rutenavn);
			setBrikke(ruteid, brikke);
		}

	}

	private void fjernBrikke(int ruteid)

	{
		brikkene[ruteid] = null;
	}

	public void fjernBrikke(String rutenavn)
	{
		if (rutenavn != null) {
			int ruteid = Koordinater.tilRuteid(rutenavn);
			fjernBrikke(ruteid);
		}
	}

	/*
	 * Metoden flytter en brikke. Sjekker om felter er gyldige, men ikke om et eventuelt sjakktrekk er gyldig
	 *
	 * @param fraRute Ruten man flytter fra
	 * @param tilRute Ruten man flytter til
	 * @return Sann hvis vi flyttet brikken
	 */
	/*
	public boolean flyttBrikke(String fraRute, String tilRute)
	{
		if (!(erLovligRutenavn(fraRute) && erLovligRutenavn(tilRute))) {
			return false;
		}
		// Sjekk at det står en brikke der vi flytter fra
		Brikke br = getBrikke(fraRute);
		if (br == null)
			return false;


		if (!br.erLovligTrekk(tilRute))
			return false;
		//Lagre snapshot
		lagreTrekk();

		// utfør trekk
		setBrikke(tilRute, getBrikke(fraRute));
		fjernBrikke(fraRute);
		nesteTrekk = nesteTrekk.motsatt();
		return true;
	}
	*/

	/**
	 * Flyttbrikke vha ruteid.
	 *
	 * @param fraRuteid
	 * @param tilRuteid
	 * @return
	 */
	public boolean flyttBrikke(int fraRuteid, int tilRuteid)
	{
		if (fraRuteid == tilRuteid) {
			return false;
		}
		if (!(erLovligRutenavn(fraRuteid) && erLovligRutenavn(tilRuteid))) {
			return false;
		}

		Brikke br = getBrikke(fraRuteid);
		if (br == null) {
			//System.out.println("Fra brikke == null");
			return false;
		}
		if (!br.erLovligTrekk(tilRuteid)) {
			//System.out.println("Ulovlig trekk");
			if (br.getRuteid() != fraRuteid) {
				System.out.println("Uvalidert brikke");
			}
			return false;
		}
		//Lagre snapshot
		lagreTrekk(fraRuteid, tilRuteid);

		setBrikke(tilRuteid, br);
		fjernBrikke(fraRuteid);
		nesteTrekk = nesteTrekk.motsatt();
		return true;
	}

	public boolean flyttBrikke(String fraRute, String tilRute)
	{
		if (fraRute != null && tilRute != null)
			return flyttBrikke(Koordinater.tilRuteid(fraRute), Koordinater.tilRuteid(tilRute));
		return false;
	}

	public boolean erKongenDød(Farge f)
	{
		for (Brikke b : getAlleBrikker(f)) {
			if (b.brikkenavn().equals("K")) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Gir en liste over alle mulige trekk for brikken
	 *
	 * @param farge farge for brikker du vil ha alle trekk av
	 * @return Lister for gyldige trekk for alle brikker av farge
	 */
	public LinkedHashMap<Integer, ArrayList<Integer>> getAlleGyldigeTrekk(Farge farge)
	{
		boolean spillerISjakk = erKongenDød(farge);
		LinkedHashMap<Integer, ArrayList<Integer>> listeOverGyldigeTrekk = new LinkedHashMap<>();
		if (spillerISjakk) return listeOverGyldigeTrekk;
		ArrayList<Brikke> fargeBrikker = getAlleBrikker(farge);
		for (Brikke brikke : fargeBrikker) {
			ArrayList<Integer> trekk = brikke.gyldigeTrekk();
			if (!trekk.isEmpty()) {
				listeOverGyldigeTrekk.put(brikke.getRuteid(), trekk);
			}
		}
		return listeOverGyldigeTrekk;

	}


	/**
	 * Gir en liste over alle brikker av Farge f;
	 * @param farge
	 * @return
	 */
	public ArrayList<Brikke> getAlleBrikker(Farge farge)
	{
		ArrayList<Brikke> brikker = new ArrayList<>();
		int len = BRETTSTØRRELSE * BRETTSTØRRELSE;
		for (int i = 0; i < len; ++i) {
			Brikke tmp = brikkene[i];
			if (tmp != null && tmp.getFarge() == farge) {
				brikker.add(tmp);
			}
		}
		Collections.shuffle(brikker); // Gi litt tilfeldigheter til AItrekk
		return brikker;
	}

	/**
	 * Finner antall brikker fortsatt i live
	 * @return
	 */
	private int antallBrikkerILive()
	{
		int antall = 0;
		int len = BRETTSTØRRELSE * BRETTSTØRRELSE;
		for (int i = 0; i < len; ++i) {
			if (brikkene[i] != null)
				++antall;

		}
		return antall;
	}

	/**
	 * Henter det "nyligeste" (forrige) trekket og setter spillbrettet til det.
	 *
	 * @return
	 */
	public boolean angre()
	{
		if (spillTrekk.size() == 0)
			return false;
		Trekk tr = spillTrekk.pop();


		Brikke fjernetBrikke = tr.getFjernetBrikke();
		Brikke flyttetBrikke = tr.getFlyttetBrikke();
		if (flyttetBrikke == null) // TOMT SPILLBRETT
		{
			brikkene = new Brikke[BRETTSTØRRELSE * BRETTSTØRRELSE];
			opprettSpillbrikker();
		}
		if (fjernetBrikke == null && flyttetBrikke != null) {
			angreSetBrikke(flyttetBrikke);
			fjernBrikke(tr.getFlyttTil());
		} else if (fjernetBrikke != null && flyttetBrikke != null) {
			angreSetBrikke(flyttetBrikke);
			angreSetBrikkeFjernet(fjernetBrikke);
		}

		nesteTrekk = tr.getSpillerSittTrekk();
		poeng = tr.getPoeng();
		return true;
	}

	private void angreSetBrikke(Brikke br)
	{
		brikkene[br.getRuteid()] = br;
	}

	/**
	 * Restore en fjernet brikke
	 *
	 * @param fjernetBrikke
	 */
	public void angreSetBrikkeFjernet(Brikke fjernetBrikke)
	{
		if (fjernetBrikke.getFarge() == Farge.HVIT) {
			poeng -= fjernetBrikke.getPoeng();
		} else {
			poeng += fjernetBrikke.getPoeng();
		}
		angreSetBrikke(fjernetBrikke);
	}


	public void lagreTrekk()
	{
		spillTrekk.push(new Trekk(null, 0, null, nesteTrekk, poeng));
	}

	public void lagreTrekk(int fraruteid, int tilruteid)
	{
		Brikke b = getBrikke(fraruteid);
		if (b == null) {
			System.out.println("ERROR!");
		}
		spillTrekk.push(new Trekk(b, tilruteid, getBrikke(tilruteid), nesteTrekk, poeng));
	}

	public Trekk seSisteTrekk()
	{
		return spillTrekk.peek();
	}

	/**
	 * Rokkering - ikke for spill, for avspilling av "replays"
	 *
	 * @param kongeside hvis kongeside rokkering
	 * @param farge     spiller.
	 *                  TODO: implementer inkrementering av trekk.
	 */
	public void Rokker(boolean kongeside, Farge farge)
	{
		lagreTrekk();
		if (kongeside) {
			if (farge == Farge.HVIT) {
				setBrikke("g1", getBrikke("e1"));
				fjernBrikke("e1");
				setBrikke("f1", getBrikke("h1"));
				fjernBrikke("h1");
			} else {
				setBrikke("g8", getBrikke("e8"));
				fjernBrikke("e8");
				setBrikke("f8", getBrikke("h8"));
				fjernBrikke("h8");
			}
		} else {
			if (farge == Farge.HVIT) {
				setBrikke("c1", getBrikke("e1"));
				fjernBrikke("e1");
				setBrikke("d1", getBrikke("a1"));
				fjernBrikke("a1");
			} else {
				setBrikke("c8", getBrikke("e8"));
				fjernBrikke("e8");
				setBrikke("d8", getBrikke("a8"));
				fjernBrikke("a8");
			}
		}
		nesteTrekk = nesteTrekk.motsatt();
	}

	public Farge getSpillerSinTur()
	{
		return nesteTrekk;
	}

	public int getPoeng(Farge spiller)
	{
		if (spiller == Farge.HVIT) {
			return poeng;
		} else {
			return 0 - poeng;
		}
	}
	/*
	public int getPoeng(Farge spiller, boolean sjekkSjakkmatt)
	{
		if (getAlleGyldigeTrekk(spiller.motsatt()).size() == 0) {
			return getPoeng(spiller) + 1000;
		}
		if (getAlleGyldigeTrekk(spiller).size() == 0) {
			return getPoeng(spiller) - 1000;
		}
		return getPoeng(spiller);
	}*/
}
