package models;

import java.util.Collections;
import java.util.Stack;

import utils.Suit;
import utils.Rank;

public class Deck extends Stack<Card> {
	public Deck () {
		super();
		for (Suit suit : Suit.values()) {
			for (Rank cardValue : Rank.values()) {
				super.add(new Card(suit, cardValue, false));
			}
		}
	}
	
	public void shuffle () {
		Collections.shuffle(this);
	}
}
