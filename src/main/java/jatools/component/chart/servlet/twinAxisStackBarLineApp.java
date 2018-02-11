/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.Bar;
/*    */ import jatools.component.chart.chart.Line;
/*    */ import jatools.component.chart.chart.TwinAxisStackBarLineChart;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class twinAxisStackBarLineApp extends barApp
/*    */ {
/*    */   public twinAxisStackBarLineApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public twinAxisStackBarLineApp(Properties defaultProperties)
/*    */   {
/* 20 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 26 */     TwinAxisStackBarLineChart thisChart = (TwinAxisStackBarLineChart)this.chart;
/* 27 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++) {
/* 28 */       String str = getParameter("dataset" + i + "Type");
/* 29 */       if (str != null) {
/* 30 */         if (str.equalsIgnoreCase("LINE")) {
/* 31 */           thisChart.setDatasetType(i, 1);
/*    */         }
/* 33 */         else if (str.equalsIgnoreCase("BAR")) {
/* 34 */           thisChart.setDatasetType(i, 0);
/*    */         }
/*    */       }
/*    */     }
/* 38 */     this.parser.parseAxOptions("barAxis", thisChart.getAuxAxis());
/*    */ 
/* 41 */     String str = getParameter("barBaseline");
/* 42 */     if (str != null)
/* 43 */       thisChart.getBar().setBaseline(Double.valueOf(str).doubleValue());
/* 44 */     str = getParameter("barClusterWidth");
/* 45 */     if (str != null)
/* 46 */       thisChart.getBar().setClusterWidth(Double.valueOf(str).doubleValue());
/* 47 */     str = getParameter("barLabelsOn");
/* 48 */     if ((str != null) && 
/* 49 */       (!str.equalsIgnoreCase("false")))
/* 50 */       thisChart.getBar().setLabelsOn(true);
/* 51 */     str = getParameter("barLabelAngle");
/* 52 */     if (str != null)
/* 53 */       thisChart.getBar().setLabelAngle(Integer.parseInt(str));
/* 54 */     str = getParameter("barLabelPrecision");
/* 55 */     if (str != null)
/* 56 */       thisChart.getBar().setLabelPrecision(Integer.parseInt(str));
/* 57 */     str = getParameter("useValueLabels");
/* 58 */     if (str != null)
/* 59 */       thisChart.getBar().setUseValueLabels(str.equalsIgnoreCase("true"));
/* 60 */     str = getParameter("individualColors");
/* 61 */     if (str != null) {
/* 62 */       thisChart.setIndividualColors(Boolean.valueOf(str).booleanValue());
/*    */     }
/*    */ 
/* 65 */     str = getParameter("plotLinesOn");
/* 66 */     if (str != null)
/* 67 */       thisChart.setLineVisible(true);
/* 68 */     str = getParameter("plotLinesOff");
/* 69 */     if (str != null)
/* 70 */       thisChart.setLineVisible(false);
/* 71 */     str = getParameter("individualMarkers");
/* 72 */     if ((str != null) && 
/* 73 */       (str.equalsIgnoreCase("true"))) {
/* 74 */       thisChart.getLine().setIndividualMarkers(true);
/*    */     }
/*    */ 
/* 77 */     str = getParameter("outlineColor");
/* 78 */     if (str != null)
/*    */     {
/* 80 */       for (int j = 0; j < thisChart.getDatasets().length; j++) {
/* 81 */         String dataType = getParameter("dataset" + j + "Type");
/* 82 */         if ((dataType != null) && (dataType.equalsIgnoreCase("BAR")))
/* 83 */           this.parser.activateOutlineFills(thisChart.getDatasets()[j], 
/* 84 */             this.parser.getColor(str), 
/* 85 */             thisChart.getIndividualColors());
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init() {
/* 91 */     initLocale();
/* 92 */     this.chart = new TwinAxisStackBarLineChart(this.userLocale);
/* 93 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.twinAxisStackBarLineApp
 * JD-Core Version:    0.6.2
 */