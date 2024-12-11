/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objeto;

/**
 *
 * @author sauls
 */
public class Linea {
    private int idLinea;
    private String nombre;

    public Linea() {
    }

    public Linea(int idLinea, String nombre) {
        this.idLinea = idLinea;
        this.nombre = nombre;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void impData(){
        System.out.println("ID Linea: "+idLinea+" Nombre: "+nombre+" ");
    }    
}
