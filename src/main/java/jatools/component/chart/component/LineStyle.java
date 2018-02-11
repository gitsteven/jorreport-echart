/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ public class LineStyle
/*     */   implements FillStyleInterface
/*     */ {
/*  17 */   Color color = Color.red;
/*     */ 
/*  19 */   int lineStyle = -1;
/*     */ 
/*  21 */   int lineWidth = 1;
/*     */ 
/*  23 */   int markerSize = 10;
/*     */ 
/*  25 */   int markerStyle = -1;
/*     */ 
/*  27 */   int iconType = 0;
/*     */   public boolean full;
/*     */ 
/*     */   public LineStyle(boolean full)
/*     */   {
/*  32 */     this.full = full;
/*     */   }
/*     */ 
/*     */   public Component createLabel(Dimension d) {
/*  36 */     return new HeaderLabel(this, d);
/*     */   }
/*     */ 
/*     */   public Icon createIcon(Dimension d)
/*     */   {
/*     */     AbstractColorIcon icon;
/*     */     AbstractColorIcon icon;
/*  41 */     if (this.iconType == 0)
/*  42 */       icon = new LineIcon(d);
/*     */     else {
/*  44 */       icon = new MarkerIcon(d);
/*     */     }
/*  46 */     icon.setStyle(this);
/*  47 */     return icon;
/*     */   }
/*     */ 
/*     */   public int getType() {
/*  51 */     return -1;
/*     */   }
/*     */ 
/*     */   public void setToGc(Gc gc) {
/*  55 */     gc.setLineColor(this.color);
/*  56 */     gc.setLineStyle(this.lineStyle);
/*  57 */     gc.setLineWidth(this.lineWidth);
/*  58 */     gc.setFillColor(this.color);
/*  59 */     gc.setMarkerSize(this.markerSize);
/*  60 */     gc.setMarkerStyle(this.markerStyle);
/*     */   }
/*     */ 
/*     */   public void getFromGc(Gc gc) {
/*  64 */     this.color = gc.getLineColor();
/*  65 */     this.lineStyle = gc.getLineStyle();
/*  66 */     this.lineWidth = gc.getLineWidth();
/*  67 */     this.markerSize = gc.getMarkerSize();
/*  68 */     this.markerStyle = gc.getMarkerStyle();
/*     */   }
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 141 */     return this.color;
/*     */   }
/*     */ 
/*     */   public void setColor(Color color) {
/* 145 */     this.color = color;
/*     */   }
/*     */ 
/*     */   public int getLineStyle() {
/* 149 */     return this.lineStyle;
/*     */   }
/*     */ 
/*     */   public void setLineStyle(int lineStyle) {
/* 153 */     this.lineStyle = lineStyle;
/*     */   }
/*     */ 
/*     */   public int getLineWidth() {
/* 157 */     return this.lineWidth;
/*     */   }
/*     */ 
/*     */   public void setLineWidth(int width) {
/* 161 */     this.lineWidth = width;
/*     */   }
/*     */ 
/*     */   public int getMarkerSize() {
/* 165 */     return this.markerSize;
/*     */   }
/*     */ 
/*     */   public void setMarkerSize(int markerSize) {
/* 169 */     this.markerSize = markerSize;
/*     */   }
/*     */ 
/*     */   public int getMarkerStyle() {
/* 173 */     return this.markerStyle;
/*     */   }
/*     */ 
/*     */   public void setMarkerStyle(int markerStyle) {
/* 177 */     this.markerStyle = markerStyle;
/*     */   }
/*     */ 
/*     */   public int getIconType() {
/* 181 */     return this.iconType;
/*     */   }
/*     */ 
/*     */   public void setIconType(int iconType) {
/* 185 */     this.iconType = iconType;
/*     */   }
/*     */ 
/*     */   class HeaderLabel extends JLabel
/*     */   {
/*     */     LineStyle lineStyle;
/*     */     int width;
/*     */     int height;
/*     */ 
/*     */     public HeaderLabel(LineStyle style, Dimension d)
/*     */     {
/*  80 */       this.lineStyle = style;
/*  81 */       this.width = d.width;
/*  82 */       this.height = d.height;
/*     */     }
/*     */ 
/*     */     protected void paintComponent(Graphics g) {
/*  86 */       FontMetrics fm = g.getFontMetrics();
/*  87 */       this.lineStyle.iconType = 0;
/*  88 */       Icon lineIcon = this.lineStyle.createIcon(new Dimension(20, 
/*  89 */         10));
/*  90 */       this.lineStyle.iconType = 1;
/*  91 */       Icon markerIcon = this.lineStyle.createIcon(new Dimension(20, 
/*  92 */         10));
/*  93 */       String llabel = null;
/*  94 */       switch (this.lineStyle.lineStyle) {
/*     */       case -1:
/*  96 */         llabel = "实线";
/*  97 */         break;
/*     */       case 0:
/*  99 */         llabel = "虚线";
/* 100 */         break;
/*     */       case 1:
/* 102 */         llabel = "点线";
/* 103 */         break;
/*     */       case 2:
/* 105 */         llabel = "点划线";
/*     */       }
/*     */ 
/* 108 */       String mlabel = null;
/* 109 */       switch (this.lineStyle.markerStyle) {
/*     */       case -1:
/* 111 */         mlabel = "无";
/* 112 */         break;
/*     */       case 0:
/* 114 */         mlabel = "方形";
/* 115 */         break;
/*     */       case 1:
/* 117 */         mlabel = "菱形";
/* 118 */         break;
/*     */       case 3:
/* 120 */         mlabel = "三角形";
/* 121 */         break;
/*     */       case 2:
/* 123 */         mlabel = "圆形";
/*     */       }
/*     */ 
/* 127 */       lineIcon.paintIcon(this, g, 3, 3);
/* 128 */       g.setColor(Color.BLACK);
/* 129 */       g.drawString(llabel, 26, this.height / 2 + 3);
/* 130 */       if (LineStyle.this.full) {
/* 131 */         int fw = fm.stringWidth(llabel);
/* 132 */         markerIcon.paintIcon(this, g, 29 + fw, 3);
/* 133 */         g.setColor(Color.BLACK);
/* 134 */         g.drawString(mlabel, 52 + fw, this.height / 2 + 3);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.LineStyle
 * JD-Core Version:    0.6.2
 */