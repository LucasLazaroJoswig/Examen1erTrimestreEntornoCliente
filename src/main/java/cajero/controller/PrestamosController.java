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
@RequestMapping("/prestamo")
public class PrestamosController {
	@Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private MovimientoDao movimientoDao;
    
    @Autowired
    private PrestamoDao prestamoDao;
    
    @GetMapping("/alta")
    public String nuevoPrestamo(Model model) {
    	
    	return "altaPrestamo"; //Mostrar formulario de alta de prestamo
    
    }
    
    @GetMapping("/lista")
    public String verPrestamos(Model model, HttpSession sesion) {
    	Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta"); // Coje el objeto cuenta de la sesion
    	
        List<Prestamo> prestamos = prestamoDao.prestamosPorCuenta(cuenta); // Genera la lista de prestamos para esa cuenta en especifico
        
        	model.addAttribute("prestamos", prestamos);
        	model.addAttribute("cuenta", cuenta);
            return "prestamos"; 
        
    }
    
    @PostMapping("/alta")
    public String altaPrestamo(@RequestParam double cantidadPrestamo, @RequestParam double tasaInteresAnual, @RequestParam int plazoMeses,
    		@RequestParam String tipoCuota, @RequestParam String descripcion, HttpSession sesion, RedirectAttributes ratt) {
    	Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta");
    	Prestamo prestamo = new Prestamo();
    	prestamo.setCantidadPrestamo(cantidadPrestamo);
    	prestamo.setCuenta(cuenta);
    	prestamo.setTipoCuota(tipoCuota);
    	prestamo.setDescripcion(descripcion);
    	prestamo.setTasaInteresAnual(tasaInteresAnual);
    	prestamo.setFechaPrestamo(new Date());
    	
    	if (prestamoDao.insertOne(prestamo) == 1)
            ratt.addFlashAttribute("mensaje", "Prestamo creado");
        else
            ratt.addFlashAttribute("mensajeError", "Prestamo NO creado");
        
        return "redirect:/prestamo/lista";
    }
    
    @GetMapping("/eliminar/{idPrestamo}")
    public String eliminar(@PathVariable int idPrestamo, Model model, RedirectAttributes ratt) {
    	Prestamo prestamo = prestamoDao.findById(idPrestamo);
		if (prestamoDao.deleteOne(prestamo) == 1) 
			ratt.addFlashAttribute("mensaje", "Prestamo Eliminado");
		else
			ratt.addFlashAttribute("mensajeError", "Prestamo NO Eliminado");
		
		return "redirect:/prestamo/lista";
	}
    
}
