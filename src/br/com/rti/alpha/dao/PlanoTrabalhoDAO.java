package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.amostra.PlanoTrabalho;

public class PlanoTrabalhoDAO extends Dao<PlanoTrabalho> 
{
	public PlanoTrabalhoDAO(Session session)
	{
		super(session, PlanoTrabalho.class);
	}
}
