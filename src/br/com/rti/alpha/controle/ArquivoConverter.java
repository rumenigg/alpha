package br.com.rti.alpha.controle;

import java.io.File;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

public class ArquivoConverter implements Converter 
{

	@Override
	public Object coerceToBean(Object arg0, Component arg1, BindContext arg2) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1, BindContext arg2) 
	{
		// TODO Auto-generated method stub
		String path = (String) arg0;
		
		if ( arg0 != null )
		{
			File file = new File(path);
			return file.getName();
		}
		
		return null;
	}
}
