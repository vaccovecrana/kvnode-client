package io.vacco.summitdb.options;

import io.vacco.summitdb.commands.SdBase;
import io.vacco.summitdb.spi.SdOptions;

import java.util.ArrayList;
import java.util.List;

class IndexPath {
  public String jsonPath;
  public SdSetIndexCsCollate csCollate;
  public SdSetIndexDesc desc;

  public static IndexPath from(String jsonPath, SdSetIndexCsCollate csCollate, SdSetIndexDesc desc) {
    IndexPath i0 = new IndexPath();
    i0.jsonPath = jsonPath;
    i0.csCollate = csCollate;
    i0.desc = desc;
    return i0;
  }
}

public class SdSetIndexJson implements SdOptions {

  private SdSetIndexNamePattern namePattern;
  private final List<IndexPath> paths = new ArrayList<>();

  public SdSetIndexJson namePattern(SdSetIndexNamePattern namePattern) {
    this.namePattern = namePattern;
    return this;
  }

  public SdSetIndexJson json(String path, SdSetIndexCsCollate csCollate, SdSetIndexDesc desc) {
    paths.add(IndexPath.from(path, csCollate, desc));
    return this;
  }

  public SdSetIndexJson json(String path, SdSetIndexCsCollate csCollate) {
    paths.add(IndexPath.from(path, csCollate, null));
    return this;
  }

  public SdSetIndexJson json(String path) {
    paths.add(IndexPath.from(path, null, null));
    return this;
  }

  @Override
  public Object[] toArgs() {
    return SdBase.flatten(new Object[] {
        "SETINDEX", namePattern.toArgs(),
        paths.stream().map(p0 -> new Object[] {
            "JSON", p0.jsonPath,
            p0.csCollate != null ? p0.csCollate.toArgs() : null,
            p0.desc != null ? p0.desc.toArgs() : null
        }).toArray()
    }).toArray();
  }
}
