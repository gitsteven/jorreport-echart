/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.component.chart.chart.Bar;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.DataRepresentation;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart.HiLoBarChart;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class hiLoBarApp extends Bean
/*     */ {
/*     */   public hiLoBarApp()
/*     */   {
/*     */   }
/*     */ 
/*     */   public hiLoBarApp(Properties defaultProperties)
/*     */   {
/*  23 */     this.properties = new Properties(defaultProperties);
/*     */   }
/*     */ 
/*     */   public void getMyOptions()
/*     */   {
/*  28 */     HiLoBarChart b = (HiLoBarChart)this.chart;
/*     */ 
/*  30 */     String str = getParameter("individualColors");
/*     */ 
/*  32 */     if (str != null) {
/*  33 */       b.setIndividualColors(Boolean.valueOf(str).booleanValue());
/*     */     }
/*     */ 
/*  36 */     str = getParameter("barBaseline");
/*     */ 
/*  38 */     if (str != null) {
/*  39 */       b.getBar().setBaseline(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/*  42 */     str = getParameter("barClusterWidth");
/*     */ 
/*  44 */     if (str != null) {
/*  45 */       b.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/*     */     }
/*     */ 
/*  48 */     str = getParameter("barLabelsOn");
/*     */ 
/*  50 */     if ((str != null) && 
/*  51 */       (!str.equalsIgnoreCase("false"))) {
/*  52 */       b.getBar().setLabelsOn(true);
/*     */     }
/*     */ 
/*  56 */     str = getParameter("barLabelAngle");
/*     */ 
/*  58 */     if (str != null) {
/*  59 */       b.getBar().setLabelAngle(Integer.parseInt(str));
/*     */     }
/*     */ 
/*  62 */     str = getParameter("useValueLabels");
/*     */ 
/*  64 */     if (str != null) {
/*  65 */       b.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/*     */     }
/*     */ 
/*  74 */     str = getParameter("labelPrecision");
/*     */ 
/*  76 */     if (str != null) {
/*  77 */       this.chart.getDataRepresentation().setLabelPrecision(Integer.parseInt(str));
/*     */     }
/*     */ 
/*  80 */     str = getParameter("barLabelFormat");
/*     */ 
/*  82 */     if (str != null)
/*     */     {
/*  84 */       this.chart.getDataRepresentation().setFormat(toLabelFormat(str));
/*     */     }
/*     */ 
/*  88 */     String strFont = getParameter("barLabelFont");
/*  89 */     String strColor = getParameter("barLabelColor");
/*  90 */     for (int i = 0; i < this.chart.getDatasets().length; i++)
/*  91 */       if (this.chart.getDatasets()[i] != null) {
/*  92 */         if (strFont != null) {
/*  93 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*     */         }
/*  95 */         if (strColor != null)
/*  96 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*     */       }
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 102 */     initLocale();
/* 103 */     this.chart = new HiLoBarChart("My Chart", this.userLocale);
/* 104 */     getOptions();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.hiLoBarApp
 * JD-Core Version:    0.6.2
 */