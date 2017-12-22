package it.polito.tdp.porto.db;

import it.polito.tdp.porto.exception.PortoException;

public class TestPortoDAO {
	
	public static void main(String args[]) {
		PortoDAO pd = new PortoDAO();
		try {
			System.out.println(pd.getAutore(85));
			System.out.println(pd.getPaper(2293546));
			System.out.println(pd.getPaper(1941144));
			//System.out.println(pd.getAuthours());
			System.out.println(pd.getCoautori(17160));
		} catch (PortoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
