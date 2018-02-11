/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.customizer.ChangeListener;
/*    */ import java.awt.Color;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class ColorComponent extends JPanel
/*    */   implements ActionListener
/*    */ {
/*    */   JLabel label;
/*    */   ZColorComboBox box;
/*    */   ChangeListener parent;
/* 21 */   SingleColor single = new SingleColor(Color.WHITE);
/* 22 */   LineStyle line = new LineStyle(true);
/*    */ 
/*    */   public ColorComponent() {
/* 25 */     this("", null);
/*    */   }
/*    */ 
/*    */   public ColorComponent(String label, Color color) {
/* 29 */     this.single.setColor(color);
/* 30 */     setLayout(new GridBagLayout());
/* 31 */     GridBagConstraints gbc = new GridBagConstraints();
/* 32 */     gbc.insets = CommonFinal.INSETS;
/* 33 */     gbc.fill = 1;
/* 34 */     this.label = new JLabel(label);
/* 35 */     this.label.setPreferredSize(CommonFinal.LABEL_SIZE);
/* 36 */     this.box = new ZColorComboBox();
/* 37 */     setValue(this.single);
/* 38 */     this.box.addActionListener(this);
/* 39 */     add(this.label);
/* 40 */     gbc.weightx = 1.0D;
/* 41 */     gbc.gridwidth = 0;
/* 42 */     add(this.box, gbc);
/*    */   }
/*    */ 
/*    */   public FillStyleInterface getValue()
/*    */   {
/* 47 */     return this.box.getFillStyle();
/*    */   }
/*    */ 
/*    */   public void setValue(FillStyleInterface object) {
/* 51 */     this.box.setFillStyle(object);
/*    */   }
/*    */ 
/*    */   public void setValue(Color color) {
/* 55 */     if ((this.box.getFillStyle() instanceof LineStyle)) {
/* 56 */       this.line = ((LineStyle)this.box.getFillStyle());
/* 57 */       this.line.setColor(color);
/* 58 */       this.box.setFillStyle(this.line);
/*    */     } else {
/* 60 */       this.single.setColor(color);
/* 61 */       this.box.setFillStyle(this.single);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Color getColor() {
/* 66 */     if ((this.box.getFillStyle() instanceof LineStyle)) {
/* 67 */       return this.line.getColor();
/*    */     }
/* 69 */     return this.single.getColor();
/*    */   }
/*    */ 
/*    */   public void addChangeListener(ChangeListener l)
/*    */   {
/* 74 */     this.parent = l;
/*    */   }
/*    */   public void removeChangeListener() {
/* 77 */     this.parent = null;
/*    */   }
/*    */ 
/*    */   public void actionPerformed(ActionEvent e) {
/* 81 */     if ((this.box.getFillStyle() instanceof LineStyle)) {
/* 82 */       this.line = new LineStyle(((LineStyle)this.box.getFillStyle()).full);
/* 83 */       this.line.setColor(this.box.getColor());
/* 84 */       this.box.setFillStyle(this.line);
/*    */     } else {
/* 86 */       this.single.setColor(this.box.getColor());
/* 87 */       this.box.setFillStyle(this.single);
/*    */     }
/* 89 */     if (this.parent != null)
/* 90 */       this.parent.fireChange(null);
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean enabled) {
/* 94 */     super.setEnabled(enabled);
/* 95 */     this.label.setEnabled(enabled);
/* 96 */     this.box.setEnabled(enabled);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ColorComponent
 * JD-Core Version:    0.6.2
 */