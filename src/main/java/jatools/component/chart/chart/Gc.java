/*      */ package jatools.component.chart.chart;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.StringTokenizer;
/*      */ 
/*      */ public class Gc
/*      */   implements Serializable
/*      */ {
/*      */   static final boolean NEW_CUBIC = false;
/*   30 */   protected static Color[] colors = { 
/*   31 */     Color.blue, Color.red, Color.green, Color.cyan, Color.orange, Color.pink, Color.yellow, 
/*   32 */     Color.magenta, Color.lightGray, Color.darkGray, Color.blue.darker(), Color.red.darker(), 
/*   33 */     Color.green.darker(), Color.cyan.darker(), Color.orange.darker(), Color.pink.darker(), 
/*   34 */     Color.yellow.darker(), Color.magenta.darker(), Color.lightGray.darker(), Color.black };
/*      */ 
/*   40 */   public static Color TRANSPARENT = null;
/*      */   public static final int DEFAULT_FORMAT = 0;
/*      */   public static final int COMMA_FORMAT = 1;
/*      */   public static final int EURO_FORMAT = 2;
/*      */   static int individualCount;
/*      */   public static final int keepBELOW = 0;
/*      */   public static final int keepLEFT = 1;
/*      */   public static final int keepABOVE = 2;
/*      */   public static final int keepRIGHT = 3;
/*   82 */   public static Font defaultFont = null;
/*   83 */   private static boolean isInitialized = false;
/*      */   public static final int MK_NONE = -1;
/*      */   public static final int MK_SQUARE = 0;
/*      */   public static final int MK_DIAMOND = 1;
/*      */   public static final int MK_CIRCLE = 2;
/*      */   public static final int MK_TRIANGLE = 3;
/*      */   public static final int FILL_SOLID = -1;
/*      */   public static final int FILL_GRADIENT = 0;
/*      */   public static final int FILL_TEXTURE = 1;
/*      */   public static final int LINE_SOLID = -1;
/*      */   public static final int LINE_DASH = 0;
/*      */   public static final int LINE_DOT = 1;
/*      */   public static final int LINE_DOT_DASH = 2;
/*      */   public static final String LINE_BREAK = "|";
/*      */   protected Color fillColor;
/*      */   protected transient Image image;
/*      */   protected transient byte[] imageBytes;
/*      */   protected Globals globals;
/*      */   protected Color lineColor;
/*  111 */   protected int lineStyle = -1;
/*  112 */   protected int lineWidth = 1;
/*  113 */   protected boolean outlineFills = false;
/*  114 */   protected int markerSize = 6;
/*  115 */   protected int markerStyle = -1;
/*  116 */   protected int fillStyle = -1;
/*  117 */   transient GcHelper gcHelper = null;
/*      */ 
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/*  100 */       init();
/*      */     } catch (Throwable t) {
/*  102 */       System.out.println("can't initialize javachart.chart.Gc");
/*      */     }
/*      */   }
/*      */ 
/*      */   public Gc(int dataSetNumber, Globals g)
/*      */   {
/*  126 */     this.globals = g;
/*      */ 
/*  128 */     if (dataSetNumber < colors.length)
/*  129 */       assignColors(colors[dataSetNumber]);
/*      */     else
/*  131 */       assignColors(randomColor());
/*      */   }
/*      */ 
/*      */   public Gc(Color clr, Globals g)
/*      */   {
/*  141 */     this.globals = g;
/*  142 */     assignColors(clr);
/*      */   }
/*      */ 
/*      */   public Gc(Globals g)
/*      */   {
/*  150 */     this.globals = g;
/*  151 */     this.fillColor = Color.black;
/*  152 */     this.lineColor = Color.black;
/*      */   }
/*      */ 
/*      */   public Gc(boolean unique, Globals g, boolean isPie)
/*      */   {
/*  161 */     this.globals = g;
/*      */ 
/*  163 */     if (!unique) {
/*  164 */       return;
/*      */     }
/*      */ 
/*  167 */     int colorLoop = 10;
/*      */ 
/*  169 */     if (isPie) {
/*  170 */       colorLoop = colors.length;
/*      */     }
/*      */ 
/*  173 */     if (individualCount % colorLoop >= colors.length)
/*  174 */       assignColors(randomColor());
/*      */     else {
/*  176 */       assignColors(colors[(individualCount % colorLoop)]);
/*      */     }
/*      */ 
/*  179 */     individualCount += 1;
/*      */   }
/*      */ 
/*      */   private void assignColors(Color clr)
/*      */   {
/*  184 */     this.fillColor = clr;
/*  185 */     this.lineColor = clr;
/*      */   }
/*      */ 
/*      */   public void drawArc(Graphics g, Point center, Point heightWidth, int startAngle, int endAngle)
/*      */   {
/*  200 */     if (this.lineColor == TRANSPARENT) {
/*  201 */       return;
/*      */     }
/*      */ 
/*  209 */     double increment = 0.0174532925199433D;
/*      */ 
/*  211 */     if (endAngle < 0) {
/*  212 */       increment = -increment;
/*      */     }
/*      */ 
/*  215 */     double startRadians = startAngle / 180.0D * 3.141592653589793D;
/*  216 */     double totalRadians = endAngle / 180.0D * 3.141592653589793D;
/*  217 */     int nPoints = (int)Math.abs(totalRadians / increment);
/*      */ 
/*  219 */     if (nPoints < 2)
/*      */     {
/*  221 */       return;
/*      */     }
/*      */ 
/*  225 */     float[] x = new float[nPoints];
/*  226 */     float[] y = new float[x.length];
/*      */ 
/*  230 */     double currentRadians = startRadians;
/*      */ 
/*  233 */     for (int i = 0; i < nPoints; i++) {
/*  234 */       if (i == nPoints - 1) {
/*  235 */         currentRadians = startRadians + totalRadians;
/*      */       }
/*      */ 
/*  238 */       double xVal = center.x + Math.cos(currentRadians) * (heightWidth.x / 2);
/*  239 */       double yVal = center.y + Math.sin(currentRadians) * (heightWidth.y / 2);
/*  240 */       x[i] = ((float)xVal);
/*  241 */       y[i] = ((float)yVal);
/*  242 */       currentRadians += increment;
/*      */     }
/*      */ 
/*  245 */     Point[] arcArray = new Point[x.length];
/*      */ 
/*  247 */     for (int i = 0; i < x.length; i++) {
/*  248 */       arcArray[i] = new Point((int)x[i], (int)y[i]);
/*      */     }
/*      */ 
/*  251 */     drawPolyline(g, arcArray);
/*      */   }
/*      */ 
/*      */   public void drawImage(Graphics g, Point pt)
/*      */   {
/*  274 */     if (this.image != null)
/*  275 */       g.drawImage(this.image, pt.x - this.image.getWidth(null) / 2, 
/*  276 */         this.globals.maxY - pt.y - this.image.getHeight(null) / 2, null);
/*      */   }
/*      */ 
/*      */   public void drawLine(Graphics g, int startx, int starty, int endx, int endy)
/*      */   {
/*  290 */     if (this.lineColor == TRANSPARENT) {
/*  291 */       return;
/*      */     }
/*      */ 
/*  294 */     g.setColor(this.lineColor);
/*      */ 
/*  296 */     if (this.globals.java2Capable) {
/*  297 */       if (this.gcHelper == null) {
/*  298 */         this.gcHelper = new GcHelper(this);
/*      */       }
/*      */ 
/*  301 */       this.gcHelper.drawLine(g, new Point(startx, starty), new Point(endx, endy));
/*      */ 
/*  303 */       return;
/*      */     }
/*      */ 
/*  306 */     if (this.lineWidth > 1) {
/*  307 */       Point[] pts = new Point[2];
/*  308 */       pts[0] = new Point(startx, starty);
/*  309 */       pts[1] = new Point(endx, endy);
/*  310 */       drawThickLine(g, pts, this.lineWidth, this.globals);
/*      */ 
/*  312 */       return;
/*      */     }
/*      */ 
/*  315 */     g.drawLine(startx, this.globals.maxY - starty, endx, this.globals.maxY - endy);
/*      */   }
/*      */ 
/*      */   public void drawLine(Graphics g, Point start, Point end)
/*      */   {
/*  325 */     if (this.lineColor == TRANSPARENT) {
/*  326 */       return;
/*      */     }
/*      */ 
/*  329 */     g.setColor(this.lineColor);
/*      */ 
/*  331 */     if (this.globals.java2Capable) {
/*  332 */       if (this.gcHelper == null) {
/*  333 */         this.gcHelper = new GcHelper(this);
/*      */       }
/*      */ 
/*  336 */       this.gcHelper.drawLine(g, start, end);
/*      */ 
/*  338 */       return;
/*      */     }
/*      */ 
/*  341 */     if (this.lineWidth < 2) {
/*  342 */       g.drawLine(start.x, this.globals.maxY - start.y, end.x, this.globals.maxY - end.y);
/*      */     } else {
/*  344 */       Point[] p = new Point[2];
/*  345 */       p[0] = start;
/*  346 */       p[1] = end;
/*  347 */       drawThickLine(g, p, this.lineWidth, this.globals);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static void drawMarker(Graphics g, int x, int y, int type, int size)
/*      */   {
/*  359 */     int halfSize = size / 2;
/*      */ 
/*  361 */     switch (type) {
/*      */     case 0:
/*  363 */       g.fillRect(x - halfSize, y - halfSize, size, size);
/*      */ 
/*  365 */       break;
/*      */     case 1:
/*  369 */       int[] xArr = new int[4];
/*  370 */       int[] yArr = new int[4];
/*  371 */       xArr[0] = (x - halfSize);
/*  372 */       yArr[0] = y;
/*  373 */       xArr[1] = x;
/*  374 */       yArr[1] = (y + halfSize);
/*  375 */       xArr[2] = (x + halfSize);
/*  376 */       yArr[2] = y;
/*  377 */       xArr[3] = x;
/*  378 */       yArr[3] = (y - halfSize);
/*  379 */       g.fillPolygon(xArr, yArr, 4);
/*      */ 
/*  381 */       break;
/*      */     case 2:
/*  385 */       g.fillOval(x - halfSize, y - halfSize, size, size);
/*      */ 
/*  387 */       break;
/*      */     case 3:
/*  391 */       int[] xArr = new int[3];
/*  392 */       int[] yArr = new int[3];
/*  393 */       xArr[0] = (x - halfSize);
/*  394 */       yArr[0] = (y + halfSize);
/*  395 */       xArr[1] = x;
/*  396 */       yArr[1] = (y - halfSize);
/*  397 */       xArr[2] = (x + halfSize);
/*  398 */       yArr[2] = (y + halfSize);
/*  399 */       g.fillPolygon(xArr, yArr, 3);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void drawPolygon(Graphics g, Point[] pts)
/*      */   {
/*  415 */     if (this.fillColor != TRANSPARENT)
/*      */     {
/*  417 */       if ((this.globals.java2Capable) && (
/*  418 */         (this.fillStyle == 0) || (this.fillStyle == 1))) {
/*  419 */         if (this.gcHelper == null) {
/*  420 */           this.gcHelper = new GcHelper(this);
/*      */         }
/*      */ 
/*  423 */         this.gcHelper.drawPolygon(g, pts);
/*      */       }
/*      */       else
/*      */       {
/*  427 */         g.setColor(this.fillColor);
/*  428 */         int[] xarr = new int[pts.length];
/*  429 */         int[] yarr = new int[pts.length];
/*      */ 
/*  431 */         for (int i = 0; i < pts.length; i++) {
/*  432 */           xarr[i] = pts[i].x;
/*  433 */           yarr[i] = (this.globals.maxY - pts[i].y);
/*      */         }
/*      */ 
/*  436 */         g.fillPolygon(xarr, yarr, pts.length);
/*      */       }
/*      */     }
/*      */ 
/*  440 */     if ((this.outlineFills) && 
/*  441 */       (this.lineColor != TRANSPARENT)) {
/*  442 */       drawPolyline(g, pts);
/*  443 */       drawLine(g, pts[0], pts[(pts.length - 1)]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void drawPolyline(Graphics g, Point[] pts)
/*      */   {
/*  455 */     drawPolyline(g, pts, 0);
/*      */   }
/*      */ 
/*      */   public void drawPolyline(Graphics g, Point[] pts, int smooth)
/*      */   {
/*  466 */     if (this.lineColor == TRANSPARENT) {
/*  467 */       return;
/*      */     }
/*      */ 
/*  470 */     g.setColor(this.lineColor);
/*      */ 
/*  472 */     if (pts.length == 1) {
/*  473 */       return;
/*      */     }
/*      */ 
/*  476 */     if (this.globals.java2Capable) {
/*  477 */       if (this.gcHelper == null) {
/*  478 */         this.gcHelper = new GcHelper(this);
/*      */       }
/*      */ 
/*  481 */       this.gcHelper.drawPoly2D((Graphics2D)g, pts, smooth);
/*      */ 
/*  483 */       return;
/*      */     }
/*      */ 
/*  486 */     if (this.lineWidth > 1) {
/*  487 */       drawThickLine(g, pts, this.lineWidth, this.globals);
/*      */ 
/*  489 */       return;
/*      */     }
/*      */ 
/*  492 */     if (smooth > 0) {
/*  493 */       for (int i = 0; i < pts.length; i++) {
/*  494 */         pts[i].y = (this.globals.maxY - pts[i].y);
/*      */       }
/*      */ 
/*  501 */       new Spline(Arrays.asList(pts), smooth).draw((Graphics2D)g);
/*      */     }
/*      */     else
/*      */     {
/*  506 */       for (int i = 1; i < pts.length; i++)
/*  507 */         g.drawLine(pts[(i - 1)].x, this.globals.maxY - pts[(i - 1)].y, pts[i].x, 
/*  508 */           this.globals.maxY - pts[i].y);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void drawPolymarker(Graphics g, Point[] pts)
/*      */   {
/*  521 */     if (this.markerStyle == -1) {
/*  522 */       return;
/*      */     }
/*      */ 
/*  525 */     if (this.fillColor == TRANSPARENT) {
/*  526 */       return;
/*      */     }
/*      */ 
/*  529 */     g.setColor(this.fillColor);
/*      */ 
/*  531 */     for (int i = 0; i < pts.length; i++)
/*  532 */       drawMarker(g, pts[i].x, this.globals.maxY - pts[i].y, this.markerStyle, this.markerSize);
/*      */   }
/*      */ 
/*      */   public void drawSmartString(Graphics g, int startx, int starty, int alignment, int angle, FontMetrics fm, String str)
/*      */   {
/*  555 */     if (this.globals.java2Capable) {
/*      */       try {
/*  557 */         GcHelper.drawSmartString(g, startx, starty, alignment, angle, fm, str, this.globals);
/*      */ 
/*  559 */         return;
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */     }
/*  565 */     int adjX = 0;
/*  566 */     int adjY = 0;
/*  567 */     int adjAngle = 0;
/*      */ 
/*  569 */     StringTokenizer strtoke = new StringTokenizer(str, "|");
/*  570 */     int lines = strtoke.countTokens();
/*      */ 
/*  572 */     int lineHeight = fm.getMaxAscent();
/*      */ 
/*  574 */     if (g.getColor() == TRANSPARENT) {
/*  575 */       return;
/*      */     }
/*      */ 
/*  578 */     if ((str == null) || (str.length() == 0)) {
/*  579 */       return;
/*      */     }
/*      */ 
/*  583 */     if (this.globals.stringRotator != null) {
/*  584 */       this.globals.stringRotator.setFont(g.getFont());
/*  585 */       this.globals.stringRotator.setColor(g.getColor());
/*      */     } else {
/*  587 */       angle = 0;
/*      */     }
/*      */ 
/*  590 */     switch (alignment) {
/*      */     case 0:
/*  592 */       if (angle == 0) {
/*  593 */         while (strtoke.hasMoreElements()) {
/*  594 */           String currentLine = strtoke.nextToken();
/*  595 */           drawString(g, startx - fm.stringWidth(currentLine) / 2, 
/*  596 */             starty - lineHeight * 1 + adjY, currentLine);
/*  597 */           adjY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  600 */         return;
/*      */       }
/*      */ 
/*  603 */       if ((angle < -90) || (angle > 90))
/*  604 */         adjAngle = 90;
/*      */       else {
/*  606 */         adjAngle = angle;
/*      */       }
/*      */ 
/*  609 */       Rectangle extent = this.globals.stringRotator.getExtent(str, startx, this.globals.maxY - starty, adjAngle, 
/*  610 */         fm);
/*      */ 
/*  612 */       if (adjAngle == 90) {
/*  613 */         adjX = startx + fm.getMaxAscent() / 2;
/*  614 */         adjY = starty - extent.height;
/*  615 */       } else if (adjAngle > 0) {
/*  616 */         adjX = startx - extent.width + fm.getMaxAscent();
/*  617 */         adjY = starty - extent.height;
/*      */       } else {
/*  619 */         adjX = startx - fm.getMaxAscent() / 2;
/*  620 */         adjY = starty - fm.getMaxAscent() / 2;
/*      */       }
/*      */ 
/*  623 */       break;
/*      */     case 2:
/*  627 */       if (angle == 0)
/*      */       {
/*  629 */         adjY = (lineHeight + (lineHeight / 5 + 1)) * (lines - 1);
/*      */ 
/*  631 */         while (strtoke.hasMoreElements()) {
/*  632 */           String currentLine = strtoke.nextToken();
/*  633 */           drawString(g, startx - fm.stringWidth(currentLine) / 2, starty + adjY, 
/*  634 */             currentLine);
/*  635 */           adjY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  638 */         return;
/*      */       }
/*      */ 
/*  641 */       if ((angle < -90) || (angle > 90))
/*  642 */         adjAngle = 90;
/*      */       else {
/*  644 */         adjAngle = angle;
/*      */       }
/*      */ 
/*  647 */       Rectangle extent = this.globals.stringRotator.getExtent(str, startx, this.globals.maxY - starty, adjAngle, 
/*  648 */         fm);
/*      */ 
/*  650 */       if (adjAngle == 90) {
/*  651 */         adjX = startx + fm.getMaxAscent() / 2;
/*  652 */         adjY = starty;
/*      */       }
/*      */ 
/*  655 */       if (adjAngle == -90) {
/*  656 */         adjX = startx - fm.getMaxAscent() / 2;
/*  657 */         adjY = starty + extent.height;
/*  658 */       } else if (angle > 0) {
/*  659 */         adjX = startx;
/*  660 */         adjY = starty;
/*      */       } else {
/*  662 */         adjX = startx - extent.width + fm.getMaxAscent() / 2;
/*  663 */         adjY = starty + extent.height - fm.getMaxAscent();
/*      */       }
/*      */ 
/*  666 */       break;
/*      */     case 3:
/*  670 */       if (angle == 0)
/*      */       {
/*  672 */         adjY = ((lineHeight + (lineHeight / 5 + 1)) * (lines - 1) - 
/*  673 */           lineHeight) / 2;
/*      */ 
/*  675 */         while (strtoke.hasMoreElements()) {
/*  676 */           String currentLine = strtoke.nextToken();
/*  677 */           drawString(g, startx, starty + adjY, currentLine);
/*  678 */           adjY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  681 */         return;
/*      */       }
/*      */ 
/*  684 */       if ((angle < -90) || (angle > 90))
/*  685 */         adjAngle = 90;
/*      */       else {
/*  687 */         adjAngle = angle;
/*      */       }
/*      */ 
/*  690 */       Rectangle extent = this.globals.stringRotator.getExtent(str, startx, this.globals.maxY - starty, adjAngle, 
/*  691 */         fm);
/*      */ 
/*  693 */       if (angle > 0)
/*  694 */         adjX = startx + fm.getMaxAscent();
/*      */       else {
/*  696 */         adjX = startx;
/*      */       }
/*      */ 
/*  699 */       if (angle == 90)
/*  700 */         adjY = starty - fm.stringWidth(str) / 2;
/*  701 */       else if (angle == -90)
/*  702 */         adjY = starty + fm.stringWidth(str) / 2;
/*      */       else {
/*  704 */         adjY = starty;
/*      */       }
/*      */ 
/*  707 */       break;
/*      */     case 1:
/*  711 */       if (angle == 0)
/*      */       {
/*  713 */         adjY = ((lineHeight + (lineHeight / 5 + 1)) * (lines - 1) - 
/*  714 */           lineHeight) / 2;
/*      */ 
/*  716 */         while (strtoke.hasMoreElements()) {
/*  717 */           String currentLine = strtoke.nextToken();
/*  718 */           drawString(g, startx - fm.stringWidth(currentLine), starty + adjY, currentLine);
/*  719 */           adjY -= lineHeight + lineHeight / 5 + 1;
/*      */         }
/*      */ 
/*  722 */         return;
/*      */       }
/*      */ 
/*  725 */       if ((angle < -90) || (angle > 90))
/*  726 */         adjAngle = 90;
/*      */       else {
/*  728 */         adjAngle = angle;
/*      */       }
/*      */ 
/*  731 */       Rectangle extent = this.globals.stringRotator.getExtent(str, startx, this.globals.maxY - starty, adjAngle, 
/*  732 */         fm);
/*  733 */       adjX = startx - extent.width + fm.getMaxAscent();
/*      */ 
/*  735 */       if (angle == 90) {
/*  736 */         adjY = starty - fm.stringWidth(str) / 2;
/*  737 */         adjX = startx;
/*  738 */       } else if (angle == -90) {
/*  739 */         adjY = starty + fm.stringWidth(str) / 2;
/*  740 */         adjX -= fm.getMaxAscent();
/*  741 */       } else if (angle > 0) {
/*  742 */         adjY = starty - extent.height + fm.getMaxAscent() / 2;
/*      */       } else {
/*  744 */         adjY = starty + extent.height - fm.getMaxAscent();
/*  745 */         adjX -= fm.getMaxAscent() / 2;
/*      */       }
/*      */ 
/*  748 */       break;
/*      */     default:
/*      */       return;
/*      */     }
/*      */     Rectangle extent;
/*  755 */     this.globals.stringRotator.drawString(str, adjX, this.globals.maxY - adjY, adjAngle, this.globals.image);
/*      */   }
/*      */ 
/*      */   public void drawString(Graphics g, int startx, int starty, String str)
/*      */   {
/*  767 */     if (g.getColor() == TRANSPARENT) {
/*  768 */       return;
/*      */     }
/*      */     try
/*      */     {
/*  772 */       g.drawString(str, startx, this.globals.maxY - starty);
/*      */     }
/*      */     catch (NullPointerException localNullPointerException) {
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void drawThickLine(Graphics g, Point[] pts, int lw, Globals gl) {
/*  779 */     if (pts.length == 0) {
/*  780 */       return;
/*      */     }
/*      */ 
/*  783 */     double[] xVals = new double[pts.length];
/*  784 */     double[] yVals = new double[pts.length];
/*  785 */     double width = lw;
/*      */ 
/*  787 */     for (int i = 0; i < pts.length; i++) {
/*  788 */       xVals[i] = pts[i].x;
/*  789 */       yVals[i] = (gl.maxY - pts[i].y);
/*      */     }
/*      */ 
/*  792 */     double[] xFactor = new double[xVals.length - 1];
/*  793 */     double[] yFactor = new double[xVals.length - 1];
/*      */ 
/*  795 */     for (int i = 1; i < xVals.length; i++) {
/*  796 */       double atan = Math.atan2(xVals[i] - xVals[(i - 1)], yVals[i] - yVals[(i - 1)]);
/*  797 */       xFactor[(i - 1)] = Math.cos(atan);
/*  798 */       yFactor[(i - 1)] = Math.sin(atan);
/*      */     }
/*      */ 
/*  801 */     int[] xRect = new int[4];
/*  802 */     int[] yRect = new int[4];
/*      */ 
/*  808 */     for (int i = 1; i < xVals.length; i++) {
/*  809 */       xRect[0] = ((int)(xVals[(i - 1)] + width * xFactor[(i - 1)]));
/*  810 */       yRect[0] = ((int)(yVals[(i - 1)] - width * yFactor[(i - 1)]));
/*  811 */       xRect[1] = ((int)(xVals[i] + width * xFactor[(i - 1)]));
/*  812 */       yRect[1] = ((int)(yVals[i] - width * yFactor[(i - 1)]));
/*  813 */       xRect[2] = ((int)(xVals[i] - width * xFactor[(i - 1)]));
/*  814 */       yRect[2] = ((int)(yVals[i] + width * yFactor[(i - 1)]));
/*  815 */       xRect[3] = ((int)(xVals[(i - 1)] - width * xFactor[(i - 1)]));
/*  816 */       yRect[3] = ((int)(yVals[(i - 1)] + width * yFactor[(i - 1)]));
/*  817 */       g.fillPolygon(xRect, yRect, 4);
/*      */     }
/*      */ 
/*  820 */     for (int i = 1; i < xVals.length - 1; i++)
/*  821 */       g.fillOval((int)(xVals[i] - width), (int)(yVals[i] - width), (int)width * 2, 
/*  822 */         (int)width * 2);
/*      */   }
/*      */ 
/*      */   public void fillArc(Graphics g, Point center, Point heightWidth, int startAngle, int endAngle)
/*      */   {
/*  852 */     float[] x = (float[])null;
/*  853 */     float[] y = (float[])null;
/*  854 */     double increment = 0.0174532925199433D;
/*      */ 
/*  856 */     if (endAngle == 0) {
/*  857 */       return;
/*      */     }
/*      */ 
/*  860 */     double startRadians = startAngle / 180.0D * 3.141592653589793D;
/*  861 */     double totalRadians = endAngle / 180.0D * 3.141592653589793D;
/*  862 */     int nPoints = (int)(totalRadians / increment);
/*      */ 
/*  864 */     if (nPoints < 2) {
/*  865 */       x = new float[3];
/*  866 */       y = new float[3];
/*  867 */       x[2] = center.x;
/*  868 */       y[2] = center.y;
/*  869 */       x[0] = ((float)(center.x + Math.cos(startRadians) * (heightWidth.x / 2)));
/*  870 */       y[0] = ((float)(center.y + Math.sin(startRadians) * (heightWidth.y / 2)));
/*  871 */       x[1] = 
/*  872 */         ((float)(center.x + 
/*  872 */         Math.cos(startRadians + totalRadians) * (heightWidth.x / 2)));
/*  873 */       y[1] = 
/*  874 */         ((float)(center.y + 
/*  874 */         Math.sin(startRadians + totalRadians) * (heightWidth.y / 2)));
/*      */     }
/*      */     else {
/*  877 */       x = new float[nPoints + 1];
/*  878 */       y = new float[x.length];
/*  879 */       x[nPoints] = center.x;
/*  880 */       y[nPoints] = center.y;
/*      */ 
/*  882 */       double currentRadians = startRadians;
/*      */ 
/*  885 */       for (int i = 0; i < nPoints; i++) {
/*  886 */         if (i == nPoints - 1) {
/*  887 */           currentRadians = startRadians + totalRadians;
/*      */         }
/*      */ 
/*  890 */         double xVal = center.x + Math.cos(currentRadians) * (heightWidth.x / 2);
/*  891 */         double yVal = center.y + Math.sin(currentRadians) * (heightWidth.y / 2);
/*  892 */         x[i] = ((float)xVal);
/*  893 */         y[i] = ((float)yVal);
/*  894 */         currentRadians += increment;
/*      */       }
/*      */     }
/*      */ 
/*  898 */     if (this.globals.java2Capable) {
/*  899 */       getHelper().drawPolygon(g, x, y);
/*      */     } else {
/*  901 */       Point[] arcArray = new Point[x.length];
/*      */ 
/*  903 */       for (int i = 0; i < x.length; i++) {
/*  904 */         arcArray[i] = new Point(Math.round(x[i]), Math.round(y[i]));
/*      */       }
/*      */ 
/*  907 */       drawPolygon(g, arcArray);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void fillRect(Graphics g, Point ll, Point ur)
/*      */   {
/*      */     int urx;
/*      */     int llx;
/*      */     int urx;
/*  935 */     if (ll.x > ur.x) {
/*  936 */       int llx = ur.x;
/*  937 */       urx = ll.x;
/*      */     } else {
/*  939 */       llx = ll.x;
/*  940 */       urx = ur.x;
/*      */     }
/*      */ 
/*  943 */     if (this.fillColor != TRANSPARENT) {
/*  944 */       if ((this.globals.java2Capable) && (
/*  945 */         (this.fillStyle == 0) || (this.fillStyle == 1))) {
/*  946 */         if (this.gcHelper == null) {
/*  947 */           this.gcHelper = new GcHelper(this);
/*      */         }
/*      */ 
/*  950 */         this.gcHelper.fillRect(g, ll, ur);
/*      */       }
/*      */       else
/*      */       {
/*  954 */         g.setColor(this.fillColor);
/*      */ 
/*  956 */         if (ur.y > ll.y)
/*  957 */           g.fillRect(llx, this.globals.maxY - ur.y, urx - llx, ur.y - ll.y);
/*      */         else {
/*  959 */           g.fillRect(llx, this.globals.maxY - ll.y, urx - llx, ll.y - ur.y);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  964 */     if (this.outlineFills) {
/*  965 */       if (this.lineColor == TRANSPARENT) {
/*  966 */         return;
/*      */       }
/*      */ 
/*  969 */       g.setColor(this.lineColor);
/*      */ 
/*  971 */       if (ur.y > ll.y)
/*  972 */         g.drawRect(llx, this.globals.getMaxY() - ur.y, urx - llx, ur.y - ll.y);
/*      */       else
/*  974 */         g.drawRect(llx, this.globals.getMaxY() - ll.y, urx - llx, ll.y - ur.y);
/*      */     }
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static String formattedLabel(String s, int format, int labelPrecision)
/*      */   {
/*  997 */     int i = s.lastIndexOf(".");
/*      */ 
/*  999 */     if (i != -1) {
/* 1000 */       int length = s.length();
/*      */ 
/* 1003 */       if ((labelPrecision == 0) || ((i == length - 2) && (s.charAt(length - 1) == '0')))
/* 1004 */         s = s.substring(0, i);
/* 1005 */       else if (length > 1 + i + labelPrecision) {
/* 1006 */         s = s.substring(0, i + 1 + labelPrecision);
/*      */       }
/*      */     }
/*      */ 
/* 1010 */     if (format == 0)
/* 1011 */       return s;
/*      */     char dot;
/*      */     char comma;
/*      */     char dot;
/* 1014 */     if (format == 1) {
/* 1015 */       char comma = ',';
/* 1016 */       dot = '.';
/*      */     } else {
/* 1018 */       comma = '.';
/* 1019 */       dot = ',';
/*      */     }
/*      */ 
/* 1022 */     int decimalLocation = s.indexOf('.');
/*      */ 
/* 1024 */     if (decimalLocation == -1)
/* 1025 */       decimalLocation = s.length();
/* 1026 */     else if (format == 2)
/* 1027 */       s = s.replace('.', ',');
/*      */     char[] newChars;
/*      */     char[] newChars;
/* 1030 */     if (decimalLocation % 3 != 0)
/* 1031 */       newChars = new char[s.length() + decimalLocation / 3];
/*      */     else {
/* 1033 */       newChars = new char[s.length() + decimalLocation / 3 - 1];
/*      */     }
/*      */ 
/* 1036 */     char[] oldChars = s.toCharArray();
/*      */ 
/* 1038 */     i = oldChars.length - 1; for (int j = newChars.length - 1; i >= decimalLocation; i--) {
/* 1039 */       newChars[j] = oldChars[i];
/* 1040 */       j--;
/*      */     }
/*      */     int firstChar;
/*      */     int firstChar;
/* 1043 */     if (oldChars[0] == '-')
/* 1044 */       firstChar = 1;
/*      */     else {
/* 1046 */       firstChar = 0;
/*      */     }
/*      */ 
/* 1049 */     j = 0; for (i = 0; i < decimalLocation; j++) {
/* 1050 */       if (((decimalLocation - i) % 3 == 0) && 
/* 1051 */         (j > firstChar) && (i < decimalLocation)) {
/* 1052 */         newChars[j] = comma;
/* 1053 */         j++;
/*      */       }
/*      */ 
/* 1057 */       newChars[j] = oldChars[i];
/*      */ 
/* 1049 */       i++;
/*      */     }
/*      */ 
/* 1060 */     return new String(newChars);
/*      */   }
/*      */ 
/*      */   public Color getFillColor()
/*      */   {
/* 1069 */     return this.fillColor;
/*      */   }
/*      */ 
/*      */   public Image getImage()
/*      */   {
/* 1078 */     return this.image;
/*      */   }
/*      */ 
/*      */   public Color getLineColor()
/*      */   {
/* 1087 */     return this.lineColor;
/*      */   }
/*      */ 
/*      */   public int getLineStyle()
/*      */   {
/* 1096 */     return this.lineStyle;
/*      */   }
/*      */ 
/*      */   public int getLineWidth()
/*      */   {
/* 1105 */     return this.lineWidth;
/*      */   }
/*      */ 
/*      */   public int getMarkerSize()
/*      */   {
/* 1114 */     return this.markerSize;
/*      */   }
/*      */ 
/*      */   public int getMarkerStyle()
/*      */   {
/* 1123 */     return this.markerStyle;
/*      */   }
/*      */ 
/*      */   public boolean getOutlineFills()
/*      */   {
/* 1132 */     return this.outlineFills;
/*      */   }
/*      */ 
/*      */   protected static int getStringWidth(FontMetrics fm, String str)
/*      */   {
/* 1142 */     int width = 0;
/*      */ 
/* 1145 */     StringTokenizer st = new StringTokenizer(str, "|");
/*      */ 
/* 1147 */     while (st.hasMoreTokens()) {
/* 1148 */       String currentLine = st.nextToken();
/* 1149 */       int lineWidth = fm.stringWidth(currentLine);
/*      */ 
/* 1151 */       if (lineWidth > width) {
/* 1152 */         width = lineWidth;
/*      */       }
/*      */     }
/*      */ 
/* 1156 */     return width;
/*      */   }
/*      */ 
/*      */   protected static int getStringHeight(FontMetrics fm, String str)
/*      */   {
/* 1166 */     int height = 0;
/* 1167 */     int lineHeight = fm.getMaxAscent();
/* 1168 */     StringTokenizer st = new StringTokenizer(str, "|");
/* 1169 */     int lines = st.countTokens();
/* 1170 */     height = lineHeight * lines + (lineHeight / 5 + 1) * (lines - 1);
/*      */ 
/* 1172 */     return height;
/*      */   }
/*      */ 
/*      */   private static synchronized void init()
/*      */   {
/* 1179 */     defaultFont = new Font("TimesRoman", 0, 12);
/* 1180 */     TRANSPARENT = new Color(1, 1, 1);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public static String nonSciNumberStr(String s, int eIndex)
/*      */   {
/* 1196 */     char[] zeroes = (char[])null;
/*      */ 
/* 1198 */     int i = s.indexOf('+');
/*      */     int mantissa;
/*      */     int mantissa;
/* 1200 */     if (i == -1)
/* 1201 */       mantissa = Integer.parseInt(s.substring(eIndex + 1));
/*      */     else {
/* 1203 */       mantissa = Integer.parseInt(s.substring(i + 1));
/*      */     }
/*      */ 
/* 1206 */     String trimmedString = s.substring(0, eIndex).trim();
/* 1207 */     int decimalLocation = trimmedString.indexOf('.');
/*      */ 
/* 1209 */     if (decimalLocation == -1) {
/* 1210 */       if (mantissa > 0)
/* 1211 */         i = mantissa;
/*      */       else {
/* 1213 */         i = -mantissa;
/*      */       }
/*      */     }
/* 1216 */     else if (mantissa > 0)
/* 1217 */       i = mantissa - (trimmedString.length() - decimalLocation) + 1;
/*      */     else {
/* 1219 */       i = -mantissa - decimalLocation;
/*      */     }
/*      */ 
/* 1223 */     if (i > 0) {
/* 1224 */       zeroes = new char[i];
/*      */ 
/* 1226 */       for (i = 0; i < zeroes.length; i++) {
/* 1227 */         zeroes[i] = '0';
/*      */       }
/*      */     }
/* 1230 */     if (decimalLocation == -1) {
/* 1231 */       if (mantissa < 0) {
/* 1232 */         return "0." + new String(zeroes) + trimmedString;
/*      */       }
/* 1234 */       return trimmedString + new String(zeroes);
/*      */     }
/* 1236 */     if (mantissa < 0)
/* 1237 */       return "0." + new String(zeroes) + trimmedString.substring(0, decimalLocation) + 
/* 1238 */         trimmedString.substring(decimalLocation + 1);
/* 1239 */     if (zeroes != null) {
/* 1240 */       return trimmedString.substring(0, decimalLocation) + 
/* 1241 */         trimmedString.substring(decimalLocation + 1) + new String(zeroes);
/*      */     }
/*      */ 
/* 1244 */     int newDecimalLoc = trimmedString.length() + i;
/*      */ 
/* 1246 */     return trimmedString.substring(0, decimalLocation) + 
/* 1247 */       trimmedString.substring(decimalLocation + 1, newDecimalLoc) + '.' + 
/* 1248 */       trimmedString.substring(newDecimalLoc);
/*      */   }
/*      */ 
/*      */   private Color randomColor()
/*      */   {
/* 1256 */     float r = (float)Math.random();
/* 1257 */     float g = (float)Math.random();
/* 1258 */     float b = (float)Math.random();
/*      */ 
/* 1260 */     return new Color(r, g, b);
/*      */   }
/*      */ 
/*      */   public void setFillColor(Color c)
/*      */   {
/* 1269 */     this.fillColor = c;
/*      */ 
/* 1271 */     if (this.gcHelper != null) {
/* 1272 */       this.gcHelper.gradient = null;
/* 1273 */       this.gcHelper.texture = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setImage(Image i)
/*      */   {
/* 1283 */     this.image = i;
/*      */   }
/*      */ 
/*      */   public void setLineColor(Color c)
/*      */   {
/* 1292 */     this.lineColor = c;
/*      */   }
/*      */ 
/*      */   public void setLineStyle(int i)
/*      */   {
/* 1301 */     this.lineStyle = i;
/*      */ 
/* 1303 */     if (this.gcHelper != null)
/* 1304 */       this.gcHelper.stroke = null;
/*      */   }
/*      */ 
/*      */   public void setLineWidth(int i)
/*      */   {
/* 1314 */     this.lineWidth = i;
/*      */ 
/* 1316 */     if (this.gcHelper != null)
/* 1317 */       this.gcHelper.stroke = null;
/*      */   }
/*      */ 
/*      */   public void setMarkerSize(int i)
/*      */   {
/* 1327 */     this.markerSize = i;
/*      */   }
/*      */ 
/*      */   public void setMarkerStyle(int i)
/*      */   {
/* 1336 */     this.markerStyle = i;
/*      */   }
/*      */ 
/*      */   public void setOutlineFills(boolean outline)
/*      */   {
/* 1345 */     this.outlineFills = outline;
/*      */   }
/*      */ 
/*      */   public void setGradient(int grad)
/*      */   {
/* 1354 */     if (!this.globals.java2Capable) {
/* 1355 */       return;
/*      */     }
/*      */     try
/*      */     {
/* 1359 */       this.fillStyle = 0;
/* 1360 */       getHelper().gradientType = grad;
/* 1361 */       this.gcHelper.gradient = null;
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getGradient()
/*      */   {
/* 1373 */     if (!this.globals.java2Capable) {
/* 1374 */       return -1;
/*      */     }
/*      */     try
/*      */     {
/* 1378 */       return getHelper().gradientType; } catch (Exception noGrad) {
/*      */     }
/* 1380 */     return -1;
/*      */   }
/*      */ 
/*      */   public GcHelper getHelper()
/*      */   {
/*      */     try
/*      */     {
/* 1391 */       if (this.gcHelper == null) {
/* 1392 */         if (!this.globals.java2Capable) {
/* 1393 */           return null;
/*      */         }
/*      */ 
/* 1396 */         this.gcHelper = new GcHelper(this);
/*      */       }
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/* 1402 */     return this.gcHelper;
/*      */   }
/*      */ 
/*      */   public void setSecondaryFillColor(Color c)
/*      */   {
/* 1412 */     if (this.globals.java2Capable) {
/* 1413 */       getHelper().secondaryFillColor = c;
/* 1414 */       this.gcHelper.gradient = null;
/* 1415 */       this.gcHelper.texture = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Color getSecondaryFillColor()
/*      */   {
/* 1425 */     if (!this.globals.java2Capable) {
/* 1426 */       return this.fillColor;
/*      */     }
/*      */ 
/* 1429 */     return getHelper().secondaryFillColor;
/*      */   }
/*      */ 
/*      */   public void setTexture(int texture)
/*      */   {
/* 1439 */     if (!this.globals.java2Capable) {
/* 1440 */       return;
/*      */     }
/*      */     try
/*      */     {
/* 1444 */       getHelper().textureType = texture;
/* 1445 */       this.gcHelper.texture = null;
/* 1446 */       this.fillStyle = 1;
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getTexture()
/*      */   {
/* 1458 */     if (!this.globals.java2Capable) {
/* 1459 */       return 1;
/*      */     }
/*      */ 
/* 1462 */     return getHelper().textureType;
/*      */   }
/*      */ 
/*      */   public void setFillStyle(int style)
/*      */   {
/* 1470 */     this.fillStyle = style;
/*      */   }
/*      */ 
/*      */   public int getFillStyle()
/*      */   {
/* 1478 */     return this.fillStyle;
/*      */   }
/*      */ 
/*      */   public void setGlobals(Globals g)
/*      */   {
/* 1486 */     this.globals = g;
/*      */   }
/*      */ 
/*      */   public Globals getGlobals()
/*      */   {
/* 1494 */     return this.globals;
/*      */   }
/*      */ 
/*      */   public byte[] getImageBytes()
/*      */   {
/* 1503 */     return this.imageBytes;
/*      */   }
/*      */ 
/*      */   public void setImageBytes(byte[] imageBytes)
/*      */   {
/* 1512 */     this.imageBytes = imageBytes;
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Gc
 * JD-Core Version:    0.6.2
 */