/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Point;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class HiLoClose extends Bar
/*    */ {
/*    */   public HiLoClose()
/*    */   {
/*    */   }
/*    */ 
/*    */   public HiLoClose(Dataset[] d, AxisInterface xAx, AxisInterface yAx, Plotarea subplot)
/*    */   {
/* 34 */     super(d, xAx, yAx, subplot);
/*    */   }
/*    */ 
/*    */   protected void doBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*    */   {
/* 52 */     int offset = this.globals.getMaxX() / this.datasets[0].getData().size();
/* 53 */     offset /= 2;
/* 54 */     offset = Math.max(offset, 2);
/* 55 */     offset = Math.min(offset, 5);
/*    */     try
/*    */     {
/* 58 */       if (this.datasets[whichSet].getDataElementAt(whichBar).label == "D")
/*    */         return;
/*    */       double llx;
/*    */       double llx;
/* 60 */       if (!this.unitScaling)
/* 61 */         llx = this.datasets[whichSet].getDataElementAt(whichBar).x;
/*    */       else
/* 63 */         llx = whichBar;
/* 64 */       double lly = this.datasets[whichSet].getDataElementAt(whichBar).y3;
/* 65 */       double ury = this.datasets[whichSet].getDataElementAt(whichBar).y2;
/* 66 */       double closeY = this.datasets[whichSet].getDataElementAt(whichBar).y3;
/* 67 */       urx = llx;
/*    */     }
/*    */     catch (ArrayIndexOutOfBoundsException e)
/*    */     {
/*    */       double urx;
/*    */       return;
/*    */     }
/*    */     double ury;
/*    */     double urx;
/*    */     double closeY;
/*    */     double lly;
/*    */     double llx;
/* 72 */     this.datasets[whichSet].gc.drawLine(
/* 73 */       g, 
/* 74 */       this.dataXfm.point(llx, lly), 
/* 75 */       this.dataXfm.point(urx, ury));
/* 76 */     Point p1 = this.dataXfm.point(llx, closeY);
/* 77 */     Point p2 = new Point(p1.x + offset, p1.y);
/* 78 */     this.datasets[whichSet].gc.drawLine(g, p1, p2);
/*    */ 
/* 80 */     if ((this.useDisplayList) && (this.globals.useDisplayList))
/*    */     {
/* 82 */       this.globals.displayList.addLine(
/* 83 */         this.datasets[whichSet], 
/* 84 */         this.dataXfm.point(llx, lly), 
/* 85 */         this.dataXfm.point(urx, ury));
/* 86 */       this.globals.displayList.addLine(
/* 87 */         this.datasets[whichSet].getDataElementAt(whichBar), 
/* 88 */         this.dataXfm.point(llx, lly), 
/* 89 */         this.dataXfm.point(urx, ury));
/* 90 */       this.globals.displayList.addLine(
/* 91 */         this, 
/* 92 */         this.dataXfm.point(llx, lly), 
/* 93 */         this.dataXfm.point(urx, ury));
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void doBarLabel(Graphics g, double howWide, int whichSet, int whichBar)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void doDBar(Graphics g, double howWide, int whichSet, int whichBar, boolean individualColors)
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.HiLoClose
 * JD-Core Version:    0.6.2
 */