/*     */ package jatools.component.chart.component;
/*     */ 
/*     */ import java.io.File;
/*     */ import javax.swing.filechooser.FileFilter;
/*     */ 
/*     */ class ImageFilter extends FileFilter
/*     */ {
/*     */   public boolean accept(File f)
/*     */   {
/* 147 */     if (f.isDirectory()) {
/* 148 */       return true;
/*     */     }
/*     */ 
/* 151 */     String extension = ZImageFileChooser.getExtension(f);
/*     */ 
/* 153 */     if (extension != null) {
/* 154 */       if ((extension.equals("tiff")) || 
/* 155 */         (extension.equals("tif")) || 
/* 156 */         (extension.equals("gif")) || 
/* 157 */         (extension.equals("jpeg")) || 
/* 158 */         (extension.equals("jpg"))) {
/* 159 */         return true;
/*     */       }
/* 161 */       return false;
/*     */     }
/*     */ 
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 170 */     return "Just Images";
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ImageFilter
 * JD-Core Version:    0.6.2
 */