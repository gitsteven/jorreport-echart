/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.customizer.ColorStyleDialog;
/*    */ import jatools.component.chart.customizer.LineAndSignDialog;
/*    */ import java.awt.Component;
/*    */ import javax.swing.JDialog;
/*    */ 
/*    */ public class FillStyleChooser
/*    */ {
/*    */   FillStyleInterface style;
/*    */ 
/*    */   public FillStyleInterface getStyle()
/*    */   {
/* 15 */     return this.style;
/*    */   }
/*    */ 
/*    */   public void setStyle(FillStyleInterface style) {
/* 19 */     this.style = style;
/*    */   }
/*    */ 
/*    */   public static FillStyleInterface showDialog(Component c, String title, FillStyleInterface init, int type)
/*    */   {
/* 24 */     FillStyleChooser chooser = new FillStyleChooser();
/* 25 */     JDialog dialog = null;
/* 26 */     if (type == 0)
/* 27 */       dialog = new ColorStyleDialog((JDialog)c, title, chooser, init, 
/* 28 */         true);
/* 29 */     else if (type == 1) {
/* 30 */       dialog = new LineAndSignDialog(c, title, chooser, init, true);
/*    */     }
/* 32 */     dialog.setLocationRelativeTo(c);
/* 33 */     dialog.show();
/* 34 */     return chooser.getStyle();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.FillStyleChooser
 * JD-Core Version:    0.6.2
 */