/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.component.chart.applet.ParameterParser;
/*     */ import jatools.component.chart.chart.Area;
/*     */ import jatools.component.chart.chart.Bar;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.TwinAxisBarAreaChart;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class twinAxisBarAreaApp extends barApp
/*     */ {
/*     */   public twinAxisBarAreaApp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public twinAxisBarAreaApp(Properties defaultProperties)
/*     */   {
/*  26 */     this.properties = new Properties(defaultProperties);
/*     */   }
/*     */ 
/*     */   public void getMyOptions()
/*     */   {
/*  33 */     TwinAxisBarAreaChart thisChart = (TwinAxisBarAreaChart)this.chart;
/*     */ 
/*  35 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++) {
/*  36 */       String str = getParameter("dataset" + i + "Type");
/*  37 */       if (str != null) {
/*  38 */         if (str.equalsIgnoreCase("Area")) {
/*  39 */           thisChart.setDatasetType(i, 1);
/*     */         }
/*  41 */         else if (str.equalsIgnoreCase("Bar")) {
/*  42 */           thisChart.setDatasetType(i, 0);
/*     */         }
/*     */       }
/*     */     }
/*  46 */     this.parser.parseAxOptions("auxAxis", thisChart.getAuxAxis());
/*     */ 
/*  49 */     String str = getParameter("barBaseline");
/*  50 */     if (str != null)
/*  51 */       thisChart.getBar().setBaseline(Double.valueOf(str).doubleValue());
/*  52 */     str = getParameter("barClusterWidth");
/*  53 */     if (str != null)
/*  54 */       thisChart.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/*  55 */     str = getParameter("barLabelsOn");
/*  56 */     if ((str != null) && 
/*  57 */       (!str.equalsIgnoreCase("false")))
/*  58 */       thisChart.getBar().setLabelsOn(true);
/*  59 */     str = getParameter("barLabelAngle");
/*  60 */     if (str != null)
/*  61 */       thisChart.getBar().setLabelAngle(Integer.parseInt(str));
/*  62 */     str = getParameter("barLabelPrecision");
/*  63 */     if (str != null)
/*  64 */       thisChart.getBar().setLabelPrecision(Integer.parseInt(str));
/*  65 */     str = getParameter("useValueLabels");
/*  66 */     if (str != null)
/*  67 */       thisChart.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/*  68 */     str = getParameter("individualColors");
/*  69 */     if (str != null)
/*  70 */       thisChart.setIndividualColors(Boolean.valueOf(str).booleanValue());
/*  71 */     str = getParameter("barLabelFormat");
/*     */ 
/*  73 */     if (str != null) {
/*  74 */       if (str.equals("0"))
/*  75 */         thisChart.getBar().setFormat(NumberFormat.getNumberInstance());
/*  76 */       else if (str.equals("1"))
/*  77 */         thisChart.getBar().setFormat(NumberFormat.getPercentInstance());
/*  78 */       else if (str.equals("2")) {
/*  79 */         thisChart.getBar().setFormat(NumberFormat.getCurrencyInstance());
/*     */       }
/*     */     }
/*     */ 
/*  83 */     String strFont = getParameter("barLabelFont");
/*  84 */     String strColor = getParameter("barLabelColor");
/*  85 */     for (int i = 0; i < this.chart.getDatasets().length; i++) {
/*  86 */       if (this.chart.getDatasets()[i] != null) {
/*  87 */         if (strFont != null) {
/*  88 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*     */         }
/*  90 */         if (strColor != null) {
/*  91 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  96 */     str = getParameter("barBaseline");
/*  97 */     if (str != null) {
/*  98 */       thisChart.getArea().setBaseline(Double.valueOf(str).doubleValue());
/*  99 */       thisChart.getBar().setBaseline(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/* 102 */     str = getParameter("stackedArea");
/* 103 */     if (str != null)
/* 104 */       if (str.equals("true"))
/* 105 */         thisChart.getArea().setStackAreas(true);
/*     */       else
/* 107 */         thisChart.getArea().setStackAreas(false);
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 117 */     initLocale();
/* 118 */     this.chart = new TwinAxisBarAreaChart(this.userLocale);
/* 119 */     getOptions();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.twinAxisBarAreaApp
 * JD-Core Version:    0.6.2
 */