/*    */ package jatools.component.chart.customizer;
/*    */ 
/*    */ import jatools.component.chart.CommonFinal;
/*    */ import jatools.component.chart.chart.Gc;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import javax.swing.JTabbedPane;
/*    */ 
/*    */ public class FillStylePanel extends Dialog
/*    */ {
/*    */   Gc gc;
/*    */   SingleColorPanel scp;
/*    */   CrossColorPanel ccp;
/*    */   CrossLinePanel clp;
/*    */   ImageSelectPanel isp;
/*    */   JTabbedPane tp;
/*    */ 
/*    */   public void setObject(Object object)
/*    */   {
/* 29 */     this.gc = ((Gc)object);
/* 30 */     initializeCustomizer();
/*    */   }
/*    */ 
/*    */   protected void initializeCustomizer() {
/* 34 */     GridBagLayout gbl = new GridBagLayout();
/* 35 */     setLayout(gbl);
/* 36 */     GridBagConstraints gbc = new GridBagConstraints();
/* 37 */     gbc.insets = CommonFinal.INSETS;
/* 38 */     gbc.fill = 1;
/* 39 */     gbc.weightx = 1.0D;
/* 40 */     gbc.weighty = 1.0D;
/* 41 */     this.tp = new JTabbedPane();
/* 42 */     this.scp = new SingleColorPanel();
/* 43 */     this.scp.setObject(this.gc);
/* 44 */     this.scp.addChangeListener(this);
/* 45 */     this.ccp = new CrossColorPanel();
/* 46 */     this.ccp.setObject(this.gc);
/* 47 */     this.ccp.addChangeListener(this);
/* 48 */     this.clp = new CrossLinePanel();
/* 49 */     this.clp.setObject(this.gc);
/* 50 */     this.clp.addChangeListener(this);
/* 51 */     this.isp = new ImageSelectPanel();
/* 52 */     this.isp.setObject(this.gc);
/* 53 */     this.isp.addChangeListener(this);
/*    */ 
/* 55 */     this.tp.add("纯色", this.scp);
/* 56 */     this.tp.add("渐变色", this.ccp);
/* 57 */     this.tp.add("线纹", this.clp);
/* 58 */     this.tp.add("图片", this.isp);
/*    */ 
/* 60 */     add(this.tp, gbc);
/*    */ 
/* 62 */     if (this.gc.getFillStyle() == -1)
/* 63 */       this.tp.setSelectedComponent(this.scp);
/* 64 */     else if (this.gc.getFillStyle() == 0)
/* 65 */       this.tp.setSelectedComponent(this.ccp);
/* 66 */     else if (this.gc.getFillStyle() == 1)
/* 67 */       if ((this.gc.getImage() != null) && (this.gc.getTexture() == -1))
/* 68 */         this.tp.setSelectedComponent(this.isp);
/*    */       else
/* 70 */         this.tp.setSelectedComponent(this.clp);
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
 * Qualified Name:     jatools.component.chart.customizer.FillStylePanel
 * JD-Core Version:    0.6.2
 */