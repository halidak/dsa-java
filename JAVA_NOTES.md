# Java beleške

Metode, teorija i trikovi, **grupisano po temi** (ne po danu kad sam učila). Za svaku stavku: šta radi, primer, kad se koristi i koliko košta. Na početku svake teme piše iz kojih izvora je.

> Šabloni (sliding window, BFS, DP…) su u [CHEATSHEET.md](CHEATSHEET.md). Ovde idu sitne Java stvari.

**Brzo traženje:** `Ctrl+F` + ime metode (npr. `getOrDefault`), ili klikni na deo ispod.

## Sadržaj

1. [Kako rešavam zadatak](#kako-rešavam-zadatak): šta intervjuer gleda, 6 koraka, komunikacija
2. [Stringovi](#stringovi): `length`, `charAt`, `toCharArray`, `c - 'a'`
3. [Nizovi](#nizovi): `Arrays.sort`, `Arrays.equals`, `Arrays.toString`, for-each, `new int[...]`
4. [Hash mape i setovi: kad i zašto](#hash-mape-i-setovi-kad-i-zašto): poređenje, složenost, ključne reči u zadatku
   - [HashMap](#hashmap): metode, prolazak kroz mapu, Java 8 metode (`computeIfAbsent`, grupisanje), [lambda izrazi](#lambda-izrazi), kako radi iznutra
   - [HashSet](#hashset): `add`, `contains`, `remove`, HashMap vs HashSet
5. [Trikovi iz zadataka](#trikovi-iz-zadataka)

---

## Kako rešavam zadatak

> Izvori: *Coding Interview Patterns*, Introduction (PDF str. 17–21) + moj postupak iz zadataka.

### Šta intervjuer gleda

| Dobro (pozitivni signali) | Loše (negativni signali) |
|---|---|
| zna strukture podataka i **kada** koju da koristi | slabe osnove (strukture, algoritmi) |
| jasno objašnjava razmišljanje i trade-off-ove | ćuti ili ne ume da objasni pristup |
| postavlja pitanja, sarađuje sa intervjuerom | preterano samopouzdanje |
| čist, čitljiv kod | ne prihvata sugestije, drži se jednog pristupa |
| optimizuje vreme i memoriju | ignoriše ograničenja i ivične slučajeve |
| sama testira i ispravlja greške | predugo na jednom delu, ne završi na vreme |

- Mala greška nije problem. Ako je **sama nađem i ispravim**, to je čak plus.
- Nije bitno samo tačno rešenje, nego **kako** sam do njega došla.

### 6 koraka na intervjuu

1. **Razjasni zadatak.** Prepričaj ga svojim rečima. Pitaj za ograničenja i ivične slučajeve: može li niz biti prazan, ima li negativnih brojeva, duplikata, koliko je veliki ulaz?
2. **Smisli algoritam.**
   - Kreni od **brute force**. Jednostavno rešenje je bolje od nikakvog, a bolje rešenje često nastane iz njega. Ako je očigledno, dovoljno je da ga opišeš rečima.
   - Razmotri više pristupa (npr. sortiranje ili HashMap), reci prednosti i mane i izaberi.
   - Za složen algoritam napiši prvo pseudokod.
3. **Proceni složenost** (vreme i memorija), okvirno, pre kodiranja.
4. **Napiši čist kod.** Smislena imena promenljivih, pomoćne metode, a po potrebi i mala klasa (npr. `Interval` sa `start` i `end`).
5. **Ponovo reci složenost**, sad tačno za napisani kod. Ako se razlikuje od procene, objasni zašto.
6. **Testiraj.**
   - prvo jednostavan primer
   - pa ivični slučajevi: prazno, jedan element, duplikati, negativni brojevi
   - pa granične vrednosti: najmanji i najveći mogući ulaz (npr. `0` i `Integer.MAX_VALUE`)

**Posle zadatka (moj postupak):** popunim header (Idea, Complexity, confidence), commit i push, a nove metode i trikove upišem ovde.

### Komunikacija

- **Razmišljaj naglas.** Intervjueru je važnije da vidi kako razmišljaš nego da odmah dođeš do optimalnog rešenja.
- **Proveri da li te prati**, pa ćeš brže čuti ako si krenula pogrešno.
- **Reci alternative**: „Mogu sa sortiranjem O(n log n) ili sa HashSet-om O(n), biram HashSet jer…“
- **Kad zapneš**, reci gde si, šta si probala i zašto misliš da ne ide. Intervjuer će često dati hint.
- **Treba ti vremena?** Reci: „Daj mi minut da razmislim.“ Tako tišina ne izgleda kao da si se izgubila.

---

## Stringovi

| Metoda | Šta radi | Složenost |
|---|---|---|
| `s.length()` | broj karaktera | O(1) |
| `s.charAt(i)` | karakter na poziciji `i` | O(1) |
| `s.toCharArray()` | string → `char[]` | O(n) |
| `c - 'a'` | slovo → broj 0–25 | O(1) |
| `new String(chars)` | `char[]` → String | O(n) |

### `s.length()`
Broj karaktera u stringu.
```java
"rat".length()   // 3
```
- Kod stringa je **metoda** sa zagradama: `length()`. Kod niza je **polje** bez zagrada: `niz.length`.

### `s.charAt(i)`
Karakter na poziciji `i` (kreće od 0).
```java
"rat".charAt(0)  // 'r'
```
- Koristi se za prolazak kroz string slovo po slovo: `for (int i = 0; i < s.length(); i++) { char c = s.charAt(i); }`

### `s.toCharArray()`
Pretvara string u niz karaktera `char[]`.
```java
char[] a = "rat".toCharArray();   // ['r', 'a', 't']
```
- Kad hoću da sortiram slova ili da menjam karaktere (string se ne može menjati, niz može).
- Može i petlja bez indeksa: `for (char c : s.toCharArray()) { ... }`
- O(n) memorije, jer pravi kopiju.

### `c - 'a'`
Pretvara malo slovo u broj od 0 do 25: `'a'` → 0, `'b'` → 1, … `'z'` → 25.
```java
int index = 'c' - 'a';   // 2
```
- Svaki `char` je u stvari broj (ASCII kod), pa oduzimanje radi.
- Koristi se uz `int[] count = new int[26]` za brojanje slova, umesto HashMap-e. To je brže.
- Radi samo ako zadatak kaže „lowercase English letters“.

### `new String(chars)` / `String.valueOf(chars)`
Pravi String od niza karaktera. Obrnuto od `toCharArray()`.
```java
char[] chars = "tea".toCharArray();   // [t, e, a]
Arrays.sort(chars);                   // [a, e, t]
String key = new String(chars);       // "aet"
```
- Koristi se kad sortirana slova treba da budu **ključ u mapi** (Group Anagrams). Niz ne valja kao ključ, jer se nizovi ne porede po sadržaju.
- **Zamka:** `chars.toString()` ne vraća slova, nego nešto kao `[C@1b6d3586` (adresa objekta). Uvek `new String(chars)` ili `String.valueOf(chars)`.

---

## Nizovi

```java
import java.util.Arrays;
```

| Metoda | Šta radi | Složenost |
|---|---|---|
| `Arrays.sort(niz)` | sortira **u mestu**, vraća `void` | O(n log n) |
| `Arrays.equals(a, b)` | isti elementi istim redom? | O(n) |
| `Arrays.toString(niz)` | lep ispis, za debug | O(n) |
| `for (int n : niz)` | prolazak bez indeksa | O(n) |
| `new int[26]` | niz od 26 nula | O(n) |
| `new int[] {a, b}` | niz sa tačno tim vrednostima | O(1) |

### `Arrays.sort(niz)`
Sortira niz **u mestu**: menja originalni niz i ništa ne vraća.
```java
char[] a = {'t', 'a', 'r'};
Arrays.sort(a);                 // a je sad ['a', 'r', 't']
```
- Greška koju ne treba praviti: `a = Arrays.sort(a)` ne radi, jer metoda vraća `void`.
- **`Arrays`, sa s na kraju!** `Array.sort` (bez s) ne postoji i VS Code ga podvuče crveno.
- Sortira samo **niz**. `Arrays.sort("tea")` ne radi, prvo `toCharArray()`.

### `Arrays.equals(a, b)`
`true` ako su dva niza iste dužine i imaju iste elemente istim redom.
```java
Arrays.equals(new int[]{1, 2}, new int[]{1, 2})   // true
```
- Ne koristi `a == b` za nizove! To proverava da li je to **isti objekat** u memoriji, ne da li je sadržaj isti.

### `Arrays.toString(niz)`
Lep ispis niza, za debug.
```java
System.out.println(Arrays.toString(a));   // [a, r, t]
```

### `for (int n : niz)` (for-each)
Prolazi kroz svaki element niza, bez indeksa.
```java
for (int n : count) {
    if (n != 0) return false;
}
```
- Kad ti treba samo vrednost, ne i pozicija.

### `new int[26]`
Niz od 26 nula. U Javi novi `int` niz uvek kreće od nula.
```java
int[] count = new int[26];
count['c' - 'a']++;   // povećaj brojač za slovo c
```

### `new int[] {a, b}`
Pravi novi niz sa tačno tim vrednostima, i odmah može da se vrati.
```java
return new int[] {seen.get(fali), i};   // npr. [0, 1]
```
- Ne postoji `niz[a, b]`. Uglaste zagrade posle imena čitaju **jedan** element: `niz[0]`.

---

## Hash mape i setovi: kad i zašto

> Izvori: *Coding Interview Patterns*, pogl. 2 Hash Maps and Sets (PDF str. 45–47) + [Baeldung – A Guide to Java HashMap](https://www.baeldung.com/java-hashmap). Detalji po strukturi su u delovima [HashMap](#hashmap) i [HashSet](#hashset).

**Ideja:** umesto da pretražujem celu listu (O(n)), „zapamtim“ podatke tako da ih nađem odmah (O(1)). Kao prodavac koji zna cene napamet umesto da ih traži po spisku.

| | HashMap | HashSet |
|---|---|---|
| Čuva | parove ključ → vrednost | samo ključeve |
| Duplikati | ključevi su jedinstveni | elementi su jedinstveni |
| Redosled | nije garantovan | nije garantovan |
| Koristim za | brojanje (frekvencije), keš, rečnik, „broj → pozicija“ | jedinstvene elemente, „već viđeno / posećeno“, proveru duplikata |

**Složenost:**

| Operacija | Prosek | Najgori slučaj |
|---|---|---|
| dodavanje | O(1) | O(n) |
| pretraga | O(1) | O(n) |
| brisanje | O(1) | O(n) |

- Na intervjuu se računa **prosek, O(1)**. Najgori slučaj nastaje kad mnogo ključeva padne u istu kantu (kolizije). U Javi 8+ je to O(log n), jer se kanta pretvara u stablo.
- Memorija je O(n).

**Ključne reči u zadatku** koje upućuju na hash mapu ili set: *frequency, count, unique, duplicate, map, dictionary, fast lookup, seen/visited*.

---

## HashMap

Čuva parove **ključ → vrednost**. Ključevi su jedinstveni, redosled nije garantovan.

```java
import java.util.HashMap;
import java.util.Map;

Map<String, Integer> map = new HashMap<>();
```

> Izvor: [Baeldung – A Guide to Java HashMap](https://www.baeldung.com/java-hashmap), delovi 2, 4 i 6

### Brzi pregled

| Metoda | Šta radi | Složenost |
|---|---|---|
| `put(k, v)` | upiše vrednost; ako ključ postoji, **pregazi** staru | O(1) |
| `get(k)` | vrati vrednost, ili `null` ako ključa nema | O(1) |
| `getOrDefault(k, d)` | vrati vrednost, ili `d` ako ključa nema | O(1) |
| `containsKey(k)` | da li ključ postoji | O(1) |
| `containsValue(v)` | da li vrednost postoji | **O(n)** |
| `remove(k)` | obriše par | O(1) |
| `size()` / `isEmpty()` | broj parova / da li je prazna | O(1) |
| `putIfAbsent(k, v)` | upiše samo ako ključa **nema** | O(1) |
| `merge(k, v, f)` | ako ključa nema upiše `v`, inače spoji staru i novu vrednost | O(1) |
| `compute(k, f)` | izračuna novu vrednost iz stare (stara može biti `null`) | O(1) |
| `computeIfAbsent(k, f)` | ako ključa nema, izračuna i upiše; vrati vrednost | O(1) |
| `keySet()` / `values()` / `entrySet()` | ključevi / vrednosti / parovi, za petlju | O(n) za prolazak |

*O(1) je prosečan slučaj. Najgori slučaj je O(n), ili O(log n) od Jave 8 (vidi [Kako radi iznutra](#kako-radi-iznutra)).*

### Osnovne metode

#### `map.put(k, v)` / `map.get(k)`
Upisuje i čita vrednost za ključ.
```java
map.put("jabuka", 5);
map.get("jabuka");      // 5
map.get("kruška");      // null, ključa nema
map.put("jabuka", 7);   // isti ključ: stara vrednost se pregazi
map.get("jabuka");      // 7
```
- `put` vraća **staru** vrednost (ili `null` ako je nije bilo).
- Ključ može biti `null`: `map.put(null, 0)` radi u HashMap-i.
- Pazi: `get` vraća `null` i kad ključa nema, i kad je vrednost baš `null`. Ako ti je važna razlika, koristi `containsKey`.

#### `map.containsKey(k)` / `map.containsValue(v)`
Da li ključ, odnosno vrednost, postoji.
```java
map.containsKey("jabuka");   // true, O(1)
map.containsValue(7);        // true, ali O(n): prolazi kroz sve vrednosti
```
- `containsKey` koristim kad pitam: „da li sam ovo već videla?“
- `containsValue` izbegavam u petlji, jer je spor.

#### `map.remove(k)`
Briše par sa tim ključem. Posle toga `get(k)` vraća `null`.
```java
map.remove("jabuka");
```

#### `map.size()` / `map.isEmpty()`
Broj parova u mapi i da li je prazna.

### Prolazak kroz mapu

```java
// 1. Ključ i vrednost zajedno (najčešće ovo treba)
for (Map.Entry<String, Integer> e : map.entrySet()) {
    String k = e.getKey();
    int v = e.getValue();
}

// 2. Samo ključevi
for (String k : map.keySet()) { ... }

// 3. Samo vrednosti
for (int v : map.values()) { ... }

// 4. Java 8, sa lambdom
map.forEach((k, v) -> System.out.println(k + " = " + v));
```
- Redosled **nije** garantovan. Ako treba redosled ubacivanja, koristi `LinkedHashMap`; ako treba sortiran po ključu, `TreeMap`.
- Ne briši iz mape dok prolaziš kroz nju for-each petljom, jer dobiješ `ConcurrentModificationException`.

### Java 8 metode

#### `map.getOrDefault(k, default)`
Vraća vrednost, a ako ključa nema, vraća `default` umesto `null`.
```java
map.put(c, map.getOrDefault(c, 0) + 1);   // brojanje
```

#### `map.putIfAbsent(k, v)`
Upiše vrednost **samo** ako ključ još ne postoji. Postojeću vrednost ne dira.
```java
map.putIfAbsent("jabuka", 1);   // ako "jabuka" već postoji, ništa se ne menja
```

#### `map.merge(k, v, funkcija)`
Ako ključa nema, upiše `v`. Ako ga ima, upiše `funkcija(staraVrednost, v)`.
```java
map.merge(c, 1, Integer::sum);   // isto što i brojanje sa getOrDefault, kraće
```

#### `map.compute(k, (key, stara) -> nova)`
Izračuna novu vrednost iz stare. `stara` je `null` ako ključa nema.
```java
map.compute("jabuka", (k, v) -> v == null ? 1 : v + 1);
```
- Razlika od `merge`: `compute` nema podrazumevanu vrednost, pa sama moraš da obradiš `null`.

#### `map.computeIfAbsent(k, key -> nova)`
Ako ključa **nema**, pozove funkciju, upiše rezultat u mapu i vrati ga. Ako ključ **postoji**, samo vrati postojeću vrednost, a funkciju **ne poziva**.

> Izvor: [Baeldung – The Map.computeIfAbsent() Method](https://www.baeldung.com/java-map-computeifabsent), delovi 2.1 i 2.2

```java
Map<String, Integer> m = new HashMap<>();
m.computeIfAbsent("John", s -> s.length());   // nema ključa → računa 4, upiše "John"→4, vrati 4

m.put("Ana", 5);
m.computeIfAbsent("Ana", s -> s.length());    // ključ postoji → vrati 5, lambda se NE poziva
```

| Ključ u mapi? | Šta radi |
|---|---|
| da | vrati postojeću vrednost |
| ne | pozove funkciju, upiše rezultat, vrati ga |

- Ako funkcija vrati `null`, ništa se ne upisuje.

**Grupisanje: lista kao vrednost.** Najčešća upotreba je mapa čija je vrednost **lista**, a lista se pravi tek kad ključ prvi put naiđe:

```java
// grupiši reči po dužini
Map<Integer, List<String>> poDuzini = new HashMap<>();
for (String w : List.of("ab", "cd", "abc")) {
    poDuzini.computeIfAbsent(w.length(), k -> new ArrayList<>()).add(w);
}
// {2=[ab, cd], 3=[abc]}
```

- `computeIfAbsent(...)` vraća listu (novu ili postojeću), pa odmah može `.add(w)`.
- Bez toga bi moralo ovako, u tri koraka:
  ```java
  if (!poDuzini.containsKey(len)) {
      poDuzini.put(len, new ArrayList<>());
  }
  poDuzini.get(len).add(w);
  ```
- Uz `List` i `ArrayList` treba `import java.util.List;` i `import java.util.ArrayList;`.
- Sve grupe na kraju: `new ArrayList<>(poDuzini.values())`.

### Lambda izrazi

Kratak zapis male funkcije bez imena: **ulaz `->` šta vraća**.

```java
s -> s.length()          // jedan parametar: "za dato s, vrati s.length()"
(k, v) -> k + "=" + v    // dva parametra idu u zagradu
k -> new ArrayList<>()   // parametar se ne mora koristiti
```

Isto kao obična metoda:
```java
int duzina(String s) { return s.length(); }
```
- Ime parametra biram sama (`s`, `kljuc`, `k`…). Tip Java sama zaključi.
- **Zamka:** parametar lambde ne sme da se zove isto kao promenljiva koja već postoji. U `topKFrequent(int[] nums, int k)` linija `computeIfAbsent(c, k -> new ArrayList<>())` ne radi (*variable k is already defined*). Daj drugo ime: `x -> new ArrayList<>()`.
- Koristi se u `forEach`, `computeIfAbsent`, `compute`, `merge`, a kasnije i u Streams API.

### Kako radi iznutra

Pitanje koje se često postavlja na intervjuu: *„Kako radi HashMap?“*

**`put(k, v)`:**
1. Izračuna `k.hashCode()`.
2. Od hash-a izračuna **kantu** (bucket): mesto u unutrašnjem nizu.
3. U toj kanti pomoću `equals()` proveri da li ključ već postoji. Ako postoji, pregazi vrednost; ako ne, doda novi par.

**`get(k)`:** isto, izračuna kantu, pa u njoj traži ključ pomoću `equals()`.

**Ključni pojmovi:**

| Pojam | Objašnjenje |
|---|---|
| **Kolizija** | Dva različita ključa padnu u istu kantu. Čuvaju se zajedno, u listi. |
| **Treeify** (Java 8) | Kad kanta ima **8+** elemenata, lista se pretvara u stablo, pa je pretraga O(log n) umesto O(n). Kad padne na 6, vraća se u listu. |
| **Kapacitet** | Broj kanti. Podrazumevano **16**. |
| **Load factor** | Podrazumevano **0.75**. Kad je mapa popunjena 75%, broj kanti se **duplira** (resize), a svi elementi se raspoređuju ponovo. |

**Ugovor `equals` / `hashCode`:**
- Ako je `a.equals(b)`, **mora** biti `a.hashCode() == b.hashCode()`.
- Obrnuto ne mora: dva različita objekta smeju imati isti hash (to je kolizija).
- Ako u svojoj klasi override-uješ `equals`, **moraš** i `hashCode`. Inače mapa neće naći ključ, jer traži u pogrešnoj kanti.

**Ključevi treba da budu nepromenljivi (immutable).** Ako promeniš objekat posle `put`, njegov hash se promeni i `get` više ne može da ga nađe. Zato su `String` i `Integer` dobri ključevi.

---

## HashSet

Čuva **samo ključeve**, bez vrednosti. Nema duplikata, redosled nije garantovan. Iznutra je to HashMap u kome se vrednosti ignorišu, pa važi sve iz [Kako radi iznutra](#kako-radi-iznutra).

```java
import java.util.HashSet;
import java.util.Set;

Set<Integer> seen = new HashSet<>();
```

| Metoda | Šta radi | Složenost |
|---|---|---|
| `add(x)` | doda `x`; vraća `false` ako je `x` već bio u setu | O(1) |
| `contains(x)` | da li je `x` u setu | O(1) |
| `remove(x)` | obriše `x` | O(1) |
| `size()` / `isEmpty()` | broj elemenata / da li je prazan | O(1) |

```java
seen.add(5);
seen.add(3);
seen.add(5);          // vraća false, 5 već postoji
seen.size();          // 2
seen.contains(3);     // true
for (int x : seen) { ... }   // prolazak, redosled nije garantovan
```

### HashMap ili HashSet?

| Pitanje | Struktura |
|---|---|
| Treba mi samo „da li sam ovo već videla?“ | **HashSet** |
| Treba mi i podatak uz ključ (pozicija, broj ponavljanja…) | **HashMap** |

| HashMap | HashSet |
|---|---|
| `Map<K, V>` | `Set<E>` |
| `containsKey(k)` | `contains(x)` |
| `put(k, v)` | `add(x)` |

- Greška koju sam pravila: `Map<Integer>` ne postoji, mapa uvek ima dva tipa. Uz `HashSet` ide `Set`.

---

## Trikovi iz zadataka

| Zadatak | Trik |
|---------|------|
| Valid Anagram | Prvo proveri dužine. Zatim ili sortiraj obe reči i uporedi (O(n log n)), ili prebroj slova u `int[26]` (O(n)). |
| Two Sum | Ne traži par, nego broj koji fali: `fali = target - nums[i]`. Pamti viđene brojeve u HashMap (broj → pozicija). **Prvo proveri** `containsKey(fali)`, **pa tek onda** `put`, da broj ne nađe sam sebe. O(n) vreme, O(n) memorija. |
| Contains Duplicate | Pamti viđene brojeve u **HashSet**. Ako je broj već u setu, `return true`. Pozicija ne treba, pa nije HashMap. O(n) vreme, O(n) memorija. Alternative: dve petlje O(n²), ili sortiraj pa uporedi susede O(n log n). |
| Group Anagrams | Anagrami posle sortiranja slova izgledaju isto: `"eat"`, `"tea"` → `"aet"`. To je **ključ grupe**. Mapa `Map<String, List<String>>`: za svaku reč `toCharArray` → `Arrays.sort` → `new String` = ključ, pa `computeIfAbsent(key, k -> new ArrayList<>()).add(reč)`. Na kraju `new ArrayList<>(groups.values())`. O(n · k log k), n = broj reči, k = dužina reči. |
| Top K Frequent | 1) prebroj: `Map<broj, koliko puta>` sa `getOrDefault`. 2) „prevrni“ u kutije: `Map<koliko puta, List<broj>>` sa `computeIfAbsent`. 3) broj ponavljanja ne može biti veći od n, pa probaj ključeve od `n` naniže (`containsKey`) i skupljaj brojeve dok ih ne bude k. O(n). Jednostavnije: sortiraj po broju ponavljanja, O(n log n). |
| | |
