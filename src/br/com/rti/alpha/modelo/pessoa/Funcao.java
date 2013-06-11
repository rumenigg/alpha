package br.com.rti.alpha.modelo.pessoa;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Funcao {
	
	@Id
	@GeneratedValue
    private int id;
    private String funcao;
    
    @OneToMany(mappedBy="funcao")
    private Set<Pessoa> pessoa;
    
    public Funcao()
    {
    	super();
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	
	public Set<Pessoa> getPessoa()
	{
		return this.pessoa;
	}
	
	public void setPessoa(Set<Pessoa> pessoa)
	{
		this.pessoa = pessoa;
	}
    
    
 }
