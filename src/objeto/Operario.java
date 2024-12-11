/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objeto;

import funcionalidad.Sistema;

    
/**
 *
 * @author sauls
 */
public class Operario {
    private String id_empt;
    private String num_emp;
    private String nombre;
    private int linea;
    private int turno;
    private String observaciones;
    Sistema s=new Sistema();
    public Operario(String id_empt, String num_emp, String nombre, int linea, int turno, String observaciones) {
        this.id_empt = id_empt;
        this.num_emp = num_emp;
        this.nombre = nombre;
        this.linea = linea;
        this.turno = turno;
        this.observaciones = observaciones;
    }

    public Operario() {
    }

    public String getId_empt() {
        return id_empt;
    }

    public void setId_empt(String id_empt) {
        this.id_empt = id_empt;
    }

    public String getNum_emp() {
        return num_emp;
    }

    public void setNum_emp(String num_emp) {
        this.num_emp = num_emp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public void impOp(){
        System.out.println("Operario: "+id_empt+" Numero: "+num_emp+" Nombre: "+nombre+" Linea: "+linea+" Turno: "+turno+" Observaciones: "+observaciones);
    }
    public boolean validacion(){
        //Validacion de los valores que estan en en la clase
        //Rquiere añadir mas validacion usa try catch
        int val=0;
        try{
            if(this.id_empt.length()==16&&this.id_empt!=null){
            //s.info("Num tarjeta Valido");
            val++;
            }
            if(this.num_emp.length()==8&&this.num_emp!=null){
                //s.info("Num emp valido");
                val++;
            }
            if(this.nombre.length()>=3&&this.nombre!=null){
                //s.info("Nombre valido");
                val++;
            }
        }catch(Exception e){
            System.out.println(""+e);
        }
        
        return val==3;
    }
}
