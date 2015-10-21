package Sjakk.Regler;

import Sjakk.Brett.Brett;
import Sjakk.Brikker.Brikke;
import Sjakk.Brikker.Farge;

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
 	 * @param flytteSidelengs
	 * @param flytteFremover
	 * @param flytteBakover
	 * @param flytteDiagonalt
	 * @param maxAvstand
	 */
	public FlytteRegel(boolean flytteSidelengs, boolean flytteFremover, boolean flytteBakover, boolean flytteDiagonalt, int maxAvstand)
	{
		this.flytteSidelengs = flytteSidelengs;
		this.flytteFremover = flytteFremover;
		this.flytteBakover = flytteBakover;
		this.flytteDiagonalt = flytteDiagonalt;
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
	 * Sjekker om et trekk for en gitt brikke er gyldig på et brett (ift brikkens definerte "egenskaper")
	 * @param fraPos brikkens utgangspunkt
	 * @param tilPos brikkens mål
	 * @param br brikken som flyttes
	 * @param brett spillbrettet
	 * @return true hvis trekket er gyldig(lovlig)
	 */
	public boolean gyldigTrekk(String fraPos, String tilPos, Brikke br, Brett brett)
	{
		Retning retning = finnRetning(fraPos, tilPos, br.getFarge());
		if (retning == null) return false;

		if(maxAvstand!=0&&(!gyldigAvstand(fraPos,tilPos))){
			return false;
		}

		if(!hoppOverAndre){ // Hvis den ikke kan hoppe over andre brikker!
			if(kollisjonsSjekk(fraPos,tilPos,brett)){
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

	/**
	 * Metode som sjekker rutene mellom fra og til posisjon for � finne ut om det er noen brikker "p� veien"
	 *
	 * @param fraPos brikkens fra-posisjon
	 * @param tilPos brikkens til-posisjon
	 * @param brett  spillbrettet
	 * @return true hvis det oppst�r kollisjoner.
	 */
	protected static boolean kollisjonsSjekk(String fraPos, String tilPos, Brett brett)
	{
		String[] diff = ruterMellom(fraPos, tilPos);
		if(diff==null)
			return false;
		for(String mellomPos : diff){
			if(brett.getBrikke(mellomPos)!=null) // Hvis feltet ikke er null inneholder det en brikke.
				return true;
		}
		return false;
	}

	/**
	 * Bestemmer retningen flyttingen g�r i
	 *
	 * @param fraPos brikkens fra-posisjon
	 * @param tilPos brikkens til-posisjon
	 * @param farge  brikkens farge (trengs for � finne ut om man beveger seg fremover eller bakover)
	 * @return Returnerer hvilken retning bevegelsen vil v�re - (SE enumen Retning)
	 */
	protected static Retning finnRetning(String fraPos, String tilPos, Farge farge)
	{
		int[] koordFra = Koordinater.til_koordinater(fraPos);
		int[] koordTil = Koordinater.til_koordinater(tilPos);
		int[] diff = Koordinater.differanse(koordFra, koordTil);
		// Frem og tilbake
		if (diff[0] == 0 && diff[1] != 0) {
			if (diff[1] > 0) {
				return (farge == Farge.HVIT) ? Retning.FREM : Retning.BAKOVER;
			}
			if (diff[1] < 0) {
				return (farge == Farge.HVIT) ? Retning.BAKOVER : Retning.FREM;
			}
		}
		// Sidelengs
		if (diff[0] != 0 && diff[1] == 0) {
			return Retning.SIDELENGS;
		}
		// Diagonalt
		if (Math.abs(diff[0])==Math.abs(diff[1])) {
			return Retning.DIAGONALT;
		}
		// Springer.. 2 frem 1 til siden
		if(Math.abs(diff[0])==2&&Math.abs(diff[1])==1){
			return Retning.SPRINGER;
		}
		//Springer.. 2 til siden 1 frem
		if(Math.abs(diff[0])==1&&Math.abs(diff[1])==2){
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
	protected static String[] ruterMellom(String fra, String til)
	{
		int[] fraKoord = Koordinater.til_koordinater(fra);
		int[] tilKoord = Koordinater.til_koordinater(til);
		int[] diff = Koordinater.differanse(fraKoord, tilKoord);
		int avstand = Koordinater.avstand(fra,til);
		if(avstand<=1) return null;

		String[] ruterMellom = new String[avstand-1];
		int stegKol = diff[0]/(avstand);
		int stegRad = diff[1]/(avstand);

		for(int i=0;i<avstand-1;++i){
			fraKoord[0]+=stegKol;fraKoord[1]+=stegRad; // Flytt til neste posisjon
			ruterMellom[i]=Koordinater.fra_koordinater(fraKoord);
		}
		return ruterMellom;
	}

	protected boolean gyldigAvstand(String fraPos, String tilPos)
	{
		return Koordinater.avstand(fraPos,tilPos)<=maxAvstand;
	}

	public void setMaxAvstand(int maxAvstand){
		this.maxAvstand=maxAvstand;
	}

}
