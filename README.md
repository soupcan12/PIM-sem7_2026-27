**Repozytorium projektowe na zajęcia „Programowanie Interfejsów Mobilnych”, na semestrze 7. Temat aplikacji TBD.**

# Etap 1: Przygotowania i wybory

Ścieżka: Kotlin & Jetpack Compose

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

## Szczegółowy opis:

Gra mobilna, tzw. „clicker”, „idler”.

Wzbogacona w miarę możliwości o różnego rodzaju minigry, rozbudowujące pętle gry.

Artstyle TBD

## Technologie:
### Frontend:

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

Może dla paru elementów, to bardziej kwestia art designu niż wyboru technologii

### Backend:

- SQLite z abstrakcją `Room`

Hardcodowanie kwerend do SQL nie jest najlepszym pomysłem, żeby nie wkopać sie potem w dołek

[Room](https://developer.android.com/training/data-storage/room) podobno bardzo z tym pomaga

Nawet jeżeli zdecydujemy na coś bardziej multiplayerowego, możemy odciążyć serwer przetrzymując dane, które nie będą tam potrzebne

Jako opcja awaryjna pozostaje nam SQLite API i metody zwykłego odczytywania plików https://developer.android.com/training/data-storage

Stosunkowy brak alternatyw bo w przeciwieństwie do regularnej deweloperki, tutaj

![./dwasposoby.png]

### Networking:

Leaderboard i wymuszona weryfikacja danych o backend,

czy podejście Offline First?

- Supabase

Dobry darmowy tier, akurat na projekt studencki

Nie powinno być blokady na konta akademickie jak z Firebase

- mikrus i mongo/postgres

bardziej wyjście awaryjne niż cokolwiek innego

powinienem mieć jeszcze abonament na mongodb na ten semestr

---

# Etap 3: Budowanie projektu

TBD

# Etap 4: Wykończenia

TBD
