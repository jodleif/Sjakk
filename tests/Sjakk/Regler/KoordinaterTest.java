package Sjakk.Regler;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 *
 * Enkel testing av koordinat "omregning"
 */
public class KoordinaterTest
{
	
	@Test
	public void testTil_koordinater() throws Exception
	{
		String koord = "d8";
		String koord2 = "e1";
		int[] koordint = {3,7};
		int[] koording2 = {4,0};
		int[] test2 = Koordinater.til_koordinater(koord2);
		int[] test = Koordinater.til_koordinater(koord);
		assertArrayEquals(koordint, test);
		assertArrayEquals(koording2, test2);
	}

	@Test
	public void testFra_koordinater() throws Exception
	{
		String forventet = "d8";
		String forventet2 = "e1";
		int[] koordint = {3,7};
		int[] koordint2 = {4,0};
		String test = Koordinater.fra_koordinater(koordint);
		String test2 = Koordinater.fra_koordinater(koordint2);
		assertTrue(test.equals(forventet));
		assertTrue(test2.equals(forventet2));
	}
}