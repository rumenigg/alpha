package br.com.rti.alpha.viewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.com.rti.alpha.controle.AImageConverter;
import br.com.rti.alpha.controle.FarolImageConverter;
import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.controle.ToolTipTextConverter;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;

public class FarolCompartimentoVM 
{
	private int idAtivo;
	private Ativo selectedAtivo;
	private List<Compartimento> allCompartimento;
	
	private Converter converter = new AImageConverter();
	private Converter farolConverter = new FarolImageConverter();
	private Converter toolTipTextConverter = new ToolTipTextConverter();
	public Ativo getSelectedAtivo() {
		return selectedAtivo;
	}
	public void setSelectedAtivo(Ativo selectedAtivo) {
		this.selectedAtivo = selectedAtivo;
	}
	public List<Compartimento> getAllCompartimento() {
		return allCompartimento;
	}
	public void setAllCompartimento(List<Compartimento> allCompartimento) {
		this.allCompartimento = allCompartimento;
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
	@NotifyChange({"allCompartimento", "selectedAtivo"})
	public void atualizaAllCompartimento()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.selectedAtivo = daof.getAtivoDAO().procura(idAtivo);
		
		this.allCompartimento = new ArrayList<Compartimento>();
		
		this.allCompartimento.addAll(this.selectedAtivo.getCompartimento());
		Collections.sort(this.allCompartimento, new Ordenar());		
		
		BindUtils.postNotifyChange(null, null, this, "allCompartimento");
	}

	@AfterCompose
	public void afterCompose(@ExecutionArgParam("ativo") int id)
	{
		this.idAtivo = id;
		
		this.atualizaAllCompartimento();
	}
	
	@Command
	public void showAmostra(@BindingParam("compartimento") int id, @BindingParam("objeto") Compartimento c)
	{
		if ( c.getAmostra().isEmpty() )
			Messagebox.show("O Compartimento selecionado não possui amostras associadas.", "Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
		else
		{
			
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("compartimento", id);
			//Executions.createComponents("/zk/amostra/farolCompartimento.zul", null, map);
			
			final Window winAmostra = (Window) Executions.createComponents("/zk/farol/farolAmostra.zul", null, map);		
						
			//winAmostra.setVisible(true);
			//winAmostra.setWidth("100%");
			//winAmostra.setHeight("100%");
 			winAmostra.setId("winAmostra");
 			winAmostra.setClosable(true);
 			winAmostra.setMaximizable(true);
 			winAmostra.setWidth("100%");
 			winAmostra.setMode("modal");
 						
 			final HashMap<String, Object> args = new HashMap<String, Object>();
 			args.put("selectedCompartimento", c);
 			args.put("readOnly", true);
 			BindUtils.postGlobalCommand(null, null, "showAmostrasCompartimento", args);
 			
 			//Map<String, Object> args2 = new HashMap<String, Object>();
 			//args2.put("selectedCompartimento", c);
 			//BindUtils.postGlobalCommand(null, null, "atualizaFarolAllAmostra", args);
		}
	}	
	
	@Command
	public void showLaudos(@BindingParam("objeto") Object obj)
	{
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("objeto", obj);
		
		final Window winLaudo = (Window) Executions.createComponents("/zk/farol/farolLaudos.zul", null, map);
		winLaudo.setId("winLaudo");
		winLaudo.setClosable(true);
		winLaudo.setMaximizable(true);
		winLaudo.setMode("modal");
		
		final HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("selectedLaudo", obj);
		args.put("readOnly", true);
		BindUtils.postGlobalCommand(null, null, "showLaudosObjetos", args);
	}
}
