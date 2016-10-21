package Sistema;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

public abstract class Translate implements Interface{
    private String cadena;
    
    public void texto(String txt){
        this.cadena = txt;
    }
        
    public void Abrir(String archivo) {
        try {
            File objetofile = new File(archivo);
            Desktop.getDesktop().open(objetofile);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }
    
    @Override
    public void Generar(String txt){      
        Document Doc = new Document();
        try {
            PdfWriter.getInstance(Doc, new FileOutputStream(txt));
            Doc.open();
            Paragraph parrafoTitulo = new Paragraph("TRADUCTOR DE TEXTO A CÓDIGO BRAILE");
            parrafoTitulo.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
            Doc.add(parrafoTitulo);
            int x = 30;
            int y = 650;
            File miDir = new File (".");
            String Ruta = "";
            try{
                Ruta = miDir.getCanonicalPath() + "/src/Braille/";
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Error archivos de imagen no encontrados", "Información", JOptionPane.ERROR_MESSAGE);
            }
            String ect = "";
            Paragraph parrafoOriginal = new Paragraph( "\nTexto Original: " + cadena + "\nTraducción a sistema Braille-------------");
            parrafoOriginal.setAlignment(Element.ALIGN_LEFT);
            Doc.add(parrafoOriginal);
            boolean Verificador =  true, Aux =  true;
            for (int i = 0; i < cadena.length(); i++) {
                if (cadena.charAt(i) == ' ') {
                    y = y - 70;
                    x = 30;
                }
                
                Image imagen = Image.getInstance(Ruta + "..jpg");
                if (cadena.charAt(i) != ' ') {
                    if ((cadena.charAt(i) == '0') || (cadena.charAt(i) == '1') || (cadena.charAt(i) == '2')
                     || (cadena.charAt(i) == '3') || (cadena.charAt(i) == '4') || (cadena.charAt(i) == '5')
                     || (cadena.charAt(i) == '6') || (cadena.charAt(i) == '7') || (cadena.charAt(i) == '8')
                     || (cadena.charAt(i) == '9')) {
                        Verificador = true;
                     if (Aux == true) {
                            imagen = Image.getInstance(Ruta + "#.jpg"); //idetifica numeros
                            imagen.scaleAbsoluteWidth(imagen.getWidth() / 3);
                            imagen.scaleAbsoluteHeight(imagen.getHeight() / 3);
                            x += (imagen.getWidth() / 3)+5;
                            imagen.setAbsolutePosition(x, y);
                            Doc.add(imagen);
                            Aux = false;
                        } 
                    }
                    if ((cadena.charAt(i) != '0') && (cadena.charAt(i) != '1') && (cadena.charAt(i) != '2')
                     && (cadena.charAt(i) != '3') && (cadena.charAt(i) != '4') && (cadena.charAt(i) != '5')
                     && (cadena.charAt(i) != '6') && (cadena.charAt(i) != '7') && (cadena.charAt(i) != '8')
                     && (cadena.charAt(i) != '9')) {
                        Aux = true;
                        if(Verificador == true){
                            imagen = Image.getInstance(Ruta + "##.jpg");//idetifica alfabeto
                            imagen.scaleAbsoluteWidth(imagen.getWidth() / 3);
                            imagen.scaleAbsoluteHeight(imagen.getHeight() / 3);
                            x += (imagen.getWidth() / 3) + 5;
                            imagen.setAbsolutePosition(x, y);
                            Doc.add(imagen);
                            Verificador = false;
                        }
                    }
                    imagen = Image.getInstance(Ruta + cadena.charAt(i) + ".jpg");
                    imagen.scaleAbsoluteWidth(imagen.getWidth() / 3);
                    x += (imagen.getWidth() / 3)+5;
                    imagen.scaleAbsoluteHeight(imagen.getHeight() / 3);
                    imagen.setAbsolutePosition(x, y);
                    Doc.add(imagen);
                }
            }
            Doc.close();
        } catch (DocumentException | IOException ex) {
        }
    }
}
