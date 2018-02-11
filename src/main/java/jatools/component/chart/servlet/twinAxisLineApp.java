/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.Line;
/*    */ import jatools.component.chart.chart.TwinAxisLineChart;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class twinAxisLineApp extends lineApp
/*    */ {
/*    */   public twinAxisLineApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public twinAxisLineApp(Properties defaultProperties)
/*    */   {
/* 20 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions() {
/* 24 */     super.getMyOptions();
/*    */ 
/* 28 */     TwinAxisLineChart l = (TwinAxisLineChart)this.chart;
/*    */ 
/* 30 */     String str = getParameter("plotLinesOn");
/* 31 */     if (str != null)
/* 32 */       l.setLineVisible(true);
/* 33 */     str = getParameter("plotLinesOff");
/* 34 */     if (str != null)
/* 35 */       l.setLineVisible(false);
/* 36 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++) {
/* 37 */       str = getParameter("dataset" + i + "onRight");
/* 38 */       if ((str != null) && 
/* 39 */         (str.equalsIgnoreCase("true"))) {
/* 40 */         l.assignToRightAxis(i, true);
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 45 */     for (int i = 0; i < 20; i++) {
/* 46 */       str = getParameter("dataset" + i + "Axis");
/* 47 */       if ((str != null) && 
/* 48 */         (str.equals("right"))) {
/* 49 */         l.assignToRightAxis(i, true);
/*    */       }
/*    */     }
/* 52 */     this.parser.parseAxOptions("auxAxis", l.getAuxAxis());
/*    */ 
/* 56 */     str = getParameter("auxPlotLinesOn");
/* 57 */     if (str != null)
/* 58 */       l.getAuxLine().setScatterPlot(true);
/* 59 */     str = getParameter("auxPlotLinesOff");
/* 60 */     if (str != null)
/* 61 */       l.getAuxLine().setScatterPlot(false);
/*    */   }
/*    */ 
/*    */   public void init() {
/* 65 */     initLocale();
/* 66 */     this.chart = new TwinAxisLineChart(this.userLocale);
/* 67 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.twinAxisLineApp
 * JD-Core Version:    0.6.2
 */