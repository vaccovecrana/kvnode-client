package io.vacco.summitdb;

import io.vacco.redis.Redis;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import stormpot.Pooled;
import stormpot.Timeout;

import java.util.concurrent.TimeUnit;
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
        Pooled<Redis> pr = sc.getPool().claim(new Timeout(5, TimeUnit.SECONDS));
        try {
          String a = new String((byte[]) pr.object.call("JSET", "user:101", "name", "Tom"));
          String b = new String((byte[]) pr.object.call("JSET", "user:101", "age", "46"));
          String c = new String((byte[]) pr.object.call("GET" , "user:101"));
          System.out.println(a);
          System.out.println(b);
          System.out.println(c);
          System.out.println(new String((byte[]) pr.object.call("RAFTLEADER")));
          System.out.println(new String((byte[]) pr.object.call("GET", "user:*")));
        } finally {
          pr.release();
        }
      });
      it("Can shutdown the client", () -> sc.getPool().shutdown());
    });
  }
}
