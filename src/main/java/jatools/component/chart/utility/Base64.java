/*      */ package jatools.component.chart.utility;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.FilterInputStream;
/*      */ import java.io.FilterOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.zip.GZIPInputStream;
/*      */ import java.util.zip.GZIPOutputStream;
/*      */ 
/*      */ public class Base64
/*      */ {
/*      */   public static final int NO_OPTIONS = 0;
/*      */   public static final int ENCODE = 1;
/*      */   public static final int DECODE = 0;
/*      */   public static final int GZIP = 2;
/*      */   public static final int DONT_BREAK_LINES = 8;
/*      */   private static final int MAX_LINE_LENGTH = 76;
/*      */   private static final byte EQUALS_SIGN = 61;
/*      */   private static final byte NEW_LINE = 10;
/*   86 */   private static final byte[] ALPHABET = { 
/*   87 */     65, 66, 67, 68, 69, 70, 71, 
/*   88 */     72, 73, 74, 75, 76, 77, 78, 
/*   89 */     79, 80, 81, 82, 83, 84, 85, 
/*   90 */     86, 87, 88, 89, 90, 
/*   91 */     97, 98, 99, 100, 101, 102, 103, 
/*   92 */     104, 105, 106, 107, 108, 109, 110, 
/*   93 */     111, 112, 113, 114, 115, 116, 117, 
/*   94 */     118, 119, 120, 121, 122, 
/*   95 */     48, 49, 50, 51, 52, 53, 
/*   96 */     54, 55, 56, 57, 43, 47 };
/*      */ 
/*  104 */   private static final byte[] DECODABET = { 
/*  105 */     -9, -9, -9, -9, -9, -9, -9, -9, -9, 
/*  106 */     -5, -5, 
/*  107 */     -9, -9, 
/*  108 */     -5, 
/*  109 */     -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
/*  110 */     -9, -9, -9, -9, -9, 
/*  111 */     -5, 
/*  112 */     -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 
/*  113 */     62, 
/*  114 */     -9, -9, -9, 
/*  115 */     63, 
/*  116 */     52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 
/*  117 */     -9, -9, -9, 
/*  118 */     -1, 
/*  119 */     -9, -9, -9, 
/*  120 */     0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 
/*  121 */     14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 
/*  122 */     -9, -9, -9, -9, -9, -9, 
/*  123 */     26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 
/*  124 */     39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 
/*  125 */     -9, -9, -9, -9 };
/*      */   private static final byte BAD_ENCODING = -9;
/*      */   private static final byte WHITE_SPACE_ENC = -5;
/*      */   private static final byte EQUALS_SIGN_ENC = -1;
/*      */ 
/*      */   private static byte[] encode3to4(byte[] threeBytes)
/*      */   {
/*  161 */     return encode3to4(threeBytes, 3);
/*      */   }
/*      */ 
/*      */   private static byte[] encode3to4(byte[] threeBytes, int numSigBytes)
/*      */   {
/*  181 */     byte[] dest = new byte[4];
/*  182 */     encode3to4(threeBytes, 0, numSigBytes, dest, 0);
/*  183 */     return dest;
/*      */   }
/*      */ 
/*      */   private static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes)
/*      */   {
/*  203 */     encode3to4(threeBytes, 0, numSigBytes, b4, 0);
/*  204 */     return b4;
/*      */   }
/*      */ 
/*      */   private static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset)
/*      */   {
/*  244 */     int inBuff = (numSigBytes > 0 ? source[srcOffset] << 24 >>> 8 : 0) | (
/*  245 */       numSigBytes > 1 ? source[(srcOffset + 1)] << 24 >>> 16 : 0) | (
/*  246 */       numSigBytes > 2 ? source[(srcOffset + 2)] << 24 >>> 24 : 0);
/*      */ 
/*  248 */     switch (numSigBytes)
/*      */     {
/*      */     case 3:
/*  251 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  252 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  253 */       destination[(destOffset + 2)] = ALPHABET[(inBuff >>> 6 & 0x3F)];
/*  254 */       destination[(destOffset + 3)] = ALPHABET[(inBuff & 0x3F)];
/*  255 */       return destination;
/*      */     case 2:
/*  258 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  259 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  260 */       destination[(destOffset + 2)] = ALPHABET[(inBuff >>> 6 & 0x3F)];
/*  261 */       destination[(destOffset + 3)] = 61;
/*  262 */       return destination;
/*      */     case 1:
/*  265 */       destination[destOffset] = ALPHABET[(inBuff >>> 18)];
/*  266 */       destination[(destOffset + 1)] = ALPHABET[(inBuff >>> 12 & 0x3F)];
/*  267 */       destination[(destOffset + 2)] = 61;
/*  268 */       destination[(destOffset + 3)] = 61;
/*  269 */       return destination;
/*      */     }
/*      */ 
/*  272 */     return destination;
/*      */   }
/*      */ 
/*      */   public static String encodeObject(Serializable serializableObject)
/*      */   {
/*  291 */     return encodeObject(serializableObject, 0);
/*      */   }
/*      */ 
/*      */   public static String encodeObject(Serializable serializableObject, int options)
/*      */   {
/*  322 */     ByteArrayOutputStream baos = null;
/*  323 */     OutputStream b64os = null;
/*  324 */     ObjectOutputStream oos = null;
/*  325 */     GZIPOutputStream gzos = null;
/*      */ 
/*  328 */     int gzip = options & 0x2;
/*  329 */     int dontBreakLines = options & 0x8;
/*      */     try
/*      */     {
/*  334 */       baos = new ByteArrayOutputStream();
/*  335 */       b64os = new OutputStream(baos, 0x1 | dontBreakLines);
/*      */ 
/*  338 */       if (gzip == 2)
/*      */       {
/*  340 */         gzos = new GZIPOutputStream(b64os);
/*  341 */         oos = new ObjectOutputStream(gzos);
/*      */       }
/*      */       else {
/*  344 */         oos = new ObjectOutputStream(b64os);
/*      */       }
/*  346 */       oos.writeObject(serializableObject);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  350 */       e.printStackTrace();
/*  351 */       return null;
/*      */     }
/*      */     finally {
/*      */       try {
/*  355 */         oos.close(); } catch (Exception localException4) {
/*      */       }try { gzos.close(); } catch (Exception localException5) {
/*      */       }try { b64os.close(); } catch (Exception localException6) {
/*      */       }try { baos.close(); } catch (Exception localException7) {
/*      */       }
/*      */     }
/*  361 */     return new String(baos.toByteArray());
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source)
/*      */   {
/*  375 */     return encodeBytes(source, 0, source.length, 0);
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int options)
/*      */   {
/*  402 */     return encodeBytes(source, 0, source.length, options);
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int off, int len)
/*      */   {
/*  417 */     return encodeBytes(source, off, len, 0);
/*      */   }
/*      */ 
/*      */   public static String encodeBytes(byte[] source, int off, int len, int options)
/*      */   {
/*  448 */     int dontBreakLines = options & 0x8;
/*  449 */     int gzip = options & 0x2;
/*      */ 
/*  452 */     if (gzip == 2)
/*      */     {
/*  454 */       ByteArrayOutputStream baos = null;
/*  455 */       GZIPOutputStream gzos = null;
/*  456 */       OutputStream b64os = null;
/*      */       try
/*      */       {
/*  462 */         baos = new ByteArrayOutputStream();
/*  463 */         b64os = new OutputStream(baos, 0x1 | dontBreakLines);
/*  464 */         gzos = new GZIPOutputStream(b64os);
/*      */ 
/*  466 */         gzos.write(source, off, len);
/*  467 */         gzos.close();
/*      */       }
/*      */       catch (IOException e)
/*      */       {
/*  471 */         e.printStackTrace();
/*  472 */         return null;
/*      */       }
/*      */       finally {
/*      */         try {
/*  476 */           gzos.close(); } catch (Exception localException3) {
/*      */         }try { b64os.close(); } catch (Exception localException4) {
/*      */         }try { baos.close(); } catch (Exception localException5) {
/*      */         }
/*      */       }
/*  481 */       return new String(baos.toByteArray());
/*      */     }
/*      */ 
/*  488 */     boolean breakLines = dontBreakLines == 0;
/*      */ 
/*  490 */     int len43 = len * 4 / 3;
/*  491 */     byte[] outBuff = new byte[len43 + (
/*  492 */       len % 3 > 0 ? 4 : 0) + (
/*  493 */       breakLines ? len43 / 76 : 0)];
/*  494 */     int d = 0;
/*  495 */     int e = 0;
/*  496 */     int len2 = len - 2;
/*  497 */     int lineLength = 0;
/*  498 */     for (; d < len2; e += 4)
/*      */     {
/*  500 */       encode3to4(source, d + off, 3, outBuff, e);
/*      */ 
/*  502 */       lineLength += 4;
/*  503 */       if ((breakLines) && (lineLength == 76))
/*      */       {
/*  505 */         outBuff[(e + 4)] = 10;
/*  506 */         e++;
/*  507 */         lineLength = 0;
/*      */       }
/*  498 */       d += 3;
/*      */     }
/*      */ 
/*  511 */     if (d < len)
/*      */     {
/*  513 */       encode3to4(source, d + off, len - d, outBuff, e);
/*  514 */       e += 4;
/*      */     }
/*      */ 
/*  517 */     return new String(outBuff, 0, e);
/*      */   }
/*      */ 
/*      */   private static byte[] decode4to3(byte[] fourBytes)
/*      */   {
/*  540 */     byte[] outBuff1 = new byte[3];
/*  541 */     int count = decode4to3(fourBytes, 0, outBuff1, 0);
/*  542 */     byte[] outBuff2 = new byte[count];
/*      */ 
/*  544 */     for (int i = 0; i < count; i++) {
/*  545 */       outBuff2[i] = outBuff1[i];
/*      */     }
/*  547 */     return outBuff2;
/*      */   }
/*      */ 
/*      */   private static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset)
/*      */   {
/*  578 */     if (source[(srcOffset + 2)] == 61)
/*      */     {
/*  583 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | 
/*  584 */         (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12;
/*      */ 
/*  586 */       destination[destOffset] = ((byte)(outBuff >>> 16));
/*  587 */       return 1;
/*      */     }
/*      */ 
/*  591 */     if (source[(srcOffset + 3)] == 61)
/*      */     {
/*  597 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | 
/*  598 */         (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12 | 
/*  599 */         (DECODABET[source[(srcOffset + 2)]] & 0xFF) << 6;
/*      */ 
/*  601 */       destination[destOffset] = ((byte)(outBuff >>> 16));
/*  602 */       destination[(destOffset + 1)] = ((byte)(outBuff >>> 8));
/*  603 */       return 2;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  615 */       int outBuff = (DECODABET[source[srcOffset]] & 0xFF) << 18 | 
/*  616 */         (DECODABET[source[(srcOffset + 1)]] & 0xFF) << 12 | 
/*  617 */         (DECODABET[source[(srcOffset + 2)]] & 0xFF) << 6 | 
/*  618 */         DECODABET[source[(srcOffset + 3)]] & 0xFF;
/*      */ 
/*  621 */       destination[destOffset] = ((byte)(outBuff >> 16));
/*  622 */       destination[(destOffset + 1)] = ((byte)(outBuff >> 8));
/*  623 */       destination[(destOffset + 2)] = ((byte)outBuff);
/*      */ 
/*  625 */       return 3;
/*      */     } catch (Exception e) {
/*  627 */       System.out.println(source[srcOffset] + ": " + DECODABET[source[srcOffset]]);
/*  628 */       System.out.println(source[(srcOffset + 1)] + ": " + DECODABET[source[(srcOffset + 1)]]);
/*  629 */       System.out.println(source[(srcOffset + 2)] + ": " + DECODABET[source[(srcOffset + 2)]]);
/*  630 */       System.out.println(source[(srcOffset + 3)] + ": " + DECODABET[source[(srcOffset + 3)]]);
/*  631 */     }return -1;
/*      */   }
/*      */ 
/*      */   public static byte[] decode(byte[] source, int off, int len)
/*      */   {
/*  652 */     int len34 = len * 3 / 4;
/*  653 */     byte[] outBuff = new byte[len34];
/*  654 */     int outBuffPosn = 0;
/*      */ 
/*  656 */     byte[] b4 = new byte[4];
/*  657 */     int b4Posn = 0;
/*  658 */     int i = 0;
/*  659 */     byte sbiCrop = 0;
/*  660 */     byte sbiDecode = 0;
/*  661 */     for (i = off; i < off + len; i++)
/*      */     {
/*  663 */       sbiCrop = (byte)(source[i] & 0x7F);
/*  664 */       sbiDecode = DECODABET[sbiCrop];
/*      */ 
/*  666 */       if (sbiDecode >= -5)
/*      */       {
/*      */         byte[] out;
/*  668 */         if (sbiDecode >= -1)
/*      */         {
/*  670 */           b4[(b4Posn++)] = sbiCrop;
/*  671 */           if (b4Posn > 3)
/*      */           {
/*  673 */             outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn);
/*  674 */             b4Posn = 0;
/*      */ 
/*  677 */             if (sbiCrop == 61) {
/*  678 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  686 */         System.err.println("Bad Base64 input character at " + i + ": " + source[i] + "(decimal)");
/*  687 */         return null;
/*      */       }
/*      */     }
/*      */ 
/*  691 */     out = new byte[outBuffPosn];
/*  692 */     System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
/*  693 */     return out;
/*      */   }
/*      */ 
/*      */   public static byte[] decode(String s)
/*      */   {
/*  709 */     byte[] bytes = s.getBytes();
/*  710 */     bytes = decode(bytes, 0, bytes.length);
/*      */ 
/*  714 */     int head = bytes[0] & 0xFF | bytes[1] << 8 & 0xFF00;
/*      */ 
/*  716 */     if ((bytes != null) && 
/*  717 */       (bytes.length >= 4) && 
/*  718 */       (35615 == head))
/*      */     {
/*  720 */       ByteArrayInputStream bais = null;
/*  721 */       GZIPInputStream gzis = null;
/*  722 */       ByteArrayOutputStream baos = null;
/*  723 */       byte[] buffer = new byte[2048];
/*  724 */       int length = 0;
/*      */       try
/*      */       {
/*  728 */         baos = new ByteArrayOutputStream();
/*  729 */         bais = new ByteArrayInputStream(bytes);
/*  730 */         gzis = new GZIPInputStream(bais);
/*      */ 
/*  732 */         while ((length = gzis.read(buffer)) >= 0)
/*      */         {
/*  734 */           baos.write(buffer, 0, length);
/*      */         }
/*      */ 
/*  738 */         bytes = baos.toByteArray();
/*      */       }
/*      */       catch (IOException localIOException)
/*      */       {
/*      */         try
/*      */         {
/*  747 */           baos.close(); } catch (Exception localException) {
/*      */         }try { gzis.close(); } catch (Exception localException1) {
/*      */         }try { bais.close(); }
/*      */         catch (Exception localException2)
/*      */         {
/*      */         }
/*      */       }
/*      */       finally
/*      */       {
/*      */         try
/*      */         {
/*  747 */           baos.close(); } catch (Exception localException3) {
/*      */         }try { gzis.close(); } catch (Exception localException4) {
/*      */         }try { bais.close();
/*      */         } catch (Exception localException5) {
/*      */         }
/*      */       }
/*      */     }
/*  754 */     return bytes;
/*      */   }
/*      */ 
/*      */   public static Object decodeToObject(String encodedObject)
/*      */   {
/*  771 */     byte[] objBytes = decode(encodedObject);
/*      */ 
/*  773 */     ByteArrayInputStream bais = null;
/*  774 */     ObjectInputStream ois = null;
/*  775 */     Object obj = null;
/*      */     try
/*      */     {
/*  779 */       bais = new ByteArrayInputStream(objBytes);
/*  780 */       ois = new ObjectInputStream(bais);
/*      */ 
/*  782 */       obj = ois.readObject();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  786 */       e.printStackTrace();
/*  787 */       obj = null;
/*      */       try
/*      */       {
/*  796 */         bais.close(); } catch (Exception localException) {
/*      */       }try { ois.close(); }
/*      */       catch (Exception localException1)
/*      */       {
/*      */       }
/*      */     }
/*      */     catch (ClassNotFoundException e)
/*      */     {
/*  791 */       e.printStackTrace();
/*  792 */       obj = null;
/*      */       try
/*      */       {
/*  796 */         bais.close(); } catch (Exception localException2) {
/*      */       }try { ois.close(); }
/*      */       catch (Exception localException3)
/*      */       {
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  796 */         bais.close(); } catch (Exception localException4) {
/*      */       }try { ois.close(); } catch (Exception localException5) {
/*      */       }
/*      */     }
/*  800 */     return obj;
/*      */   }
/*      */ 
/*      */   public static class InputStream extends FilterInputStream
/*      */   {
/*      */     private int options;
/*      */     private boolean encode;
/*      */     private int position;
/*      */     private byte[] buffer;
/*      */     private int bufferLength;
/*      */     private int numSigBytes;
/*      */     private int lineLength;
/*      */     private boolean breakLines;
/*      */ 
/*      */     public InputStream(InputStream in)
/*      */     {
/*  837 */       this(in, 0);
/*      */     }
/*      */ 
/*      */     public InputStream(InputStream in, int options)
/*      */     {
/*  864 */       super();
/*  865 */       this.options = options;
/*  866 */       this.breakLines = ((options & 0x8) != 8);
/*  867 */       this.encode = ((options & 0x1) == 1);
/*  868 */       this.breakLines = this.breakLines;
/*  869 */       this.encode = this.encode;
/*  870 */       this.bufferLength = (this.encode ? 4 : 3);
/*  871 */       this.buffer = new byte[this.bufferLength];
/*  872 */       this.position = -1;
/*  873 */       this.lineLength = 0;
/*      */     }
/*      */ 
/*      */     public int read()
/*      */       throws IOException
/*      */     {
/*  886 */       if (this.position < 0)
/*      */       {
/*  888 */         if (this.encode)
/*      */         {
/*  890 */           byte[] b3 = new byte[3];
/*  891 */           int numBinaryBytes = 0;
/*  892 */           for (int i = 0; i < 3; i++)
/*      */           {
/*      */             try
/*      */             {
/*  896 */               int b = this.in.read();
/*      */ 
/*  899 */               if (b >= 0)
/*      */               {
/*  901 */                 b3[i] = ((byte)b);
/*  902 */                 numBinaryBytes++;
/*      */               }
/*      */ 
/*      */             }
/*      */             catch (IOException e)
/*      */             {
/*  909 */               if (i == 0) {
/*  910 */                 throw e;
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/*  915 */           if (numBinaryBytes > 0)
/*      */           {
/*  917 */             Base64.encode3to4(b3, 0, numBinaryBytes, this.buffer, 0);
/*  918 */             this.position = 0;
/*  919 */             this.numSigBytes = 4;
/*      */           }
/*      */           else
/*      */           {
/*  923 */             return -1;
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  930 */           byte[] b4 = new byte[4];
/*  931 */           int i = 0;
/*  932 */           for (i = 0; i < 4; i++)
/*      */           {
/*  935 */             int b = 0;
/*      */             do b = this.in.read();
/*  937 */             while ((b >= 0) && (Base64.DECODABET[(b & 0x7F)] <= -5));
/*      */ 
/*  939 */             if (b < 0) {
/*      */               break;
/*      */             }
/*  942 */             b4[i] = ((byte)b);
/*      */           }
/*      */ 
/*  945 */           if (i == 4)
/*      */           {
/*  947 */             this.numSigBytes = Base64.decode4to3(b4, 0, this.buffer, 0);
/*  948 */             this.position = 0;
/*      */           } else {
/*  950 */             if (i == 0) {
/*  951 */               return -1;
/*      */             }
/*      */ 
/*  956 */             throw new IOException("Improperly padded Base64 input.");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  963 */       if (this.position >= 0)
/*      */       {
/*  966 */         if (this.position >= this.numSigBytes) {
/*  967 */           return -1;
/*      */         }
/*  969 */         if ((this.encode) && (this.breakLines) && (this.lineLength >= 76))
/*      */         {
/*  971 */           this.lineLength = 0;
/*  972 */           return 10;
/*      */         }
/*      */ 
/*  976 */         this.lineLength += 1;
/*      */ 
/*  980 */         int b = this.buffer[(this.position++)];
/*      */ 
/*  982 */         if (this.position >= this.bufferLength) {
/*  983 */           this.position = -1;
/*      */         }
/*  985 */         return b & 0xFF;
/*      */       }
/*      */ 
/*  994 */       throw new IOException("Error in Base64 code reading stream.");
/*      */     }
/*      */ 
/*      */     public int read(byte[] dest, int off, int len)
/*      */       throws IOException
/*      */     {
/* 1015 */       for (int i = 0; i < len; i++)
/*      */       {
/* 1017 */         int b = read();
/*      */ 
/* 1022 */         if (b >= 0) {
/* 1023 */           dest[(off + i)] = ((byte)b); } else {
/* 1024 */           if (i != 0) break;
/* 1025 */           return -1;
/*      */         }
/*      */       }
/*      */ 
/* 1029 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class OutputStream extends FilterOutputStream
/*      */   {
/*      */     private int options;
/*      */     private boolean encode;
/*      */     private int position;
/*      */     private byte[] buffer;
/*      */     private int bufferLength;
/*      */     private int lineLength;
/*      */     private boolean breakLines;
/*      */     private byte[] b4;
/*      */     private boolean suspendEncoding;
/*      */ 
/*      */     public OutputStream(OutputStream out)
/*      */     {
/* 1072 */       this(out, 1);
/*      */     }
/*      */ 
/*      */     public OutputStream(OutputStream out, int options)
/*      */     {
/* 1098 */       super();
/* 1099 */       this.options = options;
/* 1100 */       this.breakLines = ((options & 0x8) != 8);
/* 1101 */       this.encode = ((options & 0x1) == 1);
/* 1102 */       this.bufferLength = (this.encode ? 3 : 4);
/* 1103 */       this.buffer = new byte[this.bufferLength];
/* 1104 */       this.position = 0;
/* 1105 */       this.lineLength = 0;
/* 1106 */       this.suspendEncoding = false;
/* 1107 */       this.b4 = new byte[4];
/*      */     }
/*      */ 
/*      */     public void write(int theByte)
/*      */       throws IOException
/*      */     {
/* 1126 */       if (this.suspendEncoding)
/*      */       {
/* 1128 */         this.out.write(theByte);
/* 1129 */         return;
/*      */       }
/*      */ 
/* 1133 */       if (this.encode)
/*      */       {
/* 1135 */         this.buffer[(this.position++)] = ((byte)theByte);
/* 1136 */         if (this.position >= this.bufferLength)
/*      */         {
/* 1138 */           this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength));
/*      */ 
/* 1140 */           this.lineLength += 4;
/* 1141 */           if ((this.breakLines) && (this.lineLength >= 76))
/*      */           {
/* 1143 */             this.out.write(10);
/* 1144 */             this.lineLength = 0;
/*      */           }
/*      */ 
/* 1147 */           this.position = 0;
/*      */         }
/*      */ 
/*      */       }
/* 1155 */       else if (Base64.DECODABET[(theByte & 0x7F)] > -5)
/*      */       {
/* 1157 */         this.buffer[(this.position++)] = ((byte)theByte);
/* 1158 */         if (this.position >= this.bufferLength)
/*      */         {
/* 1160 */           int len = Base64.decode4to3(this.buffer, 0, this.b4, 0);
/* 1161 */           this.out.write(this.b4, 0, len);
/*      */ 
/* 1163 */           this.position = 0;
/*      */         }
/*      */       }
/* 1166 */       else if (Base64.DECODABET[(theByte & 0x7F)] != -5)
/*      */       {
/* 1168 */         throw new IOException("Invalid character in Base64 data.");
/*      */       }
/*      */     }
/*      */ 
/*      */     public void write(byte[] theBytes, int off, int len)
/*      */       throws IOException
/*      */     {
/* 1187 */       if (this.suspendEncoding)
/*      */       {
/* 1189 */         this.out.write(theBytes, off, len);
/* 1190 */         return;
/*      */       }
/*      */ 
/* 1193 */       for (int i = 0; i < len; i++)
/*      */       {
/* 1195 */         write(theBytes[(off + i)]);
/*      */       }
/*      */     }
/*      */ 
/*      */     public void flushBase64()
/*      */       throws IOException
/*      */     {
/* 1208 */       if (this.position > 0)
/*      */       {
/* 1210 */         if (this.encode)
/*      */         {
/* 1212 */           this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position));
/* 1213 */           this.position = 0;
/*      */         }
/*      */         else
/*      */         {
/* 1217 */           throw new IOException("Base64 input not properly padded.");
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void close()
/*      */       throws IOException
/*      */     {
/* 1232 */       flushBase64();
/*      */ 
/* 1236 */       super.close();
/*      */ 
/* 1238 */       this.buffer = null;
/* 1239 */       this.out = null;
/*      */     }
/*      */ 
/*      */     public void suspendEncoding()
/*      */       throws IOException
/*      */     {
/* 1253 */       flushBase64();
/* 1254 */       this.suspendEncoding = true;
/*      */     }
/*      */ 
/*      */     public void resumeEncoding()
/*      */     {
/* 1267 */       this.suspendEncoding = false;
/*      */     }
/*      */   }
/*      */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.utility.Base64
 * JD-Core Version:    0.6.2
 */