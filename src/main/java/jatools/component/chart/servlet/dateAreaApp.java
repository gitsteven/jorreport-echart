/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.Area;
/*    */ import jatools.component.chart.chart.AreaChart;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.DAreaChart;
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class dateAreaApp extends DateApp
/*    */ {
/*    */   public dateAreaApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public dateAreaApp(Properties defaultProperties)
/*    */   {
/* 25 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions() {
/* 29 */     initDateAxis((DateAxis)this.chart.getXAxis());
/* 30 */     AreaChart a = (AreaChart)this.chart;
/*    */ 
/* 32 */     String str = getParameter("baseline");
/* 33 */     if (str != null)
/* 34 */       ((Area)a.getDataRepresentation()).setBaseline(Double.valueOf(str).doubleValue());
/* 35 */     str = getParameter("stackAreas");
/* 36 */     if (str != null) {
/* 37 */       if (str.equalsIgnoreCase("true")) {
/* 38 */         a.setStackAreas(true);
/*    */       }
/*    */       else {
/* 41 */         a.setStackAreas(false);
/*    */       }
/*    */     }
/* 44 */     str = getParameter("outlineColor");
/* 45 */     if (str != null)
/* 46 */       this.parser.activateOutlineFills(this.parser.getColor(str), false);
/*    */   }
/*    */ 
/*    */   public void init() {
/* 50 */     initLocale();
/* 51 */     this.chart = new DAreaChart(this.userLocale);
/* 52 */     initDateStreamReader();
/* 53 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.dateAreaApp
 * JD-Core Version:    0.6.2
 */