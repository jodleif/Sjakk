package Sjakk.FileIO;

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
					return tilPGN;
				case 'L':
					return 'N';
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
					return fraPGN;
				case 'N':
					return 'L';
				case 'R':
					return 'T';
				default:
					return ' ';
			}
		}
	}
}
