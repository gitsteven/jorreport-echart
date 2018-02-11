/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.chart.Globals;
/*     */ import jatools.component.chart.component.FillStyleChooser;
/*     */ import jatools.component.chart.component.FillStyleInterface;
/*     */ import jatools.component.chart.component.LineStyle;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ public class LineAndSignDialog extends JDialog
/*     */   implements ChangeListener, ActionListener
/*     */ {
/*     */   ChangeListener parent;
/*     */   LineAndSignPanel lap;
/*     */   JButton ok;
/*     */   JButton cancel;
/*     */   PreviewPanel view;
/*  41 */   Gc gc = new Gc(Color.WHITE, new Globals());
/*     */   LineStyle style;
/*     */   FillStyleChooser chooser;
/*  44 */   IconType iconType = new IconType();
/*     */ 
/*     */   public LineAndSignDialog(Component frame, String title, FillStyleChooser chooser, FillStyleInterface init, boolean model) {
/*  47 */     super((JDialog)frame, title, true);
/*     */ 
/*  49 */     this.chooser = chooser;
/*  50 */     if (init == null)
/*  51 */       this.style = new LineStyle(true);
/*     */     else {
/*  53 */       this.style = ((LineStyle)init);
/*     */     }
/*  55 */     this.style.setToGc(this.gc);
/*  56 */     this.chooser.setStyle(this.style);
/*  57 */     GridBagLayout gbl = new GridBagLayout();
/*  58 */     getContentPane().setLayout(gbl);
/*  59 */     GridBagConstraints gbc = new GridBagConstraints();
/*  60 */     gbc.insets = CommonFinal.INSETS;
/*  61 */     gbc.fill = 1;
/*     */ 
/*  63 */     setSize(420, 220);
/*     */ 
/*  65 */     this.lap = new LineAndSignPanel(this.iconType, this.style.full);
/*  66 */     this.lap.setObject(this.gc);
/*  67 */     this.lap.addChangeListener(this);
/*     */ 
/*  69 */     gbc.weightx = 1.0D;
/*  70 */     gbc.weighty = 1.0D;
/*     */ 
/*  76 */     this.ok = new JButton("确定");
/*  77 */     this.ok.setActionCommand("ok");
/*  78 */     this.ok.addActionListener(this);
/*  79 */     this.cancel = new JButton("取消");
/*  80 */     this.cancel.setActionCommand("cancel");
/*  81 */     this.cancel.addActionListener(this);
/*     */ 
/*  83 */     gbc.weightx = 1.0D;
/*  84 */     gbc.gridwidth = 1;
/*  85 */     gbc.gridheight = 3;
/*  86 */     getContentPane().add(this.lap, gbc);
/*     */ 
/*  88 */     JPanel buttonPanel = new JPanel();
/*  89 */     buttonPanel.setLayout(gbl);
/*  90 */     gbc.weightx = 0.0D;
/*  91 */     gbc.gridwidth = 0;
/*  92 */     gbc.gridheight = 1;
/*  93 */     gbc.weighty = 0.0D;
/*  94 */     buttonPanel.add(this.ok, gbc);
/*  95 */     buttonPanel.add(this.cancel, gbc);
/*  96 */     gbc.weighty = 1.0D;
/*  97 */     buttonPanel.add(Box.createGlue(), gbc);
/*     */ 
/*  99 */     getContentPane().add(buttonPanel, gbc);
/*     */ 
/* 101 */     this.view = new PreviewPanel();
/* 102 */     getContentPane().add(Box.createGlue(), gbc);
/* 103 */     gbc.weighty = 0.0D;
/* 104 */     getContentPane().add(this.view, gbc);
/*     */   }
/*     */ 
/*     */   public void addChangeListener(ChangeListener l)
/*     */   {
/* 109 */     this.parent = l;
/*     */   }
/*     */   public void fireChange(Object object) {
/* 112 */     if (this.style == null)
/* 113 */       this.style = new LineStyle(true);
/*     */     else {
/* 115 */       this.style = new LineStyle(this.style.full);
/*     */     }
/* 117 */     this.style.setIconType(this.iconType.type);
/* 118 */     this.style.getFromGc(this.gc);
/* 119 */     this.view.setIcon(this.style.createIcon(CommonFinal.VIEW_ICON_SIZE));
/*     */   }
/*     */   public void actionPerformed(ActionEvent e) {
/* 122 */     if (e.getActionCommand().equals("ok")) {
/* 123 */       this.chooser.setStyle(this.style);
/*     */     }
/* 125 */     hide();
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.LineAndSignDialog
 * JD-Core Version:    0.6.2
 */