/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objeto;

/**
 *
 * @author sauls
 */
public class Herramental {
    private String id_herramental;
    private String nombre;
    private String observacion;
    private String viñeta;
    private int linea;
    private int estado; //En caso de 1 es que esta disponible, 0 es que esta prestado en ese momento
    private int reporte; 

    public Herramental(String id_herramental, String nombre, String observacion, String viñeta, int estado, int linea,int reporte) {
        this.id_herramental = id_herramental;       
        this.nombre = nombre;
        this.observacion = observacion;
        this.viñeta = viñeta;
        this.estado = estado;
        this.linea = linea;
        this.reporte=reporte;
    }    

    public int getReporte() {
        return reporte;
    }

    public void setReporte(int reporte) {
        this.reporte = reporte;
    }
    
    public Herramental(){
        
    }
    
    public void impData(){
        System.out.println("ID Herramental: "+id_herramental+" Nombre: "+nombre+" Estado: "+estado+"Viñeta: "
                +viñeta+" Linea:"+linea+" Observaciones: "+observacion);
    }

    public String getId_herramental() {
        return id_herramental;
    }

    public void setId_herramental(String id_herramental) {
        this.id_herramental = id_herramental;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getViñeta() {
        return viñeta;
    }

    public void setViñeta(String viñeta) {
        this.viñeta = viñeta;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public boolean valHtal(){
        int val=0;
        try{
            if(this.id_herramental.length()!=0){
                val++;
            }
            if(this.nombre.length()!=0){
                val++;
            }
            if(this.viñeta.length()!=0){
                val++;
            }
            if(!this.id_herramental.contains(" "))
            {
                val++;
            }
            System.out.println("Htal Valido");
            return val==4;
            
        }catch(Exception ex){
            System.out.println(""+ex);
            return false;
        }        
    }
    
}
