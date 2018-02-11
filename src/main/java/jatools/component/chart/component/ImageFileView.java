/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import java.io.File;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.filechooser.FileView;
/*     */ 
/*     */ class ImageFileView extends FileView
/*     */ {
/* 176 */   ImageIcon jpgIcon = new ImageIcon("icons/jpgIcon.gif");
/* 177 */   ImageIcon gifIcon = new ImageIcon("icons/gifIcon.gif");
/* 178 */   ImageIcon tiffIcon = new ImageIcon("icons/tiffIcon.gif");
/*     */ 
/*     */   public String getName(File f) {
/* 181 */     return null;
/*     */   }
/*     */ 
/*     */   public String getDescription(File f) {
/* 185 */     return null;
/*     */   }
/*     */ 
/*     */   public Boolean isTraversable(File f) {
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */   public String getTypeDescription(File f) {
/* 193 */     String extension = ZImageFileChooser.getExtension(f);
/* 194 */     String type = null;
/*     */ 
/* 196 */     if (extension != null) {
/* 197 */       if ((extension.equals("jpeg")) || 
/* 198 */         (extension.equals("jpg")))
/* 199 */         type = "JPEG Image";
/* 200 */       else if (extension.equals("gif"))
/* 201 */         type = "GIF Image";
/* 202 */       else if ((extension.equals("tiff")) || 
/* 203 */         (extension.equals("tif"))) {
/* 204 */         type = "TIFF Image";
/*     */       }
/*     */     }
/*     */ 
/* 208 */     return type;
/*     */   }
/*     */ 
/*     */   public Icon getIcon(File f) {
/* 212 */     String extension = ZImageFileChooser.getExtension(f);
/* 213 */     Icon icon = null;
/*     */ 
/* 215 */     if (extension != null) {
/* 216 */       if ((extension.equals("jpeg")) || 
/* 217 */         (extension.equals("jpg")))
/* 218 */         icon = this.jpgIcon;
/* 219 */       else if (extension.equals("gif"))
/* 220 */         icon = this.gifIcon;
/* 221 */       else if ((extension.equals("tiff")) || 
/* 222 */         (extension.equals("tif"))) {
/* 223 */         icon = this.tiffIcon;
/*     */       }
/*     */     }
/*     */ 
/* 227 */     return icon;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ImageFileView
 * JD-Core Version:    0.6.2
 */