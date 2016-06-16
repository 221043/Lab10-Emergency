package it.polito.tdp.emergency.model;

import it.polito.tdp.emergency.db.EventoDAO;
import it.polito.tdp.emergency.db.PazienteDAO;

public class Model {

	private Core core = new Core();
	private PazienteDAO pdao = new PazienteDAO();
	private EventoDAO edao = new EventoDAO();
	
	public void readPazienti(){
		for(Paziente p:pdao.readPazienti())
			core.aggiungiPaziente(p);
	}
	
	public void readEventi(){
		for(Evento e:edao.readEventi()){
			core.aggiungiEvento(e);
		}
	}
	
	public int addDottore(String nome, int inizioTurno){
		int id = core.getNumeroDottori()+1;
		Dottore d = new Dottore(id);
		d.setNome(nome);
		core.aggiungiDottore(d);
		core.aggiungiEvento(new Evento(core.getInizio()+inizioTurno*60, Evento.TipoEvento.DOCTOR_INIZIA_TURNO, id));
		return id;
	}
	
	public void simula(){
		core.simula();
	}
	
	public int getPazientiSalvati(){
		return core.getPazientiSalvati();
	}
	
	public int getPazientiPersi(){
		return core.getPazientiPersi();
	}
	
	public void clear(){
		core.clear();
		core.setPazientiPersi(0);
		core.setPazientiSalvati(0);
	}
	
}
