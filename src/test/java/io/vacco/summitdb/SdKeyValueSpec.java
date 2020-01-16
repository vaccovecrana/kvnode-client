package io.vacco.summitdb;

import io.vacco.summitdb.options.*;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;
import java.util.concurrent.TimeUnit;

import static j8spec.J8Spec.*;
import static io.vacco.summitdb.SdLocalClient.*;
import static io.vacco.summitdb.commands.SdBase.*;
import static io.vacco.summitdb.commands.SdKeyValue.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class SdKeyValueSpec {

  static {
    describe("SummitDB Key/value operations", () -> {

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
    });
  }
}
