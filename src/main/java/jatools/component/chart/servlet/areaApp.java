/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.Area;
/*    */ import jatools.component.chart.chart.AreaChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class areaApp extends Bean
/*    */ {
/*    */   public areaApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public areaApp(Properties defaultProperties)
/*    */   {
/* 22 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions() {
/* 26 */     AreaChart a = (AreaChart)this.chart;
/*    */ 
/* 28 */     String str = getParameter("baseline");
/* 29 */     if (str != null)
/* 30 */       ((Area)a.getDataRepresentation()).setBaseline(Double.valueOf(str).doubleValue());
/* 31 */     str = getParameter("stackAreas");
/* 32 */     if (str != null) {
/* 33 */       if (str.equalsIgnoreCase("true")) {
/* 34 */         a.setStackAreas(true);
/*    */       }
/*    */       else {
/* 37 */         a.setStackAreas(false);
/*    */       }
/*    */     }
/* 40 */     str = getParameter("outlineColor");
/* 41 */     if (str != null)
/* 42 */       this.parser.activateOutlineFills(this.parser.getColor(str), false);
/*    */   }
/*    */ 
/*    */   public void init() {
/* 46 */     initLocale();
/* 47 */     this.chart = new AreaChart(this.userLocale);
/* 48 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.areaApp
 * JD-Core Version:    0.6.2
 */