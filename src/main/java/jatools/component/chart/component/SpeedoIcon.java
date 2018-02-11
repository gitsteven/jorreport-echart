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
/*     */ public class SpeedoIcon
/*     */   implements Icon
/*     */ {
/*     */   int iconType;
/*     */   int width;
/*     */   int height;
/*     */ 
/*     */   public SpeedoIcon(Dimension d, int iconType)
/*     */   {
/*  20 */     this(d.width, d.height, iconType);
/*     */   }
/*     */ 
/*     */   public SpeedoIcon(int width, int height, int iconType) {
/*  24 */     this.width = width;
/*  25 */     this.height = height;
/*  26 */     this.iconType = iconType;
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
/*  44 */     int lux = 0; int luy = 0; int aw = 0; int ah = 0; int startAngle = 0; int endAngle = 0;
/*  45 */     switch (this.iconType) {
/*     */     case 0:
/*  47 */       lux = x + this.width / 4;
/*  48 */       luy = y + this.height / 4;
/*  49 */       aw = this.width / 2;
/*  50 */       ah = this.height;
/*  51 */       startAngle = 0;
/*  52 */       endAngle = 180;
/*  53 */       break;
/*     */     case 1:
/*  55 */       lux = x + this.width / 4;
/*  56 */       luy = y - this.height / 4;
/*  57 */       aw = this.width / 2;
/*  58 */       ah = this.height;
/*  59 */       startAngle = 180;
/*  60 */       endAngle = 180;
/*  61 */       break;
/*     */     case 2:
/*  64 */       lux = x + this.width / 4;
/*  65 */       luy = y + this.height / 4;
/*  66 */       aw = this.width;
/*  67 */       ah = this.height / 2;
/*  68 */       startAngle = 90;
/*  69 */       endAngle = 180;
/*  70 */       break;
/*     */     case 3:
/*  73 */       lux = x - this.width / 4;
/*  74 */       luy = y + this.height / 4;
/*  75 */       aw = this.width;
/*  76 */       ah = this.height / 2;
/*  77 */       startAngle = 270;
/*  78 */       endAngle = 180;
/*  79 */       break;
/*     */     case 4:
/*  82 */       lux = x - this.width / 4;
/*  83 */       luy = y + this.height / 4;
/*  84 */       aw = this.width;
/*  85 */       ah = this.height;
/*  86 */       startAngle = 0;
/*  87 */       endAngle = 90;
/*  88 */       break;
/*     */     case 5:
/*  91 */       lux = x + this.width / 4;
/*  92 */       luy = y + this.height / 4;
/*  93 */       aw = this.width;
/*  94 */       ah = this.height;
/*  95 */       startAngle = 90;
/*  96 */       endAngle = 90;
/*  97 */       break;
/*     */     case 6:
/* 100 */       lux = x + this.width / 4;
/* 101 */       luy = y - this.height / 4;
/* 102 */       aw = this.width;
/* 103 */       ah = this.height;
/* 104 */       startAngle = 180;
/* 105 */       endAngle = 90;
/* 106 */       break;
/*     */     case 7:
/* 109 */       lux = x - this.width / 4;
/* 110 */       luy = y - this.height / 4;
/* 111 */       aw = this.width;
/* 112 */       ah = this.height;
/* 113 */       startAngle = 270;
/* 114 */       endAngle = 90;
/*     */     }
/*     */ 
/* 118 */     g.drawArc(lux, luy, aw, ah, startAngle, endAngle);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 122 */     JFrame f = new JFrame();
/* 123 */     JPanel p = new JPanel();
/* 124 */     JLabel l = new JLabel();
/* 125 */     l.setIcon(new SpeedoIcon(100, 100, 7));
/* 126 */     p.add(l);
/* 127 */     f.setSize(400, 400);
/* 128 */     f.getContentPane().add(p);
/* 129 */     f.setVisible(true);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.SpeedoIcon
 * JD-Core Version:    0.6.2
 */