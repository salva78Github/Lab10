package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;
import it.polito.tdp.porto.exception.PortoException;

public class Model {

	private SimpleGraph<Author, PortoEdge> graph;
	private PortoDAO dao;
	List<Author> authors;

	public Model() throws PortoException {
		this.dao = new PortoDAO();
		this.authors = dao.getAuthours();
		this.graph = createGraph();
	}

	private SimpleGraph<Author, PortoEdge> createGraph() throws PortoException {

		graph = new SimpleGraph<Author, PortoEdge>(PortoEdge.class);

		Graphs.addAllVertices(graph, authors);

		for (Author a : authors) {
			List<Author> coautori = a.getCoautori();
			for (Author c : coautori) {
				graph.addEdge(a, c, new PortoEdge(this.dao.getPapersByAuthors(a, c)));
			}
		}

		return graph;

	}

	public List<Author> getCoautori(Author autore) throws PortoException {
		if (graph == null)
			throw new PortoException("Grafo non esistente");

		List<Author> coautori = Graphs.neighborListOf(graph, autore);
		return coautori;

	}

	public List<Author> getNoCoautori(Author autore) throws PortoException {
		if (graph == null)
			throw new PortoException("Grafo non esistente");

		List<Author> noCoautori = new ArrayList<Author>();
		for (Author a : getAutori()) {
			if (!autore.getCoautori().contains(a)) {
				noCoautori.add(a);
			}
		}

		return noCoautori;

	}

	public List<Author> getAutori() throws PortoException {
		return this.authors;
	}

	/**
	 * ricerca cammino minimo + interrogazione db per ogni coppia vertici
	 * 
	 * @param fa
	 * @param sa
	 * @throws PortoException
	 */

	public List<AuthorsPair> getAuthorPairs(Author fa, Author sa) throws PortoException {
		if (graph == null)
			throw new PortoException("Grafo non esistente");

		List<PortoEdge> path = DijkstraShortestPath.findPathBetween(graph, fa, sa);
		List<AuthorsPair> apl = new ArrayList<AuthorsPair>();
		for (PortoEdge arch : path) {
			List<Paper> papers = arch.getPapers();
			AuthorsPair ap = new AuthorsPair(fa, sa, papers);
			apl.add(ap);
		}
		System.out.println("<getAuthorPairs> apl size: " + apl.size());
		return apl;
	}

}
