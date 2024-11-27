package cajero.modelo.repository;
import java.util.List;   

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import cajero.modelo.entities.Cuenta;


public interface CuentaRepository extends JpaRepository<Cuenta, Integer>{
	/*
	 * CrudRepository
	 * PagingAndSortingRepository
	 * JpaRepository
	 */
	
}
