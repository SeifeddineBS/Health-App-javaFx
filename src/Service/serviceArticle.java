/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import utils.Connexion;
import Entities.article;
import Interfaces.Iarticle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Interfaces.Iarticle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author akram
 */
public class serviceArticle implements Iarticle {
    private PDFParser parser;
    private PDFTextStripper pdfStripper;
    private PDDocument pdDoc;
    private COSDocument cosDoc;

    private String Text;
    private String filePath;
    private File file;
    

    Connection cnx;

    public serviceArticle() {
        cnx = Connexion.getInstance().getConnection();
    }

    @Override
    public void ajouter(article a) {
String datee = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        try {
            if (cnx != null) {
                

                String requete = "INSERT INTO `article` ( `id`,`titre`, `theme`, `nom_auteur`, `date`,`article`,`id_user`) VALUES ( '" + a.getId() + "','" + a.getTitre() + "', '" + a.getTheme() + "', '" + a.getNom_auteur() + "','" + datee + "','" + a.getArticle() + "','" + a.getId_user() + "') ";
                Statement st = Connexion.getInstance().getConnection().createStatement();
                assert st != null;
                st.executeUpdate(requete);
                System.out.println(" ajouté");
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ARTICLE");
                alert.setHeaderText(null);
                alert.setContentText("Article ajouté");
                alert.showAndWait();
            } else {
                System.out.println("non");
            }
        } catch (SQLException ex) {
            System.out.println("erreur ");
            System.out.println(ex);
        }
    }

   
             
     
    @Override
    public ObservableList<article> showArticle(String id_user) {
        Statement stm = null;
        ObservableList<article> articles = FXCollections.observableArrayList();
        

        try {

            //try {
            stm = Connexion.getInstance().getConnection().createStatement();

            String query = "SELECT * FROM article WHERE id_user= '"+id_user+"' ";
            ResultSet rs = stm.executeQuery(query);
            
            while (rs.next()) {
                article A= new article();
            
                A.setId(rs.getInt(1));
                A.setTitre(rs.getString(2));
                A.setTheme(rs.getString(3));
                A.setNom_auteur(rs.getString(4));
                A.setArticle(rs.getString(6));
                A.setDate(rs.getString(5));
                 A.setId_user(rs.getString(7));
                
                
                
                articles.add(A);
            }
            /* } catch (SQLException ex) {
            System.out.println(ex.getMessage());*/

            //}
        } catch (SQLException ex) {
            Logger.getLogger(serviceArticle.class.getName()).log(Level.SEVERE, null, ex);
        }

        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return articles;
    }
    @Override
   public void modifier(article c) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            Statement st = cnx.createStatement();
           
