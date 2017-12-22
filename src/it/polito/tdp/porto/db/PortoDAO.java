package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.porto.exception.AuthorNotFoundException;
import it.polito.tdp.porto.exception.PaperNotFoundException;
import it.polito.tdp.porto.exception.PortoException;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) throws PortoException {
		Connection conn=null;
		PreparedStatement st =null;
		ResultSet rs = null;
		
		final String sql = "SELECT id, lastname, firstname FROM author where id=?";

		try {
			conn = DBConnect.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			throw new AuthorNotFoundException("L'autore con id " + id + " non esiste.");

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new PortoException("Errore Db", sqle);
		} finally {
			DBConnect.closeResources(conn, st, rs);
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getPaper(int eprintid) throws PortoException {
		Connection conn=null;
		PreparedStatement st =null;
		ResultSet rs = null;

		final String sql = "SELECT eprintid, title, issn, publication, type, types FROM paper where eprintid=?";

		try {
			conn = DBConnect.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			throw new PaperNotFoundException("L'articolo con id " + eprintid + " non esiste.");

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new PortoException("Errore Db", sqle);
		} finally {
			DBConnect.closeResources(conn, st, rs);
		}
	}
	
	public List<Author> getAuthours() throws PortoException {
		Connection conn=null;
		PreparedStatement st =null;
		ResultSet rs = null;

		final String sql = "SELECT id, lastname, firstname FROM author ORDER BY lastname, firstname";
		List<Author> authors = new ArrayList<Author>();
		
		try {
			conn = DBConnect.getConnection();
			st = conn.prepareStatement(sql);

			rs = st.executeQuery();

			while (rs.next()) {
				Author author = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				int authorId = author.getId();
				System.out.println("<getCoautori> id: " + authorId);
				author.setCoautori(getCoautori(authorId));
				authors.add(author);
			}

			System.out.println("<getAuthours> numero di autori: " + authors.size());
			return authors;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new PortoException("Errore Db", sqle);
		} finally {
			DBConnect.closeResources(conn, st, rs);
		}
	}

	public List<Author> getCoautori(int id) throws PortoException {
		Connection conn=null;
		PreparedStatement st =null;
		ResultSet rs = null;

		final String sql = "SELECT DISTINCT a.id, lastname, firstname " +
						   "FROM creator c, author a " +
						   "WHERE c.authorid = a.id " + 
						   "AND c.eprintid IN " +
							 "( " +
							    "SELECT c1.eprintid " + 
							    "FROM creator c1 " +
							    "WHERE c1.authorid = ? " +
						     ") " +
						   "AND c.authorid != ? " +
						   "ORDER BY lastname, firstname";
		
		List<Author> coautori = new ArrayList<Author>();
		
		try {
			conn = DBConnect.getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			st.setInt(2, id);
			
			rs = st.executeQuery();

			while (rs.next()) {
				Author author = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				coautori.add(author);
			}

			System.out.println("<getCoautori> numero di autori: " + coautori.size());
			return coautori;

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new PortoException("Errore Db", sqle);
		} finally {
			DBConnect.closeResources(conn, st, rs);
		}

	}
	
}