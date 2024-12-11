/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objeto;
/**
 *
 * @author sauls
 */
public class Registro {
    private int id_registro;
    private String id_htal;
    private String fecha_prestamo;
    private String fecha_entrega;
    private String num_encargado;
    private String num_encargador;
    private String observaciones;
    private String num_empleado;
    private String id_empt;
    //Se crearan metodologias para la obtencion de la hora y fecha por separado

    public Registro(int id_registro, String id_htal, String fecha_prestamo, String fecha_entrega, String num_encargado,String num_encargador, String observaciones, String num_empleado, String id_empt) {
        this.id_registro = id_registro;
        this.id_htal = id_htal;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_entrega = fecha_entrega;
        this.num_encargado = num_encargado;
        this.num_encargador= num_encargador;
        this.observaciones = observaciones;
        this.num_empleado = num_empleado;
        this.id_empt = id_empt;
    }

    public Registro() {
    }

    public Registro(int id_registro, String id_htal, String fecha_prestamo, String num_encargado, String num_empleado, String id_empt) {
        this.id_registro = id_registro;
        this.id_htal = id_htal;
        this.fecha_prestamo = fecha_prestamo;
        this.num_encargado = num_encargado;
        this.num_empleado = num_empleado;
        this.id_empt = id_empt;
    }
    

    public int getId_registro() {  
        return id_registro;
    }

    public void setId_registro(int id_registro) {
        this.id_registro = id_registro;
    }

    public String getId_htal() {
        return id_htal;
    }

    public void setId_htal(String id_htal) {
        this.id_htal = id_htal;
    }

    public String getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(String fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getNum_encargado() {
        return num_encargado;
    }

    public void setNum_encargado(String num_encargado) {
        this.num_encargado = num_encargado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNum_empleado() {
        return num_empleado;
    }

    public void setNum_empleado(String num_empleado) {
        this.num_empleado = num_empleado;
    }

    public String getId_empt() {
        return id_empt;
    }

    public void setId_empt(String id_empt) {
        this.id_empt = id_empt;
    }
     public String getNum_encargador() {
        return num_encargador;
    }

    public void setNum_encargador(String num_encargador) {
        this.num_encargador = num_encargador;
    }
    public void impData(){
        System.out.println("");
    }
    
    public boolean validacion(){
        int val=0;
        try{
            if(this.num_encargado.length()==8){
                val++;
            }
            if(this.num_empleado.length()==8){
                val++;
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
        return val==2;
    }
    public String impRegistroTep(){
        return ""
                + "ID Herramental: "+id_htal+"\n"
                + "Fecha de Prestamo: "+fecha_prestamo+"\n"
                + "Fecha de Entrega: "+fecha_entrega+"\n"
                + "Operario: "+num_empleado
              ;
    }
}
