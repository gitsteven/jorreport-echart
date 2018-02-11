/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import jatools.util.Map;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class DateStackColumnChart extends BarChart
/*    */ {
/*    */   public DateStackColumnChart()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DateStackColumnChart(String s)
/*    */   {
/* 34 */     super(s);
/*    */   }
/*    */   protected void initAxes() {
/* 37 */     setXAxis(new DateAxis());
/* 38 */     this.xAxis.setBarScaling(true);
/* 39 */     this.xAxis.setSide(0);
/* 40 */     setYAxis(new StackAxis());
/* 41 */     this.yAxis.setBarScaling(true);
/*    */   }
/*    */   protected void initChart() {
/* 44 */     initGlobals();
/* 45 */     setPlotarea(new Plotarea());
/* 46 */     setBackground(new Background());
/* 47 */     initDatasets();
/* 48 */     initAxes();
/* 49 */     this.bar = new StackColumn();
/* 50 */     this.bar.unitScaling = false;
/* 51 */     setDataRepresentation(this.bar);
/* 52 */     setLegend(new Legend());
/* 53 */     resize(640, 480);
/*    */   }
/*    */ 
/*    */   public DateStackColumnChart(String name, Locale locale)
/*    */   {
/* 64 */     super(name, locale);
/*    */   }
/*    */ 
/*    */   public DateStackColumnChart(Locale locale)
/*    */   {
/* 74 */     super(locale);
/*    */   }
/*    */ 
/*    */   public void applyProperties(Map map)
/*    */   {
/* 79 */     applyGeneralProperty(map);
/*    */ 
/* 81 */     super.applyProperties(map);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DateStackColumnChart
 * JD-Core Version:    0.6.2
 */