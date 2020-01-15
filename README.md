## `summitdb-client`

A minimal Java client for [summitdb](https://github.com/tidwall/summitdb).

## Implemented `summitdb` commands:

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
- [ ] EXPIRE
- [ ] EXPIREAT
- [ ] FENCE
- [ ] FENCEGET
- [x] FLUSHDB
- [x] GET
- [ ] GETBIT
- [ ] GETRANGE
- [ ] GETSET
- [ ] INCR
- [ ] INCRBY
- [ ] INCRBYFLOAT
- [ ] KEYS
- [ ] MGET
- [x] MSET
- [ ] MSETNX
- [ ] PDEL
- [ ] PERSIST
- [ ] PEXPIRE
- [ ] PEXPIREAT
- [ ] PTTL
- [ ] RENAME
- [ ] RENAMENX
- [x] SET
- [ ] SETBIT
- [ ] SETRANGE
- [x] STRLEN
- [ ] TTL

### JSON

- [x] JSET
- [x] JGET
- [x] JDEL

### Indexes and iteration

- [x] DELINDEX
- [x] INDEXES
- [x] ITER
- [x] RECT
- [x] SETINDEX

### Transactions

- [ ] MULTI
- [ ] EXEC
- [ ] DISCARD

### Scripts

- [ ] EVAL
- [ ] EVALRO
- [ ] EVALSHA
- [ ] EVALSHARO
- [ ] SCRIPT LOAD
- [ ] SCRIPT FLUSH

### Raft management

- [ ] RAFTADDPEER
- [ ] RAFTREMOVEPEER
- [ ] RAFTLEADER
- [ ] RAFTSNAPSHOT
- [ ] RAFTSTATE
- [ ] RAFTSTATS

### Server

- [ ] BACKUP
