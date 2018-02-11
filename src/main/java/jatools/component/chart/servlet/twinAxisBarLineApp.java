/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.component.chart.applet.ParameterParser;
/*     */ import jatools.component.chart.chart.Bar;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.Line;
/*     */ import jatools.component.chart.chart.TwinAxisBarLineChart;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import java.text.Format;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class twinAxisBarLineApp extends barApp
/*     */ {
/*     */   public twinAxisBarLineApp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public twinAxisBarLineApp(Properties defaultProperties)
/*     */   {
/*  25 */     this.properties = new Properties(defaultProperties);
/*     */   }
/*     */ 
/*     */   public void getMyOptions()
/*     */   {
/*  31 */     TwinAxisBarLineChart thisChart = (TwinAxisBarLineChart)this.chart;
/*  32 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++) {
/*  33 */       String str = getParameter("dataset" + i + "Type");
/*  34 */       if (str != null) {
/*  35 */         if (str.equalsIgnoreCase("LINE")) {
/*  36 */           thisChart.setDatasetType(i, 1);
/*     */         }
/*  38 */         else if (str.equalsIgnoreCase("BAR")) {
/*  39 */           thisChart.setDatasetType(i, 0);
/*     */         }
/*     */       }
/*     */     }
/*  43 */     this.parser.parseAxOptions("auxAxis", thisChart.getAuxAxis());
/*     */ 
/*  46 */     String str = getParameter("barBaseline");
/*  47 */     if (str != null)
/*  48 */       thisChart.getBar().setBaseline(Double.valueOf(str).doubleValue());
/*  49 */     str = getParameter("barClusterWidth");
/*  50 */     if (str != null)
/*  51 */       thisChart.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/*  52 */     str = getParameter("barLabelsOn");
/*  53 */     if ((str != null) && 
/*  54 */       (!str.equalsIgnoreCase("false"))) {
/*  55 */       thisChart.getBar().setLabelsOn(true);
/*  56 */       thisChart.getLine().setLabelsOn(true);
/*     */     }
/*     */ 
/*  59 */     str = getParameter("barLabelAngle");
/*  60 */     if (str != null) {
/*  61 */       thisChart.getBar().setLabelAngle(Integer.parseInt(str));
/*  62 */       thisChart.getLine().setLabelAngle(Integer.parseInt(str));
/*     */     }
/*  64 */     str = getParameter("barLabelPrecision");
/*  65 */     if (str != null) {
/*  66 */       thisChart.getBar().setLabelPrecision(Integer.parseInt(str));
/*  67 */       thisChart.getLine().setLabelPrecision(Integer.parseInt(str));
/*     */     }
/*  69 */     str = getParameter("barLabelFormat");
/*  70 */     Format format = null;
/*  71 */     if (str != null) {
/*  72 */       if (str.equals("0"))
/*  73 */         format = NumberFormat.getNumberInstance();
/*  74 */       else if (str.equals("1"))
/*  75 */         format = NumberFormat.getPercentInstance();
/*  76 */       else if (str.equals("2")) {
/*  77 */         format = NumberFormat.getCurrencyInstance();
/*     */       }
/*  79 */       thisChart.getLine().setFormat(format);
/*  80 */       thisChart.getBar().setFormat(format);
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
/*  95 */     str = getParameter("useValueLabels");
/*  96 */     if (str != null) {
/*  97 */       thisChart.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/*  98 */       thisChart.getLine().setUseValueLabels(str.equalsIgnoreCase("true"));
/*     */     }
/* 100 */     str = getParameter("individualColors");
/* 101 */     if (str != null) {
/* 102 */       thisChart.setIndividualColors(Boolean.valueOf(str).booleanValue());
/*     */     }
/*     */ 
/* 105 */     str = getParameter("plotLinesOn");
/* 106 */     if (str != null)
/* 107 */       thisChart.setLineVisible(true);
/* 108 */     str = getParameter("plotLinesOff");
/* 109 */     if (str != null)
/* 110 */       thisChart.setLineVisible(false);
/* 111 */     str = getParameter("individualMarkers");
/* 112 */     if ((str != null) && 
/* 113 */       (str.equalsIgnoreCase("true"))) {
/* 114 */       thisChart.getLine().setIndividualMarkers(true);
/*     */     }
/*     */ 
/* 117 */     str = getParameter("errorBars");
/* 118 */     if (str != null)
/* 119 */       thisChart.getBar().setDoErrorBars(true);
/* 120 */     str = getParameter("outlineColor");
/* 121 */     if (str != null)
/*     */     {
/* 123 */       for (int j = 0; j < thisChart.getDatasets().length; j++) {
/* 124 */         String dataType = getParameter("dataset" + j + "Type");
/* 125 */         if ((dataType != null) && (dataType.equalsIgnoreCase("BAR")))
/* 126 */           this.parser.activateOutlineFills(thisChart.getDatasets()[j], 
/* 127 */             this.parser.getColor(str), 
/* 128 */             thisChart.getIndividualColors());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void init() {
/* 134 */     initLocale();
/* 135 */     this.chart = new TwinAxisBarLineChart(this.userLocale);
/* 136 */     getOptions();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.twinAxisBarLineApp
 * JD-Core Version:    0.6.2
 */