/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.chart.Gc;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JLabel;
/*    */ 
/*    */ public class MarkerStyle
/*    */   implements FillStyleInterface
/*    */ {
/* 15 */   Color color = Color.red;
/* 16 */   int markerSize = 10;
/* 17 */   int markerStyle = 1;
/*    */ 
/*    */   public Component createLabel(Dimension d) {
/* 20 */     JLabel label = new JLabel();
/* 21 */     label.setIcon(createIcon(d));
/* 22 */     switch (this.markerStyle) {
/*    */     case -1:
/* 24 */       label.setText("无");
/*    */     case 0:
/* 26 */       label.setText("方形");
/*    */     case 1:
/* 28 */       label.setText("菱形");
/*    */     case 3:
/* 30 */       label.setText("三角形");
/*    */     case 2:
/* 32 */       label.setText("圆形");
/*    */     }
/* 34 */     return null;
/*    */   }
/*    */ 
/*    */   public Icon createIcon(Dimension d) {
/* 38 */     MarkerIcon icon = new MarkerIcon(d);
/* 39 */     icon.setStyle(this);
/* 40 */     return icon;
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 45 */     return -2;
/*    */   }
/*    */ 
/*    */   public void setToGc(Gc gc) {
/* 49 */     gc.setMarkerSize(this.markerSize);
/* 50 */     gc.setMarkerStyle(this.markerStyle);
/*    */   }
/*    */ 
/*    */   public void getFromGc(Gc gc) {
/* 54 */     this.markerSize = gc.getMarkerSize();
/* 55 */     this.markerStyle = gc.getMarkerStyle();
/* 56 */     this.color = gc.getLineColor();
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.MarkerStyle
 * JD-Core Version:    0.6.2
 */