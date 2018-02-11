/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.chart.Gc;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import javax.swing.Icon;
/*    */ 
/*    */ public class SingleColor
/*    */   implements FillStyleInterface
/*    */ {
/*    */   Color color;
/* 16 */   private String label = "纯色";
/*    */ 
/*    */   public SingleColor(Color color) {
/* 19 */     this.color = color;
/*    */   }
/*    */ 
/*    */   public void setColor(Color color) {
/* 23 */     this.color = color;
/*    */   }
/*    */ 
/*    */   public Color getColor() {
/* 27 */     return this.color;
/*    */   }
/*    */ 
/*    */   public Component createLabel(Dimension d) {
/* 31 */     return new HeaderLabel(createIcon(new Dimension(20, 10)), this.label, d);
/*    */   }
/*    */ 
/*    */   public Icon createIcon(Dimension d) {
/* 35 */     ColorIcon icon = new ColorIcon(d);
/* 36 */     icon.setStyle(this);
/* 37 */     return icon;
/*    */   }
/*    */ 
/*    */   public void setToGc(Gc gc) {
/* 41 */     gc.setFillStyle(-1);
/* 42 */     gc.setFillColor(this.color);
/*    */   }
/*    */ 
/*    */   public void getFromGc(Gc gc) {
/* 46 */     this.color = gc.getFillColor();
/*    */   }
/*    */ 
/*    */   public int getType() {
/* 50 */     return 0;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.SingleColor
 * JD-Core Version:    0.6.2
 */