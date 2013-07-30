package br.com.rti.alpha.viewModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import br.com.rti.alpha.controle.AImageConverter;
import br.com.rti.alpha.controle.FarolImageConverter;
import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.controle.ToolTipTextConverter;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.supervisao.Supervisao;

public class FarolPrincipalVM 
{
	//@Wire
	//private Div farolPrincipal;
	
	private Supervisao selectedSupervisao;
	private List<Supervisao> allSupervisao;
	
	private Converter converter = new AImageConverter();
	private Converter farolConverter = new FarolImageConverter();
	private Converter toolTipTextConverter = new ToolTipTextConverter();
	
	public Supervisao getSelectedSupervisao() {
		return selectedSupervisao;
	}

	public void setSelectedSupervisao(Supervisao selectedSupervisao) {
		this.selectedSupervisao = selectedSupervisao;
	}

	public List<Supervisao> getAllSupervisao() {
		return allSupervisao;
	}

	public void setAllSupervisao(List<Supervisao> allSupervisao) {
		this.allSupervisao = allSupervisao;
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

	/*
	 * M�todo carrega todos as informa��es referentes a Supervis�o do Banco de Dados.
	 */	
	@GlobalCommand
	@NotifyChange("allSupervisao")
	public void atualizaAllSupervisao()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allSupervisao = daof.getSupervisaoDAO().listaTudo();
		
		Collections.sort(this.allSupervisao, new Ordenar());
		
		daof = null;
		
		BindUtils.postNotifyChange(null, null, this, "allSupervisao");
	}
	
	@Init
	public void init()
	{
		this.atualizaAllSupervisao();
		
		//this.mudarFarol();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		//Selectors.wireComponents(view, this, false);
		
		//this.mudarFarol();
		
	}
	
	@Command
	public void showFrotas(@BindingParam("supervisao") int id, @BindingParam("objeto") Supervisao s)	
	{
		if ( s.getFrota().isEmpty() )
			Messagebox.show("A Supervis�o selecionada n�o possui frotas associadas.",	"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
		else
		{
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("supervisao", id);
			Executions.createComponents("/zk/farol/farolFrota.zul", null, map);
		}
	}
}
