package ex3f;

import static java.lang.Float.*;

public class MyNegaMaxPlayer extends Player{
  // 評価関数と探索の深さの制限
  Eval eval;
  int depthLimit;
  Move move;

  // コンストラクタ
  public MyNegaMaxPlayer(Eval eval, int depthLimit, int user) {
    super("NegaMax" + user + " " +depthLimit);
    this.eval = eval;
    this.depthLimit = depthLimit;
  }
  

  Move search(State state) {
    count = 0;
    branchCutCount = 0;
    this.move = new Move(Math.min(3, state.numStones), state.color);

    // if (this.color == Color.BLACK) {
     maxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
    // } else {
    //  maxSearch(state, NEGATIVE_INFINITY, POSITIVE_INFINITY, 0);
    // }
    return this.move;
  }
  

  // Maxの探索メソッド
  float maxSearch(State state, float alpha, float beta, int depth) {
    if (isTerminal(state, depth)) {
      return this.eval.value(state);
    }

    var v = NEGATIVE_INFINITY;
    for (var move : state.getMoves()) {
      var next = state.perform(move);
      count++;
      var v0 = -maxSearch(next, -beta, -alpha, depth + 1);
      System.out.println("depth = " + depth);
      System.out.println("alpha = " + alpha);
      System.out.println("beta = " + beta);
      System.out.println("v0 = " + v0);

      if (depth == 0 && v0 > v) {
        System.out.println("this.move = " + move);
        this.move = move;
      }
      v = Math.max(v, v0);
      System.out.println("v = " + v);

      
      if (beta <= v) {
        //betaカット
        System.out.println("betaカット");
        branchCutCount++;
        break;
      }
      alpha = Math.max(alpha, v);
      System.out.println("depth = " + depth);
      System.out.println("alpha = " + alpha);
      System.out.println("beta = " + beta);
    }

    return v;
  }

  // ターミナルノードかどうかを判定するメソッド
  // ゴール上状態か、深さが制限を超えている場合にtrueを返す
  boolean isTerminal(State state, int depth) {
    return state.isGoal() || depth >= this.depthLimit;
  }
}