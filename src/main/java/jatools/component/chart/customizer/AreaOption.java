/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Area;
/*     */ import jatools.component.chart.chart.AreaInterface;
/*     */ import jatools.component.chart.component.CheckComponent;
/*     */ import jatools.component.chart.component.StringComponent;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import javax.swing.Box;
/*     */ 
/*     */ public class AreaOption extends Dialog
/*     */ {
/*     */   CheckComponent ckstack;
/*     */   StringComponent scbaseline;
/*     */   private BarDatasetPanel datasetPanel;
/*     */   private Area area;
/*     */ 
/*     */   protected void initCustomizer()
/*     */   {
/*  34 */     GridBagLayout gbl = new GridBagLayout();
/*  35 */     setLayout(gbl);
/*  36 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  37 */     GridBagConstraints gbc = new GridBagConstraints();
/*  38 */     gbc.insets = CommonFinal.INSETS;
/*  39 */     gbc.fill = 2;
/*     */ 
/*  42 */     this.ckstack = new CheckComponent("堆叠", false);
/*  43 */     this.ckstack.addChangeListener(this);
/*     */ 
/*  45 */     this.scbaseline = new StringComponent("基线", null);
/*  46 */     this.scbaseline.addChangeListener(this);
/*     */ 
/*  48 */     this.datasetPanel = new BarDatasetPanel();
/*  49 */     this.datasetPanel.setChart(this.chart);
/*  50 */     this.datasetPanel.setObject(this.chart.getDatasets()[0]);
/*  51 */     this.datasetPanel.addChangeListener(this);
/*     */ 
/*  53 */     add(this.ckstack, gbc);
/*  54 */     gbc.gridwidth = 0;
/*  55 */     add(Box.createGlue(), gbc);
/*     */ 
/*  58 */     gbc.gridwidth = 1;
/*  59 */     gbc.weightx = 1.0D;
/*  60 */     add(this.scbaseline, gbc);
/*  61 */     gbc.gridwidth = 0;
/*  62 */     add(Box.createGlue(), gbc);
/*  63 */     gbc.weightx = 0.0D;
/*     */ 
/*  65 */     gbc.weightx = 1.0D;
/*  66 */     add(this.datasetPanel, gbc);
/*  67 */     gbc.weightx = 1.0D;
/*  68 */     gbc.gridwidth = 0;
/*  69 */     add(Box.createGlue(), gbc);
/*  70 */     gbc.weighty = 1.0D;
/*  71 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show() {
/*  75 */     setVals();
/*  76 */     this.datasetPanel.setVals();
/*  77 */     super.show();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/*  84 */     this.area = ((AreaInterface)this.chart).getArea();
/*  85 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/*  92 */     this.area.setBaseline(Double.parseDouble((String)this.scbaseline.getValue()));
/*  93 */     this.area.setStackAreas(this.ckstack.getValue());
/*     */ 
/*  95 */     this.scbaseline.setEnabled(!this.ckstack.getValue());
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 102 */     this.scbaseline.setValue(String.valueOf(this.area.getBaseline()));
/* 103 */     this.ckstack.setValue(this.area.getStackAreas());
/*     */ 
/* 105 */     this.scbaseline.setEnabled(!this.ckstack.getValue());
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.AreaOption
 * JD-Core Version:    0.6.2
 */