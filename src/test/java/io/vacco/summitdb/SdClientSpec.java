package io.vacco.summitdb;

import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import static j8spec.J8Spec.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class SdClientSpec {

  private static SdClient sc;

  static {
    describe("SummitDB client", () -> {
      it("Can open a connection towards a target instance", () -> {
        sc = new SdClient("127.0.0.1", 7481);
      });
      it("Can execute a command", () -> {
        sc.withConn(r -> {
          System.out.println(sc.rawStrCmd(r, "JSET", "user:101", "name", "Tom"));
          System.out.println(sc.rawStrCmd(r, "JSET", "user:101", "age", "46"));
          System.out.println(sc.rawStrCmd(r, "GET" , "user:101"));
          System.out.println(sc.rawStrCmd(r, "RAFTLEADER"));
        });
      });
      it("Can shutdown the client", () -> sc.getPool().shutdown());
    });
  }
}
