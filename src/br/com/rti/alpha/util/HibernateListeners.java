package br.com.rti.alpha.util;

import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppCleanup;
import org.zkoss.zk.ui.util.WebAppInit;

public class HibernateListeners implements WebAppInit, WebAppCleanup {
	 
    public void init(WebApp webapp) throws Exception {
        //initialize Hibernate
        HibernateUtil.getSessionFactory();
    }
 
    public void cleanup(WebApp webapp) throws Exception {
        //Close Hibernate
        HibernateUtil.getSessionFactory().close();
    }
 
}
