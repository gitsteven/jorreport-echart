/*      */ package jatools.component.chart.applet;
/*      */ 
/*      */ import jatools.component.chart.ChartSourceUtil;
/*      */ import jatools.component.chart.JavachartUtil;
/*      */ import jatools.component.chart.chart.Axis;
/*      */ import jatools.component.chart.chart.AxisInterface;
/*      */ import jatools.component.chart.chart.Background;
/*      */ import jatools.component.chart.chart.CandlestickChart;
/*      */ import jatools.component.chart.chart.ChartInterface;
/*      */ import jatools.component.chart.chart.DataRepresentation;
/*      */ import jatools.component.chart.chart.Dataset;
/*      */ import jatools.component.chart.chart.DateAxis;
/*      */ import jatools.component.chart.chart.Datum;
/*      */ import jatools.component.chart.chart.Gc;
/*      */ import jatools.component.chart.chart.Globals;
/*      */ import jatools.component.chart.chart.HiLoBarChart;
/*      */ import jatools.component.chart.chart.HiLoCloseChart;
/*      */ import jatools.component.chart.chart.HorizHiLoBarChart;
/*      */ import jatools.component.chart.chart.LegendInterface;
/*      */ import jatools.component.chart.chart.PieChart;
/*      */ import jatools.component.chart.chart.Plotarea;
/*      */ import jatools.component.chart.chart.ThresholdLine;
/*      */ import jatools.component.chart.chart._Chart;
/*      */ import jatools.component.chart.servlet.Bean;
/*      */ import jatools.core.view.FontUtil;
/*      */ import jatools.data.reader.DatasetReader;
/*      */ import jatools.engine.script.ReportContext;
/*      */ import jatools.engine.script.Script;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Image;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.FilteredImageSource;
/*      */ import java.awt.image.ImageFilter;
/*      */ import java.awt.image.ReplicateScaleFilter;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.net.URL;
/*      */ import java.text.Format;
/*      */ import java.text.NumberFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.imageio.ImageIO;
/*      */ 
/*      */ public class ParameterParser
/*      */ {
/*      */   protected ChartInterface chart;
/*      */   protected GetParam getter;
/*   62 */   public String delimiter = ",";
/*   63 */   protected double[] yArr = { 234.0D, 543.0D, 234.0D, 654.0D };
/*   64 */   boolean gotDatasets = false;
/*      */   protected URL myUrl;
/*   66 */   protected Format defaultNumberFormat = NumberFormat.getInstance();
/*   67 */   protected Format dataRepFormat = null;
/*   68 */   protected Format yAxisFormat = null;
/*   69 */   protected Format xAxisFormat = null;
/*   70 */   protected Iterator dataProvider = null;
/*      */ 
/*      */   public ParameterParser(ChartInterface c, GetParam g)
/*      */   {
/*   79 */     this.chart = c;
/*   80 */     this.getter = g;
/*      */   }
/*      */ 
/*      */   public void activateOutlineFills(Color color, boolean individualColors)
/*      */   {
/*   93 */     for (int i = 0; i < this.chart.getDatasets().length; i++)
/*   94 */       if (this.chart.getDatasets()[i] != null) {
/*   95 */         Dataset dataset = this.chart.getDatasets()[i];
/*      */ 
/*   97 */         if (!individualColors) {
/*   98 */           Gc gc = dataset.getGc();
/*      */ 
/*  100 */           if (gc.getOutlineFills()) {
/*  101 */             gc.setLineColor(color);
/*      */           }
/*      */         }
/*  104 */         else if (dataset.getGc().getOutlineFills()) {
/*  105 */           for (int j = 0; j < dataset.getData().size(); j++) {
/*  106 */             Gc gc = dataset.getDataElementAt(j).getGc();
/*  107 */             gc.setOutlineFills(true);
/*  108 */             gc.setLineColor(color);
/*      */           }
/*      */         }
/*      */       }
/*      */   }
/*      */ 
/*      */   public void activateOutlineFills(Dataset d, Color color, boolean individualColors)
/*      */   {
/*  122 */     if (!individualColors) {
/*  123 */       Gc gc = d.getGc();
/*      */ 
/*  125 */       if (gc.getOutlineFills()) {
/*  126 */         gc.setLineColor(color);
/*      */       }
/*      */     }
/*  129 */     else if (d.getGc().getOutlineFills()) {
/*  130 */       for (int j = 0; j < d.getData().size(); j++) {
/*  131 */         Gc gc = d.getDataElementAt(j).getGc();
/*  132 */         gc.setOutlineFills(true);
/*  133 */         gc.setLineColor(color);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean closeURL(InputStream myInputStream)
/*      */   {
/*      */     try
/*      */     {
/*  149 */       myInputStream.close();
/*      */     } catch (IOException e) {
/*  151 */       System.out.println("can't close URL");
/*      */ 
/*  153 */       return false;
/*      */     }
/*      */ 
/*  156 */     return true;
/*      */   }
/*      */ 
/*      */   public void getAxisOptions()
/*      */   {
/*  163 */     parseAxOptions("yAxis", this.chart.getYAxis());
/*  164 */     parseAxOptions("xAxis", this.chart.getXAxis());
/*      */   }
/*      */ 
/*      */   public void getAxisOptions(AxisInterface ax, String s)
/*      */   {
/*  188 */     if (s.indexOf("sciLogScaling") != -1) {
/*  189 */       if ((ax instanceof Axis)) {
/*  190 */         ((Axis)ax).setSciLogScaling(true);
/*      */       }
/*      */ 
/*  193 */       if (s.indexOf("sciLogMajorGridOff") != -1) {
/*  194 */         ((Axis)ax).setSciLogUseMajorGrids(false);
/*      */       }
/*      */ 
/*  197 */       if (s.indexOf("sciLogMinorGridOff") != -1) {
/*  198 */         ((Axis)ax).setSciLogUseMinorGrids(false);
/*      */       }
/*      */     }
/*      */ 
/*  202 */     if (s.indexOf("logScaling") != -1) {
/*  203 */       ax.setLogScaling(true);
/*      */     }
/*      */ 
/*  206 */     if (s.indexOf("autoScale") != -1) {
/*  207 */       ax.setAutoScale(true);
/*      */     }
/*      */ 
/*  210 */     if (s.indexOf("noAutoScale") != -1) {
/*  211 */       ax.setAutoScale(false);
/*      */     }
/*      */ 
/*  214 */     if (s.indexOf("lineOn") != -1) {
/*  215 */       ax.setLineVis(true);
/*      */     }
/*      */ 
/*  218 */     if (s.indexOf("lineOff") != -1) {
/*  219 */       ax.setLineVis(false);
/*      */     }
/*      */ 
/*  222 */     if (s.indexOf("labelsOn") != -1) {
/*  223 */       ax.setLabelVis(true);
/*      */     }
/*      */ 
/*  226 */     if (s.indexOf("labelsOff") != -1) {
/*  227 */       ax.setLabelVis(false);
/*      */     }
/*      */ 
/*  230 */     if (s.indexOf("gridOn") != -1) {
/*  231 */       ax.setGridVis(true);
/*      */     }
/*      */ 
/*  234 */     if (s.indexOf("gridOff") != -1) {
/*  235 */       ax.setGridVis(false);
/*      */     }
/*      */ 
/*  238 */     if (s.indexOf("tickOn") != -1) {
/*  239 */       ax.setMajTickVis(true);
/*      */     }
/*      */ 
/*  242 */     if (s.indexOf("tickOff") != -1) {
/*  243 */       ax.setMajTickVis(false);
/*      */     }
/*      */ 
/*  246 */     if (s.indexOf("minTickOn") != -1) {
/*  247 */       ax.setMinTickVis(true);
/*      */     }
/*      */ 
/*  250 */     if (s.indexOf("minTickOff") != -1) {
/*  251 */       ax.setMinTickVis(false);
/*      */     }
/*      */ 
/*  254 */     if (s.indexOf("rightAxis") != -1) {
/*  255 */       ax.setSide(3);
/*      */     }
/*      */ 
/*  258 */     if (s.indexOf("leftAxis") != -1) {
/*  259 */       ax.setSide(1);
/*      */     }
/*      */ 
/*  262 */     if (s.indexOf("topAxis") != -1) {
/*  263 */       ax.setSide(2);
/*      */     }
/*      */ 
/*  266 */     if (s.indexOf("bottomAxis") != -1) {
/*  267 */       ax.setSide(0);
/*      */     }
/*      */ 
/*  270 */     if (s.indexOf("rotateTitle") != -1) {
/*  271 */       ax.setTitleRotated(true);
/*      */     }
/*      */ 
/*  274 */     if (s.indexOf("currencyLabels") != -1) {
/*  275 */       ax.setLabelFormat(NumberFormat.getCurrencyInstance(this.chart.getGlobals().locale));
/*      */     }
/*      */ 
/*  278 */     if (s.indexOf("percentLabels") != -1)
/*  279 */       ax.setLabelFormat(NumberFormat.getPercentInstance(this.chart.getGlobals().locale));
/*      */   }
/*      */ 
/*      */   public boolean getDataset(int which)
/*      */   {
/*  301 */     double[] xArr = (double[])null;
/*  302 */     double[] yArr = (double[])null;
/*  303 */     double[] y2Arr = (double[])null;
/*  304 */     double[] y3Arr = (double[])null;
/*      */ 
/*  308 */     Dataset d = this.getter.getDataset(this.chart, which);
/*      */ 
/*  310 */     if (d != null) {
/*  311 */       this.chart.addDataset(d);
/*  312 */       getDatasetPropertiesFromParameters(which, d);
/*      */ 
/*  314 */       return true;
/*      */     }
/*      */ 
/*  318 */     if (this.getter.getDataProvider() != null) {
/*  319 */       if (this.dataProvider == null) {
/*  320 */         this.dataProvider = this.getter.getDataProvider().getDatasets();
/*      */       }
/*      */ 
/*  324 */       if (this.dataProvider.hasNext()) {
/*  325 */         Object o = this.dataProvider.next();
/*      */ 
/*  327 */         if ((o instanceof Dataset)) {
/*  328 */           this.chart.addDataset((Dataset)o);
/*  329 */           getDatasetPropertiesFromParameters(which, (Dataset)o);
/*      */ 
/*  331 */           return true;
/*      */         }
/*      */       }
/*      */ 
/*  335 */       return false;
/*      */     }
/*      */ 
/*  339 */     String str = getParameter("dataset" + which + "xValues");
/*      */ 
/*  341 */     if (str != null) {
/*  342 */       xArr = getVals(str);
/*      */     }
/*      */ 
/*  345 */     str = getParameter("dataset" + which + "dateValues");
/*      */ 
/*  347 */     if (str != null) {
/*  348 */       xArr = getDateVals(str);
/*      */     }
/*      */ 
/*  351 */     str = getParameter("dataset" + which + "yValues");
/*      */ 
/*  353 */     if (str != null) {
/*  354 */       yArr = getVals(str);
/*      */     }
/*      */ 
/*  357 */     str = getParameter("dataset" + which + "y2Values");
/*      */ 
/*  359 */     if (str != null) {
/*  360 */       y2Arr = getVals(str);
/*      */     }
/*      */ 
/*  363 */     str = getParameter("dataset" + which + "y3Values");
/*      */ 
/*  365 */     if (str != null) {
/*  366 */       y3Arr = getVals(str);
/*      */     }
/*      */ 
/*  369 */     str = getParameter("dataset" + which + "xyValues");
/*      */ 
/*  371 */     if (str != null) {
/*  372 */       StringTokenizer st = new StringTokenizer(str, this.delimiter);
/*  373 */       xArr = new double[st.countTokens() / 2];
/*  374 */       yArr = new double[xArr.length];
/*      */ 
/*  376 */       int i = 0;
/*      */ 
/*  378 */       while (st.hasMoreTokens()) {
/*      */         try {
/*  380 */           xArr[i] = Double.valueOf(st.nextToken().trim()).doubleValue();
/*  381 */           yArr[i] = Double.valueOf(st.nextToken().trim()).doubleValue();
/*      */         } catch (Exception e) {
/*  383 */           xArr[i] = (-1.0D / 0.0D);
/*  384 */           yArr[i] = (-1.0D / 0.0D);
/*      */         }
/*      */ 
/*  387 */         i++;
/*      */       }
/*      */     }
/*      */ 
/*  391 */     return getDatasetParameters(which, xArr, yArr, y2Arr, y3Arr);
/*      */   }
/*      */ 
/*      */   public boolean getDatasetParameters(int which, double[] xArr, double[] yArr, double[] y2Arr, double[] y3Arr)
/*      */   {
/*  412 */     String[] labels = (String[])null;
/*      */ 
/*  419 */     String str = getParameter("dataset" + which + "Name");
/*      */     String setName;
/*      */     //String setName;
/*  421 */     if (str != null)
/*  422 */       setName = str;
/*      */     else {
/*  424 */       setName = new String("dataset" + which);
/*      */     }
/*      */ 
/*  452 */     str = getParameter("dataset" + which + "URLLabels");
/*      */ 
/*  454 */     if (str != null)
/*      */     {
/*      */       InputStream myInputStream;
/*  455 */       if ((myInputStream = openURL(str)) != null) {
/*  456 */         str = getLineFromURL(myInputStream);
/*  457 */         labels = getLabels(str);
/*      */       }
/*      */ 
/*  460 */       closeURL(myInputStream);
/*      */     }
/*      */ 
/*  464 */     if ((y3Arr != null) && (y2Arr != null) && (yArr != null)) {
/*  465 */       this.chart.addDataset(setName, xArr, yArr, y2Arr, y3Arr);
/*      */     }
/*  469 */     else if ((y2Arr != null) && (yArr != null) && (labels != null)) {
/*  470 */       if (xArr == null)
/*  471 */         this.chart.addDataset(setName, yArr, y2Arr, labels);
/*      */       else
/*  473 */         this.chart.addDataset(setName, xArr, yArr, y2Arr, labels);
/*      */     }
/*  475 */     else if ((y2Arr != null) && (yArr != null) && (labels == null)) {
/*  476 */       if (xArr == null)
/*  477 */         this.chart.addDataset(setName, yArr, y2Arr);
/*      */       else {
/*  479 */         this.chart.addDataset(setName, xArr, yArr, y2Arr);
/*      */       }
/*      */ 
/*      */     }
/*  483 */     else if (yArr != null) {
/*  484 */       if (labels != null) {
/*  485 */         if (xArr == null)
/*  486 */           this.chart.addDataset(setName, yArr, labels);
/*      */         else
/*  488 */           this.chart.addDataset(setName, xArr, yArr, labels);
/*      */       }
/*  490 */       else if (xArr == null)
/*  491 */         this.chart.addDataset(setName, yArr);
/*      */       else {
/*  493 */         this.chart.addDataset(setName, xArr, yArr);
/*      */       }
/*      */     }
/*      */ 
/*  497 */     if (yArr == null)
/*      */     {
/*  499 */       return false;
/*      */     }
/*      */ 
/*  502 */     getDatasetPropertiesFromParameters(which, this.chart.getDatasets()[which]);
/*      */ 
/*  504 */     return true;
/*      */   }
/*      */ 
/*      */   public void getDatasetPropertiesFromParameters(int which, Dataset dataset)
/*      */   {
/*  517 */     Dataset d = dataset;
/*      */ 
/*  519 */     populateGc("dataset" + which, d.getGc());
/*      */ 
/*  524 */     String str = getParameter("dataset" + which + "Name");
/*      */ 
/*  526 */     if (str != null)
/*  527 */       dataset.setName(str);
/*  528 */     else if (dataset.getName() == null) {
/*  529 */       dataset.setName("dataset" + which);
/*      */     }
/*      */ 
/*  533 */     str = getParameter("dataset" + which + "Colors");
/*      */ 
/*  535 */     if (str != null) {
/*  536 */       String[] labels = getLabels(str);
/*      */ 
/*  538 */       for (int i = 0; i < labels.length; i++) {
/*      */         try {
/*  540 */           if ((labels[i] != null) && (!labels[i].equals("")) && (!labels[i].equals(" ")))
/*  541 */             dataset.getDataElementAt(i).getGc().setFillColor(getColor(labels[i]));
/*      */         }
/*      */         catch (ArrayIndexOutOfBoundsException len) {
/*  544 */           i = labels.length;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  549 */     str = getParameter("dataset" + which + "SecondaryColors");
/*      */ 
/*  551 */     if (str != null) {
/*  552 */       String[] labels = getLabels(str);
/*      */ 
/*  554 */       for (int i = 0; i < labels.length; i++) {
/*      */         try {
/*  556 */           if ((labels[i] != null) && (!labels[i].equals("")) && (!labels[i].equals(" ")))
/*  557 */             dataset.getDataElementAt(i).getGc()
/*  558 */               .setSecondaryFillColor(getColor(labels[i]));
/*      */         }
/*      */         catch (ArrayIndexOutOfBoundsException len) {
/*  561 */           i = labels.length;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  566 */     str = getParameter("dataset" + which + "Gradients");
/*      */ 
/*  568 */     if (str != null) {
/*  569 */       String[] labels = getLabels(str);
/*      */ 
/*  571 */       for (int i = 0; i < labels.length; i++) {
/*      */         try {
/*  573 */           if ((labels[i] != null) && (!labels[i].equals("")) && (!labels[i].equals(" ")))
/*  574 */             dataset.getDataElementAt(i).getGc().setGradient(Integer.parseInt(labels[i]));
/*      */         }
/*      */         catch (ArrayIndexOutOfBoundsException len) {
/*  577 */           i = labels.length;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  582 */     str = getParameter("dataset" + which + "Textures");
/*      */ 
/*  584 */     if (str != null) {
/*  585 */       String[] labels = getLabels(str);
/*      */ 
/*  587 */       for (int i = 0; i < labels.length; i++) {
/*      */         try {
/*  589 */           if ((labels[i] != null) && (!labels[i].equals("")) && (!labels[i].equals(" ")))
/*  590 */             dataset.getDataElementAt(i).getGc().setTexture(Integer.parseInt(labels[i]));
/*      */         }
/*      */         catch (ArrayIndexOutOfBoundsException len) {
/*  593 */           i = labels.length;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  600 */     str = getParameter("dataset" + which + "MarkerStyles");
/*      */ 
/*  602 */     if (str == null) {
/*  603 */       str = getParameter("datasetMarkerStyles");
/*      */     }
/*  605 */     if (str != null) {
/*  606 */       String[] labels = getLabels(str);
/*      */ 
/*  608 */       for (int i = 0; i < labels.length; i++) {
/*      */         try {
/*  610 */           if ((labels[i] != null) && (!labels[i].equals("")) && (!labels[i].equals(" ")))
/*  611 */             dataset.getDataElementAt(i).getGc()
/*  612 */               .setMarkerStyle(Integer.parseInt(labels[i]));
/*      */         }
/*      */         catch (ArrayIndexOutOfBoundsException len) {
/*  615 */           i = labels.length;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  620 */     str = getParameter("dataset" + which + "MarkerSizes");
/*  621 */     if (str == null) {
/*  622 */       str = getParameter("datasetMarkerSizes");
/*      */     }
/*      */ 
/*  625 */     if (str != null) {
/*  626 */       String[] labels = getLabels(str);
/*      */ 
/*  628 */       for (int i = 0; i < labels.length; i++) {
/*      */         try {
/*  630 */           if ((labels[i] != null) && (!labels[i].equals("")) && (!labels[i].equals(" ")))
/*  631 */             dataset.getDataElementAt(i).getGc()
/*  632 */               .setMarkerSize(Integer.parseInt(labels[i]));
/*      */         }
/*      */         catch (ArrayIndexOutOfBoundsException len) {
/*  635 */           i = labels.length;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  640 */     str = getParameter("dataset" + which + "Images");
/*      */ 
/*  642 */     if (str != null) {
/*  643 */       String[] labels = getLabels(str);
/*      */ 
/*  645 */       for (int i = 0; i < labels.length; i++) {
/*      */         try {
/*  647 */           if ((labels[i] != null) && (!labels[i].equals("")) && (!labels[i].equals(" "))) {
/*  648 */             byte[] bytes = JavachartUtil.decoderBase64(labels[i]);
/*  649 */             dataset.getDataElementAt(i).getGc().setImageBytes(bytes);
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (ArrayIndexOutOfBoundsException len)
/*      */         {
/*  658 */           i = labels.length;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  663 */     str = getParameter("dataset" + which + "OutLines");
/*      */ 
/*  665 */     if (str != null) {
/*  666 */       String[] labels = getLabels(str);
/*      */ 
/*  668 */       for (int i = 0; i < labels.length; i++) {
/*      */         try {
/*  670 */           if ((labels[i] != null) && (!labels[i].equals("")) && (!labels[i].equals(" ")))
/*  671 */             dataset.getDataElementAt(i).getGc()
/*  672 */               .setOutlineFills(Boolean.valueOf(labels[i]).booleanValue());
/*      */         }
/*      */         catch (ArrayIndexOutOfBoundsException len) {
/*  675 */           i = labels.length;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  680 */     str = getParameter("dataset" + which + "OutLineColors");
/*      */ 
/*  682 */     if (str != null) {
/*  683 */       String[] labels = getLabels(str);
/*      */ 
/*  685 */       for (int i = 0; i < labels.length; i++) {
/*      */         try {
/*  687 */           if ((labels[i] != null) && (!labels[i].equals("")) && (!labels[i].equals(" ")))
/*  688 */             dataset.getDataElementAt(i).getGc()
/*  689 */               .setLineColor(ChartUtil.getColor(labels[i]));
/*      */         }
/*      */         catch (ArrayIndexOutOfBoundsException len) {
/*  692 */           i = labels.length;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  697 */     str = getParameter("dataset" + which + "LabelFont");
/*      */ 
/*  699 */     if (str != null) {
/*  700 */       dataset.setLabelFont(getFont(str));
/*      */     }
/*      */ 
/*  703 */     str = getParameter("dataset" + which + "LabelColor");
/*      */ 
/*  705 */     if (str != null) {
/*  706 */       dataset.setLabelColor(getColor(str));
/*      */     }
/*      */ 
/*  709 */     str = getParameter("dataset" + which + "MarkerStyle");
/*      */ 
/*  711 */     if (str != null) {
/*  712 */       dataset.getGc().setMarkerStyle(Integer.parseInt(str));
/*      */     }
/*      */ 
/*  715 */     str = getParameter("dataset" + which + "MarkerSize");
/*      */ 
/*  717 */     if (str != null) {
/*  718 */       dataset.getGc().setMarkerSize(Integer.parseInt(str));
/*      */     }
/*      */ 
/*  721 */     str = getParameter("dataset" + which + "MarkerColor");
/*      */ 
/*  723 */     if (str != null) {
/*  724 */       dataset.getGc().setFillColor(getColor(str));
/*      */     }
/*      */ 
/*  727 */     str = getParameter("dataset" + which + "MarkerImage");
/*      */ 
/*  729 */     if (str != null)
/*  730 */       dataset.getGc().setImageBytes(JavachartUtil.decoderBase64(str));
/*      */   }
/*      */ 
/*      */   public void getDatasets()
/*      */   {
/*  746 */     int i = 0;
/*      */ 
/*  748 */     if (this.gotDatasets) {
/*  749 */       return;
/*      */     }
/*      */ 
/*  752 */     this.gotDatasets = true;
/*      */ 
/*  754 */     while (getDataset(i))
/*  755 */       i++;
/*      */   }
/*      */ 
/*      */   protected double[] getDateVals(String s)
/*      */   {
/*  761 */     return null;
/*      */   }
/*      */ 
/*      */   public Font getFont(String str)
/*      */   {
/*  781 */     int i = str.indexOf(this.delimiter, 0);
/*  782 */     String fontName = str.substring(0, i);
/*  783 */     int j = str.indexOf(this.delimiter, i + 1);
/*  784 */     int size = Integer.valueOf(str.substring(i + 1, j).trim()).intValue();
/*  785 */     int style = Integer.valueOf(str.substring(j + 1).trim()).intValue();
/*  786 */     Font f = new Font(fontName, style, size);
/*      */ 
/*  789 */     return FontUtil.getFont(f);
/*      */   }
/*      */ 
/*      */   public static Font getFont(String str, String delimiter)
/*      */   {
/*  814 */     if (delimiter == null) {
/*  815 */       delimiter = ",";
/*      */     }
/*      */ 
/*  818 */     int i = str.indexOf(delimiter, 0);
/*  819 */     String fontName = str.substring(0, i);
/*  820 */     int j = str.indexOf(delimiter, i + 1);
/*  821 */     int size = Integer.valueOf(str.substring(i + 1, j).trim()).intValue();
/*  822 */     int style = Integer.valueOf(str.substring(j + 1).trim()).intValue();
/*  823 */     Font f = new Font(fontName, style, size);
/*      */ 
/*  826 */     return f;
/*      */   }
/*      */ 
/*      */   public String[] getLabels(String s)
/*      */   {
/*  841 */     StringTokenizer st = new StringTokenizer(s, this.delimiter);
/*  842 */     String[] labels = new String[st.countTokens()];
/*  843 */     int i = 0;
/*      */ 
/*  845 */     while (st.hasMoreTokens()) {
/*      */       try {
/*  847 */         labels[i] = st.nextToken().trim();
/*      */       } catch (Exception e) {
/*  849 */         labels[i] = " ";
/*      */       }
/*      */ 
/*  852 */       i++;
/*      */     }
/*      */ 
/*  855 */     return labels;
/*      */   }
/*      */ 
/*      */   public synchronized String getLineFromURL(InputStream myInputStream)
/*      */   {
/*  867 */     int ch = -1;
/*  868 */     StringBuffer sb = new StringBuffer(256);
/*      */     while (true)
/*      */     {
/*      */       try {
/*  872 */         ch = myInputStream.read();
/*      */       } catch (IOException e) {
/*  874 */         System.out.println("bad i/o operation");
/*      */ 
/*  876 */         return null;
/*      */       }
/*      */ 
/*  879 */       if (ch == -1) {
/*  880 */         if (sb.length() == 0) {
/*  881 */           return null;
/*      */         }
/*  883 */         return sb.toString();
/*      */       }
/*  885 */       if ((ch == 10) || (ch == 13)) {
/*  886 */         if (sb.length() > 0)
/*  887 */           return sb.toString();
/*      */       }
/*      */       else
/*  890 */         sb.append((char)ch);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void getOptions()
/*      */   {
/*  899 */     String[] labelValues = (String[])null;
/*  900 */     String str = getParameter("labelValues");
/*      */ 
/*  902 */     if (str != null) {
/*  903 */       labelValues = str.split(",");
/*      */     }
/*      */ 
/*  906 */     DatasetReader graphReader = ((Bean)this.getter).getGraphReader();
/*  907 */     boolean rundata = false;
/*  908 */     Script jatoolsProvider = ((Bean)this.getter).getJatoolsDataProvider();
/*      */ 
/*  910 */     if ((graphReader != null) || (((Bean)this.getter).getDs() != null)) {
/*  911 */       String graphLabelField = ((Bean)this.getter).getGraphLabelField();
/*  912 */       ArrayList graphShowData = ((Bean)this.getter).getGraphShowData();
/*      */ 
/*  914 */       rundata = ChartSourceUtil.loadSource(jatoolsProvider, graphReader, ((Bean)this.getter).getDs(), graphLabelField, 
/*  915 */         graphShowData, ((Bean)this.getter).getPlotFrom(), this.chart, labelValues, ((Bean)this.getter).getIdField());
/*      */     }
/*      */ 
/*  918 */     if ((!rundata) && (!(jatoolsProvider instanceof ReportContext)) && 
/*  919 */       (this.chart.getDatasets()[0] == null)) {
/*  920 */       String[] dates = { 
/*  921 */         "2005-01-01", "2005-01-02", "2005-01-03", "2005-01-04", "2005-01-05", 
/*  922 */         "2005-01-06" };
/*      */ 
/*  924 */       double[] xValue = new double[6];
/*  925 */       double[][] data = { 
/*  926 */         { 350.0D, 378.0D, 360.0D, 400.0D, 380.0D, 340.0D }, 
/*  927 */         { 240.0D, 300.0D, 340.0D, 360.0D, 200.0D, 270.0D }, 
/*  928 */         { 280.0D, 350.0D, 350.0D, 380.0D, 300.0D, 320.0D }, 
/*  929 */         { 340.0D, 320.0D, 345.0D, 390.0D, 250.0D, 280.0D } };
/*      */ 
/*  932 */       String[] labels = { 
/*  933 */         "label1", "label2", "label3", "label4", "label5", "label6" };
/*      */ 
/*  936 */       if ((this.chart.getXAxis() instanceof DateAxis)) {
/*  937 */         SimpleDateFormat format = new SimpleDateFormat();
/*  938 */         format.applyPattern("yy-MM-dd");
/*      */ 
/*  940 */         for (int m = 0; m < 6; m++) {
/*      */           try {
/*  942 */             Date d = format.parse(dates[m]);
/*  943 */             xValue[m] = d.getTime();
/*      */           } catch (ParseException e) {
/*  945 */             e.printStackTrace();
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  951 */         if ((this.chart instanceof HiLoCloseChart))
/*  952 */           ((HiLoCloseChart)this.chart).addDataset("HiLoClose", xValue, data[0], data[1], 
/*  953 */             data[2]);
/*  954 */         else if ((this.chart instanceof CandlestickChart))
/*  955 */           ((CandlestickChart)this.chart).addDataset("Candlestick", xValue, data[0], 
/*  956 */             data[1], data[2], data[3]);
/*      */         else {
/*  958 */           for (int i = 0; i < 4; i++) {
/*  959 */             int l = data[i].length;
/*  960 */             this.chart.addDataset("dataset" + i, xValue, data[i], new String[l]);
/*      */           }
/*      */         }
/*      */ 
/*  964 */         if (this.chart.getXAxis() != null) {
/*  965 */           this.chart.getXAxis().addLabels(labels);
/*      */         }
/*      */ 
/*  968 */         this.chart.getXAxis().setNumMajTicks(6);
/*      */       }
/*      */       else
/*      */       {
/*  975 */         if ((this.chart instanceof HiLoBarChart)) {
/*  976 */           ((HiLoBarChart)this.chart).addDataset("HiLo", data[0], data[1], new String[4]);
/*  977 */         } else if ((this.chart instanceof HorizHiLoBarChart)) {
/*  978 */           ((HorizHiLoBarChart)this.chart).addDataset("HorizHiLo", data[0], data[1], 
/*  979 */             new String[4]);
/*      */         } else {
/*  981 */           double[] sampleData = { 123.0D, 432.0D, 345.0D, 432.0D };
/*  982 */           double[] sampleData1 = { 234.0D, 538.0D, 289.0D, 132.0D };
/*  983 */           double[] sampleData2 = { 208.0D, 312.0D, 470.0D, 702.0D };
/*  984 */           this.chart.addDataset("Banana Sales", sampleData);
/*  985 */           this.chart.addDataset("Apple Sales", sampleData1);
/*  986 */           this.chart.addDataset("Orange Sales", sampleData2);
/*      */         }
/*      */ 
/*  989 */         if (this.chart.getXAxis() != null) {
/*  990 */           this.chart.getXAxis().addLabels(labels);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  996 */     if ((this.chart.getYAxis() != null) && (labelValues != null)) {
/*  997 */       this.chart.getYAxis().addLabels(labelValues);
/*      */     }
/*      */ 
/* 1000 */     str = getParameter("delimiter");
/*      */ 
/* 1002 */     if (str != null) {
/* 1003 */       this.delimiter = new String(str);
/*      */     }
/*      */ 
/* 1085 */     str = getParameter("isAntialiasing");
/*      */ 
/* 1087 */     if (str != null) {
/* 1088 */       ((_Chart)this.chart).setAntialiasing(str.equals("true"));
/*      */     }
/*      */     try
/*      */     {
/* 1092 */       str = getParameter("legendOn");
/*      */ 
/* 1094 */       if (str != null) {
/* 1095 */         this.chart.setLegendVisible(true);
/*      */       }
/*      */ 
/* 1103 */       populateGc("legend", this.chart.getLegend().getBackgroundGc());
/*      */ 
/* 1105 */       str = getParameter("legendOpaque");
/*      */ 
/* 1107 */       if (str != null) {
/* 1108 */         this.chart.getLegend().setBackgroundVisible(false);
/*      */       }
/*      */ 
/* 1111 */       str = getParameter("legendVertical");
/*      */ 
/* 1113 */       if (str != null) {
/* 1114 */         this.chart.getLegend().setVerticalLayout(true);
/*      */       }
/*      */ 
/* 1121 */       str = getParameter("legendLabelColor");
/*      */ 
/* 1123 */       if (str != null) {
/* 1124 */         this.chart.getLegend().setLabelColor(getColor(str));
/*      */       }
/*      */ 
/* 1127 */       str = getParameter("legendLabelFont");
/*      */ 
/* 1129 */       if (str != null) {
/* 1130 */         this.chart.getLegend().setLabelFont(getFont(str));
/*      */       }
/*      */ 
/* 1133 */       str = getParameter("legendllX");
/*      */ 
/* 1135 */       if (str != null) {
/* 1136 */         this.chart.getLegend().setLlX(Double.valueOf(str).doubleValue());
/*      */       }
/*      */ 
/* 1139 */       str = getParameter("legendllY");
/*      */ 
/* 1141 */       if (str != null) {
/* 1142 */         this.chart.getLegend().setLlY(Double.valueOf(str).doubleValue());
/*      */       }
/*      */ 
/* 1145 */       str = getParameter("iconWidth");
/*      */ 
/* 1147 */       if (str != null) {
/* 1148 */         this.chart.getLegend().setIconWidth(Double.valueOf(str.trim()).doubleValue());
/*      */       }
/*      */ 
/* 1151 */       str = getParameter("iconHeight");
/*      */ 
/* 1153 */       if (str != null) {
/* 1154 */         this.chart.getLegend().setIconHeight(Double.valueOf(str.trim()).doubleValue());
/*      */       }
/*      */ 
/* 1157 */       str = getParameter("iconGapt");
/*      */ 
/* 1159 */       if (str != null) {
/* 1160 */         this.chart.getLegend().setIconGap(Double.valueOf(str.trim()).doubleValue());
/*      */       }
/*      */ 
/* 1163 */       str = getParameter("invertLegend");
/*      */ 
/* 1165 */       if (str != null) {
/* 1166 */         this.chart.getLegend().setInvertLegend(true);
/*      */       }
/*      */     }
/*      */     catch (NullPointerException localNullPointerException)
/*      */     {
/*      */     }
/* 1172 */     str = getParameter("plotAreaTop");
/*      */ 
/* 1174 */     if (str != null) {
/* 1175 */       this.chart.getPlotarea().setUrY(Double.valueOf(str).doubleValue());
/*      */     }
/*      */ 
/* 1178 */     str = getParameter("plotAreaBottom");
/*      */ 
/* 1180 */     if (str != null) {
/* 1181 */       this.chart.getPlotarea().setLlY(Double.valueOf(str).doubleValue());
/*      */     }
/*      */ 
/* 1184 */     str = getParameter("plotAreaLeft");
/*      */ 
/* 1186 */     if (str != null) {
/* 1187 */       this.chart.getPlotarea().setLlX(Double.valueOf(str).doubleValue());
/*      */     }
/*      */ 
/* 1190 */     str = getParameter("plotAreaRight");
/*      */ 
/* 1192 */     if (str != null) {
/* 1193 */       this.chart.getPlotarea().setUrX(Double.valueOf(str).doubleValue());
/*      */     }
/*      */ 
/* 1196 */     populateGc("plotarea", this.chart.getPlotarea().getGc());
/* 1197 */     populateGc("background", this.chart.getBackground().getGc());
/*      */ 
/* 1199 */     str = getParameter("titleColor");
/*      */ 
/* 1201 */     if (str != null) {
/* 1202 */       this.chart.getBackground().setTitleColor(getColor(str));
/*      */     }
/*      */ 
/* 1205 */     str = getParameter("titleFont");
/*      */ 
/* 1207 */     if (str != null) {
/* 1208 */       this.chart.getBackground().setTitleFont(getFont(str));
/*      */     }
/*      */ 
/* 1211 */     str = getParameter("titleString");
/*      */ 
/* 1213 */     if (str != null) {
/* 1214 */       this.chart.getBackground().setTitleString(str);
/*      */     }
/*      */ 
/* 1217 */     str = getParameter("titleX");
/*      */ 
/* 1219 */     if (str != null) {
/* 1220 */       Double b = new Double(0.0D);
/* 1221 */       b = Double.valueOf(str);
/*      */ 
/* 1223 */       if (Double.valueOf(str) != null) {
/* 1224 */         this.chart.getBackground().setTitleX(Double.valueOf(str));
/*      */       }
/*      */     }
/*      */ 
/* 1228 */     str = getParameter("titleY");
/*      */ 
/* 1230 */     if ((str != null) && 
/* 1231 */       (Double.valueOf(str) != null)) {
/* 1232 */       this.chart.getBackground().setTitleY(Double.valueOf(str));
/*      */     }
/*      */ 
/* 1236 */     str = getParameter("subTitleColor");
/*      */ 
/* 1238 */     if (str != null) {
/* 1239 */       this.chart.getBackground().setSubTitleColor(getColor(str));
/*      */     }
/*      */ 
/* 1242 */     str = getParameter("subTitleFont");
/*      */ 
/* 1244 */     if (str != null) {
/* 1245 */       this.chart.getBackground().setSubTitleFont(getFont(str));
/*      */     }
/*      */ 
/* 1248 */     str = getParameter("subTitleString");
/*      */ 
/* 1250 */     if (str != null) {
/* 1251 */       this.chart.getBackground().setSubTitleString(str);
/*      */     }
/*      */ 
/* 1254 */     str = getParameter("subTitleX");
/*      */ 
/* 1256 */     if (str != null) {
/* 1257 */       this.chart.getBackground().setSubTitleX(Double.valueOf(str));
/*      */     }
/*      */ 
/* 1260 */     str = getParameter("subTitleY");
/*      */ 
/* 1262 */     if (str != null) {
/* 1263 */       this.chart.getBackground().setSubTitleY(Double.valueOf(str));
/*      */     }
/*      */ 
/* 1266 */     str = getParameter("threeD");
/*      */ 
/* 1268 */     if ((str != null) && (!str.equalsIgnoreCase("false"))) {
/* 1269 */       this.chart.setThreeD(true);
/*      */     }
/*      */ 
/* 1276 */     str = getParameter("xDepth");
/*      */ 
/* 1278 */     if (str != null) {
/* 1279 */       this.chart.setXOffset(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 1282 */     str = getParameter("yDepth");
/*      */ 
/* 1284 */     if (str != null) {
/* 1285 */       this.chart.setYOffset(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 1289 */     str = getParameter("XDepth");
/*      */ 
/* 1291 */     if (str != null) {
/* 1292 */       this.chart.setXOffset(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 1295 */     str = getParameter("YDepth");
/*      */ 
/* 1297 */     if (str != null) {
/* 1298 */       this.chart.setYOffset(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 1301 */     str = getParameter("labelsOn");
/*      */ 
/* 1303 */     if (str != null) {
/* 1304 */       if (str.equalsIgnoreCase("true"))
/* 1305 */         this.chart.getDataRepresentation().setLabelsOn(true);
/*      */       else {
/* 1307 */         this.chart.getDataRepresentation().setLabelsOn(false);
/*      */       }
/*      */     }
/*      */ 
/* 1311 */     str = getParameter("labelAngle");
/*      */ 
/* 1313 */     if (str != null) {
/* 1314 */       this.chart.getDataRepresentation().setLabelAngle(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 1338 */     str = getParameter("dataPattern");
/*      */ 
/* 1340 */     if (str != null) {
/* 1341 */       this.chart.getDataRepresentation().setPattern(str);
/*      */     }
/*      */ 
/* 1344 */     str = getParameter("percentLabels");
/*      */ 
/* 1346 */     if ((str != null) && 
/* 1347 */       (str.equalsIgnoreCase("true"))) {
/* 1348 */       this.chart.getDataRepresentation()
/* 1349 */         .setFormat(NumberFormat.getPercentInstance(this.chart.getGlobals().locale));
/*      */     }
/*      */ 
/* 1353 */     str = getParameter("currencyLabels");
/*      */ 
/* 1355 */     if ((str != null) && 
/* 1356 */       (str.equalsIgnoreCase("true"))) {
/* 1357 */       this.chart.getDataRepresentation()
/* 1358 */         .setFormat(NumberFormat.getCurrencyInstance(this.chart.getGlobals().locale));
/*      */     }
/*      */ 
/* 1362 */     getAxisOptions();
/*      */ 
/* 1364 */     if ((this.chart instanceof PieChart)) {
/* 1365 */       populateDatumGc(this.chart.getDatasets()[0]);
/*      */     }
/*      */     else
/*      */     {
/* 1380 */       for (int i = 0; i < this.chart.getDatasets().length; i++) {
/* 1381 */         Dataset dataset = this.chart.getDatasets()[i];
/*      */ 
/* 1383 */         if (dataset != null) {
/* 1384 */           str = getParameter("dataset" + i + "Labels");
/*      */ 
/* 1386 */           if (str != null) {
/* 1387 */             String[] s = getLabels(str);
/*      */ 
/* 1389 */             for (int m = 0; m < dataset.getData().size(); m++) {
/* 1390 */               dataset.getDataElementAt(m).setLabel(null);
/*      */             }
/*      */ 
/* 1393 */             dataset.setLabels(s);
/* 1394 */             dataset.replaceLabels(s);
/*      */           }
/*      */ 
/* 1397 */           str = getParameter("dataset" + i + "MarkerStyle");
/*      */ 
/* 1399 */           if (str == null) {
/* 1400 */             str = getParameter("datasetMarkerStyles");
/*      */           }
/* 1402 */           if (str != null) {
/* 1403 */             dataset.getGc().setMarkerStyle(Integer.parseInt(str));
/*      */           }
/*      */ 
/* 1406 */           str = getParameter("dataset" + i + "MarkerSize");
/* 1407 */           if (str == null) {
/* 1408 */             str = getParameter("datasetMarkerSize");
/*      */           }
/* 1410 */           if (str != null) {
/* 1411 */             dataset.getGc().setMarkerSize(Integer.parseInt(str));
/*      */           }
/*      */ 
/* 1414 */           str = getParameter("dataset" + i + "MarkerColor");
/*      */ 
/* 1416 */           if (str != null) {
/* 1417 */             dataset.getGc().setFillColor(ChartUtil.getColor(str));
/*      */           }
/*      */ 
/* 1420 */           str = getParameter("dataset" + i + "MarkerImage");
/*      */ 
/* 1422 */           if (str != null)
/*      */           {
/* 1427 */             dataset.getGc().setImageBytes(JavachartUtil.decoderBase64(str));
/*      */           }
/*      */ 
/* 1430 */           populateGc("dataset" + i, dataset.getGc());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1455 */     str = getParameter("outlineBackgroundColor");
/*      */ 
/* 1457 */     if (str != null) {
/* 1458 */       Color c = getColor(str);
/* 1459 */       Gc gc = this.chart.getBackground().getGc();
/* 1460 */       gc.setOutlineFills(true);
/* 1461 */       gc.setLineColor(c);
/*      */     }
/*      */ 
/* 1464 */     str = getParameter("outlinePlotareaColor");
/*      */ 
/* 1466 */     if (str != null) {
/* 1467 */       Color c = getColor(str);
/* 1468 */       Gc gc = this.chart.getPlotarea().getGc();
/* 1469 */       gc.setOutlineFills(true);
/* 1470 */       gc.setLineColor(c);
/*      */     }
/*      */ 
/* 1473 */     str = getParameter("outlineLegendColor");
/*      */ 
/* 1475 */     if (str != null) {
/* 1476 */       Color c = getColor(str);
/*      */       try
/*      */       {
/* 1479 */         Gc gc = this.chart.getLegend().getBackgroundGc();
/* 1480 */         gc.setOutlineFills(true);
/* 1481 */         gc.setLineColor(c);
/*      */       }
/*      */       catch (Exception localException) {
/*      */       }
/*      */     }
/* 1486 */     str = getParameter("outlinePlotarea");
/*      */ 
/* 1488 */     if ((str != null) && 
/* 1489 */       (str.equalsIgnoreCase("false"))) {
/* 1490 */       this.chart.getPlotarea().getGc().setOutlineFills(false);
/*      */     }
/*      */ 
/* 1494 */     str = getParameter("outlineBackground");
/*      */ 
/* 1496 */     if ((str != null) && 
/* 1497 */       (str.equalsIgnoreCase("false"))) {
/* 1498 */       this.chart.getBackground().getGc().setOutlineFills(false);
/*      */     }
/*      */ 
/* 1502 */     str = getParameter("outlineLegend");
/*      */ 
/* 1504 */     if ((str != null) && 
/* 1505 */       (str.equalsIgnoreCase("false"))) {
/* 1506 */       this.chart.getLegend().getBackgroundGc().setOutlineFills(false);
/*      */     }
/*      */ 
/* 1510 */     str = getParameter("showVersion");
/*      */ 
/* 1512 */     if ((str != null) && 
/* 1513 */       (str.equalsIgnoreCase("true")))
/* 1514 */       this.chart.getBackground().setTitleString("chart 4.2.2");
/*      */   }
/*      */ 
/*      */   private void populateDatumGc(Dataset dataset)
/*      */   {
/* 1520 */     String[] colors = (String[])null;
/* 1521 */     String[] secondColors = (String[])null;
/* 1522 */     String[] textures = (String[])null;
/* 1523 */     String[] gradients = (String[])null;
/* 1524 */     String[] lineWidths = (String[])null;
/* 1525 */     String[] lineStyles = (String[])null;
/* 1526 */     String[] images = (String[])null;
/* 1527 */     String[] outlines = (String[])null;
/* 1528 */     String[] outlineColors = (String[])null;
/* 1529 */     String[] labels = (String[])null;
/*      */ 
/* 1531 */     String str = getParameter("dataset0Colors");
/*      */ 
/* 1533 */     if (str != null) {
/* 1534 */       colors = getLabels(str);
/*      */     }
/*      */ 
/* 1537 */     str = getParameter("dataset0SecondaryColors");
/*      */ 
/* 1539 */     if (str != null) {
/* 1540 */       secondColors = getLabels(str);
/*      */     }
/*      */ 
/* 1543 */     str = getParameter("dataset0Textures");
/*      */ 
/* 1545 */     if (str != null) {
/* 1546 */       textures = getLabels(str);
/*      */     }
/*      */ 
/* 1549 */     str = getParameter("dataset0Gradients");
/*      */ 
/* 1551 */     if (str != null) {
/* 1552 */       gradients = getLabels(str);
/*      */     }
/*      */ 
/* 1555 */     str = getParameter("dataset0LineWidth");
/*      */ 
/* 1557 */     if (str != null)
/*      */     {
/* 1559 */       dataset.getGc().setLineWidth(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 1562 */     str = getParameter("dataset0LineStyle");
/*      */ 
/* 1564 */     if (str != null)
/*      */     {
/* 1566 */       dataset.getGc().setLineStyle(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 1569 */     str = getParameter("dataset0Images");
/*      */ 
/* 1571 */     if (str != null) {
/* 1572 */       images = getLabels(str);
/*      */     }
/*      */ 
/* 1575 */     str = getParameter("dataset0OutLines");
/*      */ 
/* 1577 */     if (str != null) {
/* 1578 */       outlines = getLabels(str);
/*      */     }
/*      */ 
/* 1582 */     str = getParameter("dataset0OutLineColors");
/*      */ 
/* 1584 */     if (str != null) {
/* 1585 */       outlineColors = getLabels(str);
/*      */     }
/*      */ 
/* 1588 */     if (colors == null) {
/* 1589 */       return;
/*      */     }
/*      */ 
/* 1592 */     for (int i = 0; i < dataset.getData().size(); i++) {
/* 1593 */       Datum datum = dataset.getDataElementAt(i);
/*      */ 
/* 1595 */       if (datum != null) {
/* 1596 */         datum.getGc().setFillStyle(-1);
/*      */ 
/* 1598 */         if (i > colors.length - 1)
/*      */         {
/*      */           break;
/*      */         }
/* 1602 */         if (!colors[i].equals("")) {
/* 1603 */           datum.getGc().setFillColor(getColor(colors[i]));
/* 1604 */           datum.getGc().setLineColor(getColor(colors[i]));
/*      */         }
/*      */ 
/* 1607 */         if (!secondColors[i].equals("")) {
/* 1608 */           datum.getGc().setSecondaryFillColor(getColor(secondColors[i]));
/*      */         }
/*      */ 
/* 1611 */         if (!textures[i].equals("")) {
/* 1612 */           datum.getGc().setTexture(Integer.parseInt(textures[i]));
/* 1613 */           datum.getGc().setFillStyle(1);
/*      */         }
/*      */ 
/* 1616 */         if (!gradients[i].equals("")) {
/* 1617 */           datum.getGc().setGradient(Integer.parseInt(gradients[i]));
/* 1618 */           datum.getGc().setFillStyle(0);
/*      */         }
/*      */ 
/* 1627 */         if (!images[i].equals("")) {
/* 1628 */           datum.getGc().setFillStyle(1);
/*      */ 
/* 1634 */           datum.getGc().setImageBytes(JavachartUtil.decoderBase64(str));
/*      */         }
/*      */ 
/* 1637 */         if (!outlines[i].equals("")) {
/* 1638 */           datum.getGc().setOutlineFills(Boolean.valueOf(outlines[i]).booleanValue());
/*      */         }
/*      */ 
/* 1641 */         if (!outlineColors[i].equals(""))
/* 1642 */           datum.getGc().setLineColor(getColor(outlineColors[i]));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setupDatasets()
/*      */   {
/* 1652 */     if ((this.chart instanceof CandlestickChart)) {
/* 1653 */       String hi = getParameter("hiValues");
/* 1654 */       String lo = getParameter("loValues");
/* 1655 */       String open = getParameter("openValues");
/* 1656 */       String close = getParameter("closeValues");
/*      */ 
/* 1658 */       if ((hi != null) && (lo != null) && (open != null) && (close != null) && 
/* 1659 */         (getParameter("dataset0xValues") != null)) {
/* 1660 */         double[] hiValues = getVals(hi);
/* 1661 */         double[] loValues = getVals(lo);
/* 1662 */         double[] openValues = getVals(open);
/* 1663 */         double[] closeValues = getVals(close);
/* 1664 */         double[] xValues = getVals(getParameter("dataset0xValues"));
/*      */ 
/* 1666 */         ((CandlestickChart)this.chart).addDataset("Candlestick", xValues, hiValues, 
/* 1667 */           loValues, openValues, closeValues);
/*      */       }
/*      */     }
/*      */     else {
/* 1671 */       getDatasets();
/*      */     }
/*      */   }
/*      */ 
/*      */   private Font parseFont(String props)
/*      */   {
/* 1684 */     String[] propArr = props.split(",");
/*      */ 
/* 1686 */     return new Font(propArr[0], Integer.parseInt(propArr[2]), Integer.parseInt(propArr[1]));
/*      */   }
/*      */ 
/*      */   public String getParameter(String s)
/*      */   {
/* 1697 */     return this.getter.getParameter(s);
/*      */   }
/*      */ 
/*      */   protected boolean getURLDataBlock(String urlStr)
/*      */   {
/* 1709 */     double[] xArr = (double[])null;
/* 1710 */     double[] yArr = (double[])null;
/*      */ 
/* 1712 */     int i = 0;
/*      */ 
/* 1715 */     if (urlStr != null)
/*      */     {
/*      */       InputStream myInputStream;
/* 1716 */       if ((myInputStream = openURL(urlStr)) == null)
/* 1717 */         return false;
/*      */     }
/*      */     else {
/* 1720 */       return false;
/*      */     }
/*      */     InputStream myInputStream;
/* 1723 */     this.gotDatasets = true;
/*      */     String str;
/* 1725 */     while ((str = getLineFromURL(myInputStream)) != null)
/*      */     {
/*      */       //String str;
/* 1726 */       yArr = getVals(str);
/* 1727 */       getDatasetParameters(i, xArr, yArr, null, null);
/* 1728 */       i++;
/*      */     }
/*      */ 
/* 1731 */     closeURL(myInputStream);
/*      */ 
/* 1733 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean getURLDataset(int which)
/*      */   {
/* 1745 */     double[] xArr = (double[])null;
/* 1746 */     double[] yArr = (double[])null;
/* 1747 */     double[] y2Arr = (double[])null;
/*      */ 
/* 1754 */     String str = getParameter("dataset" + which + "xURL");
/*      */     InputStream myInputStream;
/* 1756 */     if ((str != null) && 
/* 1757 */       ((myInputStream = openURL(str)) != null)) {
/* 1758 */       String urlStr = getLineFromURL(myInputStream);
/* 1759 */       xArr = getVals(urlStr);
/* 1760 */       closeURL(myInputStream);
/*      */     }
/*      */ 
/* 1764 */     str = getParameter("dataset" + which + "yURL");
/*      */ 
/* 1766 */     if (str != null) {
/* 1767 */       if (str.equalsIgnoreCase("fake"))
/*      */       {
/* 1769 */         yArr = new double[5];
/*      */ 
/* 1771 */         for (int j = 0; j < 5; j++)
/* 1772 */           yArr[j] = (10.0D * Math.random());
/*      */       }
/*      */       else
/*      */       {
/*      */         //InputStream myInputStream;
/* 1773 */         if ((myInputStream = openURL(str)) != null) {
/* 1774 */           String urlStr = getLineFromURL(myInputStream);
/* 1775 */           yArr = getVals(urlStr);
/* 1776 */           closeURL(myInputStream);
/*      */         }
/*      */       }
/*      */     }
/* 1780 */     str = getParameter("dataset" + which + "y2URL");
/*      */ 
/* 1782 */     if (str != null) {
/* 1783 */       if (str.equalsIgnoreCase("fake"))
/*      */       {
/* 1785 */         yArr = new double[5];
/*      */ 
/* 1787 */         for (int j = 0; j < 5; j++)
/* 1788 */           y2Arr[j] = (10.0D * Math.random());
/*      */       }
/*      */       else
/*      */       {
/*      */         //InputStream myInputStream;
/* 1789 */         if ((myInputStream = openURL(str)) != null) {
/* 1790 */           String urlStr = getLineFromURL(myInputStream);
/* 1791 */           y2Arr = getVals(urlStr);
/* 1792 */           closeURL(myInputStream);
/*      */         }
/*      */       }
/*      */     }
/* 1796 */     str = getParameter("dataset" + which + "xyURL");
/*      */ 
/* 1798 */     if (str != null) {
/* 1799 */       System.out.println("xyVals not supported yet");
/*      */     }
/*      */ 
/* 1802 */     return getDatasetParameters(which, xArr, yArr, y2Arr, null);
/*      */   }
/*      */ 
/*      */   protected void getURLDatasets()
/*      */   {
/* 1809 */     int i = 0;
/*      */ 
/* 1811 */     if (this.gotDatasets) {
/* 1812 */       return;
/*      */     }
/*      */ 
/* 1815 */     this.gotDatasets = true;
/*      */ 
/* 1817 */     while (getURLDataset(i))
/* 1818 */       i++;
/*      */   }
/*      */ 
/*      */   protected boolean getURLXYDataColumns(String urlStr)
/*      */   {
/* 1830 */     double[] xArr = (double[])null;
/* 1831 */     double[] yArr = (double[])null;
/*      */ 
/* 1840 */     if (urlStr != null)
/*      */     {
/*      */       InputStream myInputStream;
/* 1841 */       if ((myInputStream = openURL(urlStr)) == null)
/* 1842 */         return false;
/*      */     }
/*      */     else {
/* 1845 */       return false;
/*      */     }
/*      */     InputStream myInputStream;
/* 1848 */     this.gotDatasets = true;
/* 1849 */     String myStr = getLineFromURL(myInputStream);
/* 1850 */     int nrows = Integer.parseInt(myStr.trim());
/* 1851 */     double[][] dataBlock = new double[nrows][];
/* 1852 */     xArr = new double[nrows];
/* 1853 */     yArr = new double[nrows];
/*      */ 
/* 1855 */     for (int i = 0; i < nrows; i++) {
/* 1856 */       myStr = getLineFromURL(myInputStream);
/* 1857 */       dataBlock[i] = getVals(myStr);
/*      */     }
/*      */ 
/* 1860 */     closeURL(myInputStream);
/* 1861 */     int ncolumns = dataBlock[0].length;
/*      */ 
/* 1864 */     for (int i = 0; i < ncolumns; i += 2) {
/* 1865 */       for (int j = 0; j < nrows; j++) {
/*      */         try {
/* 1867 */           xArr[j] = dataBlock[j][i];
/* 1868 */           yArr[j] = dataBlock[j][(i + 1)];
/*      */         }
/*      */         catch (ArrayIndexOutOfBoundsException e)
/*      */         {
/* 1874 */           System.out.println("need same number of x & y observations in column " + i + 
/* 1875 */             " and row " + j);
/*      */ 
/* 1877 */           return false;
/*      */         }
/*      */       }
/*      */ 
/* 1881 */       getDatasetParameters(i / 2, xArr, yArr, null, null);
/*      */     }
/*      */ 
/* 1884 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean getURLXYDataRows(String urlStr)
/*      */   {
/* 1896 */     double[] xyArr = (double[])null;
/* 1897 */     double[] xArr = (double[])null;
/* 1898 */     double[] yArr = (double[])null;
/*      */ 
/* 1900 */     int i = 0;
/*      */ 
/* 1905 */     if (urlStr != null) {
/* 1906 */       InputStream myInputStream = openURL(urlStr);
/*      */ 
/* 1908 */       if (myInputStream == null)
/* 1909 */         return false;
/*      */     }
/*      */     else {
/* 1912 */       return false;
/*      */     }
/*      */     InputStream myInputStream;
/* 1915 */     this.gotDatasets = true;
/*      */     String str;
/* 1917 */     while ((str = getLineFromURL(myInputStream)) != null)
/*      */     {
/*      */       String str;
/* 1918 */       xyArr = getVals(str);
/* 1919 */       xArr = new double[xyArr.length / 2];
/* 1920 */       yArr = new double[xArr.length];
/* 1921 */       int k = 0;
/*      */ 
/* 1923 */       for (int j = 0; j < xyArr.length; j++) {
/*      */         try {
/* 1925 */           xArr[k] = xyArr[j];
/* 1926 */           j++;
/* 1927 */           yArr[k] = xyArr[j];
/* 1928 */           k++;
/*      */         } catch (ArrayIndexOutOfBoundsException e) {
/* 1930 */           System.out.println("need same number of x & y observations in dataset " + i);
/*      */ 
/* 1932 */           return false;
/*      */         }
/*      */       }
/*      */ 
/* 1936 */       getDatasetParameters(i, xArr, yArr, null, null);
/* 1937 */       i++;
/*      */     }
/*      */ 
/* 1940 */     closeURL(myInputStream);
/*      */ 
/* 1942 */     return true;
/*      */   }
/*      */ 
/*      */   public double[] getVals(String s)
/*      */   {
/* 1954 */     StringTokenizer st = new StringTokenizer(s, this.delimiter);
/* 1955 */     double[] vals = new double[st.countTokens()];
/* 1956 */     int i = 0;
/*      */ 
/* 1958 */     while (st.hasMoreTokens()) {
/*      */       try {
/* 1960 */         vals[i] = Double.valueOf(st.nextToken().trim()).doubleValue();
/*      */       } catch (Exception e) {
/* 1962 */         vals[i] = (-1.0D / 0.0D);
/*      */       }
/*      */ 
/* 1965 */       i++;
/*      */     }
/*      */ 
/* 1968 */     return vals;
/*      */   }
/*      */ 
/*      */   protected void getAxisLabels(String s, AxisInterface ax)
/*      */   {
/* 1980 */     String[] axisLabels = getLabels(s);
/* 1981 */     ax.addLabels(axisLabels);
/*      */   }
/*      */ 
/*      */   public Image makeURLImage(String s)
/*      */   {
/* 1993 */     return this.getter.makeURLImage(s);
/*      */   }
/*      */ 
/*      */   public InputStream openURL(String s)
/*      */   {
/* 2005 */     return this.getter.openURL(s);
/*      */   }
/*      */ 
/*      */   public void parseAxOptions(String s, AxisInterface ax)
/*      */   {
/* 2017 */     String str = null;
/*      */ 
/* 2019 */     if (ax == null) {
/* 2020 */       return;
/*      */     }
/*      */ 
/* 2023 */     str = getParameter(s + "Options");
/*      */ 
/* 2025 */     if (str != null) {
/* 2026 */       getAxisOptions(ax, str);
/*      */     }
/*      */ 
/* 2029 */     if (!ax.getAutoScale()) {
/* 2030 */       str = getParameter(s + "Start");
/*      */ 
/* 2032 */       if (str != null) {
/* 2033 */         ax.setAxisStart(Double.valueOf(str).doubleValue());
/*      */       }
/*      */ 
/* 2036 */       str = getParameter(s + "End");
/*      */ 
/* 2038 */       if (str != null) {
/* 2039 */         ax.setAxisEnd(Double.valueOf(str).doubleValue());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2062 */     str = getParameter(s + "LabelFont");
/*      */ 
/* 2064 */     if (str != null) {
/* 2065 */       ax.setLabelFont(getFont(str));
/*      */     }
/*      */ 
/* 2074 */     str = getParameter(s + "Pattern");
/*      */ 
/* 2076 */     if (str != null) {
/* 2077 */       ((Axis)ax).setPattern(str);
/*      */     }
/*      */ 
/* 2080 */     str = getParameter(s + "LabelAngle");
/*      */ 
/* 2082 */     if (str != null) {
/* 2083 */       ax.setLabelAngle(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2093 */     str = getParameter(s + "Color");
/*      */ 
/* 2095 */     if (str != null) {
/* 2096 */       ax.setLabelColor(getColor(str));
/* 2097 */       ax.getLineGc().setLineColor(getColor(str));
/* 2098 */       ax.getGridGc().setLineColor(getColor(str));
/* 2099 */       ax.getTickGc().setLineColor(getColor(str));
/*      */     }
/*      */ 
/* 2102 */     str = getParameter(s + "LabelColor");
/*      */ 
/* 2104 */     if (str != null) {
/* 2105 */       ax.setLabelColor(getColor(str));
/*      */     }
/*      */ 
/* 2108 */     str = getParameter(s + "LineColor");
/*      */ 
/* 2110 */     if (str != null) {
/* 2111 */       ax.getLineGc().setLineColor(getColor(str));
/*      */     }
/*      */ 
/* 2114 */     str = getParameter(s + "GridColor");
/*      */ 
/* 2116 */     if (str != null) {
/* 2117 */       ax.getGridGc().setLineColor(getColor(str));
/*      */     }
/*      */ 
/* 2120 */     str = getParameter(s + "GridWidth");
/*      */ 
/* 2122 */     if (str != null) {
/* 2123 */       ax.getGridGc().setLineWidth(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2126 */     str = getParameter(s + "GridStyle");
/*      */ 
/* 2128 */     if (str != null) {
/* 2129 */       ax.getGridGc().setLineStyle(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2132 */     str = getParameter(s + "TickColor");
/*      */ 
/* 2134 */     if (str != null) {
/* 2135 */       ax.getTickGc().setLineColor(getColor(str));
/*      */     }
/*      */ 
/* 2138 */     str = getParameter(s + "TickLength");
/*      */ 
/* 2140 */     if (str != null) {
/* 2141 */       ax.setMajTickLength(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2144 */     str = getParameter(s + "MinTickLength");
/*      */ 
/* 2146 */     if (str != null) {
/* 2147 */       ax.setMinTickLength(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2150 */     str = getParameter(s + "TickCount");
/*      */ 
/* 2152 */     if (str != null) {
/* 2153 */       ax.setNumMajTicks(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2156 */     str = getParameter(s + "MinTickCount");
/*      */ 
/* 2158 */     if (str != null) {
/* 2159 */       ax.setNumMinTicks(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2162 */     str = getParameter(s + "GridCount");
/*      */ 
/* 2164 */     if (str != null) {
/* 2165 */       ax.setNumGrids(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2168 */     str = getParameter(s + "LabelCount");
/*      */ 
/* 2170 */     if (str != null) {
/* 2171 */       ax.setNumLabels(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2174 */     str = getParameter(s + "Title");
/*      */ 
/* 2176 */     if (str != null) {
/* 2177 */       ax.setTitleString(str);
/*      */     }
/*      */ 
/* 2180 */     str = getParameter(s + "TitleColor");
/*      */ 
/* 2182 */     if (str != null) {
/* 2183 */       ax.setTitleColor(getColor(str));
/*      */     }
/*      */ 
/* 2186 */     str = getParameter(s + "TitleFont");
/*      */ 
/* 2188 */     if (str != null) {
/* 2189 */       ax.setTitleFont(getFont(str));
/*      */     }
/*      */ 
/* 2192 */     str = getParameter(s + "StepSize");
/*      */ 
/* 2194 */     if (str != null) {
/* 2195 */       ax.setAxisStepSize(Double.valueOf(str).doubleValue());
/*      */     }
/*      */ 
/* 2198 */     getThresholdLineValues(s, ax);
/*      */   }
/*      */ 
/*      */   protected void getThresholdLineValues(String base, AxisInterface ax)
/*      */   {
/* 2211 */     for (int i = 0; i < 4; i++) {
/* 2212 */       String str = getParameter(base + "ThresholdLine" + i + "Value");
/*      */ 
/* 2214 */       if (str != null) {
/* 2215 */         String root = base + "ThresholdLine" + i;
/* 2216 */         ThresholdLine tl = new ThresholdLine();
/* 2217 */         double val = Double.valueOf(str).doubleValue();
/* 2218 */         tl.setValue(val);
/* 2219 */         populateGc(root, tl.getGc());
/*      */ 
/* 2221 */         String temp = getParameter(root + "LabelString");
/*      */ 
/* 2223 */         if ((temp == null) || (temp.equals(" ")) || (temp.equals("null")))
/* 2224 */           tl.setLabelString("");
/*      */         else {
/* 2226 */           tl.setLabelString(temp);
/*      */         }
/*      */ 
/* 2229 */         str = getParameter(root + "LabelFont");
/*      */ 
/* 2231 */         if (str != null) {
/* 2232 */           tl.setLabelFont(getFont(str));
/*      */         }
/*      */ 
/* 2235 */         str = getParameter(root + "LabelColor");
/*      */ 
/* 2237 */         if (str != null) {
/* 2238 */           tl.setLabelColor(getColor(str));
/*      */         }
/*      */         try
/*      */         {
/* 2242 */           ((Axis)ax).addThresholdLine(tl);
/*      */         } catch (Exception localException) {
/*      */         }
/*      */       } else {
/* 2246 */         ((Axis)ax).addThresholdLine(null);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void populateGc(String item, Gc gc)
/*      */   {
/* 2262 */     String str = getParameter(item + "Color");
/*      */ 
/* 2264 */     if (str != null) {
/* 2265 */       gc.setFillColor(getColor(str));
/* 2266 */       gc.setLineColor(getColor(str));
/*      */     }
/*      */ 
/* 2269 */     gc.setFillStyle(-1);
/*      */ 
/* 2271 */     str = getParameter(item + "SecondaryColor");
/*      */ 
/* 2273 */     if (str != null)
/*      */       try {
/* 2275 */         gc.setSecondaryFillColor(getColor(str));
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/* 2280 */     str = getParameter(item + "Texture");
/*      */ 
/* 2282 */     if (str != null) {
/* 2283 */       gc.setTexture(Integer.parseInt(str));
/* 2284 */       gc.setFillStyle(1);
/*      */     }
/*      */ 
/* 2287 */     str = getParameter(item + "Gradient");
/*      */ 
/* 2289 */     if (str != null) {
/*      */       try {
/* 2291 */         gc.setGradient(Integer.parseInt(str));
/* 2292 */         gc.setFillStyle(0);
/*      */       }
/*      */       catch (Exception localException1) {
/*      */       }
/* 2296 */       gc.setGradient(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2299 */     str = getParameter(item + "LineWidth");
/*      */ 
/* 2301 */     if (str != null) {
/* 2302 */       gc.setLineWidth(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2305 */     str = getParameter(item + "LineStyle");
/*      */ 
/* 2307 */     if (str != null) {
/* 2308 */       gc.setLineStyle(Integer.parseInt(str));
/*      */     }
/*      */ 
/* 2311 */     str = getParameter(item + "Image");
/*      */ 
/* 2313 */     if (str != null) {
/* 2314 */       gc.setFillStyle(1);
/*      */ 
/* 2319 */       gc.setImageBytes(JavachartUtil.decoderBase64(str));
/*      */     }
/*      */ 
/* 2322 */     str = getParameter(item + "Outlining");
/*      */ 
/* 2324 */     if (str != null) {
/* 2325 */       gc.setOutlineFills(Boolean.valueOf(str).booleanValue());
/*      */     }
/*      */ 
/* 2329 */     str = getParameter(item + "OutlineColor");
/*      */ 
/* 2331 */     if (str != null)
/* 2332 */       gc.setLineColor(getColor(str));
/*      */   }
/*      */ 
/*      */   public void replaceDataLabels(int whichSet, String[] labels)
/*      */   {
/* 2353 */     ArrayList myData = this.chart.getDatasets()[whichSet].getData();
/*      */ 
/* 2355 */     for (int i = 0; i < myData.size(); i++) {
/* 2356 */       Datum myDatum = (Datum)myData.get(i);
/*      */       try
/*      */       {
/* 2359 */         myDatum.setLabel(labels[i]);
/*      */       }
/*      */       catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void reReadURLDatasets()
/*      */   {
/* 2378 */     String str = getParameter("customDatasetHandler");
/*      */ 
/* 2380 */     if (str != null) {
/* 2381 */       this.getter.getMyDatasets(str);
/*      */     }
/*      */ 
/* 2384 */     str = getParameter("URLDataBlock");
/*      */ 
/* 2386 */     if (str != null)
/*      */     {
/*      */       InputStream myInputStream;
/* 2387 */       if ((myInputStream = openURL(str)) != null) {
/* 2388 */         for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 2389 */           String urlStr = getLineFromURL(myInputStream);
/* 2390 */           double[] yArr = getVals(urlStr);
/* 2391 */           this.chart.getDatasets()[i].replaceYData(yArr);
/*      */         }
/*      */       }
/*      */ 
/* 2395 */       closeURL(myInputStream);
/*      */     }
/*      */ 
/* 2398 */     str = getParameter("URLXYDataRows");
/*      */ 
/* 2400 */     if (str != null)
/*      */     {
/*      */       InputStream myInputStream;
/* 2405 */       if ((myInputStream = openURL(str)) != null) {
/* 2406 */         for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 2407 */           String urlStr = getLineFromURL(myInputStream);
/* 2408 */           double[] xyArr = getVals(urlStr);
/* 2409 */           double[] xArr = new double[xyArr.length / 2];
/* 2410 */           double[] yArr = new double[xArr.length];
/* 2411 */           int k = 0;
/*      */ 
/* 2413 */           for (int n = 0; n < xyArr.length; n++) {
/*      */             try {
/* 2415 */               xArr[k] = xyArr[n];
/* 2416 */               n++;
/* 2417 */               yArr[k] = xyArr[n];
/* 2418 */               k++;
/*      */             } catch (ArrayIndexOutOfBoundsException e) {
/* 2420 */               System.out.println("need same number of x & y observations in dataset " + 
/* 2421 */                 i);
/*      */ 
/* 2423 */               return;
/*      */             }
/*      */           }
/*      */ 
/* 2427 */           this.chart.getDatasets()[i].replaceYData(yArr);
/* 2428 */           this.chart.getDatasets()[i].replaceXData(xArr);
/*      */         }
/*      */       }
/*      */ 
/* 2432 */       closeURL(myInputStream);
/*      */     }
/*      */ 
/* 2435 */     str = getParameter("URLXYDataColumns");
/*      */ 
/* 2437 */     if (str != null)
/*      */     {
/*      */       InputStream myInputStream;
/* 2442 */       if ((myInputStream = openURL(str)) != null) {
/* 2443 */         String urlStr = getLineFromURL(myInputStream);
/* 2444 */         int nrows = Integer.parseInt(urlStr.trim());
/* 2445 */         int ncolumns = this.chart.getNumDatasets() * 2;
/*      */ 
/* 2448 */         double[][] dataBlock = new double[nrows][];
/* 2449 */         double[] xArr = new double[nrows];
/* 2450 */         double[] yArr = new double[nrows];
/*      */ 
/* 2452 */         for (int i = 0; i < nrows; i++) {
/* 2453 */           urlStr = getLineFromURL(myInputStream);
/* 2454 */           dataBlock[i] = getVals(urlStr);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         return;
/*      */       }
/*      */       double[][] dataBlock;
/*      */       int ncolumns;
/*      */       int nrows;
/*      */       double[] yArr;
/*      */       double[] xArr;
/*      */       String urlStr;
/* 2460 */       closeURL(myInputStream);
/*      */ 
/* 2462 */       for (int i = 0; i < ncolumns; i += 2) {
/* 2463 */         for (int j = 0; j < nrows; j++) {
/*      */           try {
/* 2465 */             xArr[j] = dataBlock[j][i];
/* 2466 */             yArr[j] = dataBlock[j][(i + 1)];
/*      */           } catch (ArrayIndexOutOfBoundsException e) {
/* 2468 */             System.out.println("need same number of x & y observations in column " + i + 
/* 2469 */               " and row " + j);
/*      */ 
/* 2471 */             return;
/*      */           }
/*      */         }
/*      */ 
/* 2475 */         this.chart.getDatasets()[(i / 2)].replaceYData(yArr);
/* 2476 */         this.chart.getDatasets()[(i / 2)].replaceXData(xArr);
/*      */       }
/*      */     }
/*      */ 
/* 2480 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 2481 */       str = getParameter("dataset" + i + "xURL");
/*      */ 
/* 2483 */       if (str != null)
/*      */       {
/*      */         InputStream myInputStream;
/* 2484 */         if ((myInputStream = openURL(str)) != null) {
/* 2485 */           String urlStr = getLineFromURL(myInputStream);
/* 2486 */           double[] xArr = getVals(urlStr);
/* 2487 */           this.chart.getDatasets()[i].replaceXData(xArr);
/*      */         }
/*      */ 
/* 2490 */         closeURL(myInputStream);
/*      */       }
/*      */ 
/* 2493 */       str = getParameter("dataset" + i + "yURL");
/*      */ 
/* 2495 */       if (str != null)
/*      */       {
/* 2497 */         if (str.equalsIgnoreCase("fake")) {
/* 2498 */           double[] yArr = new double[5];
/*      */ 
/* 2500 */           for (int j = 0; j < 5; j++) {
/* 2501 */             yArr[j] = (10.0D * Math.random());
/*      */           }
/* 2503 */           this.chart.getDatasets()[i].replaceYData(yArr);
/*      */         }
/*      */         else
/*      */         {
/*      */           InputStream myInputStream;
/* 2504 */           if ((myInputStream = openURL(str)) != null) {
/* 2505 */             String urlStr = getLineFromURL(myInputStream);
/* 2506 */             double[] yArr = getVals(urlStr);
/* 2507 */             this.chart.getDatasets()[i].replaceYData(yArr);
/* 2508 */             closeURL(myInputStream);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 2513 */       str = getParameter("dataset" + i + "URLLabels");
/*      */       InputStream myInputStream;
/* 2515 */       if ((str != null) && 
/* 2516 */         ((myInputStream = openURL(str)) != null)) {
/* 2517 */         String urlStr = getLineFromURL(myInputStream);
/* 2518 */         String[] labels = getLabels(urlStr);
/* 2519 */         replaceDataLabels(i, labels);
/* 2520 */         closeURL(myInputStream);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static Locale getLocale(String s)
/*      */   {
/* 2533 */     if (s == null) {
/* 2534 */       return null;
/*      */     }
/*      */ 
/* 2537 */     Locale locale = null;
/*      */ 
/* 2539 */     if (s.equalsIgnoreCase("canada"))
/* 2540 */       locale = Locale.CANADA;
/* 2541 */     else if (s.equalsIgnoreCase("canada_french"))
/* 2542 */       locale = Locale.CANADA_FRENCH;
/* 2543 */     else if (s.equalsIgnoreCase("china"))
/* 2544 */       locale = Locale.CHINA;
/* 2545 */     else if (s.equalsIgnoreCase("chinese"))
/* 2546 */       locale = Locale.CHINESE;
/* 2547 */     else if (s.equalsIgnoreCase("english"))
/* 2548 */       locale = Locale.ENGLISH;
/* 2549 */     else if (s.equalsIgnoreCase("france"))
/* 2550 */       locale = Locale.FRANCE;
/* 2551 */     else if (s.equalsIgnoreCase("french"))
/* 2552 */       locale = Locale.FRENCH;
/* 2553 */     else if (s.equalsIgnoreCase("german"))
/* 2554 */       locale = Locale.GERMAN;
/* 2555 */     else if (s.equalsIgnoreCase("germany"))
/* 2556 */       locale = Locale.GERMANY;
/* 2557 */     else if (s.equalsIgnoreCase("italian"))
/* 2558 */       locale = Locale.ITALIAN;
/* 2559 */     else if (s.equalsIgnoreCase("italy"))
/* 2560 */       locale = Locale.ITALY;
/* 2561 */     else if (s.equalsIgnoreCase("japan"))
/* 2562 */       locale = Locale.JAPAN;
/* 2563 */     else if (s.equalsIgnoreCase("japanese"))
/* 2564 */       locale = Locale.JAPANESE;
/* 2565 */     else if (s.equalsIgnoreCase("korea"))
/* 2566 */       locale = Locale.KOREA;
/* 2567 */     else if (s.equalsIgnoreCase("korean"))
/* 2568 */       locale = Locale.KOREAN;
/* 2569 */     else if (s.equalsIgnoreCase("PRC"))
/* 2570 */       locale = Locale.PRC;
/* 2571 */     else if (s.equalsIgnoreCase("simplified_chinese"))
/* 2572 */       locale = Locale.SIMPLIFIED_CHINESE;
/* 2573 */     else if (s.equalsIgnoreCase("taiwan"))
/* 2574 */       locale = Locale.TAIWAN;
/* 2575 */     else if (s.equalsIgnoreCase("traditional_chinese"))
/* 2576 */       locale = Locale.TRADITIONAL_CHINESE;
/* 2577 */     else if (s.equalsIgnoreCase("UK"))
/* 2578 */       locale = Locale.UK;
/* 2579 */     else if (s.equalsIgnoreCase("US"))
/* 2580 */       locale = Locale.US;
/*      */     else {
/*      */       try {
/* 2583 */         String s1 = s.substring(0, s.indexOf("_"));
/* 2584 */         String s2 = s.substring(s1.length() + 1);
/* 2585 */         locale = new Locale(s1, s2);
/*      */       }
/*      */       catch (StringIndexOutOfBoundsException e) {
/* 2588 */         System.out.println("Invalid locale");
/*      */       }
/*      */     }
/*      */ 
/* 2592 */     return locale;
/*      */   }
/*      */ 
/*      */   public Color getColor(String str)
/*      */   {
/* 2600 */     return ChartUtil.getColor(str);
/*      */   }
/*      */ 
/*      */   public Image makeImage(Gc g, String s)
/*      */   {
/* 2612 */     BufferedImage bimg = null;
/*      */     try
/*      */     {
/* 2615 */       bimg = ImageIO.read(new File(s));
/*      */     }
/*      */     catch (IOException e) {
/* 2618 */       e.printStackTrace();
/*      */     }
/*      */     ImageFilter resizeFilter;
/*      */     ImageFilter resizeFilter;
/* 2623 */     if (g != null)
/* 2624 */       resizeFilter = new ReplicateScaleFilter(g.getGlobals().getMaxX(), 
/* 2625 */         g.getGlobals().getMaxY());
/*      */     else {
/* 2627 */       resizeFilter = new ReplicateScaleFilter(8, 8);
/*      */     }
/*      */ 
/* 2630 */     FilteredImageSource source = new FilteredImageSource(bimg.getSource(), resizeFilter);
/*      */ 
/* 2632 */     return Toolkit.getDefaultToolkit().createImage(source);
/*      */   }
/*      */ 
/*      */   public Format toLabelFormat(String type)
/*      */   {
/* 2643 */     Format returnValue = null;
/*      */ 
/* 2645 */     if (type == null)
/* 2646 */       returnValue = NumberFormat.getNumberInstance();
/* 2647 */     else if (type.equals("0"))
/* 2648 */       returnValue = NumberFormat.getNumberInstance();
/* 2649 */     else if (type.equals("1"))
/* 2650 */       returnValue = NumberFormat.getPercentInstance();
/* 2651 */     else if (type.equals("2")) {
/* 2652 */       returnValue = NumberFormat.getCurrencyInstance();
/*      */     }
/*      */ 
/* 2655 */     return returnValue;
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.applet.ParameterParser
 * JD-Core Version:    0.6.2
 */