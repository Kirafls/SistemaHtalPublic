/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin;

import funcionalidad.BaseDatos;
import funcionalidad.Sistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sauls
 */
public class ConsultaInf {
    Sistema s=new Sistema();
    BaseDatos bd=new BaseDatos();
    DefaultTableModel tab;
    String [] datos;
    private String sql;
    private final String url="jdbc:mysql://localhost:3306/bdherramentales";
    private final String user="root";
    private final String password="";
    private Connection cn = null;
    public ConsultaInf(){        
       try {
                System.out.print("Conectando informes.... ");
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                this.cn = DriverManager.getConnection(url, user,password);
                System.out.println("Conexion establecida");
            } catch (SQLException ex) {
                Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
                s.error("No se puede conectar con la base de datos");
            }
    }
    //Genaracion de los reportes que se muestran en menu
    //
    public DefaultTableModel empXlinea(){//Numero de empleados por linea
        tab=new DefaultTableModel();
        tab.addColumn("Nombre de la linea");
        tab.addColumn("Operarios de la linea");
        sql="""
            SELECT 
                l.nombre AS nombre_linea, 
                COUNT(o.id_empt) AS cantidad_empleados
            FROM 
                operario o
            JOIN 
                lineas l ON o.linea = l.id_linea
            GROUP BY 
                l.nombre;
            """;
        datos=new String[2];
        try {
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        return tab;
    }    
    public DefaultTableModel reportesXop(){
        tab=new DefaultTableModel();
        tab.addColumn("Numero operario");
        tab.addColumn("Nombre");
        tab.addColumn("Linea");
        tab.addColumn("Numero de Reportes");
        sql="""
            SELECT 
                o.nombre AS nombre_operario,
                l.nombre AS nombre_linea,
                o.num_emp,
                COUNT(r.id_reporte) AS cantidad_reportes
            FROM 
                operario o
            LEFT JOIN 
                lineas l ON o.linea = l.id_linea
            LEFT JOIN 
                reportes r ON o.num_emp = r.id_op
            GROUP BY 
                o.num_emp, o.nombre, l.nombre
            HAVING 
                cantidad_reportes > 0;
            """;
        datos=new String[4];
        try {       //Base de la consulta Select
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);
                    tab.addRow(datos);
                    
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        return tab;
    }    
    public DefaultTableModel opXturno(){
        tab=new DefaultTableModel();
        tab.addColumn("Turno");
        tab.addColumn("Numero de Operarios");
        datos=new String[2];
        sql="""
            SELECT 
                o.turno AS turno, 
                COUNT(o.num_emp) AS cantidad_operarios
            FROM 
                operario o
            GROUP BY 
                o.turno;
            """;
        try {       //Base de la consulta Select
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=bd.numturno(rs.getInt(1));
                    datos[1]=rs.getInt(2)+"";
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        return tab;
    }    
    public DefaultTableModel htalXusoHR(){
        tab=new DefaultTableModel();
        tab.addColumn("ID Herramental");
        tab.addColumn("Horas de uso");
        datos=new String[2];
        sql="""
            SELECT 
                id_htal, 
                SUM(TIMESTAMPDIFF(SECOND, fecha_prestamo, fecha_entrega) / 3600) AS horas_totales
            FROM 
                registro
            GROUP BY 
                id_htal;
            """;
        try {       //Base de la consulta Select
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getFloat(2)+"";
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        return tab;
    }    
    public DefaultTableModel regXhtal(){
        tab= new DefaultTableModel();
        tab.addColumn("ID Herramental");
        tab.addColumn("Nombre");
        tab.addColumn("Numero de Registros");
        sql="""
            SELECT 
                r.id_htal, 
                h.nombre AS nombre_herramental,
                COUNT(*) AS cantidad_registros
            FROM 
                registro r
            JOIN 
                heramental h ON r.id_htal = h.id_herramental
            GROUP BY 
                r.id_htal, h.nombre;
            """;
        datos=new String[3];
         try {       //Base de la consulta Select
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getInt(3)+"";
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        return tab;
    }    
    public DefaultTableModel reporteXhtal(){
        tab=new DefaultTableModel();
        tab.addColumn("ID Herramental");
        tab.addColumn("Nombre");
        tab.addColumn("Numero de reportes");
        sql="""
            SELECT
                r.id_htal,
                h.nombre AS nombre_herramental,
                COUNT(*) AS cantidad_registros
            FROM
                reportes r
            JOIN heramental h ON
                r.id_htal = h.id_herramental
            GROUP BY
                r.id_htal,
                h.nombre
            """;
        datos=new String[3];
         try {       //Base de la consulta Select
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getInt(3)+"";
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        return tab;
    }    
    public DefaultTableModel estadoCuentas(){
        tab=new DefaultTableModel();
        tab.addColumn("Numero de Empleado");
        tab.addColumn("Nombre");
        tab.addColumn("Linea");
        tab.addColumn("Turno");
        tab.addColumn("Estado");
        tab.addColumn("Tipo de Cuenta");
        sql="""
            SELECT
                e.num_emp,
                e.nombre,
                l.nombre AS nombre_linea,
                e.turno,
                e.estado,
                e.tipo_cuenta
            FROM
                encargado e
            JOIN 
                lineas  l ON l.id_linea = e.linea
            WHERE
                1 = 1;
            """;
        datos=new String[6];
        try {       //Base de la consulta Select
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=bd.numturno(rs.getInt(4));//Turno
                    datos[4]=bd.cuentaEstado(rs.getInt(5));//Estado
                    datos[5]=bd.tipoCuenta(rs.getInt(6));//Tipo cuenta
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        return tab;
    }    
    public DefaultTableModel regXturno(){
        tab=new DefaultTableModel();
        tab.addColumn("Turno");
        tab.addColumn("Numero de reportes");
        datos= new String [2];
        sql="""
            SELECT 
                o.turno, 
                COUNT(r.id_registro) AS cantidad_registros
            FROM 
                registro r
            JOIN 
                operario o ON r.id_operario = o.num_emp
            GROUP BY 
                o.turno;
            """;
        try {       //Base de la consulta Select
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=bd.numturno(rs.getInt(1));
                    datos[1]=rs.getString(2);
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        return tab;
    }    
    public DefaultTableModel reportesActivos(){
        tab=new DefaultTableModel();
        tab.addColumn("ID");
        tab.addColumn("Encargado");
        tab.addColumn("Herramental");
        tab.addColumn("Operario");
        tab.addColumn("Tipo reporte");
        tab.addColumn("Problematica");
        tab.addColumn("Fecha abierto");
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
        datos=new String[7];
        try {       //Base de la consulta Select
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=rs.getInt(1)+"";
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);
                    datos[4]=bd.tipoReporte( rs.getInt(5));//Tipo de reporte
                    datos[5]=rs.getString(6);
                    datos[6]=rs.getString(7);
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        return tab;
    }
    public DefaultTableModel reportesCerrados(){
        tab=new DefaultTableModel();
        tab.addColumn("ID");
        tab.addColumn("Encargado");
        tab.addColumn("Herramental");
        tab.addColumn("Operario");
        tab.addColumn("Tipo reporte");
        tab.addColumn("Problematica");
        tab.addColumn("Solucion");
        tab.addColumn("Fecha abierto");
        tab.addColumn("Fecha cierre");
        sql="""
            SELECT
                `id_reporte`,
                `id_encargado`,
                `id_htal`,
                `id_op`,
                `tipo_reporte`,
                `problematica`,
                `solucion`,
                `fecha_abierto`,
                `fecha_cierre`
            FROM
                `reportes`
            WHERE
                `estado`=0
            """;
        datos=new String[9];
        try {       //Base de la consulta Select
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=rs.getInt(1)+"";
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getString(3);
                    datos[3]=rs.getString(4);
                    datos[4]=bd.tipoReporte( rs.getInt(5));//Tipo de reporte
                    datos[5]=rs.getString(6);
                    datos[6]=rs.getString(7);
                    datos[7]=rs.getString(8);
                    datos[8]=rs.getString(9);
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        
        return tab;
    }    
    public DefaultTableModel reportesXlinea(){
        tab=new DefaultTableModel();
        tab.addColumn("Linea");
        tab.addColumn("Numero de Reportes");
        datos=new String[2];
        sql="""
            SELECT
                l.nombre AS nombre_linea,
                COUNT(r.id_reporte) AS cantidad_reportes
            FROM
                reportes r
            JOIN operario o ON
                r.id_op = o.num_emp
            JOIN lineas l ON
                o.linea = l.id_linea
            GROUP BY
                l.id_linea,
                l.nombre;
            """;
        try {       //Base de la consulta Select
                Statement st = cn.createStatement();
                ResultSet rs=st.executeQuery(sql);
                while (rs.next())
                {
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getInt(2)+"";
                    tab.addRow(datos);
                }    
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
        return tab;
    }
}
