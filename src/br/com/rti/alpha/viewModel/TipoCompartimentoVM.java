package br.com.rti.alpha.viewModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.Elementos;
import br.com.rti.alpha.modelo.ativo.Compartimento;
import br.com.rti.alpha.modelo.ativo.TipoCompartimento;

public class TipoCompartimentoVM 
{
	@Wire
	private Toolbarbutton tbtnNovoTipoCompartimento;
	
	private List<TipoCompartimento> allTipoCompartimento;
	private TipoCompartimento selectedTipoCompartimento;	
	
	private List<Compartimento> allCompartimento;
	
	private List<Elementos> allElementos;
	private Elementos selectedElementos;
	
	private boolean desativado = true;
	
	private int navegador = 0;
	
	//Getters and Setters of attributes
	public List<TipoCompartimento> getAllTipoCompartimento() {
		return allTipoCompartimento;
	}
	public void setAllTipoCompartimento(List<TipoCompartimento> allTipoCompartimento) {
		this.allTipoCompartimento = allTipoCompartimento;
	}
	public TipoCompartimento getSelectedTipoCompartimento() {
		return selectedTipoCompartimento;
	}
	public void setTipoCompartimento(TipoCompartimento tipoCompartimento) {
		this.selectedTipoCompartimento = tipoCompartimento;
	}	
	public boolean getDesativado()
	{
		return this.desativado;
	}
	public void setDesativado(boolean desativado)
	{
		this.desativado = desativado;
	}	
	public List<Elementos> getAllElementos() {
		return allElementos;
	}
	public void setAllElementos(List<Elementos> allElementos) {
		this.allElementos = allElementos;
	}
	public Elementos getSelectedElementos() {
		return selectedElementos;
	}
	public void setSelectedElementos(Elementos selectedElementos) {
		this.selectedElementos = selectedElementos;
	}
	@Init
	public void init()
	{
		this.selectedTipoCompartimento = new TipoCompartimento();
		this.atualizaAllTipoCompartimento();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		//Selectors.wireEventListeners(view, this);
		
		Clients.showNotification("Clique aqui para adicionar um novo Tipo de Compartimento", "info", this.tbtnNovoTipoCompartimento, "end_center", 3000);
	}
	
	@GlobalCommand
	@NotifyChange("allTipoCompartimento")
	public void atualizaAllTipoCompartimento()
	{
		this.allTipoCompartimento = null;
		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allTipoCompartimento = daof.getTipoCompartimentoDAO().listaTudo();
		
		//Coloca em ordem crescente a lista de tipos de compartimentos usando o objeto Ordenar
		Ordenar o = new Ordenar();
		Collections.sort(this.allTipoCompartimento, o);
		o = null;
		
		this.allCompartimento = daof.getCompartimentoDAO().listaTudo();
		
		//daof.close();
	}
	
	public void atualizaElementos()
	{
		this.allElementos = null;
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allElementos = daof.getElementosDAO().listaTudo();
		
		daof = null;
	}
	
	@Command
	@NotifyChange({"selectedTipoCompartimento","desativado"})
	public void novo()
	{
		this.desativado = false;
		this.selectedTipoCompartimento = null;
		this.selectedTipoCompartimento = new TipoCompartimento();
		
		this.selectedElementos = null;
		this.selectedElementos = new Elementos();
	}
	
