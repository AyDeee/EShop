package shop.local.valueobjects;

import java.util.Vector;

import shop.local.domain.EShop;

public class Logbuch {

	private Vector<LogbuchEintrag> eintraege;

	public Logbuch() {
		this.eintraege =new Vector<LogbuchEintrag>();
	}

	public void NeuerEintrag(boolean einlagern, Person person, Artikel artikel, int anzahl)
	{
		int datum = EShop.getDayOfYear();
		LogbuchEintrag eintrag = new LogbuchEintrag(einlagern, datum, artikel, anzahl, person);
		eintraege.add(eintrag);
	}
	
	
	public String Print()//Klasse um String zusammen zu bauen
	{
		StringBuilder builder = new StringBuilder(); //builder Objekt wird erstellt
		builder.append("----Log----" + System.lineSeparator()); //System.lineSeparator() Zeichen
		
//		for (int i = 0; i < eintraege.size(); i++) {
//			LogbuchEintrag eintrag = eintraege.get(i); //Es wird eine Varibale "eintrag" erstellt und ist ein Typ von derKlasse LogbuchEintrag 		
//		}
		
		for (LogbuchEintrag eintrag : eintraege) { // Die beiden oberen Zeilen machen genau das Gleiche wie diese eine Zeile
			
			builder.append(eintrag.getDatum() + " ");
			if (eintrag.isEinlagern() == true) //isEingelagert ist der Getter von Einlagern
			{
				builder.append("Einlagern: ");
			}else{
				builder.append("Auslagern: ");
			}
			
			builder.append(eintrag.getAnzahl() + " ");
			builder.append(eintrag.getArtikel().getTitel() + " "); //getArtikel() --> springt in das Artikel Objekt getTitel()--> gibt den Namen aus 
			
			if(eintrag.getPerson() instanceof Mitarbeiter) //überprüfen ob das Objekt Person von der Klasse mitarbeiter ist (Typ Mitarbeiter)
			{
				builder.append("Mitarbeiter: ");
			}else if (eintrag.getPerson() instanceof Kunde)
			{
				builder.append("Kunde: ");
			}

			builder.append(eintrag.getPerson().getName());
			
			builder.append(System.lineSeparator());
		}
		
		builder.append("-----------" + System.lineSeparator());
		return builder.toString(); // toString lässt die einzelnen Zeichenketten zusammen ausgeben 
	}
}
