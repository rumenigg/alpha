package br.com.rti.alpha.viewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import br.com.rti.alpha.controle.AImageConverter;
import br.com.rti.alpha.controle.FarolImageConverter;
import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.controle.ToolTipTextConverter;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.supervisao.Frota;

public class FarolAtivoVM 
{
	private int idFrota;
	private Frota selectedFrota;
	private List<Ativo> allAtivo;
	
	private Converter converter = new AImageConverter();
	private Converter farolConverter = new FarolImageConverter();
	private Converter toolTipTextConverter = new ToolTipTextConverter();
	
	public Frota getSelectedFrota() {
		return selectedFrota;
	}
	public void setSelectedFrota(Frota selectedFrota) {
		this.selectedFrota = selectedFrota;
	}
	public List<Ativo> getAllAtivo() {
		return allAtivo;
	}
	public void setAllAtivo(List<Ativo> allAtivo) {
		this.allAtivo = allAtivo;
	}
	public Converter getConverter() {
		return converter;
	}
	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	public Converter getFarolConverter() {
		return farolConverter;
	}
	public void setFarolConverter(Converter farolConverter) {
		this.farolConverter = farolConverter;
	}
	public Converter getToolTipTextConverter() {
		return toolTipTextConverter;
	}
	public void setToolTipTextConverter(Converter toolTipTextConverter) {
		this.toolTipTextConverter = toolTipTextConverter;
	}
	
	@GlobalCommand
	@NotifyChange({"allAtivo", "selectedFrota"})
	public void atualizaAllAtivo()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.selectedFrota = daof.getFrotaDAO().procura(idFrota);
		
		this.allAtivo = new ArrayList<Ativo>();
		this.allAtivo.addAll(this.selectedFrota.getAtivo());
		
		Collections.sort(this.allAtivo, new Ordenar());
	}
	
	@AfterCompose
	public void afterCompose(@ExecutionArgParam("frota") int id)
	{
		this.idFrota = id;
		this.atualizaAllAtivo();
	}
	
	@Command
	public void showCompartimentos(@BindingParam("ativo") int id, @BindingParam("objeto") Ativo a)
	{
		if ( a.getCompartimento().isEmpty() )
			Messagebox.show("O Ativo selecionado não possui compartimentos associados.", "Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
		else
		{
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ativo", id);
			Executions.createComponents("/zk/farol/farolCompartimento.zul", null, map);
		}
	}
}
