package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.ativo.Oleo;

public class OleoDAO extends Dao<Oleo>
{
	public OleoDAO(Session session)
	{
		super(session, Oleo.class);
	}
}
