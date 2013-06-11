package br.com.rti.alpha.dao;

import org.hibernate.Session;

import br.com.rti.alpha.modelo.amostra.TipoColeta;

public class TipoColetaDAO extends Dao<TipoColeta> 
{
	public TipoColetaDAO(Session session)
	{
		super(session, TipoColeta.class);
	}

}
