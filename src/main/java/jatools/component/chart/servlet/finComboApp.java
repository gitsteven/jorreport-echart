/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.FinComboDateReader;
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import jatools.component.chart.chart.FinComboChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class finComboApp extends DateApp
/*    */ {
/*    */   public finComboApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public finComboApp(Properties defaultProperties)
/*    */   {
/* 25 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions() {
/* 29 */     FinComboChart c = (FinComboChart)this.chart;
/* 30 */     initDateAxis((DateAxis)c.getXAxis());
/*    */ 
/* 32 */     String str = getParameter("splitWindow");
/* 33 */     if ((str != null) && 
/* 34 */       (str.equalsIgnoreCase("false")))
/* 35 */       c.setSplitWindow(false);
/*    */   }
/*    */ 
/*    */   public void init() {
/* 39 */     initLocale();
/* 40 */     FinComboChart c = new FinComboChart(this.userLocale);
/* 41 */     this.chart = c;
/* 42 */     this.parser = new FinComboDateReader(this.chart, this);
/* 43 */     initDateStreamReader();
/* 44 */     for (int i = 0; i < 20; i++) {
/* 45 */       String str = getParameter("dataset" + i + "Type");
/* 46 */       if (str != null) {
/* 47 */         if (str.equals("Line"))
/* 48 */           c.dataAllocation[i] = 0;
/* 49 */         else if (str.equals("HLOC"))
/* 50 */           c.dataAllocation[i] = 2;
/* 51 */         else if (str.equals("Stick")) {
/* 52 */           c.dataAllocation[i] = 1;
/*    */         }
/*    */       }
/*    */     }
/* 56 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.finComboApp
 * JD-Core Version:    0.6.2
 */