/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.chart.Gc;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import javax.swing.Icon;
/*    */ 
/*    */ public class GradiantColor
/*    */   implements FillStyleInterface
/*    */ {
/*    */   int style;
/*    */   Color masterColor;
/*    */   Color secondColor;
/* 18 */   private String label = "渐变色";
/*    */ 
/*    */   public Color getMasterColor() {
/* 21 */     return this.masterColor;
/*    */   }
/*    */ 
/*    */   public void setMasterColor(Color masterColor) {
/* 25 */     this.masterColor = masterColor;
/*    */   }
/*    */ 
/*    */   public Color getSecondColor() {
/* 29 */     return this.secondColor;
/*    */   }
/*    */ 
/*    */   public void setSecondColor(Color secondColor) {
/* 33 */     this.secondColor = secondColor;
/*    */   }
/*    */ 
/*    */   public int getStyle() {
/* 37 */     return this.style;
/*    */   }
/*    */ 
/*    */   public void setStyle(int style) {
/* 41 */     this.style = style;
/*    */   }
/*    */ 
/*    */   public Component createLabel(Dimension d) {
/* 45 */     return new HeaderLabel(createIcon(new Dimension(20, 10)), this.label, d);
/*    */   }
/*    */ 
/*    */   public Icon createIcon(Dimension d) {
/* 49 */     GradiantIcon icon = new GradiantIcon(d);
/* 50 */     icon.setStyle(this);
/* 51 */     return icon;
/*    */   }
/*    */ 
/*    */   public void setToGc(Gc gc) {
/* 55 */     gc.setFillStyle(0);
/* 56 */     gc.setGradient(this.style);
/* 57 */     gc.setFillColor(this.masterColor);
/* 58 */     gc.setSecondaryFillColor(this.secondColor);
/*    */   }
/*    */ 
/*    */   public void getFromGc(Gc gc) {
/* 62 */     this.style = gc.getGradient();
/* 63 */     this.masterColor = gc.getFillColor();
/* 64 */     this.secondColor = gc.getSecondaryFillColor();
/*    */   }
/*    */ 
/*    */   public int getType() {
/* 68 */     return 1;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.GradiantColor
 * JD-Core Version:    0.6.2
 */