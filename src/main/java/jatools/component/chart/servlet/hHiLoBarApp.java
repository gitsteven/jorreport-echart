/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.DataRepresentation;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.HorizHiLoBar;
/*     */ import jatools.component.chart.chart.HorizHiLoBarChart;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class hHiLoBarApp extends Bean
/*     */ {
/*     */   public hHiLoBarApp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public hHiLoBarApp(Properties defaultProperties)
/*     */   {
/*  27 */     this.properties = new Properties(defaultProperties);
/*     */   }
/*     */ 
/*     */   public void getMyOptions()
/*     */   {
/*  33 */     HorizHiLoBarChart b = (HorizHiLoBarChart)this.chart;
/*  34 */     HorizHiLoBar bar = (HorizHiLoBar)b.getBar();
/*     */ 
/*  39 */     String str = getParameter("individualColors");
/*     */ 
/*  41 */     if (str != null) {
/*  42 */       b.setIndividualColors(Boolean.valueOf(str).booleanValue());
/*     */     }
/*     */ 
/*  45 */     str = getParameter("barBaseline");
/*     */ 
/*  47 */     if (str != null) {
/*  48 */       bar.setBaseline(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/*  51 */     str = getParameter("barClusterWidth");
/*     */ 
/*  53 */     if (str != null) {
/*  54 */       bar.setClusterWidth(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/*  57 */     str = getParameter("barLabelsOn");
/*     */ 
/*  59 */     if ((str != null) && 
/*  60 */       (!str.equalsIgnoreCase("false"))) {
/*  61 */       bar.setLabelsOn(true);
/*     */     }
/*     */ 
/*  65 */     str = getParameter("barLabelAngle");
/*     */ 
/*  67 */     if (str != null) {
/*  68 */       bar.setLabelAngle(Integer.parseInt(str));
/*     */     }
/*     */ 
/*  71 */     str = getParameter("useValueLabels");
/*     */ 
/*  73 */     if (str != null) {
/*  74 */       bar.setUseValueLabels(str.equalsIgnoreCase("true"));
/*     */     }
/*     */ 
/*  84 */     str = getParameter("barLabelPrecision");
/*     */ 
/*  86 */     if (str != null) {
/*  87 */       this.chart.getDataRepresentation().setLabelPrecision(Integer.parseInt(str));
/*     */     }
/*     */ 
/*  90 */     str = getParameter("barLabelFormat");
/*     */ 
/*  92 */     if (str != null)
/*     */     {
/*  94 */       this.chart.getDataRepresentation().setFormat(toLabelFormat(str));
/*     */     }
/*     */ 
/*  98 */     String strFont = getParameter("barLabelFont");
/*  99 */     String strColor = getParameter("barLabelColor");
/* 100 */     for (int i = 0; i < this.chart.getDatasets().length; i++)
/* 101 */       if (this.chart.getDatasets()[i] != null) {
/* 102 */         if (strFont != null) {
/* 103 */           this.chart.getDatasets()[i].setLabelFont(
/* 104 */             ChartUtil.getFont(strFont));
/*     */         }
/* 106 */         if (strColor != null)
/* 107 */           this.chart.getDatasets()[i].setLabelColor(
/* 108 */             ChartUtil.getColor(strColor));
/*     */       }
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 115 */     initLocale();
/* 116 */     this.chart = new HorizHiLoBarChart("My Chart", this.userLocale);
/* 117 */     getOptions();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.hHiLoBarApp
 * JD-Core Version:    0.6.2
 */