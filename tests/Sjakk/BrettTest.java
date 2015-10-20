package Sjakk;

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
		assertTrue(b.erLovligRutenavn("a1"));
		assertTrue(b.erLovligRutenavn("h8"));
		assertFalse(b.erLovligRutenavn("a9"));
		assertFalse(b.erLovligRutenavn("-99"));
		assertFalse(b.erLovligRutenavn("A1"));
		assertFalse(b.erLovligRutenavn("a-"));
	}
}