package br.com.rti.alpha.modelo.amostra;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class FotoAmostra 
{
	@Id
	@GeneratedValue
	private int id;
	private String foto;
	private String comentario;
	
	@ManyToOne
	@JoinColumn(name="amostra_id")
	private Amostra amostraFoto;

	public FotoAmostra()
	{
		
	}
	
	public FotoAmostra(String comentario, String foto)
	{
		this.foto = foto;
		this.comentario = comentario;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Amostra getAmostraFoto() {
		return amostraFoto;
	}

	public void setAmostraFoto(Amostra amostraFoto) {
		this.amostraFoto = amostraFoto;
	}

}
