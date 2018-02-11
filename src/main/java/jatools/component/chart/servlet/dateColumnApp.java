/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.Bar;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import jatools.component.chart.chart.DateColumnChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class dateColumnApp extends DateApp
/*    */ {
/*    */   public dateColumnApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public dateColumnApp(Properties defaultProperties)
/*    */   {
/* 27 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 34 */     initDateAxis((DateAxis)this.chart.getXAxis());
/* 35 */     DateColumnChart b = (DateColumnChart)this.chart;
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
/* 50 */     if (str != null)
/* 51 */       b.setIndividualColors(str.equalsIgnoreCase("true"));
/* 52 */     str = getParameter("useValueLabels");
/* 53 */     if (str != null)
/* 54 */       b.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/* 55 */     str = getParameter("outlineColor");
/* 56 */     if (str != null)
/* 57 */       this.parser.activateOutlineFills(this.parser.getColor(str), b
/* 58 */         .getIndividualColors());
/* 59 */     String strFont = getParameter("barLabelFont");
/* 60 */     String strColor = getParameter("barLabelColor");
/* 61 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 62 */       if (strFont != null)
/* 63 */         this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/* 64 */       if (strColor != null)
/* 65 */         this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init() {
/* 70 */     initLocale();
/* 71 */     this.chart = new DateColumnChart("My Chart", this.userLocale);
/* 72 */     initDateStreamReader();
/* 73 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.dateColumnApp
 * JD-Core Version:    0.6.2
 */