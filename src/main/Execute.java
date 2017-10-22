package main;

import finder.SearchTarget;

public class Execute {

  public static void main(String ... halloween) {
    SearchTarget searchTarget = new SearchTarget();
    searchTarget.getTargetList()
        .forEach(file -> System.out.println(file.getName()));
  }
}
