package cajero.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cajero.modelo.entities.Cuenta;
import cajero.modelo.entities.Movimiento;
import cajero.modelo.repository.CuentaRepository;
import cajero.modelo.repository.MovimientoRepository;

@Repository
public class MovimientoDaoImplJpaMy8 implements MovimientoDao {

	@Autowired
	private MovimientoRepository mrepo;
	
	@Override
	public int insertOne(Movimiento movimiento) {
		// TODO Auto-generated method stub
		return (mrepo.save(movimiento) != null) ? 1 : 0;
	}

	@Override
	public List<Movimiento> movimientosPorCuenta(Cuenta cuenta) {
		// TODO Auto-generated method stub
		return mrepo.findByCuenta(cuenta.getIdCuenta());
	}

	

}
