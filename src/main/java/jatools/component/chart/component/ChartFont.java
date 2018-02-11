/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import jatools.accessor.AutoAccessor;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ 
/*     */ public class ChartFont extends AutoAccessor
/*     */ {
/*     */   Color foreColor;
/*     */   Color backColor;
/*     */   int style;
/*     */   int size;
/*     */   String face;
/*     */ 
/*     */   public ChartFont(String face, int style, int size, Color foreColor, Color backColor)
/*     */   {
/*  46 */     this.face = face;
/*  47 */     this.size = size;
/*  48 */     this.backColor = backColor;
/*  49 */     this.foreColor = foreColor;
/*  50 */     this.style = style;
/*     */   }
/*     */ 
/*     */   public ChartFont()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g, String text)
/*     */   {
/*  66 */     paint(g, text, 0, 0);
/*     */   }
/*     */ 
/*     */   public void paint(Graphics g, String text, int x, int y)
/*     */   {
/*  82 */     if ((text != null) && (!text.equals(""))) {
/*  83 */       g.setFont(asFont());
/*     */ 
/*  85 */       FontMetrics fm = g.getFontMetrics();
/*  86 */       int textHeight = fm.getHeight();
/*     */ 
/*  88 */       if (this.backColor != null) {
/*  89 */         int textWidth = fm.stringWidth(text);
/*  90 */         g.setColor(this.backColor);
/*  91 */         g.fillRect(x, y, textWidth, textHeight);
/*     */       }
/*     */ 
/*  94 */       g.setColor(this.foreColor);
/*  95 */       g.drawString(text, x, y + textHeight);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Font asFont()
/*     */   {
/* 103 */     return new Font(this.face, this.style, this.size);
/*     */   }
/*     */ 
/*     */   public Color getBackColor()
/*     */   {
/* 112 */     return this.backColor;
/*     */   }
/*     */ 
/*     */   public String getFace()
/*     */   {
/* 121 */     return this.face;
/*     */   }
/*     */ 
/*     */   public Color getForeColor()
/*     */   {
/* 130 */     return this.foreColor;
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/* 139 */     return this.size;
/*     */   }
/*     */ 
/*     */   public int getStyle()
/*     */   {
/* 148 */     return this.style;
/*     */   }
/*     */ 
/*     */   public void setStyle(int style)
/*     */   {
/* 158 */     this.style = style;
/*     */   }
/*     */ 
/*     */   public void setForeColor(Color foreColor)
/*     */   {
/* 168 */     this.foreColor = foreColor;
/*     */   }
/*     */ 
/*     */   public void setFace(String face)
/*     */   {
/* 178 */     this.face = face;
/*     */   }
/*     */ 
/*     */   public void setBackColor(Color backColor)
/*     */   {
/* 188 */     this.backColor = backColor;
/*     */   }
/*     */ 
/*     */   public void setSize(int size)
/*     */   {
/* 198 */     this.size = size;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 207 */     StringBuffer buffer = new StringBuffer();
/* 208 */     buffer.append("[ZFont:");
/* 209 */     buffer.append(" foreColor: ");
/* 210 */     buffer.append(this.foreColor);
/* 211 */     buffer.append(" backColor: ");
/* 212 */     buffer.append(this.backColor);
/* 213 */     buffer.append(" style: ");
/* 214 */     buffer.append(this.style);
/* 215 */     buffer.append(" size: ");
/* 216 */     buffer.append(this.size);
/* 217 */     buffer.append(" face: ");
/* 218 */     buffer.append(this.face);
/* 219 */     buffer.append("]");
/*     */ 
/* 221 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 230 */     ChartFont inst = new ChartFont();
/* 231 */     inst.foreColor = this.foreColor;
/* 232 */     inst.backColor = this.backColor;
/* 233 */     inst.style = this.style;
/* 234 */     inst.size = this.size;
/* 235 */     inst.face = this.face;
/*     */ 
/* 237 */     return inst;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ChartFont
 * JD-Core Version:    0.6.2
 */