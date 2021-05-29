## `summitdb-client`

A minimal Java client for [kvnode](https://github.com/tidwall/kvnode).

## Implemented `kvnode` commands:

> Note: some standard Redis commands may be implemented,
> but are not supported by `kvnode` yet.

### Keys and values

- [x] APPEND
- [ ] BITCOUNT
- [ ] BITOP
- [ ] BITPOS
- [x] DBSIZE
- [ ] DECR
- [ ] DECRBY
- [x] DEL
- [x] EXISTS
- [x] EXPIRE
- [x] EXPIREAT
- [x] FLUSHDB
- [x] GET
- [ ] GETBIT
- [ ] GETRANGE
- [ ] GETSET
- [ ] INCR
- [ ] INCRBY
- [ ] INCRBYFLOAT
- [x] KEYS
- [ ] MGET
- [x] MSET
- [ ] MSETNX
- [ ] PERSIST
- [x] PEXPIRE
- [x] PEXPIREAT
- [x] PTTL
- [ ] RENAME
- [ ] RENAMENX
- [x] SET
- [ ] SETBIT
- [ ] SETRANGE
- [x] STRLEN
- [x] TTL

### Transactions

- [ ] MULTI
- [ ] EXEC
- [ ] DISCARD

### Scripts

- [ ] EVAL
- [ ] EVALSHA
- [ ] SCRIPT LOAD
- [ ] SCRIPT FLUSH

### Raft management

- [ ] RAFT LEADER
- [ ] RAFT INFO
- [ ] RAFT SERVER LIST
- [ ] RAFT SERVER ADD
- [ ] RAFT SERVER REMOVE
- [ ] RAFT SNAPSHOT NOW
- [ ] RAFT SNAPSHOT LIST
- [ ] RAFT SNAPSHOT FILE
- [ ] RAFT SNAPSHOT READ
