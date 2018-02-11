/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.chart.Bar;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.StackBarChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class stackBarApp extends Bean
/*    */ {
/*    */   public stackBarApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public stackBarApp(Properties defaultProperties)
/*    */   {
/* 23 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 28 */     StackBarChart b = (StackBarChart)this.chart;
/*    */ 
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
/* 53 */     String strFont = getParameter("barLabelFont");
/* 54 */     String strColor = getParameter("barLabelColor");
/* 55 */     for (int i = 0; i < this.chart.getDatasets().length; i++)
/* 56 */       if (this.chart.getDatasets()[i] != null) {
/* 57 */         if (strFont != null) {
/* 58 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*    */         }
/* 60 */         if (strColor != null)
/* 61 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*    */       }
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 67 */     initLocale();
/* 68 */     this.chart = new StackBarChart(this.userLocale);
/* 69 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.stackBarApp
 * JD-Core Version:    0.6.2
 */