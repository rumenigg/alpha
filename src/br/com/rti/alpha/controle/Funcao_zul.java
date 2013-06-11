package br.com.rti.alpha.controle;

import java.lang.annotation.Documented;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.pessoa.Funcao;


public class Funcao_zul extends SelectorComposer
{
	@Wire
	private Toolbarbutton tbtnAdicionar;
	@Wire
	private Toolbarbutton tbtnSalvar;
	@Wire
	private Toolbarbutton tbtnExcluir;
		
	@Wire
	private Textbox txtFuncao;
	
	private Funcao f;
	private List<Funcao> listFuncao;
	
	private int indice = 0;
	
	@Init
	public void init()
	{
		Clients.showNotification("Clique aqui para adicionar um nova Função", "info", this.tbtnAdicionar, "end_center", 3000);
	}
	
	@Listen("onCreate=#winFuncao")
	public void listaTudo()
	{
		try
		{
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
			this.listFuncao = daof.getFuncaoDAO().listaTudo();	
			
			Ordenar o = new Ordenar();
			Collections.sort(this.listFuncao, o);
			o = null;
			//daof.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	@Listen("onClick=#tbtnAdicionar")
	public void novoRegistro()
	{
		this.f = null;
		this.f = new Funcao();
		this.txtFuncao.setDisabled(false); 
		this.tbtnSalvar.setDisabled(false); 
		this.tbtnExcluir.setDisabled(false);
		this.txtFuncao.setValue("");
		this.txtFuncao.setFocus(true);
	}
	
	@Listen("onClick=#tbtnSalvar")
	public void salvarRegistro()
	{
		if (txtFuncao.getValue().equals(""))
		{
			Messagebox.show("Por favor, insira uma função.", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.EXCLAMATION);
			txtFuncao.setFocus(true);
		}
		else 
		{			
			try
			{
				DaoFactory daof = new DaoFactory();
				daof.beginTransaction();
				
				f.setFuncao(txtFuncao.getValue());
					
				daof.getFuncaoDAO().adiciona(f);
				daof.commit();
					
				Messagebox.show("Função " + this.f.getFuncao().toUpperCase() + " adicionada ou atualizada com sucesso.", "Hydro - Projeto Alpha", 
								Messagebox.OK, Messagebox.INFORMATION);
					
				atualizaBindComponent("atualiza", f);	
				
				this.listaTudo();	
				
				this.tbtnSalvar.setDisabled(true);
				this.tbtnExcluir.setDisabled(true);
				this.txtFuncao.setValue("");
				this.txtFuncao.setDisabled(true);
			}
			catch (Exception e)
			{
				Messagebox.show("Problema de conexão com o banco de dados.", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
				e.printStackTrace();
			}
			this.txtFuncao.setValue("");
			this.txtFuncao.setDisabled(true);
			this.tbtnSalvar.setDisabled(true);
			this.tbtnExcluir.setDisabled(true);
		}
	}
	
	@Listen("onClick=#tbtnExcluir")
	public void excluirRegistro()
	{
		try
		{
			Messagebox.show("Você realmente deseja exluir a função " + this.f.getFuncao().toUpperCase() + "?", "Hydro - Projeto Alpha",
				   Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				   new EventListener<Event>() {
				      public void onEvent(Event event) throws SQLException {
				          if (Messagebox.ON_YES.equals(event.getName()))
				          {
				        	  DaoFactory daof = new DaoFactory();
				        	  daof.beginTransaction();
				        	  daof.getFuncaoDAO().remove(f);
				        	  daof.commit();
				        	  //daof.close();
				        	  Messagebox.show("Função " + f.getFuncao().toUpperCase() + " excluida com sucesso.", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);			      			
							
				        	  atualizaBindComponent("atualiza", f);
				        	 			        	 		        	  
				        	  listaTudo();
				        	  proximoRegistro();	
				          }
				      }
				   });	
		}
		catch (NullPointerException n)
		{
			Messagebox.show("Selecione uma Função para a exclusão!", "Hydro - Projeto Alpha", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	
	@Listen("onClick=#tbtnPrimeiro")
	public void primeiroRegistro()
	{
		this.indice = 0;
		
		this.f = this.listFuncao.get(indice);
		this.txtFuncao.setValue(this.f.getFuncao());
		this.txtFuncao.setDisabled(false);
		this.tbtnSalvar.setDisabled(false);
		this.tbtnExcluir.setDisabled(false);
	}
	@Listen("onClick=#tbtnAnterior")
	public void anteriorRegistro()
	{
		if ( indice > 0)
		{
			this.f = this.listFuncao.get(--indice);
			this.txtFuncao.setValue(this.f.getFuncao());
		}
		else
		{			
			this.primeiroRegistro();
		}
		this.txtFuncao.setDisabled(false);
		
		this.tbtnSalvar.setDisabled(false);
		this.tbtnExcluir.setDisabled(false);		
	}
	@Listen("onClick=#tbtnProximo")
	public void proximoRegistro()
	{
		if (indice < this.listFuncao.size()-1)
		{
			this.f = this.listFuncao.get(++indice);
			this.txtFuncao.setValue(this.f.getFuncao());
		}
		else this.ultimoRegistro();
			
		this.txtFuncao.setDisabled(false);
		
		this.tbtnSalvar.setDisabled(false);
		this.tbtnExcluir.setDisabled(false);		
	}
	@Listen("onClick=#tbtnUltimo")
	public void ultimoRegistro()
	{
		indice = this.listFuncao.size()-1;
		
		this.f = this.listFuncao.get(indice);
		this.txtFuncao.setValue(this.f.getFuncao());
		this.txtFuncao.setDisabled(false);
		
		this.tbtnSalvar.setDisabled(false);
		this.tbtnExcluir.setDisabled(false);		
	}
	
	public void atualizaBindComponent(String metodo, Object obj)
	{
		Map args = new HashMap();
  	  	args.put("refreshList", obj);
  	  	BindUtils.postGlobalCommand(null, null, metodo, args);
	}
}
