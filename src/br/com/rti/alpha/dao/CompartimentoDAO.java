package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.ativo.Compartimento;

public class CompartimentoDAO extends Dao<Compartimento>
{
	public CompartimentoDAO(Session session)
	{
		super(session, Compartimento.class);
	}
}
