package com.github.programmingwithmati.fp.patterns.bowling;

import com.github.programmingwithmati.fp.patterns.bowling.functional.Result;

import java.util.Scanner;
import java.util.function.Function;

public class GameCliMain {

  public static void main(String[] args) {
    Game game = new Game();
    GamePrinter gamePrinter = new GamePrinter();
    printInstructions(game, gamePrinter);
    Scanner in = new Scanner(System.in);
    var commandString = in.nextLine();

    while(!"exit".equals(commandString)) {
      Game finalGame = game;
      game = parseCommand(commandString)
              .flatMap(command -> command.apply(finalGame))
              .orOnErrorSupply((s1, s2) -> {
        System.out.printf("Error processing roll. The error code is: %s. %s.%n", s1, s2);
        return finalGame;
      });

      printInstructions(game, gamePrinter);

      commandString = in.nextLine();
    }
    System.out.println("Exiting game. Bye!");
    System.exit(0);

  }

  private static Result<Function<Game, Result<Game>>> parseCommand(String commandString) {
    if (commandString.startsWith("roll ")) return Result.of(game -> processRoll(commandString, game));
    return Result.error("COMMAND_NOT_FOUND", "The command '%s' is not a valid command".formatted(commandString));
  }


  private static void printInstructions(Game game, GamePrinter gamePrinter) {
    System.out.println("Insert a command:");
    System.out.println(">roll x -- where x is a number between 0 and 10");
    System.out.println(">exit -- to exit game");
    System.out.println("Current State: ");
    System.out.println(gamePrinter.firstRowToString(game));
    System.out.println(gamePrinter.secondRowToString(game));
  }

  private static Result<Game> processRoll(String command, Game game) {
    return parseInteger(command)
            .flatMap(ScoreValue::of)
            .flatMap(game::validateRoll)
            .map(game::roll);
  }

  private static Result<Integer> parseInteger(String source) {
    try {
      var s = source.split(" ");
      if (s.length < 2) return Result.error("PARSE_COMMAND_ERROR", "Unable to parse command. '%s' has not enough parameters".formatted(source));
      if (!s[1].matches("^\\d*$")) return Result.error("PARSE_INT_ERROR", "Unable to parse int. '%s' is not an int".formatted(s[1]));

      return Result.of(Integer.parseInt(s[1]));
    } catch (Exception e) {
      return Result.error("UNEXPECTED_ERROR", e.getMessage());
    }
  }
}
