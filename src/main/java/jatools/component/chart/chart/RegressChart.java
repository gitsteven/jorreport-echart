/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import jatools.util.Map;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class RegressChart extends LineChart
/*    */ {
/*    */   public RegressChart()
/*    */   {
/*    */   }
/*    */ 
/*    */   public RegressChart(String s)
/*    */   {
/* 33 */     super(s);
/*    */   }
/*    */   protected void initChart() {
/* 36 */     initGlobals();
/* 37 */     setPlotarea(new Plotarea());
/* 38 */     setBackground(new Background());
/* 39 */     initDatasets();
/* 40 */     initAxes();
/* 41 */     Line line = new Regress();
/* 42 */     setDataRepresentation(line);
/* 43 */     setLegend(new LineLegend());
/* 44 */     resize(640, 480);
/*    */   }
/*    */ 
/*    */   public RegressChart(String name, Locale locale)
/*    */   {
/* 56 */     super(name, locale);
/*    */   }
/*    */ 
/*    */   public RegressChart(Locale locale)
/*    */   {
/* 66 */     super(locale);
/*    */   }
/*    */ 
/*    */   public void applyProperties(Map map)
/*    */   {
/* 71 */     applyGeneralProperty(map);
/*    */ 
/* 73 */     super.applyProperties(map);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.RegressChart
 * JD-Core Version:    0.6.2
 */