
package br.com.rti.alpha.modelo.amostra;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import br.com.rti.alpha.modelo.ativo.Compartimento;

@Entity
public class PlanoTrabalho 
{

	@Id
	@GeneratedValue
    private int id;
    private int plano;
    private String obs;
    
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="planoTrabalho", targetEntity=br.com.rti.alpha.modelo.amostra.TipoColeta.class)
    private List<TipoColeta> tipoColeta;
    
    @OneToMany(mappedBy="planoTrabalhoAmostra")
    private Set<Amostra> amostra;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPlano() {
		return plano;
	}
	public void setPlano(int plano) {
		this.plano = plano;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public List<TipoColeta> getTipoColeta() {
		return tipoColeta;
	}
	public void setTipoColeta(List<TipoColeta> tipoColeta) {
		this.tipoColeta = tipoColeta;
	}
	public Set<Amostra> getAmostra() {
		return amostra;
	}
	public void setAmostra(Set<Amostra> amostra) {
		this.amostra = amostra;
	}
    
    
}
