package it.polito.tdp.emergency.main;

//import it.polito.tdp.emergency.model.Core;
//import it.polito.tdp.emergency.model.Dottore;
//import it.polito.tdp.emergency.model.Evento;
import it.polito.tdp.emergency.model.Model;
//import it.polito.tdp.emergency.model.Paziente;

public class Main {

	Model model;
	
	public static void main(String[] args) {
		Main m = new Main();
		m.stub();
	}

	protected void stub(){
		model = new Model();
		model.readEventi();
//		System.out.println(model.getEventi());
		model.readPazienti();
//		System.out.println(model.getPazienti());
		model.simula();
//		System.out.println(model.getPazienti());
//		System.out.println("Persi: "+model.getPazientiPersi());
//		System.out.println("Salvati: "+model.getPazientiSalvati());
	}
	
}
