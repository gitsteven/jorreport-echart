/*    */ package jatools.component.chart.component;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.io.File;
/*    */ import javax.swing.JFileChooser;
/*    */ 
/*    */ public class ZImageFileChooser
/*    */ {
/*    */   private static ZImageFileChooser sharedInstance;
/* 20 */   private static String newline = "\n";
/*    */   public static final String jpeg = "jpeg";
/*    */   public static final String jpg = "jpg";
/*    */   public static final String gif = "gif";
/*    */   public static final String tiff = "tiff";
/*    */   public static final String tif = "tif";
/*    */   private JFileChooser fc;
/*    */   private File file;
/*    */   private int option;
/*    */   private Component owner;
/*    */   ImagePreview preview;
/*    */ 
/*    */   public ZImageFileChooser(Component owner)
/*    */   {
/* 33 */     this.fc = new JFileChooser();
/*    */ 
/* 36 */     this.fc.setFileView(new ImageFileView());
/* 37 */     this.preview = new ImagePreview(this.fc);
/* 38 */     this.fc.setAccessory(this.preview);
/*    */ 
/* 40 */     this.owner = owner;
/*    */   }
/*    */ 
/*    */   public static String getExtension(File f) {
/* 44 */     String ext = null;
/* 45 */     String s = f.getName();
/* 46 */     int i = s.lastIndexOf('.');
/*    */ 
/* 48 */     if ((i > 0) && (i < s.length() - 1)) {
/* 49 */       ext = s.substring(i + 1).toLowerCase();
/*    */     }
/*    */ 
/* 52 */     return ext;
/*    */   }
/*    */ 
/*    */   public boolean asBase64() {
/* 56 */     return this.preview.asBase64();
/*    */   }
/*    */ 
/*    */   public void setAsBase64(boolean base64) {
/* 60 */     this.preview.setAsBase64(base64);
/*    */   }
/*    */ 
/*    */   public static ZImageFileChooser getSharedInstance(Component owner) {
/* 64 */     if (sharedInstance == null) {
/* 65 */       sharedInstance = new ZImageFileChooser(owner);
/*    */     }
/*    */ 
/* 68 */     return sharedInstance;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 72 */     getSharedInstance(null).show();
/*    */   }
/*    */ 
/*    */   public boolean show() {
/* 76 */     boolean option = false;
/* 77 */     int returnVal = this.fc.showDialog(this.owner, "确定");
/*    */ 
/* 79 */     if (returnVal == 0) {
/* 80 */       this.file = this.fc.getSelectedFile();
/* 81 */       option = true;
/*    */     }
/*    */ 
/* 84 */     return option;
/*    */   }
/*    */ 
/*    */   public File getSelectedFile() {
/* 88 */     return this.file;
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.component.ZImageFileChooser
 * JD-Core Version:    0.6.2
 */