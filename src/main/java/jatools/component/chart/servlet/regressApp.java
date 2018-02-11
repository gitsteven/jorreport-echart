/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.DataRepresentation;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.Line;
/*    */ import jatools.component.chart.chart.RegressChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class regressApp extends Bean
/*    */ {
/*    */   public regressApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public regressApp(Properties defaultProperties)
/*    */   {
/* 23 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 29 */     RegressChart l = (RegressChart)this.chart;
/*    */ 
/* 31 */     l.getLine().setScatterPlot(false);
/* 32 */     String str = getParameter("plotLinesOff");
/* 33 */     if (str != null)
/*    */     {
/* 35 */       l.getLine().setScatterPlot(true);
/*    */     }
/* 37 */     str = getParameter("individualMarkers");
/* 38 */     if ((str != null) && 
/* 39 */       (str.equalsIgnoreCase("true"))) {
/* 40 */       l.getLine().setIndividualMarkers(true);
/*    */     }
/*    */ 
/* 44 */     str = getParameter("lineLabelsOn");
/*    */ 
/* 46 */     if ((str != null) && 
/* 47 */       (!str.equalsIgnoreCase("false"))) {
/* 48 */       l.getLine().setLabelsOn(true);
/*    */     }
/*    */ 
/* 52 */     str = getParameter("lineLabelAngle");
/*    */ 
/* 54 */     if (str != null) {
/* 55 */       l.getLine().setLabelAngle(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 58 */     str = getParameter("useValueLabels");
/*    */ 
/* 60 */     if (str != null) {
/* 61 */       l.getLine().setUseValueLabels(str.equalsIgnoreCase("true"));
/*    */     }
/*    */ 
/* 64 */     str = getParameter("labelPrecision");
/*    */ 
/* 66 */     if (str != null) {
/* 67 */       this.chart.getDataRepresentation().setLabelPrecision(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 70 */     str = getParameter("lineLabelFormat");
/*    */ 
/* 72 */     if (str != null) {
/* 73 */       this.chart.getDataRepresentation().setLabelFormat(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 77 */     String strFont = getParameter("lineLabelFont");
/* 78 */     String strColor = getParameter("lineLabelColor");
/* 79 */     for (int i = 0; i < this.chart.getDatasets().length; i++)
/* 80 */       if (this.chart.getDatasets()[i] != null) {
/* 81 */         if (strFont != null) {
/* 82 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*    */         }
/* 84 */         if (strColor != null)
/* 85 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*    */       }
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 91 */     initLocale();
/* 92 */     this.chart = new RegressChart(this.userLocale);
/* 93 */     ((RegressChart)this.chart).getLine().setClip(true);
/* 94 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.regressApp
 * JD-Core Version:    0.6.2
 */