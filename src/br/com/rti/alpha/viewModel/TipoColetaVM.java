package br.com.rti.alpha.viewModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.PlanoTrabalho;
import br.com.rti.alpha.modelo.amostra.TipoColeta;
import br.com.rti.alpha.modelo.supervisao.Frota;

public class TipoColetaVM 
{
	@Wire
	private Toolbarbutton tbtnNovoTipoColeta;
	@Wire
	private Listbox lbPlano;
	@Wire
	private Listbox lbPlanoTipoColeta;
	
	//GroupsModelArray<Frota, Frota, Frota, Frota> groupModel;
	
	private ListModelList<PlanoTrabalho> planoDataModel;
	private ListModelList<PlanoTrabalho> planoTipoColetaDataModel;
	private List<PlanoTrabalho> removedPlanoList = new ArrayList<PlanoTrabalho>();
	
	private int navegador;
	
	private boolean desativado = true;
	
	private List<TipoColeta> allTipoColeta;
	private TipoColeta selectedTipoColeta;
	private List<PlanoTrabalho> allPlanoTrabalho;
	private PlanoTrabalho selectedPlanoTrabalho;
	private List<PlanoTrabalho> allPlanoTipoColeta = new ArrayList<PlanoTrabalho>();
	
	public boolean isDesativado() {
		return desativado;
	}
	public void setDesativado(boolean desativado) {
		this.desativado = desativado;
	}
	public List<TipoColeta> getAllTipoColeta() {
		return allTipoColeta;
	}
	public void setAllTipoColeta(List<TipoColeta> allTipoColeta) {
		this.allTipoColeta = allTipoColeta;
	}
	public TipoColeta getSelectedTipoColeta() {
		return selectedTipoColeta;
	}
	public void setSelectedTipoColeta(TipoColeta selectedTipoColeta) {
		this.selectedTipoColeta = selectedTipoColeta;
	}	
	public List<PlanoTrabalho> getAllPlanoTrabalho() {
		return allPlanoTrabalho;
	}
	public void setAllPlanoTrabalho(List<PlanoTrabalho> allPlanoTrabalho) {
		this.allPlanoTrabalho = allPlanoTrabalho;
	}
	public PlanoTrabalho getSelectedPlanoTrabalho() {
		return selectedPlanoTrabalho;
	}
	public void setSelectedPlanoTrabalho(PlanoTrabalho selectedPlanoTrabalho) {
		this.selectedPlanoTrabalho = selectedPlanoTrabalho;
	}
	
	@GlobalCommand
	public void atualizarTipoColeta()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allTipoColeta = daof.getTipoColetaDAO().listaTudo();
		
