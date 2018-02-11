/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.CandlestickDateReader;
/*    */ import jatools.component.chart.chart.AxisInterface;
/*    */ import jatools.component.chart.chart.Candlestick;
/*    */ import jatools.component.chart.chart.CandlestickChart;
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class candlestickApp extends DateApp
/*    */ {
/*    */   public candlestickApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public candlestickApp(Properties defaultProperties)
/*    */   {
/* 23 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions() {
/* 27 */     CandlestickChart l = (CandlestickChart)this.chart;
/* 28 */     initDateAxis((DateAxis)l.getXAxis());
/*    */ 
/* 30 */     String str = getParameter("barClusterWidth");
/* 31 */     if (str != null)
/* 32 */       l.getCandlestick().setClusterWidth(Double.valueOf(str).doubleValue());
/* 33 */     str = getParameter("xAxisBarScaling");
/* 34 */     if (str != null)
/* 35 */       l.getXAxis().setBarScaling(str.equalsIgnoreCase("true"));
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 40 */     initLocale();
/* 41 */     this.chart = new CandlestickChart();
/* 42 */     this.parser = new CandlestickDateReader(this.chart, this);
/* 43 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.candlestickApp
 * JD-Core Version:    0.6.2
 */