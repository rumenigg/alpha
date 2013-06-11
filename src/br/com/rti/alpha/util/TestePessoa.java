package br.com.rti.alpha.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import br.com.rti.alpha.modelo.pessoa.Funcao;
import br.com.rti.alpha.modelo.pessoa.Pessoa;

public class TestePessoa 
{
	
	public static void main(String[] args)
	{
		Configuration conf = new AnnotationConfiguration();
		conf.configure();		
		SessionFactory factory = conf.buildSessionFactory();
		Session session = factory.openSession();
		
		Funcao f = new Funcao();
		f.setFuncao("Mecânico");
		
		Pessoa p = new Pessoa();
		p.setMatricula("HA444");
		p.setNome("Alpha2");
		p.setEmail("alpha2_hydro@hydro.com");
		p.setSenha("alpha2");
		p.setFoto("foto123456");
		
		Transaction t = session.beginTransaction();
		
		session.save(f);
		p.setFuncao(f);
		
		session.save(p);
		t.commit();
		
		session.close();
	}

}
