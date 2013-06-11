
package br.com.rti.alpha.modelo.amostra;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class TipoColeta 
{
	
	@Id
	@GeneratedValue
    private int id;
    private String descricao;
    private String obs;
    
	@ManyToMany(targetEntity=br.com.rti.alpha.modelo.amostra.PlanoTrabalho.class, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="PlanoTrabalho_TipoColeta", 
			joinColumns={@JoinColumn(name="tipocoleta_id")},
			inverseJoinColumns={@JoinColumn(name="planotrabalho_id")})
	private List<PlanoTrabalho> planoTrabalho;
	
	@OneToMany(mappedBy="tipoColetaAmostra", fetch = FetchType.LAZY)
    public Set<Amostra> amostra;
    
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
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}    
	public List<PlanoTrabalho> getPlanoTrabalho()
	{
		return this.planoTrabalho;
	}	
	public void setPlanoTrabalho(List<PlanoTrabalho> planoTrabalho)
	{
		this.planoTrabalho = planoTrabalho;
	}	
	public Set<Amostra> getAmostra() {
		return amostra;
	}
	public void setAmostra(Set<Amostra> amostra) {
		this.amostra = amostra;
	}
	
	/*public void removePlanoTrabalho(List<PlanoTrabalho> planoTrabalho)
	{
		this.planoTrabalho.removeAll(planoTrabalho);
	}*/
    
}
