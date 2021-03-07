package adivinavisual;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.*;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javax.swing.JFrame;

/**
 *
 * @author Florian
 */
public class FXMLDocumentController implements Initializable {

    static ArbolB.Nodo nodo = new ArbolB.Nodo<>();
    ArbolB.Nodo al = new ArbolB.Nodo<>();

    @FXML
    private void Adivina(ActionEvent event) {
        ArbolB.Nodo nodoAnterior = null;
        ArbolB.Nodo nodoNuevo = null;
        File a = new File("Animales.bin");//Fichero binario o de datos esta formado por secuencias de bytes puede contener datos de tipo básico (int, float, chat, etc) y objetos.

        if (a.exists()) {
            nodoNuevo = Deserializar();
        } else {
            nodoNuevo = new ArbolB.Nodo<>("El animal es doméstico?");
            //izq = si
            //der = no
            nodoNuevo.raiz = nodoNuevo;
            nodoNuevo.izq = new ArbolB.Nodo<>("Perro");
            nodoNuevo.der = new ArbolB.Nodo<>("Tigre");
            Persitencia(nodoNuevo); //Esta sentencia permite guardar un nodo nuevo cuando se agrega un animal
        }

        String responde = null;
        ArbolB.Nodo reco;
        String ds;
        reco = nodoNuevo.raiz;

        try {
            while (reco != null) {
                if (nodoNuevo.validaEmpty(reco)) {//Enmpty verifica si una cadena es nula o vacía
                    nodoAnterior = reco;

                    //Primero consulta si el animal es domestico
                    if (reco.info.equals("El animal es doméstico?")) {
                        TextInputDialog dialog = new TextInputDialog("");
                        dialog.setTitle("Adivinador de Animales");
                        dialog.setHeaderText("Ingrese s/n ó no sé");
                        dialog.setContentText(reco.info + " ");
                        // Obtener el valor de la respuesta
                        Optional<String> result = dialog.showAndWait();
                        responde = result.get();
                    } else {
                        TextInputDialog dialog1 = new TextInputDialog("");
                        dialog1.setTitle("Adivinador de Animales");
                        dialog1.setHeaderText("Ingrese s/n ó no sé");
                        dialog1.setContentText("El animal que pensaste " + reco.info + "? ");
                        //  Obtener el valor de la respuesta
                        Optional<String> result1 = dialog1.showAndWait();
                        responde = result1.get();
                    }
                    if (responde.equalsIgnoreCase("s")) {
                        reco = reco.izq;
                    } else if (responde.equalsIgnoreCase("no se")) {
                        ArbolB.Nodo x;
                        ArbolB.Nodo y;
                        x = reco.izq;
                        y = reco.der;
                        TextInputDialog dialog2 = new TextInputDialog("");
                        dialog2.setTitle("Adivinador de Animales");
                        dialog2.setHeaderText("Ingrese s/n ó no se");
                        dialog2.setContentText("Tengo estas respuestas para ti, pulse 1 ó 2 para la primera o "
                                + "segunda: \n" + x.info + "  ó  " + y.info + " ");
                        //  Obtener el valor de la respuesta
                        Optional<String> result2 = dialog2.showAndWait();
                        ds = result2.get();
                        if (ds.equals("1")) {
                            reco = reco.izq;
                        } else {
                            reco = reco.der;
                        }
                    } else {
                        reco = reco.der; //si la respuesta es no se ira a la derecha
                    }
                } else {//Es else es cuando la respuesta fué no y se va al nodo derecho
                    TextInputDialog dialog3 = new TextInputDialog("");
                    dialog3.setTitle("Adivinador de Animales");
                    dialog3.setHeaderText("Ingrese s/n ó no sé");
                    dialog3.setContentText("El animal que piensa es un(a)" + reco.info + "? ");
                    //  Obtener el valor de la respuesta
                    Optional<String> result3 = dialog3.showAndWait();
                    String numero = result3.get();
                    if (numero.equalsIgnoreCase("s")) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Gané");
                        alert.setHeaderText(null);
                        alert.setContentText("¡Soy el mejor!");
                        alert.showAndWait();

                        TextInputDialog dialog4 = new TextInputDialog("");
                        dialog4.setTitle("Adivinador de Animales");
                        dialog4.setHeaderText("Ingrese s/n ó no sé ó ingrese d para ver el arbol binario");
                        dialog4.setContentText("Deseas seguir jugando?");
                        //  Obtener el valor de la respuesta
                        Optional<String> result4 = dialog4.showAndWait();
                        String respuesta4 = result4.get();
                        if (respuesta4.equalsIgnoreCase("s")) {
                            reco = nodoNuevo;
                        } else if (respuesta4.equalsIgnoreCase("d")) {
                            setNodo(nodoNuevo);
                            DibujaArbol();

                            nodoAltura();
                            reco = null;
                        } else {
                            reco = null;
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Perdí");
                        alert.setHeaderText(null);
                        alert.setContentText("¡No lo pude adivinar!");
                        alert.showAndWait();

                        TextInputDialog dialog5 = new TextInputDialog("");
                        dialog5.setTitle("Adivinador de Animales");
                        dialog5.setHeaderText("Ingrese s/n ó no se");
                        dialog5.setContentText("Escriba el nombre del animal que estas pensando ");
                        //  Obtener el valor de la respuesta
                        Optional<String> result5 = dialog5.showAndWait();
                        String res = result5.get();

                        TextInputDialog dialog6 = new TextInputDialog("");
                        dialog6.setTitle("Adivinador de Animales");
                        dialog6.setHeaderText("Ingrese s/n ó no se");
                        dialog6.setContentText("Escriba una afirmación que sea verdadera para un(a) " + res
                                + " pero que sea falso para un(a) " + reco.info + " ");
                        // Obtener el valor de la respuesta
                        Optional<String> result6 = dialog6.showAndWait();
                        String respuesta2 = result6.get();
                        ArbolB.Nodo temp = reco;

                        ArbolB.Nodo nuevo = new ArbolB.Nodo<>(respuesta2);

                        if (responde.equalsIgnoreCase("s")) {
                            nodoAnterior.izq = nuevo;
                        } else {
                            nodoAnterior.der = nuevo;
                        }
                        nuevo.der = temp;
                        nuevo.izq = new ArbolB.Nodo<>(res);
                        TextInputDialog dialog7 = new TextInputDialog("");
                        dialog7.setTitle("Adivinador de Animales");
                        dialog7.setHeaderText("Ingrese s/n ó no sé ó ingrese d para modo depuracion");
                        dialog7.setContentText("Deseas seguir jugando?");
                        //  Obtener el valor de la respuesta
                        Optional<String> result7 = dialog7.showAndWait();
                        String respuesta3 = result7.get();
                        if (respuesta3.equalsIgnoreCase("s")) {
                            reco = nodoNuevo;
                        } else if (respuesta3.equalsIgnoreCase("d")) {
                            setNodo(nodoNuevo);
                            DibujaArbol();

                            nodoAltura();
                            reco = null;
                        } else {
                            reco = null;
                        }
                    }//Fin cuando el programa perdio
                } //fin de else cuando la respuesta es no
            }//Fin de while
            setNodo(nodoNuevo);
        } catch (Exception e) {
            System.out.println(e);
        }
        Persitencia(nodoNuevo);
    }

