package io.vacco.summitdb;

import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import static io.vacco.summitdb.SdLocalClient.*;
import static io.vacco.summitdb.commands.SdRaft.*;
import static j8spec.J8Spec.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class SdRaftSpec {

  static {
    describe("SummitDB RAFT operations", () -> {
      it("Can execute RAFTSTATS commands", () -> {
        sc.withConn(r -> {
          log(raftStats(r));
        });
      });
    });
  }

}
