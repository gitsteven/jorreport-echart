/*      */ package jatools.component.chart.chart;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.GradientPaint;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.TexturePaint;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.FilteredImageSource;
/*      */ import java.awt.image.ImageFilter;
/*      */ import java.awt.image.ReplicateScaleFilter;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Arrays;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.swing.ImageIcon;
/*      */ 
/*      */ public class GcHelper
/*      */ {
/*   37 */   static float[] dashArray = { 5.0F };
/*   38 */   static float[] dotArray = { 2.0F };
/*   39 */   static float[] dotDashArray = { 2.0F, 2.0F, 5.0F, 2.0F };
/*      */   public static final int GRAD_LEFT_RIGHT_MIRROR = 0;
/*      */   public static final int GRAD_TOP_BOTTOM_MIRROR = 1;
/*      */   public static final int GRAD_TOP_BOTTOM = 2;
/*      */   public static final int GRAD_LEFT_RIGHT = 3;
/*      */   public static final int TEXTURE_IMAGE = -1;
/*      */   public static final int TEXTURE_H_STRIPE = 0;
/*      */   public static final int TEXTURE_V_STRIPE = 1;
/*      */   public static final int TEXTURE_DOWN_STRIPE = 2;
/*      */   public static final int TEXTURE_UP_STRIPE = 3;
/*      */   public static final int TEXTURE_CROSS_STRIPE = 4;
/*   56 */   float[][] lineStyles = { dashArray, dotArray, dotDashArray };
/*      */ 
/*   59 */   Color secondaryFillColor = Color.white;
/*      */   GradientPaint gradient;
/*   61 */   GradientPaint userGradient = null;
/*   62 */   int gradientWidth = -1;
/*   63 */   protected int gradientType = 2;
/*      */   TexturePaint texture;
/*      */   TexturePaint userTexture;
/*      */   Stroke stroke;
/*      */   Stroke userStroke;
/*   70 */   protected int textureType = 0;
/*   71 */   Gc baseGc = null;
/*      */ 
/*      */   public GcHelper(Gc gc)
/*      */   {
/*   81 */     this.baseGc = gc;
/*      */   }
/*      */ 
/*      */   private void buildGradient(int width) {
/*   85 */     if (this.userGradient != null) {
/*   86 */       this.gradient = this.userGradient;
/*      */ 
/*   88 */       return;
/*      */     }
/*      */ 
/*   91 */     this.gradientWidth = width;
/*      */ 
/*   93 */     switch (this.gradientType) {
/*      */     case 0:
/*   95 */       this.gradient = new GradientPaint(0.0F, 0.0F, this.baseGc.fillColor, this.gradientWidth / 2, 0.0F, 
/*   96 */         this.secondaryFillColor, true);
/*      */ 
/*  102 */       break;
/*      */     case 3:
/*  105 */       this.gradient = new GradientPaint(0.0F, 0.0F, this.baseGc.fillColor, this.gradientWidth, 0.0F, 
/*  106 */         this.secondaryFillColor, false);
/*      */ 
/*  108 */       break;
/*      */     case 1:
/*  111 */       this.gradient = new GradientPaint(0.0F, 0.0F, this.baseGc.fillColor, 0.0F, this.gradientWidth / 2, 
/*  112 */         this.secondaryFillColor, true);
/*      */ 
/*  114 */       break;
/*      */     case 2:
/*  117 */       this.gradient = new GradientPaint(0.0F, 0.0F, this.baseGc.fillColor, 0.0F, this.gradientWidth, 
/*  118 */         this.secondaryFillColor, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void buildTexture()
/*      */   {
/*  129 */     int sz = 6;
/*      */ 
/*  131 */     if (this.texture != null) {
/*  132 */       return;
/*      */     }
/*      */ 
/*  135 */     if (this.baseGc.imageBytes != null) {
/*  136 */       ImageIcon icon = new ImageIcon(this.baseGc.imageBytes);
/*      */ 
/*  138 */       int w = icon.getIconWidth();
/*  139 */       int h = icon.getIconHeight();
/*  140 */       BufferedImage img = new BufferedImage(w, h, 1);
/*  141 */       img.getGraphics().drawImage(icon.getImage(), 0, 0, null);
/*  142 */       this.baseGc.image = img;
/*      */     }
/*      */ 
/*  145 */     if ((this.baseGc.image != null) && (this.textureType == -1))
/*      */     {
/*  150 */       BufferedImage img = null;
/*      */ 
/*  152 */       if ((this.baseGc.image.getWidth(null) > this.baseGc.getGlobals().getMaxX()) && 
/*  153 */         (this.baseGc.image.getHeight(null) > this.baseGc.getGlobals().getMaxY())) {
/*  154 */         ImageFilter resizeFilter = new ReplicateScaleFilter(this.baseGc.getGlobals().maxX, 
/*  155 */           this.baseGc.getGlobals().maxY);
/*  156 */         FilteredImageSource source = new FilteredImageSource(this.baseGc.image.getSource(), 
/*  157 */           resizeFilter);
/*      */ 
/*  159 */         this.baseGc.image = Toolkit.getDefaultToolkit().createImage(source);
/*      */       }
/*      */ 
/*  162 */       int xSize = this.baseGc.image.getWidth(null);
/*  163 */       int ySize = this.baseGc.image.getHeight(null);
/*      */ 
/*  165 */       if ((xSize < 1) || (ySize < 1)) {
/*  166 */         return;
/*      */       }
/*      */ 
/*  169 */       if ((this.baseGc.image instanceof BufferedImage)) {
/*  170 */         img = (BufferedImage)this.baseGc.image;
/*      */       } else {
/*  172 */         System.out.println("doing conversion");
/*  173 */         img = new BufferedImage(xSize, ySize, 1);
/*  174 */         img.getGraphics().drawImage(this.baseGc.image, 0, 0, null);
/*      */       }
/*      */ 
/*  177 */       Rectangle r = new Rectangle(0, 0, xSize, ySize);
/*  178 */       this.texture = new TexturePaint(img, r);
/*      */ 
/*  180 */       return;
/*      */     }
/*      */ 
/*  184 */     BufferedImage img = new BufferedImage(sz, sz, 1);
/*  185 */     Graphics ig = img.createGraphics();
/*      */ 
/*  187 */     if (this.textureType == -1)
/*  188 */       ig.setColor(this.baseGc.fillColor);
/*      */     else {
/*  190 */       ig.setColor(this.secondaryFillColor);
/*      */     }
/*      */ 
/*  193 */     ig.fillRect(0, 0, sz, sz);
/*  194 */     ig.setColor(this.baseGc.fillColor);
/*      */ 
/*  196 */     switch (this.textureType) {
/*      */     case 0:
/*  198 */       ig.drawLine(0, sz / 2, sz, sz / 2);
/*      */ 
/*  200 */       break;
/*      */     case 1:
/*  203 */       ig.drawLine(sz / 2, 0, sz / 2, sz);
/*      */ 
/*  205 */       break;
/*      */     case 2:
/*  208 */       ig.drawLine(0, 0, sz - 1, sz - 1);
/*      */ 
/*  210 */       break;
/*      */     case 3:
/*  213 */       ig.drawLine(0, sz - 1, sz - 1, 0);
/*      */ 
/*  215 */       break;
/*      */     case 4:
/*  218 */       ig.drawLine(0, sz - 1, sz - 1, 0);
/*  219 */       ig.drawLine(0, 0, sz, sz);
/*      */     }
/*      */ 
/*  225 */     Rectangle r = new Rectangle(0, 0, sz, sz);
/*  226 */     this.texture = new TexturePaint(img, r);
/*      */   }
/*      */ 
/*      */   public void drawLine(Graphics g, Point p1, Point p2)
/*      */   {
/*  241 */     Point[] points = new Point[2];
/*  242 */     points[0] = p1;
/*  243 */     points[1] = p2;
/*  244 */     drawPoly2D((Graphics2D)g, points);
/*      */   }
/*      */   public final void drawPoly2D(Graphics2D g, Point[] pts) {
/*  247 */     drawPoly2D(g, pts, 0);
/*      */   }
/*      */ 
/*      */   public final void drawPoly2D(Graphics2D g, Point[] pts, int smooth)
/*      */   {
/*  258 */     if (this.userStroke != null) {
/*  259 */       this.stroke = this.userStroke;
/*      */     }
/*      */ 
/*  262 */     if (this.stroke == null) {
/*      */       try {
/*  264 */         if (this.baseGc.lineStyle != -1)
/*  265 */           this.stroke = new BasicStroke(this.baseGc.lineWidth, 0, 
/*  266 */             0, 10.0F, this.lineStyles[this.baseGc.lineStyle], 0.0F);
/*      */         else
/*  268 */           this.stroke = new BasicStroke(this.baseGc.lineWidth);
/*      */       }
/*      */       catch (Exception e) {
/*  271 */         this.stroke = new BasicStroke(this.baseGc.lineWidth);
/*      */       }
/*      */     }
/*      */ 
/*  275 */     Stroke saveStroke = g.getStroke();
/*  276 */     g.setStroke(this.stroke);
/*      */ 
/*  278 */     int[] x = new int[pts.length];
/*  279 */     int[] y = new int[pts.length];
/*      */ 
/*  281 */     for (int i = 0; i < pts.length; i++)
/*      */     {
/*  286 */       x[i] = pts[i].x;
/*  287 */       y[i] = (this.baseGc.globals.maxY - pts[i].y);
/*      */     }
/*      */ 
/*  290 */     if (smooth > 0) {
/*  291 */       for (int i = 0; i < pts.length; i++) {
/*  292 */         pts[i].y = (this.baseGc.globals.maxY - pts[i].y);
/*      */       }
/*  294 */       new NatCubic(Arrays.asList(pts)).draw(g);
/*      */     }
/*      */     else {
/*  297 */       g.drawPolyline(x, y, pts.length);
/*      */     }
/*      */ 
/*  300 */     g.setStroke(saveStroke);
/*      */   }
/*      */ 
/*      */   public void drawPolygon(Graphics g, float[] x, float[] y)
/*      */   {
/*  312 */     GeneralPath shape = new GeneralPath();
/*  313 */     shape.moveTo(x[0], this.baseGc.globals.maxY - y[0]);
/*      */ 
/*  315 */     for (int i = 1; i < x.length; i++) {
/*  316 */       shape.lineTo(x[i], this.baseGc.globals.maxY - y[i]);
/*      */     }
/*      */ 
/*  319 */     if (this.baseGc.fillStyle == 1) {
/*  320 */       buildTexture();
/*  321 */       ((Graphics2D)g).setPaint(this.texture);
/*  322 */     } else if (this.baseGc.fillStyle == 0) {
/*  323 */       Point[] pts = new Point[x.length];
/*      */ 
/*  325 */       for (int i = 0; i < x.length; i++) {
/*  326 */         pts[i] = new Point(Math.round(x[i]), Math.round(y[i]));
/*      */       }
/*      */ 
/*  329 */       fillPolygonGradient((Graphics2D)g, pts);
/*      */     } else {
/*  331 */       g.setColor(this.baseGc.fillColor);
/*      */     }
/*      */ 
/*  334 */     if ((this.baseGc.fillColor != Gc.TRANSPARENT) || (this.baseGc.fillStyle != -1)) {
/*  335 */       ((Graphics2D)g).fill(shape);
/*      */     }
/*      */ 
/*  338 */     if ((this.baseGc.outlineFills) && 
/*  339 */       (this.baseGc.lineColor != Gc.TRANSPARENT)) {
/*  340 */       shape.closePath();
/*  341 */       g.setColor(this.baseGc.lineColor);
/*  342 */       ((Graphics2D)g).draw(shape);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void drawPolygon(Graphics g, Point[] pts)
/*      */   {
/*  360 */     if (this.baseGc.fillStyle == 0)
/*  361 */       fillPolygonGradient((Graphics2D)g, pts);
/*      */     else {
/*  363 */       fillPolygonTexture((Graphics2D)g, pts);
/*      */     }
/*      */ 
/*  366 */     if (this.baseGc.outlineFills) {
/*  367 */       this.baseGc.drawPolyline(g, pts);
/*  368 */       this.baseGc.drawLine(g, pts[0], pts[(pts.length - 1)]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void drawPolyline(Graphics g, Point[] points)
/*      */   {
/*  381 */     drawPoly2D((Graphics2D)g, points);
/*      */   }
/*      */ 
/*      */   public static void drawSmartString(Graphics g, int startx, int starty, int alignment, int angle, FontMetrics fm, String str, Globals globals)
/*      */   {
/*  408 */     int shiftX = 0;
/*  409 */     int shiftY = 0;
/*  410 */     int adjAngle = 0;
/*      */ 
/*  412 */     if (g.getColor() == Gc.TRANSPARENT) {
/*  413 */       return;
/*      */     }
/*      */ 
/*  416 */     StringTokenizer strtoke = new StringTokenizer(str, "|");
/*  417 */     int lines = strtoke.countTokens();
/*      */ 
/*  419 */     int lineHeight = fm.getMaxAscent();
/*      */ 
/*  421 */     switch (alignment) {
/*      */     case 0:
/*  423 */       if (angle == 0) {
/*  424 */         while (strtoke.hasMoreElements()) {
/*  425 */           String currentLine = strtoke.nextToken();
/*  426 */           drawString(g, startx - fm.stringWidth(currentLine) / 2, 
/*  427 */             starty - lineHeight * 1 + shiftY, currentLine, globals);
/*  428 */           shiftY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  431 */         return;
/*      */       }
/*      */ 
/*  434 */       if ((angle < -90) || (angle >= 90)) {
/*  435 */         adjAngle = 90;
/*      */ 
/*  437 */         double theta = -(adjAngle / 360.0D) * 3.141592653589793D * 2.0D;
/*  438 */         ((Graphics2D)g).rotate(theta, startx, globals.maxY - starty);
/*  439 */         shiftY = ((lineHeight + (lineHeight / 5 + 1)) * (lines - 1) - 
/*  440 */           lineHeight) / 2;
/*      */ 
/*  442 */         while (strtoke.hasMoreElements()) {
/*  443 */           String currentLine = strtoke.nextToken();
/*  444 */           drawString(g, startx - fm.stringWidth(currentLine), starty + shiftY, 
/*  445 */             currentLine, globals);
/*  446 */           shiftY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  449 */         ((Graphics2D)g).rotate(-theta, startx, globals.maxY - starty);
/*      */ 
/*  451 */         return;
/*      */       }
/*      */ 
/*  456 */       adjAngle = angle;
/*  457 */       Rectangle extent = globals.stringRotator.getExtent(str, startx, globals.maxY - starty, adjAngle, 
/*  458 */         fm);
/*      */ 
/*  460 */       if (adjAngle == 90) {
/*  461 */         shiftX = fm.getMaxAscent() / 2;
/*  462 */         shiftY = extent.height;
/*  463 */       } else if (adjAngle > 0) {
/*  464 */         shiftX = -extent.width + fm.getMaxAscent();
/*  465 */         shiftY = extent.height;
/*      */       } else {
/*  467 */         shiftX = -fm.getMaxAscent() / 2;
/*  468 */         shiftY = fm.getMaxAscent() / 2;
/*      */       }
/*      */ 
/*  471 */       break;
/*      */     case 2:
/*  475 */       if (angle == 0)
/*      */       {
/*  477 */         shiftY = (lineHeight + (lineHeight / 5 + 1)) * (lines - 1);
/*      */ 
/*  479 */         while (strtoke.hasMoreElements()) {
/*  480 */           String currentLine = strtoke.nextToken();
/*  481 */           drawString(g, startx - fm.stringWidth(currentLine) / 2, 
/*  482 */             starty + shiftY, currentLine, globals);
/*  483 */           shiftY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  488 */         return;
/*      */       }
/*      */ 
/*  491 */       if ((angle < -90) || (angle >= 90)) {
/*  492 */         adjAngle = 90;
/*      */ 
/*  494 */         double theta = -(adjAngle / 360.0D) * 3.141592653589793D * 2.0D;
/*  495 */         ((Graphics2D)g).rotate(theta, startx, globals.maxY - starty);
/*  496 */         shiftY = ((lineHeight + (lineHeight / 5 + 1)) * (lines - 1) - 
/*  497 */           lineHeight) / 2;
/*      */ 
/*  499 */         while (strtoke.hasMoreElements()) {
/*  500 */           String currentLine = strtoke.nextToken();
/*  501 */           drawString(g, startx, starty + shiftY, currentLine, globals);
/*  502 */           shiftY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  505 */         ((Graphics2D)g).rotate(-theta, startx, globals.maxY - starty);
/*      */ 
/*  507 */         return;
/*      */       }
/*      */ 
/*  512 */       adjAngle = angle;
/*  513 */       Rectangle extent = globals.stringRotator.getExtent(str, startx, globals.maxY - starty, adjAngle, 
/*  514 */         fm);
/*      */ 
/*  516 */       if (adjAngle == 90) {
/*  517 */         shiftX = 0;
/*      */       }
/*      */ 
/*  520 */       if (adjAngle == -90) {
/*  521 */         shiftX = -fm.getMaxAscent() / 2;
/*  522 */         shiftY = -extent.height;
/*  523 */       } else if (angle < 0) {
/*  524 */         shiftX = -extent.width + fm.getMaxAscent() / 2;
/*  525 */         shiftY = fm.getMaxAscent() - extent.height;
/*      */       }
/*      */ 
/*  528 */       break;
/*      */     case 3:
/*  532 */       if (angle == 0)
/*      */       {
/*  534 */         shiftY = ((lineHeight + (lineHeight / 5 + 1)) * (lines - 1) - 
/*  535 */           lineHeight) / 2;
/*      */ 
/*  537 */         while (strtoke.hasMoreElements()) {
/*  538 */           String currentLine = strtoke.nextToken();
/*  539 */           drawString(g, startx, starty + shiftY, currentLine, globals);
/*  540 */           shiftY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  545 */         return;
/*      */       }
/*      */ 
/*  548 */       if ((angle < -90) || (angle >= 90)) {
/*  549 */         adjAngle = 90;
/*      */ 
/*  551 */         double theta = -(adjAngle / 360.0D) * 3.141592653589793D * 2.0D;
/*  552 */         ((Graphics2D)g).rotate(theta, startx, globals.maxY - starty);
/*      */ 
/*  554 */         while (strtoke.hasMoreElements()) {
/*  555 */           String currentLine = strtoke.nextToken();
/*  556 */           drawString(g, startx - fm.stringWidth(currentLine) / 2, 
/*  557 */             starty - lineHeight * 1 + shiftY, currentLine, globals);
/*  558 */           shiftY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  561 */         ((Graphics2D)g).rotate(-theta, startx, globals.maxY - starty);
/*      */ 
/*  563 */         return;
/*      */       }
/*      */ 
/*  568 */       adjAngle = angle;
/*  569 */       Rectangle extent = globals.stringRotator.getExtent(str, startx, globals.maxY - starty, adjAngle, 
/*  570 */         fm);
/*      */ 
/*  572 */       if (angle > 0)
/*  573 */         shiftX = fm.getMaxAscent();
/*      */       else {
/*  575 */         shiftX = 0;
/*      */       }
/*      */ 
/*  578 */       if (angle == 90) {
/*  579 */         shiftY = fm.stringWidth(str) / 2;
/*  580 */       } else if (angle == -90) {
/*  581 */         shiftY = -fm.stringWidth(str) / 2;
/*  582 */         shiftX = 0;
/*      */       }
/*      */ 
/*  585 */       break;
/*      */     case 1:
/*  589 */       if (angle == 0)
/*      */       {
/*  591 */         shiftY = ((lineHeight + (lineHeight / 5 + 1)) * (lines - 1) - 
/*  592 */           lineHeight) / 2;
/*      */ 
/*  594 */         while (strtoke.hasMoreElements()) {
/*  595 */           String currentLine = strtoke.nextToken();
/*  596 */           drawString(g, startx - fm.stringWidth(currentLine), starty + shiftY, 
/*  597 */             currentLine, globals);
/*  598 */           shiftY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  603 */         return;
/*      */       }
/*      */ 
/*  606 */       if ((angle < -90) || (angle >= 90)) {
/*  607 */         adjAngle = 90;
/*      */ 
/*  609 */         double theta = -(adjAngle / 360.0D) * 3.141592653589793D * 2.0D;
/*  610 */         ((Graphics2D)g).rotate(theta, startx, globals.maxY - starty);
/*  611 */         shiftY = (lineHeight + (lineHeight / 5 + 1)) * (lines - 1);
/*      */ 
/*  613 */         while (strtoke.hasMoreElements()) {
/*  614 */           String currentLine = strtoke.nextToken();
/*  615 */           drawString(g, startx - fm.stringWidth(currentLine) / 2, 
/*  616 */             starty + shiftY, currentLine, globals);
/*  617 */           shiftY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  620 */         ((Graphics2D)g).rotate(-theta, startx, globals.maxY - starty);
/*      */ 
/*  622 */         return;
/*      */       }
/*      */ 
/*  627 */       adjAngle = angle;
/*  628 */       Rectangle extent = globals.stringRotator.getExtent(str, startx, globals.maxY - starty, adjAngle, 
/*  629 */         fm);
/*  630 */       shiftX = -extent.width;
/*      */ 
/*  632 */       if (angle == 90) {
/*  633 */         shiftY = fm.stringWidth(str) / 2;
/*  634 */         shiftX = 0;
/*  635 */       } else if (angle == -90) {
/*  636 */         shiftY = -fm.stringWidth(str) / 2;
/*  637 */         shiftX = -fm.getMaxAscent();
/*  638 */       } else if (angle > 0) {
/*  639 */         shiftY = extent.height - fm.getMaxAscent() / 2;
/*      */       } else {
/*  641 */         shiftY = fm.getMaxAscent() - extent.height;
/*      */       }
/*      */ 
/*  646 */       break;
/*      */     default:
/*      */       return;
/*      */     }
/*      */     Rectangle extent;
/*  655 */     double theta = -(adjAngle / 360.0D) * 3.141592653589793D * 2.0D;
/*  656 */     g.translate(shiftX, shiftY);
/*  657 */     ((Graphics2D)g).rotate(theta, startx, globals.maxY - starty);
/*      */ 
/*  680 */     g.drawString(str, startx, globals.maxY - starty);
/*  681 */     ((Graphics2D)g).rotate(-theta, startx, globals.maxY - starty);
/*  682 */     g.translate(-shiftX, -shiftY);
/*      */   }
/*      */ 
/*      */   protected static void drawString(Graphics g, int x, int y, String str, Globals globals)
/*      */   {
/*  701 */     if (g.getColor() == Gc.TRANSPARENT) {
/*  702 */       return;
/*      */     }
/*      */ 
/*  705 */     g.drawString(str, x, globals.maxY - y);
/*      */   }
/*      */ 
/*      */   public void fillPolygonGradient(Graphics2D g2d, Point[] pts)
/*      */   {
/*  718 */     int minX = 2147483647;
/*  719 */     int maxX = -2147483648;
/*  720 */     int minY = 2147483647;
/*  721 */     int maxY = -2147483648;
/*  722 */     int[] xarr = new int[pts.length];
/*  723 */     int[] yarr = new int[pts.length];
/*      */ 
/*  725 */     for (int i = 0; i < pts.length; i++) {
/*  726 */       xarr[i] = pts[i].x;
/*  727 */       yarr[i] = (this.baseGc.globals.maxY - pts[i].y);
/*  728 */       minX = Math.min(minX, xarr[i]);
/*  729 */       maxX = Math.max(maxX, xarr[i]);
/*  730 */       minY = Math.min(minY, yarr[i]);
/*  731 */       maxY = Math.max(maxY, yarr[i]);
/*      */     }
/*      */ 
/*  734 */     if ((this.gradientType == 0) || (this.gradientType == 3)) {
/*  735 */       if ((maxX - minX != this.gradientWidth) || (this.gradient == null)) {
/*  736 */         buildGradient(maxX - minX);
/*      */       }
/*      */ 
/*  739 */       for (int i = 0; i < xarr.length; i++) {
/*  740 */         xarr[i] -= minX;
/*      */       }
/*      */ 
/*  743 */       g2d.translate(minX, 0);
/*  744 */       g2d.setPaint(this.gradient);
/*  745 */       g2d.fillPolygon(xarr, yarr, pts.length);
/*  746 */       g2d.translate(-minX, 0);
/*      */     }
/*      */     else {
/*  749 */       if ((maxY - minY != this.gradientWidth) || (this.gradient == null)) {
/*  750 */         buildGradient(maxY - minY);
/*      */       }
/*      */ 
/*  753 */       for (int i = 0; i < yarr.length; i++) {
/*  754 */         yarr[i] -= minY;
/*      */       }
/*      */ 
/*  757 */       g2d.translate(0, minY);
/*  758 */       g2d.setPaint(this.gradient);
/*  759 */       g2d.fillPolygon(xarr, yarr, pts.length);
/*  760 */       g2d.translate(0, -minY);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void fillPolygonTexture(Graphics2D g2d, Point[] pts)
/*      */   {
/*  774 */     buildTexture();
/*      */ 
/*  776 */     int[] xarr = new int[pts.length];
/*  777 */     int[] yarr = new int[pts.length];
/*      */ 
/*  779 */     for (int i = 0; i < pts.length; i++) {
/*  780 */       xarr[i] = pts[i].x;
/*  781 */       yarr[i] = (this.baseGc.globals.maxY - pts[i].y);
/*      */     }
/*      */ 
/*  784 */     g2d.setPaint(this.texture);
/*  785 */     g2d.fillPolygon(xarr, yarr, pts.length);
/*      */   }
/*      */ 
/*      */   public void fillRect(Graphics g, Point ll, Point ur)
/*      */   {
/*  800 */     if (this.baseGc.fillStyle == 0)
/*  801 */       fillRectGradient((Graphics2D)g, ll, ur);
/*      */     else
/*  803 */       fillRectTexture((Graphics2D)g, ll, ur);
/*      */   }
/*      */ 
/*      */   public void fillRectGradient(Graphics2D g2d, Point ll, Point ur)
/*      */   {
/*      */     int urx;
/*      */     int llx;
/*      */     int urx;
/*  821 */     if (ll.x > ur.x) {
/*  822 */       int llx = ur.x;
/*  823 */       urx = ll.x;
/*      */     } else {
/*  825 */       llx = ll.x;
/*  826 */       urx = ur.x;
/*      */     }
/*      */ 
/*  829 */     if (ll.y > ur.y) {
/*  830 */       int y = ur.y;
/*  831 */       ur.y = ll.y;
/*  832 */       ll.y = y;
/*      */     }
/*      */ 
/*  837 */     if ((this.gradientType == 0) || (this.gradientType == 3)) {
/*  838 */       if ((urx - llx != this.gradientWidth) || (this.gradient == null)) {
/*  839 */         buildGradient(urx - llx);
/*      */       }
/*      */ 
/*  842 */       g2d.setPaint(this.gradient);
/*      */ 
/*  845 */       g2d.translate(llx, 0);
/*  846 */       g2d.fillRect(0, this.baseGc.globals.maxY - ur.y, urx - llx, ur.y - ll.y);
/*  847 */       g2d.translate(-llx, 0);
/*      */     }
/*      */     else {
/*  850 */       if ((ur.y - ll.y != this.gradientWidth) || (this.gradient == null)) {
/*  851 */         buildGradient(ur.y - ll.y);
/*      */       }
/*      */ 
/*  854 */       g2d.setPaint(this.gradient);
/*  855 */       g2d.translate(0, this.baseGc.globals.maxY - ur.y);
/*  856 */       g2d.fillRect(llx, 0, urx - llx, ur.y - ll.y);
/*  857 */       g2d.translate(0, -(this.baseGc.globals.maxY - ur.y));
/*      */     }
/*      */ 
/*  860 */     if (this.baseGc.outlineFills) {
/*  861 */       if (this.baseGc.lineColor == Gc.TRANSPARENT) {
/*  862 */         return;
/*      */       }
/*      */ 
/*  865 */       g2d.setColor(this.baseGc.lineColor);
/*  866 */       g2d.drawLine(llx, this.baseGc.globals.maxY - ur.y, llx, this.baseGc.globals.maxY - ll.y);
/*  867 */       g2d.drawLine(urx, this.baseGc.globals.maxY - ur.y, urx, this.baseGc.globals.maxY - ll.y);
/*  868 */       g2d.drawLine(llx, this.baseGc.globals.maxY - ur.y, urx, this.baseGc.globals.maxY - ur.y);
/*  869 */       g2d.drawLine(llx, this.baseGc.globals.maxY - ll.y, urx, this.baseGc.globals.maxY - ll.y);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void fillRectTexture(Graphics2D g2d, Point ll, Point ur)
/*      */   {
/*  888 */     buildTexture();
/*      */     int urx;
/*      */     int llx;
/*      */     int urx;
/*  893 */     if (ll.x > ur.x) {
/*  894 */       int llx = ur.x;
/*  895 */       urx = ll.x;
/*      */     } else {
/*  897 */       llx = ll.x;
/*  898 */       urx = ur.x;
/*      */     }
/*      */ 
/*  901 */     if (ll.y > ur.y) {
/*  902 */       int y = ur.y;
/*  903 */       ur.y = ll.y;
/*  904 */       ll.y = y;
/*      */     }
/*      */ 
/*  907 */     g2d.setPaint(this.texture);
/*  908 */     g2d.fillRect(llx, this.baseGc.globals.maxY - ur.y, urx - llx, ur.y - ll.y);
/*      */ 
/*  911 */     if (this.baseGc.outlineFills) {
/*  912 */       if (this.baseGc.lineColor == Gc.TRANSPARENT) {
/*  913 */         return;
/*      */       }
/*      */ 
/*  916 */       g2d.setColor(this.baseGc.lineColor);
/*  917 */       g2d.drawLine(llx, this.baseGc.globals.maxY - ur.y, llx, this.baseGc.globals.maxY - ll.y);
/*  918 */       g2d.drawLine(urx, this.baseGc.globals.maxY - ur.y, urx, this.baseGc.globals.maxY - ll.y);
/*  919 */       g2d.drawLine(llx, this.baseGc.globals.maxY - ur.y, urx, this.baseGc.globals.maxY - ur.y);
/*  920 */       g2d.drawLine(llx, this.baseGc.globals.maxY - ll.y, urx, this.baseGc.globals.maxY - ll.y);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void buildTexture(Point ll, Point ur) {
/*  925 */     BufferedImage img = null;
/*      */ 
/*  927 */     int w = Math.abs(ur.x - ll.x);
/*  928 */     int h = Math.abs(ur.y - ll.y);
/*      */ 
/*  930 */     if ((this.baseGc.image.getWidth(null) > w) && (this.baseGc.image.getHeight(null) > h)) {
/*  931 */       ImageFilter resizeFilter = new ReplicateScaleFilter(w, h);
/*  932 */       FilteredImageSource source = new FilteredImageSource(this.baseGc.image.getSource(), 
/*  933 */         resizeFilter);
/*      */ 
/*  935 */       this.baseGc.image = Toolkit.getDefaultToolkit().createImage(source);
/*      */     }
/*      */ 
/*  938 */     int xSize = this.baseGc.image.getWidth(null);
/*  939 */     int ySize = this.baseGc.image.getHeight(null);
/*      */ 
/*  941 */     if ((xSize < 1) || (ySize < 1)) {
/*  942 */       return;
/*      */     }
/*      */ 
/*  945 */     if ((this.baseGc.image instanceof BufferedImage)) {
/*  946 */       img = (BufferedImage)this.baseGc.image;
/*      */     } else {
/*  948 */       System.out.println("doing conversion");
/*  949 */       img = new BufferedImage(xSize, ySize, 1);
/*  950 */       img.getGraphics().drawImage(this.baseGc.image, 0, 0, null);
/*      */     }
/*      */ 
/*  953 */     Rectangle r = new Rectangle(0, 0, xSize, ySize);
/*  954 */     this.texture = new TexturePaint(img, r);
/*      */   }
/*      */ 
/*      */   public GradientPaint getGradient()
/*      */   {
/*  965 */     return this.userGradient;
/*      */   }
/*      */ 
/*      */   public TexturePaint getTexture()
/*      */   {
/*  975 */     return this.userTexture;
/*      */   }
/*      */ 
/*      */   public void setGradient(GradientPaint gp)
/*      */   {
/*  987 */     this.userGradient = gp;
/*      */   }
/*      */ 
/*      */   public void setStroke(Stroke stroke)
/*      */   {
/*  999 */     this.userStroke = stroke;
/*      */   }
/*      */ 
/*      */   public void setStroke(TexturePaint tp)
/*      */   {
/* 1010 */     this.userTexture = tp;
/*      */   }
/*      */ 
/*      */   public void setTexture(TexturePaint tp)
/*      */   {
/* 1022 */     this.userTexture = tp;
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.GcHelper
 * JD-Core Version:    0.6.2
 */