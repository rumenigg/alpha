package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.supervisao.Frota;

public class FrotaDAO extends Dao<Frota>
{
	public FrotaDAO(Session session)
	{
		super(session, Frota.class);
	}
}
