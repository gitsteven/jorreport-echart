/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.DataRepresentation;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.LabelLineChart;
/*    */ import jatools.component.chart.chart.Line;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class labelLineApp extends Bean
/*    */ {
/*    */   public labelLineApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public labelLineApp(Properties defaultProperties)
/*    */   {
/* 22 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 28 */     LabelLineChart l = (LabelLineChart)this.chart;
/*    */ 
/* 30 */     l.getLine().setScatterPlot(false);
/* 31 */     String str = getParameter("plotLinesOff");
/* 32 */     if (str != null)
/*    */     {
/* 34 */       l.getLine().setScatterPlot(true);
/*    */     }
/* 36 */     str = getParameter("individualMarkers");
/* 37 */     if ((str != null) && 
/* 38 */       (str.equalsIgnoreCase("true"))) {
/* 39 */       l.getLine().setIndividualMarkers(true);
/*    */     }
/*    */ 
/* 43 */     str = getParameter("lineLabelsOn");
/*    */ 
/* 45 */     if ((str != null) && 
/* 46 */       (!str.equalsIgnoreCase("false"))) {
/* 47 */       l.getLine().setLabelsOn(true);
/*    */     }
/*    */ 
/* 51 */     str = getParameter("lineLabelAngle");
/*    */ 
/* 53 */     if (str != null) {
/* 54 */       l.getLine().setLabelAngle(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 57 */     str = getParameter("useValueLabels");
/*    */ 
/* 59 */     if (str != null) {
/* 60 */       l.getLine().setUseValueLabels(str.equalsIgnoreCase("true"));
/*    */     }
/*    */ 
/* 63 */     str = getParameter("labelPrecision");
/*    */ 
/* 65 */     if (str != null) {
/* 66 */       this.chart.getDataRepresentation().setLabelPrecision(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 69 */     str = getParameter("lineLabelFormat");
/*    */ 
/* 71 */     if (str != null) {
/* 72 */       this.chart.getDataRepresentation().setLabelFormat(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 76 */     String strFont = getParameter("lineLabelFont");
/* 77 */     String strColor = getParameter("lineLabelColor");
/* 78 */     for (int i = 0; i < this.chart.getDatasets().length; i++)
/* 79 */       if (this.chart.getDatasets()[i] != null) {
/* 80 */         if (strFont != null) {
/* 81 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*    */         }
/* 83 */         if (strColor != null)
/* 84 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*    */       }
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 90 */     initLocale();
/* 91 */     this.chart = new LabelLineChart(this.userLocale);
/* 92 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.labelLineApp
 * JD-Core Version:    0.6.2
 */