/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class Datum
/*     */   implements Serializable
/*     */ {
/*     */   public static final String DISCONTINUITY = "D";
/*     */   public static final double DEFAULT = (-1.0D / 0.0D);
/*     */   String label;
/*     */   Gc gc;
/*  23 */   double x = (-1.0D / 0.0D);
/*  24 */   double y = this.x;
/*  25 */   double y2 = this.x;
/*  26 */   double y3 = this.x;
/*     */   Globals globals;
/*     */ 
/*     */   public Datum(double dataX, double hi, double lo, double close, Globals g)
/*     */   {
/*  44 */     this.globals = g;
/*  45 */     this.x = dataX;
/*  46 */     this.y = hi;
/*  47 */     this.y2 = lo;
/*  48 */     this.y3 = close;
/*     */ 
/*  50 */     this.gc = new Gc(true, this.globals, false);
/*     */   }
/*     */ 
/*     */   public Datum(double dataX, double dataY, double dataZ, int element, Globals g)
/*     */   {
/*  68 */     this.globals = g;
/*  69 */     this.x = dataX;
/*  70 */     this.y = dataY;
/*  71 */     this.y2 = dataZ;
/*     */ 
/*  73 */     this.gc = new Gc(true, this.globals, false);
/*     */   }
/*     */ 
/*     */   public Datum(double dataX, double dataY, double dataZ, String str, int element, Globals g)
/*     */   {
/*  93 */     this.globals = g;
/*  94 */     this.x = dataX;
/*  95 */     this.y = dataY;
/*  96 */     this.y2 = dataZ;
/*     */ 
/*  98 */     this.gc = new Gc(true, this.globals, false);
/*  99 */     this.label = str;
/*     */   }
/*     */ 
/*     */   public Datum(double dataX, double dataY, int setNumber, Globals g)
/*     */   {
/* 113 */     this.globals = g;
/* 114 */     this.x = dataX;
/* 115 */     this.y = dataY;
/*     */ 
/* 117 */     this.gc = new Gc(true, this.globals, false);
/*     */   }
/*     */ 
/*     */   public Datum(double dataX, double dataY, String str, int element, Globals g)
/*     */   {
/* 133 */     this.globals = g;
/* 134 */     this.x = dataX;
/* 135 */     this.y = dataY;
/*     */ 
/* 137 */     this.gc = new Gc(true, this.globals, false);
/* 138 */     this.label = str;
/*     */   }
/*     */ 
/*     */   public Datum(double dataX, double dataY, String str, boolean individual, Globals g)
/*     */   {
/* 154 */     this.globals = g;
/* 155 */     this.x = dataX;
/* 156 */     this.y = dataY;
/* 157 */     this.label = str;
/* 158 */     this.gc = new Gc(individual, this.globals, true);
/*     */   }
/*     */ 
/*     */   public Datum(int whichPoint, double dataY, String str, Globals g)
/*     */   {
/* 172 */     this.globals = g;
/* 173 */     this.x = whichPoint;
/* 174 */     this.y = dataY;
/* 175 */     this.label = str;
/* 176 */     this.gc = new Gc(whichPoint, this.globals);
/*     */   }
/*     */ 
/*     */   public Datum(double dataX, double dataY, Globals g)
/*     */   {
/* 189 */     this.globals = g;
/* 190 */     this.x = dataX;
/* 191 */     this.y = dataY;
/*     */ 
/* 193 */     this.gc = new Gc(true, this.globals, false);
/*     */   }
/*     */ 
/*     */   public Datum(double dataX, double dataY, boolean individual, Globals g)
/*     */   {
/* 207 */     this.globals = g;
/* 208 */     this.x = dataX;
/* 209 */     this.y = dataY;
/* 210 */     this.gc = new Gc(individual, this.globals, true);
/*     */   }
/*     */ 
/*     */   public Datum(double dataY, String str, int element, Globals g)
/*     */   {
/* 224 */     this.globals = g;
/* 225 */     this.y = dataY;
/*     */ 
/* 227 */     this.gc = new Gc(true, this.globals, false);
/* 228 */     this.label = str;
/*     */   }
/*     */ 
/*     */   public Gc getGc()
/*     */   {
/* 236 */     return this.gc;
/*     */   }
/*     */ 
/*     */   public Globals getGlobals()
/*     */   {
/* 243 */     return this.globals;
/*     */   }
/*     */ 
/*     */   public String getLabel()
/*     */   {
/* 251 */     return this.label;
/*     */   }
/*     */ 
/*     */   public String getString()
/*     */   {
/* 258 */     if (this.label != null) {
/* 259 */       return this.label;
/*     */     }
/* 261 */     String inStr = Double.toString(this.y);
/* 262 */     int i = inStr.indexOf(".");
/* 263 */     if (i != -1) {
/* 264 */       int length = inStr.length();
/* 265 */       if ((i == length - 2) && (inStr.charAt(length - 1) == '0'))
/* 266 */         inStr = inStr.substring(0, i);
/*     */     }
/* 268 */     return inStr;
/*     */   }
/*     */ 
/*     */   public double getX()
/*     */   {
/* 276 */     return this.x;
/*     */   }
/*     */ 
/*     */   public double getY()
/*     */   {
/* 284 */     return this.y;
/*     */   }
/*     */ 
/*     */   public double getY2()
/*     */   {
/* 292 */     return this.y2;
/*     */   }
/*     */ 
/*     */   public double getY3()
/*     */   {
/* 300 */     return this.y3;
/*     */   }
/*     */ 
/*     */   public void setGc(Gc g)
/*     */   {
/* 308 */     this.gc = g;
/* 309 */     this.gc.globals = this.globals;
/*     */   }
/*     */ 
/*     */   public void setGlobals(Globals g)
/*     */   {
/* 316 */     this.globals = g;
/* 317 */     this.gc.globals = g;
/*     */   }
/*     */ 
/*     */   public void setLabel(String s)
/*     */   {
/* 325 */     this.label = s;
/*     */   }
/*     */ 
/*     */   public void setX(double d)
/*     */   {
/* 333 */     this.x = d;
/*     */   }
/*     */ 
/*     */   public void setY(double d)
/*     */   {
/* 341 */     this.y = d;
/*     */   }
/*     */ 
/*     */   public void setY2(double d)
/*     */   {
/* 349 */     this.y2 = d;
/*     */   }
/*     */ 
/*     */   public void setY3(double d)
/*     */   {
/* 357 */     this.y3 = d;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Datum
 * JD-Core Version:    0.6.2
 */