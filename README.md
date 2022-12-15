# notatex
# Setting up the stack
In order to start up docker containers designed for your platform, if an image didn't run before, you should delete it and run :
```terminal
make setup
```

It'll generate a .env file suitable for your platform. Now you should be ok running the stack on your local machine.
# Starting the stack
```terminal
make start
```
This command starts the stack with full verbsoe mode, ie. all output will be visible in the terminal.
# Stoping the stack
```terminal
make stop
```
# Deleting redundant docker images
```terminal
make clean
```
So in order to chain stop and delete you'll have to go:
```terminal
make stop && make clean
```
## Code diversity status
```
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                             6             80              0            187
Bourne Shell                     1             28            109            103
Rust                             3             15              0             81
DOS Batch                        1             21              2             68
Cucumber                         8             18              8             65
Gradle                           2              5              0             30
Markdown                         2              5              0             27
D                                4              6              0             16
YAML                             1              0              0             11
Dockerfile                        1              5              0              9
make                             1              0              0              8
Properties                       2              1              0              5
TOML                             1              2              1              5
JSON                             4              0              0              4
Text                             1              4              0              2
-------------------------------------------------------------------------------
SUM:                            38            190            120            621
-------------------------------------------------------------------------------
```
