package br.com.rti.alpha.controle;

import java.util.Comparator;

import br.com.rti.alpha.modelo.amostra.Amostra;
import br.com.rti.alpha.modelo.amostra.Laudos;
import br.com.rti.alpha.modelo.amostra.PlanoTrabalho;
import br.com.rti.alpha.modelo.amostra.TipoColeta;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;
import br.com.rti.alpha.modelo.ativo.Oleo;
import br.com.rti.alpha.modelo.ativo.TipoCompartimento;
import br.com.rti.alpha.modelo.pessoa.Funcao;
import br.com.rti.alpha.modelo.pessoa.Pessoa;
import br.com.rti.alpha.modelo.supervisao.Frota;
import br.com.rti.alpha.modelo.supervisao.Supervisao;
import org.apache.commons.lang3.builder.CompareToBuilder;

public class Ordenar implements Comparator
{	
	private int result = 0;
	
	private boolean descending = false;
	
	public boolean isDescending() 
	{
		return descending;
	}
	public void setDescending(boolean descending) 
	{
		this.descending = descending;
	}
	
	@Override
	public int compare(Object o1, Object o2) 
	{
		// TODO Auto-generated method stub
		
		if ( (o1 instanceof Pessoa) && (o2 instanceof Pessoa) )
		{
			Pessoa p1 = (Pessoa) o1;
			Pessoa p2 = (Pessoa) o2;
			
			result = p1.getNome().compareToIgnoreCase(p2.getNome());
		}
		
		if ( (o1 instanceof Funcao) && (o2 instanceof Funcao) )
		{
			Funcao f1 = (Funcao) o1;
			Funcao f2 = (Funcao) o2;
			
			result = f1.getFuncao().compareToIgnoreCase(f2.getFuncao());
		}
		
		if ( (o1 instanceof TipoCompartimento) && (o2 instanceof TipoCompartimento) )
		{
			TipoCompartimento tc1 = (TipoCompartimento) o1;
			TipoCompartimento tc2 = (TipoCompartimento) o2;
			
			result = tc1.getDescricao().compareToIgnoreCase(tc2.getDescricao());
		}
		
		if ( (o1 instanceof Compartimento) && (o2 instanceof Compartimento) )
		{
			Compartimento c1 = (Compartimento) o1;
			Compartimento c2 = (Compartimento) o2;
			
			result = c1.getTag().compareToIgnoreCase(c2.getTag());
		}
		
		if ( (o1 instanceof Ativo) && ( o2 instanceof Ativo) )
		{
			Ativo a1 = (Ativo) o1;
			Ativo a2 = (Ativo) o2;
			
			result = a1.getTag().compareToIgnoreCase(a2.getTag());
		}
		
		if ( (o1 instanceof Frota) && (o2 instanceof Frota) )
		{
			Frota f1 = (Frota) o1;
			Frota f2 = (Frota) o2;
			
			result = f1.getDescricao().compareToIgnoreCase(f2.getDescricao());
		}
		
		if ( (o1 instanceof Supervisao) && (o2 instanceof Supervisao) )
		{
			Supervisao s1 = (Supervisao) o1;
			Supervisao s2 = (Supervisao) o2;
			
			result = s1.getDescricao().compareToIgnoreCase(s2.getDescricao());
		}
		
		if ( (o1 instanceof Oleo) && (o2 instanceof Oleo) )
		{
			Oleo oleo1 = (Oleo) o1;
			Oleo oleo2 = (Oleo) o2;
			
			result = oleo1.getReferencia().compareToIgnoreCase(oleo2.getReferencia());
		}
		
		if ( (o1 instanceof TipoColeta) && (o2 instanceof TipoColeta) )
		{
			TipoColeta tc1 = (TipoColeta) o1;
			TipoColeta tc2 = (TipoColeta) o2;
			
			result = tc1.getDescricao().compareToIgnoreCase(tc2.getDescricao());			
		}
		
		if ( (o1 instanceof PlanoTrabalho) && (o2 instanceof PlanoTrabalho) )
		{
			PlanoTrabalho p1 = (PlanoTrabalho) o1;
			PlanoTrabalho p2 = (PlanoTrabalho) o2;
			
			result = p1.getPlano() < p2.getPlano() ? -1 : 1;
		}
		
		if ( (o1 instanceof Amostra) && (o2 instanceof Amostra) )
		{
			Amostra a1 = (Amostra) o1;
			Amostra a2 = (Amostra) o2;
			
			if ( this.descending )
			{				
				//if ( a2.getId() < a1.getId() )
					//result = a2.getDataAmostra().compareTo(a1.getDataAmostra());
				//else
					//result = a2.getDataAmostra().compareTo(a1.getDataAmostra());		
				return new CompareToBuilder().append(a2.getDataAmostra(), a1.getDataAmostra()).append(a2.getId(), a1.getId()).toComparison();
			}
			else			
				result = a1.getDataAmostra().compareTo(a2.getDataAmostra());			
		}
		
		if ( (o1 instanceof Laudos) && (o2 instanceof Laudos) )
		{
			Laudos l1 = (Laudos) o1;
			Laudos l2 = (Laudos) o2;
			
			if ( this.descending )
				result = l2.getId() > l1.getId() ? 1 : -1;
			else
				result = l1.getId() < l2.getId() ? -1 : 1;
		}
	
		return result;
	}
}
