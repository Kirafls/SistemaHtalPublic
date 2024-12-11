/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package funcionalidad;
import objeto.Registro;
import objeto.Encargado;
import objeto.Herramental;
import objeto.Linea;
import objeto.Operario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author sauls
 */
//Clase principal para coneccion a base de datos,pensamiento de la ralizacion de la consulta
public class BaseDatos {
    Sistema s=new Sistema();    
    private String sql="select time (NOW()) as hora";//
    private final String url="jdbc:mysql://localhost:3306/bdherramentales";
    private final String user="root";
    private final String password="";
    private Connection cn = null;
    public BaseDatos(){
       try {
                System.out.print("Intentando conectar... ");
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                this.cn = DriverManager.getConnection(url, user,password);
                System.out.println("Conexion establecida");
            } catch (SQLException ex) {
                Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
                s.error("No se puede conectar con la base de datos");
            }
    }   
    public Encargado validarSesion(String numEmpleado,String contrasena){
        sql = "SELECT `id_emptE`, `num_emp`, `nombre`, `linea`, `turno`, `estado`, `tipo_cuenta` FROM encargado WHERE num_emp = ? AND contrasena = SHA2(?, 256)";
        Encargado encargado = null;
        try {
            // Preparamos la consulta con placeholders
            PreparedStatement st = cn.prepareStatement(sql);
            st.setString(1, numEmpleado);   // Asignamos el número de empleado
            st.setString(2, contrasena);    // Asignamos la contraseña en texto plano
            ResultSet rs = st.executeQuery();
            // Si existe un resultado, la autenticación es exitosa
            if (rs.next()) {
                encargado = new Encargado();
                encargado.setId_empt(rs.getString(1));
                encargado.setNumEmpleado(rs.getString(2));
                encargado.setNombre(rs.getString(3));
                encargado.setLinea(rs.getString(4));
                encargado.setTurno(rs.getInt(5));
                encargado.setEstado(rs.getInt(6));
                encargado.setTipo_cuenta(rs.getInt(7));
                // Omitimos contraseña por seguridad
                encargado.impData();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            s.error("No se puede conectar con la base de datos");
        }
        if (encargado != null) {
            if(encargado.getEstado()==1){
                return encargado;
            }
            else{
                s.error("La cuenta esta bloqueada,\nContacte al administrador para que sea restablecida");
                return null;                
            }
        }
        else{
            s.error("La cuenta no existe o\nLa contraseña es incorrecta");
            return null;
        }      
    }
    public DefaultTableModel mostrarOp(){
        
        sql="""
            SELECT    o.id_empt, 
                o.num_emp, 
                o.nombre AS nombre_operario, 
                li.nombre AS nombre_linea, 
                o.turno, 
                o.observaciones 
            FROM 
                operario o
            JOIN 
                lineas li 
            ON 
                o.linea = li.id_linea
            """;
        DefaultTableModel tab=new DefaultTableModel();
            tab.addColumn("ID Tarjeta");
            tab.addColumn("Numero de empleado");
            tab.addColumn("Nombre y Apellido");
            tab.addColumn("Linea");
            tab.addColumn("Turno");
            tab.addColumn("Observaciones");
        String [] datos=new String[6];
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())
            {
                 datos[0]=rs.getString(1);
                 datos[1]=rs.getString(2);
                 datos[2]=rs.getString(3);
                 datos[3]=rs.getString(4);
                 datos[4]=this.numturno(rs.getInt(5)); //turno
                 datos[5]=rs.getString(6);
                 
                 tab.addRow(datos);
            }    

            } catch (SQLException ex) {
               Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }           
        return tab;
    }
    public boolean altaOp(Operario op){
        //Lo utiliza la pantalla de alta operario
        sql="INSERT INTO `operario`(`id_empt`, `num_emp`, `nombre`, `linea`, `turno`, `observaciones`) VALUES (?,?,?,?,?,?)";
        //Sentencia a usar
        try {
            PreparedStatement t = (PreparedStatement) cn.prepareStatement(sql);
                t.setString(1, op.getId_empt());
                t.setString(2, op.getNum_emp().toUpperCase());
                t.setString(3, op.getNombre().toUpperCase());
                t.setInt(4, op.getLinea());
                t.setInt(5, op.getTurno());
                t.setString(6, op.getObservaciones());
               
            int reg=t.executeUpdate();
            if(reg==1){
                s.info("Se ha registrado el operario ");
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            s.error("No se ha realizado la operacion, combruebe la conxion a la base de datos");
            return false;
        }    
    }
    public DefaultTableModel buscarOp(String cadena) {
        // Crear el modelo de la tabla
        DefaultTableModel tab = new DefaultTableModel();

        // Definir las columnas
        tab.addColumn("ID Tarjeta");
        tab.addColumn("Número de Empleado");
        tab.addColumn("Nombre y Apellido");
        tab.addColumn("Línea");
        tab.addColumn("Turno");
        tab.addColumn("Observaciones");

        // Consulta SQL con placeholders
        sql = "SELECT o.id_empt, " +
                     "o.num_emp, " +
                     "o.nombre AS nombre_operario, " +
                     "li.nombre AS nombre_linea, " +
                     "o.turno, " +
                     "o.observaciones " +
                     "FROM operario o " +
                     "JOIN lineas li ON o.linea = li.id_linea " +
                     "WHERE o.num_emp LIKE ? " +
                     "OR o.id_empt LIKE ? " +
                     "OR o.nombre LIKE ?";

        // Preparar recursos fuera del bloque try para cerrarlos en finally
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Preparar la consulta
            stmt = cn.prepareStatement(sql);

            // Asignar los parámetros
            stmt.setString(1, cadena);
            stmt.setString(2, "%" + cadena + "%");
            stmt.setString(3, "%" + cadena + "%");

            // Ejecutar la consulta
            rs = stmt.executeQuery();

            // Arreglo para almacenar cada fila de datos
            String[] datos = new String[6];

            // Procesar los resultados
            while (rs.next()) {
                datos[0] = rs.getString("id_empt");
                datos[1] = rs.getString("num_emp");
                datos[2] = rs.getString("nombre_operario");
                datos[3] = rs.getString("nombre_linea");
                datos[4] = numturno(rs.getInt("turno")); // Llama a la función para convertir el turno
                datos[5] = rs.getString("observaciones");

                // Agregar la fila al modelo de tabla
                tab.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Cerrar los recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        return tab;
    }
    public boolean consultarE(Operario Op){
        Encargado aux=new Encargado();
        sql="SELECT  `num_emp` FROM `encargado` WHERE `num_emp`='"+Op.getNum_emp()+"'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())//Se guarda el resultado en el objeto op 
            {
                 aux.setNumEmpleado(rs.getString(1));  
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        aux.impData();
        return !aux.validarEnc();
    }
    public boolean altaEncargado(Operario op){ //Da de alta un nuevo encargado 
        boolean val=false;
        sql="INSERT INTO `encargado`(`id_emptE`, `num_emp`, `nombre`, `linea`, `turno`, `contrasena`,`estado`, `tipo_cuenta`) VALUES (?,?,?,?,?,SHA2(?, 256),?,?)";

        try {
        PreparedStatement t = (PreparedStatement) cn.prepareStatement(sql);
            t.setString(1, op.getId_empt());
            t.setString(2, op.getNum_emp());
            t.setString(3, op.getNombre());
            t.setInt(4, op.getLinea());
            t.setInt(5, op.getTurno());
            t.setString(6,  "123456");
            t.setInt(7, 1);
            t.setInt(8, 2);
            
            int reg=t.executeUpdate();
            if(reg==1){
                s.advertencia("El operario: "+op.getNum_emp()+" es encargado, la contraseña es de 1 a 6,\n se recomienda cambiar en el momento que se inicie sesion...");
                val=true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(""+ex);
            s.error("El operario seleccionado ya es encargado");
        }
             
        return val;
    }
    public DefaultTableModel mostrarHerramental(){
       DefaultTableModel tab=new DefaultTableModel();
       sql="""
           SELECT   e.id_herramental, 
                           e.nombre, 
                           e.observaciones, 
                           e.vieneta, 
                           e.estado, 
                           li.nombre,
                           e.reporte
                       FROM 
                           heramental e
                       JOIN 
                           lineas li 
                       ON 
                           e.linea = li.id_linea
                       WHERE e.reporte!=2    
           """;
       
       
       tab.addColumn("ID Herramental");
       tab.addColumn("Nombre");
       tab.addColumn("Detalles");
       tab.addColumn("Viñeta");
       tab.addColumn("Estado");
       tab.addColumn("Linea");
       tab.addColumn("Reporte");
       String [] datos=new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(sql);

            while (rs.next())
            {
                 datos[0]=rs.getString(1);
                 datos[1]=rs.getString(2);
                 datos[2]=rs.getString(3);
                 datos[3]=rs.getString(4);
                 if(rs.getInt(5)==1){
                     datos[4]="Disponible";
                 }
                 else{
                     datos[4]="Prestado";
                 }
                 datos[5]=rs.getString(6);
                 if(rs.getInt(7)==1){
                     datos[6]="Si";
                 }
                 else{
                     datos[6]="No";
                 }
                 tab.addRow(datos);
            }    

            } catch (SQLException ex) {
               Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }           
       return tab;
    }
    public DefaultTableModel buscarHerramental(String cadena) {
        // Crear el modelo de la tabla
        DefaultTableModel tab = new DefaultTableModel();

        // Definir las columnas
        tab.addColumn("ID Herramental");
        tab.addColumn("Nombre");
        tab.addColumn("Detalles");
        tab.addColumn("Viñeta");
        tab.addColumn("Estado");
        tab.addColumn("Línea");
        tab.addColumn("Reporte");

        // Consulta SQL con placeholders (?)
        sql = "SELECT e.id_herramental, " +
                     "e.nombre AS nombre_herramental, " +
                     "e.observaciones, " +
                     "e.vieneta, " +
                     "e.estado, " +
                     "li.nombre AS nombre_linea, " +
                     "e.reporte " +
                     "FROM heramental e " +
                     "JOIN lineas li ON e.linea = li.id_linea " +
                     "WHERE e.nombre LIKE ? OR e.id_herramental LIKE ?";

        try {
            // Preparar la consulta
            PreparedStatement stmt = cn.prepareStatement(sql);

            // Asignar los parámetros
            String parametroBusqueda = "%" + cadena + "%";
            stmt.setString(1, parametroBusqueda);
            stmt.setString(2, parametroBusqueda);

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            // Arreglo para almacenar cada fila de datos
            String[] datos = new String[7]; // Debe ser de longitud 6

            // Procesar los resultados
            while (rs.next()) {
                datos[0] = rs.getString("id_herramental");
                datos[1] = rs.getString("nombre_herramental");
                datos[2] = rs.getString("vieneta");
                datos[3] = rs.getString("observaciones");
                // Convertir el estado en texto
                datos[4] = rs.getInt("estado") == 1 ? "Disponible" : "Prestado";
                datos[5] = rs.getString("nombre_linea");
                datos[6]= rs.getInt("reporte")==1?"No":"Si";
                // gregar la fila al modelo de tabla
                tab.addRow(datos);
            }
        } catch (SQLException ex) {
            System.out.println(""+ex);
        }

        return tab;
    }
    public boolean altaHtal(Herramental htal){
        sql="INSERT INTO `heramental`(`id_herramental`, `nombre`, `observaciones`, `vieneta`, `estado`, `linea`,`reporte`) VALUES"
                + "(?,?,?,?,?,?,?) ";
        try{
            PreparedStatement t=(PreparedStatement)cn.prepareStatement(sql);
            t.setString(1, htal.getId_herramental().toUpperCase());
            t.setString(2, htal.getNombre());
            t.setString(3, htal.getObservacion());
            t.setString(4, htal.getViñeta());
            t.setInt(5, htal.getEstado());
            t.setInt(6, htal.getLinea());
            t.setInt(7, htal.getReporte());
            int res=t.executeUpdate();
            if(res==1){
                //s.info("Se ha guardado correctamente el herramantal");
                return true;
            }
        }catch(SQLException ex){
            //Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(""+ex);
            s.error("Ha ocurrido un error al registrar el Herrmantal \nRevisa que el id del Herrmantal sea diferente a los existentes");
            return false;
        }
        return false;
    }
    public Operario selectOp(String numeroOp){//Funcion se encarga de buscar el operario
        sql= "SELECT `id_empt`, `num_emp`, `nombre`, `linea`, `turno`, `observaciones` FROM `operario` WHERE  `num_emp`='" + numeroOp + "' OR `id_empt`='" +numeroOp+"'";
        //Se busca que el operario exixta en la base de datos 
        Operario op=new Operario();
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())//Se guarda el resultado en el objeto op 
            {
                 op.setId_empt(rs.getString(1));
                 op.setNum_emp(rs.getString(2));
                 op.setNombre(rs.getString(3));
                 op.setLinea(rs.getInt(4));
                 op.setTurno(rs.getInt(5));
                 op.setObservaciones(rs.getString(6));
                 
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return op; //se regresa el valor encontrado que es el objeto op En caso de ser null se descarta que exista el op
    }
    public String fechaActual(){
        String fecha,aux;
        aux=LocalTime.now().toString();
        aux=aux.substring(0, 8);
        fecha=LocalDate.now()+" "+aux;
        return fecha;
    }
    public void cambioEstado(String idHtal, boolean estado){//El estado indica que cambio se va a realizar, si se coloca en dispopnible o ocupado
        //true es igual a disponible ////// false es iguel a ocupado 
        //En esta funcion se actualiza la informacion del herramental, se cambia su estado a ocupado
        if(estado){//True igual a disponible
            sql="UPDATE `heramental` SET `estado`=0 WHERE `id_herramental`='"+idHtal+"'";
            System.out.println(idHtal+" Disponible");
        }
        else{//False quiere dacir que esta Ocupado
            sql="UPDATE `heramental` SET `estado`=1 WHERE `id_herramental`='"+idHtal+"'";
            System.out.println(idHtal+" Ocupado");
        }
        try {
            Statement st = cn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
    public boolean consultaEstado(String idHtal,boolean dato){//Si dato es igual a true, se esta buscando que estado sea igual a 1
        //En caso de que dato sea false se busca que este ocupado
        //Esta funcion consulta el estado del htal
        //En caso de estar diponible regresa true en caso de estar ocupado regresa false en alta htal 
        //En esta fucion se valida que el herramental se esncientre disponible 
        sql="SELECT `estado`,`reporte` FROM `heramental` WHERE `id_herramental`='"+idHtal+"'";
        int estado=-1; //En caso de que no se encuente disponible esta no 0 en caso de estarlo estado cambia a 1
        int rep=0;// Valida que no tenga reportes
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())//Se guarda el resultado en el objeto op 
            {
                 estado=rs.getInt(1); //El estado se toma de la tabla 
                 rep=rs.getInt(2);
                 System.out.println(" "+estado+" "+rep);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        if(dato){
            return estado==1&&rep==0;
        }
        else{
            return estado==0;
        }      
    }
    public void altaHtal(Encargado encargado, String numeroOp, String idHtal){
        Operario op;
        op=this.selectOp(numeroOp);
        op.impOp();
        //Se realiza la consulta para sabr si existe el op en caso de ser null no existe
        if(op.validacion()){
            //Desde aqui se arma la consulta
            //Se requieren 3 datos y actualizaciones a los herramentales, Donde quede indicado que esta en prestamo
            if(this.consultaEstado(idHtal,true)){//consula el estado del htal
                
                sql="INSERT INTO `tem_registro`( `id_htal`, `fecha_prestamo`, `num_encargado`, `num_emp`, `id_empt`) VALUES (?,?,?,?,?)";
                try {
                    PreparedStatement t = (PreparedStatement) cn.prepareStatement(sql);
                        t.setString(1, idHtal);
                        t.setString(2, this.fechaActual());
                        t.setString(3, encargado.getNumEmpleado());
                        t.setString(4, op.getNum_emp());
                        t.setString(5, op.getId_empt());
                    int reg=t.executeUpdate();
                    if(reg==1){
                        s.info("Se ha guardado el registro");
                        this.cambioEstado(idHtal, true);
                        //En caso de que sea true cambia su estado a ocupado
                    }
                    else{
                        s.error("Ha ocurrido un error");
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(""+ex);
                }        
            }
            else{
                s.error("El herramental no esta diponible...");
            }
        }
        else{
            s.error("El operario Seleccionado no existe");
        }
    }
    public Registro consulaRegistro(String idHtal){//La funcion buscara en los registros temporales, para saber si esta en prestamo el herramental
        Registro registro=new Registro();//Se regresara el resultado en un objeto resgistro
        sql="SELECT `id_registro`, `id_htal`, `fecha_prestamo`, `num_encargado`, `num_emp`, `id_empt` FROM `tem_registro` WHERE `id_htal`='"+idHtal+"'";
        //Se busca que el registro este en tabla
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())//Se guarda el resultado en el objeto registro
            {
                 registro.setId_registro(rs.getInt(1));
                 registro.setId_htal(rs.getString(2));
                 registro.setFecha_prestamo(rs.getString(3));
                 registro.setFecha_entrega("");
                 registro.setNum_encargado(rs.getString(4));
                 registro.setNum_empleado(rs.getString(5));
                 registro.setId_empt(rs.getString(6));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return registro;
    }
    public void bajaHtal(String idHtal,Encargado encargado){
        Registro registro;
        if(this.consultaEstado(idHtal, false)){//Se busca que el herramental este ocupado
    // en caso de estarlo
            this.cambioEstado(idHtal, false);//Cambia el estado a disponible
    //Se consulta en los datos 
            try{
                registro=this.consulaRegistro(idHtal);
    //consulta para registrar los datos permanantes en la tabla
                if(registro.validacion()){ //se crea la consulta para el registro permanente con los datos que hacen falta
                    sql="INSERT INTO `registro`(`id_registro`, `id_htal`, `fecha_prestamo`, `fecha_entrega`,"
                            + " `num_encargado`, `num_encargado_r`, `id_operario`, `id_empt` ) VALUES (?,?,?,?,?,?,?,?)";
                    try {
                        PreparedStatement t = (PreparedStatement) cn.prepareStatement(sql);
                        t.setInt(1, registro.getId_registro());
                        t.setString(2, registro.getId_htal());
                        t.setString(3,registro.getFecha_prestamo());
                        t.setString(4, this.fechaActual());
                        t.setString(5, registro.getNum_encargado());
                        t.setString(6, encargado.getNumEmpleado());
                        t.setString(7, registro.getNum_empleado());
                        t.setString(8, registro.getId_empt());
                        this.eliminarRegistro(idHtal);
                        int reg=t.executeUpdate();
                        if(reg==1){
                            s.info("Se ha guardado el registro");
                        }
                        else{
                            s.error("Ha ocurrido un error");
                        }
                    
                    } catch (SQLException ex) {
                        Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println(""+ex);
                    } 
                }
                else{
                    s.error("No existe el registro... Vuelve a intentarlo");
                }
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
    }
    public void eliminarRegistro(String idHtal){
//Se encarga de eliminar el registro por medio de un consulta en la tabla temporal
       sql="DELETE FROM `tem_registro` WHERE `id_htal`='"+idHtal+"'";
        try {
            Statement st = cn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex);
        } 
                
    }
    public String numturno(int turno){
        String t;
        switch(turno){
            case 1 ->t="Nocturno";
            case 2 ->t="Matutino";
            case 3 ->t="Vespertino";
            case 4 ->t="Administrativo";
            default -> t="";
        }
        return t;
    }
    public int turnonum(String cadena){
        if(cadena.equals("Nocturno")){
            return 1;
        }
        if(cadena.equals("Matutino")){
            return 2;
        }
        if(cadena.equals("Vespertino")){
            return 3;
        }
        if(cadena.equals("Administrativo")){
            return 4;
        }
        return 0;
    }
    public int lineaNum(String cadena){ //Se encarga de convertir las lineas entregadas en numero
        Linea linea;
        sql="SELECT `id_linea`, `nombre` FROM `lineas` WHERE 1";
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())//Se guarda el resultado en el objeto registro
            {
                 linea=new Linea();
                 linea.setIdLinea(rs.getInt(1));
                 linea.setNombre(rs.getString(2));
                 if(cadena.equals(linea.getNombre())){
                     return linea.getIdLinea(); //En caso de existir regresa el valor de la linea 
                 }
                 //linea.impData();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return 0; //En caso de no existir te regresa 0;
        }        
        return 0;
    }
    public String generarLineas(){ //Se encarga de convertir las lineas entregadas en numero
        Linea linea;
        String lineas="";
        sql="SELECT `id_linea`, `nombre` FROM `lineas` WHERE 1";
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())//Se guarda el resultado en el objeto registro
            {
                 linea=new Linea();
                 linea.setIdLinea(rs.getInt(1));
                 linea.setNombre(rs.getString(2));
                 //linea.impData();
                 lineas = lineas+linea.getNombre()+" ";
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }        
        return lineas;
    }
    public DefaultTableModel mostrarLineas(){
        DefaultTableModel tab=new DefaultTableModel();
        String [] datos=new String[2];
        tab.addColumn("ID Linea");
        tab.addColumn("Nombre");
        sql="SELECT `id_linea`, `nombre` FROM `lineas` WHERE 1";
        try {
            Statement st = cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while (rs.next())//Se guarda el resultado en el objeto registro
            {
                 datos[0]=rs.getInt(1)+"";
                 datos[1]=rs.getString(2);
                 //linea.impData();
                 tab.addRow(datos);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } 
        return tab;
    }
    public boolean altaLinea(Linea li){
        sql="INSERT INTO `lineas`( `nombre`) VALUES (?)";
        try {
            PreparedStatement t=(PreparedStatement)cn.prepareStatement(sql);
            //t.setInt(1, li.getIdLinea());
            t.setString(1, li.getNombre().toUpperCase());
            int reg=t.executeUpdate();
            return reg==1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public boolean updateLinea(Linea li){
        sql="UPDATE `lineas` SET `id_linea`=?,`nombre`=? WHERE `id_linea`=? ";
        try {
            PreparedStatement st=cn.prepareStatement(sql);
            st.setInt(1,li.getIdLinea());
            st.setString(2, li.getNombre().toUpperCase());
            st.setInt(3,li.getIdLinea());
            if(li.getNombre().length()>0){
                int reg=st.executeUpdate();
                return reg==1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public int numEstado(String cadena){
        if(cadena.equals("Disponible")){
            return 1;
        }
        if(cadena.equals("Prestado")){
            return 0;
        }
        if(cadena.equals("Inactivo")){
            return 2;
        }
        return 0;
    }
    public int numReporte(String cadena){
        if(cadena.equals("No")){
            return 1;
        }
        if(cadena.equals("Si")){
            return 0;
        }
        return 0;
    }
    public String cuentaEstado(int est){
        if(est==1){
            return "Activo";
        }
        else{
            return "Bloqueado";
        }
    }
    public int cuentaEstado(String est){
        if(est.equals("Activo")){
            return 1;
        }
        else{
            return 0;
        }
    }
    public String tipoCuenta(int cuenta){
        if(cuenta==0){
            return "Administrador";
        }
        if(cuenta==1){
            return "EncargadoHtal";
        }
        if(cuenta==2){
            return "Encargado";
        }
        return "Desconocido";
    }
    public int tipoCuenta(String cuenta){
        if(cuenta.equals("Administrador")){
            return 0;
        }
        if(cuenta.equals("EncargadoHtal")){
            return 1;
        }
        if(cuenta.equals("Encargado")){
            return 2;
        }
        return 2;
    }
    public String tipoReporte(int reporte){
        if(reporte==0){
           return "Sin Reporte"; 
        }
        if(reporte==1){
           return "Otro"; 
        }
        if(reporte==2){
           return "Extravio o Perdida"; 
        }
        if(reporte==3){
           return "Falla o Daño"; 
        }
        if(reporte==4){
           return "Mantenimiento"; 
        }
        return "Desconocido";
    }
    public int tipoReporte(String reporte){
        if(reporte.equals("Sin Reporte")){
           return 0; 
        }
        if(reporte.equals("Otro")){
           return 1; 
        }
        if(reporte.equals("Extravio o Perdida")){
           return 2; 
        }
        if(reporte.equals("Falla o Daño")){
           return 3; 
        }
        if(reporte.equals("Mantenimiento")){
           return 4; 
        }
        return 1;
    }
    //Administracion de Ops
    public boolean ModOp(Operario op){//Se modifica el operario
        sql="UPDATE `operario` SET `nombre`=? ,`linea`=? ,`turno`=? ,`observaciones`=? WHERE `num_emp`=?";
        if(s.opcion("¿Realmente desea Modificar el operario con los valores actuales?")){
            if(op.validacion()){
                try{
                    PreparedStatement t=(PreparedStatement) cn.prepareStatement(sql);
                        t.setString(1, op.getNombre());
                        t.setInt(2, op.getLinea());
                        t.setInt(3, op.getTurno());
                        t.setString(4, op.getObservaciones());
                        t.setString(5, op.getNum_emp());
                    int reg=t.executeUpdate();
                    if(reg==1){
                        s.info("Se ha modificado el registro");
                        return true;
                    }
                    else{
                        s.error("Ha courrido un error al guardar");
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            else{
                s.error("Los datos proporcionados no son correctos...");
            }
        }
        return false;  
    }
    
}
