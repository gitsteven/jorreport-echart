/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.chart.Gc;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class LineAndSignPanel extends Dialog
/*    */ {
/*    */   LinePanel lp;
/*    */   SignPanel sp;
/*    */   JTabbedPane tp;
/*    */   Gc gc;
/*    */   IconType iconType;
/*    */   private boolean full;
/*    */ 
/*    */   public LineAndSignPanel(IconType iconType, boolean full)
/*    */   {
/* 33 */     this.iconType = iconType;
/* 34 */     this.full = full;
/*    */   }
/*    */ 
/*    */   public void setObject(Object object)
/*    */   {
/* 39 */     this.gc = ((Gc)object);
/* 40 */     initializeCustomizer();
/*    */   }
/*    */ 
/*    */   protected void initializeCustomizer()
/*    */   {
/* 49 */     GridBagLayout gbl = new GridBagLayout();
/* 50 */     setLayout(gbl);
/* 51 */     GridBagConstraints gbc = new GridBagConstraints();
/* 52 */     gbc.insets = CommonFinal.INSETS;
/* 53 */     gbc.fill = 1;
/*    */ 
/* 55 */     this.tp = new JTabbedPane();
/*    */ 
/* 57 */     this.sp = new SignPanel(this.iconType);
/* 58 */     this.sp.setObject(this.gc);
/* 59 */     this.sp.addChangeListener(this);
/* 60 */     this.lp = new LinePanel(this.iconType);
/* 61 */     this.lp.setObject(this.gc);
/* 62 */     this.lp.addChangeListener(this);
/*    */ 
/* 64 */     this.tp.add("线形", this.lp);
/* 65 */     if (this.full) {
/* 66 */       this.tp.add("标记", this.sp);
/*    */     }
/* 68 */     gbc.weightx = 1.0D;
/* 69 */     gbc.weighty = 1.0D;
/* 70 */     add(this.tp, gbc);
/*    */   }
/*    */ 
/*    */   public void getVals()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setVals()
/*    */   {
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.LineAndSignPanel
 * JD-Core Version:    0.6.2
 */