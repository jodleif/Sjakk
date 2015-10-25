package Sjakk.AI;

import Sjakk.Brett.Brett;

/**
 * Created by Jo Øivind Gjernes on 25.10.2015.
 */
public class SpillTrekk
{
	private String fraPos;
	private String tilPos;
	private int poeng;

	public SpillTrekk(String fraPos, String tilPos)
	{
		this.fraPos = fraPos;
		this.tilPos = tilPos;
	}

	public SpillTrekk(String fraPos, String tilPos, int poeng)
	{
		this.fraPos = fraPos;
		this.tilPos = tilPos;
		this.poeng = poeng;
	}

	public SpillTrekk(int poeng)
	{
		this.poeng = poeng;
	}

	public void gjørTrekk(Brett b)
	{
		b.flyttBrikke(fraPos, tilPos);
	}

	public void setTrekk(String fraPos, String tilPos)
	{
		this.fraPos = fraPos;
		this.tilPos = tilPos;
	}

	public int getPoeng()
	{
		return poeng;
	}

	public void setPoeng(int poeng)
	{
		this.poeng = poeng;
	}

	public boolean utfør(Brett b)
	{
		return b.flyttBrikke(fraPos, tilPos);
	}
}
