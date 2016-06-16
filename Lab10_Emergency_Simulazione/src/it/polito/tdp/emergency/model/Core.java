package it.polito.tdp.emergency.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Core {

	final static int numeroAssistenti=3;
	
	Queue<Evento> listaEventi = new PriorityQueue<Evento>();
	Map<Integer, Paziente> pazienti = new HashMap<Integer, Paziente>();
	Set<Dottore> dottori = new HashSet<Dottore>();
	Assistente[] assistenti = new Assistente[numeroAssistenti];
	Queue<Paziente> pazientiInAttesa = new PriorityQueue<Paziente>();
	boolean flag=true;
	long inizio=0;
	
	// Statistiche
	int pazientiPersi=0;
	int pazientiSalvati=0;
	
	public Core() {
		Dottore Io = new Dottore(1);
		dottori.add(Io);
		for(int i=1; i<4; i++){
			Assistente a = new Assistente(i);
			assistenti[i-1] = a;
		}
	}
	
	public long getInizio() {
		return inizio;
	}

	public int getPazientiPersi() {
		return pazientiPersi;
	}

	public void setPazientiPersi(int pazientiPersi) {
		this.pazientiPersi = pazientiPersi;
	}

	public int getPazientiSalvati() {
		return pazientiSalvati;
	}

	public void setPazientiSalvati(int pazientiSalvati) {
		this.pazientiSalvati = pazientiSalvati;
	}

	public void aggiungiEvento(Evento e){
		listaEventi.add(e);
	}
	
	public void aggiungiPaziente(Paziente p){
		pazienti.put(p.getId(), p);
	}
	
	public void aggiungiDottore(Dottore d){
		dottori.add(d);
	}
	
	public void passo(){
		Evento e = listaEventi.remove();
		switch(e.getType()){
		case PAZIENTE_ARRIVO:
			if(flag){
				for(Dottore d:dottori){
					inizio = e.getTime();
					this.aggiungiEvento(new Evento(inizio+d.getTempo(), Evento.TipoEvento.DOCTOR_INIZIA_TURNO, d.getId()));
					flag = false;
				}	
				for(Assistente a:assistenti){
					this.aggiungiEvento(new Evento(inizio+(a.getId()-1)*8*60, Evento.TipoEvento.ASSISTENTE_INIZIA_TURNO, a.getId()));
				}
			}
			System.out.println("Arrivo paziente: "+e);
			pazientiInAttesa.add(pazienti.get(e.getDato()));
			switch(pazienti.get(e.getDato()).getStato()){
			case BIANCO:
				++pazientiSalvati;
				break;
			case GIALLO:
				this.aggiungiEvento(new Evento(e.getTime()+60*6, Evento.TipoEvento.PAZIENTE_MUORE, e.getDato()));
				break;
			case ROSSO:
				this.aggiungiEvento(new Evento(e.getTime()+60, Evento.TipoEvento.PAZIENTE_MUORE, e.getDato()));
				break;
			case VERDE:
				this.aggiungiEvento(new Evento(e.getTime()+60*12, Evento.TipoEvento.PAZIENTE_MUORE, e.getDato()));
				break;
			default:
				System.err.println("Panik!");
			}
			break;
		case PAZIENTE_GUARISCE:
			if(pazienti.get(e.getDato()).getStato()!=Paziente.StatoPaziente.NERO){
				System.out.println("Guarigione paziente: "+e);	
				pazienti.get(e.getDato()).setStato(Paziente.StatoPaziente.SALVO);
				pazientiSalvati++;
				Assistente a = pazienti.get(e.getDato()).getAssistente();
				Dottore d = pazienti.get(e.getDato()).getDottore();
				if(a==null)
					d.setStato(Dottore.StatoDottore.DIPONIBILE);
				if(d==null)
					a.setStato(Assistente.StatoAssistente.DIPONIBILE);
			}
			break;
		case PAZIENTE_MUORE:
			if(pazienti.get(e.getDato()).getStato()==Paziente.StatoPaziente.IN_CURA){
				System.out.println("Paziente morto sotto i ferri: "+e);
				pazienti.get(e.getDato()).setStato(Paziente.StatoPaziente.NERO);
				++pazientiPersi;
				Assistente a = pazienti.get(e.getDato()).getAssistente();
				Dottore d = pazienti.get(e.getDato()).getDottore();
				if(a==null)
					d.setStato(Dottore.StatoDottore.DIPONIBILE);
				if(d==null)
					a.setStato(Assistente.StatoAssistente.DIPONIBILE);
			} else if(pazienti.get(e.getDato()).getStato()==Paziente.StatoPaziente.SALVO)
				System.out.println("Paziente già salvato: "+e);	
			else{
				System.out.println("Paziente morto: "+e);
				pazienti.get(e.getDato()).setStato(Paziente.StatoPaziente.NERO);
				++pazientiPersi;
			}
			break;
		case DOCTOR_INIZIA_TURNO:
			Dottore d = this.getDottore(e.getDato());
			System.out.println("Inizio turno dottore: "+e);
		    d.setStato(Dottore.StatoDottore.DIPONIBILE);
			this.aggiungiEvento(new Evento(e.getTime()+60*8, Evento.TipoEvento.DOCTOR_FINE_TURNO, e.getDato()));
			break;
		case DOCTOR_FINE_TURNO:
			System.out.println("Fine turno dottore: "+e);
			this.aggiungiEvento(new Evento(e.getTime()+60*16, Evento.TipoEvento.DOCTOR_INIZIA_TURNO, e.getDato()));
			Dottore o = this.getDottore(e.getDato());
		    o.setStato(Dottore.StatoDottore.NON_DISPONIBILE);
			break;
		case ASSISTENTE_INIZIA_TURNO:
			Assistente a = this.getAssistente(e.getDato()-1);
			System.out.println("Inizio turno assistente: "+e);
		    a.setStato(Assistente.StatoAssistente.DIPONIBILE);
			this.aggiungiEvento(new Evento(e.getTime()+60*8, Evento.TipoEvento.ASSISTENTE_FINE_TURNO, e.getDato()));
			break;
		case ASSISTENTE_FINE_TURNO:
			System.out.println("Fine turno assistente: "+e);
			this.aggiungiEvento(new Evento(e.getTime()+60*16, Evento.TipoEvento.ASSISTENTE_INIZIA_TURNO, e.getDato()));
			Assistente s = this.getAssistente(e.getDato()-1);
		    s.setStato(Assistente.StatoAssistente.NON_DISPONIBILE);
			break;
		default:
			System.err.println("Panik!");		
		}
		while(cura(e.getTime()))
			;
	} 
	
	public boolean checkLista(){
		for(Evento e:listaEventi){
			if(e.getType().compareTo(Evento.TipoEvento.PAZIENTE_ARRIVO)==0 || e.getType().compareTo(Evento.TipoEvento.PAZIENTE_GUARISCE)==0
				|| e.getType().compareTo(Evento.TipoEvento.PAZIENTE_MUORE)==0)
				return false;
		}
		return true;
	}
	
	public void simula(){
		while(!this.checkLista()){
			passo();
		}
	}
	
	protected boolean cura(long adesso){
		if(pazientiInAttesa.isEmpty())
			return false;
		Paziente p = pazientiInAttesa.remove();
		if(p.getStato()!=Paziente.StatoPaziente.NERO){
			Dottore d = this.getDottoreDisponibile();
			if(d==null){
				if(p.getStato()==Paziente.StatoPaziente.ROSSO)
					return false;
				for(int i=1; i<4; i++){
					Assistente a = this.getAssistente(i-1);
					if(a.getStato()==Assistente.StatoAssistente.DIPONIBILE){
						aggiungiEvento(new Evento(adesso+30, Evento.TipoEvento.PAZIENTE_GUARISCE, p.getId()));
						if(p.getStato()==Paziente.StatoPaziente.BIANCO)
							--pazientiSalvati;
						pazienti.get(p.getId()).setStato(Paziente.StatoPaziente.IN_CURA);
						a.setStato(Assistente.StatoAssistente.NON_DISPONIBILE);
						p.setAssistente(a);
						return true;
					}
				}
				return false;
			}
			aggiungiEvento(new Evento(adesso+30, Evento.TipoEvento.PAZIENTE_GUARISCE, p.getId()));
			if(p.getStato()==Paziente.StatoPaziente.BIANCO)
				--pazientiSalvati;
			pazienti.get(p.getId()).setStato(Paziente.StatoPaziente.IN_CURA);
			d.setStato(Dottore.StatoDottore.NON_DISPONIBILE);
			p.setDottore(d);	
		}	
		return true;
	}
	
	public Dottore getDottore(int id){
		Iterator<Dottore> it;
	    it=dottori.iterator();
	    while(it.hasNext()) {
	    	Dottore d = it.next();
	      if(d.getId()==id)
	    	  return d;
	    }
	    return null; 
	}
	
	public Dottore getDottoreDisponibile(){
		Iterator<Dottore> it;
	    it=dottori.iterator();
	    while(it.hasNext()) {
	    	Dottore d = it.next();
	      if(d.getStato().compareTo(Dottore.StatoDottore.DIPONIBILE)==0)
	    	  return d;
	    }
	    return null; 
	}
	
	public int getNumeroDottori(){
		return dottori.size();
	}
	
	public void clear(){
		listaEventi.clear();
		pazientiInAttesa.clear();
		flag = true;
	}
	
	public Assistente getAssistente(int i){
		return assistenti[i];
	}
	
}
