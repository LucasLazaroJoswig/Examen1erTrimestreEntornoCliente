package cajero.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cajero.modelo.entities.Cuenta;
import cajero.modelo.entities.Movimiento;


public interface MovimientoRepository extends JpaRepository<Movimiento, Integer>{
	/*
	 * CrudRepository
	 * PagingAndSortingRepository
	 * JpaRepository
	 */
	@Query("select m from Movimiento m where m.cuenta.idCuenta = ?1")
    List<Movimiento> findByCuenta(int idCuenta );
	
}
