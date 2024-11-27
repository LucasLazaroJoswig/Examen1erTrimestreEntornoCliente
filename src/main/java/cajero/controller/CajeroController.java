package cajero.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cajero.modelo.dao.CuentaDao;
import cajero.modelo.dao.MovimientoDao;
import cajero.modelo.dao.PrestamoDao;
import cajero.modelo.entities.Cuenta;
import cajero.modelo.entities.Movimiento;
import cajero.modelo.entities.Prestamo;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;





@Controller
@RequestMapping("/cuenta")
public class CajeroController {
	@Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private MovimientoDao movimientoDao;
    
    @Autowired
    private PrestamoDao prestamoDao;


    // Método para mostrar el formulario de ingreso
    @GetMapping("/ingresar")
    public String mostrarFormularioIngreso(Model model) {

            return "ingresar";  // Mostrar el formulario de ingreso
        } 

    @GetMapping("/extraer")
    public String mostrarFormularioExtraer(Model model) {

            return "extraer";  // Mostrar el formulario de extraccion
    }
    @GetMapping("/transferir")
    public String mostrarFormularioTransferencia(Model model) {

            return "Transferencia";  // Mostrar el formulario de transferencia
    }

    @PostMapping("/ingresar")
    public String ingresarDinero(@RequestParam double monto, HttpSession sesion, RedirectAttributes ratt) {
    	Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta");
    	cuenta.ingresar(monto);  // Realiza el ingreso en la cuenta origen
        cuentaDao.updateOne(cuenta);  // Actualiza la cuenta en la base de datos
        ratt.addFlashAttribute("mensaje", "Ingreso válido");  // Mensaje de éxito
        
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setCantidad(monto);
        movimiento.setOperacion("Ingreso");
        movimiento.setFecha(new Date());
        movimientoDao.insertOne(movimiento); // Registra el movimiento
        
        
        return "redirect:/menu";
    }
    
    @PostMapping("/extraer")
    public String ExtraerDinero(@RequestParam double monto, HttpSession sesion, RedirectAttributes ratt) {
    	Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta");
    	
    	if (cuenta.extraer(monto)) {
    		cuentaDao.updateOne(cuenta);  // Actualiza la cuenta en la base de datos
            ratt.addFlashAttribute("mensaje", "Extracción válido");  // Mensaje de éxito
            
            Movimiento movimiento = new Movimiento();
            movimiento.setCuenta(cuenta);
            movimiento.setCantidad(monto);
            movimiento.setOperacion("Extracción");
            movimiento.setFecha(new Date());
            movimientoDao.insertOne(movimiento); // Registra el movimiento
            
		} else {
			ratt.addFlashAttribute("mensajeError", "Extracción Errónea, saldo ha de ser mayor a monto"); 
		}
        return "redirect:/menu";
    }
    
    @PostMapping("/transferencia")
    public String realizarTransferencia(@RequestParam("monto") double monto, 
    									@RequestParam("cuentaDestino") String idCuentaDestinoStr, RedirectAttributes ratt, HttpSession sesion) {
    	
    	Cuenta cuentaDestino = new Cuenta();
        if (idCuentaDestinoStr!="") {
			int idCuentaDestino = Integer.parseInt(idCuentaDestinoStr);
			cuentaDestino = cuentaDao.findById(idCuentaDestino);
		}
        Cuenta cuenta = (Cuenta)sesion.getAttribute("cuenta");
    	
    	if (cuenta.tranferir(cuentaDestino, monto)) {  // Intenta realizar la transferencia
            cuentaDao.updateOne(cuenta);  // Actualiza la cuenta origen en la base de datos
            cuentaDao.updateOne(cuentaDestino);  // Actualiza la cuenta destino en la base de datos

            // Registrar el movimiento en la cuenta origen
            Movimiento movimiento = new Movimiento();
            movimiento.setCuenta(cuenta);
            movimiento.setCantidad(monto);
            movimiento.setOperacion("Cargo por Transferencia");
            movimiento.setFecha(new Date());
            movimientoDao.insertOne(movimiento);

            // Registrar el movimiento en la cuenta destino
            Movimiento movimientoDestino = new Movimiento();
            movimientoDestino.setCuenta(cuentaDestino);
            movimientoDestino.setCantidad(monto);
            movimientoDestino.setOperacion("Abono por transferencia");
            movimientoDestino.setFecha(new Date());
            movimientoDao.insertOne(movimientoDestino);

            ratt.addFlashAttribute("mensaje", "Transferencia válida");  // Mensaje de éxito
        } else {
            ratt.addFlashAttribute("mensajeError", "Transferencia inválida, saldo ha de ser mayor a monto");  // Mensaje de error
        }
    	return "redirect:/menu";
    }

    
    @GetMapping("/movimientos")
    public String verDetalle(Model model, HttpSession sesion) {
    	Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta"); // Coje el objeto cuenta de la sesion
    	
        List<Movimiento> movimientos = movimientoDao.movimientosPorCuenta(cuenta); // Genera la lista de movimientos para esa cuenta en especifico
        
        	model.addAttribute("movimientos", movimientos);
        	model.addAttribute("cuenta", cuenta);
            return "movimientos"; 
        
    }
    
    @GetMapping("/prestamos")
    public String verPrestamos(Model model, HttpSession sesion) {
    	Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta"); // Coje el objeto cuenta de la sesion
    	
        List<Prestamo> prestamos = prestamoDao.prestamosPorCuenta(cuenta); // Genera la lista de movimientos para esa cuenta en especifico
        
        	model.addAttribute("prestamos", prestamos);
        	model.addAttribute("cuenta", cuenta);
            return "prestamos"; 
        
    }


}
