/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package funcionalidad;

import objeto.Encargado;
import javax.swing.JOptionPane;
/**
 *
 * @author sauls
 */
//La clase anteriormente seleccionada se encargara de realizar metodos utiles para todas las ventanas
public class Sistema{
    
    public void salida(){
        int seleccion=JOptionPane.showConfirmDialog(null,"¿Realmente deseas salir del programa?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        System.out.println(""+seleccion);
        if(seleccion==0){
            System.exit(seleccion);
        }
    }
    public void error(String mensaje){
        JOptionPane.showConfirmDialog(null,mensaje, "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }
    public void info(String mensaje){
        JOptionPane.showConfirmDialog(null,mensaje, "Informacion", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }
    public void advertencia(String mensaje){
        JOptionPane.showConfirmDialog(null,mensaje, "Atencion", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
    }
    public boolean opcion(String mensaje){
        Object[] options = { " Aceptar ", " Cancelar "};
        int choice = JOptionPane.showOptionDialog(null, mensaje, "Selecciona una opcion",
        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
        null, options, options[0]);
        return choice==0;
    }
    public boolean valcontrasena(String cadena,Encargado encargado){
        return false;
    }
    public boolean valCadena(String cadena){//La validacion de cadena se realiza por medio de un try catch
        //En caso de que sea valido, va ser igual a 1 //  En otro caso igual a 0
        boolean valido=false;
        try{
            if(!"".equals(cadena)){
                valido=true;
            }
            else{
                error("Se deben proporcionar los datos completos");
            }
        }catch(Exception e){
            System.out.println("Exepcion al validar cadenas");
        }
        return valido;
    }
    public String opCadena(String mensaje){
        String cadena=JOptionPane.showInputDialog(null,mensaje,"Rellena los Campos",JOptionPane.INFORMATION_MESSAGE);
        return cadena;
    }

}

