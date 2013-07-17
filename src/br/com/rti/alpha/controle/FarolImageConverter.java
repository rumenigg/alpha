package br.com.rti.alpha.controle;

import java.util.ArrayList;
import java.util.List;
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
	private boolean compartimento = false;
	List<String> situacao;

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
		//this.verificarFarol(objeto, id);
		
		if ( !compartimento )
		{
			/*System.out.println("--> Situação - Qtd: " + this.situacao.size());
			for ( String s : this.situacao )
				System.out.println("--> Situação - Desc: " + s);*/
				
			if ( this.situacao.contains("critico") )//resultado.equalsIgnoreCase("critico") )
				return src = "/img/farol/farol-aceso_vermelho-l.png";
			else
				if ( this.situacao.contains("anormal") )//resultado.equalsIgnoreCase("anormal") )
					return src = "/img/farol/farol-aceso_amarelo-l.png";
				else
					if ( this.situacao.contains("normal") )//resultado.equalsIgnoreCase("normal") )
						return src = "/img/farol/farol-aceso_verde-l.png";
					else
						if ( this.situacao.contains("semamostra") || this.situacao.contains("semanalise") )//resultado.equals("semamostra") || resultado.equals("semanalise") )
							return src = "/img/farol/farol-desligado.png";
						
		}
		else
		{
			if ( resultado.equals("semamostra") || resultado.equals("semanalise") || resultado.equalsIgnoreCase("normal") )
				src = "/img/farol/farol_verde.png";
			else
				if ( resultado.equalsIgnoreCase("anormal") )
					src = "/img/farol/farol_amarelo.png";
				else		
					if ( resultado.equalsIgnoreCase("critico") )
						src = "/img/farol/farol_vermelho.png";
		}
	
		//System.out.println("\n--> Resultado: " + resultado);
		//System.out.println("\n--> Image Src: " + src);
		this.situacao.clear();
		this.situacao = null;
		return src;
	}
	
	public String verificarFarol(String objeto, int id)
	{
		String situacao = "";
		
		this.situacao = new ArrayList<String>();
		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		if ( objeto.equals("supervisao") )
		{
			Supervisao supervisao = daof.getSupervisaoDAO().procura(id);
		
			Set<Frota> allFrota = supervisao.getFrota();
			if ( allFrota.isEmpty() )
				this.situacao.add("semamostra");//situacao = "semamostra";
			else
				for (Frota frota : allFrota)
				{
					Set<Ativo> allAtivo = frota.getAtivo();
					if ( allAtivo.isEmpty() )
						this.situacao.add("semamostra");
					else
						for ( Ativo ativo : allAtivo)
						{				
							Set<Compartimento> allCompartimento = ativo.getCompartimento();
							if ( allCompartimento.isEmpty() )
								this.situacao.add("semamostra");//situacao = "semamostra";
							else
								for (Compartimento compartimento : allCompartimento)
								{
									Set<Amostra> allAmostra = compartimento.getAmostra();
									if ( allAmostra.isEmpty() )
										this.situacao.add("semamostra");//situacao = "semamostra";
									else
									{
										for (Amostra amostra : allAmostra)
										{
											//System.out.println("\n--> Passo 1");
											if ( amostra.getSituacao().isEmpty() )
											{
												this.situacao.add("semanalise");//situacao = "semanalise";
												//System.out.println("\n--> Passo 2");
											}							
											else
											{
												if ( amostra.getVistoriado() != null)
												{
													if (amostra.getVistoriado().equals("s") )
														this.situacao.add("normal");//situacao = "normal";
													else
														this.situacao.add(amostra.getSituacao());//situacao = amostra.getSituacao();
												}
												else 
													this.situacao.add(amostra.getSituacao());//situacao = amostra.getSituacao();
												//System.out.println("\n--> Passo 3");
												//System.out.println("\n--> Situação: " + situacao);
											}
										}
										//return situacao;
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
				this.situacao.add("semamostra");//situacao = "semamostra";
			else
			for ( Ativo ativo : allAtivo)
			{				
				Set<Compartimento> allCompartimento = ativo.getCompartimento();
				if ( allCompartimento.isEmpty() )
					this.situacao.add("semamostra");//situacao = "semamostra";
				else
				for (Compartimento compartimento : allCompartimento)
				{
					Set<Amostra> allAmostra = compartimento.getAmostra();
					if ( allAmostra.isEmpty() )
						this.situacao.add("semamostra");//situacao = "semamostra";
					else
					{
						for (Amostra amostra : allAmostra)
						{
							//System.out.println("\n--> Passo 1");
							if ( amostra.getSituacao().isEmpty() )
								{
								this.situacao.add("semanalise");//situacao = "semanalise";
									//System.out.println("\n--> Passo 2");
								}							
							else
							{
								if ( amostra.getVistoriado() != null)
								{
									if (amostra.getVistoriado().equals("s") )
										this.situacao.add("normal");//situacao = "normal";
									else
										this.situacao.add(amostra.getSituacao());//situacao = amostra.getSituacao();
								}
								else 
									this.situacao.add(amostra.getSituacao());//situacao = amostra.getSituacao();
								//System.out.println("\n--> Passo 3");
								//System.out.println("\n--> Situação: " + situacao);
							}
						}
						//return situacao;
					}
				}
			}		
		}
		if ( objeto.equals("ativo") )
		{
			Ativo ativo = daof.getAtivoDAO().procura(id);		
		
			Set<Compartimento> allCompartimento = ativo.getCompartimento();
			if ( allCompartimento.isEmpty() )
				this.situacao.add("semamostra");//situacao = "semamostra";
			else
			for (Compartimento compartimento : allCompartimento)
			{
				Set<Amostra> allAmostra = compartimento.getAmostra();
				if ( allAmostra.isEmpty() )
					this.situacao.add("semamostra");//situacao = "semamostra";
				else
				{
					for (Amostra amostra : allAmostra)
					{
						//System.out.println("\n--> Passo 1");
						if ( amostra.getSituacao().isEmpty() )
							{
							this.situacao.add("semanalise");//situacao = "semanalise";
								//System.out.println("\n--> Passo 2");
							}							
						else
						{
							if ( amostra.getVistoriado() != null)
							{
								if (amostra.getVistoriado().equals("s") )
									this.situacao.add("normal");//situacao = "normal";
								else
									this.situacao.add(amostra.getSituacao());//situacao = amostra.getSituacao();
							}
							else 
								this.situacao.add(amostra.getSituacao());//situacao = amostra.getSituacao();
							//System.out.println("\n--> Passo 3");
							//System.out.println("\n--> Situação: " + situacao);
						}
					}
					//return situacao;
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
						this.compartimento = true;
						if ( amostra.getVistoriado() != null)
						{
							if (amostra.getVistoriado().equals("s") )
								situacao = "normal";
							else
								situacao = amostra.getSituacao();
						}
						else 
							situacao = amostra.getSituacao();
						//System.out.println("\n--> Passo 3");
						//System.out.println("\n--> Situação: " + situacao);
					}
				}
				return situacao;
			}
		}
		if ( objeto.equals("amostra") )
		{
			Amostra amostra = daof.getAmostraDAO().procura(id);		
			System.out.println("No. da Amostra" + amostra.getId());	
			if ( amostra.getSituacao().equals("") )
			{
				situacao = "semanalise";
			}
			else
			{
				this.compartimento = true;
				if ( amostra.getVistoriado() != null)
				{
					if (amostra.getVistoriado().equals("s") )
						situacao = "normal";
					else
						situacao = amostra.getSituacao();
				}
				else 
					situacao = amostra.getSituacao();
			}
			return situacao;
		}		
		return situacao;
	}

}
