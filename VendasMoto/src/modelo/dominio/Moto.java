package modelo.dominio;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tab_motos")
public class Moto {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ID_MOTO")
	@SequenceGenerator(name="ID_MOTO", sequenceName="SEQ_MOTO", allocationSize=1)
	private Integer codigo;
	
	@Column(length=10)
	private String placa;
	
	@Column(length=120)
	private String marca;
	
	@Column(length=120)
	private String modelo;
	
	@Column(length=120)
	private String cor;
	
	@Column(name="preco_custo", columnDefinition="NUMERIC(15,2)")
	private Float precoCusto;
	
	@Column(name="preco_venda", columnDefinition="NUMERIC(15,2)")
	private Float precoVenda;
	
	@Column(name="qtd_estoque")
	private Integer qtdEstoque;
	
	@ManyToOne
	@JoinColumn(name="id_vendedor_fk")
	private Vendedor vendedor;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	private byte[] foto;
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Float getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(Float precoCusto) {
		this.precoCusto = precoCusto;
	}

	public Float getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(Float precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	@Override
	public String toString() {
		return "Moto [codigo=" + codigo + ", placa=" + placa + ", marca=" + marca + ", modelo=" + modelo + ", cor="
				+ cor + ", precoCusto=" + precoCusto + ", precoVenda=" + precoVenda + ", qtdEstoque=" + qtdEstoque
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Moto other = (Moto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
