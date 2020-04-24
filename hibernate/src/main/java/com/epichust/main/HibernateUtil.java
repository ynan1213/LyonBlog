package com.epichust.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
	private static Configuration cfg;
	private static SessionFactory sessionFactory;
	private static Session session;
	
	static
	{
		cfg = new Configuration().configure();
		sessionFactory = cfg.buildSessionFactory();
		session = sessionFactory.openSession();
        session.getTransaction().begin();
	}
	
	public static Session openSesson(){
        return session;
    }

    public static void Commit(){
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
