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
        sc = new SdClient("127.0.0.1", 7483);
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
          log(set(r, "fleet:1:pos", "[-121.896 32.334]"));
          log(set(r, "fleet:2:pos", "[-116.671 35.735]"));
          log(set(r, "fleet:3:pos", "[-113.902 31.234]"));
          log(rect(r, new SdRect().index("fleet").rectangle("[-117 30],[-112 36]")));
        });
        sc.withConn(r -> {
          log(flushDb(r));
          log(setIndex(r, new SdSetIndexSpatial().namePattern(new SdSetIndexNamePattern().name("fleet").pattern("fleet:*")).json("pos")));
          log(set(r, "fleet:0", "{\"driver\":\"Janet\",\"pos\":{\"type\":\"Point\",\"coordinates\":[-115.567,33.532]}}"));
          log(set(r, "fleet:1", "{\"driver\":\"Tom\",\"pos\":{\"type\":\"Point\",\"coordinates\":[-121.896,32.334]}}"));
          log(set(r, "fleet:2", "{\"driver\":\"Andrew\",\"pos\":{\"type\":\"Point\",\"coordinates\":[-116.671,35.735]}}"));
          log(set(r, "fleet:3", "{\"driver\":\"Pam\",\"pos\":{\"type\":\"Point\",\"coordinates\":[-113.902,31.234]}}"));
          log(rect(r, new SdRect().index("fleet").rectangle("{\"pos\":\"[-117 30],[-112 36]\"}")));
        });
      });

      it("Can execute DELINDEX commands", () -> {
        sc.withConn(r -> {
          log(setIndex(r, new SdSetIndexText().namePattern(new SdSetIndexNamePattern().name("idx1").pattern("*"))));
          log(delIndex(r, "idx1"));
        });
      });

      it("Can execute INDEXES commands", () -> {
        sc.withConn(r -> {
          log(setIndex(r, new SdSetIndexText().namePattern(new SdSetIndexNamePattern().name("name").pattern("user:*:name"))));
          log(setIndex(r, new SdSetIndexNumber().namePattern(new SdSetIndexNamePattern().name("age").pattern("user:*:age")).type(SdSetIndexNumber.Type.INT)));
          log(indexes(r, new SdIndexes().pattern("*")));
          log(indexes(r, new SdIndexes().pattern("*").details(true)));
        });
      });

      it("Can execute TTL/EXPIRE commands", () -> {
        sc.withConn(r -> {
          log(flushDb(r));
          log(set(r, "ttlKey", "Hello"));
          log(expire(r, "ttlKey", 10));
          log(ttl(r, "ttlKey"));
          log(set(r, "ttlKey", "Hello World"));
          log(ttl(r, "ttlKey"));

          log(set(r, "ttlKey1", "Hello"));
          log(exists(r, "ttlKey1"));
          log(expireAt(r, "ttlKey1", 1293840000));
          log(exists(r, "ttlKey1"));

          log(mset(r, new SdMSet().add("one", "1").add("two", "2").add("three", "3").add("four", "4")));
          log(keys(r, new SdKeys().pattern("*o*")));
          log(keys(r, new SdKeys().pattern("t??")));
          log(keys(r, new SdKeys().pattern("*").withValues(true)));
        });
      });

      it("Can execute PTTL/PEXPIRE commands", () -> {
        sc.withConn(r -> {
          log(flushDb(r));
          log(set(r, "myKey", "Hello"));
          log(expire(r, "myKey", 1));
          log(pTtl(r, "myKey"));

          log(flushDb(r));
          log(set(r, "myKey", "Hello"));
          log(pExpire(r, "myKey", 1500));
          log(ttl(r, "myKey"));
          log(pTtl(r, "myKey"));

          log(flushDb(r));
          log(set(r, "myKey2", "Hello"));
          log(pExpireAt(r, "myKey2", "4072275820000"));
          log(ttl(r, "myKey2"));
          log(pTtl(r, "myKey2"));
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

          // DEL
          log(set(r, "keyT1", "Hello"));
          log(set(r, "keyT2", "Hello"));
          log(del(r, "keyT1", "keyT2", "keyT3"));

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
