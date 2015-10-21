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
		Brett brett = new Brett(0);
		Brett brett2 = new Brett(1);
		Trekk tr = PGN.pgnOrdTilTrekk("e4",brett, Farge.HVIT);
		Trekk tr2 = PGN.pgnOrdTilTrekk("e4", brett2, Farge.HVIT);
		tr2.utfør();
		brett.addTrekk(tr);
		brett.utfør();
		Brikke br = brett.getBrikke("e4");
		Brikke br2 = brett2.getBrikke("e4");
		assertTrue(br.brikkenavn()=="B"&&br.getFarge()==Farge.HVIT);
		assertTrue(br.brikkenavn().equals(br2.brikkenavn())&&br2.getFarge()==br.getFarge());
	}

	@Test
	public void testPgnListeTilTrekk() throws Exception
	{
		ArrayList<String> trekkListe = new ArrayList<String>();
		trekkListe.add("e4");
		trekkListe.add("e5");
		trekkListe.add("Nf3");
		trekkListe.add("Nc6");
		ArrayList<Trekk> trekk = PGN.pgnListeTilTrekk(trekkListe);
		assertTrue(trekk.size()==4);
	}
}