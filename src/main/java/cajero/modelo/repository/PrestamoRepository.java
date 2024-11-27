package cajero.modelo.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cajero.modelo.entities.Cuenta;
import cajero.modelo.entities.Prestamo;


public interface PrestamoRepository extends JpaRepository<Prestamo, Integer>{
	/*
	 * CrudRepository
	 * PagingAndSortingRepository
	 * JpaRepository
	 */
	@Query("select p from Prestamo p where p.cuenta.idCuenta = ?1")
    List<Prestamo> findByCuenta(int idCuenta );
	
}
