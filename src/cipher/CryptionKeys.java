package cipher;

import java.util.Random;
import java.util.stream.IntStream;

public class CryptionKeys {

  public final static String COMMON_KEY;

  // クラスロード時に一回実行される (コンストラクタだと new しないと使えないため)
  static {
    COMMON_KEY = make16bitKey();
    System.out.println("発行された共通キー" + COMMON_KEY);
  }

  private static String make16bitKey() {
    final int KEY_LEN = 16;
    final int CODE_A = 65;
    final int CODE_Z = 90;

    StringBuilder keyBuilder = new StringBuilder();

    for (int i = 0; i < KEY_LEN; i++) {
      char c = (char) IntStream
          .rangeClosed(CODE_A, CODE_Z)
          .toArray()[new Random().nextInt((CODE_Z - CODE_A))];

      keyBuilder.append(c);
    }
    return keyBuilder.toString();
  }

  // 初期化ベクトル (今回未使用)
  final static String INIT_VECTOR = "RandomInitVector";

  // フラグファイル
  final static String A_FLAG_FILE = "www.dat";
}
