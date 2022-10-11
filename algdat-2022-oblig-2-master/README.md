# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Morten Nilsen, S371473, s371473@oslomet.no

# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* Morten har gjort alt

# Oppgavebeskrivelse

##  Oppgave 1
I oppgave 1 lagde jeg først metodene antall() og tom(), som er ganske lette metoder. Så skulle jeg lage en konstruktør for klassen. I første utkast benyttet jeg meg av en hjelpetabell slik at jeg først kunne lage den lenkede listen med kun "forrige" pekere, for å så loope gjennom igjen og legge til "neste" pekere. Metoden fungerte forsåvidt bra, men den bruker jo unødvendig mye minne med hjelpetabellen. Heldigvis etter å ha gjort et par av de andre oppgavene lærte jeg meg noen teknikker som lot meg konstruere den lenkede listen med kun én for-løkke.

##  Oppgave 2
I oppgave 2 skulle jeg lage en toString metode og en omvendtString metode. Jeg lagde dem egentlig helt likt, bare omvendt, alstå at i omvendtString starter for-løkka på halen istedet for hodet. Jeg brukte StringBuilder klassen for å sette sammen strengen. I b) oppgaven skulle jeg lage den første leggInn metoden. Jeg oppretter en ny node og setter attributtene i henhold til de to tilfellene. 

##  Oppgave 3
I denne oppgaven skulle jeg først lage metoden finnNode som looper gjennom den lenkede listen til den kommer til ønsket indeks, og returnerer noden. Metoden gjøres raskere ved å benytte at vi vet indeksen, og kan da starte enten fra starten eller bakerst når man looper. Denne metoden blir brukt i de to neste metodene jeg skulle lage, nemlig hent og oppdater. I hent metoden brukte jeg en indeksKontroll som implementeres fra Liste klassen. Oppdater klassen returnerer verdien til en node, som blir funnet ved hjelp av finnNode klassen. I b) oppgaven skulle jeg lage en metode som returnerer en subliste av den lenkede listen vår. Jeg brukte en fratilKontroll metode jeg fant i kompendiet til å validere grensene, og så lagde en ny instans av klassen DobbeltLenketListe. Så lagde jeg en for-løkke som legger inn en og en verdi fra den opprinnelige lista til den nye lista. 

##  Oppgave 4
Her lagde jeg en metode som loopet gjennom den lenkede listen og sjekket om hver verdi tilsvarer argumentet. Hvis den finner den, returnerer den indeksen den har kommet til og for-løkken avsluttes. Hvis ikke, vil den returnere -1. Metoden inneholder(T verdi) bruker indeksTil metoden til å sjekke om en verdi er i listen ved å sjekke om returverdien til indeksTil er lik -1 eller ikke.

##  Oppgave 5
I denne oppgaven skulle jeg lage en utvidet versjon av leggInn metoden, hvor man også kan spesifisere indeks/posisjonen til verdien man vil legge inn. Først brukte jeg indeksKontroll og requireNonNull metodene for å validere argumentene, for å så bruke if/else setninger til å skille de ulike tilfellene. Hvis listen er tom eller verdien skal legges inn bakerst, gjenbrukes den første leggInn metoden. Hvis verdien skal legges inn forrerst, må hode pekeren oppdateres. Hvis den skal legges mellom to noder, må begge disse nodene oppdatere sine neste og forrige pekere.

##  Oppgave 6
I denne oppgaven skulle jeg lage to metoder for å fjerne en node, den ene med verdi som argument, og den andre med indeks som argument. I den første lager jeg en for-løkke som looper gjennom og sjekker om verdi er lik currentNode.verdi. Hvis den finner en match, oppdaterer den attributter og sånt i henhold til hvilken tilfelle den faller under, litt på samme måte som leggInn metoden. Den andre metoden bruker mange av de teknikkene for å oppdatere attributtene og sjekke spesialtilfeller, men finner frem til noden ved å bruke en annen for-løkke som kun kjøres fra 0 til indeks.

##  Oppgave 8
I oppgave 8 skulle jeg lage en Iterator klasse for DobbeltLenke klassen. Oppgave a) var veldig snill ettersom den beskrev akkurat hva man skulle skrive. Det samme gjelder egentlig de andre deloppgavene også. jUnit testen til denne oppgaven brukte metoden nullstill som egentlig skulle lages i oppgave 7, så jeg skrev koden til de to metodene i DobbeltLenkeListe klassen, men jeg gjorde ikke tidsmålinger slik som oppgaven egentlig krevde.
