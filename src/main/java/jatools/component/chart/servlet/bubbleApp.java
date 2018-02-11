/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import jatools.component.chart.applet.ChartUtil;
/*    */ import jatools.component.chart.chart.Bubble;
/*    */ import jatools.component.chart.chart.BubbleChart;
/*    */ import jatools.component.chart.chart.ChartInterface;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class bubbleApp extends Bean
/*    */ {
/*    */   public bubbleApp()
/*    */   {
/*    */   }
/*    */ 
/*    */   public bubbleApp(Properties defaultProperties)
/*    */   {
/* 30 */     this.properties = new Properties(defaultProperties);
/*    */   }
/*    */ 
/*    */   public void getMyOptions()
/*    */   {
/* 43 */     BubbleChart b = (BubbleChart)this.chart;
/*    */ 
/* 49 */     String str = getParameter("setZScale");
/* 50 */     if (str != null)
/* 51 */       b.setZScale(Double.valueOf(str).doubleValue());
/* 52 */     str = getParameter("zAutoScaleOff");
/* 53 */     if (str != null)
/* 54 */       b.setAutoZScale(false);
/*    */     else
/* 56 */       b.setAutoZScale(true);
/* 57 */     str = getParameter("maxDiameter");
/* 58 */     if (str != null)
/* 59 */       ((Bubble)b.getDataRepresentation()).maxDiameter = Double.valueOf(str).doubleValue();
/* 60 */     str = getParameter("fillBubbles");
/* 61 */     if (str != null)
/* 62 */       ((Bubble)b.getDataRepresentation()).fillBubbles = true;
/*    */     else
/* 64 */       ((Bubble)b.getDataRepresentation()).fillBubbles = false;
/* 65 */     str = getParameter("crossAxes");
/* 66 */     if (str != null)
/* 67 */       b.setCrossAxes(str.equalsIgnoreCase("true"));
/* 68 */     str = getParameter("xCrossVal");
/* 69 */     if (str != null)
/* 70 */       b.setXCrossVal(Double.valueOf(str).doubleValue());
/* 71 */     str = getParameter("yCrossVal");
/* 72 */     if (str != null)
/* 73 */       b.setYCrossVal(Double.valueOf(str).doubleValue());
/* 74 */     str = getParameter("useValueLabels");
/* 75 */     if (str != null)
/* 76 */       b.getBubble().setLabelsOn(Boolean.valueOf(str).booleanValue());
/* 77 */     String strFont = getParameter("bubbleLabelFont");
/* 78 */     String strColor = getParameter("bubbleLabelColor");
/* 79 */     for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 80 */       if (strFont != null)
/* 81 */         this.chart.getDatasets()[i].setLabelFont(ChartUtil.getFont(strFont));
/* 82 */       if (strColor != null)
/* 83 */         this.chart.getDatasets()[i].setLabelColor(ChartUtil.getColor(strColor));
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init() {
/* 88 */     initLocale();
/* 89 */     this.chart = new BubbleChart("My Chart");
/*    */ 
/* 91 */     ((BubbleChart)this.chart).setCrossAxes(false);
/* 92 */     getOptions();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.bubbleApp
 * JD-Core Version:    0.6.2
 */