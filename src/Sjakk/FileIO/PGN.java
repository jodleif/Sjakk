package Sjakk.FileIO;

import Sjakk.Brett.Brett;
import Sjakk.Brett.Historikk.Trekk;
import Sjakk.Brikker.Brikke;
import Sjakk.Regler.Farge;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Jo Øivind Gjernes on 21.10.2015.
 * <p>
 * Forsøk på å lese inn PGN filer for å spille av!
 */
public class PGN
{

	private static String fjernEkstraInformasjon(String pgnTekst){
		int start = pgnTekst.indexOf("1. ");
		return pgnTekst.substring(start);
	}

	public static ArrayList<Trekk> pgnListeTilTrekk(ArrayList<String> pgnTrekk){
		boolean hvit = true;
		ArrayList<Trekk> trekkListe = new ArrayList<>();
		Brett brett = new Brett(0);

		for(String trekk : pgnTrekk){
			Trekk tr = pgnOrdTilTrekk(trekk, brett, (hvit)?Farge.HVIT:Farge.SVART);
			try {
				tr.utfør(); // Hold brettet oppdatert
			} catch (Exception e) {
				System.err.println("ERROR PARSING LIST");
			}
			hvit = !hvit;
			trekkListe.add(tr);
		}
		return trekkListe;
	}

	public static Trekk pgnOrdTilTrekk(String pgnTrekk, Brett brett, Farge farge) {
		try {
			String[] brikkeInfo = hentInfoFraPGNtrekk(pgnTrekk); // [0] brikketype, [1] sluttPos
			String startPos = finnBrikkeStartPos(brikkeInfo[0],brikkeInfo[1],brett,farge);
			Brikke fjernetBrikke = finnFjernetBrikke(brikkeInfo[1],brett);
			Trekk trekk = new Trekk(startPos,brikkeInfo[1],brikkeInfo[0],farge,brett,fjernetBrikke);
			return trekk;
		} catch (Exception e) {
			System.err.println("Tolking feilet!");
			return null;
		}
	}

	private static Brikke finnFjernetBrikke(String pos, Brett brett){
		return brett.getBrikke(pos);
	}

	private static String[] hentInfoFraPGNtrekk(String pgnTrekk){
		String brikkeType;
		String sluttPos;
		if (pgnTrekk.length()==2){ // Ingen info om brikketype
			brikkeType = "B"; // Bonde!
			sluttPos = pgnTrekk;
		} else if (pgnTrekk.length()>2){
			brikkeType = String.valueOf(SpråkKonvertering.KonverterFraPGN(pgnTrekk.charAt(0)));
			sluttPos = pgnTrekk.substring(1,3);
		} else {
			throw new IllegalArgumentException("ugyldig pgn trekk!");
		}
		return new String[] {brikkeType, sluttPos};
	}

	private static String finnBrikkeStartPos(String brikkeType, String sluttPos, Brett brett, Farge farge) throws IllegalArgumentException {
		String startPos = new String();

		Brikke[] brikker = brett.getAlleBrikker();
		for(Brikke b : brikker){
			if(b.brikkenavn().equals(brikkeType)&&b.getFarge()==farge){
				if(b.erLovligTrekk(sluttPos)){
					startPos = b.getRuteNavn();
				}
			}
		}
		return startPos;
	}


	private static class SpråkKonvertering
	{
		public static char KonverterBrikkeNavn(char tilPGN)
		{
			switch (tilPGN) {
				case 'B':
					return 'P';
				case 'D':
					return 'Q';
				case 'K':
					return tilPGN;
				case 'S':
					return 'N';
				case 'L':
					return 'S';
				case 'T':
					return 'R';
				default:
					return ' ';
			}
		}

		public static char KonverterFraPGN(char fraPGN)
		{
			switch (fraPGN) {
				case 'P':
					return 'B';
				case 'Q':
					return 'D';
				case 'K':
					return fraPGN;
				case 'S':
					return 'L';
				case 'N':
					return 'S';
				case 'R':
					return 'T';
				default:
					return ' ';
			}
		}
	}
}
