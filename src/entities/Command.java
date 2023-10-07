package entities;

import utils.CommandType;

public class Command {

  private CommandType commandType;
  private char moveFrom;
  private char moveTo;
  private String numberOfCards;

  public Command(String input) {
    input = input.trim().toUpperCase();

    if (input.equals("Q")) commandType = CommandType.QUIT;
    else if (input.equals("D")) commandType = CommandType.DRAW;
    else {
      commandType = CommandType.MOVE;
      moveFrom = input.charAt(0);
      moveTo = input.charAt(1);
      numberOfCards = input.length() == 2 ? "" : input.substring(2);
    }
  }

  public boolean isQuit() {
		return commandType == CommandType.QUIT;
	}
	
	public boolean isDraw () {
		return commandType == CommandType.DRAW;
	}
	
	public boolean isMove () {
		return commandType == CommandType.MOVE;
	}

  public boolean isMoveFromPile () {
		return moveFrom == 'P';
	}

  public boolean isMoveFromLane () {
		return Character.toString(moveFrom).matches("[1-7]");
	}

  public boolean isMoveToLane () {
		return Character.toString(moveTo).matches("[1-7]");
	}	

	public boolean isMoveFromSuit () {
		return Character.toString(moveFrom).matches("[DHCS]");
	}

  public boolean isMoveToSuit () {
		return Character.toString(moveTo).matches("[DHCS]");
	}
	
	public boolean isMoveFromPileToLane() {
		return isMoveFromPile() && isMoveToLane();
	}

	public boolean isMoveFromPileToSuit() {
		return isMoveFromPile() && isMoveToSuit();
	}

	public boolean isMoveFromLaneToSuit() {
		return isMoveFromLane() && isMoveToSuit();
	}

	public boolean isMoveFromSuitToLane() {
		return isMoveFromSuit() && isMoveToLane();
	}

	public boolean isMoveFromLaneToLane() {
		return isMoveFromLane() && isMoveToLane();
	}
  
  public static boolean isValid (String input) {
    input = input.trim().toUpperCase();
		return input.equals("Q") || input.equals("D") || input.matches("[P1-7DHCS][1-7DHCS][0-9]*");
	}

  public int getFromIndex () {
		if (isMoveFromLane()) {
			return Character.getNumericValue(moveFrom) - 1;
		} else { // isMoveToSuit()
			return suitToIndex(moveFrom);
		}
	}

  public int getToIndex () {
		if (isMoveToLane()) {
			return Character.getNumericValue(moveTo) - 1;
		} else { // isMoveToSuit()
			return suitToIndex(moveTo);
		}
	}

  private int suitToIndex (char character) {
		switch(character) {
			case 'D': return 0;
			case 'H': return 1;
			case 'C': return 2;
			case 'S': return 3;
			default: return 0;
		}
	}

  public int getNumberOfCardsToMove() {
		if (numberOfCards.equals("") || numberOfCards.equals("1")) {
			return 1;
		} else {
			return Integer.valueOf(numberOfCards);
		}
	}
}
