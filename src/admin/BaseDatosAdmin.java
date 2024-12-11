/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import objeto.Encargado;
import funcionalidad.BaseDatos;
import funcionalidad.BaseDatos;
import funcionalidad.Sistema;
import funcionalidad.Sistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import objeto.Herramental;
import objeto.Operario;
import objeto.Reporte;

/**
 *
 * @author sauls
 */
public class BaseDatosAdmin {
    Sistema s=new Sistema();
    BaseDatos bd=new BaseDatos();
    private String sql;
    private final String url="jdbc:mysql://localhost:3306/bdherramentales";
    private final String user="root";
    private final String password="";
    private Connection cn = null;
    public BaseDatosAdmin(){        
       try {
                System.out.print("Conectando Administracion.... ");
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                this.cn = DriverManager.getConnection(url, user,password);
                System.out.println("Conexion establecida");
            } catch (SQLException ex) {
                Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
                s.error("No se puede conectar con la base de datos");
            }
    }
    public boolean setContrasena(String contrasena, String contrasenaNueva, Encargado encargado) {
        Encargado aux = bd.validarSesion(encargado.getNumEmpleado(), contrasena);

        if (aux != null && aux.validarEnc()) {
            // Consulta SQL para actualizar la contraseña
            sql = "UPDATE encargado SET contrasena = SHA2(?, 256) WHERE num_emp = ?";

            try (PreparedStatement st = cn.prepareStatement(sql)) {
                // Asignar los parámetros
                st.setString(1, contrasenaNueva);
                st.setString(2, aux.getNumEmpleado());

                // Ejecutar la actualización
                int filasAfectadas = st.executeUpdate();
                return filasAfectadas == 1; // Devuelve true si se actualizó una fila
            } catch (SQLException ex) {
                Logger.getLogger(BaseDatosAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false; // Devuelve false si la sesión no fue válida o no se actualizó
    }
    public boolean updateEncargado(Encargado e){
        sql="UPDATE `encargado` SET `nombre`=?,`estado`=?,`tipo_cuenta`=? WHERE`id_emptE`=?";
        try(PreparedStatement st=cn.prepareStatement(sql)){
            st.setString(1, e.getNombre());
            //st.setInt(2, bd.lineaNum(e.getLinea()));
            //st.setInt(3, e.getTurno());
            st.setInt(2, e.getEstado());
            st.setInt(3, e.getTipo_cuenta());
            st.setString(4, e.getId_empt());
            int update=st.executeUpdate();
            return update==1;
        }catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    public DefaultTableModel MostrarE(String cadena) throws SQLException{
        DefaultTableModel tab=new DefaultTableModel();
        tab.addColumn("Numero de Tarjeta");
        tab.addColumn("Numero Encargado");
        tab.addColumn("Nombre");
        tab.addColumn("Linea");
        tab.addColumn("Turno");
        tab.addColumn("Estado");
        tab.addColumn("Tipo de Cuenta");
        String[] datos=new String[8];
        if(cadena.equals(" ")){
            sql="""
                SELECT
                    e.id_emptE,
                    e.num_emp,
                    e.nombre,
                    l.nombre,
                    e.turno,
                    e.estado,
                    e.tipo_cuenta
                FROM
                    encargado e
                JOIN lineas l ON
                    e.linea = l.id_linea
                """;
            try {
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);//Nombre de linea
                    datos[4]=bd.numturno(rs.getInt(5));//rs.getInt(5)+"";//Turno
                    datos[5]=bd.cuentaEstado(rs.getInt(6));//rs.getInt(6)+"";//Estado
                    datos[6]=bd.tipoCuenta(rs.getInt(7));//rs.getInt(7)+"";//Tipo de cuenta
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        }else{
            sql="""
                SELECT
                    e.id_emptE,
                    e.num_emp,
                    e.nombre,
                    l.nombre,
                    e.turno,
                    e.estado,
                    e.tipo_cuenta
                FROM
                    encargado e
                JOIN lineas l ON
                    e.linea = l.id_linea
                WHERE e.nombre LIKE ? OR e.num_emp LIKE ? OR e.id_emptE LIKE ?
                """;
            PreparedStatement stmt = cn.prepareStatement(sql);
            cadena = "%" + cadena + "%";
            stmt.setString(1, cadena);
            stmt.setString(2, cadena);
            stmt.setString(3, cadena);

            ResultSet rs = stmt.executeQuery();
             try {                
                while (rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);//Nombre de linea
                    datos[4]=bd.numturno(rs.getInt(5));//rs.getInt(5)+"";//Turno
                    datos[5]=bd.cuentaEstado(rs.getInt(6));//rs.getInt(6)+"";//Estado
                    datos[6]=bd.tipoCuenta(rs.getInt(7));//rs.getInt(7)+"";//Tipo de cuenta
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
               Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tab;
    }
    //Modificacion de Htal
    public boolean modHtal(Herramental htal){
        sql="""
            UPDATE
                `heramental`
            SET
                `nombre` = ?,
                `observaciones` = ?,
                `vieneta` = ?,
                `linea` = ?
            WHERE
                `id_herramental` = ?
            """;
        try(PreparedStatement st=cn.prepareStatement(sql)){
            st.setString(1, htal.getNombre());
            st.setString(2, htal.getObservacion());
            st.setString(3, htal.getViñeta());
            st.setInt(4,htal.getLinea());
            st.setString(5, htal.getId_herramental());
            int reg=st.executeUpdate();
            return reg==1;            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    public boolean bajapHtal(String htal){//Baja permanente de Htal
        sql="""
            UPDATE `heramental` SET `reporte`=2 WHERE `id_herramental`= ?
            """;
        try(PreparedStatement st=cn.prepareStatement(sql)){
            st.setString(1, htal);
            int reg=st.executeUpdate();
            return reg==1;
        }catch(SQLException ex){
            ex.printStackTrace();
        }        
        return false;
    }
    //Administracion de reportes
    public boolean altaReporte(Reporte reporte){
        sql="""
            INSERT INTO `reportes`(
                `id_reporte`,
                `estado`,
                `id_encargado`,
                `id_htal`,
                `id_op`,
                `tipo_reporte`,
                `problematica`,
                `solucion`,
                `fecha_abierto`,
                `fecha_cierre`
            )
            VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, 0000/00/00)
            """;
        try{
            PreparedStatement t=(PreparedStatement)cn.prepareStatement(sql);
            t.setInt(1, reporte.getId());
            t.setInt(2, 1);//EL reporte esta abierto
            t.setString(3, reporte.getEncargado());
            t.setString(4, reporte.getHerramental());
            t.setString(5, reporte.getOperario());
            t.setInt(6, reporte.getTipoReporte());
            t.setString(7, reporte.getProblematica());
            t.setString(8, "Abierto");
            t.setString(9, bd.fechaActual());
            int res=t.executeUpdate();
            if(res==1){
                cambioReporte(false,reporte.getHerramental());
                return true;
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    public void cambioReporte(boolean estado,String idhtal){
        // 1 es que esta abierto el reporte
        // En caso de que que sea true es que se cierra el reporte
        if(estado==true){
            sql="UPDATE `heramental` SET `reporte`=0 WHERE `id_herramental`=?";
        }
        else{
            sql="UPDATE `heramental` SET `reporte`=1 WHERE `id_herramental`=?";
        }
        try(PreparedStatement st=cn.prepareStatement(sql)){
            st.setString(1, idhtal);
            st.executeUpdate();
            System.out.println("Se ha levantado reporte o cerrrado a: "+idhtal);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
                
    }
    public boolean bajaReporte(Reporte reporte){
        sql="UPDATE `reportes` SET  `estado`=0, `solucion`=? ,`fecha_cierre`=? WHERE`id_reporte`=?";
        try(PreparedStatement st=cn.prepareStatement(sql)){
            st.setString(1,reporte.getSolucion());
            st.setString(2, reporte.getFechaCierre());
            st.setInt(3, reporte.getId());
            int res=st.executeUpdate();
            if(res==1){
                cambioReporte(true,reporte.getHerramental());
                return true;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    public DefaultTableModel mostarRegistros(String cadena){
        DefaultTableModel tab=new DefaultTableModel();
        String [] datos=new String [10];
        tab.addColumn("ID Registro");
        tab.addColumn("Herramenetal");
        tab.addColumn("Fecha de prestamo");
        tab.addColumn("Fecha de Entrega");
        tab.addColumn("Encargado que Entrega");
        tab.addColumn("Encargado que Recive");
        tab.addColumn("Operario");
        if(cadena.equals(" ")){
            sql="""
            SELECT
                `id_registro`,
                `id_htal`,
                `fecha_prestamo`,
                `fecha_entrega`,
                `num_encargado`,
                `num_encargado_r`,
                `id_operario`
            FROM
                `registro`
            WHERE
                `fecha_entrega` >= NOW() - INTERVAL 5 HOUR
            """;
            try {
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=""+rs.getInt(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);
                    datos[4]=rs.getString(5);
                    datos[5]=rs.getString(6);
                    datos[6]=rs.getString(7);
                    //datos[7]=rs.getString(8);
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        }
        else{
            sql="""
                SELECT
                    `id_registro`,
                    `id_htal`,
                    `fecha_prestamo`,
                    `fecha_entrega`,
                    `num_encargado`,
                    `num_encargado_r`,
                    `id_operario`,
                    `id_empt`
                FROM
                    `registro`
                WHERE
                    `fecha_entrega` >= NOW() - INTERVAL 5 HOUR AND `id_htal` LIKE ? OR`id_operario`LIKE ?
                """;
            
             try {
                PreparedStatement stmt = cn.prepareStatement(sql);
                cadena = "%" + cadena + "%";
                stmt.setString(1, cadena);
                stmt.setString(2, cadena);
                ResultSet rs = stmt.executeQuery();
                while (rs.next())
                {
                    datos[0]=""+rs.getInt(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);
                    datos[4]=rs.getString(5);
                    datos[5]=rs.getString(6);
                    datos[6]=rs.getString(7);
                    //datos[7]=rs.getString(8);
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
               Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }        
        return tab;        
    }
    public DefaultTableModel mostrarReportesA(String cadena){
        DefaultTableModel tab=new DefaultTableModel();
        String []datos=new String[10];
        tab.addColumn("ID");
        tab.addColumn("Encargado");
        tab.addColumn("Herramental");
        tab.addColumn("Operario");
        tab.addColumn("Tipo reporte");
        tab.addColumn("Problematica");
        tab.addColumn("Fecha Abierto");
        if(cadena.equals(" ")){
            sql="""
                SELECT
                    `id_reporte`,
                    `id_encargado`,
                    `id_htal`,
                    `id_op`,
                    `tipo_reporte`,
                    `problematica`,
                    `fecha_abierto`
                FROM
                    `reportes`
                WHERE
                    `estado`=1
                """;
            try {
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=rs.getInt(1)+"";
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);
                    datos[4]=bd.tipoReporte(rs.getInt(5));
                    datos[5]=rs.getString(6);
                    datos[6]=rs.getString(7);
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        }
        else{
            sql="""
                SELECT
                    `id_reporte`,
                    `id_encargado`,
                    `id_htal`,
                    `id_op`,
                    `tipo_reporte`,
                    `problematica`,
                    `fecha_abierto`
                FROM
                    `reportes`
                WHERE
                    `estado`=1 AND `id_htal` LIKE ? OR `id_op` LIKE ? OR`tipo_reporte` LIKE ?
                """;
            
             try {
                PreparedStatement stmt = cn.prepareStatement(sql);
                cadena = "%" + cadena + "%";
                stmt.setString(1, cadena);
                stmt.setString(2, cadena);
                stmt.setString(3, cadena);
                ResultSet rs = stmt.executeQuery();
                while (rs.next())
                {
                    datos[0]=rs.getInt(1)+"";
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);
                    datos[4]=bd.tipoReporte(rs.getInt(5));
                    datos[5]=rs.getString(6);
                    datos[6]=rs.getString(7);
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
               Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tab;
    }
}
