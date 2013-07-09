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

import org.zkoss.bind.BindContext;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.GroupsModelArray;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import br.com.rti.alpha.controle.AImageConverter;
import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;
import br.com.rti.alpha.modelo.pessoa.Pessoa;
import br.com.rti.alpha.modelo.supervisao.Frota;
import br.com.rti.alpha.modelo.supervisao.Supervisao;

public class SupervisaoVM
{
	@Wire
	private Toolbarbutton tbtnNovoSupervisao;
	@Wire
	private Combobox cbxResponsavel;
	@Wire
	private Listbox lbFrota;
	@Wire
	private Listbox lbFrotaSupervisao;
	@Wire
	private Image fotoSupervisao;
	
	private Media media;
	
	//GroupsModelArray<Frota, Frota, Frota, Frota> groupModel;
	
	private ListModelList<Frota> frotaDataModel;
	private ListModelList<Frota> frotaSupervisaoDataModel;
	
	private int navegador = 0;
	
	private boolean desativado = true;
	
	private Pessoa selectedPessoa;
	private List<Pessoa> allPessoa;
	
	private Supervisao selectedSupervisao;
	private List<Supervisao> allSupervisao;
	
	private Frota selectedFrota;
	private List<Frota> allFrota;
	private List<Frota> removedFrotaList = new ArrayList<Frota>();
	private List<Frota> allFrotaSupervisao = new ArrayList<Frota>();
	
	private Converter converter = new AImageConverter();
	
		
	public boolean isDesativado() {
		return desativado;
	}
	public void setDesativado(boolean desativado) {
		this.desativado = desativado;
	}
	public Pessoa getSelectedPessoa() {
		return selectedPessoa;
	}
	public void setSelectedPessoa(Pessoa selectedPessoa) {
		this.selectedPessoa = selectedPessoa;
	}
	public List<Pessoa> getAllPessoa() {
		return allPessoa;
	}
	public void setAllPessoa(List<Pessoa> allPessoa) {
		this.allPessoa = allPessoa;
	}
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
	public Frota getSelectedFrota() {
		return selectedFrota;
	}
	public void setSelectedFrota(Frota selectedFrota) {
		this.selectedFrota = selectedFrota;
	}
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
	
	@NotifyChange("allPessoa")
	public void atualizaPessoa()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allPessoa = daof.getPessoaDAO().listaTudo();
		
