/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.util.Map;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class AreaChart extends _Chart
/*     */   implements AreaInterface
/*     */ {
/*     */   Area area;
/*     */ 
/*     */   public AreaChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AreaChart(String s)
/*     */   {
/*  37 */     super(s);
/*     */   }
/*     */ 
/*     */   public synchronized void drawGraph()
/*     */   {
/*  43 */     if (this.canvas == null)
/*  44 */       return;
/*  45 */     drawGraph(this.canvas);
/*     */   }
/*     */ 
/*     */   public synchronized void drawGraph(Graphics g)
/*     */   {
/*  52 */     if (g == null)
/*  53 */       return;
/*  54 */     super.drawGraph(g);
/*  55 */     this.background.draw(g);
/*  56 */     this.plotarea.draw(g);
/*  57 */     this.xAxis.draw(g);
/*  58 */     this.yAxis.draw(g);
/*  59 */     this.area.draw(g);
/*     */ 
/*  61 */     super.drawAxisOverlays(g);
/*  62 */     if (this.legendVisible)
/*  63 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public Area getArea()
/*     */   {
/*  71 */     return this.area;
/*     */   }
/*     */   protected void initAxes() {
/*  74 */     setXAxis(new LabelAxis());
/*  75 */     this.xAxis.setSide(0);
/*  76 */     this.xAxis.setBarScaling(false);
/*  77 */     setYAxis(new StackAxis());
/*     */   }
/*     */ 
/*     */   protected synchronized void initChart() {
/*  81 */     initGlobals();
/*  82 */     setPlotarea(new Plotarea());
/*  83 */     setBackground(new Background());
/*  84 */     initDatasets();
/*  85 */     initAxes();
/*  86 */     this.area = new Area();
/*  87 */     setDataRepresentation(this.area);
/*  88 */     setLegend(new Legend());
/*  89 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public void setArea(Area a)
/*     */   {
/*  98 */     this.area = a;
/*  99 */     setDataRepresentation(a);
/*     */   }
/*     */ 
/*     */   public void setStackAreas(boolean stackAreas)
/*     */   {
/* 106 */     ((StackAxis)this.yAxis).setStackValues(stackAreas);
/* 107 */     ((Area)this.dataRepresentation).setStackAreas(stackAreas);
/*     */   }
/*     */ 
/*     */   public boolean getStackAreas() {
/* 111 */     return ((Area)this.dataRepresentation).getStackAreas();
/*     */   }
/*     */ 
/*     */   public AreaChart(String name, Locale locale)
/*     */   {
/* 122 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public AreaChart(Locale locale)
/*     */   {
/* 132 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map)
/*     */   {
/* 140 */     applyGeneralProperty(map);
/*     */ 
/* 142 */     map.put("stackAreas", String.valueOf(getArea().getStackAreas()));
/* 143 */     map.put("baseline", String.valueOf(getArea().getBaseline()));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.AreaChart
 * JD-Core Version:    0.6.2
 */