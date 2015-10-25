package Sjakk.Regler;

import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 * <p>
 * Inneholder definerbare regler for hvordan en brikke skal ha lov til å bevege seg.
 * Alle "private" felter er gjort protected pga jeg har utvidet regelen med noen spesialklausuler for bonde
 */
public class FlytteRegel
{
	protected boolean hoppOverAndre = false; // Standard.
	protected boolean flytteSidelengs;
	protected boolean flytteFremover; // Alle kan dette
	protected boolean flytteBakover; // Bønder kan ikke dette.
	protected boolean flytteDiagonalt;
	protected boolean flytteSpringer = false;
	protected int maxAvstand = 0;

	/**
	 * Flytteregel for en brikke
	 * @param flytteSidelengs Har lov til å flytte sidelengs (a-h)
	 * @param flytteFremover Har lov til å flytte fremover (avhengig av farge)
	 * @param flytteBakover Har lov til å flytte bakover
	 * @param flytteDiagonalt Har lov til å flytte diagonalt
	 */
	public FlytteRegel(boolean flytteSidelengs, boolean flytteFremover, boolean flytteBakover, boolean flytteDiagonalt)
	{
		this.flytteSidelengs = flytteSidelengs;
		this.flytteFremover = flytteFremover;
		this.flytteBakover = flytteBakover;
		this.flytteDiagonalt = flytteDiagonalt;
	}

	/**
	 * Flytteregel for en brikke som kun kan bevege seg en begrenset lengde
 	 * @param flytteSidelengs Har brikken lov til å flytte sidelengs
	 * @param flytteFremover Har brikken lov til å flytte fremover
	 * @param flytteBakover Har brikken lov til å flytte bakover
	 * @param flytteDiagonalt Har brikken lov til å flytte diagonalt?
	 * @param maxAvstand Maksimalt antall ruter brikken kan forflytte seg.
	 */
	public FlytteRegel(boolean flytteSidelengs, boolean flytteFremover, boolean flytteBakover, boolean flytteDiagonalt, int maxAvstand)
	{
		this(flytteSidelengs, flytteFremover, flytteBakover, flytteDiagonalt);
		this.maxAvstand = maxAvstand;
	}

	/**
	 * Flytteregel for en brikke
	 * @param flytteSpringer Har lov til å flytte seg etter springer regler
	 * @param hoppOverAndre Har lov til å hoppe over andre brikker
	 */
	public FlytteRegel(boolean hoppOverAndre, boolean flytteSpringer)
	{
		this.hoppOverAndre = hoppOverAndre;
		this.flytteSidelengs = false;
		this.flytteFremover = false;
		this.flytteBakover = false;
		this.flytteDiagonalt = false;
		this.flytteSpringer = true;
	}

