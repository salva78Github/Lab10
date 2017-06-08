package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	private List<Author> autori;

	private SimpleGraph<Author, DefaultEdge> graph;

	public Model() {

	}

	public List<Author> trovaCoAutori(Author a) {

		if (this.graph == null)
			creaGrafo();

		List<Author> coautori = Graphs.neighborListOf(this.graph, a);
		return coautori;
	}

	private void creaGrafo() {
		PortoDAO dao = new PortoDAO();

		this.graph = new SimpleGraph<>(DefaultEdge.class);

		Graphs.addAllVertices(this.graph, getAutori());

		for (Author a : graph.vertexSet()) {
			List<Author> coautori = dao.getCoAutori(a);
			for (Author a2 : coautori) {
				this.graph.addEdge(a, a2);
			}
		}
	}

	public List<Author> getAutori() {

		if (this.autori == null) {
			PortoDAO dao = new PortoDAO();
			this.autori = dao.listAutori();
			if (this.autori == null)
				throw new RuntimeException("Errore con il database");
		}

		return this.autori;

	}

	/**
	 * Trova una sequenza di articoli che legano l'autore {@code a1} all'autore
	 * {@code a2}
	 * 
	 * @param a1
	 * @param a2
	 * @return
	 */
	public List<Paper> sequenzaArticoli(Author a1, Author a2) {

		List<Paper> result = new ArrayList<>() ; 
		PortoDAO dao = new PortoDAO();

				
		// trovo un cammino minimo tra a1 ed a2
		DijkstraShortestPath<Author, DefaultEdge> dijkstra = 
				new DijkstraShortestPath<Author, DefaultEdge>(this.graph, a1, a2);
		
		List<DefaultEdge> edges = dijkstra.getPathEdgeList() ;
		// ciascun edge corrisponderà ad un paper!
		
		for(DefaultEdge e: edges) {
			// autori che corrispondono all'edge
			Author as = graph.getEdgeSource(e) ;
			Author at = graph.getEdgeTarget(e) ;
			// trovo l'articolo
			Paper p = dao.articoloComune(as, at) ;
			if(p == null)
				throw new InternalError("Paper not found...") ;
			result.add(p) ;
		}
		
		return result ;

	}

}
