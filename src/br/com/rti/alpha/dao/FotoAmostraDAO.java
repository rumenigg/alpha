package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.amostra.FotoAmostra;

public class FotoAmostraDAO extends Dao<FotoAmostra>
{

	public FotoAmostraDAO(Session session) 
	{
		super(session, FotoAmostra.class);
		// TODO Auto-generated constructor stub
	}
	
}
