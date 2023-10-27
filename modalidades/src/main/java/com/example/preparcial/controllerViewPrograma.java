package com.example.preparcial;

import com.example.preparcial.model.Progama;
import com.example.preparcial.utils.Persistencia;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class controllerViewPrograma implements Initializable {

    //JAVA FX

    @FXML
    private ComboBox<String> comboModalidad;
    @FXML
    private Button btnListar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField Buscador;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtModalidad;
    @FXML
    private Pane paneBotones;
    @FXML
    private Pane paneCampos;
    @FXML
    private Pane panePrincipal;
    @FXML
    private Pane paneTable;
    @FXML
    private TableView<Progama> tableDatos;
    @FXML
    private TableColumn<Progama, String> columnaUno;
    @FXML
    private TableColumn<Progama, String> columnaDos;
    @FXML
    private TableColumn<Progama, String> columnaTres;
    @FXML
    private TableColumn<Progama, String> columnaCuatro;
    @FXML
    private TableColumn<Progama, String> columnaCinco;

    //-------------------------------------JAVA--------------------------------
    private String nombre = "";
    private String codigo = "";
    private String modalidad = "";
    //-----------------------------------------LISTAS-----------------------------------
    private ObservableList<Progama> listaPrincipal = FXCollections.observableArrayList();
    private ArrayList<Progama> progamas = new ArrayList();
    private Progama progama;
//--------------------------------------------AGREGAR----------------------------
    @FXML
    void agregar(ActionEvent event) throws IOException {
        nombre = txtNombre.getText();
        codigo = txtCodigo.getText();
        modalidad = comboModalidad.getValue();
        if (datosValidos()) {
            try {
                progama = new Progama(codigo, nombre, modalidad);
                if (Persistencia.cargarRecursoBancoXML() == null) {
                    progamas.add(progama);
                } else {
                    progamas = Persistencia.cargarRecursoBancoXML();
                    progamas.add(progama);
                }

                listaPrincipal.clear();
                listaPrincipal.addAll(progamas);
                columnaUno.setCellValueFactory(new PropertyValueFactory("codigo"));
                columnaDos.setCellValueFactory(new PropertyValueFactory("nombre"));
                columnaTres.setCellValueFactory(new PropertyValueFactory("modalidad"));
                tableDatos.setItems(listaPrincipal);
                Persistencia.guardarRecursoBancoXML(progamas);
                registrarAcciones("Producto agregado", 1, "Agregar Producto");
                mostrarMensaje("Crear Producto", "Producto creado", "El producto fue creado ", AlertType.CONFIRMATION);
            } catch (NumberFormatException var3) {
                mostrarMensaje("Datos invalidos", "campo valor", "no se pueden ingresar letras \nen el campo valor debe ingresar un valor numerico", AlertType.ERROR);
                registrarAcciones("Exception Producto campo numerico", 1, "En un campo llamado valor en la view de prodcuto se lanza exception por que ingresan letras y deben ser numeros");
            }
        }

    }
    //----------------------------INITIALIZABLE---------------------------------------

    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboModalidad.getItems().addAll(Persistencia.archivoPropiedades());
        Persistencia.archivoPropiedades();
        progamas = Persistencia.cargarRecursoBancoXML();
        listaPrincipal.addAll(progamas);
        columnaUno.setCellValueFactory(new PropertyValueFactory("codigo"));
        columnaDos.setCellValueFactory(new PropertyValueFactory("nombre"));
        columnaTres.setCellValueFactory(new PropertyValueFactory("modalidad"));
        tableDatos.setItems(listaPrincipal);
        Persistencia.guardarRecursoBancoXML(progamas);
    }

    //--------------------------------LOG------------------------------------

    private void registrarAcciones(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }


//------------------------LISTAR----------------------------------------------------
    @FXML
    void listar(ActionEvent event) {
        progamas = Persistencia.cargarRecursoBancoXML();
        listaPrincipal.clear();
        listaPrincipal.addAll(progamas);
        columnaUno.setCellValueFactory(new PropertyValueFactory("codigo"));
        columnaDos.setCellValueFactory(new PropertyValueFactory("nombre"));
        columnaTres.setCellValueFactory(new PropertyValueFactory("modalidad"));
        tableDatos.setItems(listaPrincipal);
        tableDatos.refresh();
        Persistencia.guardarRecursoBancoXML(progamas);
    }
    //--------------------------------------BUSCAR--------------------------------------

    @FXML
    void buscar(ActionEvent event) throws IOException {
        progamas = Persistencia.cargarRecursoBancoXML();
        boolean bandera = true;
        for (Progama p1:progamas) {
            if(p1.getCodigo().equals(btnBuscar.getText()));
            listaPrincipal.clear();
            listaPrincipal.addAll(p1);



            tableDatos.setItems(listaPrincipal);
            tableDatos.refresh();


            //se guarda en el txt
            Persistencia.guardarRecursoBancoXML(progamas);


            registrarAcciones(" Estudiante encontrado",1, "se encontro el estudiantes buscado por el codigo");
            bandera=false;
            break;


        }



        if (bandera) {
            this.registrarAcciones("No se encontro", 1, "No se encontro al usuario");
            this.mostrarMensaje("No se encontro", "No existe el estudiante", "no se encontro al estudiante", AlertType.WARNING);
        }

    }
    //------------------------DATOS VALIDOS-------------------------------

    private boolean datosValidos() {
        String mensaje = "";
        if (txtNombre.getText() == null || txtNombre.getText().equals("")) {
            mensaje = mensaje + "El campo del nombre debe rellnarlo  \n";
        }

        if (txtCodigo.getText() == null || txtCodigo.getText().equals("")) {
            mensaje = mensaje + "El campo de la descripcion debe rellenarlo \n";
        }
        if (comboModalidad.getValue() == null) {
            mensaje = mensaje + "debe selecionar un tipo de modal \n";
        }


        if (mensaje.equals("")) {
            return true;
        } else {
            this.mostrarMensaje("Notificación cliente", "Datos invalidos", mensaje, AlertType.WARNING);
            return false;
        }
    }
    //-------------------MOSTRAR MENSAJE----------------------------------------

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }
}
