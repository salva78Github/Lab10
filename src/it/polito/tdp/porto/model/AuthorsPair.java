package it.polito.tdp.porto.model;

import java.util.List;

public class AuthorsPair {
	private final Author a1;
	private final Author a2;
	private final List<Paper> papers;
	/**
	 * @param a1
	 * @param a2
	 * @param papers
	 */
	public AuthorsPair(Author a1, Author a2, List<Paper> papers) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.papers = papers;
	}
	/**
	 * @return the a1
	 */
	public Author getA1() {
		return a1;
	}
	/**
	 * @return the a2
	 */
	public Author getA2() {
		return a2;
	}
	/**
	 * @return the papers
	 */
	public List<Paper> getPapers() {
		return papers;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a1 == null) ? 0 : a1.hashCode());
		result = prime * result + ((a2 == null) ? 0 : a2.hashCode());
		result = prime * result + ((papers == null) ? 0 : papers.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthorsPair other = (AuthorsPair) obj;
		if (a1 == null) {
			if (other.a1 != null)
				return false;
		} else if (!a1.equals(other.a1))
			return false;
		if (a2 == null) {
			if (other.a2 != null)
				return false;
		} else if (!a2.equals(other.a2))
			return false;
		if (papers == null) {
			if (other.papers != null)
				return false;
		} else if (!papers.equals(other.papers))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AuthorsPair [a1=" + a1 + ", a2=" + a2 + ", papers=" + papers + "]";
	}

	
	
	
	
}
