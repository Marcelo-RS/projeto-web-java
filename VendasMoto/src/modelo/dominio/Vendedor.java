package modelo.dominio;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tab_vendedores")
public class Vendedor extends Pessoa{
	
	public Vendedor(){
		super();
	}
	private float comissao;

	@OneToMany(mappedBy = "vendedor", fetch = FetchType.LAZY)
	private List<Moto> motos;
	
	
	public float getComissao() {
		return comissao;
	}

	public void setComissao(Integer comissao) {
		this.comissao = comissao;
	}

	public List<Moto> getMotos() {
		return motos;
	}

	public void setMotos(List<Moto> motos) {
		this.motos = motos;
	}

	@Override
	public String toString() {
		return "Vendedor [comissao=" + comissao + ", motos=" + motos + "]";
	}
}
