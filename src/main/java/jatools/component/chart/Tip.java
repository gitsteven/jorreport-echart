/*     */ package jatools.component.chart;
/*     */ 
/*     */ import jatools.accessor.AutoAccessor;
/*     */ 
/*     */ public class Tip extends AutoAccessor
/*     */ {
/*     */   public String tip;
/*     */   public String url;
/*     */   public String target;
/*     */ 
/*     */   public Tip(String tip, String url, String target)
/*     */   {
/*  36 */     this.tip = tip;
/*  37 */     this.url = url;
/*  38 */     this.target = target;
/*     */   }
/*     */ 
/*     */   public Tip()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getTarget()
/*     */   {
/*  53 */     return this.target;
/*     */   }
/*     */ 
/*     */   public void setTarget(String target)
/*     */   {
/*  62 */     this.target = target;
/*     */   }
/*     */ 
/*     */   public String getTip()
/*     */   {
/*  71 */     return this.tip;
/*     */   }
/*     */ 
/*     */   public void setTip(String tip)
/*     */   {
/*  80 */     this.tip = tip;
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/*  89 */     return this.url;
/*     */   }
/*     */ 
/*     */   public void setUrl(String url)
/*     */   {
/*  98 */     this.url = url;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */     throws CloneNotSupportedException
/*     */   {
/* 109 */     return super.clone();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.Tip
 * JD-Core Version:    0.6.2
 */