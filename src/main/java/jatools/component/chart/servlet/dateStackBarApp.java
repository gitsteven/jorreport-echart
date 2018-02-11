/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.Bar;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import jatools.component.chart.chart.DateStackBarChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class dateStackBarApp extends DateApp
/*    */ {
/*    */   public dateStackBarApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public dateStackBarApp(Properties defaultProperties)
/*    */   {
/* 25 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 31 */     initDateAxis((DateAxis)this.chart.getXAxis());
/* 32 */     DateStackBarChart b = (DateStackBarChart)this.chart;
/* 33 */     String str = getParameter("barLabelsOn");
/* 34 */     if ((str != null) && 
/* 35 */       (str.indexOf("true") != -1))
/* 36 */       b.getBar().setLabelsOn(true);
/* 37 */     str = getParameter("barBaseline");
/* 38 */     if (str != null)
/* 39 */       b.getBar().setBaseline(Double.valueOf(str).doubleValue());
/* 40 */     str = getParameter("barClusterWidth");
/* 41 */     if (str != null)
/* 42 */       b.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/* 43 */     str = getParameter("barLabelAngle");
/* 44 */     if (str != null)
/* 45 */       b.getBar().setLabelAngle(Integer.parseInt(str));
/* 46 */     str = getParameter("useValueLabels");
/* 47 */     if (str != null)
/* 48 */       b.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/* 49 */     str = getParameter("individualColors");
/* 50 */     if (str != null)
/* 51 */       b.setIndividualColors(str.equalsIgnoreCase("true"));
/* 52 */     str = getParameter("outlineColor");
/* 53 */     if (str != null)
/* 54 */       this.parser.activateOutlineFills(this.parser.getColor(str), b.getIndividualColors());
/* 55 */     String strFont = getParameter("barLabelFont");
/* 56 */     String strColor = getParameter("barLabelColor");
/* 57 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 58 */       if (strFont != null)
/* 59 */         this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/* 60 */       if (strColor != null)
/* 61 */         this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor)); 
/*    */     }
/*    */   }
/*    */ 
/* 65 */   public void init() { initLocale();
/* 66 */     this.chart = new DateStackBarChart("My Chart", this.userLocale);
/* 67 */     initDateStreamReader();
/* 68 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.dateStackBarApp
 * JD-Core Version:    0.6.2
 */