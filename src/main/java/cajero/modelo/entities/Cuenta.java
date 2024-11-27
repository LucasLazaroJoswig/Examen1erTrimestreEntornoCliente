package cajero.modelo.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id_cuenta")
    private int idCuenta;

    @Column(name="saldo")
    private double saldo;

    @Column(name="tipo_cuenta")
    private String tipoCuenta;

    // Método específico de la clase que no es parte del código repetitivo generado por Lombok
    public String ingresar(double importe) {
    	
            this.saldo += importe;
            return "Ingreso Correcto";

    }
    public boolean extraer(double importe) {

        	if (importe>this.saldo) {
                return false;
			} else {
				this.saldo -= importe;
				return true;
			}
            
    }
    public boolean tranferir(Cuenta cuentaDestino, double importe) {
    	if (this.extraer(importe)) {
    		cuentaDestino.ingresar(importe);
    		return true;
    	}else {
    		return false;
    	}
    }
}
