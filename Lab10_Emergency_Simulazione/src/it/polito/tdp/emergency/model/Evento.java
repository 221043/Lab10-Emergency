package it.polito.tdp.emergency.model;

public class Evento implements Comparable<Evento>{

	public enum TipoEvento { PAZIENTE_ARRIVO, PAZIENTE_GUARISCE, PAZIENTE_MUORE, DOCTOR_INIZIA_TURNO, DOCTOR_FINE_TURNO, ASSISTENTE_INIZIA_TURNO, 
		ASSISTENTE_FINE_TURNO };
	
	protected long time;
	protected TipoEvento type;
	protected int dato;
	
	public long getTime() {
		return time;
	}
	
	public TipoEvento getType() {
		return type;
	}

	public int getDato() {
		return dato;
	}

	@Override
	public String toString() {
		return "Evento [time=" + time + ", type=" + type + ", dato=" + dato + "]";
	}

	public Evento(long time, TipoEvento type, int dato) {
		this.time = time;
		this.type = type;
		this.dato = dato;
	}

	@Override
	public int compareTo(Evento e) {
		return Long.compare(this.time, e.time);
	}
	
}
