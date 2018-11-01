#!/usr/bin/env bash

./setup.sh email add no-reply@mailparser.localhost 123456
./setup.sh alias add @localhost no-reply@mailparser.localhost
