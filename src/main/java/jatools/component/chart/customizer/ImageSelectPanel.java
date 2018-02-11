/*     */ package jatools.component.chart.customizer;
/*     */ 
/*     */ import jatools.component.chart.CommonFinal;
/*     */ import jatools.component.chart.chart.Gc;
/*     */ import jatools.component.chart.component.ZImageFileChooser;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Image;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ 
/*     */ public class ImageSelectPanel extends Dialog
/*     */   implements ActionListener
/*     */ {
/*     */   Gc gc;
/*     */   Image image;
/*     */   byte[] bytes;
/*     */ 
/*     */   public ImageSelectPanel()
/*     */   {
/*  28 */     GridBagLayout gbl = new GridBagLayout();
/*  29 */     setLayout(gbl);
/*  30 */     setBorder(CommonFinal.EMPTY_BORDER);
/*  31 */     GridBagConstraints gbc = new GridBagConstraints();
/*  32 */     gbc.insets = CommonFinal.INSETS;
/*  33 */     gbc.weightx = 1.0D;
/*  34 */     gbc.gridwidth = 0;
/*  35 */     gbc.fill = 2;
/*     */ 
/*  38 */     JButton button = new JButton("选择图片");
/*  39 */     button.addActionListener(this);
/*     */ 
/*  41 */     add(button, gbc);
/*  42 */     gbc.weighty = 1.0D;
/*  43 */     add(Box.createGlue(), gbc);
/*     */   }
/*     */ 
/*     */   public void show()
/*     */   {
/*  48 */     this.gc.setFillStyle(1);
/*  49 */     this.gc.setTexture(-1);
/*  50 */     setVals();
/*  51 */     super.show();
/*  52 */     fireChange(null);
/*     */   }
/*     */ 
/*     */   public void setObject(Object object) {
/*  56 */     this.gc = ((Gc)object);
/*     */   }
/*     */ 
/*     */   public void getVals() {
/*  60 */     this.gc.setImage(this.image);
/*  61 */     this.gc.setImageBytes(this.bytes);
/*     */   }
/*     */ 
/*     */   public void setVals() {
/*  65 */     this.image = this.gc.getImage();
/*  66 */     this.bytes = this.gc.getImageBytes();
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e)
/*     */   {
/*  71 */     ZImageFileChooser.getSharedInstance(this).show();
/*  72 */     File file = ZImageFileChooser.getSharedInstance(this).getSelectedFile();
/*     */     try {
/*  74 */       this.image = createImage(file);
/*     */ 
/*  76 */       FileInputStream fis = new FileInputStream(file);
/*  77 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  78 */       byte[] buf = new byte[4096];
/*     */       int c;
/*  80 */       while ((c = fis.read(buf)) != -1)
/*     */       {
/*     */         int c;
/*  81 */         bos.write(buf, 0, c);
/*     */       }
/*  83 */       this.bytes = bos.toByteArray();
/*  84 */       fis.close();
/*  85 */       bos.close();
/*     */     } catch (Exception e1) {
/*  87 */       e1.printStackTrace();
/*     */     }
/*  89 */     fireChange(null);
/*     */   }
/*     */ 
/*     */   public Image createImage(File file)
/*     */     throws Exception
/*     */   {
/* 115 */     int start = file.getName().lastIndexOf(".") + 1;
/* 116 */     String ext = file.getName().substring(start);
/* 117 */     if ((!ext.equals("jpg")) && (!ext.equals("gif")) && (!ext.equals("jpeg")) && (!ext.equals("tif")) && (!ext.equals("tiff"))) {
/* 118 */       throw new Exception("文件类型不正确");
/*     */     }
/*     */ 
/* 121 */     return ImageIO.read(file);
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.customizer.ImageSelectPanel
 * JD-Core Version:    0.6.2
 */