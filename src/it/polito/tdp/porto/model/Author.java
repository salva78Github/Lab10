package it.polito.tdp.porto.model;

import java.util.List;

public class Author {

	private final int id;
	private final String lastname;
	private final String firstname;
	private List<Author> coautori;	
	
	/**
	 * @return the coautori
	 */
	public List<Author> getCoautori() {
		return coautori;
	}

	public Author(int id, String lastname, String firstname) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
	}

	public int getId() {
		return id;
	}



	/**
	 * @param coautori the coautori to set
	 */
	public void setCoautori(List<Author> coautori) {
		this.coautori = coautori;
	}

	public String getLastname() {
		return lastname;
	}


	public String getFirstname() {
		return firstname;
	}



	@Override
	public String toString() {
		return "Author [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Author other = (Author) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
