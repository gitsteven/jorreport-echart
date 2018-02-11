/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.customizer.ChangeListener;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.Box;
/*    */ import javax.swing.JCheckBox;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public class CheckComponent extends JPanel
/*    */   implements ActionListener
/*    */ {
/*    */   JLabel l;
/*    */   JCheckBox box;
/*    */   ChangeListener parent;
/*    */ 
/*    */   public CheckComponent()
/*    */   {
/* 22 */     this("", false);
/*    */   }
/*    */   public CheckComponent(String label, boolean yesno) {
/* 25 */     setLayout(new GridBagLayout());
/* 26 */     GridBagConstraints gbc = new GridBagConstraints();
/* 27 */     gbc.insets = CommonFinal.INSETS;
/* 28 */     gbc.fill = 2;
/* 29 */     this.l = new JLabel(label);
/* 30 */     this.l.setPreferredSize(CommonFinal.LABEL_SIZE);
/* 31 */     this.box = new JCheckBox();
/* 32 */     this.box.addActionListener(this);
/* 33 */     this.box.setSelected(yesno);
/* 34 */     gbc.weightx = 0.0D;
/* 35 */     add(this.l, gbc);
/* 36 */     add(this.box, gbc);
/* 37 */     gbc.weightx = 1.0D;
/* 38 */     gbc.gridwidth = 0;
/* 39 */     add(Box.createGlue(), gbc);
/*    */   }
/*    */ 
/*    */   public boolean getValue() {
/* 43 */     return this.box.isSelected();
/*    */   }
/*    */   public void setValue(boolean yesno) {
/* 46 */     this.box.setSelected(yesno);
/*    */   }
/*    */   public void actionPerformed(ActionEvent e) {
/* 49 */     if (this.parent != null)
/* 50 */       this.parent.fireChange(null); 
/*    */   }
/*    */ 
/* 53 */   public void addChangeListener(ChangeListener l) { this.parent = l; }
/*    */ 
/*    */   public void removeChangeListener() {
/* 56 */     this.parent = null;
/*    */   }
/*    */   public void setEnabled(boolean enabled) {
/* 59 */     super.setEnabled(enabled);
/* 60 */     this.l.setEnabled(enabled);
/* 61 */     this.box.setEnabled(enabled);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.CheckComponent
 * JD-Core Version:    0.6.2
 */