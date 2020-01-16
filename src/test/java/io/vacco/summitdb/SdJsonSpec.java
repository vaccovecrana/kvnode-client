package io.vacco.summitdb;

import io.vacco.summitdb.options.*;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import static io.vacco.summitdb.SdLocalClient.*;
import static io.vacco.summitdb.commands.SdBase.flushDb;
import static io.vacco.summitdb.commands.SdJson.*;
import static io.vacco.summitdb.commands.SdKeyValue.*;
import static j8spec.J8Spec.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class SdJsonSpec {

  static {
    describe("SummitDB JSON operations", () -> {
      it("Can execute JGET/JSET commands", () -> {
        sc.withConn(r -> {
          log(flushDb(r));
          log(jset(r, "user:101", "name", "Tom"));
          log(jset(r, "user:101", "age", "46"));
          log(get(r, "user:101"));
          log(jget(r, "user:101", "age"));
          log(jset(r, "user:101", "name.first", "Tom"));
          log(jset(r, "user:101", "name.last", "Anderson"));
          log(get(r, "user:101"));
          log(jdel(r, "user:101", "name.last"));
          log(get(r, "user:101"));
          log(jset(r, "user:101", "friends.0", "Carol"));
          log(jset(r, "user:101", "friends.1", "Andy"));
          log(jset(r, "user:101", "friends.3", "Frank"));
          log(get(r, "user:101"));
          log(jget(r, "user:101", "friends.1"));
          log(dbSize(r));
        });
      });
      it("Can execute JSET command with options", () -> {
        sc.withConn(r -> {
          log(flushDb(r));
          log(jset(r, new SdJSet().key("user:102").path("name").value("Robert")));
          log(jset(r, new SdJSet().key("user:102").path("nameExt").value("{\"first\": \"Robert\", \"last\": \"Benfer\"}").raw(true)));
          log(jget(r, "user:102", "nameExt"));
        });
      });
    });
  }
}
