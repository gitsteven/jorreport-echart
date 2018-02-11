/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.AxisInterface;
/*     */ import jatools.component.chart.component.CheckComponent;
/*     */ import jatools.component.chart.component.MoreButton;
/*     */ import jatools.component.chart.component.StringComponent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
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
/*     */ public class AxisScaleDialog extends JDialog
/*     */   implements ChangeListener, ActionListener
/*     */ {
/*     */   private AxisInterface axis;
/*     */   private CheckComponent autoscale;
/*     */   private CheckComponent logscale;
/*     */   private StringComponent start;
/*     */   private StringComponent end;
/*     */   private ChangeListener parent;
/*     */   private JButton okButton;
/*     */   private static AxisScaleDialog instance;
/*     */ 
/*     */   private AxisScaleDialog(Component c, String title, AxisInterface axis)
/*     */   {
/*  37 */     super((Dialog)c, title, true);
/*  38 */     this.axis = axis;
/*  39 */     JPanel panel = new JPanel();
/*  40 */     GridBagLayout gbl = new GridBagLayout();
/*  41 */     panel.setLayout(gbl);
/*  42 */     panel.setBorder(CommonFinal.EMPTY_BORDER);
/*  43 */     GridBagConstraints gbc = new GridBagConstraints();
/*  44 */     gbc.insets = CommonFinal.INSETS;
/*  45 */     gbc.fill = 2;
/*     */ 
/*  47 */     this.okButton = new MoreButton("确定");
/*  48 */     this.okButton.setActionCommand("ok");
/*  49 */     this.okButton.addActionListener(this);
/*     */ 
/*  52 */     JPanel p = new JPanel(new FlowLayout(2));
/*  53 */     p.add(this.okButton);
/*     */ 
/*  55 */     this.autoscale = new CheckComponent("自动缩放", false);
/*  56 */     this.logscale = new CheckComponent("log缩放", false);
/*  57 */     this.start = new StringComponent("开始值", null);
/*  58 */     this.end = new StringComponent("结束值", null);
/*     */ 
/*  60 */     this.autoscale.addChangeListener(this);
/*  61 */     this.logscale.addChangeListener(this);
/*  62 */     this.start.addChangeListener(this);
/*  63 */     this.end.addChangeListener(this);
/*     */ 
/*  65 */     gbc.weightx = 1.0D;
/*  66 */     gbc.gridwidth = 0;
/*  67 */     panel.add(this.autoscale, gbc);
/*  68 */     panel.add(this.logscale, gbc);
/*  69 */     panel.add(this.start, gbc);
/*  70 */     panel.add(this.end, gbc);
/*  71 */     panel.add(p, gbc);
/*  72 */     gbc.weighty = 1.0D;
/*  73 */     panel.add(Box.createGlue(), gbc);
/*  74 */     getContentPane().add(panel);
/*  75 */     setSize(300, 200);
/*     */   }
/*     */ 
/*     */   public void show() {
/*  79 */     setVals();
/*  80 */     super.show();
/*     */   }
/*     */ 
/*     */   public static void showDialog(Component c, String title, AxisInterface axis, ChangeListener parent) {
/*  84 */     if (instance == null) {
/*  85 */       instance = new AxisScaleDialog(c, title, axis);
/*     */     } else {
/*  87 */       instance.removeChangeListener();
/*  88 */       instance.axis = axis;
/*     */     }
/*  90 */     instance.setLocationRelativeTo(c);
/*  91 */     instance.addChangeListener(parent);
/*  92 */     instance.setVisible(true);
/*     */   }
/*     */ 
/*     */   public void getVals()
/*     */   {
/*  97 */     if (this.autoscale.getValue()) {
/*  98 */       this.axis.setLogScaling(this.logscale.getValue());
/*  99 */       this.axis.setAxisStart((-1.0D / 0.0D));
/* 100 */       this.axis.setAxisEnd((-1.0D / 0.0D));
/* 101 */       this.axis.scale();
/*     */     } else {
/* 103 */       this.axis.setAutoScale(false);
/* 104 */       this.axis.setAxisStart(Double.parseDouble((String)this.start.getValue()));
/* 105 */       this.axis.setAxisEnd(Double.parseDouble((String)this.end.getValue()));
/*     */     }
/*     */ 
/* 108 */     validateEnabled();
/*     */   }
/*     */ 
/*     */   public void setVals() {
/* 112 */     this.autoscale.setValue(this.axis.getAutoScale());
/*     */ 
/* 115 */     this.logscale.setValue(this.axis.getLogScaling());
/* 116 */     this.start.setValue(String.valueOf(this.axis.getAxisStart()));
/* 117 */     this.end.setValue(String.valueOf(this.axis.getAxisEnd()));
/*     */ 
/* 119 */     validateEnabled();
/*     */   }
/*     */ 
/*     */   private void validateEnabled() {
/* 123 */     this.logscale.setEnabled(this.autoscale.getValue());
/* 124 */     this.start.setEnabled((!this.autoscale.getValue()) && (!this.logscale.getValue()));
/* 125 */     this.end.setEnabled((!this.autoscale.getValue()) && (!this.logscale.getValue()));
/*     */   }
/*     */ 
/*     */   public void addChangeListener(ChangeListener l) {
/* 129 */     this.parent = l;
/*     */   }
/*     */ 
/*     */   public void removeChangeListener() {
/* 133 */     this.parent = null;
/*     */   }
/*     */ 
/*     */   public void fireChange(Object object) {
/* 137 */     getVals();
/* 138 */     if (this.parent != null)
/* 139 */       this.parent.fireChange(object);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/* 149 */     if (e.getActionCommand().equals("ok"))
/* 150 */       hide();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.AxisScaleDialog
 * JD-Core Version:    0.6.2
 */