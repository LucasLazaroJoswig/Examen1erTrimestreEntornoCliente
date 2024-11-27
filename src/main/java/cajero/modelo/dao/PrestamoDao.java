package cajero.modelo.dao;

import java.util.List;  

import cajero.modelo.entities.Cuenta;
import cajero.modelo.entities.Prestamo;



public interface PrestamoDao {
	Prestamo findById(int idPrestamo);
	int insertOne(Prestamo prestamo);
	int deleteOne(Prestamo prestamo);
	List<Prestamo> prestamosPorCuenta(Cuenta cuenta);

}
