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
  private static List<File> targetList = new ArrayList<>();
  // ターゲットにしたい拡張子のリスト
  private static List<String> targetExtensionList = Arrays.asList(
      ".mp3",
      ".txt",
      ".java",
      ".jpg",
      ".jpeg",
      ".png",
      ".pdf",
      ".docx"
  );

  public String getPath() {
    return path;
  }
  public void setPath(String path) {
    this.path = path;
  }

  private static boolean isTargetFile(File file) {
    return !file.isDirectory() && targetExtensionList.contains(FileUtillity.getExtension(file));
  }

  private void searchDir(String path) throws IOException {
    // path のディレクトリ
    File dir = new File(path);
    // 上記 dir 内のファイルリスト
    File [] fileList = dir.listFiles();

    if(fileList != null) {
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

      Arrays.stream(fileList)
          .filter(SearchTarget::isTargetFile)
          .forEach(targetList::add);
    }
  }


  public static void main(String [] args) {
    SearchTarget searchTarget = new SearchTarget();
    try {
      searchTarget.searchDir("/Users/Inhwa_rg1/Desktop/securityTest");
      searchTarget.targetList.stream().map(file -> file.getName()).forEach(System.out::println);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
