/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class HorizBarChart extends BarChart
/*    */ {
/*    */   public HorizBarChart()
/*    */   {
/*    */   }
/*    */ 
/*    */   public HorizBarChart(String s)
/*    */   {
/* 30 */     super(s);
/*    */   }
/*    */   protected void initAxes() {
/* 33 */     setXAxis(new LabelAxis());
/* 34 */     this.xAxis.setSide(1);
/* 35 */     this.xAxis.setBarScaling(true);
/* 36 */     setYAxis(new Axis());
/* 37 */     this.yAxis.setBarScaling(true);
/* 38 */     this.yAxis.setSide(0);
/*    */   }
/*    */   protected void initChart() {
/* 41 */     initGlobals();
/* 42 */     setPlotarea(new Plotarea());
/* 43 */     setBackground(new Background());
/* 44 */     initDatasets();
/* 45 */     initAxes();
/* 46 */     this.bar = new HorizBar();
/* 47 */     setDataRepresentation(this.bar);
/* 48 */     setLegend(new Legend());
/*    */   }
/*    */ 
/*    */   public HorizBarChart(String name, Locale locale)
/*    */   {
/* 59 */     super(name, locale);
/*    */   }
/*    */ 
/*    */   public HorizBarChart(Locale locale)
/*    */   {
/* 69 */     super(locale);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HorizBarChart
 * JD-Core Version:    0.6.2
 */