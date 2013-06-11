
package br.com.rti.alpha.modelo.supervisao;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.pessoa.Pessoa;

@Entity
public class Frota {

	@Id
	@GeneratedValue
    private int id;
    private String descricao;
    private String foto;
    private String obs;
    
    @ManyToOne
    @JoinColumn(name="pessoa_id")
    public Pessoa pessoa;
    
    @OneToMany(mappedBy="frota")
    public Set<Ativo> ativo;
    
    @ManyToOne
    @JoinColumn(name="supervisao_id")
    public Supervisao supervisao;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Set<Ativo> getAtivo() {
		return this.ativo;
	}
	public void setAtivo(Set<Ativo> ativo) {
		this.ativo = ativo;
	}
    public Pessoa getPessoa()
    {
    	return this.pessoa;    	
    }
    public void setPessoa(Pessoa pessoa)
    {
    	this.pessoa = pessoa;
    }
	public Supervisao getSupervisao() {
		return supervisao;
	}
	public void setSupervisao(Supervisao supervisao) {
		this.supervisao = supervisao;
	}    
    
 }
