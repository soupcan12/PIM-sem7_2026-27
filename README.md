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

### Status (28/10/26):
#### Co zrobiono od ostatniego razu?

- Rozpatrzono wybory i kierunek projektu
- Rozpatrzenie możliwości oprawy graficznej i motywu artystycznego gry

#### Co pozostało do zrobienia?

- (ostateczny) Wybór kierunku artystycznego
- Fundamenty aplikacji projektowej
- Całość projektu

#### Blokady

- Praca inżynierska
- Nierówność w zdolnościach artystycznych względem autorów projektu

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

[Opis technologii](./E2Tech.md)

---

# Etap 3: Budowanie projektu

### Status (11/11/26):
#### Co zrobiono od ostatniego razu?

- Kierunek artystyczny gry
- Fundamenty działającej gry
 - Importowanie grafiki
 - Poruszające się postacie (logika placeholder)
 - Elementy interfejsu (placeholder)
 - Dynamiczne rozmieszczenie elementów w tle
  - Z uwagą na różne rozdzielczości urządzeń

#### Co pozostało do zrobienia?

- Logika gry
 - Minigry
 - Ulepszenia
 - Progresja
- Całość projektu

#### Blokady

- Android Studio
- Praca inżynierska

### Status (18/11/26):
#### Co zrobiono od ostatniego razu?

- Ulepszenia
- Zmienne wyglądy postaci
- Poprawa zachowania bannera
- Zmiany „pod maską”
- Określenie i ograniczenie ambicji
- Więcej grzechów nie pamiętam

#### Co pozostało do zrobienia?

- Minigry
- Zbalansowanie gry
- Dodatkowe widoki postaci

#### Blokady:

- Akceleracja Obliczeń w Przetwarzaniu Danych
- Praca dyplomowa

# Etap 4: Wykończenia

### Status (??/11/26)
#### Co zrobiono od ostatniego razu?

- Rozbito sprite postaci na pojedyncze elementy
    - Zmieniono galerię na sklep z ciuchami
- Postać porusza się w bardziej dynamiczny sposób
- Gra ma ikonkę [szybki placeholder]
- Dopracowane menu nawigacji
- Ekran minigier
    - Placeholdery dla gier
    - Podzespoły i przygotowanie
- Ujednolicony interfejs zakładek

#### Co zostało do zrobienia?

- Spolszczenie
- Faktyczna minigra
- SPOILER ||sprite do korupcji postaci||
    - SPOILER ||przerobienie systemu korupcji (rozbić na poszczególne elementy zamiast jednego sprite)||
- wyrzucić tekst tytułowy bo jest brzydki tbh
- Zbalansowanie ulepszeń

#### Blokady:

- Praca dyplomowa
- Akceleracja Obliczeń w Przetwarzaniu Danych
- Czekanie na nowe spritey (spoiler)
