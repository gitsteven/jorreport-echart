/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.Format;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ 
/*     */ public abstract class DataRepresentation
/*     */   implements Serializable
/*     */ {
/*     */   protected Plotarea plotarea;
/*     */   protected Globals globals;
/*     */   protected AxisInterface xAxis;
/*     */   protected AxisInterface yAxis;
/*     */   protected Dataset[] datasets;
/*  31 */   protected boolean useDisplayList = true;
/*  32 */   protected int labelAngle = 0;
/*  33 */   protected int labelFormat = 1;
/*  34 */   protected boolean labelsOn = false;
/*  35 */   protected int labelPrecision = 0;
/*  36 */   protected Format userFormat = null;
/*     */   NumberFormat numberFormat;
/*  38 */   private String pattern = "0";
/*     */ 
/*     */   public abstract void draw(Graphics paramGraphics);
/*     */ 
/*     */   protected String formatLabel(double inVal)
/*     */   {
/*  61 */     if ((this.pattern == null) || (this.pattern.equals(""))) {
/*  62 */       this.pattern = "0";
/*     */     }
/*     */ 
/*  65 */     DecimalFormat format = new DecimalFormat();
/*  66 */     format.applyPattern(this.pattern);
/*     */ 
/*  68 */     return format.format(inVal);
/*     */   }
/*     */ 
/*     */   public Dataset[] getDatasets()
/*     */   {
/*  77 */     return this.datasets;
/*     */   }
/*     */ 
/*     */   public Format getFormat()
/*     */   {
/*  85 */     return this.userFormat;
/*     */   }
/*     */ 
/*     */   public int getLabelAngle()
/*     */   {
/*  93 */     return this.labelAngle;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public int getLabelFormat()
/*     */   {
/* 103 */     return this.labelFormat;
/*     */   }
/*     */ 
/*     */   public int getLabelPrecision()
/*     */   {
/* 112 */     return this.labelPrecision;
/*     */   }
/*     */ 
/*     */   public boolean getLabelsOn()
/*     */   {
/* 121 */     return this.labelsOn;
/*     */   }
/*     */ 
/*     */   public boolean getUseDisplayList()
/*     */   {
/* 131 */     return this.useDisplayList;
/*     */   }
/*     */ 
/*     */   public AxisInterface getXAxis()
/*     */   {
/* 140 */     return this.xAxis;
/*     */   }
/*     */ 
/*     */   public AxisInterface getYAxis()
/*     */   {
/* 149 */     return this.yAxis;
/*     */   }
/*     */ 
/*     */   public void setDatasets(Dataset[] d)
/*     */   {
/* 158 */     this.datasets = d;
/*     */ 
/* 160 */     if (this.globals == null) {
/* 161 */       return;
/*     */     }
/*     */ 
/* 164 */     for (int i = 0; i < d.length; i++)
/* 165 */       if ((d[i] != null) && 
/* 166 */         (d[i].globals != this.globals))
/* 167 */         d[i].setGlobals(this.globals);
/*     */   }
/*     */ 
/*     */   public void setFormat(Format format)
/*     */   {
/* 180 */     this.userFormat = format;
/*     */   }
/*     */ 
/*     */   public void setGlobals(Globals g)
/*     */   {
/* 189 */     if (g == null) {
/* 190 */       return;
/*     */     }
/*     */ 
/* 193 */     this.globals = g;
/* 194 */     this.xAxis.setGlobals(g);
/* 195 */     this.yAxis.setGlobals(g);
/* 196 */     this.plotarea.globals = g;
/*     */ 
/* 198 */     if (this.datasets != null)
/*     */     {
/* 200 */       for (int i = 0; i < this.datasets.length; i++)
/* 201 */         if ((this.datasets[i] != null) && 
/* 202 */           (this.datasets[i].globals != g))
/* 203 */           this.datasets[i].setGlobals(g);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setLabelAngle(int num)
/*     */   {
/* 216 */     this.labelAngle = num;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setLabelFormat(int i)
/*     */   {
/* 227 */     if (i == 2) {
/* 228 */       this.numberFormat = NumberFormat.getNumberInstance(Locale.FRANCE);
/*     */     }
/*     */ 
/* 231 */     if (i == 1) {
/* 232 */       this.numberFormat = NumberFormat.getNumberInstance(Locale.US);
/*     */     }
/*     */ 
/* 235 */     if (i == 0)
/* 236 */       this.numberFormat = NumberFormat.getNumberInstance();
/*     */   }
/*     */ 
/*     */   public void setLabelPrecision(int num)
/*     */   {
/* 253 */     this.labelPrecision = num;
/*     */ 
/* 255 */     if ((this.userFormat != null) && 
/* 256 */       ((this.userFormat instanceof NumberFormat))) {
/* 257 */       ((NumberFormat)this.userFormat).setMinimumFractionDigits(num);
/* 258 */       ((NumberFormat)this.userFormat).setMaximumFractionDigits(num);
/*     */     }
/*     */ 
/* 262 */     if (this.numberFormat == null) {
/* 263 */       initNumberFormat();
/*     */     }
/*     */ 
/* 266 */     if (num != -1) {
/* 267 */       this.numberFormat.setMinimumFractionDigits(num);
/* 268 */       this.numberFormat.setMaximumFractionDigits(num);
/*     */     } else {
/* 270 */       this.numberFormat = NumberFormat.getInstance();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setLabelsOn(boolean onOff)
/*     */   {
/* 281 */     this.labelsOn = onOff;
/*     */   }
/*     */ 
/*     */   public void setUseDisplayList(boolean on)
/*     */   {
/* 291 */     this.useDisplayList = on;
/*     */   }
/*     */ 
/*     */   public void setXAxis(AxisInterface ax)
/*     */   {
/* 300 */     ax.setGlobals(this.globals);
/* 301 */     ax.setPlotarea(this.plotarea);
/* 302 */     ax.setDatasets(this.datasets);
/* 303 */     ax.setIsXAxis(true);
/*     */ 
/* 305 */     this.xAxis = ax;
/*     */   }
/*     */ 
/*     */   public void setYAxis(AxisInterface ax)
/*     */   {
/* 314 */     ax.setGlobals(this.globals);
/* 315 */     ax.setPlotarea(this.plotarea);
/* 316 */     ax.setDatasets(this.datasets);
/* 317 */     ax.setIsXAxis(false);
/*     */ 
/* 319 */     this.yAxis = ax;
/*     */   }
/*     */ 
/*     */   private void initNumberFormat()
/*     */   {
/*     */     try
/*     */     {
/* 328 */       this.numberFormat = NumberFormat.getInstance(this.globals.locale);
/*     */     } catch (MissingResourceException e) {
/* 330 */       System.out.println("locale unavailable, using default locale instead");
/* 331 */       this.numberFormat = NumberFormat.getInstance();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getPattern()
/*     */   {
/* 341 */     return this.pattern;
/*     */   }
/*     */ 
/*     */   public void setPattern(String pattern)
/*     */   {
/* 350 */     this.pattern = pattern;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.DataRepresentation
 * JD-Core Version:    0.6.2
 */