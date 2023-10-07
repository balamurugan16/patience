package utils;

public enum Suit {
	DIAMOND (DisplayColor.RED + "D", SuitColor.RED),
	HEART (DisplayColor.RED + "H", SuitColor.RED),
	CLUB (DisplayColor.BLACK + "C", SuitColor.BLACK),
	SPADE (DisplayColor.BLACK + "︎S", SuitColor.BLACK);
	
	private String symbol;
	private SuitColor colour;
	
	Suit (String symbol, SuitColor colour) {
		this.symbol = symbol;
		this.colour = colour;
	}
	
	public SuitColor getColour () {
		return colour;
	}
	
	public String toString () {
		return symbol;
	}
}
