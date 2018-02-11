/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import jatools.component.chart.chart.DateLineChart;
/*    */ import jatools.component.chart.chart.Line;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class dateLineApp extends DateApp
/*    */ {
/*    */   public dateLineApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public dateLineApp(Properties defaultProperties)
/*    */   {
/* 25 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 32 */     DateLineChart l = (DateLineChart)this.chart;
/* 33 */     initDateAxis((DateAxis)l.getXAxis());
/*    */ 
/* 35 */     String str = getParameter("plotLinesOn");
/* 36 */     if (str != null)
/* 37 */       l.setLineVisible(true);
/* 38 */     str = getParameter("plotLinesOff");
/* 39 */     if (str != null)
/* 40 */       l.setLineVisible(false);
/* 41 */     str = getParameter("individualMarkers");
/* 42 */     if ((str != null) && 
/* 43 */       (str.equalsIgnoreCase("true"))) {
/* 44 */       l.getLine().setIndividualMarkers(true);
/*    */     }
/*    */ 
/* 48 */     str = getParameter("lineLabelsOn");
/* 49 */     if (str != null) {
/* 50 */       l.getLine().setLabelsOn(true);
/*    */     }
/*    */ 
/* 53 */     str = getParameter("useValueLabels");
/* 54 */     if (str != null) {
/* 55 */       l.getLine().setUseValueLabels(true);
/*    */     }
/*    */ 
/* 58 */     str = getParameter("plotLinesOff");
/* 59 */     if (str != null) {
/* 60 */       l.getLine().setScatterPlot(true);
/*    */     }
/*    */ 
/* 63 */     String strFont = getParameter("lineLabelFont");
/* 64 */     String strColor = getParameter("lineLabelColor");
/* 65 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 66 */       if (strFont != null)
/* 67 */         this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/* 68 */       if (strColor != null)
/* 69 */         this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init() {
/* 74 */     initLocale();
/* 75 */     this.chart = new DateLineChart(this.userLocale);
/* 76 */     initDateStreamReader();
/* 77 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.dateLineApp
 * JD-Core Version:    0.6.2
 */