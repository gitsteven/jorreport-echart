/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.DataRepresentation;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.Polar;
/*    */ import jatools.component.chart.chart.PolarAxis;
/*    */ import jatools.component.chart.chart.PolarChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class polarApp extends Bean
/*    */ {
/*    */   public polarApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public polarApp(Properties defaultProperties)
/*    */   {
/* 24 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 31 */     PolarChart polar = (PolarChart)this.chart;
/*    */ 
/* 33 */     String str = getParameter("plotLinesOn");
/* 34 */     if (str != null)
/* 35 */       polar.setLineVisible(true);
/* 36 */     str = getParameter("plotLinesOff");
/* 37 */     if (str != null)
/* 38 */       polar.setLineVisible(false);
/* 39 */     PolarAxis p = (PolarAxis)polar.getYAxis();
/* 40 */     str = getParameter("manualSpoking");
/* 41 */     if (str != null)
/* 42 */       p.setManualSpoking(true);
/* 43 */     str = getParameter("numSpokes");
/* 44 */     if (str != null) {
/* 45 */       p.setNumSpokes(Integer.parseInt(str));
/*    */     }
/* 47 */     str = getParameter("polarLabelsOn");
/*    */ 
/* 49 */     if ((str != null) && 
/* 50 */       (!str.equalsIgnoreCase("false"))) {
/* 51 */       polar.getPolar().setLabelsOn(true);
/*    */     }
/*    */ 
/* 55 */     str = getParameter("polarLabelAngle");
/*    */ 
/* 57 */     if (str != null) {
/* 58 */       polar.getPolar().setLabelAngle(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 61 */     str = getParameter("labelPrecision");
/*    */ 
/* 63 */     if (str != null) {
/* 64 */       this.chart.getDataRepresentation().setLabelPrecision(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 67 */     str = getParameter("polarLabelFormat");
/*    */ 
/* 69 */     if (str != null) {
/* 70 */       this.chart.getDataRepresentation().setLabelFormat(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 74 */     String strFont = getParameter("polarLabelFont");
/* 75 */     String strColor = getParameter("polarLabelColor");
/* 76 */     for (int i = 0; i < this.chart.getDatasets().length; i++)
/* 77 */       if (this.chart.getDatasets()[i] != null) {
/* 78 */         if (strFont != null) {
/* 79 */           this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/*    */         }
/* 81 */         if (strColor != null)
/* 82 */           this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*    */       }
/*    */   }
/*    */ 
/*    */   public void init()
/*    */   {
/* 88 */     initLocale();
/* 89 */     this.chart = new PolarChart(this.userLocale);
/* 90 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.polarApp
 * JD-Core Version:    0.6.2
 */