/*     */ package jatools.component.chart;
/*     */ 
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.servlet.Bean;
/*     */ import jatools.component.chart.servlet.areaApp;
/*     */ import jatools.component.chart.servlet.barApp;
/*     */ import jatools.component.chart.servlet.barAreaApp;
/*     */ import jatools.component.chart.servlet.barAreaLineApp;
/*     */ import jatools.component.chart.servlet.barLineApp;
/*     */ import jatools.component.chart.servlet.bubbleApp;
/*     */ import jatools.component.chart.servlet.candlestickApp;
/*     */ import jatools.component.chart.servlet.columnApp;
/*     */ import jatools.component.chart.servlet.dateAreaApp;
/*     */ import jatools.component.chart.servlet.dateBarApp;
/*     */ import jatools.component.chart.servlet.dateColumnApp;
/*     */ import jatools.component.chart.servlet.dateLineApp;
/*     */ import jatools.component.chart.servlet.dateStackBarApp;
/*     */ import jatools.component.chart.servlet.dateStackColumnApp;
/*     */ import jatools.component.chart.servlet.ganttApp;
/*     */ import jatools.component.chart.servlet.hHiLoBarApp;
/*     */ import jatools.component.chart.servlet.hiLoBarApp;
/*     */ import jatools.component.chart.servlet.hiLoCloseApp;
/*     */ import jatools.component.chart.servlet.lineApp;
/*     */ import jatools.component.chart.servlet.pieApp;
/*     */ import jatools.component.chart.servlet.polarApp;
/*     */ import jatools.component.chart.servlet.regressApp;
/*     */ import jatools.component.chart.servlet.sectorMapApp;
/*     */ import jatools.component.chart.servlet.stackBarApp;
/*     */ import jatools.component.chart.servlet.stackColumnApp;
/*     */ import jatools.component.chart.servlet.twinAxisBarAreaApp;
/*     */ import jatools.component.chart.servlet.twinAxisBarLineApp;
/*     */ import jatools.component.chart.servlet.twinAxisDateLineApp;
/*     */ import jatools.component.chart.servlet.twinAxisLineApp;
/*     */ import jatools.engine.script.Script;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class ChartFactory
/*     */ {
/*  56 */   static java.util.Map appCache = new HashMap();
/*     */ 
/*     */   static {
/*  59 */     appCache.put("0", barApp.class);
/*  60 */     appCache.put("1", columnApp.class);
/*  61 */     appCache.put("2", areaApp.class);
/*  62 */     appCache.put("3", stackBarApp.class);
/*  63 */     appCache.put("4", stackColumnApp.class);
/*  64 */     appCache.put("5", pieApp.class);
/*  65 */     appCache.put("6", lineApp.class);
/*  66 */     appCache.put("7", barLineApp.class);
/*  67 */     appCache.put("8", barAreaApp.class);
/*  68 */     appCache.put("9", dateLineApp.class);
/*  69 */     appCache.put("10", dateAreaApp.class);
/*  70 */     appCache.put("11", dateColumnApp.class);
/*  71 */     appCache.put("12", dateStackColumnApp.class);
/*  72 */     appCache.put("13", dateBarApp.class);
/*  73 */     appCache.put("14", twinAxisDateLineApp.class);
/*  74 */     appCache.put("15", dateStackBarApp.class);
/*  75 */     appCache.put("16", bubbleApp.class);
/*  76 */     appCache.put("17", regressApp.class);
/*     */ 
/*  78 */     appCache.put("18", polarApp.class);
/*  79 */     appCache.put("19", sectorMapApp.class);
/*  80 */     appCache.put("20", twinAxisBarAreaApp.class);
/*  81 */     appCache.put("21", twinAxisBarLineApp.class);
/*  82 */     appCache.put("22", twinAxisLineApp.class);
/*  83 */     appCache.put("23", hHiLoBarApp.class);
/*  84 */     appCache.put("24", hiLoBarApp.class);
/*  85 */     appCache.put("25", candlestickApp.class);
/*  86 */     appCache.put("26", hiLoCloseApp.class);
/*  87 */     appCache.put("27", ganttApp.class);
/*  88 */     appCache.put("28", barAreaLineApp.class);
/*     */   }
/*     */ 
/*     */   public static ChartInterface createInstance(Chart javaChart, Script provider)
/*     */   {
/* 101 */     Bean bean = null;
/* 102 */     Properties props = javaChart.getProperties();
/* 103 */     String chartType = props.getProperty("chartType");
/* 104 */     String type = "1";
/*     */ 
/* 106 */     if (chartType != null) {
/* 107 */       type = chartType;
/*     */     }
/*     */ 
/* 110 */     props.setProperty("chartType", type);
/*     */     try
/*     */     {
/* 114 */       bean = (Bean)((Class)appCache.get(type)).newInstance();
/*     */     } catch (InstantiationException e) {
/* 116 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/* 118 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 121 */     bean.setGraphReader(javaChart.getReader());
/* 122 */     bean.setGraphLabelField(javaChart.getLabelField());
/* 123 */     bean.setGraphShowData(javaChart.getPlotData());
/* 124 */     bean.setGraphDataset(javaChart.getDs());
/* 125 */     bean.setGraphPlotFrom(javaChart.getPlotFrom());
/* 126 */     bean.setJatoolsDataProvider(provider);
/* 127 */     bean.setProperties(props);
/*     */ 
/* 129 */     return bean.getChart();
/*     */   }
/*     */ 
/*     */   public static Bean createInstance(int type)
/*     */   {
/* 140 */     Bean bean = null;
/* 141 */     Class className = (Class)appCache.get(String.valueOf(type));
/*     */     try
/*     */     {
/* 144 */       bean = (Bean)className.newInstance();
/*     */     }
/*     */     catch (InstantiationException e) {
/* 147 */       e.printStackTrace();
/*     */     }
/*     */     catch (IllegalAccessException e) {
/* 150 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 153 */     return bean;
/*     */   }
/*     */ 
/*     */   public static Properties asProperties(jatools.util.Map map)
/*     */   {
/* 164 */     Properties tmp = new Properties();
/* 165 */     Iterator it = map.names();
/*     */ 
/* 167 */     while (it.hasNext()) {
/* 168 */       String key = (String)it.next();
/*     */ 
/* 170 */       if (map.get(key) != null) {
/* 171 */         String value = (String)map.get(key);
/* 172 */         tmp.setProperty(key, value);
/*     */       }
/*     */     }
/*     */ 
/* 176 */     return tmp;
/*     */   }
/*     */ 
/*     */   public static Bean createBeanInstance(Chart javaChart, Script provider)
/*     */   {
/* 188 */     Bean bean = null;
/* 189 */     Properties props = javaChart.getProperties();
/* 190 */     String chartType = props.getProperty("chartType");
/* 191 */     String type = "1";
/*     */ 
/* 193 */     if (chartType != null) {
/* 194 */       type = chartType;
/*     */     }
/*     */ 
/* 197 */     props.setProperty("chartType", type);
/*     */ 
/* 199 */     Class clas = (Class)appCache.get(type);
/*     */     try
/*     */     {
/* 202 */       bean = (Bean)clas.newInstance();
/*     */     } catch (InstantiationException e) {
/* 204 */       e.printStackTrace();
/*     */     } catch (IllegalAccessException e) {
/* 206 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 209 */     bean.setGraphReader(javaChart.getReader());
/* 210 */     bean.setGraphLabelField(javaChart.getLabelField());
/* 211 */     bean.setGraphShowData(javaChart.getPlotData());
/* 212 */     bean.setJatoolsDataProvider(provider);
/* 213 */     bean.setProperties(props);
/* 214 */     bean.setDs(javaChart.getDs());
/* 215 */     bean.setIdField(javaChart.getIdField());
/* 216 */     bean.setGraphPlotFrom(javaChart.getPlotFrom());
/*     */ 
/* 218 */     return bean;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.ChartFactory
 * JD-Core Version:    0.6.2
 */