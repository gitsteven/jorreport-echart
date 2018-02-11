/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class StickBarChart extends BarChart
/*    */ {
/*    */   public StickBarChart()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StickBarChart(String s)
/*    */   {
/* 33 */     super(s);
/*    */   }
/*    */ 
/*    */   public void drawGraph() {
/* 37 */     if (this.canvas == null)
/* 38 */       return;
/* 39 */     drawGraph(this.canvas);
/*    */   }
/*    */   protected void initAxes() {
/* 42 */     setXAxis(new DateAxis());
/* 43 */     this.xAxis.setBarScaling(true);
/* 44 */     this.xAxis.setSide(0);
/* 45 */     setYAxis(new Axis());
/* 46 */     this.yAxis.setBarScaling(true);
/*    */   }
/*    */ 
/*    */   protected void initChart() {
/* 50 */     initGlobals();
/* 51 */     setPlotarea(new Plotarea());
/* 52 */     setBackground(new Background());
/* 53 */     initDatasets();
/* 54 */     initAxes();
/* 55 */     this.bar = new Stick();
/* 56 */     this.bar.unitScaling = false;
/* 57 */     setDataRepresentation(this.bar);
/* 58 */     setLegend(new Legend());
/* 59 */     resize(640, 480);
/*    */   }
/*    */ 
/*    */   public StickBarChart(String name, Locale locale)
/*    */   {
/* 70 */     super(name, locale);
/*    */   }
/*    */ 
/*    */   public StickBarChart(Locale locale)
/*    */   {
/* 80 */     super(locale);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.StickBarChart
 * JD-Core Version:    0.6.2
 */