/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import java.awt.BasicStroke;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Stroke;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JLabel;
/*    */ 
/*    */ public class LineIcon extends AbstractColorIcon
/*    */ {
/* 19 */   static float[] dashArray = { 5.0F };
/*    */ 
/* 21 */   static float[] dotArray = { 2.0F };
/*    */ 
/* 23 */   static float[] dotDashArray = { 2.0F, 2.0F, 5.0F, 2.0F };
/*    */ 
/* 25 */   float[][] lineStyles = { dashArray, dotArray, dotDashArray };
/*    */ 
/* 27 */   Color color = Color.RED;
/* 28 */   int lineStyle = -1;
/* 29 */   int lineWidth = 1;
/*    */ 
/*    */   public LineIcon(int width, int height) {
/* 32 */     super(width, height);
/*    */   }
/*    */ 
/*    */   public LineIcon(Dimension d) {
/* 36 */     super(d.width, d.height);
/*    */   }
/*    */ 
/*    */   protected void setStyle(FillStyleInterface style) {
/* 40 */     this.color = ((LineStyle)style).color;
/* 41 */     this.lineStyle = ((LineStyle)style).lineStyle;
/* 42 */     this.lineWidth = ((LineStyle)style).lineWidth;
/*    */   }
/*    */ 
/*    */   public void paintIcon(Component c, Graphics g, int x, int y)
/*    */   {
/* 47 */     Graphics2D g2d = (Graphics2D)g.create();
/* 48 */     if (this.color == null) return;
/* 49 */     if (this.color.equals(Color.WHITE))
/* 50 */       g2d.setColor(Color.DARK_GRAY);
/*    */     else {
/* 52 */       g2d.setColor(Color.WHITE);
/*    */     }
/* 54 */     g2d.fillRect(x, y, this.width, this.height);
/* 55 */     g2d.setColor(Color.BLACK);
/* 56 */     g2d.drawRect(x, y, this.width, this.height);
/* 57 */     g2d.setColor(this.color);
/*    */     Stroke stroke;
/*    */     try
/*    */     {
/*    */       Stroke stroke;
/* 59 */       if (this.lineStyle != -1)
/* 60 */         stroke = new BasicStroke(this.lineWidth, 
/* 61 */           0, 0, 
/* 62 */           10.0F, this.lineStyles[this.lineStyle], 0.0F);
/*    */       else
/* 64 */         stroke = new BasicStroke(this.lineWidth);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/*    */       Stroke stroke;
/* 66 */       stroke = new BasicStroke(this.lineWidth);
/*    */     }
/*    */ 
/* 69 */     g2d.setStroke(stroke);
/* 70 */     g2d.drawLine(x + 2, y + this.height / 2, x + this.width - 3, y + this.height / 2);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 74 */     JFrame frame = new JFrame();
/* 75 */     frame.setSize(400, 400);
/* 76 */     JLabel label = new JLabel();
/* 77 */     label.setIcon(new LineIcon(100, 100));
/* 78 */     frame.getContentPane().add(label);
/* 79 */     frame.setVisible(true);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.LineIcon
 * JD-Core Version:    0.6.2
 */