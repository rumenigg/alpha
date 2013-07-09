package br.com.rti.alpha.controle;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Image;

public class AImageConverter implements Converter 
{
	private AImage aimg = null;
	private Image img;
	
	
	@Override
	public Object coerceToBean(Object arg0, Component arg1, BindContext arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1, BindContext arg2) {
		// TODO Auto-generated method stub
		String tipo = (String) arg2.getConverterArg("tipo");
		String foto = (String) arg0;
		String path = "";
		if ( arg0 != null && !foto.isEmpty() )
		{
			if ( tipo.equals("thumb") )
			{
				String extensao = foto.substring(foto.length()-4, foto.length());
				String nome = foto.substring(0, foto.length()-4);
				path = nome+"_thumb"+extensao;
			}
			else
				path = foto.toString();			
		}
		else
		{
			img = new Image("/img/hydro_100x100.png");
			aimg = (AImage) img.getContent();
		}
		
		
		try 
		{
			if ( !path.isEmpty() )
				aimg = new AImage(path);
		} 
		catch (FileNotFoundException f)
		{
			f.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  aimg;
	}

}
