package tools;

import java.util.Scanner;

/**
 * 仮
 */
public class InputUtility {

  public static String textInput() {
    Scanner sc;
    String input = "";
    try {
      sc = new Scanner(System.in);
      System.out.println("[案内] キーを入力してください");
      input = sc.next();
    } catch (Exception e) { // TODO キーが異なるときもここで catch する
      System.out.println("[警告] 正しい形式を入力してください");
      textInput();
    }
    return input;
  }
}