    // Hace persistente el arbol, lo almacena en un archivo .bin
    private static ArbolB.Nodo Persitencia(ArbolB.Nodo nodonuevo) {
        try {
            final FileOutputStream fo = new FileOutputStream("Animales.bin");
            final ObjectOutputStream oos = new ObjectOutputStream(fo);
            oos.writeObject(nodonuevo); //Escribe el nombre en un nodo nuevo
            oos.flush();
            oos.close();//Cierra el archivo
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Abre el archivo .bin y carga el arbol previamente almacenado
    private static ArbolB.Nodo Deserializar() {
        ArbolB.Nodo nodoNuevo = null;
        try {
            final FileInputStream fis = new FileInputStream("Animales.bin");
            final ObjectInputStream ois = new ObjectInputStream(fis);
            final Object deserializedObject = ois.readObject();
            if (deserializedObject instanceof ArbolB.Nodo) {
                nodoNuevo = (ArbolB.Nodo) deserializedObject;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nodoNuevo;
    }

    //Devuelve la altura del arbol.
    @FXML
    private void nodoAltura() {

    }

    public static void setNodo(ArbolB.Nodo nodo) {
        FXMLDocumentController.nodo = nodo;  //Invoca la clase principal, para añadir un nuevo nodo
    }

    public static ArbolB.Nodo getNodo() { //Muestra los nodos
        return nodo;
    }

    //Este metodo despliega la pantalla donde se encuentra dibujado el arbol binario
    @FXML
    private void DibujaArbol() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screen.width / 2 - 1280 / 2;
        int y = screen.height / 2 - 720 / 2;

        JFrame Arbol = new JFrame("Depuración");

        Dibujar dibujar = new Dibujar(getNodo());

        Arbol.add(dibujar);
        Arbol.setSize(960, 500);
        Arbol.setLocation(x, y);
        Arbol.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
