package br.com.rti.alpha.controle;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Textbox;

import br.com.rti.alpha.modelo.amostra.Amostra;
import br.com.rti.alpha.modelo.amostra.Analise;
import br.com.rti.alpha.modelo.amostra.Elementos;

public class AmostraConverter implements Converter 
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
		String style = "";	
		
		String tipoElemento = (String) arg2.getConverterArg("elemento");
		
						
		if ( arg1 != null && arg0 != null)
		{
			if ( arg0 instanceof Elementos )
			{
				Elementos elemento = (Elementos) arg0;
				
				if ( tipoElemento.equals("fuligem") )
				{
					Doublespinner ds = (Doublespinner) arg1; 
					double valor = ds.getValue();						
					if ( valor > elemento.getFuligem() )
						style = "doublespinwarning";
				}			
				if ( tipoElemento.equals("oxidacao") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getOxidacao() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("nitracao") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getNitracao() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("sulfatacao") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getSulfatacao() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("tbn") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getTbn() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("viscosidade") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getViscosidade() )
						style = "doublespinwarning";
				}				
				if ( tipoElemento.equals("agua") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getAgua() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("st") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getSt() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("sul") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getSul() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("fe") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getFe() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("cu") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getCu() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("cr") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getCr() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("pb") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getPb() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("sn") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getSn() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("mo") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getMo() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("ni") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getNi() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("ag") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getAg() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("al") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getAl() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("si") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getSi() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("na") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getNa() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("k") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getK() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("ca") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getCa() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("p") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getP() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("zn") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getZn() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("mg") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getMg() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("b") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getB() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("ba") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getBa() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("w") )
				{
					Textbox ts = (Textbox) arg1;
					String valor = ts.getValue();
					if ( valor.equalsIgnoreCase("e") )
						style = "textboxwarning";
				}
				if ( tipoElemento.equals("f") )
				{
					Textbox ts = (Textbox) arg1;
					String valor = ts.getValue();
					if ( valor.equalsIgnoreCase("e") )
						style = "textboxwarning";
				}
				if ( tipoElemento.equals("diesel") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getDiesel() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("sddp") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getZddp() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("iso4u") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getIso4u() )
						style = "doublespinwarning";
				}	
				if ( tipoElemento.equals("iso6u") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getIso6u() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("iso14u") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getIso14u() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("filtro") )
				{
					Combobox cb = (Combobox) arg1;
					if ( cb.getSelectedItem() != null )
					{
						String valor = cb.getSelectedItem().getValue();
						if ( valor.equalsIgnoreCase("anormal") )
							style = "comboboxwarning";
					}
				}
				if ( tipoElemento.equals("norma4u") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getNorma4u() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("norma6u") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getNorma6u() )
						style = "doublespinwarning";
				}
				if ( tipoElemento.equals("norma14u") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getNorma14u() )
						style = "doublespinwarning";
				}
			}
			if ( arg0 instanceof Analise )
			{
				Analise analise = (Analise) arg0;
				if ( tipoElemento.equals("combustivel") )
				{					
					if ( analise.getCombustivel() != null)
					{
						if ( analise.getCombustivel().equals("s"));
							style = "comboboxwarning";
					}
				}
				if ( tipoElemento.equals("oleoescuro") )
				{
					if ( analise.getOleoescuro() != null )
					{
						if ( analise.getOleoescuro().equals("s") )
							style = "comboboxwarning";
					}
				}
				if ( tipoElemento.equals("impureza") )
				{
					if ( analise.getImpureza() != null )
					{
						if ( analise.getImpureza().equals("s") )
							style = "comboboxwarning";
					}
				}
				if ( tipoElemento.equals("limalha") )
				{
					if ( analise.getLimalha() != null )
					{
						if ( analise.getLimalha().equals("s") )
							style = "comboboxwarning";
					}
				}
				if ( tipoElemento.equals("silica") )
				{
					if ( analise.getSilica() != null )
					{
						if ( analise.getSilica().equals("s") )
							style = "comboboxwarning";
					}
				}
				if ( tipoElemento.equals("agua") )
				{
					if ( analise.getAgua() != null )
					{
						if ( analise.getAgua().equals("s") )
							style = "comboboxwarning";
					}
				}
			}
		}
		
		return style;
	}

}
