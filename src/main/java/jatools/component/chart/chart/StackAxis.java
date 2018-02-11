/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class StackAxis extends Axis
/*     */   implements AxisInterface
/*     */ {
/*  14 */   protected boolean stackValues = true;
/*     */ 
/*     */   public StackAxis()
/*     */   {
/*     */   }
/*     */ 
/*     */   public StackAxis(Dataset[] dsets, boolean xAxis, Plotarea plt)
/*     */   {
/*  29 */     super(dsets, xAxis, plt);
/*     */   }
/*     */ 
/*     */   protected double getMaxValsFromData(int nmsets)
/*     */   {
/*  39 */     if (!this.stackValues) {
/*  40 */       return super.getMaxValsFromData(nmsets);
/*     */     }
/*     */ 
/*  44 */     int length = 0;
/*     */ 
/*  46 */     double hi = (-1.0D / 0.0D);
/*     */ 
/*  48 */     if (this.userAxisEnd != null) {
/*  49 */       return this.userAxisEnd.doubleValue();
/*     */     }
/*  51 */     for (int i = 0; i < nmsets; i++) {
/*  52 */       length = Math.max(length, this.datasets[i].data.size());
/*     */     }
/*  54 */     if (!this.isXAxis)
/*  55 */       for (i = 0; i < length; i++) {
/*  56 */         double total = 0.0D;
/*  57 */         for (int j = 0; j < nmsets; j++)
/*     */           try {
/*  59 */             total += this.datasets[j].getDataElementAt(i).y;
/*     */           }
/*     */           catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/*     */           }
/*  63 */         hi = Math.max(hi, total);
/*     */       }
/*     */     else
/*  66 */       for (i = 0; i < length; i++) {
/*  67 */         double total = 0.0D;
/*  68 */         for (int j = 0; j < nmsets; j++) {
/*  69 */           total += this.datasets[j].getDataElementAt(i).x;
/*     */         }
/*  71 */         hi = Math.max(hi, total);
/*     */       }
/*  73 */     return hi;
/*     */   }
/*     */ 
/*     */   protected double getMinValsFromData(int nmsets)
/*     */   {
/*  84 */     int length = 0;
/*     */ 
/*  86 */     double low = 1.7976931348623157E+308D;
/*     */ 
/*  88 */     if (!this.stackValues) {
/*  89 */       return super.getMinValsFromData(nmsets);
/*     */     }
/*     */ 
/*  92 */     if (this.userAxisStart != null) {
/*  93 */       return this.userAxisStart.doubleValue();
/*     */     }
/*  95 */     for (int i = 0; i < nmsets; i++) {
/*  96 */       length = Math.max(length, this.datasets[i].data.size());
/*     */     }
/*  98 */     if (!this.isXAxis)
/*  99 */       for (i = 0; i < length; i++) {
/* 100 */         double total = 0.0D;
/* 101 */         for (int j = 0; j < nmsets; j++) {
/*     */           try
/*     */           {
/* 104 */             total += this.datasets[j].getDataElementAt(i).y;
/*     */           }
/*     */           catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
/*     */           {
/*     */           }
/*     */         }
/* 110 */         low = min(low, total);
/*     */       }
/*     */     else
/* 113 */       for (i = 0; i < length; i++) {
/* 114 */         double total = 0.0D;
/* 115 */         for (int j = 0; j < nmsets; j++) {
/*     */           try {
/* 117 */             total += this.datasets[j].getDataElementAt(i).x;
/*     */           }
/*     */           catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1)
/*     */           {
/*     */           }
/*     */         }
/* 123 */         low = min(low, total);
/*     */       }
/* 125 */     if (!this.isXAxis) {
/* 126 */       return min(low, this.datasets[0].minY());
/*     */     }
/* 128 */     return min(low, this.datasets[0].minX());
/*     */   }
/*     */ 
/*     */   public boolean getStackValues()
/*     */   {
/* 138 */     return this.stackValues;
/*     */   }
/*     */ 
/*     */   public void setStackValues(boolean stack)
/*     */   {
/* 146 */     this.stackValues = stack;
/*     */   }
/*     */ 
/*     */   private static double min(double a, double b)
/*     */   {
/* 154 */     if ((a == (-1.0D / 0.0D)) || (b == (-1.0D / 0.0D))) {
/* 155 */       if (a != (-1.0D / 0.0D))
/* 156 */         return a;
/* 157 */       if (b != (-1.0D / 0.0D)) {
/* 158 */         return b;
/*     */       }
/* 160 */       return 0.0D;
/*     */     }
/* 162 */     return Math.min(a, b);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.StackAxis
 * JD-Core Version:    0.6.2
 */