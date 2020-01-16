package io.vacco.summitdb;

import io.vacco.summitdb.options.*;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import static io.vacco.summitdb.SdLocalClient.*;
import static io.vacco.summitdb.commands.SdBase.flushDb;
import static io.vacco.summitdb.commands.SdIndex.*;
import static io.vacco.summitdb.commands.SdKeyValue.set;
import static j8spec.J8Spec.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class SdIndexSpec {

  static {

    describe("SummitDB index operations", () -> {
      it("Can execute SETINDEX TEXT commands", () -> {
        sc.withConn(r -> {
          log(setIndexText(r, "names", "user:*:name"));
          log(set(r, "user:0:name", "tom"));
          log(set(r, "user:1:name", "Randi"));
          log(set(r, "user:2:name", "jane"));
          log(set(r, "user:4:name", "Janet"));
          log(set(r, "user:5:name", "Paula"));
          log(set(r, "user:6:name", "peter"));
          log(set(r, "user:7:name", "Terri"));

          log(iter(r, "names"));
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
          log(setIndexNumber(r,"ages", "user:*:age", SdSetIndexNumber.Type.INT));
          log(set(r, "user:0:age", "35"));
          log(set(r, "user:1:age", "49"));
          log(set(r, "user:2:age", "13"));
          log(set(r, "user:4:age", "63"));
          log(set(r, "user:5:age", "8"));
          log(set(r, "user:6:age", "3"));
          log(set(r, "user:7:age", "16"));
          log(iter(r, "ages"));
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
          log(iter(r, "last_name"));
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
          log(iter(r, "last_name_age"));
        });
      });

      it("Can execute SETINDEX USER commands", () -> {
        sc.withConn(r -> {
          log(flushDb(r));
          log(setIndex(r, new SdSetIndexUser()
              .namePattern(new SdSetIndexNamePattern().name("ssn").pattern("user:*:ssn"))
              .eval("function(a, b){var aparts=a.split('-');var bparts=b.split('-');for (var i=0;i<3;i++){if (parseInt(aparts[i])<parseInt(bparts[i])){return true;}if (parseInt(aparts[i])>parseInt(bparts[i])){return false;}}return false;}")
          ));
          log(set(r, "user:1:ssn", "483-23-1234"));
          log(set(r, "user:2:ssn", "903-12-8735"));
          log(set(r, "user:3:ssn", "120-77-9812"));
          log(iter(r, "ssn"));
        });
      });

      it("Can execute SETINDEX SPATIAL commands", () -> {
        sc.withConn(r -> {
          log(setIndexSpatial(r, "fleet", "fleet:*:pos"));
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
    });
  }
}
