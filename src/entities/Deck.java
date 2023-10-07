package entities;

import java.util.Collections;
import java.util.Stack;

import utils.Suit;
import utils.Value;

public class Deck extends Stack<Card> {
	public Deck () {
		super();
		for (Suit suit : Suit.values()) {
			for (Value cardValue : Value.values()) {
				super.add(new Card(suit, cardValue, false));
			}
		}
	}
	
	public void shuffle () {
		Collections.shuffle(this);
	}
}
