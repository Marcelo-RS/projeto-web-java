package controle.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import modelo.dao.VendedorDAO;
import modelo.dao.MotoDAO;
import modelo.dominio.Vendedor;
import modelo.dominio.Moto;

@ManagedBean(name = "motoMB")
@RequestScoped
public class MotoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// binding de componentes
	private HtmlInputText campoModelo;

	// receber o código via <f:param >
	@ManagedProperty("#{param.codParam}")
	private String codParam;

	@ManagedProperty(value = "#{logimMB}")
	private LoginMB logimMB;
	
	private Moto moto = new Moto();
	
	private Vendedor filtroVendedor = null;
	private String filtroModelo = null;

	private MotoDAO dao = new MotoDAO();

	private List<Moto> motos = null;

	private List<Vendedor> vendedores = null;
	
	public String getContador(){
		
		String modelo = this.moto.getModelo();
		if (modelo == null)
			return "0";
		
		return String.valueOf(modelo.length());
	}
	
	// upload da Foto do produto
	private UploadedFile uploadedFile;

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public List<Moto> getMotos() {

		if (this.motos == null)
			this.motos = this.dao.lerTodos();

		return motos;
	}

	public List<Vendedor> getVendedores() {

		if (this.vendedores == null)
			this.vendedores = new VendedorDAO().lerTodos();

		return vendedores;
	}
	
	public HtmlInputText getCampoModelo() {
		return campoModelo;
	}

	public void setCampoModelo(HtmlInputText campoModelo) {
		this.campoModelo = campoModelo;
	}

	public String getCodParam() {
		return codParam;
	}

	public void setCodParam(String codParam) {
		this.codParam = codParam;
	}
	
	public Moto getMoto() {
		return moto;
	}

	public void setMoto(Moto moto) {
		this.moto = moto;
	}

	public Vendedor getFiltroVendedor() {
		return filtroVendedor;
	}

	public void setFiltroVendedor(Vendedor filtroVendedor) {
		this.filtroVendedor = filtroVendedor;
	}

	public String getFiltroModelo() {
		return filtroModelo;
	}

	public void setFiltroModelo(String filtroModelo) {
		this.filtroModelo = filtroModelo;
	}
	
	public LoginMB getLogimMB() {
		return logimMB;
	}

	public void setLogimMB(LoginMB logimMB) {
		this.logimMB = logimMB;
	}

	public void lerMoto() {

		if (codParam != null) {
			try {
				Integer id = Integer.parseInt(this.codParam);
				this.moto = this.dao.lerPorId(id);
			} catch (Exception e) {
			}
		}
	}

	public String acaoListar() {
		return "motoListar.jsf?faces-redirect=true";
	}

	public String acaoPesquisar() {

		this.motos = this.dao.filtrarMotos(filtroVendedor, filtroModelo);

		return "motoListar.jsf";
	}

	public String acaoAbrirInclusao() {

		this.moto = new Moto();

		return "motoEditar.jsf";
	}

	public String acaoAbrirAlteracao(Integer codigo) {

		this.moto = dao.lerPorId(codigo);
		return "motoEditar.jsf";
	}

	public String acaoExcluir(Integer codigo) {

		// ler objeto do banco
		this.moto = dao.lerPorId(codigo);

		this.dao.excluir(this.moto);

		return acaoListar();
	}

	public String acaoSalvar() {
		
		// recupera os bytes da foto
		byte[] foto = this.uploadedFile.getContents();

		if (foto != null && foto.length > 0)
			this.moto.setFoto(foto);

		this.dao.salvar(this.moto);

		return acaoListar();					
	}
	
	public String acaoCancelar() {

		return acaoListar();
	}
	
	public void download() throws IOException {

		FacesContext contexto = FacesContext.getCurrentInstance();
		ExternalContext external = contexto.getExternalContext();
		OutputStream saida = external.getResponseOutputStream();

		String codigo = external.getRequestParameterMap().get("codigo");

		// limpa qualquer resposta que tenha sido colocada no cache
		external.responseReset();
		// indica o tipo de conteúdo retornado
		external.setResponseContentType("image/jpeg");
		// indica qual o nome do arquivo
		external.setResponseHeader("Content-Disposition", "inline; filename=foto-" + codigo + ".jpg");
		// indica que não deve guardar cache da imagem no browser
		external.setResponseHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		external.setResponseHeader("Pragma", "no-cache"); // HTTP 1.0
		external.setResponseHeader("Expires", "0"); // Proxies.

		Integer id = null;
		Moto mot = null;
		try {
			id = Integer.parseInt(codigo);
			// lê o produto do banco
			mot = dao.lerPorId(id);
		} catch (Exception e) {
		}

		if (mot != null) {
			byte[] foto = mot.getFoto();

			if (foto != null) {
				// define o tamanho da resposta
				external.setResponseContentLength(foto.length);
				// descarrega os bytes na resposta
				saida.write(foto);
			}
		}
		// indica ao JSF que o processamento foi completado e ele deve finalizar
		contexto.responseComplete();
	}
}
