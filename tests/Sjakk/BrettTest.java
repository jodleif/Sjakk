package Sjakk;

import Sjakk.Brett.Brett;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 */
public class BrettTest
{
	
	@org.junit.Test
	public void testErLovligRutenavn() throws Exception
	{
		Brett b = new Brett(123);
		assertTrue(Brett.erLovligRutenavn("a1"));
		assertTrue(Brett.erLovligRutenavn("h8"));
		assertFalse(Brett.erLovligRutenavn("a9"));
		assertFalse(Brett.erLovligRutenavn("-99"));
		assertFalse(Brett.erLovligRutenavn("A1"));
		assertFalse(Brett.erLovligRutenavn("a-"));
	}

	@Before
	public void setUp() throws Exception
	{
		Brett b = new Brett(0);
	}
}