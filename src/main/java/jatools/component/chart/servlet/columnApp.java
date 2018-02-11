/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.chart.Bar;
/*    */ import jatools.component.chart.chart.BarChart;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class columnApp extends Bean
/*    */ {
/*    */   public columnApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public columnApp(Properties defaultProperties)
/*    */   {
/* 23 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 29 */     BarChart b = (BarChart)this.chart;
/* 30 */     String str = getParameter("individualColors");
/* 31 */     if (str != null) {
/* 32 */       b.setIndividualColors(Boolean.valueOf(str).booleanValue());
/*    */     }
/*    */ 
/* 37 */     str = getParameter("barBaseline");
/* 38 */     if (str != null)
/* 39 */       b.getBar().setBaseline(Double.valueOf(str).doubleValue());
/* 40 */     str = getParameter("barClusterWidth");
/* 41 */     if (str != null) {
/* 42 */       b.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/*    */     }
/*    */ 
/* 46 */     str = getParameter("useValueLabels");
/* 47 */     if (str != null) {
/* 48 */       b.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/*    */     }
/*    */ 
/* 53 */     str = getParameter("errorBars");
/* 54 */     if (str != null) {
/* 55 */       b.getBar().setDoErrorBars(true);
/*    */     }
/*    */ 
/* 70 */     String strFont = getParameter("barLabelFont");
/* 71 */     String strColor = getParameter("barLabelColor");
/* 72 */     for (int i = 0; i < this.chart.getDatasets().length; i++)
/* 73 */       if (this.chart.getDatasets()[i] != null) {
/* 74 */         if (strFont != null) {
/* 75 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*    */         }
/* 77 */         if (strColor != null)
/* 78 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*    */       }
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 84 */     initLocale();
/* 85 */     this.chart = new BarChart("My Chart", this.userLocale);
/* 86 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.columnApp
 * JD-Core Version:    0.6.2
 */