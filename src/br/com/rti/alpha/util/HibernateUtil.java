package br.com.rti.alpha.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil 
{
	private static SessionFactory factory;
	private static ServiceRegistry serviceRegistry;
	
	public static Configuration getInitConfiguration()
	{
		Configuration config = new Configuration();
		config.configure();
		return config;
	}
	
	static 
	{
		try
		{
	       if (factory == null) {
	           Configuration config = HibernateUtil.getInitConfiguration();
	           serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	           factory = config.buildSessionFactory(serviceRegistry);
	       }
	       //Session hibernateSession = getCurrentSession();
	       //return hibernateSession;
		}
		catch (Throwable ex) {
           throw new ExceptionInInitializerError(ex);
		}
	}
		
	public static SessionFactory getSessionFactory()
	{
		return factory;
	}
	
	/*public static Session getCurrentSession()
	{
		return factory.getCurrentSession();
	}*/
}
