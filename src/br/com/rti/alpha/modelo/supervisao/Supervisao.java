
package br.com.rti.alpha.modelo.supervisao;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.rti.alpha.modelo.pessoa.Pessoa;

@Entity
public class Supervisao {

	@Id
	@GeneratedValue
    private int id;
    private String descricao;
    private String local;
    private String foto;
    private String obs;
    
    @OneToMany(mappedBy="pessoaSupervisao", fetch=FetchType.LAZY)
    public Set<Pessoa> pessoaSupervisao;    
    
    @OneToOne
    @JoinColumn(name="pessoa_id")
    public Pessoa pessoaResponsavelSupervisao;
    
    @OneToMany(mappedBy="supervisao", fetch=FetchType.LAZY)
    public Set<Frota> frota;    
    
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
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
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
	public Set<Pessoa> getPessoaSupervisao() {
		return pessoaSupervisao;
	}
	public void setPessoaSupervisao(Set<Pessoa> pessoaSupervisao) {
		this.pessoaSupervisao = pessoaSupervisao;
	}
	public Pessoa getPessoaResponsavelSupervisao() {
		return pessoaResponsavelSupervisao;
	}
	public void setPessoaResponsavelSupervisao(Pessoa pessoaResponsavelSupervisao) {
		this.pessoaResponsavelSupervisao = pessoaResponsavelSupervisao;
	}	
	public Set<Frota> getFrota() {
		return frota;
	}
	public void setFrota(Set<Frota> frota) {
		this.frota = frota;
	}
 }
