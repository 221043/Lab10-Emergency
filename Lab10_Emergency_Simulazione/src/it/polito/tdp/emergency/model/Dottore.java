package it.polito.tdp.emergency.model;

public class Dottore {

	public enum StatoDottore { DIPONIBILE, NON_DISPONIBILE };
	
	private StatoDottore stato;
	private int id;
	private String nome;
	private long tempo;
	
	public Dottore(int id) {
		this.id = id;
		stato = Dottore.StatoDottore.NON_DISPONIBILE;
		nome = "";
		tempo = 0;
	}
	
	public long getTempo() {
		return tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public StatoDottore getStato() {
		return stato;
	}

	public void setStato(StatoDottore stato) {
		this.stato = stato;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Dottore [stato=" + stato + ", id=" + id + ", nome=" + nome + "]";
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
		Dottore other = (Dottore) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
