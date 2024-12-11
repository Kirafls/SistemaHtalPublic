/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objeto;

/**
 *
 * @author sauls
 */
public class Reporte {
    private int id;
    private int estado;
    private String encargado;
    private String herramental;
    private String operario;
    private int tipoReporte;
    private String problematica;
    private String solucion;
    private String fechaAbierto;
    private String fechaCierre;

    public Reporte() {
    }

    public Reporte(int id, int estado, String encargado, String herramental, String operario, int tipoReporte, String problematica, String solucion, String fechaAbierto, String fechaCierre) {
        this.id = id;
        this.estado = estado;
        this.encargado = encargado;
        this.herramental = herramental;
        this.operario = operario;
        this.tipoReporte = tipoReporte;
        this.problematica = problematica;
        this.solucion = solucion;
        this.fechaAbierto = fechaAbierto;
        this.fechaCierre = fechaCierre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getHerramental() {
        return herramental;
    }

    public void setHerramental(String herramental) {
        this.herramental = herramental;
    }

    public String getOperario() {
        return operario;
    }

    public void setOperario(String operario) {
        this.operario = operario;
    }

    public int getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(int tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getProblematica() {
        return problematica;
    }

    public void setProblematica(String problematica) {
        this.problematica = problematica;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public String getFechaAbierto() {
        return fechaAbierto;
    }

    public void setFechaAbierto(String fechaAbierto) {
        this.fechaAbierto = fechaAbierto;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
    
    public void impData(){
        System.out.println(
            "NReporte: "+id+
            "Estado: "+estado                            
        );
    }
    
    public String impReporte(){
        return "Herramental: "+herramental+"\n"
                + "Encargado: "+encargado+"\n"
                + "Operario: "+operario+"\n"
                + "Fecha de Inicio: "+fechaAbierto+"\n"
                + "Problematica: "+problematica;
    }
    
    public boolean valReporte(){
        try{
            int n=Integer.parseInt(id+"");
            System.out.println(""+n);
            n=herramental.length();
            return true;
        }catch(NumberFormatException ex){
            System.out.println(ex);
        }
        return false;
    }
}
