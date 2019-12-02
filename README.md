# P175B014 Duomenų struktūros projektas Unrolled Linked List
 
Šita repositorija turi P175B014 Duomenų struktūros 4th semestro užduoti ,tiksliau modulio kursinis projektas kurio pagrindinis tikslas įgyvendinti pasirinkta duomenu struktūra. Konkreti duomenų struktūra pasirinkta įgyvendinti Unrolled Linked List.

# Darbo tikslai:

1. Išmokti kurti naujas bendro naudojimo duomenų struktūrų klases pagal duotą (pasirinktą) ADT aprašą;
2. Įtvirtinti projektavimo, programavimo, testavimo ir tyrimo procesų žinias;
3. Išmokti kurti programinės įrangos dokumentaciją;

# Atsiskaitymas:

1. Pateikiamas visas programinis projektas ir ataskaitos elektroninė versija;
2. Pademonstruojamas sukurto projekto veikimas.
3. Atsakoma į klausimus apie programinės įrangos struktūrą, metodus, sprendimo pasirinkimo motyvus.

# Būtinos projekto dalys:

- pasirinktos duomenų struktūros interfeiso aprašas;
- interfeisą realizuojanti klasė;
- tos klasės testavimo arba demonstracinė klasė;
- efektyvumo tyrimas (greitaveika / atminties sąnaudos);
- demonstracinis prasmingo taikymo varijantas.

# Kas yra Unrolled Linked List ?

Unrolled Linked List iš esmės veikia labai panašai į paprasta Linked List, tačiau List node vietoj vieno elemento turi turi masyva, taip sujungumia Linked List ir Array privalumai. Pati ideja, kad visa node galima ideti i cache ir taip greiciau vyksta jieskojimas.
<p align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/1/16/Unrolled_linked_lists_%281-8%29.PNG" width="350" title="Unrolled linked list example">
</p>

# Balansuojantis sarašas

Unrolled Linked List balansuojasi add ir remove metu. Toliau pavizdys pridejimo metu veikimo.
```
{}
{[1]}
{[1, 2]}
{[1, 2, 3]}
{[1, 2, 3, 4]}
{[1, 2], [3, 4, 5]}
{[1, 2], [3, 4, 5, 6]}
{[1, 2], [3, 4], [5, 6, 7]}
{[1, 2], [3, 4], [5, 6, 7, 8]}
```

toliau pavizdys jeigu pašalintume 3 o poto 4.

```
{[1, 2], [4, 5, 6], [7, 8]}
{[1, 2], [5, 6], [7, 8]}
```


# Pros

- Linked List veikia O(n) priejimu. Turint nodes masyvus vietoj vienatiniu objekto sumažina procesu kaina.
- Taip pat sumažėją atminties naudojamas kiekis palyginus su tradiciniu Linked List.

