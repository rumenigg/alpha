package br.com.rti.alpha.controle;

import java.util.Set;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.Amostra;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;
import br.com.rti.alpha.modelo.supervisao.Frota;
import br.com.rti.alpha.modelo.supervisao.Supervisao;

public class ToolTipTextConverter implements Converter
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
		String tooltiptext = "";
		String resultado = verificarFarol(objeto, id);
		
		/*if ( resultado.equals("semamostra") || resultado.equals("semanalise") )
			tooltiptext = "Não existe amostra ou análise concluída para esta Supervisão";
		else
			if ( resultado.equals("normal") )
				tooltiptext = "A Supervisão encontra-se em situação normal";
			else
				if ( resultado.equals("anormal") )
					tooltiptext = "A Supervisão apresenta irregularidades";
				else
					if ( resultado.equals("critico") )
						tooltiptext = "Atenção gerente, a Supervisão encontra-se em nível crítico";		
		
		return tooltiptext;*/
		return resultado;
	}

	public String verificarFarol(String objeto, int id)
	{
		String situacao = "";	
		
		if ( objeto.equals("supervisao") )
		{
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
			
			Supervisao supervisao = daof.getSupervisaoDAO().procura(id);
			
			Set<Frota> allFrota = supervisao.getFrota();
			if ( allFrota.isEmpty() )
				return situacao = "Não existe amostra ou análise concluída para esta Supervisão";
			else
				for (Frota frota : allFrota)
				{
					Set<Ativo> allAtivo = frota.getAtivo();
					if ( allAtivo.isEmpty() )
						return situacao = "Não existe amostra ou análise concluída para esta Supervisão";
					else
						for ( Ativo ativo : allAtivo)
						{			
							Set<Compartimento> allCompartimento = ativo.getCompartimento();
							if ( allCompartimento.isEmpty() )
								return situacao = "Não existe amostra ou análise concluída para esta Supervisão";
							else
								for (Compartimento compartimento : allCompartimento)
								{
									Set<Amostra> allAmostra = compartimento.getAmostra();
									if ( allAmostra.isEmpty() )
										return situacao = "Não existe amostra ou análise concluída para esta Supervisão";
									else
									{
										for (Amostra amostra : allAmostra)
										{
											if ( amostra.getSituacao().isEmpty() )											
												return situacao = "Não existe amostra ou análise concluída para esta Supervisão";																		
											else
											{
												if ( amostra.getVistoriado() != null)
												{
													if (amostra.getVistoriado().equals("s") )
														return situacao = "A Supervisão encontra-se em situação normal";
													else
														return situacao = "A Supervisão encontra-se em situação " + amostra.getSituacao();
												}
												else 
													return situacao = "A Supervisão encontra-se em situação " + amostra.getSituacao();
												//System.out.println("\n--> Passo 3");
												//System.out.println("\n--> Situação: " + situacao);
											}
										}
									}
								}
						}
				}
			daof.close();
		}	
		if ( objeto.equals("frota") )
		{
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
			
			Supervisao supervisao = daof.getSupervisaoDAO().procura(id);
			
			Set<Frota> allFrota = supervisao.getFrota();
			if ( allFrota.isEmpty() )
				return situacao = "Não existe amostra ou análise concluída para esta Frota";
			else
				for (Frota frota : allFrota)
				{
					Set<Ativo> allAtivo = frota.getAtivo();
					if ( allAtivo.isEmpty() )
						return situacao = "Não existe amostra ou análise concluída para esta Frota";
					else
						for ( Ativo ativo : allAtivo)
						{			
							Set<Compartimento> allCompartimento = ativo.getCompartimento();
							if ( allCompartimento.isEmpty() )
								return situacao = "Não existe amostra ou análise concluída para esta Frota";
							else
								for (Compartimento compartimento : allCompartimento)
								{
									Set<Amostra> allAmostra = compartimento.getAmostra();
									if ( allAmostra.isEmpty() )
										return situacao = "Não existe amostra ou análise concluída para esta Frota";
									else
									{
										for (Amostra amostra : allAmostra)
										{
											if ( amostra.getSituacao().isEmpty() )											
												return situacao = "Não existe amostra ou análise concluída para esta Frota";																		
											else
											{
												if ( amostra.getVistoriado() != null)
												{
													if (amostra.getVistoriado().equals("s") )
														return situacao = "A Frota encontra-se em situação normal";
													else
														return situacao = "A Frota encontra-se em situação " + amostra.getSituacao();
												}
												else 
													return situacao = "A Frota encontra-se em situação " + amostra.getSituacao();
												//System.out.println("\n--> Passo 3");
												//System.out.println("\n--> Situação: " + situacao);
											}
										}
									}
								}
						}
				}
			daof.close();
		}	
		if ( objeto.equals("ativo") )
		{
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
			
			Ativo ativo = daof.getAtivoDAO().procura(id);
			
			Set<Compartimento> allCompartimento = ativo.getCompartimento();
			if ( allCompartimento.isEmpty() )
				return situacao = "Não existe amostra ou análise concluída para esta Ativo";
			else
				for (Compartimento compartimento : allCompartimento)
				{
					Set<Amostra> allAmostra = compartimento.getAmostra();
					if ( allAmostra.isEmpty() )
						return situacao = "Não existe amostra ou análise concluída para esta Ativo";
					else
					{
						for (Amostra amostra : allAmostra)
						{
							if ( amostra.getSituacao().isEmpty() )
								return situacao = "Não existe amostra ou análise concluída para esta Ativo";	
							else
							{
								if ( amostra.getVistoriado() != null)
								{
									if (amostra.getVistoriado().equals("s") )
										return situacao = "O Ativo encontra-se em situação normal";
									else
										return situacao = "O Ativo encontra-se em situação " + amostra.getSituacao();
								}
								else 
									return situacao = "O Ativo encontra-se em situação " + amostra.getSituacao();
									//System.out.println("\n--> Passo 3");
									//System.out.println("\n--> Situação: " + situacao);
							}
						}
					}
				}
			daof.close();
		}		
		if ( objeto.equals("compartimento") )
		{
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
			
			Compartimento compartimento = daof.getCompartimentoDAO().procura(id);
			
			Set<Amostra> allAmostra = compartimento.getAmostra();
			if ( allAmostra.isEmpty() )
				return situacao = "Não existe amostra ou análise concluída para este Compartimento";
			else
			{
				for (Amostra amostra : allAmostra)
				{
					if ( amostra.getSituacao().isEmpty() )
						return situacao = "Não existe amostra ou análise concluída para esta Compartimento";
					else
					{
						if ( amostra.getVistoriado() != null)
						{
							if (amostra.getVistoriado().equals("s") )
								return situacao = "O Compartimento encontra-se em situação normal";
							else
								return situacao = "O Compartimento encontra-se em situação " + amostra.getSituacao();
						}
						else 
							return situacao = "O Compartimento encontra-se em situação " + amostra.getSituacao();
							//System.out.println("\n--> Passo 3");
							//System.out.println("\n--> Situação: " + situacao);
					}
				}
			}			
			daof.close();
		}	
		return situacao;
	}
}
