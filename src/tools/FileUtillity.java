package tools;

import java.io.File;
import java.util.Arrays;

public class FileUtillity {
  // 拡張子を取得する
  public static String getExtension(File file) {
    String fileName = file.getName().toLowerCase();
   return fileName.substring(fileName.lastIndexOf("."));
  }
}
