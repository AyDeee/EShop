package shop.local.persistance;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.LogbuchEintrag;
import shop.local.valueobjects.Mitarbeiter;

public class PersistenceManager {

	private ObjectInputStream reader = null;
	private ObjectOutputStream writer = null;
	
	public void openForReading(String datei) throws IOException {
		reader = new ObjectInputStream(new FileInputStream(datei));
	}

	public void openForWriting(String datei) throws IOException {
		writer = new ObjectOutputStream(new FileOutputStream(datei));
	}

	public boolean close() throws IOException {
		if (writer != null)
			writer.close();
		
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return false;
			}
		}

		return true;
	}
	
public Artikel ladeArtikel() throws IOException, ClassNotFoundException {
		
		Artikel artikel = (Artikel) reader.readObject();

			return artikel;


	}
	
	
	public LogbuchEintrag ladeLogbuchEintrag() throws IOException, ClassNotFoundException {
		
		LogbuchEintrag log = (LogbuchEintrag) reader.readObject();

			return log;


	}

	public Mitarbeiter ladeMitarbeiter() throws IOException, ClassNotFoundException {
		
		Mitarbeiter mitarbeiter = (Mitarbeiter) reader.readObject();

			return mitarbeiter;


	}
	
	public Kunde ladeKunde() throws IOException, ClassNotFoundException {
		
		Kunde kunde = (Kunde) reader.readObject();

			return kunde;


	}
	/**
	 * Methode zum Schreiben der Daten in eine externe Datenquelle.
	 * 
	 */
	public boolean speichereArtikel(Artikel a) throws IOException {
		// daten schreiben
		writer.writeObject(a);

		return true;
	}
	
	
	public boolean speichereLogbuchEintrag(LogbuchEintrag l) throws IOException {
		// daten schreiben
		writer.writeObject(l);

		return true;
	}

	public boolean speichereMitarbeiter(Mitarbeiter m) throws IOException {
		// daten schreiben
		writer.writeObject(m);

		return true;
	}

	public boolean speichereKunde(Kunde k) throws IOException {
		// daten schreiben
		writer.writeObject(k);

		return true;
	}
}
