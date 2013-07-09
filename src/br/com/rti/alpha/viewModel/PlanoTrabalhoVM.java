package br.com.rti.alpha.viewModel;

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
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.PlanoTrabalho;

public class PlanoTrabalhoVM 
{
	@Wire
	private Toolbarbutton tbtnNovoPlanoTrabalho;
	
	private int navegador;
	
	private boolean desativado = true;
	
	private List<PlanoTrabalho> allPlanoTrabalho;
	private PlanoTrabalho selectedPlanoTrabalho;
	
	public boolean isDesativado() {
		return desativado;
	}
	public void setDesativado(boolean desativado) {
		this.desativado = desativado;
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
	public void atualizarPlanoTrabalho()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allPlanoTrabalho = daof.getPlanoTrabalhoDAO().listaTudo();
		
		Ordenar o = new Ordenar();
		Collections.sort(this.allPlanoTrabalho, o);
	}
	
	@Init
	public void init()
	{
		this.selectedPlanoTrabalho = new PlanoTrabalho();
		
		this.atualizarPlanoTrabalho();		
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		Clients.showNotification("Clique aqui para adicionar um novo Plano de Trabalho", "info", this.tbtnNovoPlanoTrabalho, "end_center", 3000);
	}
	
	@Command
	@NotifyChange({"selectedPlanoTrabalho","desativado"})
	public void novo()
	{
		this.desativado = false;
		
		this.selectedPlanoTrabalho = null;
		this.selectedPlanoTrabalho = new PlanoTrabalho();
	}
	
	@Command
	@NotifyChange({"selectedPlanoTrabalho","desativado"})
	public void submit()
	{
		try
		{
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
				
			daof.getPlanoTrabalhoDAO().adiciona(this.selectedPlanoTrabalho);
				
			Messagebox.show("O Plano de Trabalho " + this.selectedPlanoTrabalho.getPlano() + " foi \nadicionado ou atualizado com sucesso",
					"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);		
				
			//Atualiza a lista na aba Compartimento na janela de Cadastros;
			this.atualizaBindComponent("atualizaListas", "atualizaListas", this.selectedPlanoTrabalho);
			this.atualizaBindComponent("atualizaPlanoTrabalhoList", "atualizaPlanoTrabalhoList", this.selectedPlanoTrabalho);
				
			this.selectedPlanoTrabalho = null;
				
			this.novo();	
			this.atualizarPlanoTrabalho();
				
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
			Messagebox.show("Você realmente deseja excluir o Plano de Trabalho " + this.selectedPlanoTrabalho.getPlano() + "?", "Portal Hydro",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
				new EventListener<Event>() {
					public void onEvent(Event event) throws SQLException
					{
						if (Messagebox.ON_YES.equals(event.getName()))
						{
							try
							{
								DaoFactory daof = new DaoFactory();
								daof.beginTransaction();
								daof.getPlanoTrabalhoDAO().remove(selectedPlanoTrabalho);

								Messagebox.show("Plano de Trabalho excluido com sucesso.", "Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
								
								//Atualiza a lista na aba Compartimento na janela de Cadastros;
								atualizaBindComponent("atualizaListas", "atualizaListas", selectedPlanoTrabalho);
								atualizaBindComponent("atualizaPlanoTrabalhoList", "atualizaPlanoTrabalhoList", selectedPlanoTrabalho);
							}
							catch (Exception e)
							{
								Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
									"Portal Hydro", Messagebox.OK, Messagebox.ERROR);
								e.printStackTrace();									
							}
							
							selectedPlanoTrabalho = null;
							
							novo();																
							navegar("proximo");
						}												
					}			
				});
		}
		catch (NullPointerException n)
		{
			Messagebox.show("Selecione um Plano de Trabalho para a exclusão!", "Portal Hydro", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	@Command
	@NotifyChange({"selectedPlanoTrabalho","desativado"})
	public void navegar(@BindingParam("acao") String acao)
	{
		this.selectedPlanoTrabalho = null;
				
		this.desativado = false;
		
		if ( !this.allPlanoTrabalho.isEmpty() )
		{
			if ( acao.equals("primeiro") )
			{
				this.navegador = 0;
				this.showSelectedPlanoTrabalhoItem( this.navegador );
			}
			
			if( acao.equals("anterior") )
			{
				if ( this.navegador > 0 )
				{
					this.showSelectedPlanoTrabalhoItem( --this.navegador );
				}
				else 
					this.navegar("primeiro");
			}
		
			if ( acao.equals("proximo") )
			{
				if ( this.navegador < this.allPlanoTrabalho.size()-1 )
				{
					this.showSelectedPlanoTrabalhoItem( ++this.navegador );
				}
				else
					this.navegar("ultimo");
			}
		
			if ( acao.equals("ultimo") )
			{	
				this.showSelectedPlanoTrabalhoItem( this.navegador = this.allPlanoTrabalho.size()-1 );
			}
		}
		
	}
		
		//Método utilizado para atualizar os dados dos comboboxes ou listas em outras janelas
	public void atualizaBindComponent(String bindingParam, String metodo, Object obj)
	{
		Map args = new HashMap();
		args.put(bindingParam, obj);
		BindUtils.postGlobalCommand(null, null, metodo, args);
	}
		
		//Método utilizado para mostrar o compartimento selecionado na lista ao lado.
	@GlobalCommand
	@NotifyChange({"selectedPlanoTrabalho","desativado"})
	public void showSelectedPlanoTrabalhoItem(@BindingParam("showSelectedPlanoTrabalhoItem") int i)
	{
		this.desativado = false;
		
		this.selectedPlanoTrabalho= null;
		this.selectedPlanoTrabalho = this.allPlanoTrabalho.get( this.navegador = i );	
		
	}
}
