package cajero.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cajero.modelo.entities.Cuenta;
import cajero.modelo.entities.Movimiento;
import cajero.modelo.entities.Prestamo;
import cajero.modelo.repository.CuentaRepository;
import cajero.modelo.repository.MovimientoRepository;
import cajero.modelo.repository.PrestamoRepository;

@Repository
public class PrestamoDaoImplJpaMy8 implements PrestamoDao {

	@Autowired
	private PrestamoRepository prepo;
	
	@Override
	public int insertOne(Prestamo prestamo) {
		// TODO Auto-generated method stub
		return (prepo.save(prestamo) != null) ? 1 : 0;
	}

	@Override
	public int deleteOne(Prestamo prestamo) {
		if (prepo.existsById(prestamo.getIdPrestamo())) {
			prepo.delete(prestamo);
			return 1;
		}
		return 0;
	}

	@Override
	public List<Prestamo> prestamosPorCuenta(Cuenta cuenta) {
		// TODO Auto-generated method stub
		return prepo.findByCuenta(cuenta.getIdCuenta());
	}

	@Override
	public Prestamo findById(int idPrestamo) {
		// TODO Auto-generated method stub
		return prepo.findById(idPrestamo).orElse(null);
	}

	

}
