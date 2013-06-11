package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.supervisao.Supervisao;

public class SupervisaoDAO extends Dao<Supervisao>
{
	public SupervisaoDAO(Session session)
	{
		super(session, Supervisao.class);
	}
}
