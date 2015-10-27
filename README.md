# SjakkApp.SjakkLogikk
[6109]Prosjekt 2 - SjakkApp.SjakkLogikk
### Bildemateriale
Laget spillbrettet selv

Hentet spillbrikker fra[sjakkbrikker](https://openclipart.org/detail/11373/chess-set)
### Javadoc
Kjør javadoc med parametere:
> -encoding UTF-8 -docencoding UTF-8 -charset UTF-8

for å få riktige tegn!

### Endringer for å øke ytelsen
 - Kun bruke heltall internt for å finne brikker etc: 2x
 - Redusere antall sjekker i brikke.getGyldigePosisjoner: 2-3x
 - Total endring: 25000 -> 140000 simulerte trekk per sekund
 - Fikset en bug i minimax -> ~2.2-2.4 millioner trekk per sekund