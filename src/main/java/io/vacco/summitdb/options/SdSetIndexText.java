package io.vacco.summitdb.options;

import io.vacco.summitdb.commands.SdBase;

public class SdSetIndexText implements SdOptions {

  private SdSetIndexNamePattern namePattern;
  private SdSetIndexCsCollate csCollate;
  private SdSetIndexDesc desc;

  public SdSetIndexText namePattern(SdSetIndexNamePattern namePat) {
    this.namePattern = namePat;
    return this;
  }

  public SdSetIndexText csCollate(SdSetIndexCsCollate csCollate) {
    this.csCollate = csCollate;
    return this;
  }

  public SdSetIndexText desc(SdSetIndexDesc desc) {
    this.desc = desc;
    return this;
  }

  @Override
  public Object[] toArgs() {
    return SdBase.flatten(new Object[] {
        "SETINDEX", namePattern.toArgs(), "TEXT",
        csCollate != null ? csCollate.toArgs() : null,
        desc != null ? desc.toArgs() : null
    }).toArray();
  }
}
