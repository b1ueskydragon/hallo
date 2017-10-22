package finder;

import tools.FileUtillity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchTarget {

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

  // TODO path getter & setter

  public SearchTarget() {
    searchDir(GetTestPath.TEST_PATH);
  }
  public List<File> getTargetList() {
    return targetList;
  }

  private void searchDir(String path) {
    // path 指定 dir 内のファイルリスト
    File[] fileList = new File(path).listFiles();
    if (fileList == null) return;

    // ターゲットのファイルをターゲットリストに追加
    Arrays.stream(fileList)
        .filter(this::isTargetFile)
        .forEach(targetList::add);

    // ディレクトリの場合、再帰検索でターゲットリストに追加
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

  private boolean isTargetFile(File file) {
    return !file.isDirectory() && targetExtensionList.contains(FileUtillity.getExtension(file));
  }
}
