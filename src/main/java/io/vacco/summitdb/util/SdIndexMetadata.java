package io.vacco.summitdb.util;

public class SdIndexMetadata {

  public String name;
  public String path;
  public String type;

  public static SdIndexMetadata from(String name, String path, String type) {
    SdIndexMetadata smd = new SdIndexMetadata();
    smd.name = name;
    smd.path = path;
    smd.type = type;
    return smd;
  }

  @Override
  public String toString() {
    return "SdIndexMetadata{" +
        "name='" + name + '\'' +
        ", path='" + path + '\'' +
        ", type='" + type + '\'' +
        '}';
  }
}
