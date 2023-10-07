package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import utils.Suit;
import utils.Value;

public class Tableau {
  public static final int NUM_LANES = 7;
  public static final int NUM_SUITS = Suit.values().length;
  public static final int SUIT_SIZE = Value.values().length;

  private Deck deck;
  private Stack<Card> pile;
  private ArrayList<Stack<Card>> lanes;
  private ArrayList<Stack<Card>> suits;
  private boolean wasCardReveal;

  public Tableau() {
    deck = new Deck();
    deck.shuffle();

    pile = new Stack<Card>();
    lanes = new ArrayList<Stack<Card>>(NUM_LANES);
    suits = new ArrayList<>(NUM_SUITS);
    wasCardReveal = false;

    for (int i = 0; i < NUM_LANES; i++) {
      lanes.add(new Stack<>());
    }
    for (int i = 0; i < NUM_SUITS; i++) {
      suits.add(new Stack<>());
    }

    buildLanes();
  }

  private void buildLanes() {
    for (int i = 0; i < NUM_LANES; i++) {
      Stack<Card> currentLane = lanes.get(i);
      for (int j = 0; j < i; j++) {
        Card card = deck.pop();
        currentLane.push(card);
      }
      Card card = deck.pop();
      card.turnFaceUp();
      currentLane.push(card);
    }
  }

  public boolean drawIsPossible() {
    return !deck.empty() || !pile.empty();
  }

  public void draw() {
    if (deck.empty()) {
      redeal();
    } else {
      Card card = deck.pop();
      card.turnFaceUp();
      pile.add(card);
    }
  }

  private void redeal() {
    for (int i = 0; i < pile.size(); i++) {
      Card card = pile.pop();
      card.turnFaceDown();
      deck.add(card);
    }
  }

  public boolean moveIsPossible(Command command) {
    boolean isPossible = false;
    if (command.isMoveFromPileToLane()) {
      if (!pile.empty()) {
        Card card = pile.peek();
        Stack<Card> lane = lanes.get(command.getToIndex());
        if (lane.empty() || (!lane.empty() && lane.peek().isNextInLane(card))) {
          isPossible = true;
        }
      }
    } else if (command.isMoveFromPileToSuit()) {
      if (!pile.empty()) {
        Card card = pile.peek();
        Stack<Card> suit = suits.get(command.getToIndex());
        if (suit.empty() || (!suit.empty() && suit.peek().isNextInSuit(card))) {
          isPossible = true;
        }
      }
    } else if (command.isMoveFromLaneToSuit()) {
      Stack<Card> lane = lanes.get(command.getFromIndex());
      if (!lane.empty()) {
        Card card = lane.peek();
        Stack<Card> suit = suits.get(command.getToIndex());
        if (suit.empty() || (!suit.empty() && suit.peek().isNextInSuit(card))) {
          isPossible = true;
        }
      }
    } else if (command.isMoveFromSuitToLane()) {
      Stack<Card> suit = suits.get(command.getFromIndex());
      if (!suit.empty()) {
        Card card = suit.peek();
        Stack<Card> lane = lanes.get(command.getToIndex());
        if (lane.empty() || (!lane.empty() && lane.peek().isNextInLane(card))) {
          isPossible = true;
        }
      }
    } else { // lane to lane
      List<Card> fromLane = lanes.get(command.getFromIndex()); // use List interface to the Stack
      if (fromLane.size() >= command.getNumberOfCardsToMove() &&
          fromLane.get(fromLane.size() - command.getNumberOfCardsToMove()).isFaceUp()) {
        Card card = fromLane.get(fromLane.size() - command.getNumberOfCardsToMove());
        Stack<Card> toLane = lanes.get(command.getToIndex());
        if (toLane.empty() || (!toLane.empty() && toLane.peek().isNextInLane(card))) {
          isPossible = true;
        }
      }
    }
    return isPossible;
  }

  public void move(Command command) {
    wasCardReveal = false;
    if (command.isMoveFromPileToLane()) {
      Card card = pile.pop();
      lanes.get(command.getToIndex()).push(card);
    } else if (command.isMoveFromPileToSuit()) {
      Card card = pile.pop();
      suits.get(command.getToIndex()).push(card);
    } else if (command.isMoveFromLaneToSuit()) {
      Stack<Card> lane = lanes.get(command.getFromIndex());
      Card card = lane.pop();
      if (!lane.empty() && lane.peek().isFaceDown()) {
        lane.peek().turnFaceUp();
        wasCardReveal = true;
      }
      suits.get(command.getToIndex()).push(card);
    } else if (command.isMoveFromSuitToLane()) {
      Card card = suits.get(command.getFromIndex()).pop();
      lanes.get(command.getToIndex()).push(card);
    } else { // lane to lane move
      List<Card> fromLane = lanes.get(command.getFromIndex()); // use List interface to the Stack
      int firstCardToMoveIndex = fromLane.size() - command.getNumberOfCardsToMove();
      for (int i = 0; i < command.getNumberOfCardsToMove(); i++) {
        Card card = fromLane.get(firstCardToMoveIndex);
        fromLane.remove(firstCardToMoveIndex);
        lanes.get(command.getToIndex()).push(card);
      }
      if (!fromLane.isEmpty() && fromLane.get(fromLane.size() - 1).isFaceDown()) {
        fromLane.get(fromLane.size() - 1).turnFaceUp();
        wasCardReveal = true;
      }
    }
  }

  public boolean isGameOver() {
    for (Stack<Card> suit : suits) {
      if (suit.size() != SUIT_SIZE) {
        return false;
      }
    }
    return true;
  }

  public int maxLaneSize() {
    int maxLaneSize = 0;
    for (Stack<Card> lane : lanes) {
      if (lane.size() > maxLaneSize) {
        maxLaneSize = lane.size();
      }
    }
    return maxLaneSize;
  }

  public Deck getDeck() {
    return deck;
  }

  public Stack<Card> getPile() {
    return pile;
  }

  public Stack<Card> getLane(int index) {
    return lanes.get(index);
  }

  public Stack<Card> getSuit(int index) {
    return suits.get(index);
  }

  public boolean wasCardReveal() {
    return wasCardReveal;
  }
}
