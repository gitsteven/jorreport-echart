/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.DataRepresentation;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.Line;
/*    */ import jatools.component.chart.chart.LineChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class lineApp extends Bean
/*    */ {
/*    */   public lineApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public lineApp(Properties defaultProperties)
/*    */   {
/* 24 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 30 */     LineChart l = (LineChart)this.chart;
/*    */ 
/* 33 */     l.getLine().setScatterPlot(false);
/* 34 */     String str = getParameter("plotLinesOff");
/* 35 */     if (str != null)
/*    */     {
/* 37 */       l.getLine().setScatterPlot(true);
/*    */     }
/* 39 */     str = getParameter("individualMarkers");
/* 40 */     if ((str != null) && 
/* 41 */       (str.equalsIgnoreCase("true"))) {
/* 42 */       l.getLine().setIndividualMarkers(true);
/*    */     }
/*    */ 
/* 46 */     str = getParameter("lineLabelsOn");
/*    */ 
/* 48 */     if ((str != null) && 
/* 49 */       (!str.equalsIgnoreCase("false"))) {
/* 50 */       l.getLine().setLabelsOn(true);
/*    */     }
/*    */ 
/* 54 */     str = getParameter("lineLabelAngle");
/*    */ 
/* 56 */     if (str != null) {
/* 57 */       l.getLine().setLabelAngle(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 60 */     str = getParameter("useValueLabels");
/*    */ 
/* 62 */     if (str != null) {
/* 63 */       l.getLine().setUseValueLabels(str.equalsIgnoreCase("true"));
/*    */     }
/*    */ 
/* 66 */     str = getParameter("labelPrecision");
/*    */ 
/* 68 */     if (str != null) {
/* 69 */       this.chart.getDataRepresentation().setLabelPrecision(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 72 */     str = getParameter("lineLabelFormat");
/*    */ 
/* 74 */     if (str != null) {
/* 75 */       this.chart.getDataRepresentation().setFormat(toLabelFormat(str));
/*    */     }
/*    */ 
/* 79 */     String strFont = getParameter("lineLabelFont");
/* 80 */     String strColor = getParameter("lineLabelColor");
/* 81 */     for (int i = 0; i < this.chart.getDatasets().length; i++)
/* 82 */       if (this.chart.getDatasets()[i] != null) {
/* 83 */         if (strFont != null) {
/* 84 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*    */         }
/* 86 */         if (strColor != null)
/* 87 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*    */       }
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 93 */     initLocale();
/* 94 */     this.chart = new LineChart("My Chart", this.userLocale);
/* 95 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.lineApp
 * JD-Core Version:    0.6.2
 */