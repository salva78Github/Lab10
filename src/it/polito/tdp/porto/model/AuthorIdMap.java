package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.Map;

public class AuthorIdMap {
	private Map<Integer, Author> map;

	public AuthorIdMap() {
		map = new HashMap<>();
	}

	public Author get(Integer ccode) {
		return map.get(ccode);
	}

	/**
	 * Check whether the {@link Author} is already contained in the <em>Identity
	 * Map</em>. If yes, it returns the stored object. If no, it adds this new
	 * object to the map. In all cases, the "canonical" object is returned.
	 * 
	 * @param author
	 *            the element to be added
	 * @return the canonical reference to the object
	 */
	public Author put(Author author) {
		Author old = map.get(author.getId());
		if (old == null) {
			map.put(author.getId(), author);
			return author;
		} else {
			return old;
		}
	}

}
