package it.polito.tdp.porto.model;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private List<Author> autori ;
	
	private SimpleGraph<Author, DefaultEdge> graph ;
	
	public Model() {
		
	}

	public List<Author> trovaCoAutori(Author a) {
		
		if(this.graph==null)
			creaGrafo() ;
		
		List<Author> coautori = Graphs.neighborListOf(this.graph, a) ;
		return coautori ;
	}
	
	private void creaGrafo() {
		PortoDAO dao = new PortoDAO() ;
		
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;
		
		Graphs.addAllVertices(this.graph, getAutori()) ;
		
		for(Author a: graph.vertexSet()) {
			List<Author> coautori = dao.getCoAutori(a) ;
			for(Author a2:coautori) {
				this.graph.addEdge(a, a2) ;
			}
		}
	}

	public List<Author> getAutori() {
		
		if(this.autori==null) {
			PortoDAO dao = new PortoDAO() ;
			this.autori = dao.listAutori() ;
			if(this.autori==null) 
				throw new RuntimeException("Errore con il database") ;
		}
		
		return this.autori ;

	}
	
	
	

}
