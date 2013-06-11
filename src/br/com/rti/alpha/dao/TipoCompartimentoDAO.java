package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.ativo.TipoCompartimento;

public class TipoCompartimentoDAO extends Dao<TipoCompartimento>
{
	public TipoCompartimentoDAO(Session session)
	{
		super(session, TipoCompartimento.class);
	}
}
