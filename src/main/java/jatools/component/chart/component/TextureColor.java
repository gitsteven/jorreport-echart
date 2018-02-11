/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import jatools.component.chart.chart.Gc;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Image;
/*    */ import javax.swing.Icon;
/*    */ 
/*    */ public class TextureColor
/*    */   implements FillStyleInterface
/*    */ {
/*    */   int style;
/*    */   Color masterColor;
/*    */   Color secondColor;
/*    */   Image image;
/*    */   byte[] bytes;
/*    */ 
/*    */   public Component createLabel(Dimension d)
/*    */   {
/*    */     String label;
/*    */     String label;
/* 27 */     if ((this.image != null) && (this.style == -1))
/* 28 */       label = "图片";
/*    */     else
/* 30 */       label = "线纹";
/* 31 */     return new HeaderLabel(createIcon(new Dimension(20, 10)), label, d);
/*    */   }
/*    */ 
/*    */   public Icon createIcon(Dimension d) {
/* 35 */     TextureIcon icon = new TextureIcon(d);
/* 36 */     icon.setStyle(this);
/* 37 */     return icon;
/*    */   }
/*    */ 
/*    */   public int getType() {
/* 41 */     return 2;
/*    */   }
/*    */ 
/*    */   public void setToGc(Gc gc) {
/* 45 */     gc.setFillStyle(1);
/* 46 */     gc.setTexture(this.style);
/* 47 */     gc.setFillColor(this.masterColor);
/* 48 */     gc.setSecondaryFillColor(this.secondColor);
/* 49 */     gc.setImage(this.image);
/* 50 */     gc.setImageBytes(this.bytes);
/*    */   }
/*    */ 
/*    */   public void getFromGc(Gc gc) {
/* 54 */     this.style = gc.getTexture();
/* 55 */     this.masterColor = gc.getFillColor();
/* 56 */     this.secondColor = gc.getSecondaryFillColor();
/* 57 */     this.image = gc.getImage();
/* 58 */     this.bytes = gc.getImageBytes();
/*    */   }
/*    */ 
/*    */   public Color getMasterColor() {
/* 62 */     return this.masterColor;
/*    */   }
/*    */ 
/*    */   public void setMasterColor(Color masterColor) {
/* 66 */     this.masterColor = masterColor;
/*    */   }
/*    */ 
/*    */   public Color getSecondColor() {
/* 70 */     return this.secondColor;
/*    */   }
/*    */ 
/*    */   public void setSecondColor(Color secondColor) {
/* 74 */     this.secondColor = secondColor;
/*    */   }
/*    */ 
/*    */   public int getStyle() {
/* 78 */     return this.style;
/*    */   }
/*    */ 
/*    */   public void setStyle(int style) {
/* 82 */     this.style = style;
/*    */   }
/*    */ 
/*    */   public Image getImage() {
/* 86 */     return this.image;
/*    */   }
/*    */ 
/*    */   public void setImage(Image image) {
/* 90 */     this.image = image;
/*    */   }
/*    */ 
/*    */   public byte[] getBytes() {
/* 94 */     return this.bytes;
/*    */   }
/*    */ 
/*    */   public void setBytes(byte[] bytes) {
/* 98 */     this.bytes = bytes;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.TextureColor
 * JD-Core Version:    0.6.2
 */