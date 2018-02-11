/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import jatools.util.Map;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class StackBarChart extends BarChart
/*    */ {
/*    */   public StackBarChart()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StackBarChart(String s)
/*    */   {
/* 34 */     super(s);
/*    */   }
/*    */   protected void initAxes() {
/* 37 */     setXAxis(new LabelAxis());
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
/* 51 */     setDataRepresentation(this.bar);
/* 52 */     setLegend(new Legend());
/* 53 */     resize(640, 480);
/*    */   }
/*    */ 
/*    */   public StackBarChart(String name, Locale locale)
/*    */   {
/* 64 */     super(name, locale);
/*    */   }
/*    */ 
/*    */   public StackBarChart(Locale locale)
/*    */   {
/* 74 */     super(locale);
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
 * Qualified Name:     jatools.component.chart.chart.StackBarChart
 * JD-Core Version:    0.6.2
 */