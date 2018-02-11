/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.io.PrintStream;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.MissingResourceException;
/*     */ 
/*     */ public class SimpleDateAxis extends DateAxis
/*     */ {
/*     */   static final long oneMinute = 60000L;
/*     */   static final long oneHour = 3600000L;
/*     */   static final long oneDay = 86400000L;
/*     */   static final long oneWeek = 604800000L;
/*     */   static final long oneMonth = 2592000000L;
/*     */   static final long oneYear = 31536000000L;
/*     */ 
/*     */   public SimpleDateAxis()
/*     */   {
/*  46 */     this.isXAxis = true;
/*  47 */     this.side = 0;
/*     */   }
/*     */ 
/*     */   public SimpleDateAxis(Dataset[] datasets, boolean yesNo, Plotarea plotarea)
/*     */   {
/*  61 */     super(datasets, yesNo, plotarea);
/*     */   }
/*     */ 
/*     */   protected void doDayScale()
/*     */   {
/*  72 */     long max = this.endDate.getTime();
/*     */ 
/*  74 */     this.endDate.setHours(0);
/*  75 */     this.endDate.setMinutes(0);
/*  76 */     this.endDate.setSeconds(0);
/*  77 */     long compare = this.endDate.getTime();
/*  78 */     if (max - compare > 0L) {
/*  79 */       max = this.endDate.getTime();
/*  80 */       this.endDate.setTime(max + 86400000L);
/*     */     }
/*  82 */     this.startDate.setHours(0);
/*  83 */     this.startDate.setMinutes(0);
/*  84 */     this.startDate.setSeconds(0);
/*  85 */     long min = this.startDate.getTime();
/*  86 */     max = this.endDate.getTime();
/*  87 */     long diff = max - min;
/*  88 */     long normalizedIncrement = getDayIncrement(diff);
/*     */ 
/*  92 */     int tmp = (int)((this.endDate.getTime() - this.startDate.getTime()) / 86400000L);
/*  93 */     while (tmp % normalizedIncrement != 0L) tmp++;
/*  94 */     this.endDate.setTime(this.startDate.getTime() + tmp * 86400000L);
/*  95 */     this.axisStart = this.startDate.getTime();
/*  96 */     this.axisEnd = this.endDate.getTime();
/*  97 */     this.stepSize = (86400000L * normalizedIncrement);
/*  98 */     this.numLabels = ((int)((this.axisEnd - this.axisStart) / this.stepSize));
/*  99 */     System.out.println("startDate is " + this.startDate.toString());
/* 100 */     System.out.println("endDate is " + this.endDate.toString());
/*     */   }
/*     */ 
/*     */   protected void doHourScale()
/*     */   {
/* 111 */     int tmp = this.endDate.getMinutes();
/* 112 */     long max = this.endDate.getTime();
/* 113 */     max /= 3600000L;
/* 114 */     max *= 3600000L;
/* 115 */     if (tmp > 0)
/* 116 */       this.endDate.setTime(max + 3600000L);
/*     */     else
/* 118 */       this.endDate.setTime(max);
/* 119 */     long min = this.startDate.getTime();
/* 120 */     min /= 3600000L;
/* 121 */     min *= 3600000L;
/* 122 */     this.startDate.setTime(min);
/* 123 */     long diff = max - min;
/* 124 */     long normalizedIncrement = getHourIncrement(diff);
/* 125 */     tmp = this.startDate.getHours();
/* 126 */     while (tmp % normalizedIncrement != 0L) tmp--;
/* 127 */     this.startDate.setHours(tmp);
/*     */ 
/* 133 */     max = this.startDate.getTime();
/* 134 */     while (max < this.endDate.getTime()) {
/* 135 */       max += normalizedIncrement * 3600000L;
/*     */     }
/* 137 */     this.endDate.setTime(max);
/*     */ 
/* 150 */     this.stepSize = (normalizedIncrement * 3600000L);
/* 151 */     this.axisStart = this.startDate.getTime();
/* 152 */     this.axisEnd = this.endDate.getTime();
/* 153 */     this.numLabels = ((int)((this.axisEnd - this.axisStart) / this.stepSize));
/*     */   }
/*     */ 
/*     */   protected void doMinuteScale()
/*     */   {
/* 163 */     long max = this.endDate.getTime();
/* 164 */     long min = this.startDate.getTime();
/* 165 */     long diff = max - min;
/* 166 */     long normalizedIncrement = getMinuteIncrement(diff);
/* 167 */     min = min / 1000L * 1000L;
/* 168 */     while (min % normalizedIncrement != 0L) min -= 1000L;
/* 169 */     max = max / 1000L * 1000L;
/* 170 */     while (max % normalizedIncrement != 0L) max += 1000L;
/* 171 */     this.stepSize = normalizedIncrement;
/* 172 */     this.axisStart = min;
/* 173 */     this.axisEnd = max;
/*     */ 
/* 178 */     this.numLabels = ((int)((this.axisEnd - this.axisStart) / this.stepSize));
/*     */   }
/*     */ 
/*     */   protected void doMonthScale()
/*     */   {
/* 189 */     if (this.endDate.getDate() > 1)
/* 190 */       this.endDate.setMonth(this.endDate.getMonth() + 1);
/* 191 */     this.endDate.setDate(1);
/* 192 */     this.endDate.setHours(0);
/* 193 */     this.endDate.setMinutes(0);
/* 194 */     this.endDate.setSeconds(0);
/*     */ 
/* 196 */     this.startDate.setDate(1);
/* 197 */     this.startDate.setHours(0);
/* 198 */     this.startDate.setMinutes(0);
/* 199 */     this.startDate.setSeconds(0);
/*     */ 
/* 201 */     int min = this.startDate.getYear() * 12 + (this.startDate.getMonth() + 1);
/* 202 */     int max = this.endDate.getYear() * 12 + (this.endDate.getMonth() + 1);
/* 203 */     int diff = max - min;
/* 204 */     int normalizedIncrement = diff / 5;
/*     */ 
/* 207 */     if (normalizedIncrement == 0)
/* 208 */       normalizedIncrement = 1;
/* 209 */     while (diff % normalizedIncrement != 0) {
/* 210 */       diff++;
/* 211 */       this.endDate.setMonth(this.endDate.getMonth() + 1);
/*     */     }
/*     */ 
/* 214 */     this.axisStart = this.startDate.getTime();
/* 215 */     this.axisEnd = this.endDate.getTime();
/* 216 */     this.stepSize = normalizedIncrement;
/* 217 */     this.numLabels = (diff / (int)this.stepSize);
/*     */   }
/*     */ 
/*     */   protected void doSecondScale()
/*     */   {
/* 231 */     long max = this.endDate.getTime();
/* 232 */     long min = this.startDate.getTime();
/* 233 */     long diff = max - min;
/* 234 */     long magnitude = getMagnitude(diff);
/*     */     long normalizedIncrement;
/* 235 */     if (magnitude < 0L) {
/* 236 */       long normalizedIncrement = getMsIncrement(diff, magnitude);
/* 237 */       while (min % normalizedIncrement != 0L) min -= 1L;
/* 238 */       while (max % normalizedIncrement != 0L) max += 1L; 
/*     */     }
/*     */     else
/*     */     {
/* 241 */       normalizedIncrement = getSIncrement(diff, magnitude);
/* 242 */       while (min % normalizedIncrement != 0L) min -= 1L;
/* 243 */       while (max % normalizedIncrement != 0L) max += 1L;
/*     */     }
/* 245 */     this.stepSize = normalizedIncrement;
/* 246 */     this.axisStart = min;
/* 247 */     this.axisEnd = max;
/* 248 */     this.numLabels = ((int)((this.axisEnd - this.axisStart) / this.stepSize));
/*     */   }
/*     */ 
/*     */   protected void doWeekScale()
/*     */   {
/* 259 */     int tmp = this.endDate.getHours();
/* 260 */     long max = this.endDate.getTime();
/*     */ 
/* 262 */     this.endDate.setHours(0);
/* 263 */     this.endDate.setMinutes(0);
/* 264 */     this.endDate.setSeconds(0);
/* 265 */     if (tmp > 0) {
/* 266 */       max = this.endDate.getTime();
/* 267 */       this.endDate.setTime(max + 86400000L);
/*     */     }
/* 269 */     this.startDate.setHours(0);
/* 270 */     this.startDate.setMinutes(0);
/* 271 */     this.startDate.setSeconds(0);
/*     */ 
/* 274 */     tmp = this.startDate.getDay();
/* 275 */     while (this.endDate.getDay() != tmp) {
/* 276 */       this.endDate.setDate(this.endDate.getDate() + 1);
/*     */     }
/* 278 */     long min = this.startDate.getTime();
/* 279 */     max = this.endDate.getTime();
/* 280 */     long diff = max - min;
/*     */ 
/* 283 */     this.stepSize = getWIncrement(diff);
/*     */ 
/* 286 */     while (diff % this.stepSize > 86400000L) {
/* 287 */       max += 86400000L;
/* 288 */       diff = max - min;
/*     */     }
/* 290 */     this.endDate.setTime(max);
/*     */ 
/* 292 */     this.axisStart = this.startDate.getTime();
/* 293 */     this.axisEnd = this.endDate.getTime();
/* 294 */     this.numLabels = ((int)((this.axisEnd - this.axisStart) / this.stepSize));
/*     */   }
/*     */ 
/*     */   protected void doYearScale()
/*     */   {
/* 312 */     if (this.endDate.getMonth() > 0)
/* 313 */       this.endDate.setYear(this.endDate.getYear() + 1);
/* 314 */     this.endDate.setMonth(0);
/* 315 */     this.endDate.setDate(1);
/* 316 */     this.endDate.setHours(0);
/* 317 */     this.endDate.setMinutes(0);
/* 318 */     this.endDate.setSeconds(0);
/*     */ 
/* 320 */     this.startDate.setMonth(0);
/* 321 */     this.startDate.setDate(1);
/* 322 */     this.startDate.setHours(0);
/* 323 */     this.startDate.setMinutes(0);
/* 324 */     this.startDate.setSeconds(0);
/*     */ 
/* 326 */     int min = this.startDate.getYear();
/* 327 */     int max = this.endDate.getYear();
/* 328 */     int diff = max - min;
/* 329 */     int normalizedIncrement = diff / 5;
/*     */ 
/* 332 */     if (normalizedIncrement == 0)
/* 333 */       normalizedIncrement = 1;
/* 334 */     while (diff % normalizedIncrement != 0) {
/* 335 */       diff++;
/* 336 */       this.endDate.setYear(this.endDate.getYear() + 1);
/*     */     }
/*     */ 
/* 339 */     this.axisStart = this.startDate.getTime();
/* 340 */     this.axisEnd = this.endDate.getTime();
/* 341 */     this.stepSize = normalizedIncrement;
/* 342 */     this.numLabels = ((this.endDate.getYear() - this.startDate.getYear()) / normalizedIncrement);
/*     */   }
/*     */ 
/*     */   long getDayIncrement(long diff)
/*     */   {
/* 348 */     if (diff <= 604800000L)
/* 349 */       return 1L;
/* 350 */     if (diff <= 1209600000L)
/* 351 */       return 2L;
/* 352 */     if (diff <= 2419200000L) {
/* 353 */       return 3L;
/*     */     }
/*     */ 
/* 356 */     long increment = diff / 5L;
/* 357 */     increment /= 86400000L;
/* 358 */     increment *= 86400000L;
/* 359 */     while (increment % 604800000L > 0L) increment += 86400000L;
/*     */ 
/* 361 */     return increment / 86400000L;
/*     */   }
/*     */ 
/*     */   protected String getDayLabel(int whichLabel)
/*     */   {
/* 368 */     if (this.dateFormat == null) {
/*     */       try {
/* 370 */         this.dateFormat = DateFormat.getDateInstance(3, this.globals.locale);
/*     */       } catch (MissingResourceException e) {
/* 372 */         System.out.println("locale is not available, using default locale instead");
/* 373 */         this.dateFormat = DateFormat.getDateInstance(3);
/*     */       }
/* 375 */       if (this.timeZone != null)
/* 376 */         this.dateFormat.setTimeZone(this.timeZone);
/*     */     }
/* 378 */     this.tmpDate.setTime(this.axisStart + whichLabel * this.stepSize);
/* 379 */     return this.dateFormat.format(this.tmpDate);
/*     */   }
/*     */ 
/*     */   long getHourIncrement(long diff)
/*     */   {
/* 386 */     if (diff <= 10800000L)
/* 387 */       return 1L;
/* 388 */     long increment = diff / 5L / 60000L;
/* 389 */     if (increment <= 60L)
/* 390 */       return 2L;
/* 391 */     if (increment <= 180L) {
/* 392 */       return 3L;
/*     */     }
/* 394 */     while (increment % 360L > 0L) increment += 1L;
/* 395 */     return increment / 60L;
/*     */   }
/*     */ 
/*     */   protected String getHourLabel(int whichLabel)
/*     */   {
/* 402 */     if (this.dateFormat == null) {
/*     */       try {
/* 404 */         this.dateFormat = DateFormat.getTimeInstance(3, this.globals.locale);
/*     */       } catch (MissingResourceException e) {
/* 406 */         System.out.println("locale is not available, using default locale instead");
/* 407 */         this.dateFormat = DateFormat.getTimeInstance(3);
/*     */       }
/* 409 */       if (this.timeZone != null) {
/* 410 */         this.dateFormat.setTimeZone(this.timeZone);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 416 */     this.tmpDate.setTime(()this.axisStart + whichLabel * this.stepSize);
/* 417 */     String dayString = null;
/* 418 */     int hours = this.tmpDate.getHours();
/* 419 */     if (hours == 0) {
/* 420 */       if (this.secondaryUserDateFormat == null) {
/* 421 */         DateFormat tdf = null;
/*     */         try {
/* 423 */           tdf = DateFormat.getDateInstance(3, this.globals.locale);
/*     */         } catch (MissingResourceException e) {
/* 425 */           System.out.println("locale is not available, using default locale instead");
/* 426 */           tdf = DateFormat.getDateInstance(3);
/*     */         }
/*     */ 
/* 429 */         if (this.timeZone != null)
/* 430 */           tdf.setTimeZone(this.timeZone);
/* 431 */         return tdf.format(this.tmpDate);
/*     */       }
/*     */ 
/* 434 */       return this.secondaryUserDateFormat.format(this.tmpDate);
/*     */     }
/*     */ 
/* 437 */     return this.dateFormat.format(this.tmpDate);
/*     */   }
/*     */ 
/*     */   long getMagnitude(long num)
/*     */   {
/* 445 */     if (num > 2000L) {
/* 446 */       long comparison = 15000L;
/* 447 */       int i = 1;
/*     */       while (true) {
/* 449 */         if (comparison > num)
/* 450 */           return i - 1;
/* 451 */         comparison += 15000L;
/* 452 */         i++;
/*     */       }
/*     */     }
/* 455 */     if (num < 10L)
/* 456 */       return -1L;
/* 457 */     if (num < 100L) {
/* 458 */       return -2L;
/*     */     }
/*     */ 
/* 464 */     return -3L;
/*     */   }
/*     */ 
/*     */   long getMinuteIncrement(long diff)
/*     */   {
/* 472 */     if (diff <= 300000L)
/* 473 */       return 60000L;
/* 474 */     long increment = diff / 4L;
/* 475 */     increment /= 1000L;
/* 476 */     increment *= 1000L;
/* 477 */     if (increment <= 300000L) {
/* 478 */       while (increment % 120000L > 0L) increment += 1000L;
/*     */     }
/* 480 */     else if (increment <= 300000L)
/* 481 */       while (increment % 300000L > 0L) increment += 1000L;
/*     */     else
/* 483 */       while (increment % 900000L > 0L) increment += 1000L;
/* 484 */     return increment;
/*     */   }
/*     */ 
/*     */   protected String getMinuteLabel(int whichLabel)
/*     */   {
/* 494 */     this.tmpDate.setTime(()this.axisStart + whichLabel * this.stepSize);
/*     */ 
/* 499 */     if (this.dateFormat == null) {
/*     */       try {
/* 501 */         this.dateFormat = DateFormat.getTimeInstance(3, this.globals.locale);
/*     */       } catch (MissingResourceException e) {
/* 503 */         System.out.println("locale is not available, using default locale instead");
/* 504 */         this.dateFormat = DateFormat.getTimeInstance(3);
/*     */       }
/*     */ 
/* 507 */       if (this.timeZone != null) {
/* 508 */         this.dateFormat.setTimeZone(this.timeZone);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 521 */     return this.dateFormat.format(this.tmpDate);
/*     */   }
/*     */ 
/*     */   protected String getMonthLabel(int whichLabel)
/*     */   {
/* 529 */     if (this.dateFormat == null) {
/*     */       try {
/* 531 */         this.dateFormat = DateFormat.getDateInstance(3, this.globals.locale);
/*     */       } catch (MissingResourceException e) {
/* 533 */         System.out.println("locale is not available, using default locale instead");
/* 534 */         this.dateFormat = DateFormat.getDateInstance(3);
/*     */       }
/*     */ 
/* 537 */       if (this.timeZone != null) {
/* 538 */         this.dateFormat.setTimeZone(this.timeZone);
/*     */       }
/*     */     }
/*     */ 
/* 542 */     this.tmpDate.setTime(()this.axisStart);
/* 543 */     int curMonths = this.tmpDate.getMonth();
/* 544 */     int nYears = (int)(whichLabel * this.stepSize + curMonths) / 12;
/* 545 */     int nMonths = (int)(whichLabel * this.stepSize + curMonths) % 12;
/* 546 */     this.tmpDate.setYear(this.tmpDate.getYear() + nYears);
/* 547 */     this.tmpDate.setMonth(nMonths);
/* 548 */     return this.dateFormat.format(this.tmpDate);
/*     */   }
/*     */ 
/*     */   long getMonthPosition(int whichLabel) {
/* 552 */     this.tmpDate.setTime(()this.axisStart);
/* 553 */     this.tmpDate.setMonth(this.tmpDate.getMonth() + (int)(whichLabel * this.stepSize));
/* 554 */     return this.tmpDate.getTime();
/*     */   }
/*     */ 
/*     */   long getMsIncrement(long diff, long magnitude)
/*     */   {
/*     */     double boundary;
/*     */     double boundary;
/* 563 */     if (magnitude == -1L) {
/* 564 */       boundary = 1.0D;
/*     */     }
/*     */     else
/*     */     {
/*     */       double boundary;
/* 565 */       if (magnitude == -2L)
/* 566 */         boundary = 10.0D;
/* 567 */       else boundary = 100.0D;
/*     */     }
/* 569 */     double magic = diff / (5.0D * boundary);
/*     */     double increment;
/*     */     double increment;
/* 570 */     if ((magic > 0.0D) && (magic < 0.5D)) {
/* 571 */       increment = boundary * 0.5D;
/*     */     }
/*     */     else
/*     */     {
/*     */       double increment;
/* 572 */       if ((magic > 0.5D) && (magic < 1.0D)) {
/* 573 */         increment = boundary;
/*     */       }
/*     */       else
/*     */       {
/*     */         double increment;
/* 574 */         if ((magic > 1.0D) && (magic < 9.0D))
/* 575 */           increment = boundary * 2.0D;
/*     */         else
/* 577 */           increment = boundary; 
/*     */       }
/*     */     }
/* 578 */     return ()increment;
/*     */   }
/*     */ 
/*     */   protected String getSecondLabel(int whichLabel)
/*     */   {
/* 587 */     this.tmpDate.setTime(()this.axisStart + whichLabel * this.stepSize);
/*     */ 
/* 589 */     if (this.dateFormat == null) {
/*     */       try {
/* 591 */         this.dateFormat = DateFormat.getTimeInstance(2, this.globals.locale);
/*     */       } catch (MissingResourceException e) {
/* 593 */         System.out.println("locale is not available, using default locale instead");
/* 594 */         this.dateFormat = DateFormat.getTimeInstance();
/*     */       }
/* 596 */       if (this.timeZone != null) {
/* 597 */         this.dateFormat.setTimeZone(this.timeZone);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 602 */     if (this.stepSize < 1000L) {
/* 603 */       long ms = this.tmpDate.getTime();
/* 604 */       int secs = this.tmpDate.getSeconds();
/* 605 */       if (secs == 0) {
/* 606 */         if (this.secondaryUserDateFormat == null) {
/* 607 */           DateFormat tdf = null;
/*     */           try {
/* 609 */             tdf = DateFormat.getDateInstance(3, this.globals.locale);
/*     */           } catch (MissingResourceException e) {
/* 611 */             System.out.println("locale is not available, using default locale instead");
/* 612 */             tdf = DateFormat.getDateInstance(3);
/*     */           }
/* 614 */           if (this.timeZone != null)
/* 615 */             tdf.setTimeZone(this.timeZone);
/* 616 */           return tdf.format(this.tmpDate);
/*     */         }
/*     */ 
/* 619 */         return this.secondaryUserDateFormat.format(this.tmpDate);
/*     */       }
/*     */     }
/*     */ 
/* 623 */     return this.dateFormat.format(this.tmpDate);
/*     */   }
/*     */ 
/*     */   long getSIncrement(long diff, long magnitude)
/*     */   {
/* 632 */     if (diff <= 5000L)
/* 633 */       return 1000L;
/* 634 */     long increment = diff / 4L;
/* 635 */     if (increment <= 5000L)
/* 636 */       while (increment % 2000L > 0L) increment += 1L;
/*     */     else
/* 638 */       while (increment % 5000L > 0L) increment += 1L;
/* 639 */     return increment;
/*     */   }
/*     */ 
/*     */   protected String getWeekLabel(int whichLabel)
/*     */   {
/* 646 */     if (this.dateFormat == null) {
/*     */       try {
/* 648 */         this.dateFormat = DateFormat.getDateInstance(3, this.globals.locale);
/*     */       } catch (MissingResourceException e) {
/* 650 */         System.out.println("locale is not available, using default locale instead");
/* 651 */         this.dateFormat = DateFormat.getDateInstance(3);
/*     */       }
/* 653 */       if (this.timeZone != null)
/* 654 */         this.dateFormat.setTimeZone(this.timeZone);
/*     */     }
/* 656 */     this.tmpDate.setTime(()this.axisStart + whichLabel * this.stepSize);
/* 657 */     return this.dateFormat.format(this.tmpDate);
/*     */   }
/*     */ 
/*     */   long getWIncrement(long diff)
/*     */   {
/* 664 */     if (diff <= 604800000L)
/* 665 */       return 86400000L;
/* 666 */     if (diff <= 1209600000L)
/* 667 */       return 172800000L;
/* 668 */     if (diff <= 2419200000L) {
/* 669 */       return 259200000L;
/*     */     }
/*     */ 
/* 672 */     long increment = diff / 5L;
/* 673 */     increment /= 86400000L;
/* 674 */     increment *= 86400000L;
/* 675 */     while (increment % 604800000L > 0L) increment += 86400000L;
/*     */ 
/* 677 */     return increment;
/*     */   }
/*     */ 
/*     */   protected String getYearLabel(int whichLabel)
/*     */   {
/* 684 */     if (this.dateFormat == null) {
/*     */       try {
/* 686 */         this.dateFormat = DateFormat.getDateInstance(3, this.globals.locale);
/*     */       } catch (MissingResourceException e) {
/* 688 */         System.out.println("locale is not available, using default locale instead");
/* 689 */         this.dateFormat = DateFormat.getDateInstance(3);
/*     */       }
/* 691 */       if (this.timeZone != null) {
/* 692 */         this.dateFormat.setTimeZone(this.timeZone);
/*     */       }
/*     */     }
/* 695 */     this.tmpDate.setTime(()this.axisStart);
/* 696 */     this.tmpDate.setYear(this.tmpDate.getYear() + (int)(whichLabel * this.stepSize));
/* 697 */     return this.dateFormat.format(this.tmpDate);
/*     */   }
/*     */ 
/*     */   long getYearPosition(int whichLabel) {
/* 701 */     this.tmpDate.setTime(()this.axisStart);
/* 702 */     this.tmpDate.setYear(this.tmpDate.getYear() + (int)(whichLabel * this.stepSize));
/* 703 */     return this.tmpDate.getTime();
/*     */   }
/*     */ 
/*     */   protected int whereOnAxis(int whichStep, int whichElement)
/*     */   {
/* 719 */     if (!this.autoScale) {
/* 720 */       if (this.scalingType == 6)
/*     */       {
/* 722 */         this.stepSize = (this.endDate.getYear() - this.startDate.getYear());
/* 723 */         this.stepSize = (this.stepSize * 12L - this.startDate.getMonth() + this.endDate.getMonth());
/*     */       }
/* 725 */       else if (this.scalingType == 7)
/*     */       {
/* 727 */         this.stepSize = (this.endDate.getYear() - this.startDate.getYear()); } else {
/* 728 */         this.stepSize = (()(this.axisEnd - this.axisStart));
/*     */       }
/* 730 */       if (whichElement == 2)
/* 731 */         this.stepSize /= this.numGrids;
/* 732 */       if (whichElement == 1)
/* 733 */         this.stepSize /= this.numLabels;
/* 734 */       if (whichElement == 4)
/* 735 */         this.stepSize /= this.numMajTicks;
/* 736 */       if (whichElement == 3) {
/* 737 */         this.stepSize /= this.numMinTicks;
/*     */       }
/* 739 */       if (this.stepSize == 0L) this.stepSize = 1L;
/*     */ 
/*     */     }
/*     */ 
/* 744 */     if ((this.scalingType != 6) && (this.scalingType != 7)) {
/* 745 */       if ((this.side == 0) || (this.side == 2)) {
/* 746 */         return this.startPoint.x + (int)(this.increment * whichStep);
/*     */       }
/* 748 */       return this.startPoint.y + (int)(this.increment * whichStep);
/*     */     }
/*     */     double dataPosition;
/*     */     double dataPosition;
/* 753 */     if (this.scalingType == 6)
/* 754 */       dataPosition = getMonthPosition(whichStep) - this.axisStart;
/*     */     else {
/* 756 */       dataPosition = getYearPosition(whichStep) - this.axisStart;
/*     */     }
/* 758 */     double howFar = dataPosition / (this.axisEnd - this.axisStart);
/* 759 */     if ((this.side == 0) || (this.side == 2)) {
/* 760 */       return this.startPoint.x + (int)(howFar * (this.endPoint.x - this.startPoint.x));
/*     */     }
/* 762 */     return this.startPoint.y + (int)(howFar * (this.endPoint.y - this.startPoint.y));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.SimpleDateAxis
 * JD-Core Version:    0.6.2
 */