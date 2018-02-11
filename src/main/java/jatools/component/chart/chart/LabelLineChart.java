/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import jatools.util.Map;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class LabelLineChart extends LineChart
/*    */ {
/*    */   public LabelLineChart()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LabelLineChart(String s)
/*    */   {
/* 35 */     super(s);
/*    */   }
/*    */   protected void initAxes() {
/* 38 */     setXAxis(new LabelAxis());
/* 39 */     this.xAxis.setBarScaling(false);
/* 40 */     this.xAxis.setSide(0);
/* 41 */     setYAxis(new Axis());
/*    */   }
/*    */ 
/*    */   protected void initChart() {
/* 45 */     initGlobals();
/* 46 */     setPlotarea(new Plotarea());
/* 47 */     setBackground(new Background());
/* 48 */     initDatasets();
/* 49 */     initAxes();
/* 50 */     Line line = new Line();
/* 51 */     setDataRepresentation(line);
/* 52 */     setLegend(new LineLegend());
/* 53 */     resize(640, 480);
/*    */   }
/*    */ 
/*    */   public LabelLineChart(String name, Locale locale)
/*    */   {
/* 64 */     super(name, locale);
/*    */   }
/*    */ 
/*    */   public LabelLineChart(Locale locale)
/*    */   {
/* 74 */     super(locale);
/*    */   }
/*    */ 
/*    */   public void applyProperties(Map map)
/*    */   {
/* 81 */     super.applyProperties(map);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.LabelLineChart
 * JD-Core Version:    0.6.2
 */