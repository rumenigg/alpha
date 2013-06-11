package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.amostra.Elementos;

public class ElementosDAO extends Dao<Elementos>
{
	public ElementosDAO(Session session)
	{
		super(session, Elementos.class);
	}
}
