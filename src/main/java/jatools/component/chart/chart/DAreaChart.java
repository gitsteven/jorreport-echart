/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import jatools.util.Map;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class DAreaChart extends AreaChart
/*    */ {
/*    */   public DAreaChart()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DAreaChart(String s)
/*    */   {
/* 36 */     super(s);
/*    */   }
/*    */   protected void initAxes() {
/* 39 */     setXAxis(new DateAxis());
/* 40 */     this.xAxis.setSide(0);
/* 41 */     this.xAxis.setBarScaling(false);
/* 42 */     setYAxis(new StackAxis());
/*    */   }
/*    */ 
/*    */   public DAreaChart(String name, Locale locale)
/*    */   {
/* 53 */     super(name, locale);
/*    */   }
/*    */ 
/*    */   public DAreaChart(Locale locale)
/*    */   {
/* 63 */     super(locale);
/*    */   }
/*    */ 
/*    */   public void applyProperties(Map map)
/*    */   {
/* 70 */     super.applyProperties(map);
/*    */ 
/* 73 */     initDateAxis((DateAxis)getXAxis(), map);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DAreaChart
 * JD-Core Version:    0.6.2
 */