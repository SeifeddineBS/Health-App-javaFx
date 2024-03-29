/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aura.Objectif_Suivi;

import Entities.ObjectifPred;
import Service.ServiceAdmin;
import Service.ServiceObjectif;
import Service.ServiceObjectifPred;
import Service.ServicePdf;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author Chirine
 */
public class ObjectifPredefiniController implements Initializable {

    @FXML
    private TextField tfdesc_pred;
    @FXML
    private TextField tfduree_pred;
    @FXML
    private Button btnAjouterPred;
    @FXML
    private Button btnModifierPred;
    @FXML
    private Button btnSupprimerPred;
    @FXML
    private TableView<ObjectifPred> tvObjectifPred;
    @FXML
    private TableColumn<ObjectifPred, String> coldes_pred;
    @FXML
    private TableColumn<ObjectifPred, String> colduree_pred;
    @FXML
    private TableColumn<ObjectifPred, String> colidad_pred;
    @FXML
    private TextField tfrechObjPred;
    @FXML
    private ComboBox<String> cbtriObjPred;
    public String id_user = "";
    private String lien_icone = "";

    @FXML
    private Button btn_AjouterIcone;
    @FXML
    private TextField tf_icon_name;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ServiceObjectifPred sop = new ServiceObjectifPred();
        ObservableList<ObjectifPred> objectifsP = sop.afficherObjectifsPred();

        ObservableList<String> listTriObjPred = FXCollections.observableArrayList("par description", "par durée");
        cbtriObjPred.setItems(listTriObjPred);

