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

### Networking:

Leaderboard i wymuszona weryfikacja danych o backend, czy podejście Offline First?

Zależnie od rozwinięcia planu i ambicji:

- Supabase

Dobry darmowy tier, idealny na projekt studencki.

Dodatkowo, nie powinno być blokady na konta akademickie jak w przypadku Firebase.

- tani VPS i `mongodb`/`postgressql`

Wyjście bardziej awaryjne, choć nadal funkcjonalne, ograniczone przez wykupione zasoby, aniżeli warunki serwisu.
