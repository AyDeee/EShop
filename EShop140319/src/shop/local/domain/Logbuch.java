package shop.local.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import shop.local.persistance.PersistenceManager;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.LogbuchEintrag;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.Person;

public class Logbuch {

	public static final String LOGSAVE = "Logbuch.save";// definieren, wie die Speicherdatei vom Logbuch heißt
	static final int HISTORIENLAENGE = 30; //finale wärend das Programm läuft, kann man nicht mehr die Variable verändern 
	
	private Vector<LogbuchEintrag> eintraege;
	private PersistenceManager pm;
	private LogbuchEintrag artikelImLogbuch;

	public Logbuch(PersistenceManager pm) {
		this.pm = pm;
		this.eintraege = new Vector<LogbuchEintrag>();
		this.artikelImLogbuch = artikelImLogbuch;

		try {
			liesDaten(LOGSAVE);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(" Logbuch.save fehlt ");
		}

	}

	public void Save() {
		try {
			schreibeDaten(LOGSAVE);
		} catch (IOException e) {
			System.out.println(" Logbuch.save fehlt ");
		}
	}

	public void NeuerEintrag(boolean einlagern, Person person, Artikel artikel, int anzahl) {
		int datum = EShop.getDayOfYear();
		LogbuchEintrag eintrag = new LogbuchEintrag(einlagern, datum, artikel, anzahl, person);
		eintraege.add(eintrag);
	}

	public LogbuchEintrag sucheEindeutigenArtikel(int nummer) {

		for (LogbuchEintrag eintrag : eintraege) {
			if (eintrag.getArtikel().getNummer() == nummer) {
				return eintrag;
			}
		}

		return null;
	}

	public int[] getBestandsHistorie(int nummer) {
		if (nummer < 0) {
			return new int[0];
		}
		List<Integer> result = new ArrayList<Integer>();

		int datum = EShop.getDayOfYear();
		StringBuilder builder = new StringBuilder(); // builder Objekt wird erstellt
		builder.append("----Log----" + System.lineSeparator()); // System.lineSeparator() Zeichen

		// for (int i = 0; i < eintraege.size(); i++) {
		// LogbuchEintrag eintrag = eintraege.get(i); //Es wird eine Varibale "eintrag"
		// erstellt und ist ein Typ von derKlasse LogbuchEintrag
		// }
		int letzterEintrag = datum - HISTORIENLAENGE;
		int letzterWert = 0;
		
		//ersten Wert für die arrayfüllung suchen
		for (LogbuchEintrag ersterEintrag : eintraege) {
			if (ersterEintrag.getArtikel().getNummer() != nummer) {
				continue; // mit continue springt man zum Ende der Schleife
			}
			if (datum - HISTORIENLAENGE < ersterEintrag.getDatum()) {
				break;
			}
			letzterWert = ersterEintrag.getAnzahl();
		}
		
		for (LogbuchEintrag eintrag : eintraege) { // Die beiden oberen Zeilen machen genau das Gleiche wie diese eine
													// Zeile

			if (eintrag.getArtikel().getNummer() != nummer) {
				continue; // mit continue springt man zum Ende der Schleife
			}

			if (datum - HISTORIENLAENGE > eintrag.getDatum()) {
				continue;
			}
			
			int differenz = eintrag.getDatum()- letzterEintrag;
			while(differenz > 0) {
				result.add(letzterWert);
				differenz--;
			}
			
			result.add(eintrag.getAnzahl());
			letzterEintrag = eintrag.getDatum();
			letzterWert = eintrag.getAnzahl();
			
		}

		return convertIntegers(result);
	}

	public static int[] convertIntegers(List<Integer> integers) {
		int[] ret = new int[integers.size()];
		Iterator<Integer> iterator = integers.iterator();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = iterator.next().intValue();
		}
		return ret;
	}

	public String Print() {
		return Print(-1);
	}

	public String Print(int nummer)// Klasse um String zusammen zu bauen
	{
		int datum = EShop.getDayOfYear();
		StringBuilder builder = new StringBuilder(); // builder Objekt wird erstellt
		builder.append("----Log----" + System.lineSeparator()); // System.lineSeparator() Zeichen

		// for (int i = 0; i < eintraege.size(); i++) {
		// LogbuchEintrag eintrag = eintraege.get(i); //Es wird eine Varibale "eintrag"
		// erstellt und ist ein Typ von derKlasse LogbuchEintrag
		// }

		for (LogbuchEintrag eintrag : eintraege) { // Die beiden oberen Zeilen machen genau das Gleiche wie diese eine
													// Zeile

			if (nummer != -1) {
				if (eintrag.getArtikel().getNummer() != nummer) {
					continue; // mit continue springt man zum Ende der Schleife
				}

				if (datum - HISTORIENLAENGE > eintrag.getDatum()) {
					continue;
				}
			}

			builder.append(eintrag.getDatum() + " ");
			if (eintrag.isEinlagern() == true) // isEingelagert ist der Getter von Einlagern
			{
				builder.append("Einlagern: ");
			} else {
				builder.append("Auslagern: ");
			}

			builder.append(eintrag.getAnzahl() + " ");
			builder.append(eintrag.getArtikel().getBezeichnung() + " "); // getArtikel() --> springt in das Artikel
																			// Objekt getTitel()--> gibt den Namen aus

			if (eintrag.getPerson() instanceof Mitarbeiter) // überprüfen ob das Objekt Person von der Klasse
															// mitarbeiter ist (Typ Mitarbeiter)
			{
				builder.append("Mitarbeiter: ");
			} else if (eintrag.getPerson() instanceof Kunde) {
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
