/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import jatools.util.Map;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class StackColumnChart extends BarChart
/*    */ {
/*    */   public StackColumnChart()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StackColumnChart(String s)
/*    */   {
/* 34 */     super(s);
/*    */   }
/*    */   protected void initAxes() {
/* 37 */     setXAxis(new LabelAxis());
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
/* 50 */     setDataRepresentation(this.bar);
/* 51 */     setLegend(new Legend());
/* 52 */     resize(640, 480);
/*    */   }
/*    */ 
/*    */   public StackColumnChart(String name, Locale locale)
/*    */   {
/* 63 */     super(name, locale);
/*    */   }
/*    */ 
/*    */   public StackColumnChart(Locale locale)
/*    */   {
/* 73 */     super(locale);
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
 * Qualified Name:     jatools.component.chart.chart.StackColumnChart
 * JD-Core Version:    0.6.2
 */