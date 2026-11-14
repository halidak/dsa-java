# Java beleške

Metode i trikovi koje sam naučila kroz zadatke. Za svaku stavku: šta radi, primer, kad se koristi i koliko košta.

> Šabloni (sliding window, BFS, DP…) su u [CHEATSHEET.md](CHEATSHEET.md). Ovde idu sitne Java stvari.

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

### `s.length()`
Broj karaktera u stringu.
```java
"rat".length()   // 3
```
- Kod stringa je **metoda** sa zagradama: `length()`. Kod niza je **polje** bez zagrada: `niz.length`.
- O(1)

### `s.charAt(i)`
Karakter na poziciji `i` (kreće od 0).
```java
"rat".charAt(0)  // 'r'
```
- Koristi se za prolazak kroz string slovo po slovo: `for (int i = 0; i < s.length(); i++) { char c = s.charAt(i); }`
- O(1)

### `s.toCharArray()`
Pretvara string u niz karaktera `char[]`.
```java
char[] a = "rat".toCharArray();   // ['r', 'a', 't']
```
- Kad hoću da sortiram slova ili da menjam karaktere (string se ne može menjati, niz može).
- Može i petlja bez indeksa: `for (char c : s.toCharArray()) { ... }`
- O(n) vremena i O(n) memorije (pravi kopiju)

### `c - 'a'`
Pretvara malo slovo u broj od 0 do 25: `'a'` → 0, `'b'` → 1, … `'z'` → 25.
```java
int index = 'c' - 'a';   // 2
```
- Svaki `char` je u stvari broj (ASCII kod), pa oduzimanje radi.
- Koristi se uz `int[] count = new int[26]` za brojanje slova, umesto HashMap-e. To je brže.
- Radi samo ako zadatak kaže „lowercase English letters“.

---

## Nizovi (`java.util.Arrays`)

Na vrhu fajla: `import java.util.Arrays;`

### `Arrays.sort(niz)`
Sortira niz **u mestu**: menja originalni niz i ništa ne vraća.
```java
char[] a = {'t', 'a', 'r'};
Arrays.sort(a);                 // a je sad ['a', 'r', 't']
```
- Greška koju ne treba praviti: `a = Arrays.sort(a)` ne radi, jer metoda vraća `void`.
- O(n log n)

### `Arrays.equals(a, b)`
`true` ako su dva niza iste dužine i imaju iste elemente istim redom.
```java
Arrays.equals(new int[]{1, 2}, new int[]{1, 2})   // true
```
- Ne koristi `a == b` za nizove! To proverava da li je to **isti objekat** u memoriji, ne da li je sadržaj isti.
- O(n)

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

---

## HashMap (`java.util.HashMap`)

Čuva parove **ključ → vrednost**. Dodavanje i pretraga su O(1) u proseku.
```java
import java.util.HashMap;
import java.util.Map;

Map<Integer, Integer> map = new HashMap<>();
```

### `map.put(k, v)` / `map.get(k)`
Upisuje i čita vrednost za ključ. `get` vraća `null` ako ključ ne postoji.

### `map.containsKey(k)`
Da li ključ postoji. Koristi se kad pitam: „da li sam ovo već video?“

### `new int[] {a, b}`
Pravi novi niz sa tačno tim vrednostima, i odmah može da se vrati.
```java
return new int[] {seen.get(fali), i};   // npr. [0, 1]
```
- Ne postoji `niz[a, b]`. Uglaste zagrade posle imena čitaju **jedan** element: `niz[0]`.

### `map.getOrDefault(k, default)`
Vraća vrednost, a ako ključa nema, vraća `default` (umesto `null`).
```java
map.put(c, map.getOrDefault(c, 0) + 1);   // brojanje
```

---

## Trikovi iz zadataka

| Zadatak | Trik |
|---------|------|
| Valid Anagram | Prvo proveri dužine. Zatim ili sortiraj obe reči i uporedi (O(n log n)), ili prebroj slova u `int[26]` (O(n)). |
| Two Sum | Ne traži par, nego broj koji fali: `fali = target - nums[i]`. Pamti viđene brojeve u HashMap (broj → pozicija). **Prvo proveri** `containsKey(fali)`, **pa tek onda** `put`, da broj ne nađe sam sebe. O(n) vreme, O(n) memorija. |
| | |
