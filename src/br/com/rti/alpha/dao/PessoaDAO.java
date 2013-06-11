package br.com.rti.alpha.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.rti.alpha.modelo.pessoa.Pessoa;

public class PessoaDAO extends Dao<Pessoa> 
{
	PessoaDAO(Session session)
	{
		super(session, Pessoa.class);
	}
	
	public Pessoa existePessoa(Pessoa p)
	{
		String hql = "select p from Pessoa as p where p.matricula = :matricula and p.senha = :senha";
		Query query = getSession().createQuery(hql);
		query.setParameter("matricula", p.getMatricula());
		query.setParameter("senha", p.getSenha());
		
		return (Pessoa) query.uniqueResult();
	}

}
