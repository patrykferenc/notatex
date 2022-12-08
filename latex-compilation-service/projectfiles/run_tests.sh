#!/bin/sh
export RUSTFLAGS="-Cinstrument-coverage" &&     
cargo build &&
cargo test &&
grcov . -s . --binary-path ./target/debug/ -t html --branch --ignore-not-existing -o ./target/debug/coverage/
