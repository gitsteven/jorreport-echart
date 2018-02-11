/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import jatools.util.Map;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class DateStackBarChart extends DateBarChart
/*    */ {
/*    */   public DateStackBarChart()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DateStackBarChart(String s)
/*    */   {
/* 34 */     super(s);
/*    */   }
/*    */   protected void initAxes() {
/* 37 */     setXAxis(new DateAxis());
/* 38 */     this.xAxis.setSide(1);
/* 39 */     this.xAxis.setBarScaling(true);
/* 40 */     setYAxis(new StackAxis());
/* 41 */     this.yAxis.setBarScaling(true);
/* 42 */     this.yAxis.setSide(0);
/*    */   }
/*    */   protected void initChart() {
/* 45 */     initGlobals();
/* 46 */     setPlotarea(new Plotarea());
/* 47 */     setBackground(new Background());
/* 48 */     initDatasets();
/* 49 */     initAxes();
/* 50 */     this.bar = new StackBar();
/* 51 */     this.bar.unitScaling = false;
/* 52 */     setDataRepresentation(this.bar);
/* 53 */     setLegend(new Legend());
/* 54 */     resize(640, 480);
/*    */   }
/*    */ 
/*    */   public DateStackBarChart(String name, Locale locale)
/*    */   {
/* 65 */     super(name, locale);
/*    */   }
/*    */ 
/*    */   public DateStackBarChart(Locale locale)
/*    */   {
/* 75 */     super(locale);
/*    */   }
/*    */ 
/*    */   public void applyProperties(Map map)
/*    */   {
/* 80 */     applyGeneralProperty(map);
/*    */ 
/* 82 */     super.applyProperties(map);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DateStackBarChart
 * JD-Core Version:    0.6.2
 */