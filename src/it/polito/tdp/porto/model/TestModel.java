package it.polito.tdp.porto.model;

import java.util.List;

import it.polito.tdp.porto.exception.PortoException;

public class TestModel {

	public static void main(String[] args) {
		
		System.out.println("TODO: write a Model class and test it!");
		try {
			Model model = new Model();
			List<Author> coautori = model.getCoautori(new Author(719,"Milanese","Mario"));
			System.out.println(coautori);
			
			
		} catch (PortoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
