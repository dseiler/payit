Aktuelle bkstamm version: 24.7.2002
Um eine neue bkstamm version einzulesen muss wie folgt vorgegangen werden:

1) verifizieren anhand des bkstamm beschreibungsdokumentes ob die struktur
   des bkstamm nicht geändert hat.

2) falls die file struktur geändert hat muss der java code angepasst werden (PayIT.java)

3) falls die struktur nicht geändert hat (wahrscheinlich ändert sie nicht) müssen im bkstam.txt
   file alle hochkommas ' durch zweifache hochkommas (zwei mal ') '' ersetzt werden, um eine
   richtige verarbeitung durch sql zu erreichen.