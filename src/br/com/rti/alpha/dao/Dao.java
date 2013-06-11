package br.com.rti.alpha.dao;

import java.util.List;

import org.hibernate.Session;

public class Dao<T> 
{
	private final Session session;
	private final Class classe;
	
	public Dao(Session session, Class classe)
	{
		this.session = session;
		this.classe = classe;
	}
	
	protected Session getSession()
	{
		return session;
	}
	
	public void adiciona(T t)
	{
		this.session.saveOrUpdate(t);
	}
	
	public void remove(T t)
	{
		this.session.delete(t);
	}
	
	public void atualiza(T t)
	{
		this.session.update(t);
	}
	
	public List<T> listaTudo()
	{
		return this.session.createCriteria(this.classe).list();
	}
	
	public T procura(Long id)
	{
		return (T) session.load(this.classe, id);
	}
	
	public T procura(int id)
	{
		return (T) session.load(this.classe, id);
	}
}
