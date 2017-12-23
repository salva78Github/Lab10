package it.polito.tdp.porto.model;

import java.util.List;

import org.jgrapht.graph.DefaultEdge;

public class PortoEdge extends DefaultEdge{
	private static final long serialVersionUID = 6455163856128561588L;
	
	private final List<Paper> papers;

	/**
	 * @param papers
	 */
	public PortoEdge(List<Paper> papers) {
		this.papers = papers;
	}

	/**
	 * @return the papers
	 */
	public List<Paper> getPapers() {
		return papers;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PortoEdge [papers=" + papers + "]";
	}
	
	
	
}
