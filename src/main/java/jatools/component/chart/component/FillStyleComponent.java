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
/*    */ public class FillStyleComponent extends JPanel
/*    */   implements ActionListener
/*    */ {
/*    */   JLabel label;
/*    */   ZColorComboBox box;
/*    */   ChangeListener parent;
/*    */ 
/*    */   public FillStyleComponent()
/*    */   {
/* 23 */     this("", null);
/*    */   }
/*    */ 
/*    */   public FillStyleComponent(String label, Color color) {
/* 27 */     if (color == null)
/* 28 */       color = Color.WHITE;
/* 29 */     setLayout(new GridBagLayout());
/* 30 */     GridBagConstraints gbc = new GridBagConstraints();
/* 31 */     gbc.insets = CommonFinal.INSETS;
/* 32 */     gbc.fill = 1;
/* 33 */     this.label = new JLabel(label);
/* 34 */     this.label.setPreferredSize(CommonFinal.LABEL_SIZE);
/* 35 */     this.box = new ZColorComboBox();
/* 36 */     this.box.addActionListener(this);
/* 37 */     this.box.setColor(color);
/* 38 */     add(this.label);
/* 39 */     gbc.weightx = 1.0D;
/* 40 */     gbc.gridwidth = 0;
/* 41 */     add(this.box, gbc);
/*    */   }
/*    */ 
/*    */   public Object getValue()
/*    */   {
/* 46 */     return this.box.getFillStyle();
/*    */   }
/*    */ 
/*    */   public void setValue(Object object) {
/* 50 */     this.box.setFillStyle((FillStyleInterface)object);
/*    */   }
/*    */ 
/*    */   public void addChangeListener(ChangeListener l) {
/* 54 */     this.parent = l;
/*    */   }
/*    */   public void removeChangeListener() {
/* 57 */     this.parent = null;
/*    */   }
/*    */ 
/*    */   public void actionPerformed(ActionEvent e) {
/* 61 */     if (this.parent != null)
/* 62 */       this.parent.fireChange(null);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.FillStyleComponent
 * JD-Core Version:    0.6.2
 */