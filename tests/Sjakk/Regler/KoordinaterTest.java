package Sjakk.Regler;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

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
		String test3 = Koordinater.fra_koordinater(new int[]{koordint[0], koordint[1]});
		assertTrue(test.equals(forventet));
		assertTrue(test2.equals(forventet2));
		assertTrue(test3.equals(forventet));
	}

	@Test
	public void testKoordinater() throws Exception
	{
		Koordinater.stringTilKoord.forEach((integer, ints) -> {
			String str = Koordinater.koordTilString.get(Arrays.hashCode(ints));
			assertTrue(integer.hashCode() == str.hashCode());
			System.out.println(str + " = " + ints[0] + "," + ints[1]);

		});
	}
}