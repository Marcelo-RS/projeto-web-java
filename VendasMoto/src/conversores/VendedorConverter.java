package conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modelo.dao.VendedorDAO;
import modelo.dominio.Vendedor;

@FacesConverter(value="vend-converter")
public class VendedorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		// System.out.println("CategoriaConverter.getAsObject()");
		
		Integer codigo;
		try {
			codigo = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			codigo = null;
		}
		
		if (codigo != null)
		{
			VendedorDAO dao = new VendedorDAO();
			// lê o vendedor do banco
			Vendedor vend = dao.lerPorId(codigo);
			
			return vend;  // retorna o vendedor lido
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (value instanceof Vendedor)
		{
			Vendedor vend = (Vendedor) value;
			// retorna o ID do objeto como String
			return vend.getId().toString();
		}
		
		return null;
	}

}
