/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.accessor.ProtectPublic;
/*     */ import java.awt.Image;
/*     */ import java.io.Serializable;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class Globals
/*     */   implements Serializable, ProtectPublic
/*     */ {
/*  23 */   int xOffset = 15;
/*  24 */   int yOffset = 15;
/*  25 */   boolean threeD = false;
/*     */   int maxY;
/*     */   int maxX;
/*     */   RotateString stringRotator;
/*     */   transient Image image;
/*  30 */   boolean useDisplayList = false;
/*     */   DisplayList displayList;
/*  32 */   public boolean java2Capable = false;
/*  33 */   public Locale locale = Locale.getDefault();
/*     */ 
/*     */   public Globals()
/*     */   {
/*     */     try
/*     */     {
/*  40 */       String version = System.getProperty("java.version");
/*  41 */       int index = version.indexOf(".");
/*  42 */       double mainVersion = Double.valueOf(
/*  43 */         version.substring(index - 1, index + 2)).doubleValue();
/*  44 */       if (mainVersion >= 1.2D)
/*     */       {
/*  46 */         Class c = Class.forName("java.awt.Graphics2D");
/*  47 */         this.java2Capable = true;
/*     */       }
/*     */       else {
/*  50 */         this.java2Capable = false;
/*     */       }
/*     */     } catch (Exception e) {
/*  53 */       this.java2Capable = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Globals(int topY, int xDepth, int yDepth, boolean showDepth, RotateString r, Image i)
/*     */   {
/*  72 */     this();
/*  73 */     this.maxY = topY;
/*  74 */     this.xOffset = xDepth;
/*  75 */     this.yOffset = yDepth;
/*  76 */     this.threeD = showDepth;
/*  77 */     this.stringRotator = r;
/*  78 */     this.image = i;
/*     */   }
/*     */ 
/*     */   public DisplayList getDisplayList()
/*     */   {
/*  87 */     return this.displayList;
/*     */   }
/*     */ 
/*     */   public Image getImage()
/*     */   {
/*  96 */     return this.image;
/*     */   }
/*     */ 
/*     */   public int getMaxX()
/*     */   {
/* 104 */     return this.maxX;
/*     */   }
/*     */ 
/*     */   public int getMaxY()
/*     */   {
/* 112 */     return this.maxY;
/*     */   }
/*     */ 
/*     */   public RotateString getStringRotator()
/*     */   {
/* 120 */     return this.stringRotator;
/*     */   }
/*     */ 
/*     */   public boolean getUseDisplayList()
/*     */   {
/* 130 */     return this.useDisplayList;
/*     */   }
/*     */ 
/*     */   public int getXOffset()
/*     */   {
/* 138 */     return this.xOffset;
/*     */   }
/*     */ 
/*     */   public int getYOffset()
/*     */   {
/* 146 */     return this.yOffset;
/*     */   }
/*     */ 
/*     */   public boolean isThreeD()
/*     */   {
/* 154 */     return this.threeD;
/*     */   }
/*     */ 
/*     */   public void setDisplayList(DisplayList d)
/*     */   {
/* 162 */     this.displayList = d;
/* 163 */     d.globals = this;
/*     */   }
/*     */ 
/*     */   public void setImage(Image i)
/*     */   {
/* 172 */     this.image = i;
/*     */   }
/*     */ 
/*     */   public void setMaxX(int i)
/*     */   {
/* 180 */     this.maxX = i;
/*     */   }
/*     */ 
/*     */   public void setMaxY(int i)
/*     */   {
/* 188 */     this.maxY = i;
/*     */   }
/*     */ 
/*     */   public void setStringRotator(RotateString r)
/*     */   {
/* 196 */     this.stringRotator = r;
/*     */   }
/*     */ 
/*     */   public void setThreeD(boolean depth)
/*     */   {
/* 204 */     this.threeD = depth;
/*     */   }
/*     */ 
/*     */   public void setUseDisplayList(boolean onOff)
/*     */   {
/* 212 */     if ((onOff) && (this.displayList == null))
/* 213 */       this.displayList = new DisplayList(this);
/* 214 */     this.useDisplayList = onOff;
/*     */   }
/*     */ 
/*     */   public void setXOffset(int x)
/*     */   {
/* 222 */     this.xOffset = x;
/*     */   }
/*     */ 
/*     */   public void setYOffset(int y)
/*     */   {
/* 230 */     this.yOffset = y;
/*     */   }
/*     */ 
/*     */   public Locale getLocale()
/*     */   {
/* 241 */     return this.locale;
/*     */   }
/*     */ 
/*     */   public void setLocale(Locale newLocale)
/*     */   {
/* 250 */     this.locale = newLocale;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Globals
 * JD-Core Version:    0.6.2
 */