	@Command
	@NotifyChange({"selectedTipoCompartimento","desativado"})
	public void submit()
	{
		try
		{
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
			
			daof.getElementosDAO().adiciona(this.selectedElementos);
			this.selectedTipoCompartimento.setElementos(this.selectedElementos);			
						
			daof.getTipoCompartimentoDAO().adiciona(this.selectedTipoCompartimento);
			
			daof.commit();
			
			Messagebox.show("O tipo de compartimento " + this.selectedTipoCompartimento.getDescricao().toUpperCase() + 
					"\nfoi adicionado ou atualizado com sucesso.", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);
			
			//Atualiza o combobox Tipo de Compartimento na janela Compartimento
			this.atualizaBindComponent("atualizaCompartimentoLists", "atualizaCompartimentoLists", this.selectedTipoCompartimento);
			//Atualiza a lista na aba Compartimento na janela de Cadastros;
			this.atualizaBindComponent("atualizaListas", "atualizaListas", this.selectedTipoCompartimento);
			this.atualizaAllTipoCompartimento();
			this.atualizaElementos();
		}
		catch(Exception e)
		{
			Messagebox.show("Problemas de conexão com o banco de dados.\nContate o administrador ou o desenvolvedor do sistema.",
					"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);
			
			e.printStackTrace();
		}
		this.desativado = true;
	}
	
	@Command
	@NotifyChange("selectedTipoCompartimento")
	public void excluir()
	{
		if ( this.existeCompartimento() )
		{
			Messagebox.show("Você não pode excluir o Tipo de Compartimento " + 
					selectedTipoCompartimento.getDescricao().toUpperCase() + 
					"\ndevido possuir compartimentos atribuidos à ele.\nPara excluí-lo você deve primeiro excluir os Compartimentos existentes.",
					"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.EXCLAMATION);	
		}
		else 
			 {
				try
				{
					Messagebox.show("Você realmente deseja excluir o Tipo de Compartimento " + this.selectedTipoCompartimento.getDescricao().toUpperCase() + "?",
							"Hydro - Projeto Alpha", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
							new EventListener<Event>()
							{
								public void onEvent(Event event) throws SQLException
								{
									if ( Messagebox.ON_YES.equals(event.getName()) )
									{
										try
										{
											DaoFactory daof = new DaoFactory();
											daof.beginTransaction();
											
											daof.getTipoCompartimentoDAO().remove(selectedTipoCompartimento);
											daof.commit();
											
											Messagebox.show("Tipo de compartimento excluído com sucesso.", "Hydro - Projeto Alpha", Messagebox.OK, 
													Messagebox.INFORMATION);
											
											//Atualiza o combobox Tipo de Compartimento na janela Compartimento
											atualizaBindComponent("atualizaCompartimentoLists", "atualizaCompartimentoLists", selectedTipoCompartimento);
											//Atualiza a lista na aba Compartimento na janela de Cadastros;
											atualizaBindComponent("atualizaListas", "atualizaListas", selectedTipoCompartimento);
											atualizaAllTipoCompartimento();
										}
										catch (Exception e)
										{
											Messagebox.show("Problemas de conexão com o banco de dados.\nContate o administrador ou o desenvolvedor do sistema",
												"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
											
											e.printStackTrace();
										}
										
																	
									}							
								}					
							});
				}
				catch (NullPointerException n)
				{
						Messagebox.show("Selecione um Tipo de Compartimento para a exclusão!", "Hydro - Projeto Alpha", 
								Messagebox.OK, Messagebox.EXCLAMATION);				
				}
			}				
		/*
		*/
	}
	
	public boolean existeCompartimento()
	{
		boolean test = false;
		for (Compartimento c : allCompartimento)
		{
			if ( c.getTipoCompartimento().equals(selectedTipoCompartimento))
				test = true;
		}
		
		return test;
	}
	
	@Command
	@NotifyChange({"selectedTipoCompartimento","desativado"})
	public void navegar(@BindingParam("acao") String acao)
	{	
		this.desativado = false;
		
		this.selectedTipoCompartimento = null;
		if ( !this.allTipoCompartimento.isEmpty() ){
		if ( acao.equals("primeiro") )
		{
			this.selectedTipoCompartimento = this.allTipoCompartimento.get(this.navegador = 0);
		}
		
		if ( acao.equals("anterior") )
		{
			if ( this.navegador > 0 )
			{
				this.selectedTipoCompartimento = this.allTipoCompartimento.get(--this.navegador);
			}
			else this.navegar("primeiro");
		}
		
		if ( acao.equals("proximo") )
		{
			if ( this.navegador < this.allTipoCompartimento.size()-1 )
			{
				this.selectedTipoCompartimento = this.allTipoCompartimento.get(++this.navegador);
			}
			else this.navegar("ultimo");
		}
		
		if ( acao.equals("ultimo") )
		{
			this.selectedTipoCompartimento = this.allTipoCompartimento.get(this.navegador = this.allTipoCompartimento.size()-1);
		}}
	}
	
	//Método utilizado para atualizar os dados dos comboboxes ou listas em outras janelas
	public void atualizaBindComponent(String bindingParam, String metodo, Object obj)
	{
		Map args = new HashMap();
		args.put(bindingParam, obj);
		BindUtils.postGlobalCommand(null, null, metodo, args);
	}
	
	@GlobalCommand
	@NotifyChange({"selectedTipoCompartimento","selectedElementos","desativado"})
	public void showSelectedTipoCompartimentoItem(@BindingParam("showSelectedTipoCompartimentoItem") int i ) throws IOException
	{
		this.desativado = false;
		
		this.selectedTipoCompartimento = null;
		//this.selectedPessoa = selectedPessoa;
		
		this.selectedTipoCompartimento = this.allTipoCompartimento.get( this.navegador = i );
		
		this.selectedElementos = this.selectedTipoCompartimento.getElementos();		
	}
}
