/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import com.sun.image.codec.jpeg.JPEGCodec;
/*     */ import com.sun.image.codec.jpeg.JPEGImageEncoder;
/*     */ import jatools.component.chart.chart.ChartInterface;
/*     */ import jatools.component.chart.chart.RotateString;
/*     */ import jatools.component.chart.utility.FlashRenderer;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ class CacheManager
/*     */ {
/*     */   static final boolean demoMode = false;
/*  32 */   protected ChartInterface chart = null;
/*  33 */   private String fileName = null;
/*  34 */   private String fileNamePrefix = null;
/*  35 */   private int imageWidth = 200;
/*  36 */   private int imageHeight = 150;
/*  37 */   private String writeDirectory = "public_html/images";
/*  38 */   private String parentString = null;
/*     */   private Bean parentShell;
/*  40 */   protected boolean useCache = true;
/*  41 */   protected boolean byteStream = false;
/*  42 */   private boolean noisy = false;
/*  43 */   byte[] bytes = null;
/*  44 */   String imageType = "j_png";
/*  45 */   long startTime = System.currentTimeMillis();
/*     */   Image image;
/*     */ 
/*     */   public CacheManager(Bean ps)
/*     */   {
/*  54 */     setParent(ps);
/*  55 */     this.parentString = ps.getClass().toString();
/*  56 */     this.fileName = getParameter("fileName");
/*  57 */     this.fileNamePrefix = getParameter("fileNamePrefix");
/*     */   }
/*     */ 
/*     */   private String digestToString(byte[] digestBits)
/*     */   {
/*  64 */     ByteArrayOutputStream ou = new ByteArrayOutputStream();
/*  65 */     PrintStream p = new PrintStream(ou);
/*     */ 
/*  67 */     if (digestBits != null)
/*  68 */       for (int i = 0; i < digestBits.length; i++)
/*  69 */         hexDigit(p, digestBits[i]);
/*     */     else {
/*  71 */       p.print("<incomplete>");
/*     */     }
/*     */ 
/*  74 */     p.println();
/*     */ 
/*  76 */     return ou.toString();
/*     */   }
/*     */ 
/*     */   private void doDemoMode(Graphics g)
/*     */   {
/*  85 */     g.setXORMode(Color.white);
/*  86 */     g.setColor(Color.black);
/*  87 */     g.setFont(new Font("SanSerif", 0, 12));
/*  88 */     g.drawString("chart images from VE.com", 4, this.imageHeight - 4);
/*     */   }
/*     */ 
/*     */   public void generate()
/*     */     throws Exception
/*     */   {
/*  96 */     if (this.parentShell.userImagingCodec != null) {
/*  97 */       writeOutputWithUserCodec();
/*     */ 
/*  99 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 103 */       if ((this.imageType != null) && ((this.imageType.equalsIgnoreCase("dbl")) || (this.imageType.equalsIgnoreCase("flash")))) {
/* 104 */         writeOutput_Flash();
/* 105 */         this.image = setupAndDrawChart();
/*     */ 
/* 107 */         return;
/*     */       }
/*     */ 
/* 110 */       if ((this.imageType != null) && ((this.imageType.equalsIgnoreCase("swf")) || (this.imageType.equalsIgnoreCase("flash")))) {
/* 111 */         writeOutput_Flash();
/*     */ 
/* 113 */         return;
/*     */       }
/*     */ 
/* 118 */       if ((this.imageType != null) && (this.imageType.equalsIgnoreCase("svg"))) {
/* 119 */         writeOutput_SVG();
/*     */ 
/* 121 */         return;
/*     */       }
/*     */ 
/* 124 */       this.image = setupAndDrawChart();
/*     */ 
/* 126 */       if (this.noisy) {
/* 127 */         long now = System.currentTimeMillis() - this.startTime;
/* 128 */         this.parentShell.log("start encoding:" + now);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 141 */       this.parentShell.log("chart can't create image");
/* 142 */       throw e;
/*     */     }
/*     */ 
/* 145 */     if (this.noisy) {
/* 146 */       long now = System.currentTimeMillis() - this.startTime;
/* 147 */       this.parentShell.log("end encoding:" + now);
/*     */     }
/*     */   }
/*     */ 
/*     */   public byte[] getImageBytes()
/*     */     throws Exception
/*     */   {
/* 156 */     if (this.noisy) {
/* 157 */       long now = System.currentTimeMillis() - this.startTime;
/* 158 */       this.parentShell.log("getting image bytes:" + now);
/*     */     }
/*     */ 
/* 161 */     if (!isCached()) {
/* 162 */       if (this.bytes == null) {
/* 163 */         if (this.noisy) {
/* 164 */           long now = System.currentTimeMillis() - this.startTime;
/* 165 */           this.parentShell.log("start generation:" + now);
/*     */         }
/*     */ 
/* 168 */         generate();
/*     */ 
/* 170 */         if (this.noisy) {
/* 171 */           long now = System.currentTimeMillis() - this.startTime;
/* 172 */           this.parentShell.log("end generation:" + now);
/*     */         }
/*     */       }
/*     */ 
/* 176 */       return this.bytes;
/*     */     }
/*     */ 
/* 179 */     FileInputStream fi = null;
/*     */     try
/*     */     {
/* 183 */       File f = new File(this.writeDirectory + "/" + getImageName());
/* 184 */       fi = new FileInputStream(f);
/*     */ 
/* 186 */       long fileSize = f.length();
/*     */ 
/* 188 */       if (this.noisy) {
/* 189 */         long now = System.currentTimeMillis() - this.startTime;
/* 190 */         this.parentShell.log("reading bytes:" + now);
/*     */       }
/*     */ 
/* 193 */       this.bytes = new byte[(int)fileSize];
/* 194 */       fi.read(this.bytes);
/*     */     } catch (Exception e) {
/* 196 */       this.parentShell.log("problem retrieving image bytes: " + e.getMessage());
/*     */     } finally {
/* 198 */       fi.close();
/*     */     }
/*     */ 
/* 202 */     return this.bytes;
/*     */   }
/*     */ 
/*     */   public String getImageName()
/*     */   {
/* 211 */     if (this.parentShell == null) {
/* 212 */       this.parentShell.log("CacheManager: must set parent before retrieving file name");
/*     */ 
/* 214 */       return null;
/*     */     }
/*     */ 
/* 217 */     if (this.fileName != null) {
/* 218 */       return this.fileName;
/*     */     }
/*     */ 
/* 221 */     Enumeration en = this.parentShell.getParameterNames();
/* 222 */     StringBuffer sb = new StringBuffer();
/* 223 */     sb.append(this.parentString);
/*     */ 
/* 225 */     while (en.hasMoreElements()) {
/* 226 */       String param = (String)en.nextElement();
/* 227 */       sb.append(param);
/* 228 */       sb.append(getParameter(param));
/*     */     }
/*     */     try
/*     */     {
/* 232 */       MessageDigest md = MessageDigest.getInstance("SHA");
/* 233 */       this.fileName = digestToString(md.digest(sb.toString().getBytes())).substring(0, 27);
/*     */ 
/* 235 */       if (this.noisy) {
/* 236 */         this.parentShell.log("digestString is " + this.fileName);
/*     */       }
/*     */ 
/* 239 */       if (this.fileNamePrefix != null)
/* 240 */         this.fileName = (this.fileNamePrefix + this.fileName + imageTypeSuffix());
/*     */       else
/* 242 */         this.fileName += imageTypeSuffix();
/*     */     }
/*     */     catch (Exception e) {
/* 245 */       e.printStackTrace();
/* 246 */       this.parentShell.log("CacheManager: message digest problem");
/*     */     }
/*     */ 
/* 249 */     return this.fileName;
/*     */   }
/*     */ 
/*     */   protected String getParameter(String name)
/*     */   {
/* 261 */     return this.parentShell.getParameter(name);
/*     */   }
/*     */ 
/*     */   private static void hexDigit(PrintStream p, byte x)
/*     */   {
/* 271 */     char c = (char)(x >> 4 & 0xF);
/*     */ 
/* 273 */     if (c > '\t')
/* 274 */       c = (char)(c - '\n' + 97);
/*     */     else {
/* 276 */       c = (char)(c + '0');
/*     */     }
/*     */ 
/* 279 */     p.write(c);
/*     */ 
/* 281 */     c = (char)(x & 0xF);
/*     */ 
/* 283 */     if (c > '\t')
/* 284 */       c = (char)(c - '\n' + 97);
/*     */     else {
/* 286 */       c = (char)(c + '0');
/*     */     }
/*     */ 
/* 289 */     p.write(c);
/*     */   }
/*     */ 
/*     */   private String imageTypeSuffix()
/*     */   {
/* 298 */     if (this.imageType == null)
/* 299 */       return ".jpg";
/* 300 */     if (this.imageType.equalsIgnoreCase("gifmaker"))
/* 301 */       return ".gif";
/* 302 */     if (this.imageType.equalsIgnoreCase("jpeg"))
/* 303 */       return ".jpg";
/* 304 */     if (this.imageType.equalsIgnoreCase("bmp"))
/* 305 */       return ".bmp";
/* 306 */     if (this.imageType.startsWith("j_"))
/*     */     {
/* 309 */       return "." + this.imageType.substring(2);
/* 310 */     }if (this.imageType.equalsIgnoreCase("svg"))
/* 311 */       return ".svg";
/* 312 */     if ((this.imageType.equalsIgnoreCase("swf")) || (this.imageType.equalsIgnoreCase("flash"))) {
/* 313 */       return ".swf";
/*     */     }
/* 315 */     return ".jpg";
/*     */   }
/*     */ 
/*     */   boolean isCached()
/*     */   {
/* 325 */     if (!this.useCache) {
/* 326 */       return false;
/*     */     }
/*     */ 
/* 329 */     String imageFile = this.writeDirectory + "/" + getImageName();
/* 330 */     File f = new File(imageFile);
/*     */ 
/* 332 */     if (this.noisy) {
/* 333 */       this.parentShell.log("image should be written to " + f.getAbsolutePath());
/*     */     }
/*     */ 
/* 336 */     if ((f.exists()) && 
/* 337 */       (f.canRead())) {
/* 338 */       if (this.noisy) {
/* 339 */         this.parentShell.log("image cache file already exists, is readable");
/*     */       }
/*     */ 
/* 342 */       return true;
/*     */     }
/*     */ 
/* 346 */     return false;
/*     */   }
/*     */ 
/*     */   void setImageName(String s)
/*     */   {
/* 354 */     this.fileName = s;
/*     */   }
/*     */ 
/*     */   void setParent(Bean parentShell)
/*     */   {
/* 363 */     this.parentShell = parentShell;
/*     */ 
/* 365 */     String str = parentShell.getParameter("debug");
/*     */ 
/* 367 */     if ((str != null) && 
/* 368 */       (str.equalsIgnoreCase("true"))) {
/* 369 */       this.noisy = true;
/*     */ 
/* 371 */       long startTime = System.currentTimeMillis();
/* 372 */       parentShell.log("debugging output on...");
/*     */     }
/*     */ 
/* 376 */     str = parentShell.getParameter("writeDirectory");
/*     */ 
/* 378 */     if (str != null) {
/* 379 */       this.writeDirectory = str;
/*     */     }
/*     */ 
/* 382 */     str = parentShell.getParameter("useCache");
/*     */ 
/* 384 */     if (str != null) {
/* 385 */       if (str.equalsIgnoreCase("true"))
/* 386 */         this.useCache = true;
/*     */       else {
/* 388 */         this.useCache = false;
/*     */       }
/*     */     }
/*     */ 
/* 392 */     str = getParameter("byteStream");
/*     */ 
/* 394 */     if ((str != null) && 
/* 395 */       (str.equalsIgnoreCase("true"))) {
/* 396 */       this.byteStream = true;
/*     */     }
/*     */ 
/* 400 */     str = getParameter("width");
/*     */ 
/* 402 */     if (str != null) {
/* 403 */       this.imageWidth = Integer.parseInt(str);
/*     */     }
/*     */ 
/* 406 */     str = getParameter("height");
/*     */ 
/* 408 */     if (str != null)
/* 409 */       this.imageHeight = Integer.parseInt(str);
/*     */   }
/*     */ 
/*     */   Image setupAndDrawChart()
/*     */   {
/* 420 */     Frame frame = null;
/* 421 */     Image image = null;
/*     */     try
/*     */     {
/* 424 */       if (this.noisy) {
/* 425 */         long now = System.currentTimeMillis() - this.startTime;
/* 426 */         this.parentShell.log("create Image buffer:" + now);
/*     */       }
/*     */ 
/* 429 */       Class imgClass = Class.forName("java.awt.image.BufferedImage");
/* 430 */       Class intClass = Integer.TYPE;
/* 431 */       constructorArgs = new Class[3];
/* 432 */       constructorArgs[0] = intClass;
/* 433 */       constructorArgs[1] = intClass;
/* 434 */       constructorArgs[2] = intClass;
/*     */ 
/* 436 */       Constructor buffImgConst = imgClass.getConstructor(constructorArgs);
/* 437 */       Object[] instanceArgs = new Object[3];
/* 438 */       instanceArgs[0] = new Integer(this.imageWidth);
/* 439 */       instanceArgs[1] = new Integer(this.imageHeight);
/* 440 */       instanceArgs[2] = new Integer(1);
/* 441 */       image = (Image)buffImgConst.newInstance(instanceArgs);
/*     */ 
/* 450 */       Java2DRenderer renderer = new Java2DRenderer();
/* 451 */       this.chart.resize(this.imageWidth, this.imageHeight);
/* 452 */       this.chart.setStringRotator(new RotateString(frame));
/* 453 */       this.chart.setImage(image);
/*     */ 
/* 455 */       if (this.noisy) {
/* 456 */         long now = System.currentTimeMillis() - this.startTime;
/* 457 */         this.parentShell.log("draw chart:" + now);
/*     */       }
/*     */ 
/* 460 */       renderer.draw(image, this.chart, this.parentShell);
/*     */ 
/* 462 */       if (this.noisy) {
/* 463 */         long now = System.currentTimeMillis() - this.startTime;
/* 464 */         this.parentShell.log("done drawing:" + now);
/*     */       }
/*     */ 
/* 471 */       return image;
/*     */     } catch (Exception noBufferedImage) {
/* 473 */       frame = new Frame();
/* 474 */       frame.addNotify();
/* 475 */       image = frame.createImage(this.imageWidth, this.imageHeight);
/*     */ 
/* 478 */       Graphics g = image.getGraphics();
/*     */ 
/* 480 */       if (this.noisy) {
/* 481 */         long now = System.currentTimeMillis() - this.startTime;
/* 482 */         this.parentShell.log("draw to jdk1.1f:" + now);
/*     */       }
/*     */ 
/* 485 */       this.chart.resize(this.imageWidth, this.imageHeight);
/* 486 */       this.chart.setStringRotator(new RotateString(frame));
/* 487 */       this.chart.setImage(image);
/*     */       try
/*     */       {
/* 490 */         this.chart.drawGraph(g);
/* 491 */         this.parentShell.drawMyStuff(g);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 497 */         this.parentShell.log("chart couldn't write image");
/* 498 */         e.printStackTrace();
/*     */ 
/* 500 */         g.dispose();
/*     */         try
/*     */         {
/* 503 */           if (frame != null)
/* 504 */             frame.removeNotify();
/*     */         }
/*     */         catch (Exception localException1)
/*     */         {
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/* 500 */         g.dispose();
/*     */         try
/*     */         {
/* 503 */           if (frame != null)
/* 504 */             frame.removeNotify();
/*     */         }
/*     */         catch (Exception localException2) {
/*     */         }
/*     */       }
/*     */     }
/* 510 */     return image;
/*     */   }
/*     */ 
/*     */   private void writeOutput_GifEncoder(Image image)
/*     */     throws IOException
/*     */   {
/* 521 */     if (this.noisy) {
/* 522 */       this.parentShell.log("creating image file");
/*     */     }
/*     */ 
/* 525 */     OutputStream gif_file = new ByteArrayOutputStream();
/* 526 */     Object[] params = { image, gif_file };
/*     */     try
/*     */     {
/* 529 */       Class gifEncoder = Class.forName("Acme.JPM.Encoders.GifEncoder");
/* 530 */       Object encoder = gifEncoder.getConstructors()[0].newInstance(params);
/* 531 */       gifEncoder.getMethod("encode", null).invoke(encoder, null);
/*     */     } catch (Exception e) {
/* 533 */       this.parentShell.log("Problem accessing Acme.JPM.Encoder.GifEncoder");
/* 534 */       this.parentShell.log("Please check CLASSPATH");
/* 535 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 538 */     gif_file.close();
/* 539 */     this.bytes = ((ByteArrayOutputStream)gif_file).toByteArray();
/*     */ 
/* 541 */     if ((this.useCache) || (!this.byteStream)) {
/* 542 */       gif_file = new FileOutputStream(this.writeDirectory + "/" + getImageName());
/* 543 */       gif_file.write(this.bytes);
/* 544 */       gif_file.close();
/*     */     }
/*     */ 
/* 547 */     if (this.noisy)
/* 548 */       this.parentShell.log("created image file");
/*     */   }
/*     */ 
/*     */   private void writeOutput_SVG()
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ 
/*     */   private void writeOutput_Flash()
/*     */     throws IOException
/*     */   {
/* 578 */     this.chart.resize(this.imageWidth, this.imageHeight);
/* 579 */     FlashRenderer renderer = new FlashRenderer();
/* 580 */     renderer.draw(this.chart, this.parentShell);
/* 581 */     byte[] bytes = renderer.getSWFBytes();
/*     */ 
/* 583 */     this.bytes = bytes;
/*     */   }
/*     */ 
/*     */   private void writeOutput_Jimi(Image image)
/*     */     throws IOException
/*     */   {
/* 596 */     OutputStream out = new ByteArrayOutputStream();
/*     */ 
/* 604 */     this.bytes = ((ByteArrayOutputStream)out).toByteArray();
/*     */ 
/* 606 */     if ((this.useCache) || (!this.byteStream)) {
/* 607 */       out = new FileOutputStream(this.writeDirectory + "/" + getImageName());
/* 608 */       out.write(this.bytes);
/* 609 */       out.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeOutput_JPEG_Native(Image image) throws IOException
/*     */   {
/* 615 */     OutputStream jpg_file = new ByteArrayOutputStream();
/*     */ 
/* 617 */     JPEGImageEncoder jpg = JPEGCodec.createJPEGEncoder(jpg_file);
/* 618 */     jpg.encode((BufferedImage)image);
/*     */ 
/* 620 */     this.bytes = ((ByteArrayOutputStream)jpg_file).toByteArray();
/*     */ 
/* 622 */     if ((this.useCache) || (!this.byteStream)) {
/* 623 */       jpg_file = new FileOutputStream(this.writeDirectory + "/" + getImageName());
/* 624 */       jpg_file.write(this.bytes);
/* 625 */       jpg_file.flush();
/* 626 */       jpg_file.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeOutputWithUserCodec()
/*     */     throws IOException
/*     */   {
/* 635 */     this.chart.resize(this.imageWidth, this.imageHeight);
/*     */ 
/* 637 */     byte[] bytes = this.parentShell.userImagingCodec.drawChartToOutputStream(this.chart);
/*     */ 
/* 639 */     if (bytes == null) {
/* 640 */       Image img = setupAndDrawChart();
/* 641 */       bytes = this.parentShell.userImagingCodec.encodeImageBytes(img);
/*     */     }
/*     */ 
/* 644 */     if (bytes == null) {
/* 645 */       return;
/*     */     }
/*     */ 
/* 648 */     this.bytes = bytes;
/*     */ 
/* 652 */     if ((this.useCache) || (!this.byteStream)) {
/* 653 */       OutputStream user_file = new FileOutputStream(this.writeDirectory + "/" + getImageName());
/* 654 */       user_file.write(bytes);
/* 655 */       user_file.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean isDemoMode()
/*     */   {
/* 665 */     return false;
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.CacheManager
 * JD-Core Version:    0.6.2
 */