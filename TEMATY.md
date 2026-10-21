# Temat 1.: **Gra Idle Incremental**

## Opis:

Gra mobilna gatunku „Idler”, inspirowana pozycjami tj. „Cookie Clicker”, „NGU Idle”, „Clicker Heroes”.

Gry noszące miano tego gatunku cechują się przede wszystkim niską wymaganą aktywnością gracza, prostotą dążenia do celu, o ile taki wogóle widnieje. Głównym i najbardziej uniwersalnym motywem skłaniających graczy do kontynuacji jest pot. „number go up”.

Sądzimy, iż będzie to dobry plan do przyjęcia jako tematykę projektu na ten przedmiot, z racji jego wolności twórzej, stosunkowo niskiego progu wejściowego podczas tworzenia, a zarazem wysokiego „sufitu” możliwości, zarówno względem wykonania, jak i designu graficznego, toku gry samej w sobie, oraz zaplecza technicznego, jeżeli chodzi o optymalizację wykorzystania zasobów.

## Wymagania:
### Funkcjonalne

- Projekt posiada typowe cechy i ścieżki gry gatunkowej (obiekt do „klikania”, jednostki waluty do zbierania i wydawania, system tzw. prestiżowania).
- Projekt posiada urozmaicenia w postaci minigier nieskładających, aczkolwiek przyczyniających się do głównego systemu gry.

### Niefunkcjonalne

- Projekt naturalnie zlicza i przetwarza duże ilości „waluty” gracza, nie poniżej 2^64-1.
- Projekt uruchamia się na przykładowych urządzeniach fizycznych i/lub emulatorach.
- Projekt nie marnuje zbędnie zasobów obliczeniowych i/lub baterii urządzenia.
- Projekt nie posiada dependencji wymagających instalacji Google Play Services.

## Wybrane technologie

**Kotlin & Jetpack Compose**

~~ponieważ Godot nie spełnia warunków kursu~~

## Status:
### Co zrobione od ostatniego razu?

- Tutorial Jetpack Compose ze strony developers.android
- Wybór potencjalnych tematów na projekt

### Co pozostało do zrobienia?

- Całość projektu

### Blokady

- Brak znajomości Android API
- Brak rozeznania z językiem Kotlin 

---

# Temat 2.: **Uniwersalny Downloader** z użyciem `yt-dlp`/`aria2`

## Opis:

Aplikacja umożliwiająca archiwizację materiałów z różnych stron i mediów społecznościowych bezpośrednio na urządzeniu lokalnym.

Parę takich rozwiązań istnieje już na rynku, gdzie inspiracja przyszła właśnie z jednego z nich.

Wygoda korzystania z aplikacji, która konsoliduje te podsystemy, już utwardzone jako de facto standardy do pobierania plików przez sieć, na urządzeniach mobilnych, przychodzi z braku konieczności kopiowania linków do mediów pomiędzy telefonem i komputerem, bądź przeklejaniem linków do terminala i siłowania się z interfejsem CLI na klawiaturze dotykowej.

## Wymagania
### Funkcjonalne:

- Aplikacja parsuje linki URL wprowadzone manualnie przez użytkownika, bądź udostępnione do niej przez Android Intent API.
- Aplikacja jako ustawienie przyjmuje ustalenie lokalizacji folderu do przechowywania pobranych plików.
- Aplikacja wykorzystuje możliwości bibliotek tytułowych, udostępniając wybrane opcje użytkownikowi. 

### Niefunkcjonalne

- Aplikacja przetwarza linki URL, przechodząc przez (przynajmniej podstawowe) przekierowania stron.
- Aplikacja, wraz z pomocą bibliotek tytułowych, pobiera materiały zawarte w udostępnionym linku, zapisując je w ww. folderze.

---

# Temat 3.: **Mobilny Frontend** ułatwiający korzystanie z `ffmpeg`

## Opis:

Narzędzie `ffmpeg`, jako narzędzie zaawansowane, posiada wiele opcji i przełączników. Jest to także narzędzie CLI, przeznaczone m.in. na komputery osobiste, przynajmniej w wersji interaktywnej.

Są to cechy niesprzyjające jego użyciu przez użytkownika urządzenia mobilnego, pomimo jego użyteczności i częstej konieczności, bądź to z powodów limitów rozmiaru wysyłania pliku, albo konieczności wykorzystania pewnego formatu, lub kodeku.

Aplikacja ta ma za zadanie ułatwić/umożliwić korzystanie z tego narzędzia, bez konieczności wyszukiwania niezaufanych metod, czy też poleganiu na dostępności serwisu internetowego.

## Wymagania
### Funkcjonalne:

- Aplikacja pozwala użytkownikowi ustalić wybrane parametry podsystemu tytułowego, celem dostrojenia właściwości materiału wyjściowego.
- Aplikacja po przetworzeniu pliku przedstawia użytkownikowi wybór, czy plik zapisać lokalnie, czy wysłać go poprzez Android Intent, pozwalający użytkownikowi wysłać plik do dowolnej aplikacji.

### Niefunkcjonalne:

- Aplikacja jest względem rozsądku nieintruzyjna, mająca służyć jako wersja przenośna narzędzia, aniżeli jego kompletne zastępstwo.
- Aplikacja, o ile okaże się to możliwe do wprowadzenia, jest w stanie samoczynnie zaktualizować wersję tytułowego podsystemu.
