package ex3f;

import static ex3f.Color.*;
import java.util.*;

public class Game {
  public static void main(String[] args) {
    for (int numStones = 3; numStones <= 3; numStones++) {
      // var p0 = new RandomPlayer();
      // var p1 = new MinMaxPlayer(new Eval(), 20);
      // var p2 = new MinMaxPlayer(new Eval(), 20);
      // var p1 = new MinMaxPlayer(new Eval(), 20, 1);
      // var p2 = new MinMaxPlayer(new Eval(), 20, 2);
      var newp1 = new MyNegaMaxPlayer(new Eval(), 20, 1);
      var newp2 = new MyNegaMaxPlayer(new Eval(), 20, 2);
      // Game g = new Game(numStones, p0, p1);
      // Game g = new Game(numStones, p1, p2);
      Game g = new Game(numStones, newp1, newp2);
      g.play();
      g.printResult();
    }
  }

  State state;
  Map<Color, Player> players;

  public Game(int numStones, Player black, Player white) {
    black.color = BLACK;
    white.color = WHITE;
    this.state = new State(numStones);
    this.players = Map.of(BLACK, black, WHITE, white);
  }

  void play() {
    System.out.printf("==== %d stone(s) ====\n", state.numStones);
    int sumcount = 0;
    int whitecount = 0;
    int blackcount = 0;
    while (this.state.isGoal() == false) {
      var player = this.players.get(this.state.color);
      var move = player.think(this.state.clone());
      var next = this.state.perform(move);
      System.out.printf("%s -> %s | %s %s.  ", state, next, player, move);
      System.out.printf("各回の訪問ノード数 %d  ", player.count);
      System.out.printf("各回の枝刈り回数 %d\n", player.branchCutCount);
      if (this.state.color == BLACK) {
        blackcount += player.count;
      } else {
        whitecount += player.count;
      }
      sumcount += player.count;
      this.state = next;
    }
    System.out.printf("黒の訪問ノード数 %d\n", blackcount);
    System.out.printf("白の訪問ノード数 %d\n", whitecount);
    System.out.printf("累計訪問ノード数%d\n", sumcount);
  }

  void printResult() {
    System.out.println("Winner: " + this.players.get(this.state.winner()));
    System.out.println();
  }
}