/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JSlider;
/*    */ import javax.swing.event.ChangeEvent;
/*    */ 
/*    */ public class IntComponent extends JPanel
/*    */   implements javax.swing.event.ChangeListener
/*    */ {
/*    */   jatools.component.chart.customizer.ChangeListener parent;
/*    */   JLabel labelL;
/*    */   JLabel valueL;
/*    */   JSlider slider;
/*    */   private String suffix;
/*    */ 
/*    */   public IntComponent()
/*    */   {
/* 34 */     this("new", 0, 0, 10, "");
/*    */   }
/*    */ 
/*    */   public IntComponent(String label, int start, int min, int max, String suffix)
/*    */   {
/* 40 */     if (suffix == null)
/* 41 */       this.suffix = "";
/*    */     else {
/* 43 */       this.suffix = suffix;
/*    */     }
/* 45 */     setLayout(new GridBagLayout());
/* 46 */     GridBagConstraints gbc = new GridBagConstraints();
/* 47 */     gbc.insets = CommonFinal.INSETS;
/* 48 */     gbc.fill = 2;
/* 49 */     this.labelL = new JLabel(label);
/* 50 */     this.labelL.setPreferredSize(CommonFinal.LABEL_SIZE);
/* 51 */     this.slider = new JSlider(0, min, max + 1, start);
/* 52 */     this.slider.setPreferredSize(CommonFinal.PREFERRED_SIZE);
/* 53 */     this.slider.addChangeListener(this);
/* 54 */     this.valueL = new JLabel(String.valueOf(start) + suffix);
/* 55 */     this.valueL.setHorizontalAlignment(4);
/* 56 */     this.valueL.setPreferredSize(CommonFinal.SUFFIX_LABEL_SIZE);
/*    */ 
/* 59 */     add(this.labelL, gbc);
/* 60 */     gbc.weightx = 1.0D;
/* 61 */     add(this.slider, gbc);
/* 62 */     gbc.weightx = 0.0D;
/* 63 */     gbc.gridwidth = 0;
/* 64 */     add(this.valueL, gbc);
/*    */   }
/*    */ 
/*    */   public int getValue() {
/* 68 */     return this.slider.getValue();
/*    */   }
/*    */   public void setValue(int d) {
/* 71 */     this.slider.removeChangeListener(this);
/* 72 */     this.slider.setValue(d);
/* 73 */     this.valueL.setText(String.valueOf(d) + this.suffix);
/* 74 */     this.slider.addChangeListener(this);
/*    */   }
/*    */   public void addChangeListener(jatools.component.chart.customizer.ChangeListener l) {
/* 77 */     this.parent = l;
/*    */   }
/*    */   public void removeChangeListener() {
/* 80 */     this.parent = null;
/*    */   }
/*    */   public void stateChanged(ChangeEvent e) {
/* 83 */     this.valueL.setText(String.valueOf(this.slider.getValue()) + this.suffix);
/* 84 */     if (this.parent != null)
/* 85 */       this.parent.fireChange(null);
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean yesno) {
/* 89 */     super.setEnabled(yesno);
/* 90 */     this.labelL.setEnabled(yesno);
/* 91 */     this.valueL.setEnabled(yesno);
/* 92 */     this.slider.setEnabled(yesno);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.IntComponent
 * JD-Core Version:    0.6.2
 */