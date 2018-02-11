/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ public class Sector
/*     */ {
/*     */   int xLoc;
/*     */   int yLoc;
/*     */   int width;
/*     */   int height;
/*     */   int datasetNumber;
/*     */   int datumNumber;
/*     */   double percentage;
/*  26 */   boolean isDataset = false;
/*     */ 
/*     */   public Sector()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Sector(int datasetNum, int datumNum, double percent, boolean dataset)
/*     */   {
/*  48 */     this.datasetNumber = datasetNum;
/*  49 */     this.datumNumber = datumNum;
/*  50 */     this.percentage = percent;
/*  51 */     this.isDataset = dataset;
/*     */   }
/*     */ 
/*     */   public Sector(int x, int y, int w, int h, int datasetNum, int datumNum, boolean dataset)
/*     */   {
/*  72 */     this.xLoc = x;
/*  73 */     this.yLoc = y;
/*  74 */     this.width = w;
/*  75 */     this.height = h;
/*  76 */     this.datasetNumber = datasetNum;
/*  77 */     this.datumNumber = datumNum;
/*  78 */     this.isDataset = dataset;
/*     */   }
/*     */ 
/*     */   public static boolean compare(Sector s1, Sector s2)
/*     */   {
/*  90 */     boolean greaterThan = false;
/*  91 */     if (s1.percentage > s2.percentage)
/*  92 */       greaterThan = true;
/*  93 */     return greaterThan;
/*     */   }
/*     */ 
/*     */   public int getDatasetNumber()
/*     */   {
/* 102 */     return this.datasetNumber;
/*     */   }
/*     */ 
/*     */   public int getDatumNumber()
/*     */   {
/* 111 */     return this.datumNumber;
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 120 */     return this.height;
/*     */   }
/*     */ 
/*     */   public double getPercentage()
/*     */   {
/* 129 */     return this.percentage;
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 138 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getXLoc()
/*     */   {
/* 147 */     return this.xLoc;
/*     */   }
/*     */ 
/*     */   public int getYLoc()
/*     */   {
/* 156 */     return this.yLoc;
/*     */   }
/*     */ 
/*     */   public boolean isDataset()
/*     */   {
/* 165 */     return this.isDataset;
/*     */   }
/*     */ 
/*     */   public void setDatasetNumber(int v)
/*     */   {
/* 174 */     this.datasetNumber = v;
/*     */   }
/*     */ 
/*     */   public void setDatumNumber(int argDatumNumber)
/*     */   {
/* 183 */     this.datumNumber = argDatumNumber;
/*     */   }
/*     */ 
/*     */   public void setHeight(int v)
/*     */   {
/* 192 */     this.height = v;
/*     */   }
/*     */ 
/*     */   public void setIsDataset(boolean argIsDataset)
/*     */   {
/* 201 */     this.isDataset = argIsDataset;
/*     */   }
/*     */ 
/*     */   public void setPercentage(double argPercentage)
/*     */   {
/* 210 */     this.percentage = argPercentage;
/*     */   }
/*     */ 
/*     */   public void setWidth(int v)
/*     */   {
/* 219 */     this.width = v;
/*     */   }
/*     */ 
/*     */   public void setXLoc(int v)
/*     */   {
/* 228 */     this.xLoc = v;
/*     */   }
/*     */ 
/*     */   public void setYLoc(int v)
/*     */   {
/* 237 */     this.yLoc = v;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.Sector
 * JD-Core Version:    0.6.2
 */