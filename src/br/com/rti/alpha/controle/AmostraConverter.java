package br.com.rti.alpha.controle;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Textbox;

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
						return style = "doublespinwarning";
				}			
				if ( tipoElemento.equals("oxidacao") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getOxidacao() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("nitracao") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getNitracao() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("sulfatacao") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getSulfatacao() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("tbn") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getTbn() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("viscosidade") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getViscosidade() )
						return style = "doublespinwarning";
				}				
				if ( tipoElemento.equals("agua") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getAgua() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("st") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getSt() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("sul") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getSul() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("fe") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getFe() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("cu") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getCu() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("cr") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getCr() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("pb") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getPb() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("sn") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getSn() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("mo") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getMo() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("ni") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getNi() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("ag") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getAg() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("al") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getAl() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("si") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getSi() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("na") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getNa() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("k") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getK() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("ca") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getCa() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("p") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getP() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("zn") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getZn() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("mg") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getMg() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("b") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getB() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("ba") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getBa() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("w") )
				{
					Textbox ts = (Textbox) arg1;
					String valor = ts.getValue();
					if ( valor.equalsIgnoreCase("e") )
						return style = "textboxwarning";
				}
				if ( tipoElemento.equals("f") )
				{
					Textbox ts = (Textbox) arg1;
					String valor = ts.getValue();
					if ( valor.equalsIgnoreCase("e") )
						return style = "textboxwarning";
				}
				if ( tipoElemento.equals("diesel") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getDiesel() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("sddp") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getZddp() )
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("iso4u") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getIso4u() )
						return style = "doublespinwarning";
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
						return style = "doublespinwarning";
				}
				if ( tipoElemento.equals("norma14u") )
				{
					Doublespinner ds = (Doublespinner) arg1;
					double valor = ds.getValue();
					if ( valor > elemento.getNorma14u() )
						return style = "doublespinwarning";
				}
			}
			if ( arg0 instanceof Analise )
			{
				Analise analise = (Analise) arg0;
				if ( tipoElemento.equalsIgnoreCase("combustivel") )
				{					
					if ( analise.getCombustivel() != null)
					{
						if ( analise.getCombustivel().equalsIgnoreCase("s") )
							return style="comboboxwarning";
					}
				}
				if ( tipoElemento.equals("oleoescuro") )
				{
					if ( analise.getOleoescuro() != null )
					{
						if ( analise.getOleoescuro().equals("s") )
							return style = "comboboxwarning";
					}
				}
				if ( tipoElemento.equals("impureza") )
				{
					if ( analise.getImpureza() != null )
					{
						if ( analise.getImpureza().equals("s") )
							return style = "comboboxwarning";
					}
				}
				if ( tipoElemento.equals("limalha") )
				{
					if ( analise.getLimalha() != null )
					{
						if ( analise.getLimalha().equals("s") )
							return style = "comboboxwarning";
					}
				}
				if ( tipoElemento.equals("silica") )
				{
					if ( analise.getSilica() != null )
					{
						if ( analise.getSilica().equals("s") )
							return style = "comboboxwarning";
					}
				}
				if ( tipoElemento.equals("agua") )
				{
					if ( analise.getAgua() != null )
					{
						if ( analise.getAgua().equals("s") )
							return style = "comboboxwarning";
					}
				}
			}
		}
		
		return style;
	}

}
