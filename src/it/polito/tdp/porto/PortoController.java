package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.exception.PortoException;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AuthorsPair;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

	private Model model;

    @FXML
    void handleCoautori(ActionEvent event) {
    	this.txtResult.setText("");
    	Author autore = boxPrimo.getValue();
    	System.out.println("<handleCoautori> Autore selezionato: " + autore);
    	try {
			List<Author> coautori = this.model.getCoautori(autore);
			this.txtResult.appendText("Coautori di " + autore.getFirstname() + " " + autore.getLastname() + "\n");
			for(Author a : coautori){
				this.txtResult.appendText(a.getFirstname() + " " + a.getLastname() + "\n");
			}
			
			this.boxSecondo.getItems().addAll(this.model.getNoCoautori(autore));
			
		} catch (PortoException pe) {
			pe.printStackTrace();
			this.txtResult.setText("Errore nel caricamento della lista dei coautori: " + pe.getMessage());
		}
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	this.txtResult.setText("");
    	Author a1 = boxPrimo.getValue();
    	Author a2 = boxSecondo.getValue();
    	try {
			List<AuthorsPair> apl = model.getAuthorPairs(a1, a2);
			this.txtResult.setText("Lista articoli degli autori " + a1 + " e " + a2 + "\n");
			for(AuthorsPair ap : apl){
				List<Paper> pl = ap.getPapers();
				for(Paper p : pl){
					this.txtResult.appendText(p.getTitle() + " ***");
				}
				this.txtResult.appendText("\n");
			}
			
			
		} catch (PortoException pe) {
			pe.printStackTrace();
			this.txtResult.setText("Errore nel caricamento della lista dei coautori: " + pe.getMessage());
		}
    	
    	
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
    }

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model = model;
		try {
			boxPrimo.getItems().addAll(this.model.getAutori());
		} catch (PortoException pe) {
			pe.printStackTrace();
			txtResult.setText("Errore inizializzazione tendina degli autori.");
		}
	}
}
