/*      */ package jatools.component.chart.chart;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Point;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.Format;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Locale;
/*      */ import java.util.MissingResourceException;
/*      */ 
/*      */ public class Axis
/*      */   implements AxisInterface, Serializable
/*      */ {
/*      */   public static final double AUTO_SCALE = (-1.0D / 0.0D);
/*      */   public static final int BOTTOM = 0;
/*      */   public static final int LEFT = 1;
/*      */   public static final int RIGHT = 3;
/*      */   public static final int TOP = 2;
/*      */   protected static final int axisLabels = 1;
/*      */   protected static final int gridLines = 2;
/*      */   protected static final int minTicks = 3;
/*      */   protected static final int majTicks = 4;
/*   66 */   protected static final double errorThreshold = Math.pow(2.0D, -25.0D);
/*      */   static final double LOG_10_E = 0.4342942D;
/*   68 */   static double[] twoFiveSteps = { 0.0D, 0.903D, 2.097D };
/*   69 */   static double[] oneTwoSteps = { 
/*   70 */     0.0D, 3.01D, 4.771D, 6.02D, 6.989D, 7.781000000000001D, 
/*   71 */     8.451000000000001D, 9.031000000000001D, 9.542D, 10.0D };
/*      */ 
/*   75 */   boolean autoScale = true;
/*   76 */   protected double axisStart = 0.0D;
/*   77 */   protected double axisEnd = 10.0D;
/*   78 */   boolean majTickVis = true;
/*   79 */   boolean minTickVis = false;
/*   80 */   boolean gridVis = false;
/*   81 */   boolean labelVis = true;
/*   82 */   boolean lineVis = true;
/*   83 */   boolean titleRotation = false;
/*   84 */   String titleString = null;
/*      */   Color titleColor;
/*   86 */   Font titleFont = Gc.defaultFont;
/*   87 */   boolean useDisplayList = true;
/*      */   Color labelColor;
/*   89 */   Font labelFont = Gc.defaultFont;
/*   90 */   int labelPrecision = 2;
/*   91 */   int labelAngle = 0;
/*   92 */   int labelFormat = 0;
/*      */   Gc lineGc;
/*      */   Gc gridGc;
/*      */   Gc tickGc;
/*   96 */   protected int numMajTicks = 5;
/*   97 */   protected int numGrids = 5;
/*   98 */   protected int numMinTicks = 10;
/*   99 */   protected int numLabels = 5;
/*  100 */   int majTickLength = 5;
/*  101 */   int minTickLength = 2;
/*  102 */   int side = 1;
/*  103 */   boolean logScaling = false;
/*  104 */   boolean sciLogScaling = false;
/*  105 */   boolean internalSciLogScaling = false;
/*  106 */   Format userFormat = null;
/*      */   NumberFormat numberFormat;
/*      */   protected Globals globals;
/*  115 */   boolean isXAxis = false;
/*  116 */   boolean barScaling = false;
/*      */ 
/*  121 */   protected int numAxLabels = 0;
/*      */   protected Dataset[] datasets;
/*  133 */   protected double stepSize = 2.0D;
/*      */   protected Plotarea plotarea;
/*      */   protected Point startPoint;
/*      */   protected Point endPoint;
/*      */   protected float increment;
/*  146 */   protected Double userAxisStart = null;
/*      */ 
/*  151 */   protected Double userAxisEnd = null;
/*      */ 
/*  156 */   protected ArrayList thresholdLines = null;
/*  157 */   protected Double userAxisStepSize = null;
/*      */ 
/*  163 */   protected Transform transformer = null;
/*  164 */   private Datum logDatum = null;
/*  165 */   double labelIncrement = 0.0D;
/*  166 */   double minTickIncrement = 0.0D;
/*  167 */   boolean isZeroStart = false;
/*  168 */   protected transient double logAxisStart = 1.0D;
/*  169 */   protected transient double logAxisEnd = 2.0D;
/*  170 */   final double NODIV = 5.0D;
/*      */   private int axMagnitude;
/*  172 */   protected int titleGap = 10;
/*      */   String[] labelValues;
/*  178 */   protected int sciLogMaxLabelCount = 22;
/*  179 */   protected boolean sciLogUseMinorGrids = true;
/*  180 */   protected boolean sciLogUseMajorGrids = true;
/*  181 */   protected Gc sciLogMinorGridGc = null;
/*  182 */   private boolean doingMinorGrids = true;
/*  183 */   int labelMultiplier = 1;
/*  184 */   private Gc sciLogTempGc = null;
/*      */   private int sciLogSaveNumGrids;
/*  186 */   protected String pattern = null;
/*      */ 
/*      */   public Axis()
/*      */   {
/*  192 */     this(null, false, null);
/*      */   }
/*      */ 
/*      */   public Axis(Dataset[] dsets, boolean xAxis, Plotarea plt)
/*      */   {
/*  202 */     this.pattern = "0";
/*  203 */     this.isXAxis = xAxis;
/*      */ 
/*  205 */     if (this.isXAxis) {
/*  206 */       this.side = 0;
/*      */     }
/*      */ 
/*  210 */     this.plotarea = plt;
/*  211 */     this.datasets = dsets;
/*      */ 
/*  213 */     if (this.plotarea != null)
/*  214 */       this.globals = this.plotarea.globals;
/*      */     else {
/*  216 */       this.globals = null;
/*      */     }
/*      */ 
/*  219 */     this.titleColor = Color.black;
/*  220 */     this.labelColor = Color.black;
/*  221 */     this.lineGc = new Gc(this.globals);
/*  222 */     this.gridGc = new Gc(this.globals);
/*  223 */     this.tickGc = new Gc(this.globals);
/*      */   }
/*      */ 
/*      */   public synchronized void addLabels(String[] str)
/*      */   {
/*  231 */     this.labelValues = str;
/*      */   }
/*      */ 
/*      */   protected void buildDisplayList() {
/*  235 */     switch (this.side) {
/*      */     case 0:
/*  237 */       this.globals.displayList.addRectangle(this, 
/*  238 */         this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY), 
/*  239 */         this.plotarea.transform.point(this.plotarea.urX, this.plotarea.llY - 0.1D));
/*      */ 
/*  241 */       break;
/*      */     case 1:
/*  244 */       this.globals.displayList.addRectangle(this, 
/*  245 */         this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY), 
/*  246 */         this.plotarea.transform.point(this.plotarea.llX - 0.1D, this.plotarea.urY));
/*      */ 
/*  248 */       break;
/*      */     case 2:
/*  251 */       this.globals.displayList.addRectangle(this, 
/*  252 */         this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY), 
/*  253 */         this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY + 0.1D));
/*      */ 
/*  255 */       break;
/*      */     case 3:
/*  258 */       this.globals.displayList.addRectangle(this, 
/*  259 */         this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY), 
/*  260 */         this.plotarea.transform.point(this.plotarea.urX + 0.1D, this.plotarea.llY));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void calculateElementCount()
/*      */   {
/*  272 */     if (this.userAxisStepSize == null) {
/*  273 */       return;
/*      */     }
/*      */ 
/*  276 */     double axisStepSize = this.userAxisStepSize.doubleValue();
/*  277 */     int numDatasets = datasetsInUse();
/*      */ 
/*  282 */     this.axisEnd = 
/*  283 */       (this.axisStart + 
/*  283 */       Math.ceil((getMaxValsFromData(numDatasets) - this.axisStart) / axisStepSize) * axisStepSize);
/*  284 */     this.numLabels = ((int)Math.floor((this.axisEnd - this.axisStart) / axisStepSize));
/*  285 */     this.numGrids = this.numLabels;
/*  286 */     this.numMajTicks = this.numLabels;
/*      */   }
/*      */ 
/*      */   protected void checkLogAx()
/*      */   {
/*  305 */     if ((this.axisStart <= 0.0D) || (this.axisEnd <= 0.0D))
/*      */     {
/*  307 */       System.out.println("user settings invalid for logscaling, autoscaling instead");
/*  308 */       scale();
/*      */     }
/*      */     else {
/*      */       try {
/*  312 */         this.isZeroStart = false;
/*  313 */         this.labelIncrement = ((this.axisEnd - this.axisStart) / this.numLabels);
/*  314 */         this.minTickIncrement = ((this.axisEnd - this.axisStart) / this.numMinTicks);
/*      */ 
/*  317 */         this.numGrids = this.numLabels;
/*  318 */         this.numMajTicks = this.numLabels;
/*      */ 
/*  321 */         this.logAxisStart = Transform.log10(this.axisStart);
/*  322 */         this.logAxisEnd = Transform.log10(this.axisEnd);
/*      */ 
/*  324 */         initializeTransform();
/*      */ 
/*  326 */         if ((this.side == 0) || (this.side == 2))
/*  327 */           this.transformer.logXScaling = true;
/*      */         else
/*  329 */           this.transformer.logYScaling = true;
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected synchronized int datasetsInUse()
/*      */   {
/*  356 */     for (int i = 0; i < this.datasets.length; i++) {
/*  357 */       if (this.datasets[i] == null) {
/*  358 */         return i;
/*      */       }
/*      */     }
/*  361 */     return i;
/*      */   }
/*      */ 
/*      */   public synchronized void draw(Graphics g)
/*      */   {
/*  370 */     if (this.logScaling) {
/*  371 */       selectLogScalingType();
/*      */     }
/*      */ 
/*  374 */     if (this.autoScale)
/*  375 */       scale();
/*  376 */     else if (this.logScaling)
/*  377 */       checkLogAx();
/*  378 */     else if (this.userAxisStepSize != null) {
/*  379 */       calculateElementCount();
/*      */     }
/*      */ 
/*  382 */     if ((this.globals.threeD) && (
/*  383 */       (this.side == 3) || (this.side == 2))) {
/*  384 */       g = g.create();
/*  385 */       g.translate(this.globals.xOffset, -this.globals.yOffset);
/*      */     }
/*      */ 
/*  389 */     if ((this.logScaling) && (this.internalSciLogScaling)) {
/*  390 */       sciLogAxisDrawSetup(g);
/*      */     }
/*      */ 
/*  393 */     drawLine(g);
/*  394 */     drawTicks(g);
/*      */ 
/*  396 */     if (this.gridVis) {
/*  397 */       if (!this.globals.threeD)
/*  398 */         drawGrids(g);
/*      */       else {
/*  400 */         draw3Dgrids(g);
/*      */       }
/*      */     }
/*      */ 
/*  404 */     if (this.labelVis) {
/*  405 */       drawLabels(g);
/*      */     }
/*      */ 
/*  409 */     if (this.titleString != null) {
/*  410 */       drawTitle(g, this.titleGap);
/*      */     }
/*      */ 
/*  413 */     if ((this.useDisplayList) && (this.globals.useDisplayList)) {
/*  414 */       buildDisplayList();
/*      */     }
/*      */ 
/*  418 */     if ((this.logScaling) && (this.internalSciLogScaling)) {
/*  419 */       sciLogAxisDrawCleanup(g);
/*      */     }
/*      */ 
/*  422 */     this.transformer = null;
/*      */   }
/*      */ 
/*      */   protected void draw3Dgrids(Graphics g)
/*      */   {
/*  432 */     if ((this.side == 3) || (this.side == 2)) {
/*  433 */       g = g.create();
/*  434 */       g.translate(-this.globals.xOffset, this.globals.yOffset);
/*      */     }
/*      */ 
/*  437 */     this.startPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/*  438 */     this.endPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/*      */ 
/*  440 */     int start = 0;
/*      */ 
/*  442 */     if (this.plotarea.gc.outlineFills) {
/*  443 */       start = 1;
/*      */     }
/*      */ 
/*  446 */     switch (this.side) {
/*      */     case 0:
/*      */     case 2:
/*  449 */       this.increment = getIncrement(this.endPoint.x, this.startPoint.x, this.numGrids);
/*      */ 
/*  451 */       for (int i = start; i < this.numGrids; i++) {
/*  452 */         this.gridGc.drawLine(g, whereOnAxis(i, 2), this.startPoint.y, 
/*  453 */           whereOnAxis(i, 2) + this.globals.xOffset, this.startPoint.y + this.globals.yOffset);
/*      */       }
/*      */ 
/*  456 */       if (!this.plotarea.gc.outlineFills) {
/*  457 */         this.gridGc.drawLine(g, this.endPoint.x, this.startPoint.y, this.endPoint.x + this.globals.xOffset, 
/*  458 */           this.startPoint.y + this.globals.yOffset);
/*      */       }
/*      */ 
/*  461 */       for (i = start; i < this.numGrids; i++) {
/*  462 */         this.gridGc.drawLine(g, whereOnAxis(i, 2) + this.globals.xOffset, 
/*  463 */           this.startPoint.y + this.globals.yOffset, whereOnAxis(i, 2) + this.globals.xOffset, 
/*  464 */           this.endPoint.y + this.globals.yOffset);
/*      */       }
/*      */ 
/*  467 */       if (!this.plotarea.gc.outlineFills) {
/*  468 */         this.gridGc.drawLine(g, this.endPoint.x + this.globals.xOffset, this.startPoint.y + this.globals.yOffset, 
/*  469 */           this.endPoint.x + this.globals.xOffset, this.endPoint.y + this.globals.yOffset);
/*      */       }
/*      */ 
/*  472 */       break;
/*      */     case 1:
/*      */     case 3:
/*  476 */       this.increment = getIncrement(this.endPoint.y, this.startPoint.y, this.numGrids);
/*      */ 
/*  478 */       for (int i = start; i < this.numGrids; i++) {
/*  479 */         this.gridGc.drawLine(g, this.startPoint.x, whereOnAxis(i, 2), 
/*  480 */           this.startPoint.x + this.globals.xOffset, whereOnAxis(i, 2) + this.globals.yOffset);
/*      */       }
/*      */ 
/*  483 */       if (!this.plotarea.gc.outlineFills) {
/*  484 */         this.gridGc.drawLine(g, this.startPoint.x, this.endPoint.y, this.startPoint.x + this.globals.xOffset, 
/*  485 */           this.endPoint.y + this.globals.yOffset);
/*      */       }
/*      */ 
/*  488 */       for (i = start; i < this.numGrids; i++) {
/*  489 */         this.gridGc.drawLine(g, this.startPoint.x + this.globals.xOffset, 
/*  490 */           whereOnAxis(i, 2) + this.globals.yOffset, this.endPoint.x + this.globals.xOffset, 
/*  491 */           whereOnAxis(i, 2) + this.globals.yOffset);
/*      */       }
/*      */ 
/*  494 */       if (!this.plotarea.gc.outlineFills) {
/*  495 */         this.gridGc.drawLine(g, this.startPoint.x + this.globals.xOffset, this.endPoint.y + this.globals.yOffset, 
/*  496 */           this.endPoint.x + this.globals.xOffset, this.endPoint.y + this.globals.yOffset);
/*      */       }
/*      */ 
/*  499 */       break;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void drawGrids(Graphics g)
/*      */   {
/*  513 */     int start = 0;
/*      */ 
/*  515 */     if (this.plotarea.gc.outlineFills) {
/*  516 */       start = 1;
/*      */     }
/*      */ 
/*  519 */     this.startPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/*  520 */     this.endPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/*      */ 
/*  522 */     switch (this.side) {
/*      */     case 0:
/*      */     case 2:
/*  525 */       this.increment = getIncrement(this.endPoint.x, this.startPoint.x, this.numGrids);
/*      */ 
/*  527 */       for (int i = start; i < this.numGrids; i++) {
/*  528 */         this.gridGc.drawLine(g, whereOnAxis(i, 2), this.startPoint.y, 
/*  529 */           whereOnAxis(i, 2), this.endPoint.y);
/*      */       }
/*      */ 
/*  532 */       if (!this.plotarea.gc.outlineFills) {
/*  533 */         this.gridGc.drawLine(g, this.endPoint.x, this.startPoint.y, this.endPoint.x, this.endPoint.y);
/*      */       }
/*      */ 
/*  536 */       break;
/*      */     case 1:
/*      */     case 3:
/*  540 */       this.increment = getIncrement(this.endPoint.y, this.startPoint.y, this.numGrids);
/*      */ 
/*  542 */       for (int i = start; i < this.numGrids; i++) {
/*  543 */         this.gridGc.drawLine(g, this.startPoint.x, whereOnAxis(i, 2), this.endPoint.x, 
/*  544 */           whereOnAxis(i, 2));
/*      */       }
/*      */ 
/*  547 */       if (!this.plotarea.gc.outlineFills) {
/*  548 */         this.gridGc.drawLine(g, this.startPoint.x, this.endPoint.y, this.endPoint.x, this.endPoint.y);
/*      */       }
/*      */ 
/*  551 */       break;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void drawLabels(Graphics g)
/*      */   {
/*  569 */     g.setFont(this.labelFont);
/*  570 */     FontMetrics fm = g.getFontMetrics();
/*  571 */     g.setColor(this.labelColor);
/*      */ 
/*  573 */     int longestLabel = 0;
/*      */ 
/*  575 */     switch (this.side) {
/*      */     case 0:
/*  577 */       this.startPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/*  578 */       int strBase = this.startPoint.y - this.majTickLength - 2;
/*  579 */       this.endPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.llY);
/*  580 */       this.increment = getIncrement(this.endPoint.x, this.startPoint.x, this.numLabels);
/*  581 */       double labelIncrement = (this.axisEnd - this.axisStart) / this.numLabels;
/*      */ 
/*  583 */       for (int i = 0; i < this.numLabels; i++) {
/*  584 */         String lab = getLabel(this.axisStart + labelIncrement * i, i);
/*  585 */         this.lineGc.drawSmartString(g, whereOnAxis(i, 1), strBase, this.side, this.labelAngle, 
/*  586 */           fm, lab);
/*      */ 
/*  590 */         if (this.labelAngle != 0) {
/*  591 */           int wid = Gc.getStringWidth(fm, lab);
/*      */ 
/*  593 */           if (wid > longestLabel)
/*  594 */             longestLabel = wid;
/*      */         }
/*      */         else {
/*  597 */           int wid = Gc.getStringHeight(fm, lab);
/*      */ 
/*  599 */           if (wid > longestLabel) {
/*  600 */             longestLabel = wid;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  605 */       if ((!this.barScaling) || (!this.isXAxis))
/*      */       {
/*  609 */         String lab = getLabel(this.axisEnd, i);
/*  610 */         this.lineGc.drawSmartString(g, whereOnAxis(i, 1), strBase, this.side, this.labelAngle, fm, lab);
/*      */       }
/*  612 */       break;
/*      */     case 1:
/*  615 */       this.startPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/*  616 */       this.endPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY);
/*  617 */       this.increment = getIncrement(this.endPoint.y, this.startPoint.y, this.numLabels);
/*  618 */       double labelIncrement = (this.axisEnd - this.axisStart) / this.numLabels;
/*      */ 
/*  620 */       for (int i = 0; i < this.numLabels; i++) {
/*  621 */         String lab = getLabel(this.axisStart + labelIncrement * i, i);
/*      */         int wid;
/*      */         int wid;
/*  625 */         if (this.labelAngle != 90)
/*  626 */           wid = Gc.getStringWidth(fm, lab);
/*      */         else {
/*  628 */           wid = Gc.getStringHeight(fm, lab);
/*      */         }
/*      */ 
/*  631 */         if (wid > longestLabel) {
/*  632 */           longestLabel = wid;
/*      */         }
/*      */ 
/*  635 */         int strBase = this.startPoint.x - this.majTickLength - 2;
/*  636 */         this.lineGc.drawSmartString(g, strBase, whereOnAxis(i, 1), this.side, this.labelAngle, 
/*  637 */           fm, lab);
/*      */       }
/*      */ 
/*  640 */       if ((!this.barScaling) || (!this.isXAxis))
/*      */       {
/*  644 */         String lab = getLabel(this.axisEnd, i);
/*  645 */         int strBase = this.startPoint.x - this.majTickLength - 2;
/*  646 */         this.lineGc.drawSmartString(g, strBase, whereOnAxis(i, 1), this.side, this.labelAngle, fm, lab);
/*      */       }
/*  648 */       break;
/*      */     case 2:
/*  651 */       this.startPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY);
/*  652 */       int strBase = this.startPoint.y + this.majTickLength + 2 + fm.getMaxDescent();
/*  653 */       this.endPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/*  654 */       this.increment = getIncrement(this.endPoint.x, this.startPoint.x, this.numLabels);
/*  655 */       double labelIncrement = (this.axisEnd - this.axisStart) / this.numLabels;
/*      */ 
/*  657 */       for (int i = 0; i < this.numLabels; i++) {
/*  658 */         String lab = getLabel(this.axisStart + labelIncrement * i, i);
/*  659 */         this.lineGc.drawSmartString(g, whereOnAxis(i, 1), strBase, this.side, this.labelAngle, 
/*  660 */           fm, lab);
/*      */       }
/*      */ 
/*  663 */       if ((!this.barScaling) || (!this.isXAxis))
/*      */       {
/*  667 */         String lab = getLabel(this.axisEnd, i);
/*  668 */         this.lineGc.drawSmartString(g, whereOnAxis(i, 1), strBase, this.side, this.labelAngle, fm, lab);
/*      */ 
/*  670 */         if (this.labelAngle != 0) {
/*  671 */           int wid = Gc.getStringWidth(fm, lab);
/*      */ 
/*  673 */           if (wid > longestLabel) {
/*  674 */             longestLabel = wid;
/*      */           }
/*      */         }
/*      */       }
/*  678 */       break;
/*      */     case 3:
/*  681 */       this.startPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.llY);
/*  682 */       this.endPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/*  683 */       this.increment = getIncrement(this.endPoint.y, this.startPoint.y, this.numLabels);
/*  684 */       double labelIncrement = (this.axisEnd - this.axisStart) / this.numLabels;
/*  685 */       int strBase = this.startPoint.x + this.majTickLength + 2;
/*      */ 
/*  687 */       for (int i = 0; i < this.numLabels; i++) {
/*  688 */         String lab = getLabel(this.axisStart + labelIncrement * i, i);
/*      */         int wid;
/*      */         int wid;
/*  692 */         if (this.labelAngle != 90)
/*  693 */           wid = Gc.getStringWidth(fm, lab);
/*      */         else {
/*  695 */           wid = Gc.getStringHeight(fm, lab);
/*      */         }
/*      */ 
/*  698 */         if (wid > longestLabel) {
/*  699 */           longestLabel = wid;
/*      */         }
/*      */ 
/*  702 */         this.lineGc.drawSmartString(g, strBase, whereOnAxis(i, 1), this.side, this.labelAngle, 
/*  703 */           fm, lab);
/*      */       }
/*      */ 
/*  706 */       if ((!this.barScaling) || (!this.isXAxis))
/*      */       {
/*  710 */         String lab = getLabel(this.axisEnd, i);
/*  711 */         strBase = this.startPoint.x + this.majTickLength + 2;
/*  712 */         this.lineGc.drawSmartString(g, strBase, whereOnAxis(i, 1), this.side, this.labelAngle, fm, lab);
/*      */       }
/*  714 */       break;
/*      */     }
/*      */ 
/*  722 */     if (longestLabel == 0) {
/*  723 */       longestLabel = fm.getMaxAscent();
/*      */     }
/*      */ 
/*  726 */     this.titleGap = longestLabel;
/*      */   }
/*      */ 
/*      */   protected void drawLine(Graphics g)
/*      */   {
/*  739 */     if (!this.lineVis) {
/*  740 */       return;
/*      */     }
/*      */ 
/*  743 */     switch (this.side) {
/*      */     case 0:
/*  745 */       this.lineGc.drawLine(g, this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY), 
/*  746 */         this.plotarea.transform.point(this.plotarea.urX, this.plotarea.llY));
/*      */ 
/*  748 */       break;
/*      */     case 1:
/*  751 */       this.lineGc.drawLine(g, this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY), 
/*  752 */         this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY));
/*      */ 
/*  754 */       break;
/*      */     case 2:
/*  757 */       this.lineGc.drawLine(g, this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY), 
/*  758 */         this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY));
/*      */ 
/*  760 */       break;
/*      */     case 3:
/*  763 */       this.lineGc.drawLine(g, this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY), 
/*  764 */         this.plotarea.transform.point(this.plotarea.urX, this.plotarea.llY));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void drawTicks(Graphics g)
/*      */   {
/*  774 */     if (this.majTickVis) {
/*  775 */       switch (this.side) {
/*      */       case 0:
/*  777 */         this.startPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/*  778 */         int tickBase = this.startPoint.y;
/*  779 */         this.endPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.llY);
/*  780 */         this.increment = getIncrement(this.endPoint.x, this.startPoint.x, this.numMajTicks);
/*      */ 
/*  782 */         for (int i = 0; i < this.numMajTicks; i++) {
/*  783 */           this.tickGc.drawLine(g, whereOnAxis(i, 4), tickBase, 
/*  784 */             whereOnAxis(i, 4), tickBase - this.majTickLength);
/*      */         }
/*      */ 
/*  787 */         this.tickGc.drawLine(g, this.endPoint.x, tickBase, this.endPoint.x, tickBase - this.majTickLength);
/*      */ 
/*  789 */         break;
/*      */       case 1:
/*  792 */         this.startPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/*  793 */         int tickBase = this.startPoint.x;
/*  794 */         this.endPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY);
/*  795 */         this.increment = getIncrement(this.endPoint.y, this.startPoint.y, this.numMajTicks);
/*      */ 
/*  797 */         for (int i = 0; i < this.numMajTicks; i++) {
/*  798 */           this.tickGc.drawLine(g, tickBase, whereOnAxis(i, 4), 
/*  799 */             tickBase - this.majTickLength, whereOnAxis(i, 4));
/*      */         }
/*      */ 
/*  802 */         this.tickGc.drawLine(g, tickBase, this.endPoint.y, tickBase - this.majTickLength, this.endPoint.y);
/*      */ 
/*  804 */         break;
/*      */       case 2:
/*  807 */         this.startPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY);
/*  808 */         int tickBase = this.startPoint.y;
/*  809 */         this.endPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/*  810 */         this.increment = getIncrement(this.endPoint.x, this.startPoint.x, this.numMajTicks);
/*      */ 
/*  812 */         for (int i = 0; i <= this.numMajTicks; i++) {
/*  813 */           this.tickGc.drawLine(g, whereOnAxis(i, 4), tickBase, 
/*  814 */             whereOnAxis(i, 4), tickBase + this.majTickLength);
/*      */         }
/*      */ 
/*  817 */         this.tickGc.drawLine(g, this.endPoint.x, tickBase, this.endPoint.x, tickBase + this.majTickLength);
/*      */ 
/*  819 */         break;
/*      */       case 3:
/*  822 */         this.startPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.llY);
/*  823 */         int tickBase = this.startPoint.x;
/*  824 */         this.endPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/*  825 */         this.increment = getIncrement(this.endPoint.y, this.startPoint.y, this.numMajTicks);
/*      */ 
/*  827 */         for (int i = 0; i <= this.numMajTicks; i++) {
/*  828 */           this.tickGc.drawLine(g, tickBase, whereOnAxis(i, 4), 
/*  829 */             tickBase + this.majTickLength, whereOnAxis(i, 4));
/*      */         }
/*      */ 
/*  832 */         this.tickGc.drawLine(g, tickBase, this.endPoint.y, tickBase + this.majTickLength, this.endPoint.y);
/*      */ 
/*  834 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  841 */     if (this.minTickVis)
/*  842 */       switch (this.side) {
/*      */       case 0:
/*  844 */         this.startPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/*  845 */         int tickBase = this.startPoint.y;
/*  846 */         this.endPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.llY);
/*  847 */         this.increment = getIncrement(this.endPoint.x, this.startPoint.x, this.numMinTicks);
/*      */ 
/*  849 */         for (int i = 0; i < this.numMinTicks; i++) {
/*  850 */           this.tickGc.drawLine(g, whereOnAxis(i, 3), tickBase, 
/*  851 */             whereOnAxis(i, 3), tickBase - this.minTickLength);
/*      */         }
/*      */ 
/*  854 */         this.tickGc.drawLine(g, this.endPoint.x, tickBase, this.endPoint.x, tickBase - this.minTickLength);
/*      */ 
/*  856 */         break;
/*      */       case 1:
/*  859 */         this.startPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/*  860 */         int tickBase = this.startPoint.x;
/*  861 */         this.endPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY);
/*  862 */         this.increment = getIncrement(this.endPoint.y, this.startPoint.y, this.numMinTicks);
/*      */ 
/*  864 */         for (int i = 0; i < this.numMinTicks; i++) {
/*  865 */           this.tickGc.drawLine(g, tickBase, whereOnAxis(i, 3), 
/*  866 */             tickBase - this.minTickLength, whereOnAxis(i, 3));
/*      */         }
/*      */ 
/*  869 */         this.tickGc.drawLine(g, tickBase, this.endPoint.y, tickBase - this.minTickLength, this.endPoint.y);
/*      */ 
/*  871 */         break;
/*      */       case 2:
/*  874 */         this.startPoint = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY);
/*  875 */         int tickBase = this.startPoint.y;
/*  876 */         this.endPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/*  877 */         this.increment = getIncrement(this.endPoint.x, this.startPoint.x, this.numMinTicks);
/*      */ 
/*  879 */         for (int i = 0; i < this.numMinTicks; i++) {
/*  880 */           this.tickGc.drawLine(g, whereOnAxis(i, 3), tickBase, 
/*  881 */             whereOnAxis(i, 3), tickBase + this.minTickLength);
/*      */         }
/*      */ 
/*  884 */         break;
/*      */       case 3:
/*  887 */         this.startPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.llY);
/*  888 */         int tickBase = this.startPoint.x;
/*  889 */         this.endPoint = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/*  890 */         this.increment = getIncrement(this.endPoint.y, this.startPoint.y, this.numMinTicks);
/*      */ 
/*  892 */         for (int i = 0; i < this.numMinTicks; i++) {
/*  893 */           this.tickGc.drawLine(g, tickBase, whereOnAxis(i, 3), 
/*  894 */             tickBase + this.minTickLength, whereOnAxis(i, 3));
/*      */         }
/*      */ 
/*  897 */         this.tickGc.drawLine(g, tickBase, this.endPoint.y, tickBase + this.minTickLength, this.endPoint.y);
/*      */ 
/*  899 */         break;
/*      */       }
/*      */   }
/*      */ 
/*      */   protected void drawTitle(Graphics g, int labelFudge)
/*      */   {
/*  917 */     g.setFont(this.titleFont);
/*  918 */     FontMetrics fm = g.getFontMetrics();
/*  919 */     g.setColor(this.titleColor);
/*      */ 
/*  921 */     switch (this.side) {
/*      */     case 0:
/*      */     case 2:
/*  924 */       if (this.labelAngle != 0) {
/*  925 */         labelFudge = (int)(labelFudge * Math.sin(this.labelAngle / 180.0D * 3.141592653589793D));
/*      */ 
/*  927 */         if (this.labelAngle != 90) {
/*  928 */           labelFudge += 4;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  933 */       break;
/*      */     case 1:
/*      */     case 3:
/*  937 */       if ((this.labelAngle != 0) && (this.labelAngle != 90)) {
/*  938 */         labelFudge = (int)(labelFudge * Math.cos(this.labelAngle / 180.0D * 3.141592653589793D));
/*      */ 
/*  940 */         if (this.labelAngle != 90) {
/*  941 */           labelFudge += 4;
/*      */         }
/*      */       }
/*      */       break;
/*      */     }
/*      */ 
/*  947 */     labelFudge = Math.abs(labelFudge) + this.majTickLength + 8;
/*      */ 
/*  949 */     switch (this.side) {
/*      */     case 0:
/*  951 */       Point startAxis = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/*  952 */       Point endAxis = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.llY);
/*  953 */       this.lineGc.drawString(g, 
/*  954 */         startAxis.x + (endAxis.x - startAxis.x) / 2 - 
/*  955 */         fm.stringWidth(this.titleString) / 2, startAxis.y - labelFudge - fm.getHeight(), 
/*  956 */         this.titleString);
/*      */ 
/*  958 */       break;
/*      */     case 1:
/*  961 */       Point startAxis = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/*  962 */       Point endAxis = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY);
/*      */ 
/*  964 */       if (!this.titleRotation)
/*  965 */         this.lineGc.drawString(g, startAxis.x - fm.stringWidth(this.titleString) / 2, 
/*  966 */           endAxis.y + 4, this.titleString);
/*      */       else {
/*  968 */         this.lineGc.drawSmartString(g, startAxis.x - labelFudge, 
/*  969 */           startAxis.y + (endAxis.y - startAxis.y) / 2, this.side, 90, fm, this.titleString);
/*      */       }
/*      */ 
/*  972 */       break;
/*      */     case 2:
/*  975 */       Point startAxis = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.urY);
/*  976 */       Point endAxis = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/*  977 */       this.lineGc.drawString(g, 
/*  978 */         startAxis.x + (endAxis.x - startAxis.x) / 2 - 
/*  979 */         fm.stringWidth(this.titleString) / 2, startAxis.y + labelFudge + 3, this.titleString);
/*      */ 
/*  981 */       break;
/*      */     case 3:
/*  984 */       Point startAxis = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.llY);
/*  985 */       Point endAxis = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/*      */ 
/*  987 */       if (!this.titleRotation)
/*  988 */         this.lineGc.drawString(g, startAxis.x - fm.stringWidth(this.titleString) / 2, 
/*  989 */           endAxis.y + 4, this.titleString);
/*      */       else {
/*  991 */         this.lineGc.drawSmartString(g, startAxis.x + labelFudge, 
/*  992 */           startAxis.y + (endAxis.y - startAxis.y) / 2, this.side, 90, fm, this.titleString);
/*      */       }
/*      */ 
/*  995 */       break;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected String fmtLabel(double inVal)
/*      */   {
/* 1015 */     if (this.pattern == null) {
/* 1016 */       this.pattern = "0";
/*      */     }
/*      */ 
/* 1019 */     DecimalFormat format = new DecimalFormat();
/* 1020 */     format.applyPattern(this.pattern);
/*      */ 
/* 1022 */     return format.format(inVal);
/*      */   }
/*      */ 
/*      */   public boolean getAutoScale()
/*      */   {
/* 1033 */     return this.autoScale;
/*      */   }
/*      */ 
/*      */   public double getAxisEnd()
/*      */   {
/* 1042 */     return this.axisEnd;
/*      */   }
/*      */ 
/*      */   public double getAxisStart()
/*      */   {
/* 1051 */     return this.axisStart;
/*      */   }
/*      */ 
/*      */   public double getAxisStepSize()
/*      */   {
/* 1060 */     if (this.userAxisStepSize == null) {
/* 1061 */       return -1.0D;
/*      */     }
/* 1063 */     return this.userAxisStepSize.doubleValue();
/*      */   }
/*      */ 
/*      */   public boolean getBarScaling()
/*      */   {
/* 1073 */     return this.barScaling;
/*      */   }
/*      */ 
/*      */   public Dataset[] getDatasets()
/*      */   {
/* 1083 */     return this.datasets;
/*      */   }
/*      */ 
/*      */   public Globals getGlobals()
/*      */   {
/* 1092 */     return this.globals;
/*      */   }
/*      */ 
/*      */   public Gc getGridGc()
/*      */   {
/* 1101 */     return this.gridGc;
/*      */   }
/*      */ 
/*      */   public boolean getGridVis()
/*      */   {
/* 1110 */     return this.gridVis;
/*      */   }
/*      */ 
/*      */   protected float getIncrement(int end, int start, int num) {
/* 1114 */     return (end - start) / num;
/*      */   }
/*      */ 
/*      */   protected String getLabel(double inVal, int count)
/*      */   {
/* 1123 */     if ((this.labelValues != null) && (count < this.labelValues.length)) {
/* 1124 */       return this.labelValues[count];
/*      */     }
/* 1126 */     if (this.logScaling) {
/* 1127 */       if (this.internalSciLogScaling) {
/* 1128 */         return getSciLogLabel(inVal, count);
/*      */       }
/* 1130 */       return getLogLabel(inVal, count);
/*      */     }
/*      */ 
/* 1137 */     if ((Math.abs(inVal) < errorThreshold) && (this.stepSize > Math.pow(2.0D, -24.0D))) {
/* 1138 */       return fmtLabel(0.0D);
/*      */     }
/*      */ 
/* 1141 */     return fmtLabel(inVal);
/*      */   }
/*      */ 
/*      */   public int getLabelAngle()
/*      */   {
/* 1156 */     return this.labelAngle;
/*      */   }
/*      */ 
/*      */   public Color getLabelColor()
/*      */   {
/* 1165 */     return this.labelColor;
/*      */   }
/*      */ 
/*      */   public Font getLabelFont()
/*      */   {
/* 1174 */     return this.labelFont;
/*      */   }
/*      */ 
/*      */   public Format getLabelFormat()
/*      */   {
/* 1183 */     return this.userFormat;
/*      */   }
/*      */ 
/*      */   public int getLabelPrecision()
/*      */   {
/* 1192 */     return this.labelPrecision;
/*      */   }
/*      */ 
/*      */   public boolean getLabelVis()
/*      */   {
/* 1201 */     return this.labelVis;
/*      */   }
/*      */ 
/*      */   public Gc getLineGc()
/*      */   {
/* 1210 */     return this.lineGc;
/*      */   }
/*      */ 
/*      */   public boolean getLineVis()
/*      */   {
/* 1219 */     return this.lineVis;
/*      */   }
/*      */ 
/*      */   protected String getLogLabel(double dummy, int index)
/*      */   {
/*      */     double labelVal;
/*      */     double labelVal;
/* 1225 */     if (!this.isZeroStart) {
/* 1226 */       labelVal = this.axisStart + index * this.labelIncrement;
/*      */     }
/*      */     else
/*      */     {
/*      */       double labelVal;
/* 1228 */       if (index == 0)
/* 1229 */         labelVal = this.axisStart;
/*      */       else {
/* 1231 */         labelVal = 0.0D + index * this.labelIncrement;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1242 */     if (this.pattern == null) {
/* 1243 */       this.pattern = "0";
/*      */     }
/*      */ 
/* 1246 */     DecimalFormat format = new DecimalFormat();
/* 1247 */     format.applyPattern(this.pattern);
/*      */ 
/* 1249 */     return format.format(labelVal);
/*      */   }
/*      */ 
/*      */   public boolean getLogScaling()
/*      */   {
/* 1258 */     return this.logScaling;
/*      */   }
/*      */ 
/*      */   public int getMajTickLength()
/*      */   {
/* 1267 */     return this.majTickLength;
/*      */   }
/*      */ 
/*      */   public boolean getMajTickVis()
/*      */   {
/* 1276 */     return this.majTickVis;
/*      */   }
/*      */ 
/*      */   protected double getMaxValsFromData(int nmsets)
/*      */   {
/* 1284 */     double hi = (-1.0D / 0.0D);
/*      */ 
/* 1286 */     if (this.userAxisEnd != null) {
/* 1287 */       return this.userAxisEnd.doubleValue();
/*      */     }
/*      */ 
/* 1290 */     if (!this.isXAxis)
/* 1291 */       for (int i = 0; i < nmsets; i++)
/* 1292 */         hi = Math.max(hi, this.datasets[i].maxY());
/*      */     else {
/* 1294 */       for (int i = 0; i < nmsets; i++) {
/* 1295 */         hi = Math.max(hi, this.datasets[i].maxX());
/*      */       }
/*      */     }
/* 1298 */     return hi;
/*      */   }
/*      */ 
/*      */   public int getMinTickLength()
/*      */   {
/* 1307 */     return this.minTickLength;
/*      */   }
/*      */ 
/*      */   public boolean getMinTickVis()
/*      */   {
/* 1316 */     return this.minTickVis;
/*      */   }
/*      */ 
/*      */   protected double getMinValsFromData(int nmsets)
/*      */   {
/* 1324 */     double low = (1.0D / 0.0D);
/*      */ 
/* 1327 */     if (this.userAxisStart != null) {
/* 1328 */       return this.userAxisStart.doubleValue();
/*      */     }
/*      */ 
/* 1331 */     if (!this.isXAxis) {
/* 1332 */       for (int i = 0; i < nmsets; i++) {
/* 1333 */         double lastLow = low;
/*      */ 
/* 1335 */         low = Math.min(low, this.datasets[i].minY());
/*      */ 
/* 1337 */         if (low == (-1.0D / 0.0D))
/* 1338 */           low = lastLow;
/*      */       }
/*      */     }
/*      */     else {
/* 1342 */       for (int i = 0; i < nmsets; i++) {
/* 1343 */         double lastLow = low;
/* 1344 */         low = Math.min(low, this.datasets[i].minX());
/*      */ 
/* 1346 */         if (low == (-1.0D / 0.0D)) {
/* 1347 */           low = lastLow;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1352 */     return low;
/*      */   }
/*      */ 
/*      */   private synchronized double getNormalizedIncrement(double rough, double tenMag)
/*      */   {
/* 1357 */     if (inRange(rough, 0.0D, 0.1D)) {
/* 1358 */       return 0.1D * tenMag;
/*      */     }
/*      */ 
/* 1361 */     if (inRange(rough, 0.1D, 0.25D)) {
/* 1362 */       return 0.25D * tenMag;
/*      */     }
/*      */ 
/* 1365 */     if (inRange(rough, 0.25D, 0.5D)) {
/* 1366 */       return 0.5D * tenMag;
/*      */     }
/*      */ 
/* 1369 */     if (inRange(rough, 0.5D, 1.0D)) {
/* 1370 */       return tenMag;
/*      */     }
/*      */ 
/* 1373 */     if (inRange(rough, 1.0D, 9.0D)) {
/* 1374 */       return 2.0D * tenMag;
/*      */     }
/* 1376 */     return tenMag;
/*      */   }
/*      */ 
/*      */   public int getNumGrids()
/*      */   {
/* 1386 */     return this.numGrids;
/*      */   }
/*      */ 
/*      */   public int getNumLabels()
/*      */   {
/* 1395 */     return this.numLabels;
/*      */   }
/*      */ 
/*      */   public int getNumMajTicks()
/*      */   {
/* 1404 */     return this.numMajTicks;
/*      */   }
/*      */ 
/*      */   public int getNumMinTicks()
/*      */   {
/* 1413 */     return this.numMinTicks;
/*      */   }
/*      */ 
/*      */   public Plotarea getPlotarea()
/*      */   {
/* 1422 */     return this.plotarea;
/*      */   }
/*      */ 
/*      */   public int getSide()
/*      */   {
/* 1431 */     return this.side;
/*      */   }
/*      */ 
/*      */   public Gc getTickGc()
/*      */   {
/* 1440 */     return this.tickGc;
/*      */   }
/*      */ 
/*      */   public Color getTitleColor()
/*      */   {
/* 1449 */     return this.titleColor;
/*      */   }
/*      */ 
/*      */   public Font getTitleFont()
/*      */   {
/* 1458 */     return this.titleFont;
/*      */   }
/*      */ 
/*      */   public String getTitleString()
/*      */   {
/* 1467 */     return this.titleString;
/*      */   }
/*      */ 
/*      */   public boolean getUseDisplayList()
/*      */   {
/* 1477 */     return this.useDisplayList;
/*      */   }
/*      */ 
/*      */   protected void initNumberFormat()
/*      */   {
/*      */     try
/*      */     {
/* 1488 */       this.numberFormat = NumberFormat.getInstance(this.globals.locale);
/*      */     } catch (MissingResourceException e) {
/* 1490 */       System.out.println("locale unavailable, using default locale instead");
/* 1491 */       this.numberFormat = NumberFormat.getInstance();
/*      */     }
/*      */   }
/*      */ 
/*      */   private synchronized boolean inRange(double x, double lo, double hi) {
/* 1496 */     return (x <= hi) && (x >= lo);
/*      */   }
/*      */ 
/*      */   public boolean isTitleRotated()
/*      */   {
/* 1504 */     return this.titleRotation;
/*      */   }
/*      */ 
/*      */   public boolean isXAxis()
/*      */   {
/* 1513 */     return this.isXAxis;
/*      */   }
/*      */ 
/*      */   protected synchronized boolean linearScale()
/*      */   {
/* 1524 */     double mn = 8.999999999999999E+035D;
/* 1525 */     double mx = -8.999999999999999E+035D;
/*      */ 
/* 1527 */     boolean onepoint = true;
/*      */ 
/* 1530 */     int nmsets = datasetsInUse();
/*      */ 
/* 1532 */     if (nmsets == 0) {
/* 1533 */       return false;
/*      */     }
/*      */ 
/* 1536 */     double hi = getMaxValsFromData(nmsets);
/* 1537 */     double low = getMinValsFromData(nmsets);
/*      */ 
/* 1540 */     if ((nmsets > 1) || (this.datasets[0].data.size() > 1)) {
/* 1541 */       onepoint = false;
/*      */     }
/*      */ 
/* 1544 */     if ((this.barScaling) && (!this.logScaling))
/*      */     {
/* 1546 */       if (low > 0.0D) {
/* 1547 */         low = 0.0D;
/*      */       }
/*      */ 
/* 1550 */       if (hi < 0.0D)
/* 1551 */         hi = 0.0D;
/*      */     }
/*      */     double dif;
/*      */     double dif;
/* 1555 */     if (((onepoint) || (hi == low)) && (this.userAxisStart == null) && (this.userAxisEnd == null))
/*      */     {
/*      */       double dif;
/* 1556 */       if (low != 0.0D) {
/* 1557 */         dif = Math.abs(low * 2.0D);
/*      */       }
/*      */       else
/*      */       {
/*      */         double dif;
/* 1558 */         if (hi == 0.0D)
/* 1559 */           dif = 2.0D;
/*      */         else
/* 1561 */           dif = hi;
/*      */       }
/*      */     } else {
/* 1564 */       dif = hi - low;
/*      */     }
/*      */ 
/* 1567 */     double mag = Transform.log10(dif);
/*      */     double norm;
/*      */     double norm;
/* 1569 */     if (mag < 0.0D)
/* 1570 */       norm = Math.pow(10.0D, Math.ceil(mag));
/*      */     else {
/* 1572 */       norm = Math.pow(10.0D, Math.floor(mag));
/*      */     }
/*      */ 
/* 1575 */     double inc = dif / (5.0D * norm);
/*      */     double normalizedIncrement;
/*      */     double normalizedIncrement;
/* 1578 */     if (this.userAxisStepSize == null)
/* 1579 */       normalizedIncrement = getNormalizedIncrement(inc, norm);
/*      */     else {
/* 1581 */       normalizedIncrement = this.userAxisStepSize.doubleValue();
/*      */     }
/*      */ 
/* 1584 */     this.stepSize = normalizedIncrement;
/*      */ 
/* 1588 */     while (mn += normalizedIncrement <= low);
/* 1591 */     mn -= normalizedIncrement;
/*      */ 
/* 1596 */     while (mx += normalizedIncrement < hi);
/* 1599 */     this.axisStart = mn;
/* 1600 */     this.axisEnd = mx;
/*      */ 
/* 1602 */     this.numLabels = ((int)((mx - mn) / this.stepSize));
/*      */ 
/* 1604 */     this.numMajTicks = this.numLabels;
/* 1605 */     this.numGrids = this.numLabels;
/* 1606 */     this.numMinTicks = (2 * this.numMajTicks);
/*      */ 
/* 1608 */     return true;
/*      */   }
/*      */ 
/*      */   private void selectLogScalingType()
/*      */   {
/* 1613 */     this.internalSciLogScaling = this.sciLogScaling;
/*      */   }
/*      */ 
/*      */   protected synchronized boolean logScale() {
/* 1617 */     if (this.internalSciLogScaling) {
/* 1618 */       return sciLogScale();
/*      */     }
/*      */ 
/* 1621 */     int nmsets = datasetsInUse();
/* 1622 */     double hi = getMaxValsFromData(nmsets);
/* 1623 */     double low = getMinValsFromData(nmsets);
/*      */ 
/* 1625 */     if ((hi <= 0.0D) || (low <= 0.0D))
/*      */     {
/* 1627 */       return false;
/*      */     }
/*      */ 
/* 1630 */     linearScale();
/*      */     try
/*      */     {
/* 1634 */       this.labelIncrement = ((this.axisEnd - this.axisStart) / this.numLabels);
/* 1635 */       this.minTickIncrement = ((this.axisEnd - this.axisStart) / this.numMinTicks);
/*      */ 
/* 1638 */       if (this.axisStart <= 0.0D) {
/* 1639 */         this.logAxisStart = Math.floor(Math.log(low) / Math.log(10.0D));
/* 1640 */         this.axisStart = Math.pow(10.0D, this.logAxisStart);
/* 1641 */         this.isZeroStart = true;
/*      */       } else {
/* 1643 */         this.logAxisStart = Transform.log10(this.axisStart);
/* 1644 */         this.isZeroStart = false;
/*      */       }
/*      */ 
/* 1647 */       this.logAxisEnd = Transform.log10(this.axisEnd);
/*      */ 
/* 1649 */       if (this.logAxisEnd - this.logAxisStart > 2.0D) {
/* 1650 */         this.internalSciLogScaling = true;
/*      */ 
/* 1652 */         return sciLogScale();
/*      */       }
/*      */ 
/* 1655 */       initializeTransform();
/*      */ 
/* 1657 */       if ((this.side == 0) || (this.side == 2))
/* 1658 */         this.transformer.logXScaling = true;
/*      */       else
/* 1660 */         this.transformer.logYScaling = true;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1664 */       return false;
/*      */     }
/*      */ 
/* 1667 */     return true;
/*      */   }
/*      */ 
/*      */   public void scale()
/*      */   {
/* 1705 */     if (!this.logScaling) {
/* 1706 */       linearScale();
/* 1707 */     } else if (!logScale()) {
/* 1708 */       this.logScaling = false;
/* 1709 */       linearScale();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setAutoScale(boolean yesNo)
/*      */   {
/* 1719 */     if (!this.sciLogScaling)
/* 1720 */       this.autoScale = yesNo;
/*      */   }
/*      */ 
/*      */   public void setAxisEnd(double num)
/*      */   {
/* 1730 */     if (num == (-1.0D / 0.0D)) {
/* 1731 */       this.userAxisEnd = null;
/*      */     } else {
/* 1733 */       this.userAxisEnd = new Double(num);
/* 1734 */       this.axisEnd = num;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setAxisStart(double num)
/*      */   {
/* 1744 */     if (num == (-1.0D / 0.0D)) {
/* 1745 */       this.userAxisStart = null;
/*      */     } else {
/* 1747 */       this.axisStart = num;
/* 1748 */       this.userAxisStart = new Double(num);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setAxisStepSize(double step)
/*      */   {
/* 1760 */     if (step <= 0.0D) {
/* 1761 */       this.userAxisStepSize = null;
/*      */ 
/* 1763 */       return;
/*      */     }
/*      */ 
/* 1766 */     this.userAxisStepSize = new Double(step);
/*      */   }
/*      */ 
/*      */   public void setBarScaling(boolean yesNo)
/*      */   {
/* 1775 */     this.barScaling = yesNo;
/*      */   }
/*      */ 
/*      */   public void setDatasets(Dataset[] newDatasets)
/*      */   {
/* 1784 */     this.datasets = newDatasets;
/*      */ 
/* 1786 */     if (this.globals == null) {
/* 1787 */       return;
/*      */     }
/*      */ 
/* 1790 */     for (int i = 0; i < this.datasets.length; i++)
/* 1791 */       if ((this.datasets[i] != null) && 
/* 1792 */         (this.datasets[i].globals != this.globals))
/* 1793 */         this.datasets[i].setGlobals(this.globals);
/*      */   }
/*      */ 
/*      */   public void setGlobals(Globals g)
/*      */   {
/* 1805 */     if (g == null) {
/* 1806 */       return;
/*      */     }
/*      */ 
/* 1809 */     this.globals = g;
/* 1810 */     this.lineGc.globals = g;
/* 1811 */     this.tickGc.globals = g;
/* 1812 */     this.gridGc.globals = g;
/* 1813 */     this.plotarea.globals = g;
/*      */ 
/* 1815 */     if (this.datasets != null)
/* 1816 */       for (int i = 0; i < this.datasets.length; i++)
/* 1817 */         if ((this.datasets[i] != null) && 
/* 1818 */           (this.datasets[i].globals != g))
/* 1819 */           this.datasets[i].setGlobals(g);
/*      */   }
/*      */ 
/*      */   public void setGridGc(Gc g)
/*      */   {
/* 1832 */     this.gridGc = g;
/* 1833 */     g.globals = this.globals;
/*      */   }
/*      */ 
/*      */   public void setGridVis(boolean onOff)
/*      */   {
/* 1842 */     this.gridVis = onOff;
/*      */   }
/*      */ 
/*      */   public void setIsXAxis(boolean xAx)
/*      */   {
/* 1850 */     this.isXAxis = xAx;
/*      */   }
/*      */ 
/*      */   public void setLabelAngle(int num)
/*      */   {
/* 1859 */     this.labelAngle = num;
/*      */   }
/*      */ 
/*      */   public void setLabelColor(Color c)
/*      */   {
/* 1868 */     this.labelColor = c;
/*      */   }
/*      */ 
/*      */   public void setLabelFont(Font f)
/*      */   {
/* 1877 */     this.labelFont = f;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setLabelFormat(int i)
/*      */   {
/* 1888 */     if (i == 2) {
/* 1889 */       this.numberFormat = NumberFormat.getNumberInstance(Locale.FRANCE);
/*      */     }
/*      */ 
/* 1892 */     if (i == 1) {
/* 1893 */       this.numberFormat = NumberFormat.getNumberInstance(Locale.US);
/*      */     }
/*      */ 
/* 1896 */     if (i == 0)
/* 1897 */       this.numberFormat = NumberFormat.getNumberInstance();
/*      */   }
/*      */ 
/*      */   public void setLabelFormat(Format format)
/*      */   {
/* 1915 */     this.userFormat = format;
/*      */   }
/*      */ 
/*      */   public void setLabelPrecision(int num)
/*      */   {
/* 1924 */     this.labelPrecision = num;
/*      */ 
/* 1926 */     if (this.numberFormat == null) {
/* 1927 */       initNumberFormat();
/*      */     }
/*      */ 
/* 1930 */     if (num != -1) {
/* 1931 */       this.numberFormat.setMinimumFractionDigits(num);
/* 1932 */       this.numberFormat.setMaximumFractionDigits(num);
/*      */ 
/* 1934 */       if (this.userFormat != null)
/*      */         try {
/* 1936 */           ((NumberFormat)this.userFormat).setMinimumFractionDigits(num);
/* 1937 */           ((NumberFormat)this.userFormat).setMaximumFractionDigits(num);
/*      */         }
/*      */         catch (Exception localException) {
/*      */         }
/*      */     }
/*      */     else {
/* 1943 */       this.numberFormat = NumberFormat.getInstance();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setLabelVis(boolean onOff)
/*      */   {
/* 1953 */     this.labelVis = onOff;
/*      */   }
/*      */ 
/*      */   public void setLineGc(Gc g)
/*      */   {
/* 1962 */     this.lineGc = g;
/* 1963 */     this.lineGc.globals = this.globals;
/*      */   }
/*      */ 
/*      */   public void setLineVis(boolean onOff)
/*      */   {
/* 1972 */     this.lineVis = onOff;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setLogScaling(boolean yesNo, _Chart chart)
/*      */   {
/* 1989 */     this.logScaling = yesNo;
/*      */   }
/*      */ 
/*      */   public void setLogScaling(boolean yesNo)
/*      */   {
/* 1998 */     this.logScaling = yesNo;
/*      */   }
/*      */ 
/*      */   public void setSciLogScaling(boolean yesNo)
/*      */   {
/* 2007 */     if (yesNo) {
/* 2008 */       this.logScaling = true;
/* 2009 */       this.autoScale = true;
/*      */     }
/*      */ 
/* 2012 */     this.sciLogScaling = yesNo;
/*      */   }
/*      */ 
/*      */   public boolean getSciLogScaling()
/*      */   {
/* 2020 */     return this.sciLogScaling;
/*      */   }
/*      */ 
/*      */   public void setMajTickLength(int num)
/*      */   {
/* 2029 */     this.majTickLength = num;
/*      */   }
/*      */ 
/*      */   public void setMajTickVis(boolean onOff)
/*      */   {
/* 2038 */     this.majTickVis = onOff;
/*      */   }
/*      */ 
/*      */   public void setMinTickLength(int num)
/*      */   {
/* 2047 */     this.minTickLength = num;
/*      */   }
/*      */ 
/*      */   public void setMinTickVis(boolean onOff)
/*      */   {
/* 2056 */     this.minTickVis = onOff;
/*      */   }
/*      */ 
/*      */   public void setNumGrids(int num)
/*      */   {
/* 2065 */     this.numGrids = num;
/*      */   }
/*      */ 
/*      */   public void setNumLabels(int num)
/*      */   {
/* 2074 */     this.numLabels = num;
/*      */   }
/*      */ 
/*      */   public void setNumMajTicks(int num)
/*      */   {
/* 2083 */     this.numMajTicks = num;
/*      */   }
/*      */ 
/*      */   public void setNumMinTicks(int num)
/*      */   {
/* 2092 */     this.numMinTicks = num;
/*      */   }
/*      */ 
/*      */   public void setPlotarea(Plotarea p)
/*      */   {
/* 2101 */     this.plotarea = p;
/*      */ 
/* 2103 */     if ((this.globals == null) && 
/* 2104 */       (p != null))
/* 2105 */       this.globals = p.globals;
/*      */   }
/*      */ 
/*      */   public void setSide(int num)
/*      */   {
/* 2116 */     this.side = num;
/*      */   }
/*      */ 
/*      */   public void setTickGc(Gc g)
/*      */   {
/* 2125 */     this.tickGc = g;
/* 2126 */     this.tickGc.globals = this.globals;
/*      */   }
/*      */ 
/*      */   public void setTitleColor(Color c)
/*      */   {
/* 2135 */     this.titleColor = c;
/*      */   }
/*      */ 
/*      */   public void setTitleFont(Font f)
/*      */   {
/* 2144 */     this.titleFont = f;
/*      */   }
/*      */ 
/*      */   public void setTitleRotated(boolean yesNo)
/*      */   {
/* 2152 */     this.titleRotation = yesNo;
/*      */   }
/*      */ 
/*      */   public void setTitleString(String s)
/*      */   {
/* 2161 */     this.titleString = s;
/*      */   }
/*      */ 
/*      */   public void setUseDisplayList(boolean onOff)
/*      */   {
/* 2171 */     this.useDisplayList = onOff;
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 2178 */     return getClass().getName() + "[" + " xAxis? " + this.isXAxis + " stepSize " + this.stepSize + 
/* 2179 */       " axisStart " + this.axisStart + " axisEnd " + this.axisEnd + " numMajTicks " + this.numMajTicks + 
/* 2180 */       " numMinTicks " + this.numMinTicks + " numLabels " + this.numLabels + " ]";
/*      */   }
/*      */ 
/*      */   protected int whereOnAxis(int whichStep, int whichElement)
/*      */   {
/* 2189 */     if (this.internalSciLogScaling)
/* 2190 */       return whereOnSciLogAxis(whichStep, whichElement);
/* 2191 */     if (this.logScaling) {
/* 2192 */       return whereOnLogAxis(whichStep, whichElement);
/*      */     }
/*      */ 
/* 2196 */     if ((this.side == 0) || (this.side == 2)) {
/* 2197 */       return this.startPoint.x + (int)(this.increment * whichStep);
/*      */     }
/* 2199 */     return this.startPoint.y + (int)(this.increment * whichStep);
/*      */   }
/*      */ 
/*      */   protected int whereOnLogAxis(int whichStep, int whichElement)
/*      */   {
/* 2205 */     if (this.logDatum == null) {
/* 2206 */       this.logDatum = new Datum(0.0D, 0.0D, this.globals);
/*      */     }
/*      */ 
/* 2209 */     if (whichElement == 3) {
/* 2210 */       if (!this.isZeroStart) {
/* 2211 */         this.logDatum.setY(this.axisStart + whichStep * this.minTickIncrement);
/* 2212 */         this.logDatum.setX(this.axisStart + whichStep * this.minTickIncrement);
/*      */       }
/* 2214 */       else if (whichStep == 0) {
/* 2215 */         this.logDatum.setY(this.axisStart);
/* 2216 */         this.logDatum.setX(this.axisStart);
/*      */       } else {
/* 2218 */         this.logDatum.setY(0.0D + whichStep * this.minTickIncrement);
/* 2219 */         this.logDatum.setX(0.0D + whichStep * this.minTickIncrement);
/*      */       }
/*      */ 
/*      */     }
/* 2223 */     else if (!this.isZeroStart) {
/* 2224 */       this.logDatum.setY(this.axisStart + whichStep * this.labelIncrement);
/* 2225 */       this.logDatum.setX(this.axisStart + whichStep * this.labelIncrement);
/*      */     }
/* 2227 */     else if (whichStep == 0) {
/* 2228 */       this.logDatum.setY(this.axisStart);
/* 2229 */       this.logDatum.setX(this.axisStart);
/*      */     } else {
/* 2231 */       this.logDatum.setY(0.0D + whichStep * this.labelIncrement);
/* 2232 */       this.logDatum.setX(0.0D + whichStep * this.labelIncrement);
/*      */     }
/*      */ 
/* 2237 */     if (this.transformer == null) {
/* 2238 */       initializeTransform();
/*      */     }
/*      */ 
/* 2241 */     Point p = this.transformer.point(this.logDatum.x, this.logDatum.y);
/*      */ 
/* 2243 */     if ((this.side == 0) || (this.side == 2))
/*      */     {
/* 2245 */       return p.x;
/*      */     }
/* 2247 */     return p.y;
/*      */   }
/*      */ 
/*      */   public void addThresholdLine(ThresholdLine tl)
/*      */   {
/* 2257 */     if (this.thresholdLines == null) {
/* 2258 */       this.thresholdLines = new ArrayList();
/*      */     }
/*      */ 
/* 2261 */     this.thresholdLines.add(tl);
/*      */   }
/*      */ 
/*      */   public ArrayList getThresholdLines()
/*      */   {
/* 2269 */     return this.thresholdLines;
/*      */   }
/*      */ 
/*      */   public void drawThresholdLines(Graphics g)
/*      */   {
/* 2278 */     if (this.thresholdLines == null) {
/* 2279 */       return;
/*      */     }
/*      */ 
/* 2283 */     initializeTransform();
/*      */ 
/* 2285 */     if (this.logScaling) {
/* 2286 */       if ((this.side == 0) || (this.side == 2))
/* 2287 */         this.transformer.logXScaling = true;
/*      */       else {
/* 2289 */         this.transformer.logYScaling = true;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2294 */     for (int i = 0; i < this.thresholdLines.size(); i++) {
/* 2295 */       ((ThresholdLine)this.thresholdLines.get(i)).draw(this, g);
/*      */     }
/*      */ 
/* 2298 */     this.transformer = null;
/*      */   }
/*      */ 
/*      */   private void initializeTransform() {
/* 2302 */     Point ll = this.plotarea.transform.point(this.plotarea.llX, this.plotarea.llY);
/* 2303 */     Point ur = this.plotarea.transform.point(this.plotarea.urX, this.plotarea.urY);
/*      */ 
/* 2305 */     if ((this.side == 0) || (this.side == 2))
/* 2306 */       this.transformer = new Transform(this.axisStart, 0.0D, this.axisEnd, 1.0D, ll, ur);
/*      */     else
/* 2308 */       this.transformer = new Transform(0.0D, this.axisStart, 1.0D, this.axisEnd, ll, ur);
/*      */   }
/*      */ 
/*      */   private void sciLogAxisDrawSetup(Graphics g)
/*      */   {
/* 2313 */     this.sciLogTempGc = this.gridGc;
/* 2314 */     this.sciLogSaveNumGrids = this.numGrids;
/*      */ 
/* 2316 */     if (this.sciLogUseMinorGrids) {
/* 2317 */       this.doingMinorGrids = true;
/*      */ 
/* 2319 */       if (this.sciLogMinorGridGc == null) {
/* 2320 */         this.sciLogMinorGridGc = new Gc(this.globals);
/* 2321 */         this.sciLogMinorGridGc.setLineColor(Color.lightGray);
/*      */       }
/*      */ 
/* 2324 */       setGridGc(this.sciLogMinorGridGc);
/* 2325 */       this.numGrids = this.numMinTicks;
/*      */     } else {
/* 2327 */       this.doingMinorGrids = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void sciLogAxisDrawCleanup(Graphics g) {
/* 2332 */     setGridGc(this.sciLogTempGc);
/*      */ 
/* 2334 */     if ((this.sciLogUseMinorGrids) && (this.sciLogUseMajorGrids) && (this.gridVis)) {
/* 2335 */       this.doingMinorGrids = false;
/* 2336 */       setGridGc(this.sciLogTempGc);
/* 2337 */       this.numGrids = this.sciLogSaveNumGrids;
/*      */ 
/* 2339 */       if (!this.globals.threeD)
/* 2340 */         drawGrids(g);
/*      */       else {
/* 2342 */         draw3Dgrids(g);
/*      */       }
/*      */     }
/*      */ 
/* 2346 */     this.internalSciLogScaling = false;
/*      */   }
/*      */ 
/*      */   protected void checkSciLogAx()
/*      */   {
/* 2351 */     this.axisStart = Math.floor(this.axisStart);
/* 2352 */     this.axisEnd = Math.ceil(this.axisEnd);
/* 2353 */     this.axMagnitude = ((int)(this.axisEnd - this.axisStart));
/*      */ 
/* 2355 */     int tens = this.axMagnitude * 10;
/* 2356 */     int threes = this.axMagnitude * 3;
/*      */   }
/*      */ 
/*      */   protected String getSciLogLabel(double dummy, int index) {
/* 2360 */     double val = Math.pow(10.0D, this.logAxisStart + index * this.labelMultiplier);
/*      */ 
/* 2362 */     return fmtLabel(val);
/*      */   }
/*      */ 
/*      */   static double log10(double inVal) {
/* 2366 */     return Math.log(inVal) * 0.4342942D;
/*      */   }
/*      */ 
/*      */   protected synchronized boolean sciLogScale() {
/* 2370 */     this.labelMultiplier = 1;
/*      */ 
/* 2372 */     int nmsets = datasetsInUse();
/*      */ 
/* 2374 */     if (nmsets == 0) {
/* 2375 */       return false;
/*      */     }
/*      */ 
/* 2378 */     double hi = getMaxValsFromData(nmsets);
/* 2379 */     double low = getMinValsFromData(nmsets);
/*      */ 
/* 2381 */     if ((hi <= 0.0D) || (low <= 0.0D)) {
/* 2382 */       return false;
/*      */     }
/*      */     try
/*      */     {
/* 2386 */       this.logAxisStart = Math.floor(log10(low));
/* 2387 */       this.logAxisEnd = Math.ceil(log10(hi));
/* 2388 */       this.axisStart = Math.pow(10.0D, this.logAxisStart);
/* 2389 */       this.axisEnd = Math.pow(10.0D, this.logAxisEnd);
/*      */ 
/* 2391 */       this.axMagnitude = ((int)(this.logAxisEnd - this.logAxisStart));
/* 2392 */       this.numMinTicks = (10 * this.axMagnitude);
/* 2393 */       this.numLabels = (this.numMajTicks = this.numGrids = this.axMagnitude);
/*      */ 
/* 2395 */       if (this.numLabels > this.sciLogMaxLabelCount) {
/* 2396 */         boolean easilyReducible = true;
/*      */         do
/*      */         {
/* 2399 */           if (this.numLabels % 2 == 0) {
/* 2400 */             this.numLabels /= 2;
/* 2401 */             this.labelMultiplier *= 2;
/* 2402 */           } else if (this.numLabels % 3 == 0) {
/* 2403 */             this.numLabels /= 3;
/* 2404 */             this.labelMultiplier *= 3;
/* 2405 */           } else if (this.numLabels % 5 == 0) {
/* 2406 */             this.numLabels /= 5;
/* 2407 */             this.labelMultiplier *= 5;
/* 2408 */           } else if (this.numLabels % 7 == 0) {
/* 2409 */             this.numLabels /= 7;
/* 2410 */             this.labelMultiplier *= 7;
/* 2411 */           } else if (this.numLabels % 11 == 0) {
/* 2412 */             this.numLabels /= 11;
/* 2413 */             this.labelMultiplier *= 11;
/*      */           } else {
/* 2415 */             easilyReducible = false;
/*      */           }
/* 2398 */           if (!easilyReducible) break;  } while (this.numLabels > this.sciLogMaxLabelCount);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2421 */       return false;
/*      */     }
/*      */ 
/* 2424 */     return true;
/*      */   }
/*      */ 
/*      */   protected int whereOnSciLogAxis(int whichStep, int whichElement) {
/* 2428 */     boolean doTwoFive = false;
/* 2429 */     boolean doOneTwo = false;
/* 2430 */     int threes = this.axMagnitude * 3;
/* 2431 */     int loc = 0;
/* 2432 */     int side = getSide();
/*      */ 
/* 2434 */     if ((whichElement == 3) || ((this.doingMinorGrids) && (whichElement == 2)))
/*      */     {
/* 2436 */       loc = (int)((whichStep - whichStep % 10) * this.increment + 
/* 2437 */         this.increment * oneTwoSteps[(whichStep % 10)]);
/*      */ 
/* 2439 */       if ((whichStep == this.numGrids - 1) && (this.plotarea.gc.outlineFills))
/* 2440 */         return -10;
/*      */     }
/*      */     else {
/* 2443 */       loc = (int)(this.increment * whichStep);
/*      */     }
/*      */ 
/* 2446 */     if ((side == 0) || (side == 2)) {
/* 2447 */       return this.startPoint.x + loc;
/*      */     }
/* 2449 */     return this.startPoint.y + loc;
/*      */   }
/*      */ 
/*      */   public void setSciLogMaxLabelCount(int count)
/*      */   {
/* 2459 */     this.sciLogMaxLabelCount = count;
/*      */   }
/*      */ 
/*      */   public int getSciLogMaxLabelCount()
/*      */   {
/* 2467 */     return this.sciLogMaxLabelCount;
/*      */   }
/*      */ 
/*      */   public void setSciLogUseMajorGrids(boolean majGrid)
/*      */   {
/* 2475 */     this.sciLogUseMajorGrids = majGrid;
/*      */   }
/*      */ 
/*      */   public boolean getSciLogUseMajorGrids()
/*      */   {
/* 2483 */     return this.sciLogUseMajorGrids;
/*      */   }
/*      */ 
/*      */   public void setSciLogUseMinorGrids(boolean minGrid)
/*      */   {
/* 2491 */     this.sciLogUseMinorGrids = minGrid;
/*      */   }
/*      */ 
/*      */   public boolean getSciLogUseMinorGrids()
/*      */   {
/* 2499 */     return this.sciLogUseMinorGrids;
/*      */   }
/*      */ 
/*      */   public void setSciLogMinorGridGc(Gc gc)
/*      */   {
/* 2507 */     gc.globals = this.globals;
/* 2508 */     this.sciLogMinorGridGc = gc;
/*      */   }
/*      */ 
/*      */   public Gc getSciLogMinorGridGc()
/*      */   {
/* 2516 */     return this.sciLogMinorGridGc;
/*      */   }
/*      */ 
/*      */   public String getPattern()
/*      */   {
/* 2525 */     return this.pattern;
/*      */   }
/*      */ 
/*      */   public void setPattern(String pattern)
/*      */   {
/* 2534 */     this.pattern = pattern;
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Axis
 * JD-Core Version:    0.6.2
 */