        afficherObjectifsPred();
        new animatefx.animation.Flash(btnAjouterPred).play();
        new animatefx.animation.Flash(btnModifierPred).play();
        new animatefx.animation.Flash(btnSupprimerPred).play();
        tfdesc_pred.setText(null);
        tfduree_pred.setText(null);
        tf_icon_name.setText(null);

    }

    public void initializeFxml(String id) {

        System.out.println(id_user);
    }

    @FXML
    private void ajouterPred(ActionEvent event) {
        ServiceObjectifPred sp = new ServiceObjectifPred();
        ObjectifPred o = new ObjectifPred();
        ServiceAdmin sa = new ServiceAdmin();
        if (tfdesc_pred.getText() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Attention");
            a.setContentText("Vous devez saisir la description de l'objectif");
            a.setHeaderText(null);
            a.showAndWait();
        } else if (tfduree_pred.getText() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Attention");
            a.setContentText("Vous devez proposez une durée propre à l'objectif");
            a.setHeaderText(null);
            a.showAndWait();
        } else if (tfduree_pred.getText().equals(null) && tfdesc_pred.getText().equals(null)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Attention");
            a.setContentText("Vous devez effectuer la saisie avant d'ajouter");
            a.setHeaderText(null);
            a.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation ajout objectif prédéfini");
            alert.setHeaderText("Etes vous sur de vouloir ajouter cet objectif?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                o.setDescriptionP(tfdesc_pred.getText());
                o.setDureeP(Integer.parseInt(tfduree_pred.getText()));
                o.setAdmin(sa.load_data_modify(id_user));
                if(tf_icon_name.getText()==null)
                {
                    o.setIcone("defaut.png");
                }else{
                    o.setIcone(tf_icon_name.getText());
                }
                sp.ajouterObjectifPred(o);
                afficherObjectifsPred();
                tfdesc_pred.setText(null);
                tfduree_pred.setText(null);
                new animatefx.animation.Swing(btnAjouterPred).play();
            } else {
                System.out.println("Cancel");
            }

        }
    }

    private void afficherObjectifsPred() {
        ServiceObjectifPred sop = new ServiceObjectifPred();
        ObservableList<ObjectifPred> objectifsP = sop.afficherObjectifsPred();
        coldes_pred.setCellValueFactory(new PropertyValueFactory<>("descriptionP"));
        colduree_pred.setCellValueFactory(new PropertyValueFactory<>("dureeP"));
        colidad_pred.setCellValueFactory(new PropertyValueFactory<>("Admin"));
        tvObjectifPred.setItems(objectifsP);

    }

    @FXML
    private void modifierPred(ActionEvent event) {
        if (tfdesc_pred.getText() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Attention");
            a.setContentText("Vous devez saisir la description de l'objectif que vous voulez modifier (Vous pouvez le selectionner du tableau)");
            a.setHeaderText(null);
            a.showAndWait();
        } else if (tfduree_pred.getText() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Attention");
            a.setContentText("Vous devez proposez une durée propre à l'objectif pour le modifier");
            a.setHeaderText(null);
            a.showAndWait();
        } else if (tfduree_pred.getText().equals(null) && tfdesc_pred.getText().equals(null)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Attention");
            a.setContentText("Vous devez effectuer la saisie avant de modifier (Vous pouvez selectionner un objectif du tableau)");
            a.setHeaderText(null);
            a.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation modification");
            alert.setHeaderText("Etes vous sur de modifier cet objectif prédéfini?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                ServiceObjectifPred sp = new ServiceObjectifPred();
                ObjectifPred o = new ObjectifPred();
                ServiceAdmin sa = new ServiceAdmin();
                o.setIdP(sp.getIdParDesc(tfdesc_pred.getText()));
                o.setDescriptionP(tfdesc_pred.getText());
                o.setDureeP(Integer.parseInt(tfduree_pred.getText()));
                sp.modifierObjectifPred(o);
                afficherObjectifsPred();
                tfdesc_pred.setText(null);
                tfduree_pred.setText(null);
                new animatefx.animation.Swing(btnModifierPred).play();
            } else {
                System.out.println("Cancel");
            }

        }

    }

    @FXML
    private void supprimerPred(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation suppression");
        alert.setHeaderText("Etes vous sur de supprimer cet objectif prédéfini?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ServiceObjectifPred sp = new ServiceObjectifPred();
            sp.supprimerObjectifPred(sp.getIdParDesc(tfdesc_pred.getText()));
            afficherObjectifsPred();
            tfdesc_pred.setText(null);
            tfduree_pred.setText(null);
            new animatefx.animation.Swing(btnSupprimerPred).play();
        } else {
            System.out.println("Cancel");
        }
    }

    @FXML
    private void rechercherObjectifPred(KeyEvent event) {
        ServiceObjectifPred sop = new ServiceObjectifPred();
        ObservableList<ObjectifPred> objectifsP = sop.rechercherObjectifPred(tfrechObjPred.getText());
        tvObjectifPred.setItems(objectifsP);
    }

    @FXML
    private void selectTriObjPred(ActionEvent event) {
        ServiceObjectifPred sop = new ServiceObjectifPred();
        ObservableList<ObjectifPred> objectifs = FXCollections.observableArrayList();
        if (cbtriObjPred.getValue().equals("par ID")) {
            objectifs = sop.trierObjectifPredparId();
        } else if (cbtriObjPred.getValue().equals("par durée")) {
            objectifs = sop.trierObjectifPredparDuree();
        } else {
            objectifs = sop.trierObjectifPredparDesc();
        }
        tvObjectifPred.setItems(objectifs);
    }

    @FXML
    private void OnMouseObjectifPred(MouseEvent event) {
        ObjectifPred obj = tvObjectifPred.getSelectionModel().getSelectedItem();
        tfdesc_pred.setText(obj.getDescriptionP());
        tfduree_pred.setText("" + obj.getDureeP());
    }

    @FXML
    private void AjouterIcone(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        
        if (f !=null){
            System.out.println(f.getName());
            tf_icon_name.setText(f.getName());
        }else{
            
        }
    }

    @FXML
    private void PDF(ActionEvent event) throws FileNotFoundException, DocumentException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de création du PDF");
        alert.setHeaderText("Etes vous sur de vouloir créer un PDF qui contient la liste de vos objectifs?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
             ServicePdf sp= new ServicePdf();
        sp.liste_objectifsPred();
        }
        
     
    }

}
