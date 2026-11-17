# Java teorija

Teorija za intervju (Java core, kolekcije, kasnije concurrency, Spring, JPA…), **grupisano po temi**. Na početku svake teme piše iz kojih izvora je.

> Metode i trikovi iz zadataka su u [JAVA_NOTES.md](JAVA_NOTES.md), šabloni u [CHEATSHEET.md](CHEATSHEET.md).

## Sadržaj

1. [Stringovi](#1-stringovi): [nepromenljivost](#string-je-nepromenljiv-immutable), [String pool](#string-pool), [zašto StringBuilder](#zašto-stringbuilder), [StringBuilder vs StringBuffer](#stringbuilder-vs-stringbuffer)
2. [Kako HashMap radi iznutra](#2-kako-hashmap-radi-iznutra): kante, kolizije, treeify, load factor
3. [equals i hashCode](#3-equals-i-hashcode): `==` vs `equals`, ugovori, zašto oba, `Objects.hash`, nasleđivanje vs kompozicija, kada override

---

## 1. Stringovi

> Izvori: [Baeldung – Why String Is Immutable](https://www.baeldung.com/java-string-immutable), [Baeldung – Java String Pool](https://www.baeldung.com/java-string-pool), [Baeldung – StringBuilder vs StringBuffer](https://www.baeldung.com/java-string-builder-string-buffer). Metode (`length`, `substring`, `append`…): [JAVA_NOTES → Stringovi](JAVA_NOTES.md#stringovi).

### String je nepromenljiv (immutable)

**Šta znači:** kad se String jednom napravi, njegov sadržaj se **više nikad ne menja**. Sve metode koje „menjaju“ string (`+`, `substring`, `toUpperCase`, `replace`…) u stvari prave **novi** String, a stari ostaje isti.

```java
String s = "abc";
s.toUpperCase();          // napravi "ABC", ali ga niko ne sačuva
System.out.println(s);    // "abc"  ← s se nije promenio
s = s.toUpperCase();      // sad s pokazuje na NOVI objekat "ABC"
```

- Promenljiva `s` može da pokaže na drugi String, ali **objekat** "abc" se ne menja.

**Zašto su ga napravili nepromenljivim (4 razloga, dobro pitanje za intervju):**

| Razlog | Objašnjenje |
|---|---|
| **String pool** | Pošto se ne menja, JVM sme da čuva **jednu** kopiju istog teksta i da je deli svima. Da može da se menja, promena kod jednog korisnika bi promenila i kod drugih. Štedi memoriju. |
| **Sigurnost** | Stringovi nose lozinke, korisnička imena, URL-ove za bazu. Primer: metoda proveri da je `userName` ispravan, pa ga ubaci u SQL upit. Da je String promenljiv, neko ko ima istu referencu mogao bi da ga promeni **između** provere i upita (SQL injection). |
| **Thread-safe** | Više niti može da čita isti String bez `synchronized`, jer niko ne može da ga promeni. „Promena“ samo napravi novi objekat. |
| **Keširanje `hashCode`** | Hash se izračuna **jednom** (prvi put) i zapamti. Zato je String brz i siguran ključ u HashMap/HashSet: hash je isti pri `put` i pri `get`. Da se String menja, hash bi se promenio i mapa ga ne bi našla. Vidi [equals i hashCode](#3-equals-i-hashcode). |

### String pool

**Šta je:** posebno mesto u memoriji (na heap-u) gde JVM čuva **po jednu kopiju** svakog String **literala** (teksta pod navodnicima u kodu). Proces se zove **interning**.

**Kako radi:**
- Napišem `"Baeldung"` → JVM pogleda u pool. Ako tamo već postoji `"Baeldung"`, vrati **isti** objekat. Ako ne, doda ga u pool.
- Napišem `new String("Baeldung")` → **uvek** pravi novi objekat na heap-u, van pool-a.

```java
String a = "Baeldung";
String b = "Baeldung";
String c = new String("Baeldung");

a == b        // true   ← isti objekat iz pool-a
a == c        // false  ← c je novi objekat
a.equals(c)   // true   ← isti sadržaj
c.intern() == a   // true  ← intern() vrati kopiju iz pool-a
```

**Pouka:** `==` kod stringova poredi **da li je to isti objekat**, a ne tekst. Nekad slučajno radi (literali iz pool-a), nekad ne (`new String`, string pročitan iz fajla ili napravljen u programu). Zato **stringove uvek poredi sa `equals`**.

- Piši `String s = "tekst";`, ne `new String("tekst")`: čitljivije je i koristi pool.
- `s.intern()` ručno ubaci string u pool i vrati tu kopiju (retko se koristi).
- **Gde je pool:** do Jave 7 u PermGen (fiksna veličina, nije se čistio → `OutOfMemoryError`). Od Jave 7 je na **heap-u** i garbage collector ga čisti.
- Pool je iznutra hash tabela; broj kanti se podešava sa `-XX:StringTableSize` (podrazumevano 60013 od Jave 7u40).
- **Java 9 – Compact Strings:** ranije je svaki znak bio 2 bajta (`char[]`, UTF-16). Od Jave 9, ako tekst ima samo latinične znakove, čuva se kao `byte[]` (1 bajt po znaku) → manje memorije.

### Zašto StringBuilder

**Problem:** pošto je String nepromenljiv, `s = s + x` u petlji svaki put pravi **novi** String i u njega **prepisuje ceo dosadašnji tekst**.

```java
// SPORO: O(n²) – 1000 novih stringova, svaki sve duži
String s = "";
for (int i = 0; i < 1000; i++) {
    s = s + "def";
}

// BRZO: O(n) – jedan objekat koji raste
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append("def");
}
String rezultat = sb.toString();
```

**Odgovor na pitanje „zašto je `s + x` u petlji sporo?“** Svaki `+` pravi novi objekat i kopira sve slova od ranije. Prvi put kopira 3 znaka, pa 6, pa 9… ukupno oko n² / 2. StringBuilder je **promenljiv**: ima unutrašnji niz sa rezervom mesta i samo dopisuje na kraj (kad se napuni, poveća se odjednom), pa je ukupno O(n).

Metode StringBuilder-a: [JAVA_NOTES → StringBuilder](JAVA_NOTES.md#stringbuilder).

### StringBuilder vs StringBuffer

| | String | StringBuilder | StringBuffer |
|---|---|---|---|
| Menja se? | ne (immutable) | da | da |
| Thread-safe? | da (jer se ne menja) | **ne** | da (`synchronized` metode) |
| Brzina | spor za spajanje u petlji | **najbrži** | malo sporiji (zbog sinhronizacije) |
| Od kad | uvek | Java 1.5 (zamena za StringBuffer) | Java 1.0 |

- Isti API (`append`, `insert`, `reverse`…), razlika je samo u sinhronizaciji.
- U praksi: **uvek StringBuilder**. StringBuffer samo ako više niti piše u isti objekat (retko).
- Za mali broj operacija razlika je zanemarljiva (Baeldung benchmark: 1.000 append-ova ~91k vs ~86k ops/s).

---

## 2. Kako HashMap radi iznutra

> Izvor: [Baeldung – A Guide to Java HashMap](https://www.baeldung.com/java-hashmap). Metode HashMap-e: [JAVA_NOTES → HashMap](JAVA_NOTES.md#hashmap).

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

**Ugovor `equals` / `hashCode`:** jednaki objekti moraju imati isti hash, i ako override-uješ `equals`, moraš i `hashCode`. Ceo ugovor i primer sa HashMap ključem: [equals i hashCode](#3-equals-i-hashcode).

**Ključevi treba da budu nepromenljivi (immutable).** Ako promeniš objekat posle `put`, njegov hash se promeni i `get` više ne može da ga nađe. Zato su `String` i `Integer` dobri ključevi.

---

## 3. equals i hashCode

> Izvor: [Baeldung – Java equals() and hashCode() Contracts](https://www.baeldung.com/java-equals-hashcode-contracts), delovi 1–7. Kako HashMap koristi ove metode: [Kako HashMap radi iznutra](#2-kako-hashmap-radi-iznutra).

**Ukratko:** `equals` odgovara na pitanje „da li su ova dva objekta ista po sadržaju?“, a `hashCode` na pitanje „u koju kantu HashMap/HashSet da stavi ovaj objekat?“. Ova dva odgovora moraju da se slažu, inače mapa ne nalazi ključ.

### 3.1 Odakle uopšte ove metode?

Svaka klasa u Javi nasleđuje klasu `Object`, a `Object` već ima obe metode:

```java
public boolean equals(Object o)   // podrazumevano: this == o
public int hashCode()             // podrazumevano: broj izveden iz adrese objekta u memoriji
```

Podrazumevano obe gledaju samo **identitet**: da li je to **isti objekat** u memoriji. Sadržaj ih ne zanima.

```java
Money a = new Money(55, "USD");
Money b = new Money(55, "USD");

a == b         // false: dva različita objekta
a.equals(b)    // false: podrazumevani equals radi isto što i ==
```

Za nas su to „isti novac“, ali Java to ne zna. Da bi znala, moramo **sami da napišemo (override)** `equals`, a uz njega i `hashCode`.

**Analogija:** dve novčanice od 50 dinara su **dva različita papira** (`==` je `false`), ali **vrede isto** (`equals` treba da bude `true`).

### 3.2 `==` vs `equals`

| | `==` | `equals` |
|---|---|---|
| Pita | da li je to **isti objekat** (ista adresa) | da li je **isti sadržaj** |
| Za primitive (`int`, `char`…) | poredi vrednost, to je OK | ne postoji (primitivi nemaju metode) |
| Za objekte | skoro nikad nije ono što želiš | ono što obično želiš, **ako je klasa override-ovala `equals`** |

- `String`, `Integer`, `List`… već imaju dobar `equals`. Za **svoje** klase moraš da ga napišeš.
- Stringove uvek poredi sa `equals` (vidi [String pool](#string-pool)).

### 3.3 Kako se piše `equals` (korak po korak)

```java
public class Money {
    int amount;
    String currencyCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                    // 1.
        if (!(o instanceof Money)) return false;       // 2.
        Money other = (Money) o;                       // 3.
        return amount == other.amount                  // 4.
            && Objects.equals(currencyCode, other.currencyCode);
    }
}
```

1. **`this == o`:** ako poredim objekat sa samim sobom, odmah `true`. To je brza prečica.
2. **`o instanceof Money`:** ako `o` nije `Money` (npr. String), nisu jednaki. `instanceof` vraća `false` i za `null`, pa je ovaj korak ujedno i zaštita od `null`.
3. **Cast:** parametar je tipa `Object`, pa ga moram pretvoriti u `Money` da bih mogla da pristupim poljima `amount` i `currencyCode`.
4. **Poređenje polja**, onih koja određuju „istost“:
   - primitivi (`int`, `long`, `boolean`) → `==`
   - objekti (`String`…) → `Objects.equals(a, b)`. Radi i kad je neko od njih `null`; običan `a.equals(b)` bi bacio `NullPointerException` kad je `a == null`.

- `@Override` iznad metode: kompajler proveri da stvarno override-ujem metodu iz `Object`. Česta greška je `equals(Money o)` umesto `equals(Object o)`. To nije override nego nova metoda, pa je HashMap nikad ne poziva.

### 3.4 Ugovor `equals`: 4 pravila

„Ugovor“ su pravila koja **svaki** `equals` mora da poštuje, jer se kolekcije (HashMap, HashSet, `List.contains`…) oslanjaju na njih.

| Pravilo | Formalno | Na običnom jeziku |
|---|---|---|
| **Refleksivnost** | `x.equals(x)` je `true` | objekat je jednak samom sebi |
| **Simetričnost** | `x.equals(y)` ⇔ `y.equals(x)` | ako je A jednako B, onda je i B jednako A. Redosled ne sme da bude bitan. |
| **Tranzitivnost** | `x.equals(y)` i `y.equals(z)` ⇒ `x.equals(z)` | ako A = B i B = C, onda A = C |
| **Konzistentnost** | više poziva → isti rezultat | rezultat se menja **samo** ako se promeni polje koje učestvuje u poređenju; nema nasumičnosti ni zavisnosti od vremena |

I dodatno: `x.equals(null)` je uvek `false` i ne sme da baci izuzetak.

Zašto je to bitno: ako je simetričnost prekršena, `list.contains(a)` i `list.contains(b)` mogu da daju različite odgovore za „iste“ objekte, a set može da primi duplikat. Primer kako se to desi je u delu 8.

### 3.5 Ugovor `hashCode`: 3 pravila

| Pravilo | Na običnom jeziku |
|---|---|
| **1. Interna konzistentnost** | dok se ne promene polja iz `equals`, `hashCode` vraća **isti broj** |
| **2. Jednaki objekti → isti hash** | ako je `a.equals(b)`, onda **mora** `a.hashCode() == b.hashCode()` |
| **3. Kolizije su dozvoljene** | različiti objekti **smeju** imati isti hash |

Najvažnije je pravilo 2. Obrnuto ne mora da važi: isti hash ne znači da su objekti jednaki.

**Analogija:** `hashCode` je **broj police** u magacinu. Dve iste novčanice moraju biti na **istoj polici**, inače ih ne nađeš. Na jednoj polici smeju da stoje i različite stvari (kolizija), i zato se posle police još proverava `equals`.

### 3.6 Zašto UVEK oba: primer `Team` kao ključ u HashMap

```java
class Team {
    String city;
    String department;

    @Override
    public boolean equals(Object o) { ... poredi city i department ... }
    // hashCode NIJE override-ovan!
}

Map<Team, String> leaders = new HashMap<>();
leaders.put(new Team("New York", "development"), "Anne");

leaders.get(new Team("New York", "development"));   // null !!!
```

**Šta se tačno desilo, korak po korak:**

1. `put`: HashMap pozove `hashCode()` prvog `Team` objekta. Pošto nije override-ovan, dobije broj iz **adrese** tog objekta (npr. 1234). Na osnovu njega stavi „Anne“ u, recimo, **kantu 7**.
2. `get`: ovo je **novi** objekat (`new Team(...)`), sa drugom adresom → drugi hash (npr. 9876) → HashMap ode u, recimo, **kantu 3**.
3. Kanta 3 je prazna → vrati `null`. Do `equals` nikad ni ne stigne, iako bi on rekao `true`.

**Kad dodam `hashCode`:**

```java
@Override
public int hashCode() {
    return Objects.hash(city, department);   // ista polja kao u equals!
}
```

Sad oba objekta daju isti hash → ista kanta → tamo `equals` kaže `true` → `get` vrati **„Anne“**. ✓

**Pravilo: override-uj oba ili nijedan.** Isto važi za HashSet: bez `hashCode` set primi dva „ista“ objekta.

### 3.7 Kako se piše `hashCode`

**Najlakše:**

```java
@Override
public int hashCode() {
    return Objects.hash(city, department);
}
```

- Koristi **tačno ista polja** kao `equals`. Ako `equals` gleda `city` i `department`, `hashCode` gleda ta ista dva. Ako bi `hashCode` gledao i neko polje koje `equals` ne gleda, dva jednaka objekta bi mogla da dobiju različit hash (krši pravilo 2).

**Ručno, kako izgleda iznutra (Baeldung primer):**

```java
@Override
public int hashCode() {
    int result = 17;
    result = 31 * result + (city == null ? 0 : city.hashCode());
    result = 31 * result + (department == null ? 0 : department.hashCode());
    return result;
}
```

- Kreće od nekog broja (17), pa za svako polje: pomnoži sa **31** i doda hash polja.
- Zašto 31: neparan prost broj, pa se hash vrednosti lepo rasporede po kantama (manje kolizija). Zato `Team("A", "B")` i `Team("B", "A")` dobiju različit hash: redosled vrednosti utiče na rezultat.
- `null` polje se računa kao 0.

### 3.8 Zamka: nasleđivanje kvari simetriju

Recimo da napravim `WrongVoucher extends Money`, koji ima još i polje `store` (prodavnica), i u svom `equals` proverava `instanceof WrongVoucher`:

```java
Money cash = new Money(42, "USD");
WrongVoucher voucher = new WrongVoucher(42, "USD", "Amazon");

cash.equals(voucher);   // true:  Money.equals pita „je l' voucher instanceof Money?“ → da (podklasa), iznos i valuta isti
voucher.equals(cash);   // false: WrongVoucher.equals pita „je l' cash instanceof WrongVoucher?“ → ne
```

Isti par objekata, a odgovor zavisi od redosleda. Prekršena je **simetričnost**.

**Rešenje: kompozicija umesto nasleđivanja.** `Voucher` ne **jeste** `Money`, nego **ima** `Money` kao polje:

```java
class Voucher {
    private Money value;     // ima Money (kompozicija), ne nasleđuje ga
    private String store;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Voucher)) return false;
        Voucher other = (Voucher) o;
        return Objects.equals(value, other.value)     // koristi Money.equals
            && Objects.equals(store, other.store);
    }
}
```

Sad `Money` i `Voucher` nisu u srodstvu, pa `cash.equals(voucher)` i `voucher.equals(cash)` oba vraćaju `false`. Simetrija je sačuvana.

- Za intervju: „Favor composition over inheritance“ (radije kompozicija nego nasleđivanje) je i princip iz SOLID/Effective Java priče.
- Drugo rešenje: označi klasu (ili `equals`) kao `final`, pa niko ne može da je nasledi i pokvari.

### 3.9 Kada override-ovati

| Vrsta klase | Override? | Zašto | Primer |
|---|---|---|---|
| **Value objekat** (definiše ga samo vrednost) | **da**, oba | dva objekta sa istim vrednostima su „ista stvar“ | `Money(55, "USD")`, `Point(x, y)`, ključ u mapi |
| **Entitet** (ima svoj identitet) | obično ne | dva korisnika sa istim imenom nisu ista osoba | `User`, `Order` (JPA entiteti: posebna tema u nedelji 4) |

- Klasa koja ide kao **ključ u HashMap** ili **element u HashSet** mora imati oba.
- Polja koja učestvuju u `equals`/`hashCode` treba da budu **nepromenljiva**. Ako promenim polje objekta koji je već u mapi, njegov hash se promeni i `get` traži u pogrešnoj kanti (vidi [Kako HashMap radi iznutra](#2-kako-hashmap-radi-iznutra)).
- **`record`** (Java 16+) sam napravi `equals`, `hashCode` i `toString` od svih polja: `record Money(int amount, String currencyCode) {}`. Najlakši način za value objekte.

### 3.10 Alati da ne pišeš ručno

| Alat | Kako |
|---|---|
| IDE (IntelliJ, VS Code) | desni klik → Generate / Source Action → `equals()` and `hashCode()` |
| `java.util.Objects` | `Objects.equals(a, b)` i `Objects.hash(polja...)`, ugrađeno u Javu |
| Lombok | `@EqualsAndHashCode` (ili `@Data` / `@Value`) generiše oba zajedno |
| Apache Commons Lang, Guava | pomoćne klase (`EqualsBuilder`, `HashCodeBuilder`…) |
| **EqualsVerifier** (za testove) | `EqualsVerifier.forClass(Team.class).verify();` sam proveri sva pravila oba ugovora |

- EqualsVerifier je **strožiji** od Java ugovora: traži da metode ne bacaju `NullPointerException`, da klasa ili metode budu `final` i da polja budu nepromenljiva. Pojedina upozorenja se gase sa `.suppress(Warning.NAZIV)`.

### Pitanja za intervju

- *Koja je razlika između `==` i `equals`?* `==` poredi reference (isti objekat), `equals` sadržaj, ako ga je klasa override-ovala.
- *Šta je ugovor `equals`?* Refleksivnost, simetričnost, tranzitivnost, konzistentnost, i `equals(null)` je `false`.
- *Šta ako override-uješ `equals`, a ne `hashCode`?* HashMap/HashSet ne nalaze „jednake“ objekte, jer završe u različitim kantama; set može da primi duplikat.
- *Mogu li dva različita objekta imati isti `hashCode`?* Da, to je kolizija. Ali jednaki objekti **moraju** imati isti hash.
- *Zašto nasleđivanje može da pokvari `equals`?* Podklasa sa dodatnim poljem krši simetričnost. Rešenje je kompozicija ili `final`.
- *Zašto je `String` dobar ključ?* Nepromenljiv je, ima dobar `equals`/`hashCode`, a hash se kešira.
