/*      */ package jatools.component.chart.chart;
/*      */ 
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Point;
/*      */ import java.io.PrintStream;
/*      */ import java.text.Format;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.TimeZone;
/*      */ 
/*      */ public class DateAxis extends Axis
/*      */   implements AxisInterface
/*      */ {
/*   24 */   Date startDate = new Date(0L);
/*   25 */   Date endDate = new Date(0L);
/*   26 */   Date tmpDate = new Date(0L);
/*      */ 
/*   31 */   protected Calendar calendar = Calendar.getInstance();
/*   32 */   private Calendar tmpCalendar = (Calendar)this.calendar.clone();
/*      */ 
/*   37 */   protected java.text.DateFormat dateFormat = null;
/*   38 */   protected java.text.DateFormat userDateFormat = null;
/*   39 */   protected java.text.DateFormat secondaryUserDateFormat = null;
/*   40 */   protected Integer userScalingType = null;
/*   41 */   protected Integer userStepSize = null;
/*      */   protected long stepSize;
/*   56 */   int scalingType = 4;
/*      */   public static final int SECOND_SCALING = 1;
/*      */   public static final int MINUTE_SCALING = 2;
/*      */   public static final int HOUR_SCALING = 3;
/*      */   public static final int DAY_SCALING = 4;
/*      */   public static final int WEEK_SCALING = 5;
/*      */   public static final int MONTH_SCALING = 6;
/*      */   public static final int YEAR_SCALING = 7;
/*      */   static final long oneMinute = 60000L;
/*      */   static final long oneHour = 3600000L;
/*      */   static final long oneDay = 86400000L;
/*      */   static final long oneWeek = 604800000L;
/*      */   static final long oneMonth = 2592000000L;
/*      */   static final long oneYear = 31536000000L;
/*      */   TimeZone timeZone;
/*      */ 
/*      */   public DateAxis()
/*      */   {
/*   99 */     this.pattern = "yy-MM-dd";
/*      */   }
/*      */ 
/*      */   protected void calculateElementCount()
/*      */   {
/*  106 */     if (this.userStepSize == null)
/*  107 */       return;
/*  108 */     int axisStepSize = this.userStepSize.intValue();
/*      */     int span;
/*      */     int span;
/*      */     int span;
/*      */     int span;
/*      */     int span;
/*      */     int span;
/*  112 */     switch (this.scalingType) {
/*      */     case 7:
/*  114 */       span = getSpan(1, this.startDate, this.endDate);
/*  115 */       break;
/*      */     case 6:
/*  117 */       span = getMonthSpan(this.startDate, this.endDate);
/*  118 */       break;
/*      */     case 5:
/*  120 */       int span = getSpan(5, this.startDate, this.endDate);
/*  121 */       span /= this.calendar.getMaximum(7);
/*  122 */       break;
/*      */     case 4:
/*  124 */       span = getSpan(5, this.startDate, this.endDate);
/*  125 */       break;
/*      */     case 3:
/*  127 */       int hourSpan = 0;
/*  128 */       long rawTime = this.startDate.getTime();
/*  129 */       while (rawTime < this.endDate.getTime()) {
/*  130 */         hourSpan++;
/*  131 */         rawTime += 3600000L;
/*      */       }
/*  133 */       span = hourSpan;
/*  134 */       break;
/*      */     case 2:
/*  136 */       span = getSpan(12, this.startDate, this.endDate);
/*  137 */       break;
/*      */     default:
/*  139 */       span = (int)((this.axisEnd - this.axisStart) / 1000.0D);
/*      */     }
/*      */ 
/*  142 */     this.numLabels = ((int)Math.floor(span / axisStepSize));
/*  143 */     this.numGrids = this.numLabels;
/*  144 */     this.numMajTicks = this.numLabels;
/*      */   }
/*      */ 
/*      */   public DateAxis(Dataset[] datasets, boolean yesNo, Plotarea plotarea)
/*      */   {
/*  157 */     super(datasets, yesNo, plotarea);
/*  158 */     this.pattern = "yy-MM-dd";
/*      */   }
/*      */ 
/*      */   protected void zDoDayScale()
/*      */   {
/*  165 */     int normalizedIncrement = getDayIncrement(this.startDate, this.endDate);
/*      */ 
/*  167 */     this.calendar.setTime(this.startDate);
/*  168 */     int tmp = 0;
/*  169 */     this.tmpDate = this.calendar.getTime();
/*  170 */     while (this.tmpDate.before(this.endDate)) {
/*  171 */       this.calendar.add(5, 1);
/*  172 */       this.tmpDate = this.calendar.getTime();
/*  173 */       tmp++;
/*      */     }
/*      */ 
/*  176 */     this.calendar.setTime(this.endDate);
/*  177 */     while (tmp % normalizedIncrement != 0) {
/*  178 */       this.calendar.add(5, 1);
/*  179 */       tmp++;
/*      */     }
/*  181 */     this.endDate = this.calendar.getTime();
/*      */ 
/*  183 */     this.axisStart = this.startDate.getTime();
/*  184 */     this.axisEnd = this.endDate.getTime();
/*  185 */     this.stepSize = normalizedIncrement;
/*  186 */     this.numLabels = (tmp / normalizedIncrement);
/*      */   }
/*      */ 
/*      */   protected void doDayScale()
/*      */   {
/*  196 */     this.calendar = Calendar.getInstance();
/*  197 */     this.calendar.setTime(this.endDate);
/*  198 */     long endTime = this.calendar.getTime().getTime();
/*      */ 
/*  200 */     this.calendar.set(11, 0);
/*  201 */     this.calendar.set(12, 0);
/*  202 */     this.calendar.set(13, 0);
/*  203 */     this.calendar.set(14, 0);
/*  204 */     long adjustedTime = this.calendar.getTime().getTime();
/*  205 */     if (endTime - adjustedTime > 0L) {
/*  206 */       this.calendar.add(5, 1);
/*      */     }
/*  208 */     this.endDate = this.calendar.getTime();
/*      */ 
/*  210 */     this.calendar.setTime(this.startDate);
/*  211 */     this.calendar.set(11, 0);
/*  212 */     this.calendar.set(12, 0);
/*  213 */     this.calendar.set(13, 0);
/*  214 */     this.calendar.set(14, 0);
/*  215 */     this.startDate = this.calendar.getTime();
/*      */ 
/*  218 */     int normalizedIncrement = getDayIncrement(this.startDate, this.endDate);
/*      */ 
/*  220 */     this.calendar.setTime(this.startDate);
/*      */ 
/*  224 */     this.startDate = this.calendar.getTime();
/*      */ 
/*  226 */     int tmp = 0;
/*  227 */     this.tmpDate = this.calendar.getTime();
/*  228 */     while (this.tmpDate.before(this.endDate)) {
/*  229 */       this.calendar.add(5, 1);
/*  230 */       this.tmpDate = this.calendar.getTime();
/*  231 */       tmp++;
/*      */     }
/*      */ 
/*  234 */     this.calendar.setTime(this.endDate);
/*  235 */     while (tmp % normalizedIncrement != 0) {
/*  236 */       this.calendar.add(5, 1);
/*  237 */       tmp++;
/*      */     }
/*  239 */     this.endDate = this.calendar.getTime();
/*      */ 
/*  241 */     this.axisStart = this.startDate.getTime();
/*  242 */     this.axisEnd = this.endDate.getTime();
/*  243 */     this.stepSize = normalizedIncrement;
/*  244 */     this.numLabels = (tmp / normalizedIncrement);
/*      */   }
/*      */ 
/*      */   protected void doHourScale()
/*      */   {
/*  260 */     Calendar endCalendar = this.tmpCalendar;
/*  261 */     endCalendar.setTime(this.endDate);
/*  262 */     int tmp = endCalendar.get(12);
/*  263 */     if (tmp > 0) {
/*  264 */       endCalendar.set(11, endCalendar.get(11) + 1);
/*  265 */       endCalendar.set(12, endCalendar.getMinimum(12));
/*  266 */       endCalendar.set(13, endCalendar.getMinimum(13));
/*  267 */       endCalendar.set(14, endCalendar.getMinimum(14));
/*  268 */       this.endDate = endCalendar.getTime();
/*      */     }
/*      */ 
/*  271 */     this.calendar.setTime(this.startDate);
/*  272 */     this.calendar.set(12, 0);
/*  273 */     this.calendar.set(13, this.calendar.getMinimum(13));
/*  274 */     this.calendar.set(14, this.calendar.getMinimum(14));
/*  275 */     this.startDate = this.calendar.getTime();
/*      */ 
/*  277 */     int hourSpan = 0;
/*  278 */     long rawTime = this.startDate.getTime();
/*  279 */     while (rawTime < endCalendar.getTime().getTime()) {
/*  280 */       hourSpan++;
/*  281 */       rawTime += 3600000L;
/*      */     }
/*  283 */     int normalizedIncrement = getHourIncrement(hourSpan);
/*      */ 
/*  286 */     this.calendar.setTime(this.startDate);
/*  287 */     tmp = this.calendar.get(11);
/*  288 */     rawTime = this.startDate.getTime();
/*  289 */     while (tmp % normalizedIncrement != 0) {
/*  290 */       rawTime -= 3600000L;
/*  291 */       this.startDate.setTime(rawTime);
/*  292 */       this.calendar.setTime(this.startDate);
/*  293 */       tmp = this.calendar.get(11);
/*  294 */       hourSpan++;
/*      */     }
/*      */ 
/*  297 */     tmp = endCalendar.get(11);
/*  298 */     rawTime = this.endDate.getTime();
/*  299 */     while (tmp % normalizedIncrement != 0) {
/*  300 */       rawTime += 3600000L;
/*  301 */       this.endDate.setTime(rawTime);
/*  302 */       endCalendar.setTime(this.endDate);
/*  303 */       tmp = endCalendar.get(11);
/*  304 */       hourSpan++;
/*      */     }
/*  306 */     this.endDate.setTime(rawTime);
/*      */ 
/*  308 */     this.stepSize = normalizedIncrement;
/*  309 */     this.axisStart = this.startDate.getTime();
/*  310 */     this.axisEnd = this.endDate.getTime();
/*  311 */     this.numLabels = (hourSpan / normalizedIncrement);
/*  312 */     this.tmpDate.setTime(this.startDate.getTime() + 3600000 * hourSpan);
/*  313 */     this.calendar.setTime(this.startDate);
/*  314 */     this.calendar.add(10, hourSpan);
/*  315 */     if (this.calendar.getTime().getTime() < this.tmpDate.getTime())
/*  316 */       this.numLabels += 1;
/*      */   }
/*      */ 
/*      */   protected void doMinuteScale()
/*      */   {
/*  326 */     Calendar endCalendar = this.tmpCalendar;
/*  327 */     Calendar startCalendar = this.calendar;
/*  328 */     boolean secondsAtEnd = false;
/*      */ 
/*  331 */     startCalendar.setTime(this.startDate);
/*  332 */     startCalendar.set(13, startCalendar.getMinimum(13));
/*  333 */     startCalendar.set(14, startCalendar.getMinimum(14));
/*  334 */     endCalendar.setTime(this.endDate);
/*  335 */     if (endCalendar.get(13) > 0) {
/*  336 */       secondsAtEnd = true;
/*      */     }
/*  338 */     if ((!secondsAtEnd) && (endCalendar.get(14) > 0)) {
/*  339 */       secondsAtEnd = true;
/*      */     }
/*  341 */     endCalendar.set(13, endCalendar.getMinimum(13));
/*  342 */     endCalendar.set(14, endCalendar.getMinimum(14));
/*      */ 
/*  344 */     this.startDate = startCalendar.getTime();
/*  345 */     this.endDate = endCalendar.getTime();
/*  346 */     int minuteSpan = getSpan(12, this.startDate, this.endDate);
/*  347 */     startCalendar.setTime(this.startDate);
/*  348 */     endCalendar.setTime(this.endDate);
/*  349 */     int normalizedIncrement = getMinuteIncrement(minuteSpan);
/*      */ 
/*  351 */     int tmpMinute = startCalendar.get(12);
/*  352 */     while (tmpMinute % normalizedIncrement != 0) {
/*  353 */       tmpMinute--;
/*  354 */       startCalendar.add(12, -1);
/*  355 */       minuteSpan++;
/*      */     }
/*      */ 
/*  359 */     if (secondsAtEnd) {
/*  360 */       minuteSpan++;
/*  361 */       endCalendar.add(12, 1);
/*      */     }
/*  363 */     while (minuteSpan % normalizedIncrement != 0)
/*      */     {
/*  365 */       endCalendar.add(12, 1);
/*  366 */       minuteSpan++;
/*      */     }
/*      */ 
/*  370 */     this.startDate = startCalendar.getTime();
/*  371 */     this.endDate = endCalendar.getTime();
/*      */ 
/*  373 */     this.stepSize = normalizedIncrement;
/*  374 */     this.axisStart = this.startDate.getTime();
/*  375 */     this.axisEnd = this.endDate.getTime();
/*  376 */     this.numLabels = (minuteSpan / normalizedIncrement);
/*      */   }
/*      */ 
/*      */   protected void doMonthScale()
/*      */   {
/*  385 */     this.calendar.setTime(this.endDate);
/*  386 */     if (this.calendar.get(5) > 1)
/*  387 */       this.calendar.set(2, this.calendar.get(2) + 1);
/*  388 */     this.calendar.set(5, 1);
/*  389 */     this.calendar.set(11, 0);
/*  390 */     this.calendar.set(12, 0);
/*  391 */     this.calendar.set(13, 0);
/*  392 */     this.calendar.set(14, 0);
/*  393 */     this.endDate = this.calendar.getTime();
/*      */ 
/*  396 */     this.calendar.setTime(this.startDate);
/*  397 */     this.calendar.set(5, 1);
/*  398 */     this.calendar.set(11, 0);
/*  399 */     this.calendar.set(12, 0);
/*  400 */     this.calendar.set(13, 0);
/*  401 */     this.calendar.set(14, 0);
/*  402 */     this.startDate = this.calendar.getTime();
/*      */ 
/*  404 */     int monthSpan = getSpan(2, this.startDate, this.endDate);
/*      */ 
/*  406 */     int normalizedIncrement = monthSpan / 4;
/*      */ 
/*  409 */     if (normalizedIncrement == 0) {
/*  410 */       normalizedIncrement = 1;
/*      */     }
/*      */ 
/*  413 */     if (normalizedIncrement > 2) {
/*  414 */       if (monthSpan <= 12)
/*  415 */         normalizedIncrement = 3;
/*  416 */       else if (monthSpan <= 24)
/*  417 */         normalizedIncrement = 6;
/*  418 */       else normalizedIncrement = 12;
/*      */     }
/*      */ 
/*  421 */     this.tmpCalendar.setTime(this.endDate);
/*  422 */     while (monthSpan % normalizedIncrement != 0) {
/*  423 */       monthSpan++;
/*  424 */       this.tmpCalendar.add(2, 1);
/*      */     }
/*  426 */     this.endDate = this.tmpCalendar.getTime();
/*      */ 
/*  428 */     this.axisStart = this.startDate.getTime();
/*  429 */     this.axisEnd = this.endDate.getTime();
/*  430 */     this.stepSize = normalizedIncrement;
/*  431 */     this.numLabels = (monthSpan / (int)this.stepSize);
/*      */   }
/*      */ 
/*      */   protected void doSecondScale()
/*      */   {
/*  443 */     long max = this.endDate.getTime();
/*  444 */     long min = this.startDate.getTime();
/*  445 */     long diff = max - min;
/*  446 */     long magnitude = getMagnitude(diff);
/*      */     long normalizedIncrement;
/*  447 */     if (magnitude < 0L) {
/*  448 */       long normalizedIncrement = getMsIncrement(diff, magnitude);
/*  449 */       while (min % normalizedIncrement != 0L) min -= 1L;
/*  450 */       while (max % normalizedIncrement != 0L) max += 1L; 
/*      */     }
/*      */     else
/*      */     {
/*  453 */       normalizedIncrement = getSIncrement(diff / 1000L, magnitude);
/*  454 */       this.calendar.setTime(this.startDate);
/*  455 */       this.calendar.set(14, 0);
/*  456 */       int seconds = this.calendar.get(13);
/*  457 */       while (seconds % normalizedIncrement != 0L) {
/*  458 */         this.calendar.add(13, -1);
/*  459 */         seconds--;
/*      */       }
/*  461 */       this.startDate = this.calendar.getTime();
/*      */ 
/*  463 */       this.calendar.setTime(this.endDate);
/*  464 */       if (this.calendar.get(14) > 0) {
/*  465 */         this.calendar.add(13, 1);
/*  466 */         this.calendar.set(14, 0);
/*      */       }
/*  468 */       seconds = this.calendar.get(13);
/*  469 */       while (seconds % normalizedIncrement != 0L) {
/*  470 */         this.calendar.add(13, 1);
/*  471 */         seconds++;
/*      */       }
/*  473 */       this.endDate = this.calendar.getTime();
/*  474 */       min = this.startDate.getTime();
/*  475 */       max = this.endDate.getTime();
/*  476 */       normalizedIncrement *= 1000L;
/*      */     }
/*  478 */     this.stepSize = normalizedIncrement;
/*  479 */     this.axisStart = min;
/*  480 */     this.axisEnd = max;
/*  481 */     this.numLabels = ((int)((this.axisEnd - this.axisStart) / this.stepSize));
/*      */   }
/*      */ 
/*      */   protected void doWeekScale()
/*      */   {
/*  490 */     Calendar endCalendar = this.tmpCalendar;
/*  491 */     endCalendar.setTime(this.endDate);
/*  492 */     int tmp = endCalendar.get(10);
/*      */ 
/*  494 */     endCalendar.set(11, endCalendar.getMinimum(11));
/*  495 */     endCalendar.set(12, 0);
/*  496 */     endCalendar.set(13, 0);
/*  497 */     this.calendar.set(14, 0);
/*  498 */     if (tmp > 0) {
/*  499 */       endCalendar.add(5, 1);
/*      */     }
/*      */ 
/*  502 */     this.calendar.setTime(this.startDate);
/*  503 */     this.calendar.set(11, this.calendar.getMinimum(11));
/*  504 */     this.calendar.set(12, this.calendar.getMinimum(12));
/*  505 */     this.calendar.set(13, this.calendar.getMinimum(13));
/*  506 */     this.calendar.set(14, this.calendar.getMinimum(14));
/*  507 */     tmp = this.calendar.getMinimum(7);
/*  508 */     while (this.calendar.get(7) != tmp)
/*  509 */       this.calendar.add(5, -1);
/*  510 */     this.startDate = this.calendar.getTime();
/*      */ 
/*  513 */     tmp = this.calendar.get(7);
/*  514 */     while (endCalendar.get(7) != tmp) {
/*  515 */       endCalendar.add(5, 1);
/*      */     }
/*  517 */     this.endDate = endCalendar.getTime();
/*      */ 
/*  519 */     int weekSpan = getSpan(5, this.startDate, this.endDate);
/*  520 */     weekSpan /= this.calendar.getMaximum(7);
/*      */ 
/*  523 */     this.stepSize = getWIncrement(weekSpan);
/*      */ 
/*  526 */     endCalendar.setTime(this.endDate);
/*  527 */     while (weekSpan % this.stepSize != 0L) {
/*  528 */       endCalendar.add(5, 7);
/*  529 */       weekSpan++;
/*      */     }
/*  531 */     this.endDate = endCalendar.getTime();
/*      */ 
/*  533 */     this.axisStart = this.startDate.getTime();
/*  534 */     this.axisEnd = this.endDate.getTime();
/*  535 */     this.numLabels = (weekSpan / (int)this.stepSize);
/*      */   }
/*      */ 
/*      */   protected void doYearScale()
/*      */   {
/*  551 */     this.calendar.setTime(this.endDate);
/*  552 */     if (this.calendar.get(2) > 0)
/*  553 */       this.calendar.set(1, this.calendar.get(1) + 1);
/*  554 */     this.calendar.set(2, 0);
/*  555 */     this.calendar.set(5, 1);
/*  556 */     this.calendar.set(11, 0);
/*  557 */     this.calendar.set(12, 0);
/*  558 */     this.calendar.set(13, 0);
/*  559 */     this.calendar.set(14, 0);
/*  560 */     this.endDate = this.calendar.getTime();
/*      */ 
/*  562 */     this.calendar.setTime(this.startDate);
/*  563 */     this.calendar.set(2, 0);
/*  564 */     this.calendar.set(5, 1);
/*  565 */     this.calendar.set(11, 0);
/*  566 */     this.calendar.set(12, 0);
/*  567 */     this.calendar.set(13, 0);
/*  568 */     this.calendar.set(14, 0);
/*  569 */     this.startDate = this.calendar.getTime();
/*      */ 
/*  571 */     int yearSpan = getSpan(1, this.startDate, this.endDate);
/*      */ 
/*  573 */     int normalizedIncrement = yearSpan / 5;
/*      */ 
/*  575 */     Calendar endCalendar = this.tmpCalendar;
/*  576 */     endCalendar.setTime(this.endDate);
/*      */ 
/*  578 */     if (normalizedIncrement == 0)
/*  579 */       normalizedIncrement = 1;
/*  580 */     while (yearSpan % normalizedIncrement != 0) {
/*  581 */       yearSpan++;
/*  582 */       endCalendar.add(1, 1);
/*      */     }
/*      */ 
/*  585 */     this.axisStart = this.startDate.getTime();
/*  586 */     this.axisEnd = endCalendar.getTime().getTime();
/*  587 */     this.stepSize = normalizedIncrement;
/*  588 */     this.numLabels = (yearSpan / normalizedIncrement);
/*      */   }
/*      */ 
/*      */   public synchronized void draw(Graphics g)
/*      */   {
/*  595 */     if (this.userDateFormat != null)
/*  596 */       this.dateFormat = this.userDateFormat;
/*  597 */     if (this.autoScale) {
/*  598 */       scale();
/*      */     } else {
/*  600 */       this.stepSize = -1L;
/*  601 */       if (this.userStepSize != null)
/*  602 */         calculateElementCount();
/*      */     }
/*  604 */     if ((this.globals.threeD) && (
/*  605 */       (this.side == 3) || (this.side == 2))) {
/*  606 */       g = g.create();
/*  607 */       g.translate(this.globals.xOffset, -this.globals.yOffset);
/*      */     }
/*      */ 
/*  610 */     if (this.lineVis)
/*  611 */       drawLine(g);
/*  612 */     drawTicks(g);
/*  613 */     if (!this.autoScale)
/*  614 */       this.stepSize = -1L;
/*  615 */     if (this.gridVis)
/*  616 */       if (!this.globals.threeD)
/*  617 */         drawGrids(g);
/*      */       else
/*  619 */         draw3Dgrids(g);
/*  620 */     if (!this.autoScale)
/*  621 */       this.stepSize = -1L;
/*  622 */     if (this.labelVis)
/*  623 */       drawLabels(g);
/*  624 */     if (this.titleString != null)
/*  625 */       drawTitle(g, this.titleGap);
/*  626 */     if ((this.useDisplayList) && (this.globals.useDisplayList))
/*  627 */       buildDisplayList();
/*  628 */     this.dateFormat = null;
/*      */   }
/*      */ 
/*      */   public Calendar getCalendar()
/*      */   {
/*  636 */     return this.calendar;
/*      */   }
/*      */ 
/*      */   protected int getDayIncrement(Date from, Date to)
/*      */   {
/*  646 */     int diff = 0;
/*  647 */     this.calendar.setTime(from);
/*  648 */     Calendar toCal = (Calendar)this.calendar.clone();
/*  649 */     toCal.setTime(to);
/*  650 */     while (this.calendar.before(toCal)) {
/*  651 */       this.calendar.add(5, 1);
/*  652 */       diff++;
/*      */     }
/*      */ 
/*  656 */     if (diff <= 7)
/*  657 */       return 1;
/*  658 */     if (diff <= 14)
/*  659 */       return 2;
/*  660 */     if (diff <= 28) {
/*  661 */       return 3;
/*      */     }
/*      */ 
/*  664 */     int increment = diff / 5;
/*      */ 
/*  666 */     while (increment % this.calendar.getMaximum(7) > 0) increment++;
/*  667 */     return increment;
/*      */   }
/*      */ 
/*      */   protected String getDayLabel(int whichLabel)
/*      */   {
/*  686 */     if (this.barScaling)
/*  687 */       this.dateFormat = java.text.DateFormat.getDateInstance(3, 
/*  688 */         this.globals.locale);
/*      */     else {
/*  690 */       this.dateFormat = java.text.DateFormat.getDateInstance(3);
/*      */     }
/*      */ 
/*  693 */     ((SimpleDateFormat)this.dateFormat).applyPattern(this.pattern);
/*  694 */     this.tmpDate.setTime(()this.axisStart);
/*  695 */     this.calendar.setTime(this.tmpDate);
/*  696 */     this.calendar.add(5, (int)(whichLabel * this.stepSize));
/*  697 */     this.tmpDate = this.calendar.getTime();
/*  698 */     return this.dateFormat.format(this.tmpDate);
/*      */   }
/*      */ 
/*      */   protected int getHourIncrement(int diff)
/*      */   {
/*  705 */     if (diff <= 3)
/*  706 */       return 1;
/*  707 */     int increment = diff / 5;
/*  708 */     if (increment <= 1)
/*  709 */       return 2;
/*  710 */     if (increment <= 3) {
/*  711 */       return 3;
/*      */     }
/*  713 */     while (increment % 6 > 0) increment++;
/*  714 */     return increment;
/*      */   }
/*      */ 
/*      */   protected String getHourLabel(int whichLabel)
/*      */   {
/*  721 */     if (this.dateFormat == null) {
/*      */       try {
/*  723 */         this.dateFormat = java.text.DateFormat.getTimeInstance(3, this.globals.locale);
/*      */       } catch (MissingResourceException e) {
/*  725 */         System.out.println("locale is not available, using default locale instead");
/*  726 */         this.dateFormat = java.text.DateFormat.getTimeInstance(3);
/*      */       }
/*  728 */       if (this.timeZone != null) {
/*  729 */         this.dateFormat.setTimeZone(this.timeZone);
/*      */       }
/*      */     }
/*  732 */     this.tmpDate.setTime(()this.axisStart);
/*  733 */     this.calendar.setTime(this.tmpDate);
/*  734 */     this.calendar.add(10, (int)(whichLabel * this.stepSize));
/*  735 */     this.tmpDate = this.calendar.getTime();
/*      */ 
/*  738 */     if (this.calendar.get(11) == 0)
/*      */     {
/*  747 */       if (this.secondaryUserDateFormat == null) {
/*  748 */         java.text.DateFormat tdf = null;
/*      */         try {
/*  750 */           tdf = java.text.DateFormat.getDateInstance(3, this.globals.locale);
/*      */         } catch (MissingResourceException e) {
/*  752 */           System.out.println("locale is not available, using default locale instead");
/*  753 */           tdf = java.text.DateFormat.getDateInstance(3);
/*      */         }
/*      */ 
/*  756 */         if (this.timeZone != null)
/*  757 */           tdf.setTimeZone(this.timeZone);
/*  758 */         return tdf.format(this.tmpDate);
/*      */       }
/*      */ 
/*  761 */       return this.secondaryUserDateFormat.format(this.tmpDate);
/*      */     }
/*      */ 
/*  764 */     return jatools.formatter.DateFormat.format(this.tmpDate, this.pattern);
/*      */   }
/*      */ 
/*      */   protected String getLabel(double unused, int which)
/*      */   {
/*  774 */     if ((this.barScaling) && (
/*  775 */       (which == 0) || ((which == this.numLabels - 1) && (this.autoScale)))) {
/*  776 */       return " ";
/*      */     }
/*  778 */     if (this.pattern == null) this.pattern = "yy-MM-dd";
/*  779 */     switch (this.scalingType) { case 1:
/*  780 */       return getSecondLabel(which);
/*      */     case 2:
/*  781 */       return getMinuteLabel(which);
/*      */     case 3:
/*  782 */       return getHourLabel(which);
/*      */     case 4:
/*  783 */       return getDayLabel(which);
/*      */     case 5:
/*  784 */       return getWeekLabel(which);
/*      */     case 6:
/*  785 */       return getMonthLabel(which);
/*      */     case 7:
/*  786 */       return getYearLabel(which);
/*      */     }
/*  788 */     return "bad";
/*      */   }
/*      */ 
/*      */   public Format getLabelFormat()
/*      */   {
/*  795 */     return this.userDateFormat;
/*      */   }
/*      */ 
/*      */   long getMagnitude(long num)
/*      */   {
/*  803 */     if (num > 2000L) {
/*  804 */       long comparison = 15000L;
/*  805 */       int i = 1;
/*      */       while (true) {
/*  807 */         if (comparison > num)
/*  808 */           return i - 1;
/*  809 */         comparison += 15000L;
/*  810 */         i++;
/*      */       }
/*      */     }
/*  813 */     if (num < 10L)
/*  814 */       return -1L;
/*  815 */     if (num < 100L) {
/*  816 */       return -2L;
/*      */     }
/*      */ 
/*  822 */     return -3L;
/*      */   }
/*      */ 
/*      */   protected double getMaxValsFromData(int nmsets)
/*      */   {
/*  830 */     if (this.barScaling)
/*  831 */       return super.getMaxValsFromData(nmsets) + 1.0D;
/*  832 */     return super.getMaxValsFromData(nmsets);
/*      */   }
/*      */ 
/*      */   int getMinuteIncrement(int diff)
/*      */   {
/*  840 */     if (diff <= 5)
/*  841 */       return 1;
/*  842 */     int increment = diff / 4;
/*  843 */     if (increment <= 5) {
/*  844 */       while (increment % 2 > 0) increment++;
/*      */     }
/*  846 */     else if (increment <= 5)
/*  847 */       while (increment % 5 > 0) increment++;
/*      */     else
/*  849 */       while (increment % 15 > 0) increment++;
/*  850 */     return increment;
/*      */   }
/*      */ 
/*      */   protected String getMinuteLabel(int whichLabel)
/*      */   {
/*  858 */     if (this.dateFormat == null) {
/*      */       try {
/*  860 */         this.dateFormat = java.text.DateFormat.getTimeInstance(3, this.globals.locale);
/*      */       } catch (MissingResourceException e) {
/*  862 */         System.out.println("locale is not available, using default locale instead");
/*  863 */         this.dateFormat = java.text.DateFormat.getTimeInstance(3);
/*      */       }
/*      */ 
/*  866 */       if (this.timeZone != null) {
/*  867 */         this.dateFormat.setTimeZone(this.timeZone);
/*      */       }
/*      */     }
/*  870 */     this.tmpDate.setTime(()this.axisStart);
/*  871 */     this.calendar.setTime(this.tmpDate);
/*  872 */     this.calendar.add(12, (int)(whichLabel * this.stepSize));
/*  873 */     this.tmpDate = this.calendar.getTime();
/*  874 */     if ((this.calendar.get(12) == 0) && (this.calendar.get(11) == 0)) {
/*  875 */       if (this.secondaryUserDateFormat == null) {
/*  876 */         java.text.DateFormat tdf = null;
/*      */         try {
/*  878 */           tdf = java.text.DateFormat.getDateInstance(2, this.globals.locale);
/*      */         } catch (MissingResourceException e) {
/*  880 */           System.out.println("locale is not available, using default locale instead");
/*  881 */           tdf = java.text.DateFormat.getDateInstance(2);
/*      */         }
/*      */ 
/*  884 */         if (this.timeZone != null) {
/*  885 */           tdf.setTimeZone(this.timeZone);
/*      */         }
/*  887 */         this.tmpDate = this.calendar.getTime();
/*  888 */         return tdf.format(this.tmpDate);
/*      */       }
/*      */ 
/*  891 */       return this.secondaryUserDateFormat.format(this.tmpDate);
/*      */     }
/*      */ 
/*  895 */     return jatools.formatter.DateFormat.format(this.tmpDate, this.pattern);
/*      */   }
/*      */ 
/*      */   protected double getMinValsFromData(int nmsets)
/*      */   {
/*  903 */     double min = super.getMinValsFromData(nmsets);
/*  904 */     if (this.barScaling)
/*  905 */       return min - 10.0D;
/*  906 */     return min;
/*      */   }
/*      */ 
/*      */   protected String getMonthLabel(int whichLabel)
/*      */   {
/*  914 */     if (this.dateFormat == null) {
/*      */       try {
/*  916 */         this.dateFormat = java.text.DateFormat.getDateInstance(3, this.globals.locale);
/*      */       } catch (MissingResourceException e) {
/*  918 */         System.out.println("locale is not available, using default locale instead");
/*  919 */         this.dateFormat = java.text.DateFormat.getDateInstance(3);
/*      */       }
/*      */ 
/*  922 */       if (this.timeZone != null) {
/*  923 */         this.dateFormat.setTimeZone(this.timeZone);
/*      */       }
/*      */     }
/*  926 */     this.tmpDate.setTime(()this.axisStart);
/*  927 */     this.calendar.setTime(this.tmpDate);
/*  928 */     this.calendar.add(2, (int)(whichLabel * this.stepSize));
/*  929 */     this.tmpDate = this.calendar.getTime();
/*  930 */     if (this.calendar.get(2) == this.calendar.getMinimum(2)) {
/*  931 */       if (this.secondaryUserDateFormat == null) {
/*  932 */         java.text.DateFormat tdf = null;
/*      */         try {
/*  934 */           tdf = java.text.DateFormat.getDateInstance(2, this.globals.locale);
/*      */         } catch (MissingResourceException e) {
/*  936 */           System.out.println("locale is not available, using default locale instead");
/*  937 */           tdf = java.text.DateFormat.getDateInstance(2);
/*      */         }
/*  939 */         if (this.timeZone != null)
/*  940 */           tdf.setTimeZone(this.timeZone);
/*  941 */         this.tmpDate = this.calendar.getTime();
/*  942 */         return tdf.format(this.tmpDate);
/*      */       }
/*      */ 
/*  945 */       return this.secondaryUserDateFormat.format(this.tmpDate);
/*      */     }
/*  947 */     return jatools.formatter.DateFormat.format(this.tmpDate, this.pattern);
/*      */   }
/*      */ 
/*      */   long getMsIncrement(long diff, long magnitude)
/*      */   {
/*      */     double boundary;
/*      */     double boundary;
/*  956 */     if (magnitude == -1L) {
/*  957 */       boundary = 1.0D;
/*      */     }
/*      */     else
/*      */     {
/*      */       double boundary;
/*  958 */       if (magnitude == -2L)
/*  959 */         boundary = 10.0D;
/*  960 */       else boundary = 100.0D;
/*      */     }
/*  962 */     double magic = diff / (5.0D * boundary);
/*      */     double increment;
/*      */     double increment;
/*  963 */     if ((magic > 0.0D) && (magic < 0.5D)) {
/*  964 */       increment = boundary * 0.5D;
/*      */     }
/*      */     else
/*      */     {
/*      */       double increment;
/*  965 */       if ((magic > 0.5D) && (magic < 1.0D)) {
/*  966 */         increment = boundary;
/*      */       }
/*      */       else
/*      */       {
/*      */         double increment;
/*  967 */         if ((magic > 1.0D) && (magic < 9.0D))
/*  968 */           increment = boundary * 2.0D;
/*      */         else
/*  970 */           increment = boundary; 
/*      */       }
/*      */     }
/*  971 */     return ()increment;
/*      */   }
/*      */   long getPosition(int whichLabel, int itemType) {
/*  974 */     this.startDate.setTime(()this.axisStart);
/*  975 */     this.calendar.setTime(this.startDate);
/*  976 */     this.calendar.add(itemType, (int)(whichLabel * this.stepSize));
/*  977 */     return this.calendar.getTime().getTime();
/*      */   }
/*      */ 
/*      */   public int getScalingType()
/*      */   {
/*  984 */     return this.scalingType;
/*      */   }
/*      */ 
/*      */   protected String getSecondLabel(int whichLabel)
/*      */   {
/*  991 */     if (this.dateFormat == null) {
/*      */       try {
/*  993 */         this.dateFormat = java.text.DateFormat.getTimeInstance(2, this.globals.locale);
/*      */       } catch (MissingResourceException e) {
/*  995 */         System.out.println("locale is not available, using default locale instead");
/*  996 */         this.dateFormat = java.text.DateFormat.getTimeInstance();
/*      */       }
/*  998 */       if (this.timeZone != null) {
/*  999 */         this.dateFormat.setTimeZone(this.timeZone);
/*      */       }
/*      */     }
/* 1002 */     this.tmpDate.setTime(()this.axisStart);
/* 1003 */     this.calendar.setTime(this.tmpDate);
/* 1004 */     this.calendar.add(14, (int)(whichLabel * this.stepSize));
/* 1005 */     this.tmpDate = this.calendar.getTime();
/* 1006 */     if ((this.calendar.get(14) == 0) && 
/* 1007 */       (this.calendar.get(13) == 0) && 
/* 1008 */       (this.calendar.get(12) == 0) && 
/* 1009 */       (this.calendar.get(10) == 0)) {
/* 1010 */       if (this.secondaryUserDateFormat == null) {
/* 1011 */         java.text.DateFormat tdf = null;
/*      */         try {
/* 1013 */           tdf = java.text.DateFormat.getDateInstance(3, this.globals.locale);
/*      */         } catch (MissingResourceException e) {
/* 1015 */           System.out.println("locale is not available, using default locale instead");
/* 1016 */           tdf = java.text.DateFormat.getDateInstance(3);
/*      */         }
/* 1018 */         if (this.timeZone != null)
/* 1019 */           tdf.setTimeZone(this.timeZone);
/* 1020 */         return tdf.format(this.tmpDate);
/*      */       }
/*      */ 
/* 1023 */       return this.secondaryUserDateFormat.format(this.tmpDate);
/*      */     }
/* 1025 */     return jatools.formatter.DateFormat.format(this.tmpDate, this.pattern);
/*      */   }
/*      */ 
/*      */   long getSIncrement(long diff, long magnitude)
/*      */   {
/* 1034 */     if (diff <= 5L)
/* 1035 */       return 1L;
/* 1036 */     long increment = diff / 4L;
/* 1037 */     if (increment <= 5L)
/* 1038 */       while (increment % 2L > 0L) increment += 1L;
/* 1039 */     else if (increment <= 20L)
/* 1040 */       while (increment % 10L > 0L) increment += 1L;
/*      */     else
/* 1042 */       while (increment % 30L > 0L) increment += 1L;
/* 1043 */     return increment;
/*      */   }
/*      */ 
/*      */   private int getSpan(int itemType, Date fromDate, Date toDate)
/*      */   {
/* 1053 */     Calendar fromCalendar = this.calendar;
/* 1054 */     Calendar toCalendar = this.tmpCalendar;
/* 1055 */     fromCalendar.setTime(fromDate);
/* 1056 */     toCalendar.setTime(toDate);
/* 1057 */     int numItems = 0;
/* 1058 */     while (fromCalendar.before(toCalendar)) {
/* 1059 */       fromCalendar.add(itemType, 1);
/* 1060 */       numItems++;
/*      */     }
/* 1062 */     return numItems;
/*      */   }
/*      */ 
/*      */   protected String getWeekLabel(int whichLabel)
/*      */   {
/* 1069 */     if (this.dateFormat == null) {
/*      */       try {
/* 1071 */         this.dateFormat = java.text.DateFormat.getDateInstance(3, this.globals.locale);
/*      */       } catch (MissingResourceException e) {
/* 1073 */         System.out.println("locale is not available, using default locale instead");
/* 1074 */         this.dateFormat = java.text.DateFormat.getDateInstance(3);
/*      */       }
/* 1076 */       if (this.timeZone != null) {
/* 1077 */         this.dateFormat.setTimeZone(this.timeZone);
/*      */       }
/*      */     }
/* 1080 */     this.tmpDate.setTime(()this.axisStart);
/* 1081 */     this.calendar.setTime(this.tmpDate);
/* 1082 */     this.calendar.add(3, (int)(whichLabel * this.stepSize));
/* 1083 */     this.tmpDate = this.calendar.getTime();
/* 1084 */     return jatools.formatter.DateFormat.format(this.tmpDate, this.pattern);
/*      */   }
/*      */ 
/*      */   long getWIncrement(long diff) {
/* 1088 */     if (diff <= 1L)
/* 1089 */       return 1L;
/* 1090 */     if (diff <= 2L)
/* 1091 */       return 2L;
/* 1092 */     if (diff <= 4L) {
/* 1093 */       return 3L;
/*      */     }
/*      */ 
/* 1096 */     return diff / 5L;
/*      */   }
/*      */ 
/*      */   protected String getYearLabel(int whichLabel)
/*      */   {
/* 1104 */     if (this.dateFormat == null) {
/*      */       try {
/* 1106 */         this.dateFormat = java.text.DateFormat.getDateInstance(3, this.globals.locale);
/*      */       } catch (MissingResourceException e) {
/* 1108 */         System.out.println("locale is not available, using default locale instead");
/* 1109 */         this.dateFormat = java.text.DateFormat.getDateInstance(3);
/*      */       }
/* 1111 */       if (this.timeZone != null) {
/* 1112 */         this.dateFormat.setTimeZone(this.timeZone);
/*      */       }
/*      */     }
/* 1115 */     this.tmpDate.setTime(()this.axisStart);
/* 1116 */     this.calendar.setTime(this.tmpDate);
/* 1117 */     this.calendar.add(1, (int)(whichLabel * this.stepSize));
/* 1118 */     this.tmpDate = this.calendar.getTime();
/* 1119 */     return jatools.formatter.DateFormat.format(this.tmpDate, this.pattern);
/*      */   }
/*      */ 
/*      */   public synchronized void scale()
/*      */   {
/* 1130 */     int nmsets = datasetsInUse();
/* 1131 */     if ((nmsets == 0) && ((this.userAxisStart == null) || (this.userAxisEnd == null))) {
/* 1132 */       return;
/*      */     }
/* 1134 */     long hi = ()getMaxValsFromData(nmsets);
/* 1135 */     long lo = ()getMinValsFromData(nmsets);
/* 1136 */     if (hi - lo < 10000L)
/* 1137 */       hi = lo + 86400000L;
/* 1138 */     long diff = hi - lo;
/* 1139 */     this.startDate.setTime(lo);
/* 1140 */     this.endDate.setTime(hi);
/*      */ 
/* 1146 */     if (this.userScalingType != null) {
/* 1147 */       this.scalingType = this.userScalingType.intValue();
/* 1148 */       switch (this.scalingType) { case 1:
/* 1149 */         doSecondScale(); break;
/*      */       case 2:
/* 1150 */         doMinuteScale(); break;
/*      */       case 3:
/* 1151 */         doHourScale(); break;
/*      */       case 4:
/* 1152 */         doDayScale(); break;
/*      */       case 5:
/* 1153 */         doWeekScale(); break;
/*      */       case 6:
/* 1154 */         doMonthScale(); break;
/*      */       case 7:
/* 1155 */         doYearScale();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1164 */     if (diff < 180000L) {
/* 1165 */       doSecondScale();
/* 1166 */       this.scalingType = 1;
/*      */     }
/* 1168 */     else if (diff < 14400000L) {
/* 1169 */       doMinuteScale();
/* 1170 */       this.scalingType = 2;
/*      */     }
/* 1172 */     else if (diff < 172800000L) {
/* 1173 */       doHourScale();
/* 1174 */       this.scalingType = 3;
/*      */     }
/* 1176 */     else if (diff < 2419200000L)
/*      */     {
/* 1182 */       doDayScale();
/* 1183 */       this.scalingType = 4;
/*      */     }
/* 1185 */     else if (diff < 10368000000L) {
/* 1186 */       doWeekScale();
/* 1187 */       this.scalingType = 5;
/*      */     }
/* 1189 */     else if (diff < 126144000000L) {
/* 1190 */       doMonthScale();
/* 1191 */       this.scalingType = 6;
/*      */     }
/*      */     else {
/* 1194 */       doYearScale();
/* 1195 */       this.scalingType = 7;
/*      */     }
/*      */ 
/* 1198 */     this.numMajTicks = this.numLabels;
/* 1199 */     this.numGrids = this.numLabels;
/* 1200 */     this.numMinTicks = (2 * this.numLabels);
/* 1201 */     if (this.barScaling)
/* 1202 */       this.numLabels += 1;
/*      */   }
/*      */ 
/*      */   public void setAxisEnd(double num)
/*      */   {
/* 1217 */     super.setAxisEnd(num);
/* 1218 */     this.endDate.setTime(()num);
/*      */   }
/*      */ 
/*      */   public void setAxisStart(double num)
/*      */   {
/* 1226 */     super.setAxisStart(num);
/* 1227 */     this.startDate.setTime(()num);
/*      */   }
/*      */ 
/*      */   public void setCalendar(Calendar c)
/*      */   {
/* 1235 */     this.calendar = c;
/* 1236 */     this.tmpCalendar = ((Calendar)c.clone());
/*      */   }
/*      */ 
/*      */   public void setDateFormat(java.text.DateFormat format)
/*      */   {
/* 1244 */     this.userDateFormat = format;
/*      */   }
/*      */ 
/*      */   public void setLabelFormat(Format format)
/*      */   {
/* 1259 */     if ((format == null) || ((format instanceof java.text.DateFormat)))
/* 1260 */       this.userDateFormat = ((java.text.DateFormat)format);
/*      */     else
/* 1262 */       super.setLabelFormat(format);
/*      */   }
/*      */ 
/*      */   public void setLogScaling(boolean yesNo)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void setScalingType(int typ)
/*      */   {
/* 1278 */     if (typ == -1) {
/* 1279 */       this.userScalingType = null;
/* 1280 */       return;
/*      */     }
/* 1282 */     this.userScalingType = new Integer(typ);
/* 1283 */     this.scalingType = typ;
/*      */   }
/*      */ 
/*      */   public void setStepSize(int step)
/*      */   {
/* 1292 */     if (step == -1) {
/* 1293 */       this.userStepSize = null;
/* 1294 */       return;
/*      */     }
/* 1296 */     this.userStepSize = new Integer(step);
/*      */   }
/*      */ 
/*      */   public int getStepSize()
/*      */   {
/* 1304 */     if (this.userStepSize == null) {
/* 1305 */       return -1;
/*      */     }
/* 1307 */     return this.userStepSize.intValue();
/*      */   }
/*      */ 
/*      */   public void setSecondaryDateFormat(java.text.DateFormat format)
/*      */   {
/* 1320 */     this.secondaryUserDateFormat = format;
/*      */   }
/*      */ 
/*      */   protected int whereOnAxis(int whichStep, int whichElement)
/*      */   {
/* 1329 */     double dataPosition = 0.0D;
/*      */ 
/* 1336 */     if ((!this.autoScale) && 
/* 1337 */       (this.stepSize == -1L))
/*      */     {
/* 1340 */       if (this.scalingType == 7) {
/* 1341 */         this.stepSize = getSpan(1, this.startDate, this.endDate);
/*      */       }
/* 1343 */       else if (this.scalingType == 6)
/*      */       {
/* 1345 */         this.stepSize = getMonthSpan(this.startDate, this.endDate);
/*      */       }
/* 1347 */       else if (this.scalingType == 5) {
/* 1348 */         this.stepSize = getSpan(5, this.startDate, this.endDate);
/* 1349 */         this.stepSize /= this.calendar.getMaximum(7);
/*      */       }
/* 1351 */       else if (this.scalingType == 4) {
/* 1352 */         this.stepSize = getSpan(5, this.startDate, this.endDate);
/*      */       }
/* 1354 */       else if (this.scalingType == 3)
/*      */       {
/* 1356 */         int hourSpan = 0;
/* 1357 */         long rawTime = this.startDate.getTime();
/* 1358 */         while (rawTime < this.endDate.getTime()) {
/* 1359 */           hourSpan++;
/* 1360 */           rawTime += 3600000L;
/*      */         }
/* 1362 */         this.stepSize = hourSpan;
/*      */       }
/* 1364 */       else if (this.scalingType == 2) {
/* 1365 */         this.stepSize = getSpan(12, this.startDate, this.endDate);
/*      */       } else {
/* 1367 */         this.stepSize = (()(this.axisEnd - this.axisStart));
/*      */       }
/* 1369 */       if (this.scalingType != 1) {
/* 1370 */         switch (whichElement) {
/*      */         case 2:
/* 1372 */           if (this.stepSize > this.numGrids) {
/* 1373 */             while (this.stepSize % this.numGrids != 0L) this.numGrids += 1;
/*      */           }
/*      */           else {
/* 1376 */             while (this.stepSize % this.numGrids != 0L) this.numGrids -= 1;
/*      */           }
/* 1378 */           break;
/*      */         case 1:
/* 1380 */           if (this.stepSize > this.numLabels) {
/* 1381 */             while (this.stepSize % this.numLabels != 0L) this.numLabels += 1;
/*      */           }
/*      */           else {
/* 1384 */             while (this.stepSize % this.numLabels != 0L) this.numLabels -= 1;
/*      */           }
/* 1386 */           break;
/*      */         case 4:
/* 1388 */           if (this.stepSize > this.numMajTicks) {
/* 1389 */             while (this.stepSize % this.numMajTicks != 0L) this.numMajTicks += 1;
/*      */           }
/*      */           else {
/* 1392 */             while (this.stepSize % this.numMajTicks != 0L) this.numMajTicks -= 1;
/*      */           }
/* 1394 */           break;
/*      */         case 3:
/* 1396 */           if (this.stepSize > this.numMinTicks) {
/* 1397 */             while (this.stepSize % this.numMinTicks != 0L) this.numMinTicks += 1;
/*      */           }
/*      */           else {
/* 1400 */             while (this.stepSize % this.numMinTicks != 0L) this.numMinTicks -= 1;
/*      */           }
/*      */           break;
/*      */         }
/*      */       }
/* 1405 */       if (this.userStepSize != null)
/* 1406 */         this.stepSize = this.userStepSize.intValue();
/*      */       else {
/* 1408 */         switch (whichElement) { case 2:
/* 1409 */           this.stepSize /= this.numGrids; break;
/*      */         case 1:
/* 1410 */           this.stepSize /= this.numLabels; break;
/*      */         case 4:
/* 1411 */           this.stepSize /= this.numMajTicks; break;
/*      */         case 3:
/* 1412 */           this.stepSize /= this.numMinTicks;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1417 */       if (this.stepSize == 0L) this.stepSize = 1L;
/*      */ 
/*      */     }
/*      */ 
/* 1423 */     if (this.scalingType == 1) {
/* 1424 */       if ((this.side == 0) || (this.side == 2)) {
/* 1425 */         return this.startPoint.x + (int)(this.increment * whichStep);
/*      */       }
/* 1427 */       return this.startPoint.y + (int)(this.increment * whichStep);
/*      */     }
/*      */ 
/* 1432 */     switch (this.scalingType) {
/*      */     case 7:
/* 1434 */       dataPosition = getPosition(whichStep, 1) - this.axisStart; break;
/*      */     case 6:
/* 1436 */       dataPosition = getPosition(whichStep, 2) - this.axisStart; break;
/*      */     case 5:
/* 1438 */       dataPosition = getPosition(whichStep * this.calendar.getMaximum(7), 5) - this.axisStart; break;
/*      */     case 4:
/* 1440 */       dataPosition = getPosition(whichStep, 5) - this.axisStart; break;
/*      */     case 3:
/* 1442 */       dataPosition = getPosition(whichStep, 10) - this.axisStart; break;
/*      */     case 2:
/* 1444 */       dataPosition = getPosition(whichStep, 12) - this.axisStart;
/*      */     }
/*      */ 
/* 1447 */     double howFar = dataPosition / (this.axisEnd - this.axisStart);
/*      */     int where;
/* 1450 */     if ((this.side == 0) || (this.side == 2)) {
/* 1451 */       int where = this.startPoint.x + (int)(howFar * (this.endPoint.x - this.startPoint.x));
/* 1452 */       if (where > this.endPoint.x)
/* 1453 */         where = 32767;
/*      */     }
/*      */     else {
/* 1456 */       where = this.startPoint.y + (int)(howFar * (this.endPoint.y - this.startPoint.y));
/* 1457 */       if (where > this.endPoint.y)
/* 1458 */         where = 32767;
/*      */     }
/* 1460 */     return where;
/*      */   }
/*      */ 
/*      */   public TimeZone getTimeZone()
/*      */   {
/* 1471 */     return this.timeZone;
/*      */   }
/*      */ 
/*      */   public void setTimeZone(TimeZone newTimeZone)
/*      */   {
/* 1480 */     this.timeZone = newTimeZone;
/* 1481 */     this.calendar.setTimeZone(newTimeZone);
/* 1482 */     this.tmpCalendar.setTimeZone(newTimeZone);
/* 1483 */     if (this.dateFormat != null)
/* 1484 */       this.dateFormat.setTimeZone(newTimeZone);
/* 1485 */     if (this.userDateFormat != null)
/* 1486 */       this.userDateFormat.setTimeZone(newTimeZone);
/* 1487 */     if (this.secondaryUserDateFormat != null)
/* 1488 */       this.secondaryUserDateFormat.setTimeZone(newTimeZone);
/*      */   }
/*      */ 
/*      */   private int getMonthSpan(Date fromDate, Date toDate)
/*      */   {
/* 1500 */     Calendar fromCalendar = this.calendar;
/* 1501 */     Calendar toCalendar = this.tmpCalendar;
/* 1502 */     fromCalendar.setTime(fromDate);
/* 1503 */     int startDay = fromCalendar.get(5);
/* 1504 */     toCalendar.setTime(toDate);
/* 1505 */     int numMonths = 0;
/* 1506 */     while (fromCalendar.before(toCalendar)) {
/* 1507 */       int thisMonth = fromCalendar.get(2);
/* 1508 */       while (thisMonth == fromCalendar.get(2))
/* 1509 */         fromCalendar.add(5, 1);
/* 1510 */       numMonths++;
/*      */     }
/* 1512 */     if (startDay == 1) {
/* 1513 */       return numMonths;
/*      */     }
/* 1515 */     return numMonths - 1;
/*      */   }
/*      */ 
/*      */   public void addLabels(Date[] datasetLabel) {
/* 1519 */     SimpleDateFormat format = new SimpleDateFormat();
/* 1520 */     format.applyPattern(this.pattern);
/* 1521 */     int len = datasetLabel.length;
/* 1522 */     String[] labels = new String[len];
/* 1523 */     for (int i = 0; i < datasetLabel.length; i++) {
/* 1524 */       Date date = datasetLabel[i];
/* 1525 */       labels[i] = format.format(date);
/*      */     }
/* 1527 */     super.addLabels(labels);
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DateAxis
 * JD-Core Version:    0.6.2
 */