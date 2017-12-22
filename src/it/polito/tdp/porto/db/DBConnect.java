package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

import it.polito.tdp.porto.exception.PortoException;

public class DBConnect {

	private static String jdbcURL = "jdbc:mysql://localhost/porto2015?user=root&password=salva_root";
	private static DataSource ds;

	public static Connection getConnection() throws PortoException {

		if (ds == null) {
			// crea il DataSource
			try {
				ds = DataSources.pooledDataSource(
						DataSources.unpooledDataSource(jdbcURL));
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new PortoException("Errore nella creazione del datasource", sqle);
			}
		}

		try {
			Connection c = ds.getConnection();
			return c;
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
			throw new PortoException("Errore nel recupero della connessione. ", sqle);
		}

	}
	
	public static void closeResources(Connection c, Statement s, ResultSet rs){
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}