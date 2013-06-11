package br.com.rti.alpha.viewModel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.hibernate.exception.ConstraintViolationException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Image;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.pessoa.Funcao;
import br.com.rti.alpha.modelo.pessoa.Pessoa;
import br.com.rti.alpha.modelo.supervisao.Frota;
import br.com.rti.alpha.modelo.supervisao.Supervisao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class PessoaVM
{
	@Wire
	private Toolbarbutton tbtnNovoPessoa;
	
	@Wire("#cbxFuncao")
	private Combobox cbxFuncao;
	
	@Wire
	private Combobox cbxSupervisao;
	
	@Wire
	private Image foto;
	
	private List<Pessoa> allPessoa = new ArrayList<Pessoa>();
	private Pessoa selectedPessoa;
	private List<Funcao> allFuncao = null;
	private Funcao selectedFuncao;
	private List<Supervisao> allSupervisao = null;
	private Supervisao selectedSupervisao;
	private String confirmarSenha;
	
	private boolean desativado = true;
	
	private int navegador = 0;
	
	private Media media;
	
	public List<Pessoa> getAllPessoa()
	{
		return this.allPessoa;
	}
	
	public void setAllPessoa(List<Pessoa> allPessoa)
	{
		this.allPessoa = allPessoa;
	}
	
	public List<Funcao> getAllFuncao()
	{
		return this.allFuncao;
	}
	
	public void setAllFuncao(List<Funcao> allFuncao)
	{
		this.allFuncao = allFuncao;
	}
	
	public Pessoa getSelectedPessoa()
	{
		return this.selectedPessoa;
	}
	
	public void setSelectedPessoa(Pessoa selectedPessoa)
	{
		this.selectedPessoa = selectedPessoa;
	}	
	
	public Funcao getSelectedFuncao()
	{		
		return selectedFuncao;
	}
	
	public void setSelectedFuncao(Funcao selectedFuncao)
	{
		this.selectedFuncao = selectedFuncao;
	}
	
	public List<Supervisao> getAllSupervisao() {
		return allSupervisao;
	}

	public void setAllSupervisao(List<Supervisao> allSupervisao) {
		this.allSupervisao = allSupervisao;
	}

	public Supervisao getSelectedSupervisao() {
		return selectedSupervisao;
	}

	public void setSelectedSupervisao(Supervisao selectedSupervisao) {
		this.selectedSupervisao = selectedSupervisao;
	}

	public String getConfirmarSenha()
	{
		return this.confirmarSenha;
	}
	
	public void setConfirmarSenha(String confirmarSenha)
	{
		this.confirmarSenha = confirmarSenha;
	}
	
	public boolean getDesativado()
	{
		return this.desativado;
	}
	
	public void setDesativado(boolean desativado)
	{
		this.desativado = desativado;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		Selectors.wireEventListeners(view, this);
		
		Clients.showNotification("Clique aqui para adicionar uma nova Pessoa", "info", this.tbtnNovoPessoa, "end_center", 3000);
	}
	
	@Init
	@GlobalCommand
	public void init()
	{		
		this.selectedPessoa = new Pessoa();
		this.selectedFuncao = new Funcao();
		this.selectedSupervisao = new Supervisao();

		this.atualizaPessoa();		
		this.atualizaFuncao();
		this.atualizaSupervisao();
	}
	
	@GlobalCommand
	@NotifyChange("allPessoa")
	public void atualizaPessoa()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allPessoa = daof.getPessoaDAO().listaTudo();
		
		Ordenar op = new Ordenar();
		Collections.sort(this.allPessoa, op);
		op = null;
		//daof.close();
	}
	
	public void atualizaFuncao()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allFuncao = daof.getFuncaoDAO().listaTudo();
		
		Ordenar op = new Ordenar();
		Collections.sort(this.allFuncao, op);
		op = null;
		//daof.close();
	}
	
	public void atualizaSupervisao()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allSupervisao = daof.getSupervisaoDAO().listaTudo();
			
		Ordenar o = new Ordenar();
		Collections.sort(this.allSupervisao, o);
		o = null;
		
		Supervisao s = new Supervisao();
		s.setDescricao("Nenhum");
		
		this.allSupervisao.add(0, s);
	}
	
	@GlobalCommand
	@NotifyChange("allFuncao")
	public void atualiza(@BindingParam("refreshList") Object o)
	{
		if ( o instanceof Funcao )
		{
			this.allFuncao = null;
			this.atualizaFuncao();
		}
		if ( o instanceof Supervisao )
		{
			this.allSupervisao = null;
			this.atualizaSupervisao();
		}
	}
		
	@Command
	@NotifyChange({"selectedPessoa","selectedFuncao","selectedSupervisao","confirmarSenha","desativado"})
	public void novoRegistro()
	{
		this.desativado = false;
		
		this.selectedPessoa = null;		
		this.selectedFuncao = null;
		this.selectedSupervisao = null;
		this.confirmarSenha = null;
		this.selectedPessoa = new Pessoa();
		
		//this.confirmarSenha = new String();
	}
	
	@Command
	@NotifyChange({"selectedPessoa", "selectedFuncao","selectedSupervisao","confirmarSenha","desativado"})
	public void submit() 
	{
		try
		{	
			if (selectedPessoa.getMatricula().equalsIgnoreCase("HA1234"))
				Messagebox.show("Você não pode alterar os dados dessa pessoa, \nela é utiliza para testes pelo desenvolvedor.",
						"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.EXCLAMATION);
			else
			{
				DaoFactory daof = new DaoFactory();
				daof.beginTransaction();
			
				this.selectedPessoa.setFuncao(this.selectedFuncao);
				
				if ( this.selectedSupervisao != null )
					if ( this.selectedSupervisao.getDescricao().equalsIgnoreCase("nenhum") )
						this.selectedPessoa.setPessoaSupervisao(null);
					else
						this.selectedPessoa.setPessoaSupervisao(this.selectedSupervisao);
				
				if ( this.media != null && this.selectedPessoa.getFoto() != null )
					this.delFoto(this.selectedPessoa.getFoto());
				
				this.salvarFoto();
				
				daof.getPessoaDAO().adiciona(selectedPessoa);
				//daof.commit();
		
				Messagebox.show("A pessoa " + this.selectedPessoa.getNome().toUpperCase() + " foi adicionada ou atualizada com sucesso.", 
								"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);
				
				this.atualizaBindComponent("atualizaListas", "atualizaListas", this.selectedPessoa);
				this.atualizaBindComponent("atualizaAllPessoaFrota", "atualizaAllPessoaFrota", this.selectedPessoa);
				this.atualizaBindComponent("atualizaAllPessoaSupervisao", "atualizaAllPessoaSupervisao", this.selectedPessoa);

				this.selectedPessoa = null;
				this.selectedFuncao = null;
				this.selectedSupervisao = null;
				this.confirmarSenha = null;
				this.novoRegistro();
				this.atualizaPessoa();
				
				this.desativado = true;
			}
		}
		catch (Exception e)
		{
			Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema.",
							"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@Command
	@NotifyChange("selectedPessoa")
	public void excluirRegistro()
	{
		try
		{
			Messagebox.show("Você realmente deseja excluir a pessoa " + this.selectedPessoa.getNome().toUpperCase() + "?", "Hydro - Projeto Alpha",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
					new EventListener<Event>() {
						public void onEvent(Event event) throws SQLException, ConstraintViolationException, MySQLIntegrityConstraintViolationException
						{
							if (Messagebox.ON_YES.equals(event.getName()))
							{
								try
								{
									if (selectedPessoa.getMatricula().equalsIgnoreCase("HA1234"))
										Messagebox.show("Você não pode excluir essa pessoa, \nela é utiliza para testes pelo desenvolvedor.",
												"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.EXCLAMATION);
									else
									{
										DaoFactory daof = new DaoFactory();
										daof.beginTransaction();									
										
										selectedPessoa = daof.getPessoaDAO().procura(selectedPessoa.getId());
										if ( !selectedPessoa.getFrota().isEmpty() )
										{
											for ( Frota sf : selectedPessoa.getFrota() )
											{
												sf.setPessoa(null);
											}
										}										
										
										if ( selectedPessoa.getSupervisao() != null )
										{
											Messagebox.show("Você não pode excluir essa pessoa por que ela é responsável pela Supervisão - " + 
													selectedPessoa.getSupervisao().getDescricao().toUpperCase()+
													". Substitua por outro responsável para poder excluí-la.",
													"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
										}
										else
										{
											//BufferedImage imagem = ImageIO.read( this.media.getStreamData() );
											if ( selectedPessoa.getFoto() != null )
												delFoto(selectedPessoa.getFoto());										
										
											daof.getPessoaDAO().remove(selectedPessoa);
																				
											//daof.commit();
											Messagebox.show("Pessoa excluida com sucesso.", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);
									
											atualizaBindComponent("atualizaListas", "atualizaListas", selectedPessoa);
											atualizaBindComponent("atualizaAllPessoaFrota", "atualizaAllPessoaFrota", selectedPessoa);
											atualizaBindComponent("atualizaAllPessoaSupervisao", "atualizaAllPessoaSupervisao", selectedPessoa);
										
											//novoRegistro();
											atualizaPessoa();																								
											navegar("proximo");
											BindUtils.postNotifyChange(null,null,this,"selectedPessoa");
										}									}
								}
								catch (ConstraintViolationException cve)
								{
									Messagebox.show("Você não pode excluir essa pessoa pois ela está associada a uma supervisão ou frota. Substitua a pessoa, na supervisão ou na frota, por outra para poder excluir.",
											"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
								}
								catch (Exception e)
								{
									Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
													"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
									e.printStackTrace();									
								}								
							}
						}			
			});			
		}
		catch (ConstraintViolationException cve)
		{
			Messagebox.show("Você não pode excluir essa pessoa pois ela está associada a uma supervisão ou frota. Substitua a pessoa, na supervisão ou na frota, por outra para poder excluir.",
					"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
		}
		catch (NullPointerException n)
		{
			Messagebox.show("Selecione uma Pessoa para a exclusão!", "Hydro - Projeto Alpha", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}		
	}
	
	@Command
	@NotifyChange({"selectedPessoa","selectedFuncao","confirmarSenha","desativado"})
	public void navegar(@BindingParam("acao") String acao) throws IOException
	{
		this.desativado = false;
		this.selectedPessoa = null;
		this.selectedPessoa = new Pessoa();
		if ( !allPessoa.isEmpty() )
		{
			if ( acao.equals("primeiro") )
			{
				this.navegador = 0;
				this.showSelectedPessoaItem( this.navegador );//this.selectedPessoa = this.allPessoa.get(this.navegador) );
				/*this.confirmarSenha = this.selectedPessoa.getSenha();
				this.selectFuncao(this.selectedPessoa.getFuncao());
				this.selectSupervisao(this.selectedPessoa.getPessoaSupervisao());*/
			}
			if ( acao.equals("anterior") )
			{
				if ( this.navegador > 0 )
				{
					this.showSelectedPessoaItem( --this.navegador );//this.selectedPessoa = this.allPessoa.get(--this.navegador) );
					/*this.confirmarSenha = this.selectedPessoa.getSenha();
					this.selectFuncao(this.selectedPessoa.getFuncao());
					this.selectSupervisao(this.selectedPessoa.getPessoaSupervisao());*/
				}
				else 
					this.navegar("primeiro");
			}
			if ( acao.equals("proximo") )
			{
				if ( this.navegador < this.allPessoa.size()-1 )
				{
					this.showSelectedPessoaItem( ++this.navegador );//this.selectedPessoa = this.allPessoa.get(++this.navegador) );
					/*this.confirmarSenha = this.selectedPessoa.getSenha();
					this.selectFuncao(this.selectedPessoa.getFuncao());
					this.selectSupervisao(this.selectedPessoa.getPessoaSupervisao());*/
				}
				else
					this.navegar("ultimo");
			}
			if ( acao.equals("ultimo") )
			{
				this.showSelectedPessoaItem( this.navegador = this.allPessoa.size()-1 );//this.selectedPessoa = this.allPessoa.get( this.navegador = this.allPessoa.size()-1 ) );
				/*this.confirmarSenha = this.selectedPessoa.getSenha();
				this.selectFuncao(this.selectedPessoa.getFuncao());
				this.selectSupervisao(this.selectedPessoa.getPessoaSupervisao());*/
			}
		}
	}
	
	@NotifyChange("selectedFuncao")
	public void selectFuncao(Funcao funcao)
	{
		this.selectedFuncao = funcao;
		
		if ( this.selectedFuncao != null )
		{
			for (int i = 0; i < this.allFuncao.size(); i++)
			{
				Funcao f = this.allFuncao.get(i);
				if (f.getId() == this.selectedFuncao.getId())
					this.cbxFuncao.setSelectedIndex(i);
			}
		}
	}
	
	@NotifyChange("selectedSupervisao")
	public void selectSupervisao(Supervisao s)
	{
		if ( s != null )
		{
			this.selectedSupervisao = s;
			
			for ( int i = 0; i < this.allSupervisao.size(); i++)
			{
				Supervisao su = this.allSupervisao.get(i);
				if ( su.getId() == this.selectedSupervisao.getId() )
					this.cbxSupervisao.setSelectedIndex(i);
			}
		}
		else this.selectedSupervisao = null;
	}
	
	@GlobalCommand
	@NotifyChange({"selectedPessoa","selectedFuncao","selectedSupervisao","confirmarSenha","desativado","selectedFoto"})
	public void showSelectedPessoaItem(@BindingParam("atualizaPessoa") int i ) throws IOException
	{
		this.desativado = false;
		
		this.selectedPessoa = null;
		//this.selectedPessoa = selectedPessoa;
		
		this.selectedPessoa = this.allPessoa.get( this.navegador = i );
		
		this.selectFuncao(this.selectedPessoa.getFuncao());
		this.selectSupervisao(this.selectedPessoa.getPessoaSupervisao());
		
		this.confirmarSenha = this.selectedPessoa.getSenha();
		
		if ( (this.selectedPessoa.getFoto() != null) && !this.selectedPessoa.getFoto().isEmpty() )
		{
			org.zkoss.image.AImage img = new org.zkoss.image.AImage(this.selectedPessoa.getFoto());
			this.foto.setContent(img);
		}
		else
			this.foto.setSrc(null);
		//this.foto.setSrc(this.selectedPessoa.getFoto());		
	}

	public void atualizaBindComponent(String bindingParam, String metodo, Object obj)
	{
		Map args = new HashMap();
		args.put(bindingParam, obj);
		BindUtils.postGlobalCommand(null, null, metodo, args);
	}
	/*
	 * Método responsável por receber a imagem, foto da pessoa e salvar
	 */
	@Listen("onUpload=#btnFoto")
	public void addFoto(UploadEvent evt)
	{
		this.media = evt.getMedia();
	}
	
	public void salvarFoto() throws IOException
	{
		if ( this.media != null )
		{
			/*String url = getClass().getResource(getClass().getSimpleName() + ".class").getPath();//.replaceAll("%20", " ");//.substring(1, getClass().getResource(getClass().getSimpleName() + ".class").getPath().indexOf("WEB-INF"));
			if ( url.contains("%20") )
				url = url.replaceAll("%20%", " ");
		
			File dir = new File(url).getParentFile().getCanonicalFile();
		
			String path = dir.getAbsolutePath().substring(0, dir.getAbsolutePath().indexOf("WEB-INF"));
			*/
		
			/*String path2 = getClass().getResource(getClass().getSimpleName() + ".class").getPath().substring(1, getClass().getResource(getClass().getSimpleName() + ".class").getPath().indexOf("/WEB-INF"));
		
			System.out.println("Endereço 1:\t" + path +
							"\nEndereço 2:\t" + path2);//getClass().getResource("/").getPath());//esource("/"));//getClass().getResource("alpha/web"));//getPackage().getName());
			 */
			String path = "rti/alpha";//System.getProperty("user.home"); 
			//String extraPath = "";
			//if ( path.contains("\\") )
				//extraPath = "img\\imagens\\pessoas\\" + this.selectedPessoa.getNome()+"_" + this.media.getName();
			//else
			path += "/hydro/img/imagens/pessoas/" + this.selectedPessoa.getNome()+"_" + this.media.getName();
			
			BufferedImage imagem = ImageIO.read( this.media.getStreamData() );
			File arquivo = new File(path);
			arquivo.mkdirs();
			// fazer algo com a imagem...
			ImageIO.write(imagem, "PNG", arquivo);
			
			/*System.out.println("Src: \t" + media.getName() +
							"\nContext" + media.getContentType()+
							"\nArquivo gerado:\t\t" + arquivo.getName() + "\t" + arquivo.getAbsolutePath() + "\t" + arquivo.toURI().getPath() +
							"\nCaminho da Classe(Canonical): " + arquivo.getCanonicalPath() +
							"\nCaminho da URI-Path: " + arquivo.toURI().getPath());		
			 */
			this.selectedPessoa.setFoto(arquivo.getCanonicalPath());//"/img/imagens/pessoas/" + this.selectedPessoa.getNome()+"_" + this.media.getName());
		}
	}
	
	public void delFoto(String foto) throws IOException
	{
		/*String nomeArquivo = foto.substring(foto.indexOf("pessoas"), foto.length());
		if ( nomeArquivo.contains("/") )
			nomeArquivo = nomeArquivo.replace("/", "\\");
		
		System.out.printf("--> Nome do arquivo: \t%s\n", nomeArquivo);
		
		String url = getClass().getResource(getClass().getSimpleName() + ".class").getPath().replaceAll("%20", " ");//.substring(1, getClass().getResource(getClass().getSimpleName() + ".class").getPath().indexOf("WEB-INF"));
		if ( url.contains("%20") )
			url = url.replaceAll("%20%", " ");
		
		System.out.printf("--> URL Normal: \t%s\n", url);
		
		File dir = new File(url).getParentFile().getCanonicalFile();
		
		String path = dir.getAbsolutePath().substring(0, dir.getAbsolutePath().indexOf("WEB-INF"));		
		
		System.out.printf("--> Path Normal: \t%s\n", path);
		
		System.out.println("--> Path Completo: \t" + path + "img\\imagens\\" + nomeArquivo);*/
		
		File arquivo = new File(foto);//path + "img\\imagens\\" + nomeArquivo);
		
		arquivo.delete();
	}
	/*
	@NotifyChange("foto")
	public void recuperarFoto() throws IOException
	{
		if ( !this.selectedPessoa.getFoto().isEmpty() )
		{
			Media media;
			BufferedImage imagem = ImageIO.read( new File(this.selectedPessoa.getFoto()));
			media = (Media) imagem;
			this.foto = (org.zkoss.image.Image)media;
		}
	}*/
	
}
