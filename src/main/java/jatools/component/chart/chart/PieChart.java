/*     */ package jatools.component.chart.chart;
/*     */ 
/*     */ import jatools.component.chart.applet.ChartUtil;
/*     */ import jatools.util.Map;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class PieChart extends _Chart
/*     */ {
/*     */   Pie pie;
/*     */ 
/*     */   public PieChart()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PieChart(String s)
/*     */   {
/*  38 */     super(s);
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] y)
/*     */   {
/*  49 */     this.pie.dataset = new Dataset(s, y, true, this.globals);
/*  50 */     this.datasets[0] = this.pie.dataset;
/*  51 */     PieLegend pl = (PieLegend)this.legend;
/*  52 */     pl.dataset = this.pie.dataset;
/*  53 */     this.numberOfDatasets = 1;
/*     */   }
/*     */ 
/*     */   public void addDataset(String s, double[] y, String[] dataLabels)
/*     */   {
/*  66 */     this.pie.dataset = new Dataset(s, y, dataLabels, true, this.globals);
/*  67 */     this.datasets[0] = this.pie.dataset;
/*  68 */     PieLegend pl = (PieLegend)this.legend;
/*  69 */     pl.dataset = this.pie.dataset;
/*  70 */     this.numberOfDatasets = 1;
/*     */   }
/*     */ 
/*     */   public void addDataset(Dataset d)
/*     */   {
/*  78 */     super.addDataset(d);
/*  79 */     this.pie.dataset = d;
/*     */   }
/*     */ 
/*     */   public void drawGraph(Graphics g)
/*     */   {
/*  86 */     if (g == null)
/*  87 */       return;
/*  88 */     super.drawGraph(g);
/*  89 */     this.background.draw(g);
/*  90 */     this.pie.dataset = this.datasets[0];
/*  91 */     this.pie.draw(g);
/*  92 */     if (this.legendVisible)
/*  93 */       this.legend.draw(g);
/*     */   }
/*     */ 
/*     */   public Pie getPie()
/*     */   {
/* 100 */     return this.pie;
/*     */   }
/*     */   protected void initChart() {
/* 103 */     initGlobals();
/* 104 */     setBackground(new Background());
/* 105 */     setPlotarea(new Plotarea());
/* 106 */     initDatasets();
/* 107 */     this.pie = new Pie();
/* 108 */     setDataRepresentation(this.pie);
/* 109 */     setLegend(new PieLegend());
/* 110 */     this.legend.setLlX(0.0D);
/* 111 */     this.legend.setLlY(0.0D);
/* 112 */     this.legend.setVerticalLayout(false);
/* 113 */     resize(640, 480);
/*     */   }
/*     */ 
/*     */   public void setDataRepresentation(DataRepresentation dr)
/*     */   {
/* 120 */     super.setDataRepresentation(dr);
/* 121 */     this.pie = ((Pie)this.dataRepresentation);
/* 122 */     this.pie.lineGc.globals = this.globals;
/*     */   }
/*     */ 
/*     */   public void setPie(Pie p)
/*     */   {
/* 129 */     this.pie = p;
/* 130 */     setDataRepresentation(p);
/*     */   }
/*     */ 
/*     */   public void setSliceColor(int slice, Color color)
/*     */   {
/*     */     try
/*     */     {
/* 140 */       this.datasets[0].getDataElementAt(slice).getGc().setFillColor(color);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public PieChart(String name, Locale locale)
/*     */   {
/* 155 */     super(name, locale);
/*     */   }
/*     */ 
/*     */   public PieChart(Locale locale)
/*     */   {
/* 165 */     super(locale);
/*     */   }
/*     */ 
/*     */   public void applyProperties(Map map) {
/* 169 */     Pie pie = getPie();
/* 170 */     applyGeneralProperty(map);
/*     */ 
/* 172 */     if (pie.getTextLabelsOn()) {
/* 173 */       map.put("textLabelsOn", "true");
/*     */     }
/*     */ 
/* 176 */     if (pie.getValueLabelsOn()) {
/* 177 */       map.put("valueLabelsOn", "true");
/*     */     }
/*     */ 
/* 180 */     if (!pie.getPercentLabelsOn()) {
/* 181 */       map.put("percentLabelsOff", "false");
/*     */     }
/*     */ 
/* 184 */     map.put("labelPosition", String.valueOf(pie.getLabelPosition()));
/*     */ 
/* 186 */     map.put("startDegrees", String.valueOf(pie.getStartDegrees()));
/* 187 */     map.put("xLoc", String.valueOf(pie.getXLoc()));
/* 188 */     map.put("yLoc", String.valueOf(pie.getYLoc()));
/* 189 */     map.put("pieWidth", String.valueOf(pie.getWidth()));
/* 190 */     map.put("pieHeight", String.valueOf(pie.getHeight()));
/*     */ 
/* 193 */     String s = "";
/* 194 */     String p = "";
/* 195 */     Dataset dataset = getDatasets()[0];
/*     */ 
/* 197 */     for (int i = 0; i < dataset.getData().size(); i++)
/*     */     {
/* 200 */       s = s + String.valueOf(dataset.getDataElementAt(i).getY2()) + ",";
/* 201 */       p = p + String.valueOf(dataset.getDataElementAt(i).getY3()) + ",";
/*     */     }
/*     */ 
/* 204 */     s = s.substring(0, s.length() - 1);
/* 205 */     p = p.substring(0, p.length() - 1);
/*     */ 
/* 207 */     map.put("explodeSlices", s);
/* 208 */     map.put("pointerLengths", p);
/*     */ 
/* 210 */     map.put("pieLabelFont", ChartUtil.toString(pie.getLabelFont()));
/* 211 */     map.put("pieLabelColor", ChartUtil.toString(pie.getLabelColor()));
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.chart.PieChart
 * JD-Core Version:    0.6.2
 */