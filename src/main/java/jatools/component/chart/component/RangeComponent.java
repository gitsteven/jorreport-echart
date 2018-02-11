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
/*    */ public class RangeComponent extends JPanel
/*    */   implements javax.swing.event.ChangeListener
/*    */ {
/*    */   jatools.component.chart.customizer.ChangeListener parent;
/*    */   JLabel labelL;
/*    */   JLabel valueL;
/*    */   JSlider slider;
/*    */ 
/*    */   public RangeComponent()
/*    */   {
/* 33 */     this("", 0.0D);
/*    */   }
/*    */ 
/*    */   public RangeComponent(String label, double start)
/*    */   {
/* 39 */     setLayout(new GridBagLayout());
/* 40 */     GridBagConstraints gbc = new GridBagConstraints();
/* 41 */     gbc.insets = CommonFinal.INSETS;
/* 42 */     gbc.fill = 1;
/* 43 */     this.labelL = new JLabel(label);
/* 44 */     this.labelL.setPreferredSize(CommonFinal.LABEL_SIZE);
/* 45 */     this.slider = new JSlider(0, 0, 100, (int)(start * 100.0D));
/* 46 */     this.slider.setPreferredSize(CommonFinal.PREFERRED_SIZE);
/* 47 */     this.slider.addChangeListener(this);
/* 48 */     this.valueL = new JLabel("blank");
/* 49 */     this.valueL.setPreferredSize(CommonFinal.SUFFIX_LABEL_SIZE);
/* 50 */     this.valueL.setHorizontalAlignment(4);
/* 51 */     setLabelText((int)(start * 100.0D));
/*    */ 
/* 53 */     add(this.labelL, gbc);
/* 54 */     gbc.weightx = 1.0D;
/* 55 */     add(this.slider, gbc);
/* 56 */     gbc.weightx = 0.0D;
/* 57 */     gbc.gridwidth = 0;
/* 58 */     add(this.valueL, gbc);
/*    */   }
/*    */ 
/*    */   public double getValue() {
/* 62 */     return this.slider.getValue() / 100.0D;
/*    */   }
/*    */ 
/*    */   private void setLabelText(int i)
/*    */   {
/* 71 */     this.valueL.setText(String.valueOf(i) + "%");
/*    */   }
/*    */   public void setValue(double d) {
/* 74 */     this.slider.removeChangeListener(this);
/* 75 */     this.slider.setValue((int)(d * 100.0D));
/* 76 */     this.slider.addChangeListener(this);
/* 77 */     setLabelText(this.slider.getValue());
/*    */   }
/*    */   public void addChangeListener(jatools.component.chart.customizer.ChangeListener l) {
/* 80 */     this.parent = l;
/*    */   }
/*    */   public void removeChangeListener() {
/* 83 */     this.parent = null;
/*    */   }
/*    */   public void stateChanged(ChangeEvent e) {
/* 86 */     setLabelText(this.slider.getValue());
/* 87 */     if (this.parent != null)
/* 88 */       this.parent.fireChange(null);
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean yesno) {
/* 92 */     super.setEnabled(yesno);
/* 93 */     this.labelL.setEnabled(yesno);
/* 94 */     this.valueL.setEnabled(yesno);
/* 95 */     this.slider.setEnabled(yesno);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.RangeComponent
 * JD-Core Version:    0.6.2
 */