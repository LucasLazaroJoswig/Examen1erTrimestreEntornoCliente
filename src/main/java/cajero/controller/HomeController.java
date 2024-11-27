package cajero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import cajero.modelo.dao.CuentaDao;
import cajero.modelo.entities.Cuenta;

@Controller
public class HomeController {

    @Autowired
    private CuentaDao cuentaDao;
    
    @GetMapping("/login")
    public String mostrarLogin() {
        return "FormLogin";
    }
    
    @PostMapping("/login")
    public String procesarLogin(@RequestParam int idCuenta, HttpSession sesion, RedirectAttributes ratt) {
        Cuenta cuenta = cuentaDao.findById(idCuenta);
        
        if (cuenta != null) {
            sesion.setAttribute("cuenta", cuenta);
            return "redirect:/menu";
        } else {
            ratt.addFlashAttribute("mensaje", "El número de cuenta no es válido.");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/menu")
    public String mostrarMenu(HttpSession sesion, Model model) {
        Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta");
        
        if (cuenta == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("cuenta", cuenta);
        return "menu";
    }
    @GetMapping("/")
    public String inicio(HttpSession sesion, Model model) {
    	Cuenta cuenta = (Cuenta) sesion.getAttribute("cuenta");
    	if (cuenta==null) {
    		return "redirect:/login";
    	}else {
    		return "redirect:/menu";
    	}
    }
   
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession sesion) {
        sesion.removeAttribute("cuenta");
        sesion.invalidate();
        return "redirect:/login";
    }
}
