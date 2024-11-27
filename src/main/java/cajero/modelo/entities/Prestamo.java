package cajero.modelo.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="prestamos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_prestamo")
    private int idPrestamo;
	
	private String descripcion;
	
	@Column(name="cantidad_prestamo")
	private double cantidadPrestamo;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_prestamo")
    private Date fechaPrestamo;
	
	@Column(name="tasa_interes_anual")
	private double tasaInteresAnual;
	
	@Column(name="plazo_meses")
	private int plazoMeses;
	
	@Column(name="tipo_cuota")
	private String tipoCuota;
	
	@ManyToOne
    @JoinColumn(name = "id_cuenta")
    private Cuenta cuenta;
}
