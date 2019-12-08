/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

/**
 *
 * @author EHef_
 */
public class GenerarPDF {
    private Font fuenteBold = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);
    private Font fuenteNormal = new Font(Font.FontFamily.COURIER, 3, Font.NORMAL);
    private Font fuenteItalic = new Font(Font.FontFamily.COURIER, 10, Font.ITALIC);
    
    public void generarPDF(String header, String info, String footer, String rutaImagen, String Salida){
        try{
            
            Document document = new Document(PageSize.A7, 36, 36, 10, 10);
            PdfWriter pw =  PdfWriter.getInstance(document, new FileOutputStream(Salida));
            document.open();
            Image imagen = Image.getInstance(rutaImagen);
            imagen.scaleAbsolute(100, 100);
            imagen.setAlignment(Element.ALIGN_CENTER);
            document.add(getInfo(header));
            document.add(getInfo(info));
            document.add(getFooter(footer));
            document.add(imagen);
            document.close();
            
        }catch(Exception e){
            
        }
    }
    
    private Paragraph getHeader(String texto){
        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        p.setAlignment(Element.ALIGN_CENTER);
        c.append(texto);
        c.setFont(fuenteBold);
        p.add(c);
        return p;
    }
    
    private Paragraph getInfo(String texto){
        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        p.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
        c.append(texto);
        c.setFont(fuenteNormal);
        p.add(c);
        return p;
    }
    
    private Paragraph getFooter(String texto){
        Paragraph p = new Paragraph();
        Chunk c = new Chunk();
        p.setAlignment(Element.ALIGN_RIGHT);
        c.append(texto);
        c.setFont(fuenteItalic);
        p.add(c);
        return p;
    }
}
