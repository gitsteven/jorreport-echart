/*    */ package jatools.component.chart;
/*    */ 
/*    */ import bsh.ValueAlways;
/*    */ 
/*    */ public class SimpleValue
/*    */   implements ValueAlways
/*    */ {
/*    */   Object val;
/*    */ 
/*    */   public Object value()
/*    */   {
/* 27 */     return this.val;
/*    */   }
/*    */ 
/*    */   public void setValue(Object val)
/*    */   {
/* 36 */     this.val = val;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.SimpleValue
 * JD-Core Version:    0.6.2
 */