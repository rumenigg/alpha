package br.com.rti.alpha.controle;

import java.util.Set;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Image;

import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.Amostra;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;
import br.com.rti.alpha.modelo.supervisao.Frota;
import br.com.rti.alpha.modelo.supervisao.Supervisao;

public class FarolImageConverter implements Converter
{
	

	@Override
	public Object coerceToBean(Object arg0, Component arg1, BindContext arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1, BindContext arg2) {
		// TODO Auto-generated method stub
		String objeto = (String) arg2.getConverterArg("objeto");
		int id = (Integer) arg0;
		String src = "";
		
		
		String resultado = verificarFarol(objeto, id);
		
		if ( resultado.equals("semamostra") || resultado.equals("semanalise") )
			src = "/img/farol/farol-desligado.png";		
		else 
			if ( resultado.equalsIgnoreCase("normal") )
				src = "/img/farol/farol-aceso_verde-l.png";
			else
				if ( resultado.equalsIgnoreCase("anormal") )
					src = "/img/farol/farol-aceso_amarelo-l.png";
				else		
					if ( resultado.equalsIgnoreCase("critico") )
						src = "/img/farol/farol-aceso_vermelho-l.png";
	
		//System.out.println("\n--> Resultado: " + resultado);
		//System.out.println("\n--> Image Src: " + src);
		
		return src;
	}
	
	public String verificarFarol(String objeto, int id)
	{
		String situacao = "";
		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		if ( objeto.equals("supervisao") )
		{
			Supervisao supervisao = daof.getSupervisaoDAO().procura(id);
		
			Set<Frota> allFrota = supervisao.getFrota();
			if ( allFrota.isEmpty() )
				situacao = "semamostra";
			else
				for (Frota frota : allFrota)
				{
					Set<Ativo> allAtivo = frota.getAtivo();
					if ( allAtivo.isEmpty() )
						situacao = "semamostra";
					else
						for ( Ativo ativo : allAtivo)
						{				
							Set<Compartimento> allCompartimento = ativo.getCompartimento();
							if ( allCompartimento.isEmpty() )
								situacao = "semamostra";
							else
								for (Compartimento compartimento : allCompartimento)
								{
									Set<Amostra> allAmostra = compartimento.getAmostra();
									if ( allAmostra.isEmpty() )
										situacao = "semamostra";
									else
									{
										for (Amostra amostra : allAmostra)
										{
											//System.out.println("\n--> Passo 1");
											if ( amostra.getSituacao().isEmpty() )
											{
												situacao = "semanalise";
												//System.out.println("\n--> Passo 2");
											}							
											else
											{
												if ( amostra.getVistoriado() != null)
												{
													if (amostra.getVistoriado().equals("s") )
														return situacao = "normal";
													else
														return situacao = amostra.getSituacao();
												}
												else 
													return situacao = amostra.getSituacao();
												//System.out.println("\n--> Passo 3");
												//System.out.println("\n--> Situação: " + situacao);
											}
										}
									}
								}
						}
				}
		}
		if ( objeto.equals("frota") )
		{
			Frota frota = daof.getFrotaDAO().procura(id);		
		
			Set<Ativo> allAtivo = frota.getAtivo();
			if ( allAtivo.isEmpty() )
				situacao = "semamostra";
			else
			for ( Ativo ativo : allAtivo)
			{				
				Set<Compartimento> allCompartimento = ativo.getCompartimento();
				if ( allCompartimento.isEmpty() )
					situacao = "semamostra";
				else
				for (Compartimento compartimento : allCompartimento)
				{
					Set<Amostra> allAmostra = compartimento.getAmostra();
					if ( allAmostra.isEmpty() )
						situacao = "semamostra";
					else
					{
						for (Amostra amostra : allAmostra)
						{
							//System.out.println("\n--> Passo 1");
							if ( amostra.getSituacao().isEmpty() )
								{
									situacao = "semanalise";
									//System.out.println("\n--> Passo 2");
								}							
							else
							{
								if ( amostra.getVistoriado() != null)
								{
									if (amostra.getVistoriado().equals("s") )
										return situacao = "normal";
									else
										return situacao = amostra.getSituacao();
								}
								else 
									return situacao = amostra.getSituacao();
								//System.out.println("\n--> Passo 3");
								//System.out.println("\n--> Situação: " + situacao);
							}
						}
					}
				}
			}		
		}
		if ( objeto.equals("ativo") )
		{
			Ativo ativo = daof.getAtivoDAO().procura(id);		
		
			Set<Compartimento> allCompartimento = ativo.getCompartimento();
			if ( allCompartimento.isEmpty() )
				situacao = "semamostra";
			else
			for (Compartimento compartimento : allCompartimento)
			{
				Set<Amostra> allAmostra = compartimento.getAmostra();
				if ( allAmostra.isEmpty() )
					situacao = "semamostra";
				else
				{
					for (Amostra amostra : allAmostra)
					{
						//System.out.println("\n--> Passo 1");
						if ( amostra.getSituacao().isEmpty() )
							{
								situacao = "semanalise";
								//System.out.println("\n--> Passo 2");
							}							
						else
						{
							if ( amostra.getVistoriado() != null)
							{
								if (amostra.getVistoriado().equals("s") )
									return situacao = "normal";
								else
									return situacao = amostra.getSituacao();
							}
							else 
								return situacao = amostra.getSituacao();
							//System.out.println("\n--> Passo 3");
							//System.out.println("\n--> Situação: " + situacao);
						}
					}
				}
			}
		}		
		if ( objeto.equals("compartimento") )
		{
			Compartimento compartimento = daof.getCompartimentoDAO().procura(id);		
				
			Set<Amostra> allAmostra = compartimento.getAmostra();
			if ( allAmostra.isEmpty() )
				situacao = "semamostra";
			else
			{
				for (Amostra amostra : allAmostra)
				{
					//System.out.println("\n--> Passo 1");
					if ( amostra.getSituacao().isEmpty() )
						{
							situacao = "semanalise";
							//System.out.println("\n--> Passo 2");
						}							
					else
					{
						if ( amostra.getVistoriado() != null)
						{
							if (amostra.getVistoriado().equals("s") )
								return situacao = "normal";
							else
								return situacao = amostra.getSituacao();
						}
						else 
							return situacao = amostra.getSituacao();
						//System.out.println("\n--> Passo 3");
						//System.out.println("\n--> Situação: " + situacao);
					}
				}
			}
		}	
		
		return situacao;
	}

}
