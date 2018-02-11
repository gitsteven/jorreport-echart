/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import jatools.util.Map;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class DateLineChart extends LineChart
/*    */ {
/*    */   public DateLineChart()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DateLineChart(String s)
/*    */   {
/* 34 */     super(s);
/*    */   }
/*    */   protected void initAxes() {
/* 37 */     setXAxis(new DateAxis());
/* 38 */     this.xAxis.setSide(0);
/* 39 */     setYAxis(new Axis());
/*    */   }
/*    */ 
/*    */   public DateLineChart(String name, Locale locale)
/*    */   {
/* 50 */     super(name, locale);
/*    */   }
/*    */ 
/*    */   public DateLineChart(Locale locale)
/*    */   {
/* 60 */     super(locale);
/*    */   }
/*    */ 
/*    */   public void applyProperties(Map map)
/*    */   {
/* 65 */     applyGeneralProperty(map);
/*    */ 
/* 67 */     initDateAxis((DateAxis)getXAxis(), map);
/*    */ 
/* 69 */     super.applyProperties(map);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DateLineChart
 * JD-Core Version:    0.6.2
 */