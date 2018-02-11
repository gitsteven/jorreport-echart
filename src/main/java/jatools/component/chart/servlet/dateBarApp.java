/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.Bar;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import jatools.component.chart.chart.DateBarChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class dateBarApp extends DateApp
/*    */ {
/*    */   public dateBarApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public dateBarApp(Properties defaultProperties)
/*    */   {
/* 27 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 34 */     initDateAxis((DateAxis)this.chart.getXAxis());
/* 35 */     DateBarChart b = (DateBarChart)this.chart;
/* 36 */     String str = getParameter("barLabelsOn");
/* 37 */     if ((str != null) && 
/* 38 */       (str.indexOf("true") != -1))
/* 39 */       b.getBar().setLabelsOn(true);
/* 40 */     str = getParameter("barBaseline");
/* 41 */     if (str != null)
/* 42 */       b.getBar().setBaseline(Double.valueOf(str).doubleValue());
/* 43 */     str = getParameter("barClusterWidth");
/* 44 */     if (str != null)
/* 45 */       b.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/* 46 */     str = getParameter("barLabelAngle");
/* 47 */     if (str != null)
/* 48 */       b.getBar().setLabelAngle(Integer.parseInt(str));
/* 49 */     str = getParameter("individualColors");
/* 50 */     if (str != null) {
/* 51 */       b.setIndividualColors(str.equalsIgnoreCase("true"));
/*    */     }
/* 53 */     str = getParameter("useValueLabels");
/* 54 */     if (str != null)
/* 55 */       b.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/* 56 */     str = getParameter("outlineColor");
/* 57 */     if (str != null)
/* 58 */       this.parser.activateOutlineFills(this.parser.getColor(str), b
/* 59 */         .getIndividualColors());
/* 60 */     String strFont = getParameter("barLabelFont");
/* 61 */     String strColor = getParameter("barLabelColor");
/* 62 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 63 */       if (strFont != null)
/* 64 */         this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/* 65 */       if (strColor != null)
/* 66 */         this.chart.getDatasets()[i].setLabelColor(
/* 67 */           ChartUtil.getColor(strColor));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init() {
/* 72 */     initLocale();
/* 73 */     this.chart = new DateBarChart("My Chart", this.userLocale);
/* 74 */     initDateStreamReader();
/* 75 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.dateBarApp
 * JD-Core Version:    0.6.2
 */