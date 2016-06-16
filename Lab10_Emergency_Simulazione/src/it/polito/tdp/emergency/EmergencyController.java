package it.polito.tdp.emergency;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.emergency.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EmergencyController {

	private Model model = new Model();
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNomeMedico;

    @FXML
    private TextField txtOre;

    @FXML
    private Button btnSimula;

    @FXML
    private Button btnAggiungiDottore;

    @FXML
    private TextArea txtRisultato;

    @FXML
    void doAggiungiDottore(ActionEvent event) {
    	String nome = txtNomeMedico.getText();
    	try{
    		int ore = Integer.parseInt(txtOre.getText());
    		int id = model.addDottore(nome, ore);
    		txtRisultato.setText("Inserito nuovo dottore "+nome+" con id "+id+", inizio turno dopo "+ore+" ore");
    	} catch(NumberFormatException e){
    		e.printStackTrace();
    		txtRisultato.setText("Inserire valore numerico valido in campo 'ore'");
    	}  	
    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	model.clear();
    	model.readEventi();
    	model.readPazienti();
    	model.simula();
    	txtRisultato.setText("Pazienti persi: "+model.getPazientiPersi()+"\n");
    	txtRisultato.appendText("Pazienti salvati: "+model.getPazientiSalvati());
    }

    public void setModel(Model model){
    	this.model = model;
    }
    
    @FXML
    void initialize() {
        assert txtNomeMedico != null : "fx:id=\"txtNomeMedico\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert txtOre != null : "fx:id=\"txtOre\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert btnAggiungiDottore != null : "fx:id=\"btnAggiungiDottore\" was not injected: check your FXML file 'Emergency.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Emergency.fxml'.";

    }
    
}
