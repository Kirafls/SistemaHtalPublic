 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author sauls
 */
public class SistemaHerramentales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Programa principal inicio del programa
        // TODO code application logic here
        //Inicio del sistema herramentales 
        //Formato para almacenamiento de fechas "AAAA-MM-DD HH:MM:SS" 
        // Asegúrate de aplicar el Look & Feel Nimbus ANTES de crear la ventana
        setNimbusLookAndFeel();

        System.out.println("Sistema iniciado...");

        // Crear y mostrar la ventana en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            Inicio f = new Inicio();
            f.setVisible(true);
        });
    }

    // Método para aplicar el Look and Feel Nimbus
    private static void setNimbusLookAndFeel(){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    System.out.println("Nimbus");
                    break;
                }
            }
        } catch (ClassNotFoundException | 
                 InstantiationException | 
                 IllegalAccessException | 
                 UnsupportedLookAndFeelException e) {
            System.err.println("Error al aplicar Nimbus Look and Feel: \n" + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
}
/*
    reportes a generar 
operarios
ok    1- empleados por linea 
ok    2- numero de reportes por operario
ok    4- numero de operarios por turno
Herramental
ok    3- reporte de uso total del herramental hr
ok    5- herrmantales con mayor cantadad de registros
ok    8- numero de reportes por herramenatal
Cuentas
ok    6- Estado de las cuentas y tipo de cuentas 
pendiente    9- Generacion de codigo de barras
Reportes
ok    7- Cantidad de registros por turno
ok    10- Reportes Activos 
ok    11- Reportes por linea
ok    12- Reportes cerrados

*/