/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Bubble;
/*     */ import jatools.component.chart.chart.BubbleChart;
/*     */ import jatools.component.chart.chart.Dataset;
/*     */ import jatools.component.chart.chart._Chart;
/*     */ import jatools.component.chart.component.AbstractComponent;
/*     */ import jatools.component.chart.component.ChartFont;
/*     */ import jatools.component.chart.component.CheckComponent;
/*     */ import jatools.component.chart.component.FontComponent;
/*     */ import jatools.component.chart.component.FormatComponent;
/*     */ import jatools.component.chart.component.StringComponent;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ 
/*     */ public class BubbleOption extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   CheckComponent ckshowlabel;
/*     */   CheckComponent ckcrossaxis;
/*     */   CheckComponent ckfill;
/*     */   CheckComponent ckautosize;
/*     */   StringComponent scxaxis;
/*     */   StringComponent scyaxis;
/*     */   StringComponent scmaxdiameter;
/*     */   StringComponent scrate;
/*     */   private BarDatasetPanel datasetPanel;
/*     */   private Bubble bubble;
/*     */   private AbstractComponent dataFormat;
/*     */ 
/*     */   public void initCustomizer()
/*     */   {
/*  53 */     GridBagLayout gbl = new GridBagLayout();
/*  54 */     setLayout(gbl);
/*  55 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  56 */     GridBagConstraints gbc = new GridBagConstraints();
/*  57 */     gbc.insets = CommonFinal.INSETS;
/*  58 */     gbc.fill = 2;
/*     */ 
/*  60 */     this.ckshowlabel = new CheckComponent("显示标签", false);
/*  61 */     this.ckshowlabel.addChangeListener(this);
/*     */ 
/*  63 */     this.dataFormat = FormatComponent.getDecimalFormatInstance("数据样式");
/*  64 */     this.dataFormat.addChangeListener(this);
/*     */ 
/*  66 */     this.ckcrossaxis = new CheckComponent("交叉轴", false);
/*  67 */     this.ckcrossaxis.addChangeListener(this);
/*     */ 
/*  69 */     this.ckfill = new CheckComponent("填充", false);
/*  70 */     this.ckfill.addChangeListener(this);
/*  71 */     this.ckautosize = new CheckComponent("自动大小", false);
/*  72 */     this.ckautosize.addChangeListener(this);
/*     */ 
/*  74 */     this.scxaxis = new StringComponent("交x轴于:", null);
/*  75 */     this.scxaxis.addChangeListener(this);
/*  76 */     this.scyaxis = new StringComponent("交y轴于:", null);
/*  77 */     this.scyaxis.addChangeListener(this);
/*  78 */     this.scmaxdiameter = new StringComponent("最大直径", null);
/*  79 */     this.scmaxdiameter.addChangeListener(this);
/*  80 */     this.scrate = new StringComponent("缩放比例", null);
/*  81 */     this.scrate.addChangeListener(this);
/*     */ 
/*  87 */     this.datasetPanel = new BarDatasetPanel();
/*     */ 
/*  89 */     this.datasetPanel.setChart(this.chart);
/*  90 */     this.datasetPanel.setObject(this.chart.getDatasets()[0]);
/*  91 */     this.datasetPanel.addChangeListener(this);
/*     */ 
/*  94 */     gbc.gridwidth = 1;
/*  95 */     gbc.weightx = 1.0D;
/*  96 */     add(this.ckshowlabel, gbc);
/*  97 */     gbc.weightx = 0.0D;
/*     */ 
/* 100 */     gbc.gridwidth = 0;
/* 101 */     add(Box.createGlue(), gbc);
/*     */ 
/* 103 */     gbc.weightx = 1.0D;
/* 104 */     gbc.gridwidth = 0;
/* 105 */     add(this.dataFormat, gbc);
/*     */ 
/* 108 */     gbc.weightx = 1.0D;
/* 109 */     gbc.gridwidth = 1;
/* 110 */     add(this.ckcrossaxis, gbc);
/* 111 */     gbc.gridwidth = 0;
/* 112 */     add(this.ckfill, gbc);
/*     */ 
/* 116 */     gbc.weightx = 1.0D;
/* 117 */     gbc.gridwidth = 1;
/* 118 */     add(this.scxaxis, gbc);
/* 119 */     gbc.gridwidth = 0;
/* 120 */     add(this.scyaxis, gbc);
/*     */ 
/* 122 */     gbc.weightx = 1.0D;
/* 123 */     add(this.ckautosize, gbc);
/*     */ 
/* 126 */     gbc.gridwidth = 1;
/* 127 */     add(this.scmaxdiameter, gbc);
/* 128 */     gbc.gridwidth = 0;
/* 129 */     add(this.scrate, gbc);
/*     */ 
/* 133 */     gbc.weightx = 1.0D;
/* 134 */     add(this.datasetPanel, gbc);
/* 135 */     gbc.weightx = 1.0D;
/* 136 */     gbc.gridwidth = 0;
/* 137 */     add(Box.createGlue(), gbc);
/* 138 */     gbc.weighty = 1.0D;
/* 139 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show() {
/* 143 */     setVals();
/* 144 */     this.datasetPanel.setVals();
/* 145 */     super.show();
/*     */   }
/*     */ 
/*     */   public void setObject(Object object)
/*     */   {
/* 154 */     this.bubble = ((BubbleChart)this.chart).getBubble();
/* 155 */     initCustomizer();
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/* 164 */     ((BubbleChart)this.chart).setCrossAxes(this.ckcrossaxis.getValue());
/* 165 */     this.bubble.setLabelsOn(this.ckshowlabel.getValue());
/* 166 */     ((BubbleChart)this.chart).setXCrossVal(Double.parseDouble(
/* 167 */       (String)this.scxaxis
/* 167 */       .getValue()));
/* 168 */     ((BubbleChart)this.chart).setYCrossVal(Double.parseDouble(
/* 169 */       (String)this.scyaxis
/* 169 */       .getValue()));
/* 170 */     this.bubble.fillBubbles = this.ckfill.getValue();
/* 171 */     this.bubble.maxDiameter = Double.parseDouble(
/* 172 */       (String)this.scmaxdiameter
/* 172 */       .getValue());
/* 173 */     ((BubbleChart)this.chart).setAutoZScale(this.ckautosize.getValue());
/* 174 */     this.bubble.setZScale(Double.parseDouble((String)this.scrate.getValue()));
/*     */ 
/* 177 */     this.bubble.setPattern((String)this.dataFormat.getValue());
/* 178 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   public void setVals()
/*     */   {
/* 183 */     this.ckcrossaxis.setValue(((BubbleChart)this.chart).getCrossAxes());
/* 184 */     this.ckshowlabel.setValue(this.bubble.getLabelsOn());
/* 185 */     this.scxaxis.setValue(String.valueOf(((BubbleChart)this.chart).getXCrossVal()));
/* 186 */     this.scyaxis.setValue(String.valueOf(((BubbleChart)this.chart).getYCrossVal()));
/* 187 */     this.ckfill.setValue(this.bubble.fillBubbles);
/* 188 */     this.scmaxdiameter.setValue(String.valueOf(this.bubble.maxDiameter));
/* 189 */     this.ckautosize.setValue(((BubbleChart)this.chart).getAutoZScale());
/* 190 */     this.scrate.setValue(String.valueOf(this.bubble.getZScale()));
/* 191 */     this.dataFormat.setValue(this.bubble.getPattern());
/* 192 */     volidateEnabled();
/*     */   }
/*     */ 
/*     */   private void volidateEnabled() {
/* 196 */     if (this.ckautosize.getValue()) {
/* 197 */       this.scrate.setEnabled(false);
/* 198 */       this.scmaxdiameter.setEnabled(true);
/*     */     } else {
/* 200 */       this.scrate.setEnabled(true);
/* 201 */       this.scmaxdiameter.setEnabled(false);
/*     */     }
/* 203 */     this.dataFormat.setEnabled(this.ckshowlabel.getValue());
/* 204 */     this.scxaxis.setEnabled(this.ckcrossaxis.getValue());
/* 205 */     this.scyaxis.setEnabled(this.ckcrossaxis.getValue());
/*     */ 
/* 207 */     this.datasetPanel.validateEnabled();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 217 */     if ((e.getActionCommand().equals("label font")) && 
/* 218 */       (FontComponent.getDefault().showChooser(this))) {
/* 219 */       ChartFont font = (ChartFont)FontComponent.getDefault().getValue();
/* 220 */       Color color = font.getForeColor();
/* 221 */       Font ff = new Font(font.getFace(), font.getStyle(), font
/* 222 */         .getSize());
/* 223 */       for (int i = 0; i < this.chart.getNumDatasets(); i++) {
/* 224 */         this.chart.getDatasets()[i].setLabelColor(color);
/* 225 */         this.chart.getDatasets()[i].setLabelFont(ff);
/*     */       }
/*     */     }
/*     */ 
/* 229 */     if (this.parent != null)
/* 230 */       this.parent.fireChange(null);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.BubbleOption
 * JD-Core Version:    0.6.2
 */