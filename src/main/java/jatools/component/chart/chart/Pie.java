/*      */ package jatools.component.chart.chart;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.Format;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.StringTokenizer;
/*      */ 
/*      */ public class Pie extends DataRepresentation
/*      */   implements Serializable
/*      */ {
/*   32 */   boolean textLabelsOn = false;
/*   33 */   boolean valueLabelsOn = false;
/*   34 */   boolean percentLabelsOn = true;
/*   35 */   int labelPosition = 2;
/*   36 */   int labelFormat = 0;
/*   37 */   int labelPrecision = 0;
/*   38 */   double xLoc = 0.5D;
/*   39 */   double yLoc = 0.5D;
/*   40 */   int startDegrees = 0;
/*   41 */   double width = 0.6D;
/*   42 */   double height = 0.6D;
/*   43 */   Font labelFont = Gc.defaultFont;
/*   44 */   Color labelColor = Color.black;
/*      */   Dataset dataset;
/*   48 */   double total = 0.0D;
/*   49 */   Gc lineGc = new Gc(Color.black, null);
/*      */   private Point widthHeight;
/*      */   protected NumberFormat percentFormat;
/*   52 */   ArrayList sideInfo = new ArrayList();
/*   53 */   ArrayList sideOrder = new ArrayList();
/*   54 */   ArrayList edgeInfo = new ArrayList();
/*   55 */   ArrayList labelInfo = new ArrayList();
/*      */ 
/*      */   public Pie()
/*      */   {
/*      */   }
/*      */ 
/*      */   public Pie(Dataset d, Plotarea p, Globals g)
/*      */   {
/*   73 */     this.dataset = d;
/*   74 */     this.plotarea = p;
/*   75 */     this.globals = g;
/*   76 */     this.lineGc.globals = g;
/*      */   }
/*      */ 
/*      */   private void collectEdge(Datum dat, Point p, Point widthHeight, int angleOne, int angleTwo)
/*      */   {
/*   88 */     int i = 0;
/*      */ 
/*   90 */     while ((i < this.edgeInfo.size()) && (dat.y2 < ((Datum)this.edgeInfo.get(i)).y2)) {
/*   91 */       i += 5;
/*      */     }
/*      */ 
/*   94 */     this.edgeInfo.add(i++, dat);
/*   95 */     this.edgeInfo.add(i++, p);
/*   96 */     this.edgeInfo.add(i++, widthHeight);
/*   97 */     this.edgeInfo.add(i++, new Integer(angleOne));
/*   98 */     this.edgeInfo.add(i, new Integer(angleTwo));
/*      */   }
/*      */ 
/*      */   private void collectSide(Datum d, Point center, Point widthHeight, int startAngle, int arcAngle)
/*      */   {
/*  109 */     double startRadians = startAngle / 180.0D * 3.141592653589793D;
/*  110 */     float xVal = (float)(center.x + Math.round(Math.cos(startRadians) * (widthHeight.x / 2.0D)));
/*  111 */     float yVal = (float)(center.y + Math.round(Math.sin(startRadians) * (widthHeight.y / 2.0D)));
/*      */ 
/*  118 */     float p1x = center.x;
/*  119 */     float p1y = center.y - this.globals.yOffset + 1.0F;
/*  120 */     float p2x = xVal;
/*  121 */     float p2y = yVal - this.globals.yOffset + 1.0F;
/*  122 */     float p3x = xVal;
/*  123 */     float p3y = yVal;
/*      */ 
/*  125 */     while (startAngle > 360) {
/*  126 */       startAngle -= 360;
/*      */     }
/*  128 */     if ((startAngle > 270) || (startAngle < 90)) {
/*  129 */       insertIntoSideInfo(startAngle, d, center, p1x, p1y, p2x, p2y, p3x, p3y);
/*      */     }
/*      */ 
/*  132 */     int endAngle = startAngle + arcAngle;
/*      */ 
/*  134 */     while (endAngle > 360) {
/*  135 */       endAngle -= 360;
/*      */     }
/*  137 */     double endRadians = endAngle / 180.0D * 3.141592653589793D;
/*  138 */     xVal = center.x + (int)Math.round(Math.cos(endRadians) * (widthHeight.x / 2));
/*  139 */     yVal = center.y + (int)Math.round(Math.sin(endRadians) * (widthHeight.y / 2));
/*      */ 
/*  145 */     p2x = xVal;
/*  146 */     p2y = yVal - this.globals.yOffset + 1.0F;
/*  147 */     p3x = xVal;
/*  148 */     p3y = yVal;
/*      */ 
/*  150 */     if ((endAngle > 90) && (endAngle < 270))
/*  151 */       insertIntoSideInfo(endAngle, d, center, p1x, p1y, p2x, p2y, p3x, p3y);
/*      */   }
/*      */ 
/*      */   protected void doDPie(Graphics g, double xCenter, double yCenter)
/*      */   {
/*  164 */     int startAngle = this.startDegrees;
/*      */ 
/*  173 */     this.widthHeight = this.plotarea.transform.point(this.width, this.height);
/*  174 */     Point[] pt = new Point[4];
/*      */ 
/*  176 */     for (int i = 0; i < this.dataset.data.size(); i++) {
/*  177 */       this.total += this.dataset.getDataElementAt(i).y;
/*      */     }
/*      */ 
/*  180 */     double cumulativeRoundingError = 0.0D;
/*      */ 
/*  184 */     this.sideInfo.clear();
/*  185 */     this.sideOrder.clear();
/*  186 */     startAngle = this.startDegrees;
/*  187 */     cumulativeRoundingError = 0.0D;
/*      */ 
/*  189 */     boolean lastSlice = false;
/*  190 */     ArrayList startAngles = new ArrayList();
/*  191 */     ArrayList arcAngles = new ArrayList();
/*  192 */     ArrayList explodedXs = new ArrayList();
/*  193 */     ArrayList explodedYs = new ArrayList();
/*      */ 
/*  195 */     for (i = 0; i < this.dataset.data.size(); i++) {
/*  196 */       Datum dat = this.dataset.getDataElementAt(i);
/*      */       int arcAngle;
/*  198 */       if (i == this.dataset.data.size() - 1) {
/*  199 */         int arcAngle = 360 - startAngle + this.startDegrees;
/*  200 */         lastSlice = true;
/*      */       } else {
/*  202 */         double val = 360.0D * dat.y / this.total;
/*  203 */         arcAngle = (int)Math.round(val);
/*  204 */         cumulativeRoundingError += val - arcAngle;
/*      */       }
/*      */ 
/*  207 */       if (Math.abs(cumulativeRoundingError) > 1.0D) {
/*  208 */         arcAngle += (int)cumulativeRoundingError;
/*  209 */         cumulativeRoundingError -= Math.floor(cumulativeRoundingError);
/*      */       }
/*      */ 
/*  212 */       double radians = (arcAngle / 2 + startAngle) / 180.0D * 3.141592653589793D;
/*      */       double explodedY;
/*      */       double explodedX;
/*      */       double explodedY;
/*  214 */       if (dat.y2 != (-1.0D / 0.0D)) {
/*  215 */         double explodedX = this.xLoc + dat.y2 * Math.cos(radians);
/*  216 */         explodedY = this.yLoc + dat.y2 * Math.sin(radians);
/*      */       } else {
/*  218 */         explodedX = this.xLoc;
/*  219 */         explodedY = this.yLoc;
/*      */       }
/*      */ 
/*  222 */       if (lastSlice);
/*  230 */       startAngles.add(new Integer(startAngle));
/*  231 */       arcAngles.add(new Integer(arcAngle));
/*  232 */       explodedXs.add(new Double(explodedX));
/*  233 */       explodedYs.add(new Double(explodedY));
/*      */ 
/*  235 */       collectSide(dat, this.plotarea.transform.point(explodedX, explodedY), this.widthHeight, 
/*  236 */         startAngle, arcAngle);
/*  237 */       startAngle += arcAngle;
/*      */     }
/*      */ 
/*  240 */     drawSides(g);
/*      */ 
/*  245 */     this.edgeInfo.clear();
/*  246 */     startAngle = this.startDegrees;
/*  247 */     cumulativeRoundingError = 0.0D;
/*      */ 
/*  249 */     for (i = 0; i < this.dataset.data.size(); i++) {
/*  250 */       startAngle = ((Integer)startAngles.get(i)).intValue();
/*  251 */       int arcAngle = ((Integer)arcAngles.get(i)).intValue();
/*      */ 
/*  253 */       double x = ((Double)explodedXs.get(i)).doubleValue();
/*  254 */       double y = ((Double)explodedYs.get(i)).doubleValue();
/*  255 */       collectEdge(this.dataset.getDataElementAt(i), this.plotarea.transform.point(x, y), this.widthHeight, 
/*  256 */         startAngle, arcAngle);
/*      */     }
/*      */ 
/*  259 */     drawEdges(g);
/*      */ 
/*  263 */     startAngle = this.startDegrees;
/*  264 */     cumulativeRoundingError = 0.0D;
/*      */ 
/*  266 */     for (i = 0; i < this.dataset.data.size(); i++) {
/*  267 */       Datum dat = this.dataset.getDataElementAt(i);
/*  268 */       startAngle = ((Integer)startAngles.get(i)).intValue();
/*  269 */       int arcAngle = ((Integer)arcAngles.get(i)).intValue();
/*      */ 
/*  271 */       double x = ((Double)explodedXs.get(i)).doubleValue();
/*  272 */       double y = ((Double)explodedYs.get(i)).doubleValue();
/*  273 */       dat.gc.fillArc(g, this.plotarea.transform.point(x, y), this.widthHeight, startAngle, arcAngle);
/*      */ 
/*  275 */       if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/*  276 */         this.globals.displayList.addArc(dat, this.plotarea.transform.point(x, y), 
/*  277 */           this.plotarea.transform.point(this.width, this.height), startAngle, arcAngle);
/*  278 */         this.globals.displayList.addArc(this.dataset, this.plotarea.transform.point(x, y), 
/*  279 */           this.plotarea.transform.point(this.width, this.height), startAngle, arcAngle);
/*      */       }
/*      */ 
/*  282 */       double radians = (arcAngle / 2 + startAngle) / 180.0D * 3.141592653589793D;
/*      */ 
/*  284 */       if ((this.textLabelsOn) || (this.valueLabelsOn) || (this.percentLabelsOn)) {
/*  285 */         doPieLabel(g, x, y, radians, dat);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  290 */     this.total = 0.0D;
/*      */   }
/*      */ 
/*      */   protected void doPie(Graphics g, double xCenter, double yCenter)
/*      */   {
/*  304 */     int startAngle = this.startDegrees;
/*      */ 
/*  310 */     for (int i = 0; i < this.dataset.data.size(); i++) {
/*  311 */       this.total += this.dataset.getDataElementAt(i).y;
/*      */     }
/*      */ 
/*  314 */     double cumulativeRoundingError = 0.0D;
/*      */ 
/*  316 */     for (i = 0; i < this.dataset.data.size(); i++) {
/*  317 */       Datum dat = this.dataset.getDataElementAt(i);
/*      */       int arcAngle;
/*      */       int arcAngle;
/*  319 */       if (i == this.dataset.data.size() - 1) {
/*  320 */         arcAngle = 360 - startAngle + this.startDegrees;
/*      */       } else {
/*  322 */         double val = 360.0D * dat.y / this.total;
/*  323 */         arcAngle = (int)Math.round(val);
/*  324 */         cumulativeRoundingError += val - arcAngle;
/*      */       }
/*      */ 
/*  327 */       if (Math.abs(cumulativeRoundingError) > 1.0D) {
/*  328 */         arcAngle += (int)cumulativeRoundingError;
/*  329 */         cumulativeRoundingError -= Math.floor(cumulativeRoundingError);
/*      */       }
/*      */ 
/*  332 */       double radians = (arcAngle / 2 + startAngle) / 180.0D * 3.141592653589793D;
/*      */       double explodedY;
/*      */       double explodedX;
/*      */       double explodedY;
/*  334 */       if (dat.y2 != (-1.0D / 0.0D)) {
/*  335 */         double explodedX = this.xLoc + dat.y2 * Math.cos(radians);
/*  336 */         explodedY = this.yLoc + dat.y2 * Math.sin(radians);
/*      */       } else {
/*  338 */         explodedX = this.xLoc;
/*  339 */         explodedY = this.yLoc;
/*      */       }
/*      */ 
/*  342 */       dat.gc.fillArc(g, this.plotarea.transform.point(explodedX, explodedY), 
/*  343 */         this.plotarea.transform.point(this.width, this.height), startAngle, arcAngle);
/*      */ 
/*  345 */       if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/*  346 */         this.globals.displayList.addArc(dat, this.plotarea.transform.point(explodedX, explodedY), 
/*  347 */           this.plotarea.transform.point(this.width, this.height), startAngle, arcAngle);
/*  348 */         this.globals.displayList.addArc(this.dataset, this.plotarea.transform.point(explodedX, explodedY), 
/*  349 */           this.plotarea.transform.point(this.width, this.height), startAngle, arcAngle);
/*      */       }
/*      */ 
/*  352 */       if ((this.textLabelsOn) || (this.valueLabelsOn) || (this.percentLabelsOn)) {
/*  353 */         doPieLabel(g, explodedX, explodedY, radians, dat);
/*      */       }
/*      */ 
/*  356 */       startAngle += arcAngle;
/*      */     }
/*      */ 
/*  359 */     this.total = 0.0D;
/*      */   }
/*      */ 
/*      */   private void doPieLabel(Graphics g, double centerX, double centerY, double radians, Datum dataPoint)
/*      */   {
/*  376 */     String valueString = null;
/*  377 */     double twoPI = 6.283185307179586D;
/*  378 */     StringTokenizer st = null;
/*  379 */     int longestTextStringWidth = 20;
/*  380 */     PieLabelInfo pieLabelInfo = new PieLabelInfo();
/*      */ 
/*  383 */     while (radians > twoPI) {
/*  384 */       radians -= twoPI;
/*      */     }
/*  386 */     if (this.valueLabelsOn) {
/*  387 */       valueString = formatLabel(dataPoint.y);
/*      */     }
/*      */ 
/*  390 */     g.setFont(this.labelFont);
/*  391 */     g.setColor(this.labelColor);
/*      */ 
/*  393 */     FontMetrics fm = g.getFontMetrics();
/*      */ 
/*  395 */     if ((this.textLabelsOn) && (dataPoint.label != null)) {
/*  396 */       st = new StringTokenizer(dataPoint.label, "|");
/*      */ 
/*  398 */       while (st.hasMoreTokens()) {
/*  399 */         int length = fm.stringWidth(st.nextToken());
/*      */ 
/*  401 */         if (length > longestTextStringWidth) {
/*  402 */           longestTextStringWidth = length;
/*      */         }
/*      */       }
/*      */ 
/*  406 */       st = new StringTokenizer(dataPoint.label, "|");
/*      */     }
/*      */     int y;
/*  409 */     if (this.labelPosition == 1)
/*      */     {
/*  411 */       double pieEdgeX = centerX + this.width / 2.0D * Math.cos(radians);
/*  412 */       double pieEdgeY = centerY + this.height / 2.0D * Math.sin(radians);
/*  413 */       Point pixelLoc = this.plotarea.transform.point(pieEdgeX, pieEdgeY);
/*  414 */       int x = pixelLoc.x;
/*  415 */       y = pixelLoc.y;
/*      */     }
/*      */     else
/*      */     {
/*      */       int y;
/*  416 */       if (this.labelPosition == 0)
/*      */       {
/*  418 */         double pieEdgeX = centerX + this.width / 4.0D * Math.cos(radians);
/*  419 */         double pieEdgeY = centerY + this.height / 4.0D * Math.sin(radians);
/*  420 */         Point pixelLoc = this.plotarea.transform.point(pieEdgeX, pieEdgeY);
/*  421 */         int x = pixelLoc.x;
/*  422 */         y = pixelLoc.y;
/*  423 */       } else if (this.labelPosition == 2)
/*      */       {
/*  425 */         double pieEdgeX = centerX + this.width / 3.0D * Math.cos(radians);
/*  426 */         double pieEdgeY = centerY + this.height / 3.0D * Math.sin(radians);
/*      */ 
/*  428 */         double extenderLength = 0.2D;
/*      */ 
/*  430 */         if (dataPoint.y3 != (-1.0D / 0.0D)) {
/*  431 */           extenderLength = dataPoint.y3;
/*      */         }
/*      */ 
/*  434 */         double pointerEndX = centerX + this.width / (2.0D - extenderLength) * Math.cos(radians);
/*  435 */         double pointerEndY = centerY + this.height / (2.0D - extenderLength) * Math.sin(radians);
/*      */ 
/*  437 */         Point pointerStart = this.plotarea.transform.point(pieEdgeX, pieEdgeY);
/*  438 */         Point pointerEnd = this.plotarea.transform.point(pointerEndX, pointerEndY);
/*      */ 
/*  443 */         pointerEndX = centerX + this.width / (2.0D - extenderLength - 0.01D) * Math.cos(radians);
/*  444 */         pointerEndY = centerY + this.height / (2.0D - extenderLength - 0.01D) * Math.sin(radians);
/*  445 */         Point pixelLoc = this.plotarea.transform.point(pointerEndX, pointerEndY);
/*  446 */         int x = pixelLoc.x;
/*  447 */         int y = pixelLoc.y;
/*      */ 
/*  450 */         pieLabelInfo.setRadians(radians);
/*  451 */         pieLabelInfo.startPointer = pointerStart;
/*  452 */         pieLabelInfo.endPointer = pointerEnd;
/*      */       }
/*      */       else
/*      */       {
/*      */         return;
/*      */       }
/*      */     }
/*      */     int y;
/*      */     int x;
/*      */     Point pixelLoc;
/*      */     double pieEdgeY;
/*      */     double pieEdgeX;
/*  457 */     if ((this.globals.threeD) && (radians > 3.141592653589793D) && (this.labelPosition > 0)) {
/*  458 */       y += (int)(Math.sin(radians) * this.globals.yOffset);
/*      */     }
/*      */ 
/*  462 */     if ((radians >= 4.71238898038469D) && (radians <= twoPI))
/*      */     {
/*  464 */       int startY = y;
/*      */ 
/*  466 */       if ((this.textLabelsOn) && (dataPoint.label != null)) {
/*  467 */         while (st.hasMoreTokens()) {
/*  468 */           pieLabelInfo.addString(st.nextToken());
/*      */ 
/*  470 */           y -= fm.getAscent();
/*      */         }
/*      */       }
/*      */ 
/*  474 */       if (this.valueLabelsOn)
/*      */       {
/*  476 */         pieLabelInfo.addString(valueString);
/*  477 */         y -= fm.getAscent();
/*      */       }
/*      */ 
/*  480 */       if (this.percentLabelsOn)
/*      */       {
/*  482 */         pieLabelInfo.addString(pctStr(dataPoint.y / this.total));
/*  483 */         y -= fm.getAscent();
/*      */       }
/*      */ 
/*  486 */       pieLabelInfo.location = new Rectangle(x, y, longestTextStringWidth, startY - y);
/*  487 */       this.labelInfo.add(pieLabelInfo);
/*      */ 
/*  489 */       return;
/*      */     }
/*      */ 
/*  493 */     if ((radians >= 0.0D) && (radians < 1.570796326794897D)) {
/*  494 */       int startY = y;
/*      */ 
/*  496 */       if (this.percentLabelsOn)
/*      */       {
/*  498 */         pieLabelInfo.addString(pctStr(dataPoint.y / this.total));
/*  499 */         y += fm.getAscent();
/*      */       }
/*      */ 
/*  502 */       if (this.valueLabelsOn)
/*      */       {
/*  504 */         pieLabelInfo.addString(valueString);
/*  505 */         y += fm.getAscent();
/*      */       }
/*      */ 
/*  508 */       if ((this.textLabelsOn) && (dataPoint.label != null)) {
/*  509 */         ArrayList v = new ArrayList();
/*      */ 
/*  511 */         while (st.hasMoreTokens()) {
/*  512 */           v.add(st.nextToken());
/*      */         }
/*  514 */         for (int i = v.size(); i > 0; i--)
/*      */         {
/*  516 */           pieLabelInfo.addString((String)v.get(i - 1));
/*  517 */           y += fm.getAscent();
/*      */         }
/*      */       }
/*      */ 
/*  521 */       pieLabelInfo.location = new Rectangle(x, startY, longestTextStringWidth, y - startY);
/*  522 */       this.labelInfo.add(pieLabelInfo);
/*      */ 
/*  524 */       return;
/*      */     }
/*      */ 
/*  528 */     if ((radians >= 1.570796326794897D) && (radians < 3.141592653589793D)) {
/*  529 */       if ((this.textLabelsOn) && (dataPoint.label != null))
/*  530 */         x -= longestTextStringWidth;
/*  531 */       else if (this.valueLabelsOn)
/*  532 */         x -= fm.stringWidth(valueString);
/*      */       else {
/*  534 */         x -= fm.stringWidth(pctStr(dataPoint.y / this.total));
/*      */       }
/*      */ 
/*  537 */       int startY = y;
/*      */ 
/*  539 */       if (this.percentLabelsOn)
/*      */       {
/*  541 */         pieLabelInfo.addString(pctStr(dataPoint.y / this.total));
/*  542 */         y += fm.getAscent();
/*      */       }
/*      */ 
/*  545 */       if (this.valueLabelsOn)
/*      */       {
/*  547 */         pieLabelInfo.addString(valueString);
/*  548 */         y += fm.getAscent();
/*      */       }
/*      */ 
/*  551 */       if ((this.textLabelsOn) && (dataPoint.label != null)) {
/*  552 */         ArrayList v = new ArrayList();
/*      */ 
/*  554 */         while (st.hasMoreTokens()) {
/*  555 */           v.add(st.nextToken());
/*      */         }
/*  557 */         for (int i = v.size(); i > 0; i--)
/*      */         {
/*  559 */           pieLabelInfo.addString((String)v.get(i - 1));
/*  560 */           y += fm.getAscent();
/*      */         }
/*      */       }
/*      */ 
/*  564 */       pieLabelInfo.location = new Rectangle(x, startY, longestTextStringWidth, y - startY);
/*  565 */       this.labelInfo.add(pieLabelInfo);
/*      */ 
/*  567 */       return;
/*      */     }
/*      */ 
/*  571 */     if ((radians >= 3.141592653589793D) && (radians < 4.71238898038469D)) {
/*  572 */       if ((this.textLabelsOn) && (dataPoint.label != null))
/*  573 */         x -= longestTextStringWidth;
/*  574 */       else if (this.valueLabelsOn)
/*  575 */         x -= fm.stringWidth(valueString);
/*      */       else {
/*  577 */         x -= fm.stringWidth(pctStr(dataPoint.y / this.total));
/*      */       }
/*      */ 
/*  581 */       int startY = y;
/*      */ 
/*  583 */       if ((this.textLabelsOn) && (dataPoint.label != null)) {
/*  584 */         while (st.hasMoreTokens())
/*      */         {
/*  586 */           pieLabelInfo.addString(st.nextToken());
/*  587 */           y -= fm.getAscent();
/*      */         }
/*      */       }
/*      */ 
/*  591 */       if (this.valueLabelsOn)
/*      */       {
/*  593 */         pieLabelInfo.addString(valueString);
/*  594 */         y -= fm.getAscent();
/*      */       }
/*      */ 
/*  597 */       if (this.percentLabelsOn)
/*      */       {
/*  599 */         pieLabelInfo.addString(pctStr(dataPoint.y / this.total));
/*  600 */         y -= fm.getAscent();
/*      */       }
/*      */ 
/*  603 */       pieLabelInfo.location = new Rectangle(x, y, longestTextStringWidth, startY - y);
/*  604 */       this.labelInfo.add(pieLabelInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   public synchronized void draw(Graphics g)
/*      */   {
/*  612 */     if (g == null) {
/*  613 */       return;
/*      */     }
/*      */ 
/*  616 */     if (this.dataset == null) {
/*  617 */       this.dataset = this.datasets[0];
/*      */     }
/*      */ 
/*  620 */     if (this.dataset == null) {
/*  621 */       return;
/*      */     }
/*      */ 
/*  625 */     this.labelInfo.clear();
/*      */ 
/*  627 */     if (!this.globals.threeD)
/*  628 */       doPie(g, this.xLoc, this.yLoc);
/*      */     else {
/*  630 */       doDPie(g, this.xLoc, this.yLoc);
/*      */     }
/*      */ 
/*  633 */     drawLabels(g);
/*      */   }
/*      */ 
/*      */   private void drawLabels(Graphics g)
/*      */   {
/*  642 */     adjustLabels();
/*  643 */     g.setFont(this.labelFont);
/*      */ 
/*  645 */     FontMetrics fm = g.getFontMetrics();
/*  646 */     int ascent = fm.getAscent();
/*      */ 
/*  648 */     Iterator en = this.labelInfo.iterator();
/*      */     PieLabelInfo pli;
/*      */     int i;
/*  650 */     for (; en.hasNext(); 
/*  669 */       i < pli.strings.size())
/*      */     {
/*  651 */       pli = (PieLabelInfo)en.next();
/*      */ 
/*  653 */       if (this.labelPosition == 2) {
/*  654 */         this.lineGc.drawLine(g, pli.startPointer, pli.endPointer);
/*      */       }
/*      */ 
/*  657 */       g.setColor(this.labelColor);
/*      */ 
/*  659 */       int x = pli.location.x;
/*  660 */       int y = pli.location.y;
/*      */ 
/*  669 */       i = 0; continue;
/*  670 */       this.lineGc.drawString(g, x, y, (String)pli.strings.get(i));
/*  671 */       y += ascent;
/*      */ 
/*  669 */       i++;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void adjustLabels()
/*      */   {
/*  680 */     if (this.labelPosition != 2) {
/*  681 */       return;
/*      */     }
/*      */ 
/*  684 */     ArrayList compareSet = new ArrayList();
/*      */ 
/*  687 */     for (int i = 0; i < this.labelInfo.size(); i++) {
/*  688 */       PieLabelInfo pli = (PieLabelInfo)this.labelInfo.get(i);
/*      */ 
/*  690 */       if ((pli.isRightTop) || (pli.isLeftBottom)) {
/*  691 */         compareSet.add(pli);
/*      */       }
/*      */     }
/*      */ 
/*  695 */     compareAndShift(compareSet);
/*      */ 
/*  698 */     int sz = this.labelInfo.size() - 1;
/*      */ 
/*  700 */     for (int i = 0; i < this.labelInfo.size(); i++) {
/*  701 */       PieLabelInfo pli = (PieLabelInfo)this.labelInfo.get(sz - i);
/*      */ 
/*  703 */       if ((pli.isLeftTop) || (pli.isRightBottom)) {
/*  704 */         compareSet.add(pli);
/*      */       }
/*      */     }
/*      */ 
/*  708 */     compareAndShift(compareSet);
/*      */   }
/*      */ 
/*      */   private void compareAndShift(ArrayList c) {
/*  712 */     for (int j = 0; j < c.size(); j++) {
/*  713 */       PieLabelInfo pli1 = (PieLabelInfo)c.get(j);
/*      */ 
/*  715 */       for (int i = 0; i < c.size(); i++) {
/*  716 */         PieLabelInfo pli2 = (PieLabelInfo)c.get(i);
/*      */ 
/*  718 */         if ((pli1 != pli2) && 
/*  719 */           (pli1.location.intersects(pli2.location)))
/*      */         {
/*  721 */           if (pli1.isRightTop) {
/*  722 */             if (pli1.radians > pli2.radians) {
/*  723 */               int newY = pli2.location.y + pli1.location.height + 5;
/*  724 */               pli1.endPointer.y -= pli1.location.y - newY;
/*  725 */               pli1.location.y = newY;
/*      */             } else {
/*  727 */               int newY = pli1.location.y + pli2.location.height + 5;
/*  728 */               pli2.endPointer.y -= pli2.location.y - newY;
/*  729 */               pli2.location.y = newY;
/*      */             }
/*  731 */           } else if (pli1.isRightBottom) {
/*  732 */             if (pli1.radians > pli2.radians) {
/*  733 */               int newY = pli1.location.y - (pli2.location.height + 5);
/*  734 */               pli2.endPointer.y -= pli2.location.y - newY;
/*  735 */               pli2.location.y = newY;
/*      */             } else {
/*  737 */               int newY = pli2.location.y - (pli1.location.height + 5);
/*  738 */               pli1.endPointer.y -= pli1.location.y - newY;
/*  739 */               pli1.location.y = newY;
/*      */             }
/*  741 */           } else if (pli1.isLeftTop) {
/*  742 */             if (pli1.radians > pli2.radians) {
/*  743 */               int newY = pli1.location.y + (pli1.location.height + 5);
/*  744 */               pli2.endPointer.y += Math.abs(pli2.location.y - newY);
/*  745 */               pli2.location.y = newY;
/*      */             } else {
/*  747 */               int newY = pli2.location.y + (pli2.location.height + 5);
/*  748 */               pli1.endPointer.y += Math.abs(pli1.location.y - newY);
/*  749 */               pli1.location.y = newY;
/*      */             }
/*  751 */           } else if (pli1.isLeftBottom)
/*  752 */             if (pli1.radians > pli2.radians) {
/*  753 */               int newY = pli2.location.y - (pli1.location.height + 5);
/*  754 */               pli1.endPointer.y -= Math.abs(pli1.location.y - newY);
/*  755 */               pli1.location.y = newY;
/*      */             } else {
/*  757 */               int newY = pli1.location.y - (pli2.location.height + 5);
/*  758 */               pli2.endPointer.y -= Math.abs(pli2.location.y - newY);
/*  759 */               pli2.location.y = newY;
/*      */             }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void drawEdge(Graphics g, Datum dat, Point center, Point widthHeight, int startAngle, int arcAngle)
/*      */   {
/*  770 */     while (startAngle > 360) {
/*  771 */       startAngle -= 360;
/*      */     }
/*  773 */     if ((arcAngle > 180) && ((startAngle + arcAngle) % 360 > 180) && (startAngle > 180)) {
/*  774 */       drawEdge(g, dat, center, widthHeight, startAngle, 360 - startAngle);
/*  775 */       arcAngle = (startAngle + arcAngle) % 360 - 180;
/*  776 */       startAngle = 180;
/*      */     }
/*      */ 
/*  779 */     if (startAngle < 180) {
/*  780 */       arcAngle -= 180 - startAngle;
/*      */ 
/*  782 */       if (arcAngle < 1) {
/*  783 */         return;
/*      */       }
/*      */ 
/*  786 */       startAngle = 180;
/*      */     }
/*      */ 
/*  789 */     if (startAngle + arcAngle > 360) {
/*  790 */       if (startAngle < 180) {
/*  791 */         int val = startAngle + arcAngle;
/*      */ 
/*  793 */         while (val > 360) {
/*  794 */           val -= 360;
/*      */         }
/*      */ 
/*  797 */         arcAngle = val - 180;
/*  798 */         startAngle = 180;
/*      */       } else {
/*  800 */         arcAngle = 360 - startAngle;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  811 */     double increment = 0.0174532925199433D;
/*      */ 
/*  813 */     double currentRadians = startAngle / 180.0D * 3.141592653589793D;
/*  814 */     double totalRadians = arcAngle / 180.0D * 3.141592653589793D;
/*  815 */     int nPoints = (int)(totalRadians / increment);
/*      */ 
/*  817 */     if (nPoints < 2) {
/*  818 */       return;
/*      */     }
/*      */ 
/*  822 */     float[] x = new float[(nPoints + 1) * 2];
/*  823 */     float[] y = new float[x.length];
/*  824 */     double xVal = center.x + 
/*  825 */       Math.cos(currentRadians + totalRadians) * (widthHeight.x / 2.0D);
/*  826 */     double yVal = center.y + 
/*  827 */       Math.sin(currentRadians + totalRadians) * (widthHeight.y / 2.0D) - 
/*  828 */       this.globals.yOffset;
/*  829 */     x[nPoints] = ((float)xVal);
/*  830 */     y[nPoints] = ((float)yVal);
/*  831 */     x[(nPoints + 1)] = ((float)xVal);
/*  832 */     y[(nPoints + 1)] = ((float)(yVal + this.globals.yOffset));
/*      */ 
/*  835 */     for (int i = 0; i < nPoints; i++) {
/*  836 */       xVal = center.x + Math.cos(currentRadians) * (widthHeight.x / 2.0D);
/*  837 */       yVal = center.y + Math.sin(currentRadians) * (widthHeight.y / 2.0D) - 
/*  838 */         this.globals.yOffset;
/*  839 */       x[i] = ((float)xVal);
/*  840 */       y[i] = ((float)yVal);
/*  841 */       x[(x.length - 1 - i)] = ((float)xVal);
/*  842 */       y[(y.length - 1 - i)] = ((float)(yVal + this.globals.yOffset));
/*  843 */       currentRadians += increment;
/*      */     }
/*      */ 
/*  846 */     Color c = dat.gc.fillColor;
/*  847 */     dat.gc.fillColor = c.darker();
/*      */ 
/*  849 */     if (this.globals.java2Capable) {
/*  850 */       dat.gc.getHelper().drawPolygon(g, x, y);
/*      */     } else {
/*  852 */       Point[] arcArray = new Point[x.length];
/*      */ 
/*  854 */       for (int i = 0; i < x.length; i++) {
/*  855 */         arcArray[i] = new Point((int)x[i], (int)y[i]);
/*      */       }
/*      */ 
/*  858 */       dat.gc.drawPolygon(g, arcArray);
/*      */     }
/*      */ 
/*  861 */     dat.gc.fillColor = c;
/*      */   }
/*      */ 
/*      */   private void drawEdges(Graphics g)
/*      */   {
/*  868 */     Iterator en = this.edgeInfo.iterator();
/*      */ 
/*  870 */     while (en.hasNext()) {
/*  871 */       Datum dat = (Datum)en.next();
/*  872 */       Point p1 = (Point)en.next();
/*  873 */       Point p2 = (Point)en.next();
/*  874 */       int i1 = ((Integer)en.next()).intValue();
/*  875 */       int i2 = ((Integer)en.next()).intValue();
/*  876 */       drawEdge(g, dat, p1, p2, i1, i2);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void drawSides(Graphics g)
/*      */   {
/*  884 */     Iterator en = this.sideInfo.iterator();
/*      */ 
/*  886 */     while (en.hasNext()) {
/*  887 */       Datum dat = (Datum)en.next();
/*  888 */       float[] x = (float[])en.next();
/*  889 */       float[] y = (float[])en.next();
/*  890 */       Color c = dat.gc.fillColor;
/*  891 */       dat.gc.fillColor = c.darker();
/*      */ 
/*  893 */       if (this.globals.java2Capable) {
/*  894 */         dat.gc.getHelper().drawPolygon(g, x, y);
/*      */       } else {
/*  896 */         Point[] p = new Point[4];
/*      */ 
/*  898 */         for (int i = 0; i < p.length; i++) {
/*  899 */           p[i] = new Point(Math.round(x[i]), Math.round(y[i]));
/*      */         }
/*  901 */         dat.gc.drawPolygon(g, p);
/*      */       }
/*      */ 
/*  914 */       dat.gc.fillColor = c;
/*      */     }
/*      */   }
/*      */ 
/*      */   private Point getArcPoint(Point center, int angle)
/*      */   {
/*  923 */     double radians = angle / 180.0D * 3.141592653589793D;
/*  924 */     int x = (int)Math.round(this.widthHeight.x / 2 * Math.cos(radians)) + center.x;
/*  925 */     int y = (int)Math.round(this.widthHeight.y / 2 * Math.sin(radians)) + center.y;
/*      */ 
/*  927 */     return new Point(x, y);
/*      */   }
/*      */ 
/*      */   public double getHeight()
/*      */   {
/*  936 */     return this.height;
/*      */   }
/*      */ 
/*      */   public Color getLabelColor()
/*      */   {
/*  945 */     return this.labelColor;
/*      */   }
/*      */ 
/*      */   public Font getLabelFont()
/*      */   {
/*  954 */     return this.labelFont;
/*      */   }
/*      */ 
/*      */   public int getLabelPosition()
/*      */   {
/*  963 */     return this.labelPosition;
/*      */   }
/*      */ 
/*      */   public Color getLineColor()
/*      */   {
/*  971 */     return this.lineGc.lineColor;
/*      */   }
/*      */ 
/*      */   public Format getPercentFormat()
/*      */   {
/*  980 */     if (this.percentFormat == null) {
/*  981 */       initPercentFormat();
/*      */     }
/*      */ 
/*  984 */     return this.percentFormat;
/*      */   }
/*      */ 
/*      */   public boolean getPercentLabelsOn()
/*      */   {
/*  993 */     return this.percentLabelsOn;
/*      */   }
/*      */ 
/*      */   public int getStartDegrees()
/*      */   {
/* 1002 */     return this.startDegrees;
/*      */   }
/*      */ 
/*      */   public boolean getTextLabelsOn()
/*      */   {
/* 1013 */     return this.textLabelsOn;
/*      */   }
/*      */ 
/*      */   public boolean getValueLabelsOn()
/*      */   {
/* 1022 */     return this.valueLabelsOn;
/*      */   }
/*      */ 
/*      */   public double getWidth()
/*      */   {
/* 1031 */     return this.width;
/*      */   }
/*      */ 
/*      */   public double getXLoc()
/*      */   {
/* 1040 */     return this.xLoc;
/*      */   }
/*      */ 
/*      */   public double getYLoc()
/*      */   {
/* 1049 */     return this.yLoc;
/*      */   }
/*      */ 
/*      */   private void insertIntoSideInfo(int angle, Datum d, Point p1, float p2x, float p2y, float p3x, float p3y, float p4x, float p4y)
/*      */   {
/* 1063 */     while (angle > 360) {
/* 1064 */       angle -= 360;
/*      */     }
/*      */ 
/* 1067 */     int i = 0;
/* 1068 */     double sinVal = Math.sin(angle / 180.0D * 3.141592653589793D);
/*      */ 
/* 1070 */     while ((i < this.sideOrder.size()) && 
/* 1071 */       (sinVal < ((Double)this.sideOrder.get(i)).doubleValue())) {
/* 1072 */       i++;
/*      */     }
/*      */ 
/* 1075 */     this.sideOrder.add(i, new Double(sinVal));
/*      */ 
/* 1077 */     int insertionPoint = i * 3;
/* 1078 */     this.sideInfo.add(insertionPoint++, d);
/*      */ 
/* 1080 */     float[] xVals = new float[4];
/* 1081 */     float[] yVals = new float[4];
/* 1082 */     xVals[0] = p1.x;
/* 1083 */     yVals[0] = p1.y;
/* 1084 */     xVals[1] = p2x;
/* 1085 */     yVals[1] = p2y;
/* 1086 */     xVals[2] = p3x;
/* 1087 */     yVals[2] = p3y;
/* 1088 */     xVals[3] = p4x;
/* 1089 */     yVals[3] = p4y;
/* 1090 */     this.sideInfo.add(insertionPoint++, xVals);
/* 1091 */     this.sideInfo.add(insertionPoint, yVals);
/*      */   }
/*      */ 
/*      */   protected String pctStr(double d)
/*      */   {
/* 1099 */     if (this.percentFormat == null) {
/* 1100 */       initPercentInstance();
/*      */     }
/*      */ 
/* 1103 */     return this.percentFormat.format(d);
/*      */   }
/*      */ 
/*      */   public synchronized void setExplosion(int whichSlice, double exp)
/*      */   {
/*      */     try
/*      */     {
/* 1116 */       if (this.dataset == null) {
/* 1117 */         this.dataset = this.datasets[0];
/*      */       }
/*      */ 
/* 1120 */       this.dataset.getDataElementAt(whichSlice).y2 = exp;
/*      */     }
/*      */     catch (NullPointerException localNullPointerException)
/*      */     {
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setFormat(Format format)
/*      */   {
/* 1137 */     super.setFormat(format);
/*      */ 
/* 1139 */     if (format == null) {
/* 1140 */       this.numberFormat = NumberFormat.getInstance();
/*      */     }
/* 1142 */     else if (((format instanceof NumberFormat)) && (this.percentFormat == null)) {
/* 1143 */       ((NumberFormat)format); this.percentFormat = NumberFormat.getPercentInstance();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setHeight(double h)
/*      */   {
/* 1154 */     if (h < 1.0D)
/* 1155 */       this.height = h;
/*      */   }
/*      */ 
/*      */   public void setLabelColor(Color c)
/*      */   {
/* 1165 */     this.labelColor = c;
/*      */   }
/*      */ 
/*      */   public void setLabelFont(Font f)
/*      */   {
/* 1174 */     this.labelFont = f;
/*      */   }
/*      */ 
/*      */   public void setLabelPosition(int pos)
/*      */   {
/* 1183 */     if ((this.labelPosition < 0) || (this.labelPosition > 2))
/* 1184 */       this.labelPosition = 1;
/*      */     else
/* 1186 */       this.labelPosition = pos;
/*      */   }
/*      */ 
/*      */   public void setLineColor(Color c)
/*      */   {
/* 1195 */     this.lineGc.setLineColor(c);
/*      */   }
/*      */ 
/*      */   public void setPercentFormat(Format format)
/*      */   {
/* 1204 */     if (format == null)
/* 1205 */       this.percentFormat = NumberFormat.getPercentInstance();
/*      */   }
/*      */ 
/*      */   public void setPercentLabelsOn(boolean onOff)
/*      */   {
/* 1215 */     this.percentLabelsOn = onOff;
/*      */   }
/*      */ 
/*      */   public void setStartDegrees(int deg)
/*      */   {
/* 1224 */     if ((this.startDegrees >= 0) || (this.startDegrees <= 360)) {
/* 1225 */       this.startDegrees = deg;
/*      */     }
/*      */ 
/* 1228 */     while (this.startDegrees < 0) {
/* 1229 */       this.startDegrees += 360;
/*      */     }
/* 1231 */     while (this.startDegrees > 360)
/* 1232 */       this.startDegrees -= 360;
/*      */   }
/*      */ 
/*      */   public void setTextLabelsOn(boolean onOff)
/*      */   {
/* 1242 */     this.textLabelsOn = onOff;
/*      */   }
/*      */ 
/*      */   public void setValueLabelsOn(boolean onOff)
/*      */   {
/* 1251 */     this.valueLabelsOn = onOff;
/*      */   }
/*      */ 
/*      */   public void setWidth(double w)
/*      */   {
/* 1260 */     if (w < 1.0D)
/* 1261 */       this.width = w;
/*      */   }
/*      */ 
/*      */   public void setXLoc(double loc)
/*      */   {
/* 1271 */     if ((loc < 0.0D) || (loc > 1.0D))
/* 1272 */       this.xLoc = 0.5D;
/*      */     else
/* 1274 */       this.xLoc = loc;
/*      */   }
/*      */ 
/*      */   public void setYLoc(double loc)
/*      */   {
/* 1284 */     if ((loc < 0.0D) || (loc > 1.0D))
/* 1285 */       this.yLoc = 0.5D;
/*      */     else
/* 1287 */       this.yLoc = loc;
/*      */   }
/*      */ 
/*      */   protected void initPercentFormat()
/*      */   {
/*      */     try
/*      */     {
/* 1297 */       this.percentFormat = NumberFormat.getPercentInstance(this.globals.locale);
/*      */     } catch (MissingResourceException e) {
/* 1299 */       System.out.println("locale unavailable, using default locale instead");
/* 1300 */       this.numberFormat = NumberFormat.getPercentInstance();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void initPercentInstance()
/*      */   {
/*      */     try
/*      */     {
/* 1310 */       this.percentFormat = NumberFormat.getPercentInstance(this.globals.locale);
/*      */     } catch (MissingResourceException e) {
/* 1312 */       System.out.println("locale unavailable, using default locale instead");
/* 1313 */       this.numberFormat = NumberFormat.getPercentInstance();
/*      */     }
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Pie
 * JD-Core Version:    0.6.2
 */