	/**
	 * Metode som sjekker rutene mellom fra og til posisjon for � finne ut om det er noen brikker "p� veien"
	 *
	 * @param fraPos brikkens fra-posisjon
	 * @param tilPos brikkens til-posisjon
	 * @param brett  spillbrettet
	 * @return true hvis det oppst�r kollisjoner.
	 */
	protected static boolean kollisjonsSjekk(int fraPos, int tilPos, Brikke brikke, Brett brett)
	{
		/*Brikke tmp = brett.getBrikke(tilPos);
		if(tmp!=null&&tmp.getFarge()== brikke.getFarge()){
			return true;
		}*/
		ArrayList<Integer> diff = ruterMellom(fraPos, tilPos);
		if(diff==null)
			return false;
		for (Integer ruteid : diff) {
			if (brett.getBrikke(ruteid) != null) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Bestemmer retningen flyttingen går i
	 *
	 * @param fraRute brikkens fra-posisjon
	 * @param tilRute brikkens til-posisjon
	 * @param farge  brikkens farge (trengs for å finne ut om man beveger seg fremover eller bakover)
	 * @return Returnerer hvilken retning bevegelsen vil være - (SE enumen Retning)
	 */
	protected static Retning finnRetning(int fraRute, int tilRute, Farge farge)
	{
		int fraRutey = fraRute / Brett.BRETTSTØRRELSE;
		int fraRutex = fraRute - fraRutey * Brett.BRETTSTØRRELSE;
		int tilRutey = tilRute / Brett.BRETTSTØRRELSE;
		int tilRutex = tilRute - tilRutey * Brett.BRETTSTØRRELSE; // Skal være raskere enn tilRUte%Brettstørrelse (x retning går aldri opp i 8 (0-7))
		int diffx = tilRutex - fraRutex;
		int diffy = tilRutey - fraRutey;
		// Frem og tilbake
		if (diffx == 0 && diffy != 0) {
			if (diffy > 0) {
				return (farge == Farge.HVIT) ? Retning.FREM : Retning.BAKOVER;
			}
			if (diffy < 0) {
				return (farge == Farge.HVIT) ? Retning.BAKOVER : Retning.FREM;
			}
		}
		// Sidelengs
		if (diffx != 0 && diffy == 0) {
			return Retning.SIDELENGS;
		}
		// Diagonalt
		if (Math.abs(diffx) == Math.abs(diffy)) {
			return Retning.DIAGONALT;
		}
		// Springer.. 2 frem 1 til siden
		if (Math.abs(diffx) == 2 && Math.abs(diffy) == 1) {
			return Retning.SPRINGER;
		}
		//Springer.. 2 til siden 1 frem
		if (Math.abs(diffx) == 1 && Math.abs(diffy) == 2) {
			return Retning.SPRINGER;
		}
		return null;
	}

	/**
	 * Finner rutene mellom to sjakkruter f.eks a1 og d4 skal gi oss b2 c3 som resultat - skal brukes til å sjekke for kollisjoner i et sjakktrekk.
	 * Funksjonen antar at rutene man skal sjekke er vertikal, horisontalt eller diagonalt orientert ift hverandre
	 * @param fra rute man starter fra
	 * @param til rute man ender i
	 * @return navn på rutene mellom fra og til
	 */
	protected static ArrayList<Integer> ruterMellom(int fra, int til)
	{

		int fraRutey = fra / Brett.BRETTSTØRRELSE;
		int fraRutex = fra % Brett.BRETTSTØRRELSE;
		int tilRutey = til / Brett.BRETTSTØRRELSE;
		int tilRutex = til % Brett.BRETTSTØRRELSE;
		int diffx = tilRutex - fraRutex;
		int diffy = tilRutey - fraRutey;
		int avstand = Koordinater.avstand(fra,til);
		if(avstand<=1) return null;

		int stegKol = diffx / (avstand);
		int stegRad = diffx / (avstand);
		ArrayList<Integer> ruterMellom = new ArrayList<>();
		for(int i=0;i<avstand-1;++i){
			fraRutex += stegKol;
			fraRutey += stegRad; // Flytt til neste posisjon
			ruterMellom.add(Koordinater.fraXY(fraRutex, fraRutey));
		}
		return ruterMellom;
	}

	/**
	 * Sjekker om et trekk for en gitt brikke er gyldig på et brett (ift brikkens definerte "egenskaper")
	 *
	 * @param fraPos brikkens utgangspunkt
	 * @param tilPos brikkens mål
	 * @param br     brikken som flyttes
	 * @param brett  spillbrettet
	 * @return true hvis trekket er gyldig(lovlig)
	 */
	public boolean gyldigTrekk(int fraPos, int tilPos, Brikke br, Brett brett)
	{
		Retning retning = finnRetning(fraPos, tilPos, br.getFarge());
		if (retning == null) return false;

		if (maxAvstand != 0 && (!gyldigAvstand(fraPos, tilPos))) {
			return false;
		}

		if (!hoppOverAndre) { // Hvis den ikke kan hoppe over andre brikker!
			if (kollisjonsSjekk(fraPos, tilPos, br, brett)) {
				return false; // Hvis kollisjon - ugyldig trekk!
			}
		}
		switch (retning) {
			case FREM:
				return flytteFremover;
			case BAKOVER:
				return flytteBakover;
			case DIAGONALT:
				return flytteDiagonalt;
			case SIDELENGS:
				return flytteSidelengs;
			case SPRINGER:
				return flytteSpringer;
			default:
				return false;
		}
	}

	public boolean gyldigAngrep(int fraPos, int tilPos, Brikke brikke, Brett brett)
	{
		return gyldigTrekk(fraPos, tilPos, brikke, brett);
	}

	protected boolean gyldigAvstand(int fraPos, int tilPos)
	{
		return Koordinater.avstand(fraPos,tilPos)<=maxAvstand;
	}

	public void setMaxAvstand(int maxAvstand){
		this.maxAvstand=maxAvstand;
	}

}
