package cajero.modelo.dao;

import cajero.modelo.entities.Cuenta;

public interface CuentaDao {
	Cuenta findById(int idCuenta);
	int updateOne(Cuenta cuenta);
}
