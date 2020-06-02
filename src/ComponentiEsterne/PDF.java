package ComponentiEsterne;

import org.pdfclown.documents.Document;
import org.pdfclown.documents.Page;
import org.pdfclown.documents.contents.composition.BlockComposer;
import org.pdfclown.documents.contents.composition.PrimitiveComposer;
import org.pdfclown.documents.contents.composition.XAlignmentEnum;
import org.pdfclown.documents.contents.composition.YAlignmentEnum;
import org.pdfclown.documents.contents.fonts.StandardType1Font;
import org.pdfclown.files.File;
import org.pdfclown.files.SerializationModeEnum;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.StringTokenizer;

/**Classe che permette la creazione di un PDF*/
public class PDF {
    /**Istanza della classe PDF*/
    private static PDF instance = new PDF();


    /**Metodo getter della variabile instance*/
    public static PDF get() {
        return instance;
    }

    /**Costruttore di default*/
    private PDF() {
    }

    /**Metodo per la creazione di un PDF del testo passato come parametro*/
    public void stampaCC(String testo){
        File file = new File();
        Document document = file.getDocument();
        Page page = new Page(document);
        document.getPages().add(page);
        Dimension2D pageSize = page.getSize();
        PrimitiveComposer composer = new PrimitiveComposer(page);
        BlockComposer blockComposer = new BlockComposer(composer);
        composer.beginLocalState();
        Rectangle2D frame = new Rectangle2D.Double(50, 50, pageSize.getWidth()-100, pageSize.getHeight()-100);
        blockComposer.begin(frame, XAlignmentEnum.Left, YAlignmentEnum.Top);

        composer.setFont(new StandardType1Font(document, StandardType1Font.FamilyEnum.Helvetica, true, false), 16);
        blockComposer.showText("Cartella clinica");
        blockComposer.showBreak();
        blockComposer.showText("----------------------");
        blockComposer.showBreak();

        composer.setFont(new StandardType1Font(document, StandardType1Font.FamilyEnum.Helvetica, false, false), 8);
        StringTokenizer st = new StringTokenizer(testo,"\n");
        while(st.hasMoreTokens()){
            String currentLine = st.nextElement().toString();
            blockComposer.showText(currentLine);
            blockComposer.showBreak();
        }
        blockComposer.end();
        composer.end();
        composer.flush();
        try {
            file.save("/Users/marco/Desktop/Cartella clinica.pdf", SerializationModeEnum.Standard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

