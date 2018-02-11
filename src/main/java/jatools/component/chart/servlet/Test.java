/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class Test
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 25 */     Properties properties = new Properties();
/*    */ 
/* 28 */     properties.put("width", "500");
/* 29 */     properties.put("height", "300");
/* 30 */     properties.put("imageType", "j_png");
/* 31 */     properties.put("barBaseline", "0.2");
/* 32 */     properties.put("labelsOn", "true");
/* 33 */     properties.put("useValueLabels", "true");
/* 34 */     properties.put("labelPrecision", "2");
/* 35 */     properties.put("barClusterWidth", "0.65");
/* 36 */     properties.put("outlineColor", "000000");
/* 37 */     properties.put("dataset0LabelFont", "Dialog,12,2");
/* 38 */     properties.put("dataset0LabelColor", "000000");
/* 39 */     properties.put("dataset1LabelFont", "Dialog,12,2");
/* 40 */     properties.put("dataset1LabelColor", "000000");
/* 41 */     properties.put("backgroundColor", "ffffff");
/* 42 */     properties.put("backgroundOutlining", "true");
/* 43 */     properties.put("backgroundOutlineColor", "000000");
/* 44 */     properties.put("plotAreaColor", "ffffff");
/* 45 */     properties.put("plotAreaOutlining", "true");
/* 46 */     properties.put("plotAreaOutlineColor", "000000");
/* 47 */     properties.put("dataset0Name", "Series 1");
/* 48 */     properties.put("dataset0Color", "0000ff");
/* 49 */     properties.put("dataset0Outlining", "true");
/* 50 */     properties.put("dataset0OutlineColor", "000000");
/* 51 */     properties.put("dataset0Labels", "a,b,c,d");
/* 52 */     properties.put("dataset1Name", "Series 2");
/* 53 */     properties.put("dataset1Labels", "a,b,c,d");
/* 54 */     properties.put("yAxisLabelColor", "000000");
/* 55 */     properties.put("yAxisLineColor", "000000");
/* 56 */     properties.put("yAxisTickColor", "000000");
/* 57 */     properties.put("yAxisOptions", "bottomAxis,");
/* 58 */     properties.put("yAxisLabelFont", "Dialog,12,0");
/* 59 */     properties.put("yAxisLabelColor", "000000");
/* 60 */     properties.put("yAxisLabelPrecision", "2");
/* 61 */     properties.put("xAxisLabelColor", "000000");
/* 62 */     properties.put("xAxisLineColor", "000000");
/* 63 */     properties.put("xAxisTickColor", "000000");
/* 64 */     properties.put("xAxisOptions", "leftAxis,");
/* 65 */     properties.put("xAxisLabelFont", "Dialog,12,0");
/* 66 */     properties.put("xAxisLabelColor", "000000");
/* 67 */     properties.put("xAxisLabelPrecision", "2");
/* 68 */     properties.put("legendColor", "ffffff");
/* 69 */     properties.put("legendOutlining", "true");
/* 70 */     properties.put("legendOutlineColor", "000000");
/* 71 */     properties.put("writeDirectory", "/usr/apache/htdocs/chart_images/");
/* 72 */     properties.put("dwellUseDatasetName", "false");
/* 73 */     properties.put("dwellUseYValue", "true");
/* 74 */     properties.put("dwellYString", "Y: XX");
/* 75 */     properties.put("dwellUseXValue", "false");
/* 76 */     properties.put("dwellUseLabelString", "false");
/*    */ 
/* 80 */     Bean chart = new barApp(properties);
/*    */     try
/*    */     {
/* 89 */       chart.getFileName();
/*    */     }
/*    */     catch (Exception e) {
/* 92 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.Test
 * JD-Core Version:    0.6.2
 */