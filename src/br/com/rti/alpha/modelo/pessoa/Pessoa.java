
package br.com.rti.alpha.modelo.pessoa;

import java.util.Set;

import javax.persistence.*;

import br.com.rti.alpha.modelo.supervisao.Frota;
import br.com.rti.alpha.modelo.supervisao.Supervisao;

@Entity
public class Pessoa {
	
	@Id
	@GeneratedValue
    private int id;
    private String matricula;
    private String nome;
    private String email;
    private String senha;
    private String foto;
    
    public Pessoa()
    {
    	super();
    }
    
    public Pessoa(String matricula, String senha)
    {
    	this.matricula = matricula;
    	this.senha = senha;
    }
    
    @ManyToOne
    @JoinColumn(name="funcao_id")
    public br.com.rti.alpha.modelo.pessoa.Funcao funcao;
    
    @OneToMany(mappedBy="pessoa", cascade = CascadeType.PERSIST)
    public Set<Frota> frota;
    
    @ManyToOne
    @JoinColumn(name="supervisao_id")
    public Supervisao pessoaSupervisao;
    
    @OneToOne(mappedBy="pessoaResponsavelSupervisao", fetch = FetchType.LAZY)
    public Supervisao supervisao;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public br.com.rti.alpha.modelo.pessoa.Funcao getFuncao() {
		return funcao;
	}
	public void setFuncao(br.com.rti.alpha.modelo.pessoa.Funcao funcao) {
		this.funcao = funcao;
	}
    public Set<Frota> getFrota()
    {
    	return this.frota;
    }
    public void setFrota(Set<Frota> frota)
    {
    	this.frota = frota;
    }    
	public Supervisao getPessoaSupervisao() {
		return pessoaSupervisao;
	}
	public void setPessoaSupervisao(Supervisao pessoaSupervisao) {
		this.pessoaSupervisao = pessoaSupervisao;
	}
	public Supervisao getSupervisao() {
		return supervisao;
	}
	public void setSupervisao(Supervisao supervisao) {
		this.supervisao = supervisao;
	}      
 }