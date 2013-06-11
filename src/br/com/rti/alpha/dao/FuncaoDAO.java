package br.com.rti.alpha.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.rti.alpha.modelo.pessoa.Funcao;
import br.com.rti.alpha.modelo.pessoa.Pessoa;

public class FuncaoDAO extends Dao<Funcao>
{

	FuncaoDAO(Session session) 
	{
		super(session, Funcao.class);
	}
	
	

}
