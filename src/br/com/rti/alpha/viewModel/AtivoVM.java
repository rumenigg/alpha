package br.com.rti.alpha.viewModel;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.GroupsModelArray;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import br.com.rti.alpha.controle.CompartimentoAtivoListGroupRenderer;
import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;

public class AtivoVM 
{
	GroupsModelArray<Compartimento, Compartimento, Compartimento, Compartimento> groupModel;
	
	//private ListModelList<Compartimento> compartimentoDataModel;
	private ListModelList<Compartimento> compartimentoAtivoDataModel; 
	
	@Wire
	private Toolbarbutton tbtnNovoAtivo;
	@Wire
	private Listbox lbCompartimentoList;
	@Wire
	private Listbox lbAtivoCompartimentoList;
	@Wire
	private Image fotoAtivo;
	
	private int navegador = 0;
	
	private List<Ativo> allAtivo = new ArrayList<Ativo>();
	private Ativo selectedAtivo;
	
	private List<Compartimento> allCompartimento;
	private Compartimento selectedCompartimento;
	
	private List<Compartimento> allAtivoCompartimento = new ArrayList<Compartimento>();
	private List<Compartimento> removedCompartimentoList = new ArrayList<Compartimento>();
	
	private boolean desativado = true;	
	
	private Media media;

	//Set<Compartimento> set = new HashSet<Compartimento>();
	
	public List<Ativo> getAllAtivo() {
		return allAtivo;
	}

	public void setAllAtivo(List<Ativo> allAtivo) {
		this.allAtivo = allAtivo;
	}

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

	public Compartimento getSelectedCompartimento() {
		return selectedCompartimento;
	}

	public void setSelectedCompartimento(Compartimento selectedCompartimento) {
		this.selectedCompartimento = selectedCompartimento;
	}

	public List<Compartimento> getAllAtivoCompartimento() {
		return allAtivoCompartimento;
	}

	public void setAllAtivoCompartimento(List<Compartimento> allAtivoCompartimento) {
		this.allAtivoCompartimento = allAtivoCompartimento;
	}

	public boolean isDesativado() {
		return desativado;
	}

	public void setDesativado(boolean desativado) {
		this.desativado = desativado;
	}
	
	@GlobalCommand
	@NotifyChange("allAtivo")
	public void atualizaAllAtivo()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allAtivo = daof.getAtivoDAO().listaTudo();
		
		//Coloca em ordem crescente a lista de ativos usando o objeto Ordenar
		Ordenar o = new Ordenar();
		Collections.sort(this.allAtivo, o);
		o = null;
		
