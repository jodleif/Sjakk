package Sjakk.FileIO;

import Sjakk.Brett.Brett;
import Sjakk.Brett.Historikk.Trekk;
import Sjakk.Brikker.Brikke;
import Sjakk.Regler.Farge;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Jo Øivind Gjernes on 21.10.2015.
 *
 * test
 */
public class PGNTest
{
	
	@Test
	public void testPgnOrdTilTrekk() throws Exception
	{

	}

	@Test
	public void testPgnListeTilTrekk() throws Exception
	{
		ArrayList<String> trekkListe = new ArrayList<String>();
		trekkListe.add("e4");
		trekkListe.add("e5");
		trekkListe.add("Nf3");
		trekkListe.add("Nc6");
		trekkListe.add("Bb3");
		trekkListe.add("a6");
		trekkListe.add("Ba4");
		trekkListe.add("Nf6");
		ArrayList<Trekk> trekk = PGN.pgnListeTilTrekk(trekkListe);
		assertTrue(trekk.size()==9); // Startsposisjon også et trekk.
	}
}