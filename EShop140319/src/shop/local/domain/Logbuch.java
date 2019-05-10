package shop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import shop.local.persistance.PersistenceManager;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.LogbuchEintrag;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.Person;

public class Logbuch {

	public static final String LOGSAVE = "Logbuch.save";//definieren, wie die Speicherdatei vom Logbuch heißt
	
	private Vector<LogbuchEintrag> eintraege;
	private PersistenceManager pm;
	
	public Logbuch(PersistenceManager pm) {
		this.pm = pm;
		this.eintraege =new Vector<LogbuchEintrag>();
		
		try {
			liesDaten(LOGSAVE);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void Save() {
		try {
			schreibeDaten(LOGSAVE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			builder.append(eintrag.getArtikel().getBezeichnung() + " "); //getArtikel() --> springt in das Artikel Objekt getTitel()--> gibt den Namen aus 
			
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
	
	public void schreibeDaten(String datei) throws IOException {
		// PersistenzManager f�r Schreibvorg�nge �ffnen
		pm.openForWriting(datei);

		Iterator<LogbuchEintrag> it = eintraege.iterator();
		while (it.hasNext()) {
			LogbuchEintrag l = it.next();
			pm.speichereLogbuchEintrag(l);
		}
		// Persistenz-Schnittstelle wieder schlie�en
		pm.close();
	}

	
	public void liesDaten(String datei) throws IOException, ClassNotFoundException {
		// PersistenzManager f�r Lesevorg�nge �ffnen
		try {
			pm.openForReading(datei);

			LogbuchEintrag log;
			do {
				// Artikel-Objekt einlesen
				log = pm.ladeLogbuchEintrag();
				if (log != null) {
					// Artikel in Liste einf�gen
					eintraege.add(log);
				}
			} while (log != null);
		} catch (IOException e) {
			// TODO: exception
		}
		// Persistenz-Schnittstelle wieder schlie�en
		pm.close();
	}
	
}
