# notatex
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
