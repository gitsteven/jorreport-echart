/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.chart.Globals;
/*    */ import jatools.component.chart.chart.PieChart;
/*    */ import jatools.component.chart.chart._Chart;
/*    */ import jatools.component.chart.component.CheckComponent;
/*    */ import jatools.component.chart.component.IntComponent;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import javax.swing.Box;
/*    */ 
/*    */ public class ThreeDPanel extends Dialog
/*    */ {
/*    */   private CheckComponent isThreeD;
/*    */   private IntComponent xDepth;
/*    */   private IntComponent yDepth;
/*    */ 
/*    */   public ThreeDPanel()
/*    */   {
/* 25 */     GridBagLayout gbl = new GridBagLayout();
/* 26 */     setLayout(gbl);
/*    */ 
/* 28 */     GridBagConstraints gbc = new GridBagConstraints();
/* 29 */     gbc.insets = CommonFinal.INSETS;
/* 30 */     gbc.fill = 2;
/*    */ 
/* 32 */     this.isThreeD = new CheckComponent("3D", false);
/* 33 */     this.isThreeD.addChangeListener(this);
/* 34 */     this.xDepth = new IntComponent("深度 x:", 15, 0, 30, "");
/* 35 */     this.xDepth.addChangeListener(this);
/* 36 */     this.yDepth = new IntComponent("     y:", 15, 0, 30, "");
/* 37 */     this.yDepth.addChangeListener(this);
/*    */ 
/* 47 */     SepratorPanel sp = new SepratorPanel("3D效果");
/* 48 */     gbc.weightx = 1.0D;
/* 49 */     gbc.gridwidth = 0;
/* 50 */     add(sp, gbc);
/* 51 */     gbc.weightx = 0.0D;
/* 52 */     add(this.isThreeD, gbc);
/* 53 */     gbc.weightx = 1.0D;
/* 54 */     gbc.gridwidth = 0;
/* 55 */     add(this.xDepth, gbc);
/* 56 */     add(this.yDepth, gbc);
/* 57 */     gbc.weighty = 1.0D;
/* 58 */     add(Box.createGlue(), gbc);
/*    */   }
/*    */ 
/*    */   public void setObject(Object object)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void getVals()
/*    */   {
/* 68 */     this.chart.setThreeD(this.isThreeD.getValue());
/* 69 */     this.chart.getGlobals().setXOffset(this.xDepth.getValue());
/* 70 */     this.chart.getGlobals().setYOffset(this.yDepth.getValue());
/*    */ 
/* 72 */     validateEnabled();
/*    */   }
/*    */ 
/*    */   public void setVals() {
/* 76 */     this.isThreeD.setValue(this.chart.getThreeD());
/* 77 */     this.xDepth.setValue(this.chart.getGlobals().getXOffset());
/* 78 */     this.yDepth.setValue(this.chart.getGlobals().getYOffset());
/*    */ 
/* 80 */     validateEnabled();
/*    */   }
/*    */ 
/*    */   private void validateEnabled() {
/* 84 */     this.xDepth.setEnabled(this.isThreeD.getValue());
/* 85 */     this.yDepth.setEnabled(this.isThreeD.getValue());
/* 86 */     if ((this.chart instanceof PieChart))
/* 87 */       this.xDepth.setEnabled(false);
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean yesno)
/*    */   {
/* 92 */     super.setEnabled(yesno);
/* 93 */     this.isThreeD.setEnabled(yesno);
/* 94 */     this.xDepth.setEnabled(yesno);
/* 95 */     this.yDepth.setEnabled(yesno);
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.ThreeDPanel
 * JD-Core Version:    0.6.2
 */