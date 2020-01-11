package io.vacco.summitdb;

import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import java.util.concurrent.TimeUnit;

import static j8spec.J8Spec.*;
import static io.vacco.summitdb.commands.SdJson.*;
import static io.vacco.summitdb.commands.SdKeyValue.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class SdClientSpec {

  private static SdClient sc;

  static {
    describe("SummitDB client", () -> {
      it("Can open a connection towards a target instance", () -> {
        sc = new SdClient("127.0.0.1", 7481);
      });
      it("Can execute JSON commands", () -> {
        sc.withConn(r -> {
          System.out.println(jset(r, "user:101", "name", "Tom"));
          System.out.println(jset(r, "user:101", "age", "46"));
          System.out.println(jget(r, "user:101", "age"));

          System.out.println(jset(r, "user:101", "name.first", "Tom"));
          System.out.println(jset(r, "user:101", "name.last", "Anderson"));
          System.out.println(jdel(r, "user:101", "name.last"));
        });
      });
      it("Can change pool timeout values", () -> {
        sc.setTimeout(3, TimeUnit.SECONDS);
      });
      it("Can execute key/value commands", () -> {
        sc.withConn(r -> {
          // APPEND
          System.out.println(exists(r, "mykey0"));
          System.out.println(append(r, "mykey0", "Hello"));
          System.out.println(append(r, "mykey0", " World"));
          System.out.println(get(r, "mykey0"));

          // EXISTS
          System.out.println(set(r, "key1", "Hello"));
          System.out.println(exists(r, "key1"));
          System.out.println(exists(r, "nosuchkey"));
          System.out.println(exists(r, "key2", "World"));
          System.out.println(exists(r, "key1", "key2", "nosuchkey"));

          // GET
          System.out.println(get(r, "nonexisting"));
          System.out.println(set(r, "mykey1", "Hello"));
          System.out.println(get(r, "mykey1"));
        });
      });
      it("Can shutdown the client", () -> sc.getPool().shutdown());
    });
  }
}
