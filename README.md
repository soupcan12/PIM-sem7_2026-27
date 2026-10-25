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

~~Do wyboru~~

- Kotlin & JetpackCompose

Frameworki webowe były by lepszym wyborem dla dostępności i prostoty, aczkolwiek idea ta krzyżuje się z zamysłem przedmiotu. Podobna kwestia dotyczy jakiegokolwiek silnika przeznaczonego dla gier.

- Material Design

Dla wybranych elementów, bądź jako ogólne zasady dla spójności interfejsu.

### Backend:

- SQLite z abstrakcją `Room`

Hardcodowanie kwerend do SQL nie jest najlepszym pomysłem, delikatnie mówiąc. O ile ułatwia początkową pracę, tym bardziej wprowadza tzw. „Tech debt” od samego początku projektu.

Abstrakcja [Room](https://developer.android.com/training/data-storage/room) powinna bardzo pomóc z tym problemem.

Nawet jeżeli zdecydujemy się w projekcie na aspekty bardziej wieloosobowe, możemy odciążyć serwer globalny, przetrzymując dane, które nie będą tam potrzebne na urządzeniu końcowym.

Jako opcja awaryjna pozostaje nam SQLite API i metody zwykłego odczytywania plików: [https://developer.android.com/training/data-storage](https://developer.android.com/training/data-storage)

Stosunkowy brak alternatyw bo w przeciwieństwie do regularnej deweloperki, tutaj jest „jedyna błogosławiona droga”.

![są dwa sposoby, mój albo](https://raw.githubusercontent.com/soupcan12/PIM-sem7_2026-27/refs/heads/main/dwasposoby.png)

### Networking:

Leaderboard i wymuszona weryfikacja danych o backend, czy podejście Offline First?

Zależnie od rozwinięcia planu i ambicji:

- Supabase

Dobry darmowy tier, idealny na projekt studencki.

Dodatkowo, nie powinno być blokady na konta akademickie jak w przypadku Firebase.

- tani VPS i `mongodb`/`postgressql`

Wyjście bardziej awaryjne, choć nadal funkcjonalne, ograniczone przez wykupione zasoby, aniżeli warunki serwisu.

---

# Etap 3: Budowanie projektu

TBD

# Etap 4: Wykończenia

TBD
