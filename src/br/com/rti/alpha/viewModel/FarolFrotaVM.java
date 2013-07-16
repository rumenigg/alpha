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
import br.com.rti.alpha.modelo.supervisao.Frota;
import br.com.rti.alpha.modelo.supervisao.Supervisao;

public class FarolFrotaVM 
{
	private int idSupervisao;
	private Supervisao selectedSupervisao;
	
	private List<Frota> allFrota;
	
	private Converter converter = new AImageConverter();
	private Converter farolConverter = new FarolImageConverter();
	private Converter toolTipTextConverter = new ToolTipTextConverter();
	
	
	public List<Frota> getAllFrota() {
		return allFrota;
	}
	public void setAllFrota(List<Frota> allFrota) {
		this.allFrota = allFrota;
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
	public Supervisao getSelectedSupervisao() {
		return selectedSupervisao;
	}
	public void setSelectedSupervisao(Supervisao selectedSupervisao) {
		this.selectedSupervisao = selectedSupervisao;
	}
	
	@GlobalCommand
	@NotifyChange({"allFrota","selectedSupervisao"})
	public void atualizaAllFrota()
	{
		DaoFactory daof = new DaoFactory();
		this.selectedSupervisao = daof.getSupervisaoDAO().procura(this.idSupervisao);
		this.allFrota = new ArrayList<Frota>();
		
		this.allFrota.addAll( selectedSupervisao.getFrota() );
		
		Collections.sort( allFrota, new Ordenar() );
	}
	
	@AfterCompose
	public void afterCompose(@ExecutionArgParam("supervisao") int id)
	{
		this.idSupervisao = id;
		
		this.atualizaAllFrota();
	}
	
	@Command
	public void showAtivos(@BindingParam("frota") int id, @BindingParam("objeto") Frota f)
	{
		if ( f.getAtivo().isEmpty() )
			Messagebox.show("A Frota selecionada não possui ativos associados.", "Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
		else
		{
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("frota", id);
			Executions.createComponents("/zk/farol/farolAtivo.zul", null, map);
		}
	}
}