		//daof.close();
	}
	
	@NotifyChange("allCompartimento")
	public void atualizaAllCompartimento()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allCompartimento = daof.getCompartimentoDAO().listaTudo();
		
		//Coloca em ordem crescente a lista de compartimentos usando o objeto Ordenar
		Ordenar o = new Ordenar();
		Collections.sort(this.allCompartimento, o);
		o = null;
		
		//daof.close();
	}
	
	@Init
	public void init()
	{
		this.selectedAtivo = new Ativo();
		this.selectedCompartimento = new Compartimento();
		
		this.atualizaAllAtivo();
		this.atualizaAllCompartimento();
	}
	
	//Popula o Listbox separando por categorias de acordo com o tipo de compartimento
	public void addLBCompartimento()
	{
		List<Compartimento> compart = this.allCompartimento;
		Compartimento[] compartArray = compart.toArray(new Compartimento[compart.size()]);
		this.groupModel = new GroupsModelArray<Compartimento, Compartimento, Compartimento, Compartimento>(
				compartArray, new FieldComparator("tipoCompartimento.descricao", true));
		
		this.groupModel.setMultiple(true);
		
		//this.compartimentoDataModel = groupModel.;
		this.lbCompartimentoList.setCheckmark(true);
		this.lbCompartimentoList.setModel(groupModel);
		this.lbCompartimentoList.setItemRenderer(new CompartimentoAtivoListGroupRenderer(this.desativado));
	}
	
	//Método responsável por mapear os objetos wire com a view. Nesse método pode também ser utilizado para criar as Listen com os eventos ex. onClick
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		Selectors.wireEventListeners(view, this);
		
		//Adiciona os itens no Listbox compartimento de acordo com os tipos de compartimentos
		this.addLBCompartimento();
		
		//this.compartimentoDataModel = new ListModelList<Compartimento>();
		
		this.lbAtivoCompartimentoList.setModel(this.compartimentoAtivoDataModel = new ListModelList<Compartimento>());
		this.compartimentoAtivoDataModel.setMultiple(true);
		
		Clients.showNotification("Clique aqui para adicionar um novo Ativo", "info", this.tbtnNovoAtivo, "end_center", 3000);
	}
	
	/*
	 * Método disparado através da janela Compartimentos para atualizar o listbox de compartimentos.
	 */
	@GlobalCommand
	@NotifyChange("allCompartimento")
	public void atualizaAtivoListCompartimento(@BindingParam("atualizaAtivoListCompartimento") Compartimento Compartimento)
	{
		this.allCompartimento = null;
		this.atualizaAllCompartimento();
		this.addLBCompartimento();
	}
	
	@Command
	@NotifyChange({"selectedAtivo","desativado"})
	public void novo()
	{
		this.desativado = false;
		
		this.selectedAtivo = null;
		this.selectedAtivo = new Ativo();
		
		this.compartimentoAtivoDataModel.clear();
		this.allAtivoCompartimento.clear();
		this.removedCompartimentoList.clear();
		
		this.addLBCompartimento();
	}
	
	@Command
	@NotifyChange({"selectedAtivo","desativado"})
	public void submit()
	{				
		try
		{
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
			
			if ( this.media != null && this.selectedAtivo.getFoto() != null )
				this.delFoto(this.selectedAtivo.getFoto() );
									
			daof.getAtivoDAO().adiciona(this.selectedAtivo);
						
			this.allAtivoCompartimento = this.compartimentoAtivoDataModel.getInnerList();
			
			if ( !this.allAtivoCompartimento.isEmpty() )
			{		
				System.out.println("\n--> Tamanho da Lista de compartimentos: " + this.allAtivoCompartimento.size()+"\n");
				//for (Compartimento c : this.allAtivoCompartimento)
				for(int i = 0; i< this.allAtivoCompartimento.size(); i++)
				{
					Compartimento c = this.allAtivoCompartimento.get(i);
					c.setAtivo(this.selectedAtivo);
					daof.getCompartimentoDAO().adiciona(c);		
				}
			}

			if ( !this.removedCompartimentoList.isEmpty() )
			{
				for ( Compartimento c : this.removedCompartimentoList )
				{
					c.setAtivo(null);
					daof.getCompartimentoDAO().adiciona(c);
				}
			}
			
			daof.commit();
			this.salvarFoto();
			
			Messagebox.show("O Ativo " + this.selectedAtivo.getTag().toUpperCase() + " foi \nadicionado ou atualizado com sucesso",
						"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);			
				
			//Atualiza a lista na aba Compartimento na janela de Cadastros;
			this.atualizaBindComponent(this.selectedAtivo);
			//this.novo();
			this.selectedAtivo = null;
			this.atualizaAllAtivo();
			this.atualizaAllCompartimento();

			this.compartimentoAtivoDataModel.clear();
			
			this.allAtivoCompartimento.clear();
			//this.allAtivoCompartimento = null;
			this.removedCompartimentoList.clear();
			//this.removedCompartimentoList = null;
			
			this.desativado = true;
			
			//Adiciona os itens no Listbox compartimento de acordo com os tipos de compartimentos			
			this.addLBCompartimento();	
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
	@NotifyChange("selectedAtivo")
	public void excluir()
	{		
		try
		{
			if ( !this.selectedAtivo.getCompartimento().isEmpty() )
			{
				Messagebox.show("Atenção, o Ativo " + this.selectedAtivo.getTag().toUpperCase() + " possui compartimento(s) associado(s). "+
						"Caso prossiga com a exclusão os compartimentos ficarão sem ativo. Você realmente deseja prosseguir com a exclusão?", "Portal Hydro",
						Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION, 
						new EventListener<Event>() {
							public void onEvent(Event event) throws SQLException
							{
								if (Messagebox.ON_YES.equals(event.getName()))
								{
									try
									{
										DaoFactory daof = new DaoFactory();
										daof.beginTransaction();
										
										selectedAtivo = daof.getAtivoDAO().procura(selectedAtivo.getId());
										for ( Compartimento c : selectedAtivo.getCompartimento() )
											c.setAtivo(null);										
										
										daof.getAtivoDAO().remove(selectedAtivo);
										
										if ( selectedAtivo.getFoto() != null )
											delFoto(selectedAtivo.getFoto());
										
										//daof.commit();
										
										//Atualiza a lista na aba Compartimento na janela de Cadastros;
										atualizaBindComponent(selectedAtivo);				
										
										Messagebox.show("Ativo excluido com sucesso.", "Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
																				
										BindUtils.postNotifyChange(null,null,this,"selectedAtivo");
										atualizaAllAtivo();													
										navegar("proximo");
									}
									catch (Exception e)
									{
										Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
													"Portal Hydro", Messagebox.OK, Messagebox.ERROR);
										e.printStackTrace();									
									}
								}								
							}			
						});				
			}
			else 
			{			
				Messagebox.show("Você realmente deseja excluir o Ativo " + this.selectedAtivo.getTag().toUpperCase() + "?", "Portal Hydro",
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
									
									daof.getAtivoDAO().remove(selectedAtivo);
									
									if ( selectedAtivo.getFoto() != null )
										delFoto(selectedAtivo.getFoto());
									
									//daof.commit();

									//Atualiza a lista na aba Compartimento na janela de Cadastros;
									atualizaBindComponent(selectedAtivo);		
									
									Messagebox.show("Ativo excluido com sucesso.", "Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
									
									atualizaAllAtivo();													
									navegar("proximo");
									BindUtils.postNotifyChange(null,null,this,"selectedAtivo");
								}
								catch (Exception e)
								{
									Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
												"Portal Hydro", Messagebox.OK, Messagebox.ERROR);
									e.printStackTrace();									
								}
							}							
						}			
					});				
			}
		}
		catch (NullPointerException n)
		{
			Messagebox.show("Selecione um Ativo para a exclusão!", "Portal Hydro", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	@Command
	@NotifyChange({"selectedAtivo","desativado"})
	public void navegar(@BindingParam("acao") String acao) throws IOException
	{
		this.selectedAtivo = null;
		this.selectedAtivo = new Ativo();
		
		this.desativado = false;
		
		if ( !this.allAtivo.isEmpty() )
		{
			if ( acao.equals("primeiro") )
			{
				this.navegador = 0;
				this.showSelectedAtivoItem( this.navegador );			
			}
			
			if ( acao.equals("anterior") )
			{
				if ( this.navegador > 0 )
				{
					this.showSelectedAtivoItem( --this.navegador );
				}
				else 
					this.navegar("primeiro");
			}
		
			if ( acao.equals("proximo") )
			{
				if ( this.navegador < this.allAtivo.size()-1 )
				{
					this.showSelectedAtivoItem( ++this.navegador );
				}
				else
					this.navegar("ultimo");
			}
		
			if ( acao.equals("ultimo") )
			{
				this.showSelectedAtivoItem( this.navegador = this.allAtivo.size()-1 );
			}
		}
		
	}
	public void adicionarCompartimentoUnidade(Compartimento c)
	{		
		//System.out.println("\n* Passo 1 - Tamanho: " + this.compartimentoAtivoDataModel.size());
		if ( this.compartimentoAtivoDataModel.isEmpty() )
		{
			this.compartimentoAtivoDataModel.add(c);
			//System.out.println("\n* Passo 2 - Tamanho: " + this.compartimentoAtivoDataModel.size());
		}
		else
		{
			//System.out.println("\n* Passo 3 - Tamanho: " + this.compartimentoAtivoDataModel.size());
			if ( this.compartimentoAtivoDataModel.getInnerList().contains(c) )
			{			
				Messagebox.show("O Compartimento " + c.getTag() + " já faz parte desse Ativo", 
						"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
			}		
			else
			{
				this.compartimentoAtivoDataModel.add(c);
				//System.out.println("\n* Passo 4 - Tamanho: " + this.compartimentoAtivoDataModel.size());
			}				
		}
	}
	
	@Command
	@NotifyChange("allAtivoCompartimento")
	public void adicionarCompartimento(Set<Compartimento> sc)
	{		
		Set<Compartimento> so = sc == null ? this.groupModel.getSelection() : sc;
		
		//System.out.println("\n--> Tamanho da lista de adição dos componentes: " + so.size());
		
		for (Compartimento comp : so)
		{
			final Compartimento c = comp;//(Compartimento) o;
			
			//Se o compartimento adicionado vier da lista de compartimentos
			if ( sc == null )
			{
				if ( c.getAtivo() != null )
				{
					Messagebox.show("ATENÇÃO, o compartimento selecionado pertence a outro ATIVO. "+
							"Caso continue com a adição, o compartimento selecionado passará a fazer parte do ativo atual. "+ 
							"Você deseja continuar com a adição do compartimento?", "Portal Hydro",
							Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
							new EventListener<Event>() {
							public void onEvent(Event event) throws SQLException
							{
								if (Messagebox.ON_YES.equals(event.getName()))
									adicionarCompartimentoUnidade(c);									
							}
					});
				}
				else
					this.adicionarCompartimentoUnidade(c);
			}
			else
				this.adicionarCompartimentoUnidade(c);
		}		
		this.lbAtivoCompartimentoList.setCheckmark(true);
		//this.allAtivoCompartimento = this.compartimentoAtivoDataModel.getInnerList();
		//System.out.println("\n--*> Tamanho da lista dos componentes adicionados: " + this.allAtivoCompartimento.size());
		
		this.groupModel.clearSelection();		
	}
	
	@Command
	public void removerCompartimento()
	{
		Set<Compartimento> set = this.compartimentoAtivoDataModel.getSelection();
		//for (Compartimento c : set)
		//{
		if ( this.removedCompartimentoList == null)
			this.removedCompartimentoList = new ArrayList<Compartimento>();
			
		this.removedCompartimentoList.addAll(set);
		
		this.compartimentoAtivoDataModel.removeAll(set);
		//}
		//this.lbAtivoCompartimentoList.setModel(this.compartimentoAtivoDataModel);
	}

	public void atualizaBindComponent(Object obj)
	{
		Map args = new HashMap();
		args.put("atualizaListas", obj);
		BindUtils.postGlobalCommand(null, null, "atualizaListas", args);
	}
	
	@GlobalCommand
	@NotifyChange({"selectedAtivo","desativado"})
	public void showSelectedAtivoItem(@BindingParam("showSelectedAtivoItem") int i) throws IOException
	{		
		this.desativado = false;
		this.addLBCompartimento();
		this.selectedAtivo = null;
		this.selectedAtivo = this.allAtivo.get( this.navegador = i );
		this.selectedCompartimento = null;
				
		if ( this.removedCompartimentoList == null )
			this.removedCompartimentoList = new ArrayList<Compartimento>();
		else
			this.removedCompartimentoList.clear();		
		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();	
		
		this.selectedAtivo = daof.getAtivoDAO().procura(selectedAtivo.getId());				
		
		Set<Compartimento> sc = this.selectedAtivo.getCompartimento();	
				
		if ( (this.selectedAtivo.getFoto() != null) && !this.selectedAtivo.getFoto().isEmpty() )
		{
			org.zkoss.image.AImage img = new org.zkoss.image.AImage(this.selectedAtivo.getFoto());
			this.fotoAtivo.setContent(img);
		}
		else
			this.fotoAtivo.setSrc(null);
		
		this.compartimentoAtivoDataModel.clear();
		
		this.adicionarCompartimento(sc);
	}
	
	@Listen("onUpload=#btnAtivoFoto")
	public void addFoto(UploadEvent evt)
	{
		this.media = evt.getMedia();
	}
	
	public void salvarFoto() throws IOException
	{
		if ( this.media != null )
		{
			String path = "rti/alpha";//System.getProperty("user.home"); 
			
			path += "/hydro/img/imagens/ativos/" + this.selectedAtivo.getTag()+"_" + this.media.getName();
			
			BufferedImage imagem = ImageIO.read( this.media.getStreamData() );
			File arquivo = new File(path);
			arquivo.mkdirs();
			// fazer algo com a imagem...
			int type = BufferedImage.TYPE_INT_RGB;
	        boolean isPng = path.toUpperCase().endsWith("PNG");
			if (isPng) {
	            type = BufferedImage.BITMASK;
	        }
	        
	        if (isPng) {
	            ImageIO.write(imagem, "PNG", arquivo);
	        }else{
	            ImageIO.write(imagem, "JPG", arquivo);
	        }
			
			this.selectedAtivo.setFoto(arquivo.getCanonicalPath());//path);
			
			BufferedImage thumb = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = thumb.createGraphics();
	        g.setComposite(AlphaComposite.Src);
	        g.drawImage(imagem, 0, 0, 100, 100, null);
	        
			String extensao = path.substring(path.length()-4, path.length());
			String nome = path.substring(0, path.length()-4);
			File arquivoThumb = new File(nome+"_thumb"+extensao);

			 if (isPng) {
		            ImageIO.write(thumb, "PNG", arquivoThumb);
		        }else{
		            ImageIO.write(thumb, "JPG", arquivoThumb);
		        }		
		}
	}
	
	public void delFoto(String foto) throws IOException
	{
		File arquivo = new File(foto);
		
		arquivo.delete();
	}
}
