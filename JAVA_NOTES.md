# Java beleške

Metode i trikovi koje sam naučila kroz zadatke. Za svaku stavku: šta radi, primer, kad se koristi i koliko košta.

> Šabloni (sliding window, BFS, DP…) su u [CHEATSHEET.md](CHEATSHEET.md). Ovde idu sitne Java stvari.

**Brzo traženje:** `Ctrl+F` + ime metode (npr. `getOrDefault`), ili klikni na deo ispod.

## Sadržaj

1. [Kako rešavam zadatak](#kako-rešavam-zadatak)
2. [Stringovi](#stringovi): `length`, `charAt`, `toCharArray`, `c - 'a'`
3. [Nizovi](#nizovi): `Arrays.sort`, `Arrays.equals`, `Arrays.toString`, for-each, `new int[...]`
4. [HashMap](#hashmap): metode, prolazak kroz mapu, Java 8 metode, kako radi iznutra
5. [HashSet](#hashset): `add`, `contains`, `remove`, HashMap vs HashSet
6. [Trikovi iz zadataka](#trikovi-iz-zadataka)

---

## Kako rešavam zadatak

1. **Pročitam i prepričam** šta se traži, svojim rečima. Šta je ulaz, šta vraćam?
2. **Prođem primere ručno**, na papiru.
3. **Pitam se kako bih to uradila bez računara.** To je obično brute force.
4. **Napišem brute force** i kažem njegovu složenost.
5. **Optimizujem:** da li može brže? Da li mi pomaže HashMap, sortiranje ili dva pokazivača?
6. **Pokrenem testove**, pa razmislim o ivičnim slučajevima (prazno, jedan element, duplikati, negativni brojevi).
7. **Popunim header** (Idea, Complexity, confidence), pa commit i push.
8. **Ovde upišem** svaku novu metodu ili trik.

---

## Stringovi

| Metoda | Šta radi | Složenost |
|---|---|---|
| `s.length()` | broj karaktera | O(1) |
| `s.charAt(i)` | karakter na poziciji `i` | O(1) |
| `s.toCharArray()` | string → `char[]` | O(n) |
| `c - 'a'` | slovo → broj 0–25 | O(1) |

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

## HashMap

Čuva parove **ključ → vrednost**. Ključevi su jedinstveni, redosled nije garantovan.

```java
import java.util.HashMap;
import java.util.Map;

Map<String, Integer> map = new HashMap<>();
```

> Izvor: [Baeldung – A Guide to Java HashMap](https://www.baeldung.com/java-hashmap)

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
| | |
