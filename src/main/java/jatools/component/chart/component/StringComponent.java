/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.customizer.ChangeListener;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.event.KeyEvent;
/*    */ import java.awt.event.KeyListener;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ public class StringComponent extends JPanel
/*    */   implements KeyListener
/*    */ {
/*    */   JLabel l;
/*    */   JTextField t;
/*    */   ChangeListener parent;
/*    */ 
/*    */   public StringComponent()
/*    */   {
/* 22 */     this("", null);
/*    */   }
/*    */ 
/*    */   public StringComponent(String label, String text) {
/* 26 */     setLayout(new GridBagLayout());
/* 27 */     GridBagConstraints gbc = new GridBagConstraints();
/* 28 */     gbc.insets = CommonFinal.INSETS;
/* 29 */     gbc.fill = 2;
/* 30 */     this.l = new JLabel(label);
/* 31 */     this.l.setPreferredSize(CommonFinal.SHORT_LABEL_SIZE);
/* 32 */     this.t = new JTextField(text);
/* 33 */     this.t.setPreferredSize(CommonFinal.PREFERRED_SIZE);
/* 34 */     this.t.addKeyListener(this);
/* 35 */     add(this.l, gbc);
/* 36 */     gbc.weightx = 1.0D;
/* 37 */     gbc.gridwidth = 0;
/* 38 */     add(this.t, gbc);
/*    */   }
/*    */ 
/*    */   public void setValue(Object object) {
/* 42 */     this.t.setText((String)object);
/*    */   }
/*    */ 
/*    */   public Object getValue() {
/* 46 */     return this.t.getText();
/*    */   }
/*    */ 
/*    */   public void addChangeListener(ChangeListener l) {
/* 50 */     this.parent = l;
/*    */   }
/*    */   public void removeChangeListener() {
/* 53 */     this.parent = null;
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean yesno) {
/* 57 */     super.setEnabled(yesno);
/* 58 */     this.l.setEnabled(yesno);
/* 59 */     this.t.setEnabled(yesno);
/*    */   }
/*    */ 
/*    */   public void keyPressed(KeyEvent e) {
/*    */   }
/*    */ 
/*    */   public void keyReleased(KeyEvent e) {
/* 66 */     if (this.parent != null)
/* 67 */       this.parent.fireChange(null);
/*    */   }
/*    */ 
/*    */   public void keyTyped(KeyEvent e)
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.StringComponent
 * JD-Core Version:    0.6.2
 */