package io.vacco.summitdb;

import io.vacco.summitdb.options.*;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import java.util.concurrent.TimeUnit;

import static j8spec.J8Spec.*;
import static io.vacco.summitdb.commands.SdBase.*;
import static io.vacco.summitdb.commands.SdJson.*;
import static io.vacco.summitdb.commands.SdKeyValue.*;
import static io.vacco.summitdb.commands.SdIndex.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class SdClientSpec {

  private static SdClient sc;

  private static void log(Object o) {
    System.out.println(o);
  }

  static {
    describe("SummitDB client", () -> {
      it("Can open a connection towards a target instance", () -> {
        sc = new SdClient("127.0.0.1", 7481);
      });
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
      it("Can execute MSET commands", () -> {
        sc.withConn(r -> {
          log(mset(r, new SdMSet().add("key1", "Hello").add("key2", "World")));
          log(get(r, "key1"));
          log(get(r, "key2"));
        });
      });
      it("Can execute STRLEN commands", () -> {
        sc.withConn(r -> {
          log(set(r, "mykey", "Hello World"));
          log(strLen(r, "mykey"));
          log(strLen(r, "nonexisting"));
        });
      });

      it("Can execute SETINDEX TEXT commands", () -> {
        sc.withConn(r -> {
          log(setIndex(r, new SdSetIndexText()
              .namePattern(new SdSetIndexNamePattern().name("names").pattern("user:*:name"))
          ));
          log(set(r, "user:0:name", "tom"));
          log(set(r, "user:1:name", "Randi"));
          log(set(r, "user:2:name", "jane"));
          log(set(r, "user:4:name", "Janet"));
          log(set(r, "user:5:name", "Paula"));
          log(set(r, "user:6:name", "peter"));
          log(set(r, "user:7:name", "Terri"));

          log(iter(r, new SdIter().index("names")));
          log(iter(r, new SdIter().index("names").match("*2*")));
          log(iter(r, new SdIter().index("names").limit(new SdLimit().limit(3))));
          log(iter(r, new SdIter().index("names").limit(new SdLimit().limit(3))));
          log(iter(r, new SdIter().index("names").rangeMin("matt").rangeMax("peter")));
          log(iter(r, new SdIter().index("names").rangeMin("matt").rangeMax("peter").limit(new SdLimit().limit(1))));
          log(iter(r, new SdIter().index("names").rangeMin("matt").rangeMax("+inf")));
        });
      });

      it("Can execute SETINDEX NUMBER commands", () -> {
        sc.withConn(r -> {
          log(setIndex(r,
              new SdSetIndexNumber().namePattern(
                  new SdSetIndexNamePattern().name("ages").pattern("user:*:age")
              ).type(SdSetIndexNumber.Type.INT)
          ));
          log(set(r, "user:0:age", "35"));
          log(set(r, "user:1:age", "49"));
          log(set(r, "user:2:age", "13"));
          log(set(r, "user:4:age", "63"));
          log(set(r, "user:5:age", "8"));
          log(set(r, "user:6:age", "3"));
          log(set(r, "user:7:age", "16"));
          log(iter(r, new SdIter().index("ages")));
        });
      });

      it("Can execute SETINDEX JSON commands", () -> {
        sc.withConn(r -> {
          log(flushDb(r));
          log(setIndex(r,
              new SdSetIndexJson().namePattern(
                  new SdSetIndexNamePattern().name("last_name").pattern("*")
              ).json("name.last")
          ));
          log(set(r, "user:1", "{\"name\":{\"first\":\"Tom\",\"last\":\"Johnson\"},\"age\":38}"));
          log(set(r, "user:2", "{\"name\":{\"first\":\"Janet\",\"last\":\"Prichard\"},\"age\":47}"));
          log(set(r, "user:3", "{\"name\":{\"first\":\"Carol\",\"last\":\"Anderson\"},\"age\":52}"));
          log(set(r, "user:4", "{\"name\":{\"first\":\"Alan\",\"last\":\"Cooper\"},\"age\":28}"));
          log(iter(r, new SdIter().index("last_name")));
        });
        sc.withConn(r -> {
          log(flushDb(r));
          log(setIndex(r,
              new SdSetIndexJson().namePattern(
                  new SdSetIndexNamePattern().name("last_name_age").pattern("*")
              ).json("name.last").json("age")
          ));
          log(set(r, "user:1", "{\"name\":{\"first\":\"Tom\",\"last\":\"Johnson\"},\"age\":38}"));
          log(set(r, "user:2", "{\"name\":{\"first\":\"Janet\",\"last\":\"Prichard\"},\"age\":47}"));
          log(set(r, "user:3", "{\"name\":{\"first\":\"Carol\",\"last\":\"Anderson\"},\"age\":52}"));
          log(set(r, "user:4", "{\"name\":{\"first\":\"Alan\",\"last\":\"Cooper\"},\"age\":28}"));
          log(set(r, "user:5", "{\"name\":{\"first\":\"Sam\",\"last\":\"Anderson\"},\"age\":51}"));
          log(set(r, "user:6", "{\"name\":{\"first\":\"Melinda\",\"last\":\"Prichard\"},\"age\":44}"));
          log(iter(r, new SdIter().index("last_name_age")));
        });
      });

      it("Can execute SETINDEX USER commands", () -> {
        sc.withConn(r -> {
          log(flushDb(r));
          log(setIndex(r, new SdSetIndexUser().namePattern(
              new SdSetIndexNamePattern()
                  .name("ssn").pattern("user:*:ssn")
              ).eval(
                  "function(a, b){var aparts=a.split('-');var bparts=b.split('-');for (var i=0;i<3;i++){if (parseInt(aparts[i])<parseInt(bparts[i])){return true;}if (parseInt(aparts[i])>parseInt(bparts[i])){return false;}}return false;}"
              )
          ));
          log(set(r, "user:1:ssn", "483-23-1234"));
          log(set(r, "user:2:ssn", "903-12-8735"));
          log(set(r, "user:3:ssn", "120-77-9812"));
          log(iter(r, new SdIter().index("ssn")));
        });
      });

      it("Can execute SETINDEX SPATIAL commands", () -> {
        sc.withConn(r -> {
          log(setIndex(r, new SdSetIndexSpatial().namePattern(new SdSetIndexNamePattern().name("fleet").pattern("fleet:*:pos"))));
          log(set(r, "fleet:0:pos", "[-115.567 33.532]"));

        });
      });

      it("Can change pool timeout values", () -> {
        sc.setTimeout(3, TimeUnit.SECONDS);
      });
      it("Can execute key/value commands", () -> {
        sc.withConn(r -> {
          log(flushDb(r));
          // APPEND
          log(exists(r, "mykey0"));
          log(append(r, "mykey0", "Hello"));
          log(append(r, "mykey0", " World"));
          log(get(r, "mykey0"));

          // EXISTS
          log(set(r, "key1", "Hello"));
          log(exists(r, "key1"));
          log(exists(r, "nosuchkey"));
          log(exists(r, "key2", "World"));
          log(exists(r, "key1", "key2", "nosuchkey"));

          // GET
          log(get(r, "nonexisting"));
          log(set(r, "mykey1", "Hello"));
          log(get(r, "mykey1"));

          // SET
          log(set(r, new SdSetEx().key("key3").value("Friend").exSeconds(5).nx(true)));
        });
      });
      it("Can shutdown the client", () -> sc.getPool().shutdown());
    });
  }
}
