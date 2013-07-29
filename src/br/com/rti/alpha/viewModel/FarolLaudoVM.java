package br.com.rti.alpha.viewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;

import br.com.rti.alpha.controle.FarolImageConverter;
import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.Amostra;
import br.com.rti.alpha.modelo.amostra.Laudos;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;

public class FarolLaudoVM 
{
	private int idLaudo;
	
	private Object obj;
	
	private Laudos selectedLaudo = new Laudos();
	private List<Laudos> allLaudos = new ArrayList<Laudos>();
	
	private Converter farolConverter = new FarolImageConverter();
	
	public Laudos getSelectedLaudo() {
		return selectedLaudo;
	}

	public void setSelectedLaudo(Laudos selectedLaudo) {
		this.selectedLaudo = selectedLaudo;
	}

	public List<Laudos> getAllLaudos() {
		return allLaudos;
	}

	public void setAllLaudos(List<Laudos> allLaudos) {
		this.allLaudos = allLaudos;
	}

	public Converter getFarolConverter() {
		return farolConverter;
	}

	public void setFarolConverter(Converter farolConverter) {
		this.farolConverter = farolConverter;
	}

	@GlobalCommand
	@NotifyChange({"allLaudos","farolConverter"})
	public void atualizaFarolAllLaudos()
	{
		this.allLaudos = null;
		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allLaudos = new ArrayList<Laudos>();
		
		if ( obj instanceof Ativo )
		{
			Ativo ativo = (Ativo) obj;
			ativo = daof.getAtivoDAO().procura(ativo.getId());
			this.allLaudos.addAll( ativo.getLaudos() );
		}
		if ( obj instanceof Compartimento)
		{
			Compartimento compartimento = (Compartimento) obj;
			compartimento = daof.getCompartimentoDAO().procura(compartimento.getId());
			this.allLaudos.addAll( compartimento.getLaudos() );
		}			
		
		Ordenar o = new Ordenar();
		o.setDescending(true);
		Collections.sort(this.allLaudos, o);
		
		o = null;
		daof = null;
		
		//BindUtils.postNotifyChange(null, null, this, "allAmostra");
	}
	
	@AfterCompose
	public void afterCompose(@ExecutionArgParam("objeto") Object obj)
	{
		this.obj = obj;
		
		this.atualizaFarolAllLaudos();
	}
	
	
}
