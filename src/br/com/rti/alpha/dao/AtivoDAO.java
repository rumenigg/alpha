package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.ativo.Ativo;

public class AtivoDAO extends Dao<Ativo> 
{
	public AtivoDAO(Session session)
	{
		super(session, Ativo.class);
	}
}
