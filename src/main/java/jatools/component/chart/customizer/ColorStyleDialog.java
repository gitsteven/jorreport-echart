/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.chart.Globals;
/*     */ import jatools.component.chart.component.FillStyleChooser;
/*     */ import jatools.component.chart.component.FillStyleFactory;
/*     */ import jatools.component.chart.component.FillStyleInterface;
/*     */ import jatools.component.chart.component.SingleColor;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class ColorStyleDialog extends JDialog
/*     */   implements ChangeListener, ActionListener
/*     */ {
/*     */   ChangeListener parent;
/*     */   FillStyleChooser chooser;
/*     */   FillStyleInterface style;
/*  34 */   Gc gc = new Gc(Color.WHITE, new Globals());
/*     */   FillStylePanel panel;
/*     */   JButton ok;
/*     */   JButton cancel;
/*     */   PreviewPanel view;
/*     */ 
/*     */   public ColorStyleDialog(Component frame, String title, FillStyleChooser chooser, FillStyleInterface init, boolean mode)
/*     */   {
/*  44 */     super((Dialog)frame, title, mode);
/*     */ 
/*  46 */     this.chooser = chooser;
/*  47 */     if (init == null)
/*  48 */       this.style = new SingleColor(Color.WHITE);
/*     */     else {
/*  50 */       this.style = init;
/*     */     }
/*  52 */     this.style.setToGc(this.gc);
/*  53 */     this.chooser.setStyle(this.style);
/*  54 */     GridBagLayout gbl = new GridBagLayout();
/*  55 */     GridBagConstraints gbc = new GridBagConstraints();
/*  56 */     gbc.insets = CommonFinal.INSETS;
/*  57 */     gbc.fill = 1;
/*  58 */     gbc.weightx = 1.0D;
/*  59 */     gbc.weighty = 1.0D;
/*  60 */     getContentPane().setLayout(gbl);
/*     */ 
/*  62 */     this.panel = new FillStylePanel();
/*  63 */     this.panel.setObject(this.gc);
/*  64 */     this.panel.addChangeListener(this);
/*     */ 
/*  66 */     this.ok = new JButton("确定");
/*  67 */     this.ok.setActionCommand("ok");
/*  68 */     this.ok.addActionListener(this);
/*  69 */     this.cancel = new JButton("取消");
/*  70 */     this.cancel.setActionCommand("cancel");
/*  71 */     this.cancel.addActionListener(this);
/*     */ 
/*  73 */     gbc.weightx = 1.0D;
/*  74 */     gbc.gridwidth = 1;
/*  75 */     gbc.gridheight = 3;
/*  76 */     getContentPane().add(this.panel, gbc);
/*     */ 
/*  78 */     JPanel buttonPanel = new JPanel();
/*  79 */     buttonPanel.setLayout(gbl);
/*  80 */     gbc.weightx = 0.0D;
/*  81 */     gbc.gridwidth = 0;
/*  82 */     gbc.gridheight = 1;
/*  83 */     gbc.weighty = 0.0D;
/*  84 */     buttonPanel.add(this.ok, gbc);
/*  85 */     buttonPanel.add(this.cancel, gbc);
/*  86 */     gbc.weighty = 1.0D;
/*  87 */     buttonPanel.add(Box.createGlue(), gbc);
/*     */ 
/*  89 */     getContentPane().add(buttonPanel, gbc);
/*     */ 
/*  91 */     this.view = new PreviewPanel();
/*  92 */     getContentPane().add(Box.createGlue(), gbc);
/*  93 */     gbc.weighty = 0.0D;
/*  94 */     getContentPane().add(this.view, gbc);
/*     */ 
/*  96 */     setSize(400, 250);
/*     */   }
/*     */ 
/*     */   public void addChangeListener(ChangeListener l) {
/* 100 */     this.parent = l;
/*     */   }
/*     */ 
/*     */   public void fireChange(Object object) {
/* 104 */     this.style = FillStyleFactory.createFillStyle(this.gc);
/* 105 */     this.view.setIcon(this.style.createIcon(CommonFinal.VIEW_ICON_SIZE));
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e) {
/* 109 */     if (e.getActionCommand().equals("ok")) {
/* 110 */       this.chooser.setStyle(this.style);
/*     */     }
/* 112 */     hide();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.ColorStyleDialog
 * JD-Core Version:    0.6.2
 */