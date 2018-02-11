/*     */ package jatools.component.chart.utility;
/*     */ 
/*     */ import com.anotherbigidea.flash.movie.FontDefinition;
/*     */ import com.anotherbigidea.flash.movie.FontDefinition.Glyph;
/*     */ import com.anotherbigidea.flash.movie.FontDefinition.KerningPair;
/*     */ import jatools.engine.ProtectClass;
/*     */ import java.awt.FontFormatException;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.GlyphMetrics;
/*     */ import java.awt.font.GlyphVector;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class SystemFontDefinition extends FontDefinition
/*     */   implements ProtectClass
/*     */ {
/*  53 */   private static FontRenderContext frc = g2d.getFontRenderContext();
/*     */   private static Graphics2D g2d;
/*     */   private static final boolean DEBUG_GETSHAPE = false;
/*     */   private static final boolean DEBUG_KERNING = false;
/*     */   private static final float HUGE_FONT_SIZE = 512.0F;
/*     */   private FontMetrics metrics;
/*     */   private java.awt.Font font;
/*     */   private AffineTransform trans;
/*     */   private double scale;
/*     */   static com.anotherbigidea.flash.movie.Font defaultFont;
/*     */   static com.anotherbigidea.flash.movie.Font dialogFont;
/*     */ 
/*     */   static
/*     */   {
/*  46 */     BufferedImage dummyImage = new BufferedImage(1, 1, 
/*  47 */       6);
/*  48 */     g2d = (Graphics2D)dummyImage.getGraphics();
/*  49 */     g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
/*  50 */       RenderingHints.VALUE_ANTIALIAS_ON);
/*  51 */     g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, 
/*  52 */       RenderingHints.VALUE_FRACTIONALMETRICS_ON);
/*     */   }
/*     */ 
/*     */   public SystemFontDefinition(java.awt.Font sfont)
/*     */   {
/*  73 */     this.font = sfont.deriveFont(512.0F);
/*  74 */     this.metrics = g2d.getFontMetrics(this.font);
/*     */ 
/*  76 */     double maxHeight = this.metrics.getAscent();
/*     */ 
/*  83 */     this.scale = (51.200000000000003D / maxHeight);
/*     */ 
/*  85 */     this.trans = AffineTransform.getScaleInstance(this.scale, this.scale);
/*     */ 
/*  87 */     setName(this.font.getName());
/*  88 */     setFontFlags(true, false, false, this.font.isItalic(), this.font.isBold(), true);
/*  89 */     setAscent(this.metrics.getAscent() * this.scale);
/*  90 */     setDescent(this.metrics.getDescent() * this.scale);
/*  91 */     setLeading(this.metrics.getLeading() * this.scale);
/*     */   }
/*     */ 
/*     */   public boolean canDisplayAll(String text) {
/*  95 */     return this.font.canDisplayUpTo(text) == -1;
/*     */   }
/*     */ 
/*     */   public static com.anotherbigidea.flash.movie.Font getDefaultFlashFont()
/*     */   {
/* 102 */     if (defaultFont == null) {
/* 103 */       InputStream is = SystemFontDefinition.class
/* 104 */         .getResourceAsStream("/com/jatools/icons/simsun.ttc");
/*     */ 
/* 106 */       java.awt.Font font = null;
/*     */       try {
/* 108 */         font = 
/* 109 */           java.awt.Font.createFont(0, is);
/*     */       }
/*     */       catch (FontFormatException e) {
/* 112 */         e.printStackTrace();
/*     */       }
/*     */       catch (IOException e) {
/* 115 */         e.printStackTrace();
/*     */       }
/*     */ 
/* 118 */       SystemFontDefinition fd = new SystemFontDefinition(font);
/* 119 */       defaultFont = new com.anotherbigidea.flash.movie.Font(fd);
/*     */     }
/*     */ 
/* 123 */     return defaultFont;
/*     */   }
/*     */ 
/*     */   public static com.anotherbigidea.flash.movie.Font getDialogFlashFont()
/*     */   {
/* 130 */     if (dialogFont == null)
/*     */     {
/* 132 */       SystemFontDefinition fd = new SystemFontDefinition(
/* 133 */         new java.awt.Font("Dialog", 0, 12));
/* 134 */       dialogFont = new com.anotherbigidea.flash.movie.Font(fd);
/*     */     }
/*     */ 
/* 138 */     return dialogFont;
/*     */   }
/*     */ 
/*     */   private com.anotherbigidea.flash.movie.Shape getShape(GlyphVector gv, int pos, AffineTransform tran)
/*     */   {
/* 143 */     java.awt.Shape shape = gv.getGlyphOutline(pos);
/*     */ 
/* 145 */     PathIterator pi = shape.getPathIterator(tran);
/*     */ 
/* 147 */     com.anotherbigidea.flash.movie.Shape returns = new com.anotherbigidea.flash.movie.Shape();
/*     */ 
/* 149 */     double[] values = new double[6];
/*     */ 
/* 152 */     boolean closed = true;
/*     */ 
/* 154 */     double beginX = -1.0D;
/* 155 */     double beginY = -1.0D;
/*     */ 
/* 157 */     double lastX = -1.0D;
/* 158 */     double lastY = -1.0D;
/*     */ 
/* 160 */     while (!pi.isDone())
/*     */     {
/* 162 */       int type = pi.currentSegment(values);
/* 163 */       for (int i = 0; i < values.length; i++) {
/* 164 */         values[i] = (Math.rint(10.0D * values[i]) / 10.0D);
/*     */       }
/*     */ 
/* 169 */       if (type == 0)
/*     */       {
/* 171 */         closed = false;
/* 172 */         beginX = values[0];
/* 173 */         beginY = values[1];
/*     */ 
/* 175 */         returns.move(values[0], values[1]);
/* 176 */       } else if (type == 1)
/*     */       {
/* 178 */         lastX = values[0];
/* 179 */         lastY = values[1];
/* 180 */         returns.line(lastX, lastY);
/* 181 */       } else if (type == 4)
/*     */       {
/* 183 */         if (!closed) {
/* 184 */           closed = true;
/*     */ 
/* 186 */           if ((beginX != lastX) || (beginY != lastY)) {
/* 187 */             returns.line(beginX, beginY);
/*     */           }
/*     */         }
/*     */       }
/* 191 */       else if (type == 2)
/*     */       {
/* 193 */         lastX = values[2];
/* 194 */         lastY = values[3];
/*     */ 
/* 196 */         returns.curve(lastX, lastY, values[0], values[1]);
/*     */       }
/*     */ 
/* 205 */       pi.next();
/*     */     }
/*     */ 
/* 209 */     return returns;
/*     */   }
/*     */ 
/*     */   private FontDefinition.Glyph createGlyph(int code)
/*     */   {
/* 220 */     char[] chars = { (char)code };
/*     */ 
/* 222 */     if (!this.font.canDisplay((char)code)) {
/* 223 */       return null;
/*     */     }
/* 225 */     GlyphVector gv = this.font.createGlyphVector(frc, chars);
/* 226 */     com.anotherbigidea.flash.movie.Shape s = getShape(gv, 0, this.trans);
/* 227 */     GlyphMetrics gm = gv.getGlyphMetrics(0);
/* 228 */     FontDefinition.Glyph g = new FontDefinition.Glyph(s, this.metrics
/* 229 */       .charWidth(chars[0]) * 
/* 230 */       this.scale, code);
/*     */ 
/* 232 */     return g;
/*     */   }
/*     */ 
/*     */   public FontDefinition.Glyph getGlyph(int code)
/*     */   {
/* 245 */     FontDefinition.Glyph ret = super.getGlyph(code);
/*     */ 
/* 247 */     if (ret != null) {
/* 248 */       return ret;
/*     */     }
/* 250 */     ret = createGlyph(code);
/*     */ 
/* 252 */     if (ret == null) {
/* 253 */       this.glyphs.add(ret);
/* 254 */       this.glyphLookup.put(new Integer(code), ret);
/*     */     }
/*     */ 
/* 257 */     return ret;
/*     */   }
/*     */ 
/*     */   public double getKerningOffset(int code1, int code2)
/*     */   {
/* 266 */     Integer i1 = new Integer(code1);
/* 267 */     Integer i2 = new Integer(code2);
/*     */ 
/* 269 */     if (this.kernLookup == null) {
/* 270 */       this.kernLookup = new HashMap();
/*     */ 
/* 272 */       if (this.kerning.size() != 0)
/* 273 */         throw new IllegalStateException(
/* 274 */           "Internal Error in SystemFontDefinition: no kernLookup, but kerning contains entries!");
/*     */     } else {
/* 276 */       HashMap kerns = (HashMap)this.kernLookup.get(i1);
/* 277 */       if (kerns != null) {
/* 278 */         FontDefinition.KerningPair pair = (FontDefinition.KerningPair)kerns.get(i2);
/* 279 */         if (pair != null) {
/* 280 */           return pair.getAdjustment();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 285 */     double ret = calculateKerningOffset(code1, code2);
/*     */ 
/* 287 */     HashMap kerns = (HashMap)this.kernLookup.get(i1);
/* 288 */     if (kerns == null) {
/* 289 */       kerns = new HashMap();
/* 290 */       this.kernLookup.put(i1, kerns);
/*     */     }
/*     */ 
/* 293 */     kerns.put(i2, new FontDefinition.KerningPair(code1, code2, ret));
/*     */ 
/* 295 */     return ret;
/*     */   }
/*     */ 
/*     */   private double calculateKerningOffset(int code1, int code2)
/*     */   {
/* 305 */     char[] chars = { (char)code1, (char)code2 };
/* 306 */     GlyphVector gv = this.font.createGlyphVector(frc, chars);
/* 307 */     double adj = (gv.getGlyphPosition(1).getX() - this.metrics
/* 308 */       .charWidth(chars[0])) * 
/* 309 */       this.scale;
/*     */ 
/* 319 */     return adj;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.utility.SystemFontDefinition
 * JD-Core Version:    0.6.2
 */