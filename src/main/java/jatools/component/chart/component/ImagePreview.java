/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.File;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ 
/*     */ class ImagePreview extends JPanel
/*     */   implements PropertyChangeListener
/*     */ {
/*  94 */   ImageIcon thumbnail = null;
/*  95 */   File file = null;
/*     */   JRadioButton asBase64;
/*     */   JRadioButton asLink;
/*  98 */   ZImageViewer viewer = new ZImageViewer();
/*     */ 
/*     */   public ImagePreview(JFileChooser fc) {
/* 101 */     setPreferredSize(new Dimension(150, 50));
/* 102 */     fc.addPropertyChangeListener(this);
/* 103 */     setLayout(new BorderLayout());
/* 104 */     buildUI();
/*     */   }
/*     */ 
/*     */   public boolean asBase64() {
/* 108 */     return this.asBase64.isSelected();
/*     */   }
/*     */ 
/*     */   public void setAsBase64(boolean base64) {
/* 112 */     this.asBase64.setSelected(base64);
/*     */   }
/*     */ 
/*     */   private void buildUI() {
/* 116 */     add(this.viewer, "Center");
/*     */   }
/*     */ 
/*     */   public void loadImage() {
/* 120 */     if (this.file == null) {
/* 121 */       return;
/*     */     }
/*     */ 
/* 124 */     ImageIcon tmpIcon = new ImageIcon(this.file.getPath());
/* 125 */     this.viewer.setImageIcon(tmpIcon);
/* 126 */     this.viewer.repaint();
/*     */   }
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent e) {
/* 130 */     String prop = e.getPropertyName();
/*     */ 
/* 132 */     if (prop.equals("SelectedFileChangedProperty")) {
/* 133 */       this.file = ((File)e.getNewValue());
/*     */ 
/* 135 */       if (isShowing()) {
/* 136 */         loadImage();
/* 137 */         this.viewer.repaint();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ImagePreview
 * JD-Core Version:    0.6.2
 */