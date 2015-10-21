package Sjakk.FileIO;

import java.util.Scanner;

/**
 * Created by Jo Øivind Gjernes on 21.10.2015.
 *
 * Lesing og skriving til/fra tekstfiler
 */
public class FilVerktøy
{
	private static String lesTekstFraFil(String filbane){
		String res = new String();
		java.io.File fil = new java.io.File(filbane);
		try {
			Scanner filleser = new Scanner(fil);
			while(filleser.hasNext()){
				res += filleser.nextLine();
			}
			filleser.close();
		} catch (Exception e) {
			System.err.println("[FilVerktøy.lesTekstFraFil] Feil under lesing av : " + filbane + "\n" +e.getMessage());
		}
		return res;
	}
}
