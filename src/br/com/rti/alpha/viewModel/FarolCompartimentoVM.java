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
			
			//final HashMap<String, Object> map = new HashMap<String, Object>();
			//map.put("ativo", id);
			//Executions.createComponents("/zk/amostra/farolCompartimento.zul", null, map);
			
			final Window winAmostra = (Window) Executions.createComponents("/zk/farol/farolAmostra.zul", null, null);		
						
			//winAmostra.setVisible(true);
			//winAmostra.setWidth("100%");
			//winAmostra.setHeight("100%");
 			winAmostra.setId("winAmostra");
 			winAmostra.setClosable(true);
 			winAmostra.setMaximizable(true);
 			winAmostra.setMode("modal");
 						
 			final HashMap<String, Object> args = new HashMap<String, Object>();
 			args.put("selectedCompartimento", id);
 			args.put("readOnly", true);
 			BindUtils.postGlobalCommand(null, null, "showAmostrasCompartimento", args);
 			
 			Map<String, Object> args2 = new HashMap<String, Object>();
 			args2.put("selectedCompartimento", c);
 			BindUtils.postGlobalCommand(null, null, "showSelectedAmostrasCompartimento", args2);
		}
	}	
}
