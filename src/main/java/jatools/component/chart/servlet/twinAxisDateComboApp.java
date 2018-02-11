/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ParameterParser;
/*    */ import jatools.component.chart.chart.AxisInterface;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Line;
/*    */ import jatools.component.chart.chart.Stick;
/*    */ import jatools.component.chart.chart.TwinAxisDateComboChart;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import jatools.component.chart.parts.SymbolFormat;
/*    */ import java.awt.Graphics;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class twinAxisDateComboApp extends dateLineApp
/*    */ {
/*    */   public twinAxisDateComboApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public twinAxisDateComboApp(Properties defaultProperties)
/*    */   {
/* 26 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void drawMyStuff(Graphics g)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 40 */     super.getMyOptions();
/* 41 */     String str = null;
/* 42 */     TwinAxisDateComboChart thisChart = (TwinAxisDateComboChart)this.chart;
/* 43 */     for (int i = 0; i < _Chart.MAX_DATASETS; i++) {
/* 44 */       str = getParameter("dataset" + i + "onRight");
/* 45 */       if ((str != null) && 
/* 46 */         (str.equalsIgnoreCase("true"))) {
/* 47 */         thisChart.assignToRightAxis(i, true);
/*    */       }
/*    */ 
/* 50 */       str = getParameter("dataset" + i + "Type");
/* 51 */       if (str != null) {
/* 52 */         if (str.equalsIgnoreCase("LINE")) {
/* 53 */           thisChart.setDatasetType(i, 0);
/*    */         }
/* 55 */         else if (str.equalsIgnoreCase("STICK")) {
/* 56 */           thisChart.setDatasetType(i, 1);
/*    */         }
/*    */       }
/*    */     }
/* 60 */     this.parser.parseAxOptions("auxAxis", thisChart.getAuxAxis());
/*    */ 
/* 64 */     str = getParameter("barBaseline");
/* 65 */     if (str != null)
/* 66 */       thisChart.getStick().setBaseline(Double.valueOf(str).doubleValue());
/* 67 */     str = getParameter("barWidth");
/* 68 */     if (str != null) {
/* 69 */       thisChart.getStick().setWidth(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 73 */     str = getParameter("auxBarBaseline");
/* 74 */     if (str != null)
/* 75 */       thisChart.getAuxStick().setBaseline(Double.valueOf(str).doubleValue());
/* 76 */     str = getParameter("auxBarWidth");
/* 77 */     if (str != null) {
/* 78 */       thisChart.getAuxStick().setWidth(Integer.parseInt(str));
/*    */     }
/*    */ 
/* 82 */     str = getParameter("auxPlotLinesOn");
/* 83 */     if (str != null)
/* 84 */       thisChart.getAuxLine().setScatterPlot(true);
/* 85 */     str = getParameter("auxPlotLinesOff");
/* 86 */     if (str != null)
/* 87 */       thisChart.getAuxLine().setScatterPlot(false);
/*    */   }
/*    */ 
/*    */   public void init() {
/* 91 */     initLocale();
/* 92 */     this.chart = new TwinAxisDateComboChart(this.userLocale);
/* 93 */     this.chart.getYAxis().setLabelFormat(new SymbolFormat());
/* 94 */     ((TwinAxisDateComboChart)this.chart).getAuxAxis().setLabelFormat(new SymbolFormat());
/* 95 */     initDateStreamReader();
/* 96 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.twinAxisDateComboApp
 * JD-Core Version:    0.6.2
 */