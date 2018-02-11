/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import jatools.component.chart.chart.Stick;
/*    */ import jatools.component.chart.chart.StickChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class stickApp extends DateApp
/*    */ {
/*    */   public stickApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public stickApp(Properties defaultProperties)
/*    */   {
/* 25 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 32 */     StickChart b = (StickChart)this.chart;
/* 33 */     initDateAxis((DateAxis)b.getXAxis());
/*    */ 
/* 35 */     String str = getParameter("barBaseline");
/* 36 */     if (str != null)
/* 37 */       b.getStick().setBaseline(Double.valueOf(str).doubleValue());
/* 38 */     str = getParameter("barWidth");
/* 39 */     if (str != null)
/* 40 */       b.getStick().setWidth(Integer.parseInt(str)); 
/*    */   }
/*    */ 
/* 43 */   public void init() { initLocale();
/* 44 */     this.chart = new StickChart(this.userLocale);
/* 45 */     initDateStreamReader();
/* 46 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.stickApp
 * JD-Core Version:    0.6.2
 */