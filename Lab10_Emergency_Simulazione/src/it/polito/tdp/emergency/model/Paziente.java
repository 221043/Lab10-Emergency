package it.polito.tdp.emergency.model;

public class Paziente implements Comparable<Paziente>{

	public enum StatoPaziente { ROSSO, GIALLO, VERDE, BIANCO, IN_CURA, SALVO, NERO };
	
	private int id;
	private StatoPaziente stato;
	private Dottore dottore;
	private Assistente assistente;
	
	public Paziente(int id, StatoPaziente stato) {
		this.id = id;
		this.stato = stato;
	}

	public Assistente getAssistente() {
		return assistente;
	}

	public void setAssistente(Assistente assistente) {
		this.assistente = assistente;
	}

	public Dottore getDottore() {
		return dottore;
	}

	public void setDottore(Dottore dottore) {
		this.dottore = dottore;
	}

	public StatoPaziente getStato() {
		return stato;
	}

	public void setStato(StatoPaziente stato) {
		this.stato = stato;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Paziente [id=" + id + ", stato=" + stato + "]";
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paziente other = (Paziente) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(Paziente p) {
		return Integer.compare(this.getStato().ordinal(), p.getStato().ordinal());
	}
	
}
