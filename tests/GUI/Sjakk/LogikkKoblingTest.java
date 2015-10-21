package GUI.Sjakk;

import org.junit.Test;
import sun.rmi.runtime.Log;

import static org.junit.Assert.*;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class LogikkKoblingTest
{
	
	@Test
	public void testFraGridPane() throws Exception
	{
		String str1 = "a1";
		String str2 = "h8";
		String gridP = LogikkKobling.fraGridPane(0,7);
		String gridP2 = LogikkKobling.fraGridPane(7,0);
		assertTrue(str1.equals(gridP));
		assertTrue(str2.equals(gridP2));
	}

	@Test
	public void testTilGridPane() throws Exception
	{
		String str1 = "a1";
		String str2 = "h8";
		int[] koord = {0,7};
		int[] koord2 = {7,0};
		int[] gridP = LogikkKobling.tilGridPane(str1);
		int[] gridP2 = LogikkKobling.tilGridPane(str2);
		assertArrayEquals(koord,gridP);
		assertArrayEquals(koord2,gridP2);
	}
}