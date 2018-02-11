/*    */ package jatools.component.chart;
/*    */ 
/*    */ import jatools.component.chart.chart.AreaChart;
/*    */ import jatools.component.chart.chart.BarAreaChart;
/*    */ import jatools.component.chart.chart.BarAreaLineChart;
/*    */ import jatools.component.chart.chart.BarChart;
/*    */ import jatools.component.chart.chart.BarLineChart;
/*    */ import jatools.component.chart.chart.BarLineInterface;
/*    */ import jatools.component.chart.chart.BubbleChart;
/*    */ import jatools.component.chart.chart.CandlestickChart;
/*    */ import jatools.component.chart.chart.DAreaChart;
/*    */ import jatools.component.chart.chart.Dataset;
/*    */ import jatools.component.chart.chart.DateBarChart;
/*    */ import jatools.component.chart.chart.DateColumnChart;
/*    */ import jatools.component.chart.chart.DateLineChart;
/*    */ import jatools.component.chart.chart.DateStackBarChart;
/*    */ import jatools.component.chart.chart.DateStackColumnChart;
/*    */ import jatools.component.chart.chart.GanttChart;
/*    */ import jatools.component.chart.chart.HiLoBarChart;
/*    */ import jatools.component.chart.chart.HiLoCloseChart;
/*    */ import jatools.component.chart.chart.HorizBarChart;
/*    */ import jatools.component.chart.chart.HorizHiLoBarChart;
/*    */ import jatools.component.chart.chart.LineChart;
/*    */ import jatools.component.chart.chart.PieChart;
/*    */ import jatools.component.chart.chart.PolarChart;
/*    */ import jatools.component.chart.chart.RegressChart;
/*    */ import jatools.component.chart.chart.SectorMapChart;
/*    */ import jatools.component.chart.chart.StackBarChart;
/*    */ import jatools.component.chart.chart.StackColumnChart;
/*    */ import jatools.component.chart.chart.TwinAxisBarAreaChart;
/*    */ import jatools.component.chart.chart.TwinAxisBarLineChart;
/*    */ import jatools.component.chart.chart.TwinAxisDateLineChart;
/*    */ import jatools.component.chart.chart.TwinAxisLineChart;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class PanelLabelMap
/*    */ {
/* 41 */   private static HashMap labelMap = new HashMap();
/*    */ 
/* 43 */   static { labelMap.put(BarChart.class, "柱形图");
/* 44 */     labelMap.put(HorizBarChart.class, "条形图");
/* 45 */     labelMap.put(AreaChart.class, "面积图");
/* 46 */     labelMap.put(StackBarChart.class, "堆叠条形图");
/* 47 */     labelMap.put(StackColumnChart.class, "堆叠柱形图");
/* 48 */     labelMap.put(PieChart.class, "饼图");
/* 49 */     labelMap.put(LineChart.class, "线形图");
/* 50 */     labelMap.put(BarLineChart.class, "柱形线形图");
/* 51 */     labelMap.put(BarAreaChart.class, "柱形堆积图");
/* 52 */     labelMap.put(DateLineChart.class, "时间线形图");
/* 53 */     labelMap.put(DateColumnChart.class, "时间柱形图");
/* 54 */     labelMap.put(DateStackColumnChart.class, "时间堆叠柱形图");
/* 55 */     labelMap.put(DateBarChart.class, "时间条形图");
/* 56 */     labelMap.put(TwinAxisDateLineChart.class, "双轴时间线形图");
/* 57 */     labelMap.put(DateStackBarChart.class, "时间堆叠条形图");
/* 58 */     labelMap.put(BubbleChart.class, "气泡图");
/* 59 */     labelMap.put(RegressChart.class, "趋势图");
/*    */ 
/* 61 */     labelMap.put(SectorMapChart.class, "块状图");
/* 62 */     labelMap.put(TwinAxisBarLineChart.class, "双轴柱形线形图");
/* 63 */     labelMap.put(TwinAxisBarAreaChart.class, "双轴柱形堆积图");
/* 64 */     labelMap.put(HiLoBarChart.class, "高低柱形图");
/* 65 */     labelMap.put(CandlestickChart.class, "K线图");
/* 66 */     labelMap.put(HiLoCloseChart.class, "高低收盘图");
/* 67 */     labelMap.put(DAreaChart.class, "时间面积图");
/* 68 */     labelMap.put(HorizHiLoBarChart.class, "高低条形图");
/* 69 */     labelMap.put(PolarChart.class, "雷达图");
/* 70 */     labelMap.put(TwinAxisLineChart.class, "双轴线形图");
/* 71 */     labelMap.put(GanttChart.class, "甘特图");
/* 72 */     labelMap.put(BarAreaLineChart.class, "柱形面积线性图"); }
/*    */ 
/*    */   public static String getLabel(_Chart chart)
/*    */   {
/* 76 */     return (String)labelMap.get(chart.getClass());
/*    */   }
/*    */ 
/*    */   public static String getLabel(_Chart chart, Dataset dataset) {
/* 80 */     String value = "颜色及填充";
/*    */ 
/* 82 */     if ((chart instanceof BarLineInterface)) {
/* 83 */       for (int i = 0; i < chart.getNumDatasets(); i++) {
/* 84 */         if ((chart.getDatasets()[i].getName().equals(dataset.getName())) && 
/* 85 */           (((BarLineInterface)chart).getChartType(i) == 1)) {
/* 86 */           value = "线形及标记";
/* 87 */           break;
/*    */         }
/*    */       }
/*    */     }
/* 91 */     else if ((chart instanceof LineChart))
/* 92 */       value = "线形及标记";
/* 93 */     else if ((chart instanceof PolarChart)) {
/* 94 */       value = "线形及标记";
/*    */     }
/*    */ 
/* 97 */     return value;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.PanelLabelMap
 * JD-Core Version:    0.6.2
 */