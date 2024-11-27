package cajero.modelo.dao;

import java.util.List;

import cajero.modelo.entities.Cuenta;
import cajero.modelo.entities.Movimiento;

public interface MovimientoDao {
	int insertOne(Movimiento movimiento);
	List<Movimiento> movimientosPorCuenta(Cuenta cuenta);
}
