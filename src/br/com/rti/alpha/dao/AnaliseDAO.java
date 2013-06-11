package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.amostra.Analise;

public class AnaliseDAO extends Dao<Analise> 
{

	public AnaliseDAO(Session session) 
	{
		super(session, Analise.class);
		// TODO Auto-generated constructor stub
	}

	
}
