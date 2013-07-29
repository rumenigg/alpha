package br.com.rti.alpha.viewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;

import br.com.rti.alpha.controle.FarolImageConverter;
import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.Amostra;
import br.com.rti.alpha.modelo.ativo.Compartimento;

public class FarolAmostraVM 
{
	private int idCompartimento;
	
	private Amostra selectedAmostra = new Amostra();
	private List<Amostra> allAmostra = new ArrayList<Amostra>();
	
	private Converter farolConverter = new FarolImageConverter();
	
	public Amostra getSelectedAmostra() {
		return selectedAmostra;
	}
	public void setSelectedAmostra(Amostra selectedAmostra) {
		this.selectedAmostra = selectedAmostra;
	}
	public List<Amostra> getAllAmostra() {
		return allAmostra;
	}
	public void setAllAmostra(List<Amostra> allAmostra) {
		this.allAmostra = allAmostra;
	}		
	public Converter getFarolConverter() {
		return farolConverter;
	}
	public void setFarolConverter(Converter farolConverter) {
		this.farolConverter = farolConverter;
	}
	
	
	@GlobalCommand
	@NotifyChange({"allAmostra","farolConverter"})
	public void atualizaFarolAllAmostra()
	{
		this.allAmostra = null;
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		Compartimento comp = daof.getCompartimentoDAO().procura(this.idCompartimento);
		
		this.allAmostra = new ArrayList<Amostra>();
		
		this.allAmostra.addAll( comp.getAmostra() );
		
		Ordenar o = new Ordenar();
		o.setDescending(true);
		Collections.sort(this.allAmostra, o);
		
		o = null;
		daof = null;
		
		//BindUtils.postNotifyChange(null, null, this, "allAmostra");
	}
	
	@AfterCompose
	public void afterCompose(@ExecutionArgParam("compartimento") int id)
	{
		this.idCompartimento = id;
		
		this.atualizaFarolAllAmostra();
	}


}
