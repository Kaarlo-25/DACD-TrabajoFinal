package org.CaballeroNillukka.model;

public class Rate {
	//Constructor
	private final String code;
	private final String webpage;
	private final float price;
	private final float tax;
	public Rate(String code, String webpage, float price, float tax) {
		this.code = code;
		this.webpage = webpage;
		this.price = price;
		this.tax = tax;
	}

	//Methods
	@Override
	public String toString() {
		return "Rate{" +
				"code='" + code + '\'' +
				", webpage='" + webpage + '\'' +
				", price=" + price +
				", tax=" + tax +
				'}';
	}
}
