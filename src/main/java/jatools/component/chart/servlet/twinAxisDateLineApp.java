/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.Line;
/*    */ import jatools.component.chart.chart.TwinAxisDateLineChart;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import java.awt.Graphics;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class twinAxisDateLineApp extends dateLineApp
/*    */ {
/*    */   public twinAxisDateLineApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public twinAxisDateLineApp(Properties defaultProperties)
/*    */   {
/* 20 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void drawMyStuff(Graphics g)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 33 */     super.getMyOptions();
/* 34 */     String str = null;
/* 35 */     TwinAxisDateLineChart thisChart = (TwinAxisDateLineChart)this.chart;
/* 36 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++) {
/* 37 */       str = getParameter("dataset" + i + "onRight");
/* 38 */       if ((str != null) && 
/* 39 */         (str.equalsIgnoreCase("true"))) {
/* 40 */         thisChart.assignToRightAxis(i, true);
/*    */       }
/*    */     }
/*    */ 
/* 44 */     this.parser.parseAxOptions("auxAxis", thisChart.getAuxAxis());
/*    */ 
/* 48 */     str = getParameter("auxPlotLinesOn");
/* 49 */     if (str != null)
/* 50 */       thisChart.getAuxLine().setScatterPlot(true);
/* 51 */     str = getParameter("auxPlotLinesOff");
/* 52 */     if (str != null)
/* 53 */       thisChart.getAuxLine().setScatterPlot(false);
/*    */   }
/*    */ 
/*    */   public void init() {
/* 57 */     initLocale();
/* 58 */     this.chart = new TwinAxisDateLineChart(this.userLocale);
/*    */ 
/* 61 */     initDateStreamReader();
/* 62 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.twinAxisDateLineApp
 * JD-Core Version:    0.6.2
 */