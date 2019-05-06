package shop.local.valueobjects.unused;

import shop.local.valueobjects.Artikel;

public class Bestseller extends Artikel {

	private int verkaufsrang = 0;
	
	public Bestseller() {
		// TODO
		super("Dummy", 111,1, 152.7f);
	}

	public int getVerkaufsrang() {
		return verkaufsrang;
	}

	public void setVerkaufsrang(int verkaufsrang) {
		this.verkaufsrang = verkaufsrang;
	}
}
