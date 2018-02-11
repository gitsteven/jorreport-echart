/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.chart.Gc;
/*    */ import jatools.component.chart.component.ColorComponent;
/*    */ import jatools.component.chart.component.ColorIcon;
/*    */ import jatools.component.chart.component.FillStyleFactory;
/*    */ import jatools.component.chart.component.FillStyleInterface;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import javax.swing.Box;
/*    */ import javax.swing.JLabel;
/*    */ 
/*    */ public class SingleColorPanel extends Dialog
/*    */ {
/*    */   ColorComponent single;
/*    */   Gc gc;
/*    */   ColorIcon icon;
/*    */   JLabel preview;
/*    */ 
/*    */   public SingleColorPanel()
/*    */   {
/* 24 */     GridBagLayout gbl = new GridBagLayout();
/* 25 */     setLayout(gbl);
/* 26 */     setBorder(CommonFinal.EMPTY_BORDER);
/* 27 */     GridBagConstraints gbc = new GridBagConstraints();
/* 28 */     gbc.insets = CommonFinal.INSETS;
/* 29 */     gbc.fill = 2;
/*    */ 
/* 31 */     this.single = new ColorComponent("颜色", null);
/* 32 */     this.single.addChangeListener(this);
/*    */ 
/* 34 */     gbc.weightx = 1.0D;
/* 35 */     gbc.gridwidth = 0;
/* 36 */     add(this.single, gbc);
/* 37 */     gbc.weighty = 1.0D;
/* 38 */     add(Box.createGlue(), gbc);
/*    */   }
/*    */ 
/*    */   public void show() {
/* 42 */     this.gc.setFillStyle(-1);
/* 43 */     setVals();
/* 44 */     super.show();
/* 45 */     fireChange(null);
/*    */   }
/*    */ 
/*    */   public void setObject(Object object) {
/* 49 */     this.gc = ((Gc)object);
/*    */   }
/*    */ 
/*    */   public void getVals() {
/* 53 */     this.single.getValue().setToGc(this.gc);
/*    */   }
/*    */ 
/*    */   public void setVals() {
/* 57 */     this.single.setValue(FillStyleFactory.createFillStyle(this.gc));
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.SingleColorPanel
 * JD-Core Version:    0.6.2
 */