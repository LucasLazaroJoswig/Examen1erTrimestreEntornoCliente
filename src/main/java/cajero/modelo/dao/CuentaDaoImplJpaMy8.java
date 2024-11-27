package cajero.modelo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cajero.modelo.entities.Cuenta;
import cajero.modelo.repository.CuentaRepository;
@Repository
public class CuentaDaoImplJpaMy8 implements CuentaDao{

	@Autowired
	private CuentaRepository crepo;
	
	@Override
	public Cuenta findById(int idCuenta) {
		// TODO Auto-generated method stub
		return crepo.findById(idCuenta).orElse(null);
	}

	@Override
	public int updateOne(Cuenta cuenta) {
		// TODO Auto-generated method stub
		if (crepo.existsById(cuenta.getIdCuenta())) {
			crepo.save(cuenta);
			return 1;
		}
		return 0;
	}


}
