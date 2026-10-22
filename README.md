**Repozytorium projektowe na zajęcia „Programowanie Interfejsów Mobilnych”, na semestrze 7. Temat aplikacji TBD.**

# Etap 1: Przygotowania i wybory

### Status (21/10/26):
#### Co zrobione od ostatniego razu?

- Tutorial Jetpack Compose ze strony developers.android
- Wybór potencjalnych tematów na projekt

#### Co pozostało do zrobienia?

- Całość projektu

#### Blokady

- Brak znajomości Android API
- Brak rozeznania z językiem Kotlin 

## Temat projektu: **Gra Idle Incremental**

[Opis wstępny](./E1Opis.md)

# Etap 2: Plan na wykonanie

## Dokładniejszy opis:

Gra mobilna, tzw. „clicker”, „idler”. Gracz zbiera punkty naciskając na główny element interfejsu, wydając je później na różnej maści ulepszenia pozwalające zbierać punkty w tempie szybszym niż uprzednio. W planach uwzględnione jest wzbogacenie projektu w miarę możliwości o zróżnicowane minigry, rozbudowujące pętle gry i przynoszące dodatkowe bonusy graczowi.

Poglądowy opis pętli grywalności:
- poklikać parę razy w „główny element”,
- zakupić wybrane ulepszenia,
- *powtarzać do znudzenia*,
- opcj. wziąć udział w nagradzanej/-ych minigrze/-ach,
- opcj. skorzystać z systemu prestiżu, (system [metapostępu](https://en.wiktionary.org/wiki/meta-progression) resetujący dotychczasowe osiągnięcia w zamian za długoterminowe nagrody)
- opcj. dążyć do odkrycia [osiągnięć (def. 4.)](https://en.wiktionary.org/wiki/achievement#Noun) przynoszących odpowiednie bonusy,
- *powtarzać do znudzenia*

Istotną mechaniką tego rodzaju gier jest także otrzymywanie pewnej części punktów, o wartości obliczanej na podstawie ulepszeń oraz czasu od opuszczenia gry.

Wartości artystyczne bliżej nieokreślone na tym etapie projektu, pozostałe szczegóły także niewymienione z racji otwartości twórczej projektu.

## Technologie:
### Frontend:

> Do wyboru:
> 
> Kotlin & JetpackCompose vs Flutter
>
> Lepsza Java, z interfejsem natywnym, wykorzystujący dostęp do API `androidx`
> 
> albo
> 
> Nowy język do nauki, ale wygląda na badziej uniwersalny, do tego w większości crossplatformowy

Wszelkie opcje bardziej przeznaczone dla gier odpadają, bo to PIM a nie gierki

Frameworki webowe byłyby lepszym wyborem jeżeli chcielibyśmy przenosić się z tą grą dalej, ale chyba nie ma sensu

- Material Design?

Jeżeli zdecydujemy się na Kotlina to może się przydać

Może dla paru elementów, to bardziej kwestia art designu niż wyboru technologii

### Backend:

- SQLite z abstrakcją `Room`

Hardcodowanie kwerend do SQL nie jest najlepszym pomysłem, żeby nie wkopać sie potem w dołek

[Room](https://developer.android.com/training/data-storage/room) podobno bardzo z tym pomaga

Nawet jeżeli zdecydujemy na coś bardziej multiplayerowego, możemy odciążyć serwer przetrzymując dane, które nie będą tam potrzebne

Jako opcja awaryjna pozostaje nam SQLite API i metody zwykłego odczytywania plików https://developer.android.com/training/data-storage

Stosunkowy brak alternatyw bo w przeciwieństwie do regularnej deweloperki, tutaj

![są dwa sposoby, mój albo](https://raw.githubusercontent.com/soupcan12/PIM-sem7_2026-27/refs/heads/main/dwasposoby.png)

### Networking:

Leaderboard i wymuszona weryfikacja danych o backend,

czy podejście Offline First?

- Supabase

Dobry darmowy tier, akurat na projekt studencki

Nie powinno być blokady na konta akademickie jak z Firebase

- mikrus i mongo/postgres

bardziej wyjście awaryjne niż cokolwiek innego

i tak powinienem mieć jeszcze abonament na mongodb na ten semestr

---

# Etap 3: Budowanie projektu

TBD

# Etap 4: Wykończenia

TBD