		Ordenar o = new Ordenar();
		Collections.sort(this.allPessoa, o);
		o = null;
	}
	
	@GlobalCommand
	@NotifyChange("allSupervisao")
	public void atualizaSupervisao()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allSupervisao = daof.getSupervisaoDAO().listaTudo();
		
		Ordenar o = new Ordenar();
		Collections.sort(this.allSupervisao, o);
		o = null;
	}
	
	@NotifyChange("allFrota")
	public void atualizaFrota()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allFrota= daof.getFrotaDAO().listaTudo();
		
		Ordenar o = new Ordenar();
		Collections.sort(this.allFrota, o);
		o = null;
	}
	
	@Init
	public void init()
	{
		
		this.selectedSupervisao = new Supervisao();
		//this.selectedPessoa = new Pessoa();
		//this.selectedFrota = new Frota();
		
		this.allPessoa = new ArrayList<Pessoa>();
		this.allSupervisao = new ArrayList<Supervisao>();
		this.allFrota = new ArrayList<Frota>();
		this.allFrotaSupervisao = new ArrayList<Frota>();
		this.removedFrotaList = new ArrayList<Frota>();
		
		this.atualizaPessoa();
		this.atualizaSupervisao();
		this.atualizaFrota();

	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		Selectors.wireEventListeners(view, this);
		
		this.frotaDataModel = new ListModelList<Frota>();		
		this.frotaDataModel.addAll(this.allFrota);
		this.frotaDataModel.setMultiple(true);
		this.lbFrota.setModel(this.frotaDataModel);		
		
		this.lbFrotaSupervisao.setModel(this.frotaSupervisaoDataModel = new ListModelList<Frota>());
		this.frotaSupervisaoDataModel.setMultiple(true);
		
		Clients.showNotification("Clique aqui para adicionar uma nova Supervisão", "info", this.tbtnNovoSupervisao, "end_center", 3000);
	}
	
	@Command
	@NotifyChange({"selectedPessoa","selectedSupervisao","desativado"})
	public void novo()
	{
		this.desativado = false;
		this.selectedPessoa = null;
		this.selectedSupervisao = null;
		this.selectedSupervisao = new Supervisao();
		
		if ( !this.frotaSupervisaoDataModel.isEmpty() )
			this.frotaSupervisaoDataModel.clear();
		
		this.frotaDataModel.clearSelection();
		
		//BindUtils.postNotifyChange(null, null, this, "selectedPessoa");
		this.cbxResponsavel.setSelectedItem(null);
	}
	
	@Command
	@NotifyChange({"selectedSupervisao", "desativado", "selectedPessoa"})
	public void submit()
	{
		try
		{	
			if ( this.selectedPessoa == null )
				Clients.showNotification("Você deve selecionar um responsável para essa Supervisão", "info", this.cbxResponsavel, "end_center", 2500);
			else
			{
				/*if ( this.selectedSupervisao.getPessoaResponsavelSupervisao() == null )
				{					
					if ( !this.verificarResponsavel() )
					{
						this.selectedSupervisao.setPessoaResponsavelSupervisao(this.selectedPessoa);
						this.submit();
					}
				}*/
			
				if ( !this.verificarResponsavel() )
				{
					DaoFactory daof = new DaoFactory();
					daof.beginTransaction();
					
					if ( this.media != null && this.selectedSupervisao.getFoto() != null )
						this.delFoto(this.selectedSupervisao.getFoto());
					
					this.salvarFoto();
					
					this.selectedSupervisao.setPessoaResponsavelSupervisao(this.selectedPessoa);
					daof.getSupervisaoDAO().adiciona(this.selectedSupervisao);
			
					if ( !this.allFrotaSupervisao.isEmpty() )
					{			
						for (Frota frota : this.allFrotaSupervisao)
						{
							frota.setSupervisao(this.selectedSupervisao);
							daof.getFrotaDAO().adiciona(frota);						
						}
					}
				
					if ( !this.removedFrotaList.isEmpty() )
					{
						for ( Frota f : this.removedFrotaList )
						{
							f.setSupervisao(null);
							daof.getFrotaDAO().adiciona(f);
						}
					}
					//daof.commit();
			
					Messagebox.show("A Supervisão " + this.selectedSupervisao.getDescricao().toUpperCase() + " foi adicionada ou atualizada com sucesso",
							"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
			
				
					//Atualiza a lista na aba Compartimento na janela de Cadastros;
					this.atualizaBindComponent("atualizaListas", "atualizaListas", this.selectedSupervisao);					
					//Atualiza a lista de Supervisões na janela de Gestão de Pessoas
					this.atualizaBindComponent("refreshList", "atualiza", this.selectedSupervisao);
					this.novo();	
					this.atualizaFrota();
					this.atualizaSupervisao();
					//Adiciona os itens no Listbox compartimento de acordo com os tipos de compartimentos
					//this.addLBCompartimento();
					this.frotaDataModel.clear();
					this.frotaDataModel.addAll(this.allFrota);
					this.desativado = true;
				}
				//else
				//{
					//Clients.showNotification("Essa pessoa é responsável por outra Supervisão, selecione outra.", "info", this.cbxResponsavel, "end_center", 2500);
					//this.cbxResponsavel.setFocus(true);
				//}
			}
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
	@NotifyChange({"selectedSupervisao","desativado","selectedFuncao"})
	public void excluir()
	{
		try
		{
			if ( !this.selectedSupervisao.getFrota().isEmpty() )
			{
				Messagebox.show("Atenção, a Supervisão " + this.selectedSupervisao.getDescricao().toUpperCase() + " possui frota(s) associada(s). "+
						"Caso prossiga com a exclusão a(s) frota(a) deixará(ão) de fazer parte de qualquer supervisão. " +
						"Você realmente deseja prosseguir com a exclusão?", "Portal Hydro",
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
										
										selectedSupervisao = daof.getSupervisaoDAO().procura(selectedSupervisao.getId());
										for ( Frota f : selectedSupervisao.getFrota() )
											f.setSupervisao(null);										
										
										daof.getSupervisaoDAO().remove(selectedSupervisao);
										//daof.commit();
										if ( selectedSupervisao.getFoto() != null )
											delFoto(selectedSupervisao.getFoto());
										
										Messagebox.show("Supervisão excluida com sucesso.", "Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
																				
										desativado = true;
										atualizaFrota();
										atualizaSupervisao();
										
										//Atualiza a lista na aba Compartimento na janela de Cadastros;
										atualizaBindComponent("atualizaListas", "atualizaListas", selectedSupervisao);
										
										navegar("proximo");
										
										frotaDataModel.clear();
										frotaDataModel.addAll(allFrota);										
									}
									catch (IndexOutOfBoundsException iobe)
									{
										
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
				Messagebox.show("Você realmente deseja excluir a Supervisão " + this.selectedSupervisao.getDescricao().toUpperCase() + "?", "Portal Hydro",
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
									
									daof.getSupervisaoDAO().remove(selectedSupervisao);
									//daof.commit();
									
									Messagebox.show("Supervisão excluida com sucesso.", "Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
									
									desativado = true;
									atualizaSupervisao();
									
									//Atualiza a lista na aba Compartimento na janela de Cadastros;
									atualizaBindComponent("atualizaListas", "atualizaListas", selectedSupervisao);
									
									navegar("proximo");																	
								}
								catch (IndexOutOfBoundsException iobe)
								{
									
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
			Messagebox.show("Selecione uma Supervisão para a exclusão!", "Portal Hydro", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}	
	}
	
	@Command
	@NotifyChange({"selectedSupervisao","desativado"})
	public void navegar(@BindingParam("acao") String acao) throws IOException
	{
		this.selectedSupervisao = null;
		
		this.desativado = false;
		
		if ( !this.allSupervisao.isEmpty() )
		{
			if ( acao.equals("primeiro") )
			{
				this.navegador = 0;
				this.showSelectedSupervisaoItem( this.navegador );			
			}
			
			if( acao.equals("anterior") )
			{
				if ( this.navegador > 0 )
				{
					this.showSelectedSupervisaoItem( --this.navegador );
				}
				else 
					this.navegar("primeiro");
			}
		
			if ( acao.equals("proximo") )
			{
				if ( this.navegador < this.allSupervisao.size()-1 )
				{
					this.showSelectedSupervisaoItem( ++this.navegador );
				}
				else
					this.navegar("ultimo");
			}
		
			if ( acao.equals("ultimo") )
			{
				this.showSelectedSupervisaoItem( this.navegador = this.allSupervisao.size()-1 );
			}
		}
	}
	
	/*
	 * Método utilizado para mostrar o item selecionado na aba correspondente, Supervisão
	 */
	@GlobalCommand
	@NotifyChange({"selectedSupervisao","fotoSupervisao","desativado"})
	public void showSelectedSupervisaoItem(@BindingParam("showSelectedSupervisaoItem") int i) throws IOException
	{		
		this.desativado = false;
		//this.addLBCompartimento();
		this.selectedFrota = null;
			
		if ( this.removedFrotaList == null )
			this.removedFrotaList = new ArrayList<Frota>();
		else
			this.removedFrotaList.clear();		
			
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.selectedSupervisao = this.allSupervisao.get( this.navegador = i );
		this.selectedSupervisao = daof.getSupervisaoDAO().procura(selectedSupervisao.getId());			
		
		Set<Frota> sf = this.selectedSupervisao.getFrota();	
		
		this.selectPessoa(this.selectedSupervisao.getPessoaResponsavelSupervisao());
			
		this.frotaSupervisaoDataModel.clear();
		
		this.adicionarFrota(sf);
		
		if ( (this.selectedSupervisao.getFoto() != null) && !this.selectedSupervisao.getFoto().isEmpty() )
		{
			org.zkoss.image.AImage img = new org.zkoss.image.AImage(this.selectedSupervisao.getFoto());
			this.fotoSupervisao.setContent(img);//(this.selectedSupervisao.getFoto()); //.setContent(img);
		}
		else
			this.fotoSupervisao.setSrc(null);		
	}
	
	@NotifyChange("selectedPessoa")
	public void selectPessoa(Pessoa pessoa)
	{
		this.selectedPessoa = pessoa;
		
		//List<Comboitem> li = this.cbxFuncao.getItems().;
		if ( this.selectedPessoa != null )
		{
			for (int i = 0; i < this.allPessoa.size(); i++)
			{
				Pessoa p = this.allPessoa.get(i);
				if (p.getId() == this.selectedPessoa.getId())
					this.cbxResponsavel.setSelectedIndex(i);
			}
		}
	}
	
	public void adicionarFrotaUnidade(Frota f)
	{		
		if (this.frotaSupervisaoDataModel.isEmpty())
			this.frotaSupervisaoDataModel.add(f);
		else if ( this.frotaSupervisaoDataModel.getInnerList().contains(f) )
			{			
				Messagebox.show("A Frota " + f.getDescricao().toUpperCase() + " já faz parte dessa Supervisao", 
						"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
			}		
			else
				this.frotaSupervisaoDataModel.add(f);
	}
	
	@Command
	public void adicionarFrota(Set<Frota> sf)
	{
		Set<Frota> so = sf == null ? this.frotaDataModel.getSelection() : sf;
		
		for (Frota frota : so)
		{
			final Frota f = frota;//(Compartimento) o;
			
			//Se o compartimento adicionado vier da lista de compartimentos
			if ( sf == null )
			{
				if ( f.getSupervisao() != null )
				{
					Messagebox.show("ATENÇÃO, a frota selecionada pertence a outra SUPERVISÃO. "+
							"Caso continue com a adição, a frota selecionada passará a fazer parte da supervisao atual. "+ 
							"Você deseja continuar com a adição da frota?", "Portal Hydro",
							Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
							new EventListener<Event>() {
							public void onEvent(Event event) throws SQLException
							{
								if (Messagebox.ON_YES.equals(event.getName()))
									adicionarFrotaUnidade(f);									
							}
					});
				}
				else
					this.adicionarFrotaUnidade(f);
			}
			else
				this.adicionarFrotaUnidade(f);
		}		
		this.lbFrotaSupervisao.setCheckmark(true);
		this.allFrotaSupervisao = this.frotaSupervisaoDataModel.getInnerList();
		
		this.frotaDataModel.clearSelection();
	}
	
	@Command
	public void removerFrota()
	{
		Set<Frota> set = this.frotaSupervisaoDataModel.getSelection();
		//for (Compartimento c : set)
		//{
		if ( this.removedFrotaList == null)
			this.removedFrotaList = new ArrayList<Frota>();
			
		this.removedFrotaList.addAll(set);
				
		this.frotaSupervisaoDataModel.removeAll(set);
		//}
		//this.lbAtivoCompartimentoList.setModel(this.compartimentoAtivoDataModel);
	}
	
	@Command
	public boolean verificarResponsavel()
	{
		if ( this.selectedPessoa == null )
			this.selectedPessoa = new Pessoa();
		
		boolean existe = false;
				
		if ( this.selectedSupervisao != null && this.selectedSupervisao.getPessoaResponsavelSupervisao() != null )
		{
			if (this.selectedSupervisao.getPessoaResponsavelSupervisao().getId() == this.selectedPessoa.getId())
				existe = false;	
			else
			{
				existe = true;
				Clients.showNotification("Essa pessoa é responsável por outra Supervisão, selecione outra.", "info", this.cbxResponsavel, "end_center", 2500);
				this.cbxResponsavel.setFocus(true);
			}			
		}			
		else 
		{
			if ( !this.allSupervisao.isEmpty() )
				for ( Supervisao s : allSupervisao )
					if ( (this.selectedPessoa != null) && (s.getPessoaResponsavelSupervisao().getId() == this.selectedPessoa.getId()) ) 
						{
							existe = true;
							Clients.showNotification("Essa pessoa é responsável por outra Supervisão, selecione outra.", "info", this.cbxResponsavel, "end_center", 2500);
							this.cbxResponsavel.setFocus(true);
						}
		}
		
		return existe;
	}
	
	/*
	 * Método utilizado para atualizar a lista de pessoas. Este ação será disparada na janela Pessoa ou em qualquer 
	 * outra que fizer alteração a uma pessoa qualquer.
	 */
	@GlobalCommand
	@NotifyChange("allPessoa")
	public void atualizaAllPessoaSupervisao(@BindingParam("atualizaAllPessoaSupervisao") Pessoa selectedPessoa)
	{
		this.allPessoa = null;
		this.atualizaPessoa();
	}

	public void atualizaBindComponent(String bindingParam, String metodo, Object obj)
	{
		Map args = new HashMap();
		args.put(bindingParam, obj);
		BindUtils.postGlobalCommand(null, null, metodo, args);
	}
	
	/*
	 * Métodos utilizado para manipular a foto relacionada a Supervisão 
	*/ 
	@Listen("onUpload=#btnFotoSupervisao")
	public void addFoto(UploadEvent evt)
	{
		this.media = evt.getMedia();
	}
	
	@GlobalCommand
	public void carregarFoto(@BindingParam("carregarFoto") Media media)
	{
		this.media = media;
	}
	
	public void salvarFoto() throws IOException
	{
		if ( this.media != null )
		{			
			String path = "rti/alpha";//System.getProperty("user.home"); 
			 
			path += "/hydro/img/imagens/supervisao/" + this.selectedSupervisao.getDescricao() + "_" + this.media.getName();
			
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
			
			this.selectedSupervisao.setFoto(arquivo.getCanonicalPath());//toURI().toURL().getPath());//oString());//toURI().toString());//getPath());//toURI().toURL().getPath());//getCanonicalPath());//"/img/imagens/pessoas/" + this.selectedPessoa.getNome()+"_" + this.media.getName());
			
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
		File arquivo = new File(foto);//path + "img\\imagens\\" + nomeArquivo);
		
		arquivo.delete();
	}
}
