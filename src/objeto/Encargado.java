/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objeto;

/**
 *
 * @author sauls
 */
public class Encargado {
    private String id_empt;
    private String numEmpleado;
    private String nombre;
    private String linea;
    private int turno;
    private String contraseña;
    private int estado;
    private int tipo_cuenta;

    public Encargado(String id_empt, String numEmpleado, String nombre, String linea, int turno, String contraseña, int estado, int tipo_cuenta) {
        this.id_empt = id_empt;
        this.numEmpleado = numEmpleado;
        this.nombre = nombre;
        this.linea = linea;
        this.turno = turno;
        this.contraseña = contraseña;
        this.estado = estado;
        this.tipo_cuenta = tipo_cuenta;
    }

    public Encargado() {
    }

    public String getId_empt() {
        return id_empt;
    }

    public void setId_empt(String id_empt) {
        this.id_empt = id_empt;
    }

    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getTipo_cuenta() {
        return tipo_cuenta;
    }

    public void setTipo_cuenta(int tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }       
    
    public void impData(){
        System.out.println(
                "Nombre: "+nombre+" Numero de empleado: "+numEmpleado+"  "+id_empt+" Estado: "+this.getEstado(estado)+" Tipo cuenta:"+this.getTipo_cuenta(tipo_cuenta)
        );
    }
    public boolean validarEnc(){
        try{
            return this.numEmpleado.length()==8;
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }
    }
    
    public String getTipo_cuenta(int n){
        //Se generan 3 tipos de cuentas, las cuales estan relacionadas con los privilegios que se le permiten a cada Encargado
        // 0: Administrador 1:Administrador de Htal: 2:Encargado
        switch(n){
            case 0: return "Administrador";
            case 1: return "EncargadoHtal";
            case 2: return "Encargado";
            default: return "Desconicido";
        }
    }
    
    public String getEstado(int n){
        //Regresa el estado de la cuenta que se esta trabajando
        switch(n){
            case 0:return "Bloqueado";
            case 1:return "Activo";
            default: return "Desconicido";
        }
    }
}
