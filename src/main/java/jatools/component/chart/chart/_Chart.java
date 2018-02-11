/*      */ package jatools.component.chart.chart;
/*      */ 
/*      */ import jatools.component.chart.JavachartUtil;
/*      */ import jatools.component.chart.Tip;
/*      */ import jatools.component.chart.applet.ChartUtil;
/*      */ import jatools.component.chart.applet.ParameterParser;
/*      */ import jatools.engine.ProtectClass;
/*      */ import jatools.util.Map;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.RenderingHints;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Locale;
/*      */ 
/*      */ public abstract class _Chart
/*      */   implements ChartInterface, Serializable, ProtectClass
/*      */ {
/*   64 */   String name = "New Chart";
/*      */   protected Graphics canvas;
/*   68 */   protected boolean legendVisible = false;
/*      */ 
/*   70 */   protected int width = 640;
/*      */ 
/*   72 */   protected int height = 480;
/*      */ 
/*   74 */   protected boolean xAxisVisible = true;
/*      */ 
/*   76 */   protected boolean yAxisVisible = true;
/*      */ 
/*   78 */   public ParameterParser parser = null;
/*      */   protected Globals globals;
/*      */   protected Dataset[] datasets;
/*      */   protected Plotarea plotarea;
/*      */   protected Background background;
/*      */   protected LegendInterface legend;
/*      */   protected AxisInterface xAxis;
/*      */   protected AxisInterface yAxis;
/*      */   protected DataRepresentation dataRepresentation;
/*      */   Locale locale;
/*  102 */   protected int numberOfDatasets = 0;
/*      */ 
/*  104 */   private boolean isAntialiasing = true;
/*      */ 
/*  106 */   public static int MAX_DATASETS = 300;
/*      */   public static final String version = "chart 4.2.2";
/*      */ 
/*      */   public _Chart()
/*      */   {
/*  114 */     initChart();
/*      */   }
/*      */ 
/*      */   public _Chart(String s)
/*      */   {
/*  124 */     this.name = s;
/*  125 */     initChart();
/*      */   }
/*      */ 
/*      */   public _Chart(String s, Locale chartLocale)
/*      */   {
/*  138 */     this.locale = chartLocale;
/*  139 */     this.name = s;
/*  140 */     initChart();
/*      */   }
/*      */ 
/*      */   public _Chart(Locale chartLocale)
/*      */   {
/*  151 */     this.locale = chartLocale;
/*  152 */     initChart();
/*      */   }
/*      */ 
/*      */   public void addDataset(String s, double[] x)
/*      */   {
/*  165 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  166 */       this.datasets[this.numberOfDatasets] = new Dataset(s, x, this.numberOfDatasets, 
/*  167 */         this.globals);
/*  168 */       this.numberOfDatasets += 1;
/*      */     } else {
/*  170 */       System.out.println("max datasets is " + MAX_DATASETS);
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void addDataSet(String s, double[] x)
/*      */   {
/*  184 */     addDataset(s, x);
/*      */   }
/*      */ 
/*      */   public void addDataset(String s, double[] x, double[] y)
/*      */   {
/*  199 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  200 */       this.datasets[this.numberOfDatasets] = new Dataset(s, x, y, this.numberOfDatasets, 
/*  201 */         this.globals);
/*  202 */       this.numberOfDatasets += 1;
/*      */     } else {
/*  204 */       System.out.println("max datasets is " + MAX_DATASETS);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addDataset(String s, double[] x, double[] y, String[] labels, Tip[] tips) {
/*  209 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  210 */       this.datasets[this.numberOfDatasets] = new Dataset(s, x, y, labels, 
/*  211 */         this.numberOfDatasets, this.globals, tips);
/*  212 */       this.numberOfDatasets += 1;
/*      */     } else {
/*  214 */       System.out.println("max datasets is " + MAX_DATASETS);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addDataset(String s, double[] x, double[] y, double[] z)
/*      */   {
/*  233 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  234 */       this.datasets[this.numberOfDatasets] = new Dataset(s, x, y, z, 
/*  235 */         this.numberOfDatasets, this.globals);
/*  236 */       this.numberOfDatasets += 1;
/*      */     } else {
/*  238 */       System.out.println("max datasets is " + MAX_DATASETS);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addDataset(String s, double[] x, double[] hi, double[] lo, double[] close)
/*      */   {
/*  257 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  258 */       this.datasets[this.numberOfDatasets] = new Dataset(s, x, hi, lo, close, 
/*  259 */         this.numberOfDatasets, this.globals);
/*  260 */       this.numberOfDatasets += 1;
/*      */     } else {
/*  262 */       System.out.println("maximum number of datasets is " + MAX_DATASETS);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addDataset(String s, double[] x, double[] y, double[] z, String[] labels)
/*      */   {
/*  282 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  283 */       this.datasets[this.numberOfDatasets] = new Dataset(s, x, y, z, labels, 
/*  284 */         this.numberOfDatasets, this.globals);
/*  285 */       this.numberOfDatasets += 1;
/*      */     } else {
/*  287 */       System.out.println("max datasets is " + MAX_DATASETS);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addDataset(String s, double[] x, double[] y, String[] labels)
/*      */   {
/*  304 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  305 */       this.datasets[this.numberOfDatasets] = new Dataset(s, x, y, labels, 
/*  306 */         this.numberOfDatasets, this.globals);
/*  307 */       this.numberOfDatasets += 1;
/*      */     } else {
/*  309 */       System.out.println("max datasets is " + MAX_DATASETS);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addDataset(String s, double[] y, String[] dataLabels)
/*      */   {
/*  324 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  325 */       this.datasets[this.numberOfDatasets] = new Dataset(s, y, dataLabels, 
/*  326 */         this.numberOfDatasets, this.globals);
/*  327 */       this.numberOfDatasets += 1;
/*      */     } else {
/*  329 */       System.out.println("maximum number of datasets is " + MAX_DATASETS);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addDataset(String name, Date[] dates, double[] y)
/*      */   {
/*  348 */     double[] x = new double[dates.length];
/*  349 */     for (int i = 0; i < x.length; i++) {
/*  350 */       x[i] = dates[i].getTime();
/*      */     }
/*  352 */     addDataset(name, x, y);
/*      */   }
/*      */ 
/*      */   public void addDataset(Dataset d)
/*      */   {
/*  362 */     if (this.numberOfDatasets < MAX_DATASETS) {
/*  363 */       this.datasets[this.numberOfDatasets] = d;
/*  364 */       d.setGlobals(this.globals);
/*  365 */       this.numberOfDatasets += 1;
/*      */     } else {
/*  367 */       System.out.println("max datasets is " + MAX_DATASETS);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void deleteDataset(String s)
/*      */   {
/*  377 */     for (int i = 0; i < this.numberOfDatasets; i++)
/*  378 */       if (this.datasets[i].setName.equals(s)) {
/*  379 */         deleteDataset(this.datasets[i]);
/*  380 */         return;
/*      */       }
/*      */   }
/*      */ 
/*      */   public void deleteDataset(Dataset d)
/*      */   {
/*  394 */     for (int i = 0; i < this.numberOfDatasets; i++)
/*  395 */       if (this.datasets[i] == d) {
/*  396 */         this.datasets[i] = null;
/*  397 */         for (int j = i + 1; j < this.datasets.length; j++)
/*  398 */           this.datasets[(j - 1)] = this.datasets[j];
/*  399 */         i = this.numberOfDatasets;
/*      */       }
/*  401 */     this.numberOfDatasets -= 1;
/*  402 */     this.numberOfDatasets = Math.max(0, this.numberOfDatasets);
/*      */   }
/*      */ 
/*      */   public void drawGraph()
/*      */   {
/*  409 */     if (this.globals.useDisplayList)
/*  410 */       this.globals.displayList.clear();
/*      */   }
/*      */ 
/*      */   public void drawGraph(Graphics g)
/*      */   {
/*  420 */     if (this.globals.useDisplayList)
/*  421 */       this.globals.displayList.clear();
/*  422 */     if (this.isAntialiasing)
/*  423 */       ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  424 */         RenderingHints.VALUE_ANTIALIAS_ON);
/*      */   }
/*      */ 
/*      */   protected void drawAxisOverlays(Graphics g)
/*      */   {
/*  433 */     if (this.xAxis != null) {
/*  434 */       Graphics saveG = g;
/*  435 */       if ((this.globals.threeD) && (
/*  436 */         (this.xAxis.getSide() == 3) || 
/*  437 */         (this.xAxis.getSide() == 2))) {
/*  438 */         g = g.create();
/*  439 */         g.translate(this.globals.xOffset, -this.globals.yOffset);
/*      */       }
/*      */       try
/*      */       {
/*  443 */         Axis ax = (Axis)this.xAxis;
/*  444 */         if (ax.lineVis)
/*  445 */           ax.drawLine(g);
/*  446 */         ax.drawThresholdLines(g);
/*      */       } catch (Exception localException) {
/*      */       }
/*  449 */       g = saveG;
/*      */     }
/*  451 */     if (this.yAxis != null) {
/*  452 */       Graphics saveG = g;
/*  453 */       if ((this.globals.threeD) && (
/*  454 */         (this.yAxis.getSide() == 2) || 
/*  455 */         (this.yAxis.getSide() == 3))) {
/*  456 */         g = g.create();
/*  457 */         g.translate(this.globals.xOffset, -this.globals.yOffset);
/*      */       }
/*      */       try
/*      */       {
/*  461 */         Axis ax = (Axis)this.yAxis;
/*  462 */         if (ax.lineVis)
/*  463 */           ax.drawLine(g);
/*  464 */         ax.drawThresholdLines(g);
/*      */       } catch (Exception localException1) {
/*      */       }
/*  467 */       g = saveG;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Background getBackground()
/*      */   {
/*  477 */     return this.background;
/*      */   }
/*      */ 
/*      */   public DataRepresentation getDataRepresentation()
/*      */   {
/*  493 */     return this.dataRepresentation;
/*      */   }
/*      */ 
/*      */   public Dataset getDataset(String name)
/*      */   {
/*  503 */     for (int i = 0; i < this.datasets.length; i++) {
/*  504 */       if ((this.datasets[i] != null) && 
/*  505 */         (this.datasets[i].setName.equals(name)))
/*  506 */         return this.datasets[i];
/*      */     }
/*  508 */     return null;
/*      */   }
/*      */ 
/*      */   public Dataset[] getDatasets()
/*      */   {
/*  517 */     return this.datasets;
/*      */   }
/*      */ 
/*      */   private boolean isEmptyDatasets()
/*      */   {
/*  522 */     return (this.datasets == null) || (this.datasets.length == 0) || (this.datasets[0] == null);
/*      */   }
/*      */ 
/*      */   public DisplayList getDisplayList()
/*      */   {
/*  532 */     return this.globals.getDisplayList();
/*      */   }
/*      */ 
/*      */   public Globals getGlobals()
/*      */   {
/*  541 */     return this.globals;
/*      */   }
/*      */ 
/*      */   public int getHeight()
/*      */   {
/*  550 */     return this.globals.maxY;
/*      */   }
/*      */ 
/*      */   public Image getImage()
/*      */   {
/*  563 */     return this.globals.getImage();
/*      */   }
/*      */ 
/*      */   public LegendInterface getLegend()
/*      */   {
/*  572 */     return this.legend;
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/*  582 */     return this.name;
/*      */   }
/*      */ 
/*      */   public int getNumDatasets()
/*      */   {
/*  589 */     return this.numberOfDatasets;
/*      */   }
/*      */ 
/*      */   public Plotarea getPlotarea()
/*      */   {
/*  598 */     return this.plotarea;
/*      */   }
/*      */ 
/*      */   public RotateString getStringRotator()
/*      */   {
/*  605 */     return this.globals.stringRotator;
/*      */   }
/*      */ 
/*      */   public boolean getUseDisplayList()
/*      */   {
/*  613 */     return this.globals.getUseDisplayList();
/*      */   }
/*      */ 
/*      */   public int getWidth()
/*      */   {
/*  622 */     return this.globals.maxX;
/*      */   }
/*      */ 
/*      */   public AxisInterface getXAxis()
/*      */   {
/*  631 */     return this.xAxis;
/*      */   }
/*      */ 
/*      */   public int getXOffset()
/*      */   {
/*  639 */     return this.globals.xOffset;
/*      */   }
/*      */ 
/*      */   public AxisInterface getYAxis()
/*      */   {
/*  648 */     return this.yAxis;
/*      */   }
/*      */ 
/*      */   public int getYOffset()
/*      */   {
/*  655 */     return this.globals.yOffset;
/*      */   }
/*      */ 
/*      */   public boolean includesDataset(Dataset d)
/*      */   {
/*  662 */     for (int i = 0; i < this.datasets.length; i++)
/*  663 */       if (this.datasets[i] == d)
/*  664 */         return true;
/*  665 */     return false;
/*      */   }
/*      */ 
/*      */   protected void initChart()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void initDatasets()
/*      */   {
/*  675 */     this.datasets = new Dataset[MAX_DATASETS];
/*      */   }
/*      */ 
/*      */   protected void initGlobals()
/*      */   {
/*  684 */     this.globals = new Globals();
/*  685 */     if (this.locale != null)
/*  686 */       this.globals.locale = this.locale;
/*      */   }
/*      */ 
/*      */   public boolean isLegendVisible()
/*      */   {
/*  696 */     return this.legendVisible;
/*      */   }
/*      */ 
/*      */   public boolean isThreeD()
/*      */   {
/*  703 */     return this.globals.threeD;
/*      */   }
/*      */ 
/*      */   public boolean isXAxisVisible()
/*      */   {
/*  712 */     return this.xAxisVisible;
/*      */   }
/*      */ 
/*      */   public boolean isYAxisVisible()
/*      */   {
/*  721 */     return this.yAxisVisible;
/*      */   }
/*      */ 
/*      */   public void paint()
/*      */   {
/*  728 */     drawGraph();
/*      */   }
/*      */ 
/*      */   public void paint(Component c, Graphics g)
/*      */   {
/*  758 */     if (isEmptyDatasets())
/*  759 */       return;
/*  760 */     setStringRotator(new RotateString(c));
/*  761 */     resize(c.getWidth(), c.getHeight());
/*  762 */     drawGraph(g);
/*      */   }
/*      */ 
/*      */   public void paint(Component c, Graphics g, int width, int height)
/*      */   {
/*  772 */     if (isEmptyDatasets())
/*  773 */       return;
/*  774 */     setStringRotator(new RotateString(c));
/*  775 */     resize(width, height);
/*  776 */     drawGraph(g);
/*      */   }
/*      */ 
/*      */   public void paint(Graphics g)
/*      */   {
/*  786 */     if (isEmptyDatasets())
/*  787 */       return;
/*  788 */     drawGraph(g);
/*      */   }
/*      */ 
/*      */   public void resize(int newWidth, int newHeight)
/*      */   {
/*  795 */     this.globals.maxY = newHeight;
/*  796 */     if (this.plotarea != null)
/*  797 */       this.plotarea.resize(newWidth, newHeight);
/*  798 */     if (this.background != null)
/*  799 */       this.background.resize(newWidth, newHeight);
/*  800 */     if (this.legend != null)
/*  801 */       this.legend.resize(newWidth, newHeight);
/*  802 */     this.globals.maxX = newWidth;
/*  803 */     this.globals.maxY = newHeight;
/*  804 */     this.width = newWidth;
/*  805 */     this.height = newHeight;
/*      */   }
/*      */ 
/*      */   public void setBackground(Background b)
/*      */   {
/*  816 */     this.background = b;
/*  817 */     this.background.globals = this.globals;
/*  818 */     this.background.gc.globals = this.globals;
/*      */   }
/*      */ 
/*      */   public void setDataRepresentation(DataRepresentation rep)
/*      */   {
/*  832 */     rep.plotarea = this.plotarea;
/*  833 */     rep.xAxis = this.xAxis;
/*  834 */     rep.yAxis = this.yAxis;
/*  835 */     rep.datasets = this.datasets;
/*  836 */     rep.globals = this.globals;
/*  837 */     this.dataRepresentation = rep;
/*      */   }
/*      */ 
/*      */   public void setDisplayList(DisplayList d)
/*      */   {
/*  845 */     this.globals.setDisplayList(d);
/*      */   }
/*      */ 
/*      */   public void setGlobals(Globals g)
/*      */   {
/*  855 */     this.globals = g;
/*      */     try {
/*  857 */       this.dataRepresentation.globals = g;
/*  858 */       this.plotarea.globals = g;
/*  859 */       this.plotarea.gc.globals = g;
/*  860 */       this.background.globals = g;
/*  861 */       this.background.gc.globals = g;
/*  862 */       if ((this.legend != null) && 
/*  863 */         ((this.legend instanceof Legend))) {
/*  864 */         ((Legend)this.legend).globals = g;
/*  865 */         ((Legend)this.legend).backgroundGc.globals = g;
/*      */       }
/*  867 */       for (int i = 0; i < this.datasets.length; i++)
/*  868 */         if (this.datasets[i] != null)
/*  869 */           this.datasets[i].setGlobals(g);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setHeight(int h)
/*      */   {
/*  883 */     this.height = h;
/*  884 */     resize(this.width, this.height);
/*      */   }
/*      */ 
/*      */   public void setImage(Image i)
/*      */   {
/*  895 */     this.globals.setImage(i);
/*      */   }
/*      */ 
/*      */   public void setLegend(LegendInterface l)
/*      */   {
/*  906 */     this.legend = l;
/*  907 */     if ((this.legend instanceof Legend)) {
/*  908 */       ((Legend)this.legend).datasets = this.datasets;
/*  909 */       ((Legend)this.legend).globals = this.globals;
/*  910 */       ((Legend)this.legend).backgroundGc.globals = this.globals;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setLegendVisible(boolean v)
/*      */   {
/*  922 */     this.legendVisible = v;
/*      */   }
/*      */ 
/*      */   public void setName(String s)
/*      */   {
/*  933 */     this.name = s;
/*      */   }
/*      */ 
/*      */   public void setPlotarea(Plotarea p)
/*      */   {
/*  944 */     this.plotarea = p;
/*  945 */     this.plotarea.globals = this.globals;
/*  946 */     this.plotarea.gc.globals = this.globals;
/*      */   }
/*      */ 
/*      */   public void setStringRotator(RotateString rotator)
/*      */   {
/*  954 */     this.globals.stringRotator = rotator;
/*      */   }
/*      */ 
/*      */   public void setThreeD(boolean b)
/*      */   {
/*  964 */     this.globals.threeD = b;
/*      */   }
/*      */ 
/*      */   public boolean getThreeD() {
/*  968 */     return this.globals.threeD;
/*      */   }
/*      */ 
/*      */   public void setUseDisplayList(boolean yesNo)
/*      */   {
/*  982 */     this.globals.setUseDisplayList(yesNo);
/*      */   }
/*      */ 
/*      */   public void setWidth(int w)
/*      */   {
/*  993 */     this.width = w;
/*  994 */     resize(this.width, this.height);
/*      */   }
/*      */ 
/*      */   public void setXAxis(AxisInterface a)
/*      */   {
/* 1004 */     this.xAxis = a;
/*      */     try {
/* 1006 */       Axis ax = (Axis)a;
/* 1007 */       ax.globals = this.globals;
/* 1008 */       ax.lineGc.globals = this.globals;
/* 1009 */       ax.tickGc.globals = this.globals;
/* 1010 */       ax.gridGc.globals = this.globals;
/* 1011 */       ax.plotarea = this.plotarea;
/* 1012 */       ax.datasets = this.datasets;
/* 1013 */       ax.isXAxis = true;
/* 1014 */       if (this.dataRepresentation != null)
/* 1015 */         this.dataRepresentation.xAxis = a;
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setXAxisVisible(boolean vis)
/*      */   {
/* 1029 */     this.xAxisVisible = vis;
/*      */   }
/*      */ 
/*      */   public void setXOffset(int i)
/*      */   {
/* 1039 */     this.globals.xOffset = i;
/*      */   }
/*      */ 
/*      */   public void setYAxis(AxisInterface a)
/*      */   {
/* 1049 */     this.yAxis = a;
/*      */     try {
/* 1051 */       Axis ax = (Axis)a;
/* 1052 */       ax.globals = this.globals;
/* 1053 */       ax.lineGc.globals = this.globals;
/* 1054 */       ax.tickGc.globals = this.globals;
/* 1055 */       ax.gridGc.globals = this.globals;
/* 1056 */       ax.plotarea = this.plotarea;
/* 1057 */       ax.datasets = this.datasets;
/* 1058 */       ax.isXAxis = false;
/* 1059 */       if (this.dataRepresentation != null)
/* 1060 */         this.dataRepresentation.yAxis = a;
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setYAxisVisible(boolean vis)
/*      */   {
/* 1074 */     this.yAxisVisible = vis;
/*      */   }
/*      */ 
/*      */   public void setYOffset(int i)
/*      */   {
/* 1081 */     this.globals.yOffset = i;
/*      */   }
/*      */ 
/*      */   public void applyGeneralProperty(Map map)
/*      */   {
/* 1086 */     String str = getBackground().getTitleString();
/* 1087 */     if ((str != null) && (!str.equals(""))) {
/* 1088 */       map.put("titleString", str);
/*      */     }
/* 1090 */     if (getBackground().getTitleFont() != null) {
/* 1091 */       map.put("titleFont", 
/* 1092 */         ChartUtil.toString(getBackground().getTitleFont()));
/*      */     }
/* 1094 */     map.put("titleColor", 
/* 1095 */       ChartUtil.toString(getBackground().getTitleColor()));
/* 1096 */     if (getBackground().getTitleX() != null)
/* 1097 */       map.put("titleX", getBackground().getTitleX());
/* 1098 */     if (getBackground().getTitleY() != null) {
/* 1099 */       map.put("titleY", getBackground().getTitleY());
/*      */     }
/* 1101 */     str = getBackground().getSubTitleString();
/* 1102 */     if (str != null) {
/* 1103 */       map.put("subTitleString", str);
/*      */     }
/* 1105 */     if (getBackground().getSubTitleFont() != null) {
/* 1106 */       map.put("subTitleFont", 
/* 1107 */         ChartUtil.toString(getBackground().getSubTitleFont()));
/*      */     }
/* 1109 */     map.put("subTitleColor", 
/* 1110 */       ChartUtil.toString(getBackground().getSubTitleColor()));
/* 1111 */     if (getBackground().getSubTitleX() != null)
/* 1112 */       map.put("subTitleX", getBackground().getSubTitleX());
/* 1113 */     if (getBackground().getSubTitleY() != null) {
/* 1114 */       map.put("subTitleY", getBackground().getSubTitleY());
/*      */     }
/* 1116 */     putGc("background", getBackground().getGc(), map);
/*      */ 
/* 1118 */     map.put("isAntialiasing", isAntialiasing());
/*      */ 
/* 1120 */     if (getPlotarea() != null) {
/* 1121 */       map.put("plotAreaLeft", getPlotarea().getLlX());
/* 1122 */       map.put("plotAreaRight", getPlotarea().getUrX());
/* 1123 */       map.put("plotAreaTop", getPlotarea().getUrY());
/* 1124 */       map.put("plotAreaBottom", getPlotarea().getLlY());
/*      */     }
/* 1126 */     putGc("plotarea", getPlotarea().getGc(), map);
/*      */ 
/* 1128 */     if (getThreeD()) {
/* 1129 */       map.put("threeD", "true");
/* 1130 */       map.put("XDepth", getXOffset());
/* 1131 */       map.put("YDepth", getYOffset());
/*      */     }
/*      */ 
/* 1134 */     if (this.legendVisible) {
/* 1135 */       map.put("legendOn", "true");
/*      */ 
/* 1137 */       if (getLegend().getVerticalLayout()) {
/* 1138 */         map.put("legendVertical", getLegend().getVerticalLayout());
/*      */       }
/*      */ 
/* 1141 */       if (!getLegend().getBackgroundVisible()) {
/* 1142 */         map.put("legendOpaque", getLegend().getBackgroundVisible());
/*      */       }
/*      */ 
/* 1145 */       if (getLegend().getInvertLegend()) {
/* 1146 */         map.put("invertLegend", getLegend().getInvertLegend());
/*      */       }
/*      */ 
/* 1149 */       map.put("legendLabelFont", 
/* 1150 */         ChartUtil.toString(getLegend().getLabelFont()));
/* 1151 */       map.put("legendLabelColor", 
/* 1152 */         ChartUtil.toString(getLegend().getLabelColor()));
/* 1153 */       map.put("legendllX", getLegend().getLlX());
/* 1154 */       map.put("legendllY", getLegend().getLlY());
/* 1155 */       map.put("iconWidth", getLegend().getIconWidth());
/* 1156 */       map.put("iconHeight", getLegend().getIconHeight());
/* 1157 */       map.put("iconGapt", getLegend().getIconGap());
/*      */ 
/* 1159 */       putGc("legend", getLegend().getBackgroundGc(), map);
/*      */     }
/*      */ 
/* 1163 */     DataRepresentation data = getDataRepresentation();
/* 1164 */     if (data != null) {
/* 1165 */       if (data.getLabelsOn())
/* 1166 */         map.put("labelsOn", "true");
/*      */       else
/* 1168 */         map.put("labelsOn", "false");
/* 1169 */       map.put("labelAngle", data.getLabelAngle());
/*      */ 
/* 1181 */       map.put("dataPattern", data.getPattern());
/*      */     }
/*      */ 
/* 1186 */     putAxOptions(getXAxis(), "xAxis", map);
/* 1187 */     putAxOptions(getYAxis(), "yAxis", map);
/*      */ 
/* 1190 */     putDatasets("dataset", map);
/*      */   }
/*      */ 
/*      */   public String valueOfLabelFormat()
/*      */   {
/* 1195 */     String format = null;
/*      */ 
/* 1197 */     DataRepresentation data = getDataRepresentation();
/* 1198 */     DecimalFormat fm = (DecimalFormat)data.getFormat();
/* 1199 */     if ((data != null) || 
/* 1201 */       (fm == null)) {
/* 1202 */       format = "0";
/* 1203 */     } else if (fm.getMultiplier() == 100) {
/* 1204 */       format = "1";
/*      */     } else {
/* 1206 */       char c = fm.toPattern().charAt(0);
/*      */ 
/* 1208 */       if (c != '#')
/* 1209 */         format = "2";
/*      */       else {
/* 1211 */         format = "0";
/*      */       }
/*      */     }
/* 1214 */     return format;
/*      */   }
/*      */ 
/*      */   private void putDatasets(String prefix, Map map)
/*      */   {
/* 1223 */     for (int i = 0; i < getDatasets().length; i++)
/* 1224 */       if (getDatasets()[i] != null) {
/* 1225 */         Dataset dataset = getDatasets()[i];
/* 1226 */         if (dataset.getLabels() != null) {
/* 1227 */           String str = JavachartUtil.plus(dataset.getLabels(), ",");
/* 1228 */           if ((str != null) && (!str.equals("")))
/* 1229 */             map.put(prefix + i + "Labels", str);
/*      */         }
/* 1231 */         map.put(prefix + i + "LabelFont", 
/* 1232 */           ChartUtil.toString(dataset.getLabelFont()));
/* 1233 */         map.put(prefix + i + "LabelColor", 
/* 1234 */           ChartUtil.toString(dataset.getLabelColor()));
/* 1235 */         map.put(prefix + i + "Name", dataset.getName());
/* 1236 */         map.put(prefix + i + "MarkerStyle", dataset.getGc()
/* 1237 */           .getMarkerStyle());
/* 1238 */         map.put(prefix + i + "MarkerSize", dataset.getGc()
/* 1239 */           .getMarkerSize());
/* 1240 */         map.put(prefix + i + "MarkerColor", 
/* 1241 */           ChartUtil.toString(dataset.getGc().getFillColor()));
/*      */ 
/* 1265 */         putGc(prefix + i, dataset.getGc(), map);
/* 1266 */         map.put(prefix + i + "MarkerStyle", dataset.getGc()
/* 1267 */           .getMarkerStyle());
/* 1268 */         map.put(prefix + i + "MarkerSize", dataset.getGc()
/* 1269 */           .getMarkerSize());
/* 1270 */         map.put(prefix + i + "MarkerColor", 
/* 1271 */           ChartUtil.toString(dataset.getGc().getFillColor()));
/* 1272 */         if (dataset.getGc().getImageBytes() != null) {
/* 1273 */           map.put(prefix + i + "MarkerImage", 
/* 1274 */             JavachartUtil.edcodeBase64(dataset.getGc().getImageBytes()));
/*      */         }
/* 1276 */         if ((this instanceof PieChart))
/* 1277 */           putDatum(prefix + i, dataset, map);
/*      */       }
/*      */   }
/*      */ 
/*      */   public String setVals(double[] d)
/*      */   {
/* 1286 */     StringBuffer str = new StringBuffer();
/* 1287 */     for (int i = 0; i < d.length; i++) {
/* 1288 */       if (Double.isInfinite(d[i])) {
/* 1289 */         return null;
/*      */       }
/* 1291 */       str.append(d[i] + ",");
/*      */     }
/* 1293 */     return str.substring(0, str.length() - 1);
/*      */   }
/*      */ 
/*      */   private String setDateVals(double[] d) {
/* 1297 */     return null;
/*      */   }
/*      */ 
/*      */   private String setVals(String[] s) {
/* 1301 */     StringBuffer str = new StringBuffer();
/* 1302 */     if (s.length <= 0)
/* 1303 */       return null;
/* 1304 */     for (int i = 0; i < s.length; i++) {
/* 1305 */       if (s[i] == null)
/* 1306 */         return null;
/* 1307 */       str.append(s[i] + ",");
/*      */     }
/* 1309 */     return str.substring(0, str.length() - 1);
/*      */   }
/*      */ 
/*      */   public void putAxOptions(AxisInterface ax, String item, Map map) {
/* 1313 */     if (ax != null)
/*      */     {
/* 1315 */       getAxisOptions(ax, item, map);
/*      */ 
/* 1317 */       if (!ax.getAutoScale()) {
/* 1318 */         map.put(item + "Start", String.valueOf(ax.getAxisStart()));
/* 1319 */         map.put(item + "End", String.valueOf(ax.getAxisEnd()));
/* 1320 */         map.put(item + "StepSize", String.valueOf(ax.getAxisStepSize()));
/*      */       }
/*      */ 
/* 1323 */       if ((ax.getTitleString() != null) && (!ax.getTitleString().equals(""))) {
/* 1324 */         map.put(item + "Title", ax.getTitleString());
/* 1325 */         map.put(item + "TitleFont", toString(ax.getTitleFont()));
/* 1326 */         map.put(item + "TitleColor", 
/* 1327 */           ChartUtil.toString(ax.getTitleColor()));
/*      */       }
/*      */ 
/* 1331 */       if (ax.getLabelVis())
/*      */       {
/* 1333 */         map.put(item + "LabelFont", toString(ax.getLabelFont()));
/* 1334 */         map.put(item + "LabelColor", 
/* 1335 */           ChartUtil.toString(ax.getLabelColor()));
/* 1336 */         if ((((Axis)ax).getPattern() != null) && 
/* 1337 */           (!((Axis)ax).getPattern().equals(""))) {
/* 1338 */           map.put(item + "Pattern", ((Axis)ax).getPattern());
/*      */         }
/*      */ 
/* 1356 */         map.put(item + "LabelAngle", String.valueOf(ax.getLabelAngle()));
/*      */ 
/* 1359 */         if (ax.getAutoScale())
/* 1360 */           map.remove(item + "LabelCount");
/*      */         else {
/* 1362 */           map.put(item + "LabelCount", 
/* 1363 */             String.valueOf(ax.getNumLabels()));
/*      */         }
/*      */       }
/*      */ 
/* 1367 */       if (ax.getLineVis()) {
/* 1368 */         if (ax.getLineGc().getFillColor() == null) {
/* 1369 */           ax.getLineGc().setFillColor(Color.BLACK);
/*      */         }
/*      */ 
/* 1372 */         map.put(item + "LineColor", 
/* 1373 */           ChartUtil.toString(ax.getLineGc().getLineColor()));
/*      */       }
/*      */ 
/* 1377 */       if (ax.getMajTickVis()) {
/* 1378 */         if (ax.getTickGc().getFillColor() == null) {
/* 1379 */           ax.getTickGc().setFillColor(Color.BLACK);
/*      */         }
/* 1381 */         map.put(item + "TickColor", 
/* 1382 */           ChartUtil.toString(ax.getTickGc().getLineColor()));
/* 1383 */         map.put(item + "TickLength", 
/* 1384 */           String.valueOf(ax.getMajTickLength()));
/* 1385 */         if (!ax.getAutoScale()) {
/* 1386 */           map.put(item + "TickCount", 
/* 1387 */             String.valueOf(ax.getNumMajTicks()));
/*      */         }
/*      */       }
/*      */ 
/* 1391 */       if (ax.getMinTickVis()) {
/* 1392 */         map.put(item + "MinTickLength", 
/* 1393 */           String.valueOf(ax.getMinTickLength()));
/* 1394 */         if (!ax.getAutoScale()) {
/* 1395 */           map.put(item + "MinTickCount", 
/* 1396 */             String.valueOf(ax.getNumMinTicks()));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1401 */       if (ax.getGridVis()) {
/* 1402 */         if (ax.getGridGc().getFillColor() == null) {
/* 1403 */           ax.getGridGc().setFillColor(Color.BLACK);
/*      */         }
/* 1405 */         if (ax.getGridGc().getLineColor() != null) {
/* 1406 */           map.put(item + "GridColor", 
/* 1407 */             ChartUtil.toString(ax.getGridGc().getLineColor()));
/*      */         }
/* 1409 */         map.put(item + "GridStyle", 
/* 1410 */           String.valueOf(ax.getGridGc().getLineStyle()));
/* 1411 */         map.put(item + "GridWidth", 
/* 1412 */           String.valueOf(ax.getGridGc().getLineWidth()));
/* 1413 */         if (ax.getAutoScale())
/* 1414 */           map.remove(item + "GridCount");
/*      */         else {
/* 1416 */           map.put(item + "GridCount", 
/* 1417 */             String.valueOf(ax.getNumGrids()));
/*      */         }
/*      */       }
/*      */ 
/* 1421 */       putThresholdLineValue(ax, item, map);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void putAxLabels(AxisInterface ax, String item, Map map)
/*      */   {
/* 1433 */     if ((ax != null) && 
/* 1434 */       (ax.getClass().equals(LabelAxis.class))) {
/* 1435 */       LabelAxis labelAx = (LabelAxis)ax;
/*      */ 
/* 1437 */       ArrayList labels = labelAx.getLabels();
/*      */ 
/* 1439 */       if (labels == null) {
/* 1440 */         return;
/*      */       }
/* 1442 */       StringBuffer str = new StringBuffer();
/*      */ 
/* 1444 */       for (int i = 0; i < labels.size(); i++) {
/* 1445 */         str.append(labels.get(i));
/* 1446 */         str.append(",");
/*      */       }
/*      */ 
/* 1449 */       map.put(item + "Labels", str.substring(0, str.length() - 1));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void putThresholdLineValue(AxisInterface ax, String item, Map map)
/*      */   {
/* 1461 */     if (((Axis)ax).getThresholdLines() == null) {
/* 1462 */       return;
/*      */     }
/* 1464 */     for (int i = 0; i < ((Axis)ax).getThresholdLines().size(); i++) {
/* 1465 */       String prefix = item + "ThresholdLine" + i;
/* 1466 */       ThresholdLine tl = 
/* 1467 */         (ThresholdLine)((Axis)ax).getThresholdLines()
/* 1467 */         .get(i);
/* 1468 */       if (tl != null) {
/* 1469 */         map.put(prefix + "Value", tl.getValue());
/*      */ 
/* 1471 */         if (tl.getGc().getLineColor() != null)
/* 1472 */           map.put(prefix + "Color", 
/* 1473 */             ChartUtil.toString(tl.getGc().getLineColor()));
/* 1474 */         map.put(prefix + "LineStyle", tl.getGc().getLineStyle());
/* 1475 */         if ((tl.getLabelString() != null) && 
/* 1476 */           (!tl.getLabelString().equals("")))
/* 1477 */           map.put(prefix + "LabelString", tl.getLabelString());
/*      */         else
/* 1479 */           map.put(prefix + "LabelString", "null");
/* 1480 */         if (tl.getLabelColor() != null)
/* 1481 */           map.put(prefix + "LabelColor", 
/* 1482 */             ChartUtil.toString(tl.getLabelColor()));
/* 1483 */         if (tl.getLabelFont() != null)
/* 1484 */           map.put(prefix + "LabelFont", 
/* 1485 */             ChartUtil.toString(tl.getLabelFont()));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void initDateAxis(DateAxis ax, Map map)
/*      */   {
/* 1494 */     if (ax == null) {
/* 1495 */       return;
/*      */     }
/*      */ 
/* 1504 */     if (ax.userScalingType != null) {
/* 1505 */       map.put("userScalingType", ax.userScalingType);
/* 1506 */       map.put("scalingType", String.valueOf(ax.getScalingType()));
/*      */     }
/*      */ 
/* 1509 */     map.put("axisTimeZone", String.valueOf(ax.getTimeZone()));
/*      */   }
/*      */ 
/*      */   public void getAxisOptions(AxisInterface ax, String s, Map map)
/*      */   {
/* 1514 */     StringBuffer str = new StringBuffer();
/* 1515 */     if (((Axis)ax).getSciLogScaling()) {
/* 1516 */       str.append("sciLogScaling,");
/*      */ 
/* 1518 */       if (!((Axis)ax).getSciLogUseMajorGrids()) {
/* 1519 */         str.append("sciLogMajorGridOff,");
/*      */       }
/*      */ 
/* 1522 */       if (!((Axis)ax).getSciLogUseMinorGrids()) {
/* 1523 */         str.append("sciLogMinorGridOff,");
/*      */       }
/*      */     }
/*      */ 
/* 1527 */     if (ax.getLogScaling()) {
/* 1528 */       str.append("logScaling,");
/*      */     }
/*      */ 
/* 1531 */     if (ax.getAutoScale())
/* 1532 */       str.append("autoScale,");
/*      */     else {
/* 1534 */       str.append("noAutoScale,");
/*      */     }
/*      */ 
/* 1537 */     if (ax.getLineVis())
/* 1538 */       str.append("lineOn,");
/*      */     else {
/* 1540 */       str.append("lineOff,");
/*      */     }
/*      */ 
/* 1543 */     if (ax.getLabelVis())
/* 1544 */       str.append("labelsOn,");
/*      */     else {
/* 1546 */       str.append("labelsOff,");
/*      */     }
/*      */ 
/* 1549 */     if (ax.getGridVis())
/* 1550 */       str.append("gridOn,");
/*      */     else {
/* 1552 */       str.append("gridOff,");
/*      */     }
/*      */ 
/* 1555 */     if (ax.getMajTickVis())
/* 1556 */       str.append("tickOn,");
/*      */     else {
/* 1558 */       str.append("tickOff,");
/*      */     }
/*      */ 
/* 1561 */     if (ax.getMinTickVis())
/* 1562 */       str.append("minTickOn,");
/*      */     else {
/* 1564 */       str.append("minTickOff,");
/*      */     }
/*      */ 
/* 1567 */     if (ax.getSide() == 3)
/* 1568 */       str.append("rightAxis,");
/* 1569 */     else if (ax.getSide() == 1)
/* 1570 */       str.append("leftAxis,");
/* 1571 */     else if (ax.getSide() == 2)
/* 1572 */       str.append("topAxis,");
/* 1573 */     else if (ax.getSide() == 4) {
/* 1574 */       str.append("bottomAxis,");
/*      */     }
/*      */ 
/* 1577 */     if (ax.isTitleRotated()) {
/* 1578 */       str.append("rotateTitle,");
/*      */     }
/*      */ 
/* 1581 */     if (ax.getLabelFormat() != null) {
/* 1582 */       if (ax.getLabelFormat().equals(
/* 1583 */         NumberFormat.getCurrencyInstance(getGlobals().locale)))
/* 1584 */         str.append("currencyLabels,");
/* 1585 */       else if (ax.getLabelFormat().equals(
/* 1586 */         NumberFormat.getPercentInstance(getGlobals().locale))) {
/* 1587 */         str.append("percentLabels,");
/*      */       }
/*      */     }
/*      */ 
/* 1591 */     map.put(s + "Options", str.substring(0, str.length() - 1));
/*      */   }
/*      */ 
/*      */   public void activateOutlineFills(Map map, boolean individualColors)
/*      */   {
/* 1599 */     for (int i = 0; i < getDatasets().length; i++)
/* 1600 */       if (getDatasets()[i] != null) {
/* 1601 */         Dataset dataset = getDatasets()[i];
/*      */ 
/* 1603 */         if (!individualColors) {
/* 1604 */           Gc gc = dataset.getGc();
/*      */ 
/* 1606 */           if (gc.getOutlineFills())
/*      */           {
/* 1608 */             map.put("OutlineColor", 
/* 1609 */               ChartUtil.toString(gc.getLineColor()));
/*      */           }
/*      */         }
/* 1612 */         else if (dataset.getGc().getOutlineFills()) {
/* 1613 */           for (int j = 0; j < dataset.getData().size(); j++) {
/* 1614 */             Gc gc = dataset.getDataElementAt(j).getGc();
/*      */ 
/* 1616 */             map.put("OutlineFills", "true");
/* 1617 */             map.put("OutlineColor", 
/* 1618 */               ChartUtil.toString(gc.getLineColor()));
/*      */           }
/*      */         }
/*      */       }
/*      */   }
/*      */ 
/*      */   public void putGc(String item, Gc gc, Map map)
/*      */   {
/* 1629 */     int style = gc.getFillStyle();
/*      */ 
/* 1631 */     if (gc.getTexture() == -1) {
/* 1632 */       if (gc.getImageBytes() != null)
/* 1633 */         map.put(item + "Image", 
/* 1634 */           JavachartUtil.edcodeBase64(gc.getImageBytes()));
/* 1635 */       map.put(item + "Texture", gc.getTexture());
/*      */     }
/* 1637 */     else if (style == -1) {
/* 1638 */       map.put(item + "Color", ChartUtil.toString(gc.getFillColor()));
/*      */     }
/* 1640 */     else if (style == 0) {
/* 1641 */       map.put(item + "Color", ChartUtil.toString(gc.getFillColor()));
/* 1642 */       map.put(item + "SecondaryColor", 
/* 1643 */         ChartUtil.toString(gc.getSecondaryFillColor()));
/* 1644 */       map.put(item + "Gradient", gc.getGradient());
/* 1645 */     } else if (style == 1)
/*      */     {
/* 1647 */       map.put(item + "LineStyle", gc.getLineStyle());
/* 1648 */       map.put(item + "LineWidth", gc.getLineWidth());
/*      */ 
/* 1650 */       map.put(item + "Color", ChartUtil.toString(gc.getFillColor()));
/* 1651 */       map.put(item + "SecondaryColor", 
/* 1652 */         ChartUtil.toString(gc.getSecondaryFillColor()));
/* 1653 */       map.put(item + "Texture", gc.getTexture());
/*      */     }
/*      */ 
/* 1658 */     map.put(item + "LineStyle", gc.getLineStyle());
/* 1659 */     map.put(item + "LineWidth", gc.getLineWidth());
/*      */ 
/* 1665 */     map.put(item + "Outlining", gc.getOutlineFills());
/*      */ 
/* 1668 */     map.put(item + "OutlineColor", ChartUtil.toString(gc.getLineColor()));
/*      */   }
/*      */ 
/*      */   public void putDatum(String item, Dataset dataset, Map map)
/*      */   {
/* 1675 */     ArrayList data = dataset.getData();
/*      */ 
/* 1677 */     StringBuffer sbLabel = new StringBuffer();
/* 1678 */     StringBuffer sbColor = new StringBuffer();
/* 1679 */     StringBuffer sbSecColor = new StringBuffer();
/* 1680 */     StringBuffer sbGrd = new StringBuffer();
/* 1681 */     StringBuffer sbTxt = new StringBuffer();
/* 1682 */     StringBuffer sbImg = new StringBuffer();
/* 1683 */     StringBuffer sbMkStyle = new StringBuffer();
/* 1684 */     StringBuffer sbMkSize = new StringBuffer();
/* 1685 */     StringBuffer sbOutLine = new StringBuffer();
/* 1686 */     StringBuffer sbOLColor = new StringBuffer();
/*      */ 
/* 1688 */     for (int i = 0; i < data.size(); i++) {
/* 1689 */       Datum datum = (Datum)data.get(i);
/* 1690 */       Gc gc = datum.getGc();
/*      */ 
/* 1692 */       if ((datum.label != null) && (!datum.label.equals("")))
/* 1693 */         sbLabel.append(datum.label + ",");
/*      */       else
/* 1695 */         sbLabel.append(" ,");
/* 1696 */       int style = gc.getFillStyle();
/* 1697 */       if (gc.getTexture() == -1) {
/* 1698 */         sbColor.append(" ,");
/* 1699 */         sbSecColor.append(" ,");
/* 1700 */         sbGrd.append(" ,");
/* 1701 */         sbTxt.append(gc.getTexture() + ",");
/* 1702 */         sbImg.append(JavachartUtil.edcodeBase64(gc.getImageBytes()) + 
/* 1703 */           ",");
/*      */       }
/* 1705 */       else if (style == -1) {
/* 1706 */         if (gc.getFillColor() != null) {
/* 1707 */           sbColor.append(ChartUtil.toString(gc.getFillColor()) + 
/* 1708 */             ",");
/* 1709 */           sbSecColor.append(" ,");
/* 1710 */           sbGrd.append(" ,");
/* 1711 */           sbTxt.append(" ,");
/* 1712 */           sbImg.append(" ,");
/*      */         }
/* 1714 */       } else if (style == 0) {
/* 1715 */         sbColor.append(ChartUtil.toString(gc.getFillColor()) + ",");
/* 1716 */         sbSecColor.append(ChartUtil.toString(gc
/* 1717 */           .getSecondaryFillColor()) + ",");
/* 1718 */         sbGrd.append(gc.getGradient() + ",");
/* 1719 */         sbTxt.append(" ,");
/* 1720 */         sbImg.append(" ,");
/* 1721 */       } else if (style == 1) {
/* 1722 */         sbColor.append(ChartUtil.toString(gc.getFillColor()) + ",");
/* 1723 */         sbSecColor.append(ChartUtil.toString(gc
/* 1724 */           .getSecondaryFillColor()) + ",");
/* 1725 */         sbGrd.append(" ,");
/* 1726 */         sbTxt.append(gc.getTexture() + ",");
/* 1727 */         sbImg.append(" ,");
/*      */       }
/*      */ 
/* 1731 */       sbMkStyle.append(gc.getMarkerStyle() + ",");
/* 1732 */       sbMkSize.append(gc.getMarkerSize() + ",");
/* 1733 */       sbOutLine.append(gc.getOutlineFills() + ",");
/* 1734 */       if (gc.outlineFills)
/* 1735 */         sbOLColor.append(ChartUtil.toString(gc.getLineColor()) + ",");
/*      */       else
/* 1737 */         sbOLColor.append(ChartUtil.toString(Color.WHITE) + ",");
/*      */     }
/* 1739 */     map.put(item + "Labels", sbLabel.substring(0, sbLabel.length() - 1));
/* 1740 */     map.put(item + "Colors", sbColor.substring(0, sbColor.length() - 1));
/* 1741 */     map.put(item + "SecondaryColors", 
/* 1742 */       sbSecColor.substring(0, sbSecColor.length() - 1));
/* 1743 */     map.put(item + "Gradients", sbGrd.substring(0, sbGrd.length() - 1));
/* 1744 */     map.put(item + "Textures", sbTxt.substring(0, sbTxt.length() - 1));
/* 1745 */     map.put(item + "Images", sbImg.substring(0, sbImg.length() - 1));
/* 1746 */     map.put(item + "MarkerStyles", 
/* 1747 */       sbMkStyle.substring(0, sbMkStyle.length() - 1));
/* 1748 */     map.put(item + "MarkerSizes", 
/* 1749 */       sbMkSize.substring(0, sbMkSize.length() - 1));
/* 1750 */     map.put(item + "OutLines", 
/* 1751 */       sbOutLine.substring(0, sbOutLine.length() - 1));
/* 1752 */     map.put(item + "OutLineColors", 
/* 1753 */       sbOLColor.substring(0, sbOLColor.length() - 1));
/*      */   }
/*      */ 
/*      */   static String toString(Font font) {
/* 1757 */     return font.getName() + "," + font.getSize() + "," + font.getStyle();
/*      */   }
/*      */ 
/*      */   public boolean isAntialiasing() {
/* 1761 */     return this.isAntialiasing;
/*      */   }
/*      */ 
/*      */   public void setAntialiasing(boolean isAntialiasing) {
/* 1765 */     this.isAntialiasing = isAntialiasing;
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart._Chart
 * JD-Core Version:    0.6.2
 */