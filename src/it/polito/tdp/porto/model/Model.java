package it.polito.tdp.porto.model;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;
import it.polito.tdp.porto.exception.PortoException;

public class Model {

	private SimpleGraph<Author, DefaultEdge> graph;
	private PortoDAO dao;

	public Model() throws PortoException {
		this.dao = new PortoDAO();
		this.graph = createGraph();
	}

	private SimpleGraph<Author, DefaultEdge> createGraph() throws PortoException {

		graph = new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);

		List<Author> authors = dao.getAuthours();
		Graphs.addAllVertices(graph, authors);

		for (Author a : authors) {
			List<Author> coautori = a.getCoautori();
			for (Author c : coautori) {
				graph.addEdge(a, c);
			}
		}
		return graph;

	}

	public List<Author> getCoautori(Author autore) throws PortoException{
		if (graph == null)
			throw new PortoException("Grafo non esistente");

		List<Author> coautori = Graphs.neighborListOf(graph, autore);
		return coautori;
		
	}
	
	public List<Author> getAutori() throws PortoException{
		return this.dao.getAuthours();
	}
	
	
}
