package br.com.rti.alpha.controle;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.dao.PessoaDAO;
import br.com.rti.alpha.modelo.pessoa.Pessoa;

public class Loging extends SelectorComposer<Window>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**br.Login
	 * 
	 */	
	@Wire
	private Textbox matricula;
	@Wire
	private Textbox senha;
	@Wire
	private Label msg;
	@Wire
	private Window winlogin;	
	
	@Listen("onOK=#winlogin")
	public void onOK()
	{
		this.confirm();
	}
	
	@Listen("onOpen=#winlogin")
	public void limparCampos()
	{
		this.matricula.setValue("");
		this.senha.setValue("");
	}
	
	@Listen("onClick=#btnLogin")
	public void confirm()
	{
		Pessoa pessoa = new Pessoa();
		pessoa.setMatricula(matricula.getValue());
		pessoa.setSenha(senha.getValue());
		try
		{			
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
			
			PessoaDAO pdao = daof.getPessoaDAO();
			
			Pessoa pes = pdao.existePessoa(pessoa);
			
			if ( pes != null )
			{
				winlogin.setVisible(false);
				winlogin.getParent().getFellow("principalArea").setVisible(true);
			}
			else
			{				
				msg.setVisible(true);				
				msg.setValue("Matrícula ou senha incorreta, tente novamente!");
				Clients.evalJavaScript("loginFaild()");
			}
			//daof.close();
						
		}
		catch (Exception e)
		{
			Messagebox.show("Problema de conexão com o Banco de Dados", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
			
			e.printStackTrace();
		}		
	}
}
