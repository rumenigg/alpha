package br.com.rti.alpha.dao;



import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.rti.alpha.modelo.pessoa.Pessoa;
import br.com.rti.alpha.util.HibernateUtil;


public class DaoFactory 
{
	private final Session session;
	private Transaction transaction;

	public DaoFactory()
	{
		this.session = HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	public DaoFactory(Session session)
	{
		this.session = session;
	}
	
	public void beginTransaction()
	{
		this.transaction = this.session.getTransaction().isActive() ? this.session.getTransaction() : this.session.beginTransaction();
	}
	
	public void commit()
	{
		this.transaction.commit();
		this.transaction = null;
	}
	
	public boolean hasTransaction()
	{
		return this.transaction != null;
	}
	
	public void rollback()
	{
		this.transaction.rollback();
		this.transaction = null;
	}
	
	public void close()
	{
		this.session.close();		
	}
	
	public Dao<Pessoa> getPessoaDao()
	{
		return new Dao<Pessoa>(this.session, Pessoa.class);
	}
	
	public PessoaDAO getPessoaDAO()
	{
		return new PessoaDAO(this.session);
	}
	
	public FuncaoDAO getFuncaoDAO()
	{
		return new FuncaoDAO(this.session);
	}
	
	public CompartimentoDAO getCompartimentoDAO()
	{
		return new CompartimentoDAO(this.session);
	}
	
	public TipoCompartimentoDAO getTipoCompartimentoDAO()
	{
		return new TipoCompartimentoDAO(this.session);
	}
	
	public OleoDAO getOleoDAO()
	{
		return new OleoDAO(this.session);
	}
	
	public AtivoDAO getAtivoDAO()
	{
		return new AtivoDAO(this.session);
	}
	
	public FrotaDAO getFrotaDAO()
	{
		return new FrotaDAO(this.session);
	}
	
	public SupervisaoDAO getSupervisaoDAO()
	{
		return new SupervisaoDAO(this.session);
	}
	
	public PlanoTrabalhoDAO getPlanoTrabalhoDAO()
	{
		return new PlanoTrabalhoDAO(this.session);
	}
	
	public TipoColetaDAO getTipoColetaDAO()
	{
		return new TipoColetaDAO(this.session);
	}
	
	public AmostraDAO getAmostraDAO()
	{
		return new AmostraDAO(this.session);
	}
	
	public FotoAmostraDAO getFotoAmostraDAO()
	{
		return new FotoAmostraDAO(this.session);
	}
	
	public AnaliseDAO getAnaliseDAO()
	{
		return new AnaliseDAO(this.session);
	}
	
	public ElementosDAO getElementosDAO()
	{
		return new ElementosDAO(this.session);
	}
	
	public LaudosDAO getLaudosDAO()
	{
		return new LaudosDAO(this.session);
	}
}
