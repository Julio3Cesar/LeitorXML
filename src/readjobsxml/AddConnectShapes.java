/*
 * Copyright 2001-2015 Aspose Pty Ltd. All Rights Reserved.
 *
 * This file is part of Aspose.Diagram. The source code in this file
 * is only intended as a supplement to the documentation, and is provided
 * "as is", without warranty of any kind, either expressed or implied.
 */



import readjobsxml.Utils;
import com.aspose.diagram.Char;
import com.aspose.diagram.ConnectionPointPlace;
import com.aspose.diagram.Cp;
import com.aspose.diagram.Diagram;
import com.aspose.diagram.SaveFileFormat;
import com.aspose.diagram.Shape;
import com.aspose.diagram.StyleValue;
import com.aspose.diagram.Txt;


public class AddConnectShapes {
	public static void main(String[] args) throws Exception {
		// ExStart:AddConnectShapes
		// The path to the documents directory.
		String dataDir = Utils.getSharedDataDir(AddConnectShapes.class) + "TechnicalArticles/";

		// Load masters from any existing diagram, stencil or template
		// and add in the new diagram
		String visioStencil = dataDir + "AddConnectShapes.vss";

		// Names of the masters present in the stencil
		String rectangleMaster = "Rectangle",
				connectorMaster = "Dynamic connector";

		int pageNumber = 0;
		double width = 1.5, height = 0.5, pinX = 4.25, pinY = 9.5;

		// Create a new diagram
		Diagram diagram = new Diagram(visioStencil);

		// Add a new rectangle shape
		long paiId = diagram.addShape(pinX, pinY, width, height, rectangleMaster, pageNumber);

		// Set the new shape's properties
		Shape shapePai = diagram.getPages().getPage(pageNumber).getShapes().getShape(paiId);
		shapePai.getText().getValue().add(new Cp(0));
		shapePai.getText().getValue().add(new Txt("BUOUPD001."));
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

		// Add a new star shape
	
		long filhoId = diagram.addShape(pinX, pinY, width, height, rectangleMaster, pageNumber);
		Shape shapeFilho = diagram.getPages().getPage(pageNumber).getShapes().getShape(filhoId);
		shapeFilho.getText().getValue().add(new Cp(0));
		shapeFilho.getText().getValue().add(new Txt("BUOWPD092."));
		shapeFilho.getChars().add(new Char());
		shapeFilho.getChars().get(0).setIX(0);
		shapeFilho.getChars().get(0).getFont().setValue(4);
		shapeFilho.getChars().get(0).getSize().setValue(0.15);
		shapeFilho.getChars().get(0).getStyle().setValue(StyleValue.BOLD);
		shapeFilho.setName("Rectangle1");
		shapeFilho.getXForm().getLocPinX().getUfe().setF("Width*0.5");
		shapeFilho.getXForm().getLocPinY().getUfe().setF("Height*1.5");
		shapeFilho.getLine().getLineColor().setValue("#9999FF");
		shapeFilho.getLine().getLineWeight().setValue(0.03);
		shapeFilho.getFill().getFillBkgnd().setValue("#9999FF");
		shapeFilho.getFill().getFillForegnd().setValue("#9999FF");
		shapeFilho.getFill().getFillPattern().setValue(31);
		
		
	
		long filhoId2 = diagram.addShape(pinX, pinY, width, height, rectangleMaster, pageNumber);
		Shape shapeFilho2 = diagram.getPages().getPage(pageNumber).getShapes().getShape(filhoId2);
		shapeFilho2.getText().getValue().add(new Cp(0));
		shapeFilho2.getText().getValue().add(new Txt("BUOWPD092."));
		shapeFilho2.getChars().add(new Char());
		shapeFilho2.getChars().get(0).setIX(0);
		shapeFilho2.getChars().get(0).getFont().setValue(4);
		shapeFilho2.getChars().get(0).getSize().setValue(0.15);
		shapeFilho2.getChars().get(0).getStyle().setValue(StyleValue.BOLD);
		shapeFilho2.setName("Rectangle1");
		shapeFilho2.getXForm().getLocPinX().getUfe().setF("Width*0.5");
		shapeFilho2.getXForm().getLocPinY().getUfe().setF("Height*1.5");
		shapeFilho2.getLine().getLineColor().setValue("#9999FF");
		shapeFilho2.getLine().getLineWeight().setValue(0.03);
		shapeFilho2.getFill().getFillBkgnd().setValue("#9999FF");
		shapeFilho2.getFill().getFillForegnd().setValue("#9999FF");
		shapeFilho2.getFill().getFillPattern().setValue(31);
		
		
		
		
		// Add master to dynamic connector from the stencil
		diagram.addMaster(visioStencil, connectorMaster);

		// Connect rectangle and star shapes
		Shape connector1 = new Shape();
		long connecter1Id = diagram.addShape(connector1, connectorMaster, 0);
		diagram.getPages().getPage(0).connectShapesViaConnector(paiId, ConnectionPointPlace.BOTTOM, filhoId,
				ConnectionPointPlace.TOP, connecter1Id);
		
		Shape connector2 = new Shape();
		long connecter2Id = diagram.addShape(connector2, connectorMaster, 0);
		diagram.getPages().getPage(0).connectShapesViaConnector(paiId, ConnectionPointPlace.BOTTOM, filhoId2,
				ConnectionPointPlace.TOP, connecter2Id);


		// Save the diagram
		diagram.save(dataDir + "AddConnectShapes_Out.pdf", SaveFileFormat.PDF);
		// ExEnd:AddConnectShapes
	}
}
