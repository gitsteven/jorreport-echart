/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class ArrowIcon
/*     */   implements Icon
/*     */ {
/*     */   int arrowType;
/*     */   int width;
/*     */   int height;
/*     */ 
/*     */   public ArrowIcon(Dimension d, int arrowType)
/*     */   {
/*  18 */     this.width = d.width;
/*  19 */     this.height = d.height;
/*  20 */     this.arrowType = arrowType;
/*     */   }
/*     */ 
/*     */   public ArrowIcon(int width, int height, int arrowType) {
/*  24 */     this.width = width;
/*  25 */     this.height = height;
/*  26 */     this.arrowType = arrowType;
/*     */   }
/*     */ 
/*     */   public int getIconHeight() {
/*  30 */     return this.height;
/*     */   }
/*     */ 
/*     */   public int getIconWidth() {
/*  34 */     return this.width;
/*     */   }
/*     */ 
/*     */   public void paintIcon(Component c, Graphics g1, int x, int y) {
/*  38 */     Graphics g = g1.create();
/*  39 */     g.setColor(Color.WHITE);
/*  40 */     g.fillRect(x, y, this.width, this.height);
/*  41 */     g.setColor(Color.BLACK);
/*  42 */     g.drawRect(x, y, this.width, this.height);
/*  43 */     g.setColor(Color.red);
/*  44 */     int endx = x + this.width / 4;
/*  45 */     int endy = y + this.height / 4;
/*  46 */     int startx = x + this.width * 3 / 4;
/*  47 */     int starty = y + this.height * 3 / 4;
/*  48 */     switch (this.arrowType) {
/*     */     case 0:
/*  50 */       g.drawLine(startx, starty, endx, endy);
/*  51 */       double radians = 0.6806784082777885D;
/*  52 */       double arrowEndx = endx + this.width * 0.45D * Math.cos(radians);
/*  53 */       double arrowEndy = endy + this.height * 0.45D * Math.sin(radians);
/*  54 */       g.drawLine(endx, endy, (int)arrowEndx, (int)arrowEndy);
/*  55 */       radians = 0.9948376736367678D;
/*  56 */       arrowEndx = endx + this.width * 0.45D * Math.cos(radians);
/*  57 */       arrowEndy = endy + this.height * 0.45D * Math.sin(radians);
/*  58 */       g.drawLine(endx, endy, (int)arrowEndx, (int)arrowEndy);
/*  59 */       break;
/*     */     case 1:
/*  61 */       g.drawLine(startx, starty, endx, endy);
/*  62 */       break;
/*     */     case 2:
/*  64 */       doFatPointer(g, startx, starty, endx, endy);
/*  65 */       break;
/*     */     case 3:
/*  67 */       g.fillArc(endx, endy, (startx - endx) * 2, (startx - endx) * 2, 115, 65);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doFatPointer(Graphics g, int startx, int starty, int endx, int endy)
/*     */   {
/*  79 */     double r = Math.cos(0.7853981633974483D);
/*  80 */     int arrowHeight = 3;
/*  81 */     int arrowWidth = 2;
/*  82 */     int llx = (int)(startx - r * arrowWidth);
/*  83 */     int lly = (int)(starty + r * arrowWidth);
/*  84 */     int lrx = (int)(startx + r * arrowWidth);
/*  85 */     int lry = (int)(starty - r * arrowWidth);
/*  86 */     int urx = (int)(lrx - r * (startx - endx - arrowHeight));
/*  87 */     int ury = (int)(lry - r * (starty - endy - arrowHeight));
/*  88 */     int ulx = (int)(llx - r * (startx - endx - arrowHeight));
/*  89 */     int uly = (int)(lly - r * (startx - endx - arrowHeight));
/*     */ 
/*  91 */     int[] xpoints = { llx, lrx, urx, endx, ulx };
/*  92 */     int[] ypoints = { lly, lry, ury, endy, uly };
/*  93 */     g.fillPolygon(xpoints, ypoints, 5);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/*  97 */     JFrame f = new JFrame();
/*  98 */     JPanel p = new JPanel();
/*  99 */     JLabel l = new JLabel();
/* 100 */     l.setIcon(new ArrowIcon(100, 100, 3));
/* 101 */     p.add(l);
/* 102 */     f.setSize(400, 400);
/* 103 */     f.getContentPane().add(p);
/* 104 */     f.setVisible(true);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ArrowIcon
 * JD-Core Version:    0.6.2
 */