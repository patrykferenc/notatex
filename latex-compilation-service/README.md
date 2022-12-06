# Compilation app contract
## Sending file to be compiled
Endpoint:
```
<resolvable hostname>/compile-tex
```
Request, with .tex file :
```
POST: Content-Type: application/octet-stream
BODY:
{
  name: "name.tex"
}
```
Returns on success:
```
200, <filename>.pdf
```
Returns on failure:
```
400, spadaj dziadu
```
