package it.polito.tdp.emergency.model;

public class Assistente {

public enum StatoAssistente { DIPONIBILE, NON_DISPONIBILE };
	
	private StatoAssistente stato;
	private int id;
	
	public Assistente(int id) {
		this.id = id;
		stato = Assistente.StatoAssistente.NON_DISPONIBILE;
	}

	public StatoAssistente getStato() {
		return stato;
	}

	public void setStato(StatoAssistente stato) {
		this.stato = stato;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Dottore [stato=" + stato + ", id=" + id + "]";
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
		Assistente other = (Assistente) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
