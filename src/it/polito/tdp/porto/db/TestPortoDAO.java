package it.polito.tdp.porto.db;

import java.util.List;

import it.polito.tdp.porto.exception.PortoException;
import it.polito.tdp.porto.model.Author;

public class TestPortoDAO {
	
	public static void main(String args[]) {
		PortoDAO pd = new PortoDAO();
		try {
			System.out.println(pd.getAutore(85));
			System.out.println(pd.getPaper(2293546));
			System.out.println(pd.getPaper(1941144));
			//System.out.println(pd.getAuthours());
			/*
			List<Author> authors = pd.getAuthours();
			for(Author a : authors){
				if(a.getId() == 12816){
					System.out.println("<Articoli di Ficarra Elisa> " + a.getPapers());
				}
			}
			*/
			System.out.println(pd.getCoautori(17160));
		} catch (PortoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
