/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.CandlestickDateReader;
/*    */ import jatools.component.chart.chart.AxisInterface;
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import jatools.component.chart.chart.HLOC;
/*    */ import jatools.component.chart.chart.HLOCChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class hLOCApp extends DateApp
/*    */ {
/*    */   public hLOCApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public hLOCApp(Properties defaultProperties)
/*    */   {
/* 20 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions() {
/* 24 */     HLOCChart l = (HLOCChart)this.chart;
/* 25 */     initDateAxis((DateAxis)l.getXAxis());
/* 26 */     String str = getParameter("barClusterWidth");
/* 27 */     if (str != null)
/* 28 */       ((HLOC)l.getDataRepresentation()).setClusterWidth(Double.valueOf(str).doubleValue());
/* 29 */     str = getParameter("xAxisBarScaling");
/* 30 */     if (str != null)
/* 31 */       l.getXAxis().setBarScaling(str.equalsIgnoreCase("true")); 
/*    */   }
/*    */ 
/* 34 */   public void init() { initLocale();
/* 35 */     this.chart = new HLOCChart();
/* 36 */     this.parser = new CandlestickDateReader(this.chart, this);
/* 37 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.hLOCApp
 * JD-Core Version:    0.6.2
 */