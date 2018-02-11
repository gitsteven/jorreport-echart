/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import java.awt.Event;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class MouseEvent
/*    */ {
/*    */   private Event e;
/*    */ 
/*    */   public synchronized void put(Event e)
/*    */   {
/* 21 */     while ((this.e != null) && (
/* 22 */       (this.e.id != 506) || (e.id != 506))) {
/*    */       try {
/* 24 */         wait();
/*    */       } catch (InterruptedException ex) {
/* 26 */         System.err.println("Exception: " + ex);
/*    */       }
/*    */     }
/*    */ 
/* 30 */     this.e = e;
/* 31 */     notify();
/*    */   }
/*    */ 
/*    */   public synchronized Event get()
/*    */   {
/* 40 */     while (this.e == null) {
/*    */       try {
/* 42 */         wait();
/*    */       } catch (InterruptedException ex) {
/* 44 */         System.err.println("Exception: " + ex);
/*    */       }
/*    */     }
/*    */ 
/* 48 */     notify();
/*    */ 
/* 50 */     Event save = this.e;
/* 51 */     this.e = null;
/*    */ 
/* 53 */     return save;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.MouseEvent
 * JD-Core Version:    0.6.2
 */