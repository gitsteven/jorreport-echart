/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.Bar;
/*    */ import jatools.component.chart.chart.IndColumnChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class indColumnApp extends Bean
/*    */ {
/*    */   public indColumnApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public indColumnApp(Properties defaultProperties)
/*    */   {
/* 22 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 27 */     IndColumnChart b = (IndColumnChart)this.chart;
/*    */ 
/* 29 */     String str = getParameter("barLabelsOn");
/* 30 */     if ((str != null) && 
/* 31 */       (str.indexOf("true") != -1))
/* 32 */       b.getBar().setLabelsOn(true);
/* 33 */     str = getParameter("barBaseline");
/* 34 */     if (str != null)
/* 35 */       b.getBar().setBaseline(Double.valueOf(str).doubleValue());
/* 36 */     str = getParameter("barClusterWidth");
/* 37 */     if (str != null)
/* 38 */       b.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/* 39 */     str = getParameter("barLabelAngle");
/* 40 */     if (str != null)
/* 41 */       b.getBar().setLabelAngle(Integer.parseInt(str));
/* 42 */     str = getParameter("useValueLabels");
/* 43 */     if (str != null)
/* 44 */       b.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/* 45 */     str = getParameter("outlineColor");
/* 46 */     if (str != null)
/* 47 */       this.parser.activateOutlineFills(this.parser.getColor(str), true); 
/*    */   }
/*    */ 
/* 50 */   public void init() { initLocale();
/* 51 */     this.chart = new IndColumnChart("My Chart", this.userLocale);
/* 52 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.indColumnApp
 * JD-Core Version:    0.6.2
 */