package SjakkApp.SjakkLogikk.Brett.AI;

import SjakkApp.SjakkLogikk.Brett.Brett;

/**
 * Created by Jo Øivind Gjernes on 25.10.2015.
 */
public class SpillTrekk
{
	private int fraPos;
	private int tilPos;
	private int poeng = 0;

	public SpillTrekk(int fraPos, int tilPos)
	{
		this.fraPos = fraPos;
		this.tilPos = tilPos;
	}

	public SpillTrekk(int fraPos, int tilPos, int poeng)
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

	public void setTrekk(int fraPos, int tilPos)
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
