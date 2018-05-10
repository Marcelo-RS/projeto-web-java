package modelo.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import modelo.dominio.Vendedor;
import modelo.dominio.Moto;

public class MotoDAO extends JpaDAO<Moto> {

	public List<Moto> filtrarMotos(Vendedor vend, String modelo) {
		String jpql = "from Moto m ";

		if (vend != null) {
			jpql = jpql + " where m.vendedor = :vende";
		} else if (modelo != null) {
			jpql = jpql + " where m.modelo like :modelo";
		}

		jpql = jpql + " order by m.modelo";

		TypedQuery<Moto> comando = this.getEntityManager().createQuery(jpql, Moto.class);

		if (vend != null)
			comando.setParameter("vende", vend);
		else if (modelo != null)
			comando.setParameter("modelo", "%" + modelo + "%");

		return comando.getResultList();
	}

}
