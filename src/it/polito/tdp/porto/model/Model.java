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

	private SimpleGraph<Author, DefaultEdge> graph;
	private PortoDAO dao;
	private List<Author> authors;
	private AuthorIdMap authorIdMap ;
	private List<Paper> papers;

	public Model() throws PortoException {
		this.dao = new PortoDAO();
		this.authorIdMap = new AuthorIdMap();
		this.papers = dao.getPapers();
		this.graph = createGraph();
	}

	private SimpleGraph<Author, DefaultEdge> createGraph() throws PortoException {

		graph = new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);

		Graphs.addAllVertices(graph, getAutori());

		for (Author a : authors) {
			List<Author> coautori = a.getCoautori();
			for (Author c : coautori) {
				c.setCoautori(this.authorIdMap.get(c.getId()).getCoautori());
				c.setPapers(this.authorIdMap.get(c.getId()).getPapers());
				graph.addEdge(a, c);
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
		
		if(this.authors==null) {
			this.authors = dao.getAuthours(authorIdMap) ;
		}
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

		List<DefaultEdge> path = DijkstraShortestPath.findPathBetween(graph, fa, sa);
		List<AuthorsPair> apl = new ArrayList<AuthorsPair>();
		for (DefaultEdge arch : path) {
			List<Paper> papers = new ArrayList<Paper>();
			Author a1 = graph.getEdgeSource(arch);
			Author a2 = graph.getEdgeTarget(arch);
			if(a1.getPapers().size()>a2.getPapers().size()){
				for(Paper p : a2.getPapers()){
					if(a1.getPapers().contains(p)){
						papers.add(p);
					}
				}
			}
			else{
				for(Paper p : a1.getPapers()){
					if(a2.getPapers().contains(p)){
						papers.add(p);
					}
				}				
			}
			
			AuthorsPair ap = new AuthorsPair(fa, sa, papers);
			apl.add(ap);
		}
		System.out.println("<getAuthorPairs> apl size: " + apl.size());
		return apl;
	}

}
