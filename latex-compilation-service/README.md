# Compilation app contract
## Sending file to be compiled
Endpoint:
```
<resolvable hostname>/compile-tex
```
Request can be summarized with these curls:
```terminal
(test_resources) $ curl -d "@test_file_valid.tex" -X POST http://localhost:8000/compile-tex  >> please.pdf
```
Response is a binary dump which, even if the texfile sent is not correct, will send a dump that when saves as pdf will be openable and readable.
## Running microbenchmarks
In project directory run:
```
cargo bench
```