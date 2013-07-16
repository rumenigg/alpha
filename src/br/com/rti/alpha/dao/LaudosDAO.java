package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.amostra.Laudos;

public class LaudosDAO extends Dao<Laudos> {

	public LaudosDAO(Session session) {
		super(session, Laudos.class);
		
	}

}
