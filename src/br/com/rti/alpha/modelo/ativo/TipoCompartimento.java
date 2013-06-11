
package br.com.rti.alpha.modelo.ativo;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class TipoCompartimento {

	@Id
	@GeneratedValue
    private int id;
	
    private String descricao;
    private String foto;
    
    @OneToMany(mappedBy="tipoCompartimento") 
    public Set<Compartimento> compartimento;
    
    //public br.com.rti.alpha.modelo.amostra.limiteTendencia limiteTendencia;
    
    public TipoCompartimento()
    {
    	super();
    }
    
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
	/*public br.com.rti.alpha.modelo.amostra.limiteTendencia getLimiteTendencia() {
		return limiteTendencia;
	}
	public void setLimiteTendencia(
			br.com.rti.alpha.modelo.amostra.limiteTendencia limiteTendencia) {
		this.limiteTendencia = limiteTendencia;
	} */   
	
	public Set<Compartimento> getCompartimento()
	{
		return this.compartimento;
	}
	
	public void setCompartimento(Set<Compartimento> compartimento)
	{
		this.compartimento = compartimento;
	}
 }
