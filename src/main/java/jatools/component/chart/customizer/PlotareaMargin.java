/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Plotarea;
/*     */ import jatools.component.chart.component.MoreButton;
/*     */ import jatools.component.chart.component.RangeComponent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class PlotareaMargin extends JDialog
/*     */   implements ChangeListener, ActionListener
/*     */ {
/*     */   private RangeComponent lmargin;
/*     */   private RangeComponent rmargin;
/*     */   private RangeComponent tmargin;
/*     */   private RangeComponent bmargin;
/*     */   private ChangeListener parent;
/*     */   private Plotarea plotarea;
/*     */   private static PlotareaMargin instance;
/*     */   private JButton okButton;
/*     */ 
/*     */   private PlotareaMargin(Component c, String title, Plotarea plotarea)
/*     */   {
/*  33 */     super((JDialog)c, title, true);
/*  34 */     this.plotarea = plotarea;
/*  35 */     JPanel panel = new JPanel();
/*  36 */     GridBagLayout gbl = new GridBagLayout();
/*  37 */     panel.setLayout(gbl);
/*  38 */     panel.setBorder(CommonFinal.EMPTY_BORDER);
/*  39 */     GridBagConstraints gbc = new GridBagConstraints();
/*  40 */     gbc.insets = CommonFinal.INSETS;
/*  41 */     gbc.fill = 2;
/*     */ 
/*  43 */     this.okButton = new MoreButton("确定");
/*  44 */     this.okButton.setActionCommand("ok");
/*  45 */     this.okButton.addActionListener(this);
/*     */ 
/*  48 */     JPanel p = new JPanel(new FlowLayout(2));
/*  49 */     p.add(this.okButton);
/*     */ 
/*  52 */     this.lmargin = new RangeComponent("左边距", 0.0D);
/*  53 */     this.rmargin = new RangeComponent("右边距", 0.0D);
/*  54 */     this.tmargin = new RangeComponent("上边距", 0.0D);
/*  55 */     this.bmargin = new RangeComponent("下边距", 0.0D);
/*     */ 
/*  57 */     this.lmargin.addChangeListener(this);
/*  58 */     this.rmargin.addChangeListener(this);
/*  59 */     this.tmargin.addChangeListener(this);
/*  60 */     this.bmargin.addChangeListener(this);
/*     */ 
/*  62 */     gbc.weightx = 1.0D;
/*  63 */     gbc.gridwidth = 0;
/*  64 */     panel.add(this.lmargin, gbc);
/*  65 */     panel.add(this.rmargin, gbc);
/*  66 */     panel.add(this.tmargin, gbc);
/*  67 */     panel.add(this.bmargin, gbc);
/*  68 */     panel.add(p, gbc);
/*  69 */     gbc.weighty = 1.0D;
/*  70 */     panel.add(Box.createGlue(), gbc);
/*  71 */     getContentPane().add(panel);
/*  72 */     setSize(300, 200);
/*     */   }
/*     */ 
/*     */   public void show() {
/*  76 */     setVals();
/*  77 */     super.show();
/*     */   }
/*     */ 
/*     */   public static void showDialog(Component c, String title, Plotarea plotarea, ChangeListener parent) {
/*  81 */     if (instance == null) {
/*  82 */       instance = new PlotareaMargin(c, title, plotarea);
/*     */     } else {
/*  84 */       instance.removeChangeListener();
/*  85 */       instance.plotarea = plotarea;
/*     */     }
/*  87 */     instance.setLocationRelativeTo(c);
/*  88 */     instance.addChangeListener(parent);
/*  89 */     instance.setVisible(true);
/*     */   }
/*     */ 
/*     */   public void getVals() {
/*  93 */     this.plotarea.setLlX(this.lmargin.getValue());
/*  94 */     this.plotarea.setLlY(this.bmargin.getValue());
/*  95 */     this.plotarea.setUrX(this.rmargin.getValue());
/*  96 */     this.plotarea.setUrY(this.tmargin.getValue());
/*     */   }
/*     */ 
/*     */   public void setVals() {
/* 100 */     this.tmargin.setValue(this.plotarea.getUrY());
/* 101 */     this.bmargin.setValue(this.plotarea.getLlY());
/* 102 */     this.lmargin.setValue(this.plotarea.getLlX());
/* 103 */     this.rmargin.setValue(this.plotarea.getUrX());
/*     */   }
/*     */ 
/*     */   public void addChangeListener(ChangeListener l) {
/* 107 */     this.parent = l;
/*     */   }
/*     */ 
/*     */   public void removeChangeListener() {
/* 111 */     this.parent = null;
/*     */   }
/*     */ 
/*     */   public void fireChange(Object object) {
/* 115 */     if (this.tmargin.getValue() < this.bmargin.getValue())
/* 116 */       this.tmargin.setValue(this.bmargin.getValue() + 0.1D);
/* 117 */     if (this.rmargin.getValue() < this.lmargin.getValue())
/* 118 */       this.rmargin.setValue(this.lmargin.getValue() + 0.1D);
/* 119 */     getVals();
/* 120 */     if (this.parent != null)
/* 121 */       this.parent.fireChange(object);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 131 */     if (e.getActionCommand().equals("ok"))
/* 132 */       hide();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.PlotareaMargin
 * JD-Core Version:    0.6.2
 */