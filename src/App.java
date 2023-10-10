import controller.Command;
import models.*;
import view.View;

public class App {
    public static void main(String... args) {
        Tableau board = new Tableau();
        Score scoreboard = new Score();
        View view = new View();
        view.displayWelcome();
        Command command;
        do {
            view.displayScore(scoreboard);
            view.displayBoard(board);
            boolean commandDone = false;
            do {
                command = view.getUserInput();
                if (command.isDraw()) {
                    if (board.drawIsPossible()) {
                        board.draw();
                        scoreboard.drawPlayed(command);
                        commandDone = true;
                    } else {
                        view.displayCommandNotPossible();
                    }
                } else if (command.isMove()) {
                    if (board.moveIsPossible(command)) {
                        board.move(command);
                        scoreboard.movePlayed(command, board);
                        commandDone = true;
                    } else {
                        view.displayCommandNotPossible();
                    }
                } else if (command.isQuit()) {
                    commandDone = true;
                }
            } while (!commandDone);
        } while (!command.isQuit() && !board.isGameOver());
        if (board.isGameOver()) {
            view.displayScore(scoreboard);
            view.displayBoard(board);
            view.displayGameOver();
        } else {
            view.displayQuit();
        }
    }

}
