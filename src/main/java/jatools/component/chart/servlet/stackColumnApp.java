/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.chart.Bar;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.StackColumnChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class stackColumnApp extends Bean
/*    */ {
/*    */   public stackColumnApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public stackColumnApp(Properties defaultProperties)
/*    */   {
/* 25 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 30 */     StackColumnChart b = (StackColumnChart)this.chart;
/*    */ 
/* 32 */     String str = getParameter("individualColors");
/* 33 */     if (str != null) {
/* 34 */       b.setIndividualColors(Boolean.valueOf(str).booleanValue());
/*    */     }
/*    */ 
/* 39 */     str = getParameter("barBaseline");
/* 40 */     if (str != null)
/* 41 */       b.getBar().setBaseline(Double.valueOf(str).doubleValue());
/* 42 */     str = getParameter("barClusterWidth");
/* 43 */     if (str != null) {
/* 44 */       b.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/*    */     }
/*    */ 
/* 51 */     str = getParameter("useValueLabels");
/* 52 */     if (str != null) {
/* 53 */       b.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/*    */     }
/*    */ 
/* 58 */     String strFont = getParameter("barLabelFont");
/* 59 */     String strColor = getParameter("barLabelColor");
/* 60 */     for (int i = 0; i < this.chart.getDatasets().length; i++)
/* 61 */       if (this.chart.getDatasets()[i] != null) {
/* 62 */         if (strFont != null) {
/* 63 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*    */         }
/* 65 */         if (strColor != null)
/* 66 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*    */       }
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 72 */     initLocale();
/* 73 */     this.chart = new StackColumnChart(this.userLocale);
/* 74 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.stackColumnApp
 * JD-Core Version:    0.6.2
 */