package cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class Cryption {

  // TODO fileEncrypt を実行するかどうか判断する flag 設置

  /**
   * 共通鍵暗号化方式::AES でファイルを暗号化する
   *
   * @param input      ファイル
   * @param commonKey  共通キーになる秘密キー (128 bit key)
   * @param initVector 初期化ベクトル (16 bytes IV)
   * @param output     暗号化されて排出されるファイル
   */
  public void fileEncrypt(int cryptMode, String commonKey, String initVector, File input, File output) {
    IvParameterSpec iv;
    Key skeySpec;
    Cipher cipher;

    FileInputStream inputStream = null;
    FileOutputStream outputStream = null;

    try {
      // 暗号化ロジック
      iv = new IvParameterSpec(initVector.getBytes());
      skeySpec = new SecretKeySpec(commonKey.getBytes(), "AES");
      cipher = Cipher.getInstance("AES");
      cipher.init(cryptMode, skeySpec, iv);

      // File IO
      inputStream = new FileInputStream(input);
      byte[] inputBytes = new byte[(int) input.length()];
      byte[] outputBytes = cipher.doFinal(inputBytes);
      outputStream = new FileOutputStream(output);
      outputStream.write(outputBytes);

    } catch (NoSuchAlgorithmException
        | NoSuchPaddingException | BadPaddingException
        | InvalidKeyException | IllegalBlockSizeException
        | InvalidAlgorithmParameterException | IOException e) {
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

  /**
   * ファイルリストを暗号化し、拡張子を変換する
   *
   * @param files     複数のファイル
   */
  public void exeEncrypt(List<File> files) {
    files.forEach(file -> fileEncrypt(Cipher.ENCRYPT_MODE, CryptionKeys.COMMON_KEY, CryptionKeys.INIT_VECTOR,
            file, new File(file.getPath() + ".hallo")));
    System.out.println("[log] 暗号化完了");
  }
}
