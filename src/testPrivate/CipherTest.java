package testPrivate;

import org.apache.commons.codec.binary.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.stream.IntStream;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * 単体テストのクラス
 */
public class CipherTest {

  private void fileCrypt(int cryptMode, String commonKey, File fileInput, File fileOutput) {
    Key skeySpec;
    Cipher cipher;

    FileInputStream inputStream = null;
    FileOutputStream outputStream = null;

    try {
      // 暗号化ロジック
      skeySpec = new SecretKeySpec(commonKey.getBytes(), "AES");
      cipher = Cipher.getInstance("AES");
      cipher.init(cryptMode, skeySpec); // ここに 16byte の initVector 追加したかった

      // File IO
      inputStream = new FileInputStream(fileInput);
      byte[] inputBytes = new byte[(int) fileInput.length()];
      inputStream.read(inputBytes);

      byte[] outputBytes = cipher.doFinal(inputBytes);
      outputStream = new FileOutputStream(fileOutput);
      outputStream.write(outputBytes);

      // 暗号化 || 復号化が終わったら、元のファイルを削除する
      fileInput.delete();

    } catch (NoSuchAlgorithmException
        | NoSuchPaddingException
        | BadPaddingException
        | InvalidKeyException
        | IllegalBlockSizeException
        | IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (inputStream != null) inputStream.close();
        if (outputStream != null) outputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private String textCrypt(int cryptMode, String commonKey, String target) {
    Key skeySpec;
    Cipher cipher;

    byte[] outputBytes = null;
    try {
      // 暗号化ロジック
      skeySpec = new SecretKeySpec(commonKey.getBytes(), "AES");
      cipher = Cipher.getInstance("AES");
      cipher.init(cryptMode, skeySpec); // ここに 16byte の initVector 追加したかった


      byte[] inputBytes = new byte[target.length()];
      outputBytes = cipher.doFinal(inputBytes);

    } catch (NoSuchAlgorithmException
        | NoSuchPaddingException
        | BadPaddingException
        | InvalidKeyException
        | IllegalBlockSizeException e) {
      e.printStackTrace();
    }

    return StringUtils.newStringUtf16Le(outputBytes);
  }

  public static void main(String[] args) {

    CipherTest cipherTest = new CipherTest();
    final String cmKey = makeKey("");

    String targetCrypted = cipherTest.textCrypt(ENCRYPT_MODE, cmKey, "はろおおお");
    System.out.println(targetCrypted);
    System.out.println(cipherTest.textCrypt(DECRYPT_MODE, cmKey, targetCrypted));

  }

  // 正直普通に for 文だけで十分
  private static String makeKey(String key) {
    final int KEY_LEN = 16;
    final int CODE_A = 65;
    final int CODE_Z = 90;
    int STR_LEN = key.length();

    if (STR_LEN == KEY_LEN) {
      return key;
    } else if (STR_LEN < KEY_LEN) {
      int supplyLen = KEY_LEN - STR_LEN;
      StringBuilder keyBuilder = new StringBuilder(key);
      for (int i = 0; i < supplyLen; i++) {
        char c = (char) IntStream
            .rangeClosed(CODE_A, CODE_Z)
            .toArray()[new Random().nextInt((CODE_Z - CODE_A))];

        keyBuilder.append(c);
      }
      key = keyBuilder.toString();
      return key;
    } else {
      key = key.substring(0, KEY_LEN);
    }
    return key;
  }
}
