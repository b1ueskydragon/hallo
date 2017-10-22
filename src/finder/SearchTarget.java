package finder;

import tools.FileUtillity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchTarget {

  // ターゲットの経路
  private String path;
  // ターゲットになるファイルのリスト
  private List<File> targetList = new ArrayList<>();
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
      ".py"
  );

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  private boolean isTargetFile(File file) {
    return !file.isDirectory() && targetExtensionList.contains(FileUtillity.getExtension(file));
  }

  private void searchDir(String path) {
    // path のディレクトリ
    File dir = new File(path);
    // 上記 dir 内のファイルリスト
    File[] fileList = dir.listFiles();

    if (fileList == null) return;

//    try {
//      for (File file : fileList) {
//        // ターゲットファイル発見
//        if (isTargetFile(file)) {
//          targetList.add(file);
//        }
//        // ディレクトリ発見
//        else if (file.isDirectory()) {
//          searchDir(file.getCanonicalPath());
//        }
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }


    Arrays.stream(fileList)
        .filter(this::isTargetFile)
        .forEach(targetList::add);

    Arrays.stream(fileList)
        .filter(File::isDirectory)
        .forEach(file -> {
          try {
            searchDir(file.getCanonicalPath());
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }


  public static void main(String[] args) {
    SearchTarget searchTarget = new SearchTarget();
    searchTarget.searchDir(GetTestPath.TEST_PATH);

    searchTarget.targetList
        .forEach(file -> System.out.println(file.getName()));
  }
}
