/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ 
/*    */ public class MarkerIcon extends AbstractColorIcon
/*    */ {
/* 14 */   int size = 10;
/*    */ 
/* 16 */   int style = 1;
/*    */ 
/* 18 */   Color color = Color.red;
/*    */ 
/*    */   public MarkerIcon(int width, int height) {
/* 21 */     super(width, height);
/*    */   }
/*    */ 
/*    */   public MarkerIcon(Dimension d) {
/* 25 */     super(d.width, d.height);
/*    */   }
/*    */ 
/*    */   protected void setStyle(FillStyleInterface style) {
/* 29 */     this.size = ((LineStyle)style).markerSize;
/* 30 */     this.style = ((LineStyle)style).markerStyle;
/* 31 */     this.color = ((LineStyle)style).color;
/*    */   }
/*    */ 
/*    */   public void paintIcon(Component c, Graphics g1, int x, int y)
/*    */   {
/* 36 */     Graphics g = g1.create();
/* 37 */     if (this.color == null)
/* 38 */       return;
/* 39 */     if (this.color.equals(Color.WHITE))
/* 40 */       g.setColor(Color.DARK_GRAY);
/*    */     else {
/* 42 */       g.setColor(Color.WHITE);
/*    */     }
/* 44 */     g.fillRect(x, y, this.width, this.height);
/* 45 */     g.setColor(this.color);
/* 46 */     int xc = x + this.width / 2;
/* 47 */     int yc = y + this.height / 2;
/* 48 */     int halfSize = this.height / 4;
/* 49 */     switch (this.style) {
/*    */     case 0:
/* 51 */       g.fillRect(xc - halfSize, yc - halfSize, this.height / 2, this.height / 2);
/* 52 */       break;
/*    */     case 1:
/* 55 */       int[] xArr = new int[4];
/* 56 */       int[] yArr = new int[4];
/* 57 */       xArr[0] = (xc - halfSize);
/* 58 */       yArr[0] = yc;
/* 59 */       xArr[1] = xc;
/* 60 */       yArr[1] = (yc + halfSize);
/* 61 */       xArr[2] = (xc + halfSize);
/* 62 */       yArr[2] = yc;
/* 63 */       xArr[3] = xc;
/* 64 */       yArr[3] = (yc - halfSize);
/* 65 */       g.fillPolygon(xArr, yArr, 4);
/* 66 */       break;
/*    */     case 2:
/* 69 */       g.fillOval(xc - halfSize, yc - halfSize, this.height / 2, this.height / 2);
/* 70 */       break;
/*    */     case 3:
/* 73 */       int[] xArr = new int[3];
/* 74 */       int[] yArr = new int[3];
/* 75 */       xArr[0] = (xc - halfSize);
/* 76 */       yArr[0] = (yc + halfSize);
/* 77 */       xArr[1] = xc;
/* 78 */       yArr[1] = (yc - halfSize);
/* 79 */       xArr[2] = (xc + halfSize);
/* 80 */       yArr[2] = (yc + halfSize);
/* 81 */       g.fillPolygon(xArr, yArr, 3);
/*    */     }
/*    */ 
/* 84 */     g.setColor(Color.BLACK);
/* 85 */     g.drawRect(x, y, this.width, this.height);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.MarkerIcon
 * JD-Core Version:    0.6.2
 */