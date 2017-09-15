/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readjobsxml.service;

import com.aspose.diagram.Char;
import com.aspose.diagram.ConnectionPointPlace;
import com.aspose.diagram.Cp;
import readjobsxml.Utils;
import com.aspose.diagram.Diagram;
import com.aspose.diagram.SaveFileFormat;
import com.aspose.diagram.Shape;
import com.aspose.diagram.StyleValue;
import com.aspose.diagram.Txt;
import java.util.List;
import readjobsxml.model.Job;

/**
 *
 * @author julio
 */
public class ExportVIsioService {

    final static String DATADIR = Utils.getSharedDataDir(ExportVIsioService.class) + "TechnicalArticles/";
    final static String VISIOSTENCIL = DATADIR + "AddConnectShapes.vss";
    final Diagram DIAGRAM;
    static double width = 1.5, height = 0.5, pinX = 4.25, pinY = 9.5;

    public ExportVIsioService() throws Exception {
        DIAGRAM = new Diagram(VISIOSTENCIL);
    }

    public Shape export(List<Job> jobs) throws Exception {
        Shape filho;
        Shape pai;

        for (Job job : jobs) {
            if (job.getDependentes().isEmpty()) {
                return criarShapeRetagulo(job);
                // este é o ultimo filho da arvore
                //retorna shape
            } else {
                filho = export(job.getDependentes());
                //cria shape
                pai = criarShapeRetagulo(job);
                //liga shape filho com pai
                criarConexão(pai, filho);
                //retonar pai
                if (!job.getDependencias().isEmpty() &&job.getJob() == null) {
                    return pai;
                } else {
                    DIAGRAM.save(DATADIR + "AddConnectShapes_Out.pdf", SaveFileFormat.PDF);
                }
            }
        }
        return null;
    }

    private Shape criarShapeRetagulo(Job job) throws Exception {
        int pageNumber = 0;
        long id = 0;
        pinX -= 3.5;
        pinY -= 3.5;
        String rectangleMaster = "Rectangle";
        id = DIAGRAM.addShape(pinX, pinY, width, height, rectangleMaster, pageNumber);

        Shape shapePai = DIAGRAM.getPages().getPage(pageNumber).getShapes().getShape(id);
        shapePai.getText().getValue().add(new Cp(0));
        shapePai.getText().getValue().add(new Txt(job.getNome()));
        shapePai.getChars().add(new Char());
        shapePai.getChars().get(0).setIX(0);
        shapePai.getChars().get(0).getFont().setValue(4);
        shapePai.getChars().get(0).getSize().setValue(0.15);
        shapePai.getChars().get(0).getStyle().setValue(StyleValue.BOLD);
        shapePai.setName("Rectangle1");
        shapePai.getXForm().getLocPinX().getUfe().setF("Width*0.5");
        shapePai.getXForm().getLocPinY().getUfe().setF("Height*0.5");
        shapePai.getLine().getLineColor().setValue("#9999FF");
        shapePai.getLine().getLineWeight().setValue(0.03);
        shapePai.getFill().getFillBkgnd().setValue("#9999FF");
        shapePai.getFill().getFillForegnd().setValue("#9999FF");
        shapePai.getFill().getFillPattern().setValue(31);

        return shapePai;
    }

    private void criarConexão(Shape pai, Shape filho) throws Exception {
        String linha = "Dynamic connector";
        DIAGRAM.addMaster(VISIOSTENCIL, linha);
        Shape connector = new Shape();
        long connecterId = DIAGRAM.addShape(connector, linha, 0);
        Shape shapeLinha = DIAGRAM.getPages().getPage(0).getShapes().getShape(connecterId);
        DIAGRAM.getPages().getPage(0).connectShapesViaConnector(pai, ConnectionPointPlace.BOTTOM, filho,
                ConnectionPointPlace.TOP, shapeLinha);

    }
}
