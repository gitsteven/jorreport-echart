/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.Bar;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.DateAxis;
/*    */ import jatools.component.chart.chart.DateStackColumnChart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class dateStackColumnApp extends DateApp
/*    */ {
/*    */   public dateStackColumnApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public dateStackColumnApp(Properties defaultProperties)
/*    */   {
/* 21 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 28 */     initDateAxis((DateAxis)this.chart.getXAxis());
/* 29 */     DateStackColumnChart b = (DateStackColumnChart)this.chart;
/* 30 */     String str = getParameter("barLabelsOn");
/* 31 */     if ((str != null) && 
/* 32 */       (str.indexOf("true") != -1))
/* 33 */       b.getBar().setLabelsOn(true);
/* 34 */     str = getParameter("barBaseline");
/* 35 */     if (str != null)
/* 36 */       b.getBar().setBaseline(Double.valueOf(str).doubleValue());
/* 37 */     str = getParameter("barClusterWidth");
/* 38 */     if (str != null)
/* 39 */       b.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/* 40 */     str = getParameter("barLabelAngle");
/* 41 */     if (str != null)
/* 42 */       b.getBar().setLabelAngle(Integer.parseInt(str));
/* 43 */     str = getParameter("useValueLabels");
/* 44 */     if (str != null)
/* 45 */       b.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/* 46 */     str = getParameter("individualColors");
/* 47 */     if (str != null)
/* 48 */       b.setIndividualColors(str.equalsIgnoreCase("true"));
/* 49 */     str = getParameter("outlineColor");
/* 50 */     if (str != null)
/* 51 */       this.parser.activateOutlineFills(this.parser.getColor(str), b
/* 52 */         .getIndividualColors());
/* 53 */     String strFont = getParameter("barLabelFont");
/* 54 */     String strColor = getParameter("barLabelColor");
/* 55 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 56 */       if (strFont != null)
/* 57 */         this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/* 58 */       if (strColor != null)
/* 59 */         this.chart.getDatasets()[i].setLabelColor(
/* 60 */           ChartUtil.getColor(strColor));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init() {
/* 65 */     initLocale();
/* 66 */     this.chart = new DateStackColumnChart("My Chart", this.userLocale);
/* 67 */     initDateStreamReader();
/* 68 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.dateStackColumnApp
 * JD-Core Version:    0.6.2
 */