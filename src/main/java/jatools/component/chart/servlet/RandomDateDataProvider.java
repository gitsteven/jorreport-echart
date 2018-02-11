/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import jatools.component.chart.applet.DataProvider;
/*     */ import jatools.component.chart.chart.CandlestickDatum;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.tagext.TagSupport;
/*     */ 
/*     */ public class RandomDateDataProvider extends TagSupport
/*     */   implements DataProvider
/*     */ {
/*  28 */   protected int numDatasets = 1;
/*  29 */   protected int numObservations = 20;
/*  30 */   protected int dayIncrement = 1;
/*     */ 
/*  33 */   ArrayList datasets = new ArrayList();
/*  34 */   String uniqueIdentifier = null;
/*     */ 
/*     */   public void setNumDatasets(int i)
/*     */   {
/*  41 */     this.numDatasets = i;
/*     */   }
/*     */ 
/*     */   public void setNumObservations(int i)
/*     */   {
/*  49 */     this.numObservations = i;
/*     */   }
/*     */ 
/*     */   public void setDayIncrement(int i)
/*     */   {
/*  57 */     this.dayIncrement = i;
/*     */   }
/*     */ 
/*     */   public int doStartTag() throws JspException {
/*     */     try {
/*  62 */       ChartTag chartTag = (ChartTag)
/*  63 */         findAncestorWithClass(this, Class.forName("javachart.servlet.ChartTag"));
/*     */ 
/*  65 */       createData();
/*  66 */       chartTag.setDataProvider(this);
/*     */     } catch (ClassNotFoundException e) {
/*  68 */       throw new JspException("Couldn't find an outer chart tag to provide data to.");
/*     */     }
/*  70 */     return 1;
/*     */   }
/*     */ 
/*     */   protected void createData() {
/*  74 */     this.datasets.clear();
/*  75 */     Date startDate = new Date();
/*  76 */     Calendar calendar = Calendar.getInstance();
/*  77 */     for (int i = 0; i < this.numDatasets; i++) {
/*  78 */       calendar.setTime(startDate);
/*  79 */       Dataset d = new Dataset();
/*  80 */       d.setName("Series " + i);
/*  81 */       double lastClose = 0.0D;
/*     */ 
/*  83 */       for (int j = 0; j < this.numObservations; j++)
/*     */       {
/*     */         double close;
/*  84 */         if (j == 0) {
/*  85 */           double close = 50.0D + Math.random() * 10.0D;
/*  86 */           lastClose = close;
/*     */         }
/*     */         else {
/*  89 */           close = lastClose + (Math.random() - 0.5D) * 3.0D;
/*     */         }
/*  91 */         double high = close + Math.random() * 2.0D;
/*  92 */         double low = close - Math.random() * 2.0D;
/*  93 */         double open = low + (high - low) / 2.0D;
/*  94 */         double date = calendar.getTime().getTime();
/*  95 */         d.addDatum(new CandlestickDatum(date, high, low, open, close, null));
/*  96 */         calendar.add(5, this.dayIncrement);
/*     */       }
/*  98 */       this.datasets.add(d);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Iterator getDatasets()
/*     */   {
/* 109 */     return this.datasets.iterator();
/*     */   }
/*     */ 
/*     */   public String getUniqueIdentifier()
/*     */   {
/* 117 */     return "time:" + System.currentTimeMillis();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.RandomDateDataProvider
 * JD-Core Version:    0.6.2
 */