package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.amostra.Amostra;

public class AmostraDAO extends Dao<Amostra>
{

	public AmostraDAO(Session session) {
		super(session, Amostra.class);
		// TODO Auto-generated constructor stub
	}

}
