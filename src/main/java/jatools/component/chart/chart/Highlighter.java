/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import java.awt.Point;
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class Highlighter
/*    */   implements Serializable
/*    */ {
/*    */   Globals globals;
/*    */ 
/*    */   public Highlighter(Globals g)
/*    */   {
/* 28 */     this.globals = g;
/*    */   }
/*    */ 
/*    */   public synchronized Point[] getHighlightPoints(Object o)
/*    */   {
/* 35 */     ArrayList v = new ArrayList();
/*    */ 
/* 37 */     for (int i = 0; i < this.globals.displayList.objectList.size(); i++) {
/* 38 */       if (this.globals.displayList.objectList.get(i) == o) {
/* 39 */         Point[] p = (Point[])this.globals.displayList.primitiveList.get(i);
/*    */ 
/* 41 */         for (int j = 0; j < p.length; j++) {
/* 42 */           v.add(p[j]);
/*    */         }
/* 44 */         i = 2147483646;
/*    */       }
/*    */     }
/*    */ 
/* 48 */     if (v.size() == 0) {
/* 49 */       return null;
/*    */     }
/*    */ 
/* 52 */     Point[] p = new Point[v.size()];
/*    */ 
/* 54 */     for (int i = 0; i < v.size(); i++) {
/* 55 */       p[i] = ((Point)v.get(i));
/*    */     }
/*    */ 
/* 58 */     return p;
/*    */   }
/*    */ 
/*    */   public synchronized Point[][] getHighlightPointSet(Object o)
/*    */   {
/* 67 */     ArrayList v = new ArrayList();
/*    */ 
/* 69 */     for (int i = 0; i < this.globals.displayList.objectList.size(); i++) {
/* 70 */       if (this.globals.displayList.objectList.get(i) == o) {
/* 71 */         Point[] p = (Point[])this.globals.displayList.primitiveList.get(i);
/* 72 */         v.add(p);
/*    */       }
/*    */     }
/*    */ 
/* 76 */     if (v.size() == 0) {
/* 77 */       return null;
/*    */     }
/*    */ 
/* 80 */     Point[][] p = new Point[v.size()][];
/*    */ 
/* 82 */     for (int i = 0; i < v.size(); i++) {
/* 83 */       p[i] = ((Point[])v.get(i));
/*    */     }
/*    */ 
/* 86 */     return p;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Highlighter
 * JD-Core Version:    0.6.2
 */