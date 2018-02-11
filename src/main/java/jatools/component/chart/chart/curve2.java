/*    */ package jatools.component.chart.chart;
/*    */ 
/*    */ import java.awt.Frame;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.geom.CubicCurve2D.Double;
/*    */ import java.awt.geom.Point2D.Double;
/*    */ 
/*    */ public class curve2 extends Frame
/*    */ {
/* 11 */   private int x1 = 200;
/* 12 */   private int y1 = 200;
/*    */ 
/* 14 */   private int ctrlx = 300;
/* 15 */   private int ctrly = 300;
/*    */ 
/* 17 */   private int x2 = 300;
/* 18 */   private int y2 = 500;
/*    */ 
/*    */   public curve2()
/*    */   {
/* 22 */     super("First Bezier Curve");
/* 23 */     setSize(600, 600);
/*    */ 
/* 25 */     setVisible(true);
/*    */   }
/*    */ 
/*    */   public void paint(Graphics g)
/*    */   {
/* 30 */     Point2D.Double P1 = new Point2D.Double(50.0D, 75.0D);
/* 31 */     Point2D.Double P2 = new Point2D.Double(150.0D, 75.0D);
/* 32 */     Point2D.Double ctrl1 = new Point2D.Double(80.0D, 25.0D);
/* 33 */     Point2D.Double ctrl2 = new Point2D.Double(160.0D, 100.0D);
/*    */ 
/* 37 */     CubicCurve2D.Double cubicCurve = new CubicCurve2D.Double(P1.x, P1.y, ctrl1.x, ctrl1.y, ctrl2.x, ctrl2.y, P2.x, P2.y);
/*    */ 
/* 39 */     Graphics2D g2 = (Graphics2D)g;
/* 40 */     g2.draw(cubicCurve);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 44 */     new curve2();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.curve2
 * JD-Core Version:    0.6.2
 */