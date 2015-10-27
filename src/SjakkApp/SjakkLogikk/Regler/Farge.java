package SjakkApp.SjakkLogikk.Regler;

/**
 * Created by Jo Øivind Gjernes on 20.10.2015.
 * <p>
 * Enum som beskriver de to mulige fargene i sjakk.
 */
public enum Farge
{
	SVART,
	HVIT;

	public Farge motsatt()
	{
		return (this == Farge.HVIT) ? Farge.SVART : Farge.HVIT;
	}
}

