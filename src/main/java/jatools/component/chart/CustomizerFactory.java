/*     */ package jatools.component.chart;
/*     */ 
/*     */ import jatools.component.chart.chart.AreaChart;
/*     */ import jatools.component.chart.chart.BarAreaChart;
/*     */ import jatools.component.chart.chart.BarAreaLineChart;
/*     */ import jatools.component.chart.chart.BarChart;
/*     */ import jatools.component.chart.chart.BarLineChart;
/*     */ import jatools.component.chart.chart.BubbleChart;
/*     */ import jatools.component.chart.chart.CandlestickChart;
/*     */ import jatools.component.chart.chart.DAreaChart;
/*     */ import jatools.component.chart.chart.DateBarChart;
/*     */ import jatools.component.chart.chart.DateColumnChart;
/*     */ import jatools.component.chart.chart.DateLineChart;
/*     */ import jatools.component.chart.chart.DateStackBarChart;
/*     */ import jatools.component.chart.chart.DateStackColumnChart;
/*     */ import jatools.component.chart.chart.GanttChart;
/*     */ import jatools.component.chart.chart.HiLoBarChart;
/*     */ import jatools.component.chart.chart.HiLoCloseChart;
/*     */ import jatools.component.chart.chart.HorizBarChart;
/*     */ import jatools.component.chart.chart.HorizHiLoBarChart;
/*     */ import jatools.component.chart.chart.LabelLineChart;
/*     */ import jatools.component.chart.chart.LineChart;
/*     */ import jatools.component.chart.chart.PieChart;
/*     */ import jatools.component.chart.chart.PolarChart;
/*     */ import jatools.component.chart.chart.RegressChart;
/*     */ import jatools.component.chart.chart.SectorMapChart;
/*     */ import jatools.component.chart.chart.StackBarChart;
/*     */ import jatools.component.chart.chart.StackColumnChart;
/*     */ import jatools.component.chart.chart.TwinAxisBarAreaChart;
/*     */ import jatools.component.chart.chart.TwinAxisBarLineChart;
/*     */ import jatools.component.chart.chart.TwinAxisDateLineChart;
/*     */ import jatools.component.chart.chart.TwinAxisLineChart;
/*     */ import jatools.component.chart.customizer.AreaTabShell;
/*     */ import jatools.component.chart.customizer.BarAreaLineTabShell;
/*     */ import jatools.component.chart.customizer.BarAreaTabShell;
/*     */ import jatools.component.chart.customizer.BarLineTabShell;
/*     */ import jatools.component.chart.customizer.BarTabShell;
/*     */ import jatools.component.chart.customizer.BubbleTabShell;
/*     */ import jatools.component.chart.customizer.CandleTabShell;
/*     */ import jatools.component.chart.customizer.HiLoCloseTabShell;
/*     */ import jatools.component.chart.customizer.LineTabShell;
/*     */ import jatools.component.chart.customizer.PieTabShell;
/*     */ import jatools.component.chart.customizer.RadarTabShell;
/*     */ import jatools.component.chart.customizer.SectorTabShell;
/*     */ import jatools.component.chart.customizer.Tabs;
/*     */ import jatools.component.chart.customizer.TwinAxisBarAreaTabShell;
/*     */ import jatools.component.chart.customizer.TwinAxisBarLineTabShell;
/*     */ import jatools.component.chart.customizer.TwinAxisLineTabShell;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CustomizerFactory
/*     */ {
/*  67 */   static HashMap tabsMap = new HashMap();
/*     */ 
/* 103 */   static Map map = new HashMap();
/*     */ 
/*     */   static
/*     */   {
/*  70 */     tabsMap.put(BarChart.class, BarTabShell.class);
/*  71 */     tabsMap.put(HorizBarChart.class, BarTabShell.class);
/*  72 */     tabsMap.put(AreaChart.class, AreaTabShell.class);
/*  73 */     tabsMap.put(StackBarChart.class, BarTabShell.class);
/*  74 */     tabsMap.put(PieChart.class, PieTabShell.class);
/*  75 */     tabsMap.put(LineChart.class, LineTabShell.class);
/*  76 */     tabsMap.put(LabelLineChart.class, LineTabShell.class);
/*  77 */     tabsMap.put(BarLineChart.class, BarLineTabShell.class);
/*  78 */     tabsMap.put(BarAreaChart.class, BarAreaTabShell.class);
/*  79 */     tabsMap.put(DateLineChart.class, LineTabShell.class);
/*  80 */     tabsMap.put(StackColumnChart.class, BarTabShell.class);
/*  81 */     tabsMap.put(DateColumnChart.class, BarTabShell.class);
/*  82 */     tabsMap.put(DateStackColumnChart.class, BarTabShell.class);
/*  83 */     tabsMap.put(DateBarChart.class, BarTabShell.class);
/*  84 */     tabsMap.put(TwinAxisDateLineChart.class, TwinAxisLineTabShell.class);
/*  85 */     tabsMap.put(DateStackBarChart.class, BarTabShell.class);
/*  86 */     tabsMap.put(BubbleChart.class, BubbleTabShell.class);
/*  87 */     tabsMap.put(RegressChart.class, LineTabShell.class);
/*     */ 
/*  89 */     tabsMap.put(SectorMapChart.class, SectorTabShell.class);
/*  90 */     tabsMap.put(TwinAxisBarLineChart.class, TwinAxisBarLineTabShell.class);
/*  91 */     tabsMap.put(TwinAxisBarAreaChart.class, TwinAxisBarAreaTabShell.class);
/*  92 */     tabsMap.put(HiLoBarChart.class, BarTabShell.class);
/*  93 */     tabsMap.put(CandlestickChart.class, CandleTabShell.class);
/*  94 */     tabsMap.put(HiLoCloseChart.class, HiLoCloseTabShell.class);
/*  95 */     tabsMap.put(DAreaChart.class, AreaTabShell.class);
/*  96 */     tabsMap.put(HorizHiLoBarChart.class, BarTabShell.class);
/*  97 */     tabsMap.put(PolarChart.class, RadarTabShell.class);
/*  98 */     tabsMap.put(TwinAxisLineChart.class, TwinAxisLineTabShell.class);
/*  99 */     tabsMap.put(GanttChart.class, BarTabShell.class);
/* 100 */     tabsMap.put(BarAreaLineChart.class, BarAreaLineTabShell.class);
/*     */   }
/*     */ 
/*     */   public static Tabs createInstance(Class cls)
/*     */   {
/* 107 */     Tabs tab = (Tabs)map.get(cls);
/* 108 */     if (tab == null) {
/* 109 */       Class tabClass = (Class)tabsMap.get(cls);
/*     */       try {
/* 111 */         tab = (Tabs)tabClass.newInstance();
/*     */       }
/*     */       catch (InstantiationException e) {
/* 114 */         e.printStackTrace();
/*     */       }
/*     */       catch (IllegalAccessException e) {
/* 117 */         e.printStackTrace();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 122 */     return tab;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.CustomizerFactory
 * JD-Core Version:    0.6.2
 */