            String query = "UPDATE  article SET titre= '" + c.getTitre()+ "', theme = '" + c.getTheme()+ "', nom_auteur = '" + c.getNom_auteur()+ "', article = '" + c.getArticle()+ "'  WHERE id = '" + c.getId()+ "'";
            st.executeUpdate(query);
            System.out.println("modification avec succes");
        } catch (SQLException ex) {
            System.out.println("erreur ");
            System.out.println(ex);
        }
    }
    
    @Override
   public void supprimer(article c) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
       
        try {
            Statement st = cnx.createStatement();
           
            String query = "DELETE FROM article WHERE id = '" + c.getId()+ "' ";
            st.executeUpdate(query);
            System.out.println("modification avec succes");
        } catch (SQLException ex) {
            System.out.println("erreur ");
            System.out.println(ex);
        }
    }
    
    public article recup(int id){
        article A = new article();
        
          try {
            Statement st = cnx.createStatement();
            String query = "select * FROM article WHERE id='" + id + "'";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {   
             
              A.setId(rs.getInt(1));
               A.setTitre(rs.getString(2));
                A.setTheme(rs.getString(3));
                A.setNom_auteur(rs.getString(4));
                A.setArticle(rs.getString(6));
                A.setDate(rs.getString(5));
                
                
                
                System.out.println("!!!");
               
              
                /*ch.setId(("id"));
                ch.setTitre(rst.getString(2));
                ch.setType(rst.getString(3));
                ch.setDescription(rst.getString(4));
                ch.setImg(rst.getString(5));
                ch.setDate_debut(rst.getDate(6));
                ch.setDate_fin(rst.getDate(7));
                ch.setNb_participants(rst.getInt(8));
                ch.setEtat(rst.getString(9));
                ch.setNiveau(rst.getInt(10));*/
            }
            
        } catch (SQLException ex) {
            System.out.println("erreur get IdOBJ pour suivi");
            System.out.println(ex);
        }
        
            return A;
        
    }
    
    
    @Override
    public ObservableList<article> trier(String id_user) {
        Statement stm = null;
        ObservableList<article> articles = FXCollections.observableArrayList();
        

        try {

            //try {
            stm = Connexion.getInstance().getConnection().createStatement();

            String query = "SELECT * FROM article  WHERE id_user='" + id_user + "' ORDER BY titre ";
            ResultSet rs = stm.executeQuery(query);
                while (rs.next()) {
                article A= new article();
            
              
                A.setTitre(rs.getString(2));
                A.setTheme(rs.getString(3));
                A.setNom_auteur(rs.getString(4));
                A.setArticle(rs.getString(6));
                A.setDate(rs.getString(5));
               
                
                
                
                articles.add(A);}
            /* } catch (SQLException ex) {
            System.out.println(ex.getMessage());*/

            //}
        } catch (SQLException ex) {
            Logger.getLogger(serviceArticle.class.getName()).log(Level.SEVERE, null, ex);
        }

        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return articles;
    }
    @Override
    public ObservableList<article> trierc() {
        Statement stm = null;
        ObservableList<article> articles = FXCollections.observableArrayList();
        

        try {

            //try {
            stm = Connexion.getInstance().getConnection().createStatement();

            String query = "SELECT * FROM article WHERE approuver=0 ORDER BY titre ";
            ResultSet rs = stm.executeQuery(query);
                while (rs.next()) {
                article A= new article();
            
              
                A.setTitre(rs.getString(2));
                A.setTheme(rs.getString(3));
                A.setNom_auteur(rs.getString(4));
                A.setArticle(rs.getString(6));
                A.setDate(rs.getString(5));
                A.setApprouver(rs.getInt(8));
                
               
                
                
                
                articles.add(A);}
            /* } catch (SQLException ex) {
            System.out.println(ex.getMessage());*/

            //}
        } catch (SQLException ex) {
            Logger.getLogger(serviceArticle.class.getName()).log(Level.SEVERE, null, ex);
        }

        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return articles;
    }
      @Override
    public String recup_article(String art){
        article ch = new article();
        
          try {
            Statement st = cnx.createStatement();
            
            String query = "select * FROM `article` WHERE article='" + art + "'";
            ResultSet rst = st.executeQuery(query);
            if(rst.next()){
   ch.setArticle(rst.getString("article"));
}
            
                 
            
        } catch (SQLException ex) {
            System.out.println("erreur get IdOBJ pour suivi");
            System.out.println(ex);
        }
        
            return ch.getArticle();
            
    }
    @Override
    public ObservableList<article> recherche(String theme ) {
        Statement stm = null;
        ObservableList<article> articles = FXCollections.observableArrayList();
        

        try {

            //try {
            stm = Connexion.getInstance().getConnection().createStatement();

            String query = " select * from article  WHERE theme = '"+theme+"' "  ;
            ResultSet rs = stm.executeQuery(query);
            System.out.println(rs);
            while (rs.next()) {
                article A= new article();
            
              
               A.setTitre(rs.getString(2));
                A.setTheme(rs.getString(3));
                A.setNom_auteur(rs.getString(4));
                A.setArticle(rs.getString(6));
                A.setDate(rs.getString(5));
                
                
                articles.add(A);
            }
            /*} catch (SQLException ex) {
            System.out.println(ex.getMessage());*/

            //}
        } catch (SQLException ex) {
            Logger.getLogger(serviceArticle.class.getName()).log(Level.SEVERE, null, ex);
        }

        return articles;
    }
    
      /*public void pdfto() throws IOException{
      

        File f = new File("C:\\Users\\akram\\Desktop\\unix\\Cron&At.pdf");
PDDocument document = PDDocument.load(f);
   PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page)
            {
                        
            
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                String fileName = "C:\\Users\\akram\\Desktop\\unix\\"+ "image-" + page + ".png";
                ImageIOUtil.writeImage(bim, fileName, 300);
            
            document.close();

}
  
    }*/

    public String toText() throws IOException {
        this.pdfStripper = null;
        this.pdDoc = null;
        this.cosDoc = null;

        file = new File(filePath);
        parser = new PDFParser(new RandomAccessFile(file, "r")); // update for PDFBox V 2.0

        parser.parse();
        cosDoc = parser.getDocument();
        pdfStripper = new PDFTextStripper();
        pdDoc = new PDDocument(cosDoc);
        pdDoc.getNumberOfPages();
        pdfStripper.setStartPage(0);
        pdfStripper.setEndPage(pdDoc.getNumberOfPages());
        Text = pdfStripper.getText(pdDoc);
        return Text;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public PDDocument getPdDoc() {
        return pdDoc;
    }
 public ObservableList<article> showArticleAdmin() {
        Statement stm = null;
        ObservableList<article> articles = FXCollections.observableArrayList();
        

        try {

            //try {
            stm = Connexion.getInstance().getConnection().createStatement();

            String query = "SELECT * FROM article Where approuver=0 ";
            ResultSet rs = stm.executeQuery(query);
            
            while (rs.next()) {
                article A= new article();
            
                              A.setId(rs.getInt(1));

                A.setTitre(rs.getString(2));
                A.setTheme(rs.getString(3));
                A.setNom_auteur(rs.getString(4));
                A.setArticle(rs.getString(6));
                A.setDate(rs.getString(5));
                                A.setApprouver(rs.getInt(8));

                
                
                
                articles.add(A);
            }
            /* } catch (SQLException ex) {
            System.out.println(ex.getMessage());*/

            //}
        } catch (SQLException ex) {
            Logger.getLogger(serviceArticle.class.getName()).log(Level.SEVERE, null, ex);
        }

        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return articles;
    }
    @Override
 public void approuver(article c) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            Statement st = cnx.createStatement();
           
            String query = "UPDATE  article SET approuver = 1 WHERE id= '" + c.getId()+ "'";
            st.executeUpdate(query);
            System.out.println("modification avec succes");
        } catch (SQLException ex) {
            System.out.println("erreur ");
            System.out.println(ex);
        }
    }
    @Override
 public void supprimerad(article c) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       
       
        try {
            Statement st = cnx.createStatement();
            String query = "DELETE FROM article WHERE id = '" + c.getId()+ "' ";
            st.executeUpdate(query);
            System.out.println("suppression avec succes");
        } catch (SQLException ex) {
            System.out.println("erreur supprimer ");
            System.out.println(ex);
        }
        
    }

}

