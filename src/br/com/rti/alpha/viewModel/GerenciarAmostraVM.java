package br.com.rti.alpha.viewModel;

import java.util.Collections;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.Amostra;

public class GerenciarAmostraVM 
{
	private Amostra selectedAmostra;
	private List<Amostra> allAmostra;
	
	
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
	
	@GlobalCommand
	@NotifyChange("allAmostra")
	public void atualizaAmostra()
	{
		this.allAmostra = null;
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allAmostra = daof.getAmostraDAO().listaTudo();
		
		Ordenar o = new Ordenar();
		o.setDescending(true);
		Collections.sort(this.allAmostra, o);
		
		o = null;
		daof = null;
	}
	
	@Init
	public void init()
	{
		this.selectedAmostra = new Amostra();
		
		this.atualizaAmostra();
	}
	
	@Command
	public void showSelectedAmostra()
	{
		
	}
}