		Ordenar o = new Ordenar();
		Collections.sort(this.allTipoColeta, o);
		o = null;
	}
	
	public void atualizarPlanoTrabalho()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allPlanoTrabalho = daof.getPlanoTrabalhoDAO().listaTudo();
		
		Ordenar o = new Ordenar();
		Collections.sort(this.allPlanoTrabalho, o);
		o = null;
	}
	
	@Init
	public void init()
	{
		this.selectedTipoColeta = new TipoColeta();
		
		this.atualizarTipoColeta();		
		this.atualizarPlanoTrabalho();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		
		this.planoDataModel = new ListModelList<PlanoTrabalho>();		
		this.planoDataModel.addAll(this.allPlanoTrabalho);
		this.planoDataModel.setMultiple(true);
		this.lbPlano.setModel(this.planoDataModel);		
		
		this.lbPlanoTipoColeta.setModel(this.planoTipoColetaDataModel = new ListModelList<PlanoTrabalho>());
		this.planoTipoColetaDataModel.setMultiple(true);
		
		Clients.showNotification("Clique aqui para adicionar um novo Tipo de Coleta", "info", this.tbtnNovoTipoColeta, "end_center", 3000);
	}
	
	@Command
	@NotifyChange({"selectedTipoColeta","desativado"})
	public void novo()
	{
		this.desativado = false;
		
		this.selectedTipoColeta = null;
		this.selectedTipoColeta = new TipoColeta();
		
		if ( !this.planoTipoColetaDataModel.isEmpty() )
			this.planoTipoColetaDataModel.clear();
		
		this.planoDataModel.clearSelection();
	}
	
	@Command
	@NotifyChange({"selectedTipoColeta","desativado"})
	public void submit()
	{
		try
		{
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
							
			//if ( !this.allPlanoTipoColeta.isEmpty() )
			this.selectedTipoColeta.setPlanoTrabalho(this.allPlanoTipoColeta);
			daof.getTipoColetaDAO().adiciona(this.selectedTipoColeta);
			daof.commit();
			/*if ( !this.removedPlanoList.isEmpty() )
			{
				this.selectedTipoColeta = daof.getTipoColetaDAO().procura(this.selectedTipoColeta.getId());
				
				if ( this.selectedTipoColeta.getPlanoTrabalho().containsAll(this.removedPlanoList) )
					this.selectedTipoColeta.removePlanoTrabalho(this.removedPlanoList);				
			}*/		
			
			Messagebox.show("O Tipo de Coleta " + this.selectedTipoColeta.getDescricao().toUpperCase() + " foi \nadicionado ou atualizado com sucesso",
					"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);		
				
			//Atualiza a lista na aba Compartimento na janela de Cadastros;
			this.atualizaBindComponent("atualizaListas", "atualizaListas", this.selectedTipoColeta);
							
			this.selectedTipoColeta = null;
				
			this.novo();	
			this.atualizarTipoColeta();
			this.atualizarPlanoTrabalho();
			this.planoDataModel.clear();
			this.planoDataModel.addAll(this.allPlanoTrabalho);
				
			this.desativado = true;			
		}
		catch (WrongValueException wve)
		{
			//Messagebox.show("Você deve selecionar um Tipo de Compartimento, campo obrigatório.", "Hydro - Projeto Alpha", 
							//Messagebox.OK, Messagebox.EXCLAMATION);
			//Clients.showNotification("Tipo de Compartimento deve ser selecionado", "info", this.cbxTP, "end_center", 3000);
			wve.printStackTrace();
		}
		catch (NullPointerException npe)
		{
			//Clients.showNotification("Tipo de Compartimento deve ser selecionado", "info", this.cbxTP, "end_center", 3000);
			npe.printStackTrace();
		}
		catch (Exception e)
		{
			Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema.",
					"Portal Hydro", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
		
	@Command
	@NotifyChange("selectedPlanoTrabalho")
	public void excluir()
	{
		try
		{
			Messagebox.show("Você realmente deseja excluir o Tipo de Coleta " + this.selectedTipoColeta.getDescricao().toUpperCase() + "?", 
					"Portal Hydro", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
				new EventListener<Event>() {
					public void onEvent(Event event) throws SQLException
					{
						if (Messagebox.ON_YES.equals(event.getName()))
						{
							try
							{
								DaoFactory daof = new DaoFactory();
								daof.beginTransaction();
								daof.getTipoColetaDAO().remove(selectedTipoColeta);

								Messagebox.show("Tipo de Coleta excluido com sucesso.", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);
								
								//Atualiza a lista na aba Compartimento na janela de Cadastros;
								atualizaBindComponent("atualizaListas", "atualizaListas", selectedTipoColeta);
								//atualizaBindComponent("atualizaPlanoTrabalhoList", "atualizaPlanoTrabalhoList", selectedPlanoTrabalho);
							}
							catch (Exception e)
							{
								Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
									"Portal Hydro", Messagebox.OK, Messagebox.ERROR);
								e.printStackTrace();									
							}
							
							selectedTipoColeta = null;
							
							novo();																
							navegar("proximo");
						}												
					}			
				});
		}
		catch (NullPointerException n)
		{
			Messagebox.show("Selecione um Tipo de Coleta para a exclusão!", "Portal Hydro", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	@Command
	@NotifyChange({"selectedTipoColeta","desativado"})
	public void navegar(@BindingParam("acao") String acao)
	{
		this.selectedTipoColeta = null;
				
		this.desativado = false;
		
		if ( !this.allTipoColeta.isEmpty() )
		{
			if ( acao.equals("primeiro") )
			{
				this.navegador = 0;
				this.showSelectedTipoColetaItem( this.navegador );
			}
			
			if( acao.equals("anterior") )
			{
				if ( this.navegador > 0 )
				{
					this.showSelectedTipoColetaItem( --this.navegador );
				}
				else 
					this.navegar("primeiro");
			}
		
			if ( acao.equals("proximo") )
			{
				if ( this.navegador < this.allTipoColeta.size()-1 )
				{
					this.showSelectedTipoColetaItem( ++this.navegador );
				}
				else
					this.navegar("ultimo");
			}
		
			if ( acao.equals("ultimo") )
			{	
				this.showSelectedTipoColetaItem( this.navegador = this.allTipoColeta.size()-1 );
			}
		}
		
	}
	
	public void adicionarPlanoUnidade(PlanoTrabalho pt)
	{		
		if (this.planoTipoColetaDataModel.isEmpty())
			this.planoTipoColetaDataModel.add(pt);
		else if ( this.planoTipoColetaDataModel.contains(pt) )
			{			
				Messagebox.show("O(s) Plano(s) já faz(em) parte desse Tipo de Coleta, selecione outro(s)!", 
						"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
			}		
			else
				this.planoTipoColetaDataModel.add(pt);
	}
	
	@Command
	public void adicionarPlano(List<PlanoTrabalho> pt)
	{
		List<PlanoTrabalho> aux = new ArrayList<PlanoTrabalho>();
		for (PlanoTrabalho p : this.planoDataModel.getSelection())		
			aux.add(p);
		
		List<PlanoTrabalho> sp = (pt == null ? aux : pt);
		
		for (PlanoTrabalho plano : sp)
		{
			//final Frota f = frota;//(Compartimento) o;
			
			//Se o compartimento adicionado vier da lista de compartimentos
			//if ( pt == null )
			//{
				this.adicionarPlanoUnidade(plano);									
			//}
			//else
				//this.adicionarFrotaUnidade(f);
		}		
		this.lbPlanoTipoColeta.setCheckmark(true);
		this.allPlanoTipoColeta = this.planoTipoColetaDataModel.getInnerList();
		
		//for ( PlanoTrabalho pp : this.allPlanoTipoColeta )
		//	System.out.printf("Plano: %d\n", pp.getPlano());
		
		this.planoDataModel.clearSelection();
	}
	
	@Command
	public void removerPlano()
	{
		//Set<Frota> set = this.frotaSupervisaoDataModel.getSelection();
		//for (Compartimento c : set)
		//{
		
		//List<PlanoTrabalho> lpt = new ArrayList<PlanoTrabalho>();
		//lpt.addAll(this.planoTipoColetaDataModel.getSelection());
		
		if ( this.removedPlanoList == null)
			this.removedPlanoList = new ArrayList<PlanoTrabalho>();
			
		this.removedPlanoList.addAll(this.planoTipoColetaDataModel.getSelection());
				
		this.planoTipoColetaDataModel.removeAll(this.planoTipoColetaDataModel.getSelection());
		//}
		//this.lbAtivoCompartimentoList.setModel(this.compartimentoAtivoDataModel);
	}
		
	//Método utilizado para atualizar os dados dos comboboxes ou listas em outras janelas
	public void atualizaBindComponent(String bindingParam, String metodo, Object obj)
	{
		Map args = new HashMap();
		args.put(bindingParam, obj);
		BindUtils.postGlobalCommand(null, null, metodo, args);
	}
	
	@GlobalCommand
	@NotifyChange("allPlanoTrabalho")
	public void atualizaPlanoTrabalhoList(@BindingParam("atualizaPlanoTrabalhoList") PlanoTrabalho selectedPlanoTrabalho)
	{
		this.allPlanoTrabalho = null;
		this.atualizarPlanoTrabalho();
		this.planoDataModel.clear();
		this.planoDataModel.addAll(this.allPlanoTrabalho);
	}
	
	//Método utilizado para mostrar o tipo de coleta selecionado na lista ao lado.
	@GlobalCommand
	@NotifyChange({"selectedTipoColeta","desativado"})
	public void showSelectedTipoColetaItem(@BindingParam("showSelectedTipoColetaItem") int i)
	{
		this.desativado = false;
		
		this.selectedTipoColeta= null;
		this.selectedTipoColeta = this.allTipoColeta.get( this.navegador = i );
		
		this.planoTipoColetaDataModel.clear();
		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.selectedTipoColeta = daof.getTipoColetaDAO().procura(this.selectedTipoColeta.getId());
		//for ( PlanoTrabalho p : this.selectedTipoColeta.getPlanoTrabalho() )
			//System.out.printf("Tipo Coleta: %s\t\tPlano: %d\n", this.selectedTipoColeta.getDescricao(), p.getPlano() );
		
		this.adicionarPlano(this.selectedTipoColeta.getPlanoTrabalho());
		
		if ( this.removedPlanoList == null)
			this.removedPlanoList = new ArrayList<PlanoTrabalho>();
			
		this.removedPlanoList.clear();
	}
}
