/*     */ package jatools.component.chart.servlet;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class ChartServlet extends HttpServlet
/*     */ {
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  37 */     handleRequest(request, response);
/*     */   }
/*     */ 
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*     */   {
/*  42 */     handleRequest(request, response);
/*     */   }
/*     */   String getContentType(String encodingType) {
/*  45 */     if (encodingType == null) {
/*  46 */       return "image/jpeg";
/*     */     }
/*  48 */     if (encodingType.equalsIgnoreCase("gifencoder")) {
/*  49 */       return "image/gif";
/*     */     }
/*  51 */     if (encodingType.startsWith("j_")) {
/*  52 */       return encodingType.substring(5);
/*     */     }
/*  54 */     return "image/jpeg";
/*     */   }
/*     */ 
/*     */   public String getServletInfo()
/*     */   {
/*  61 */     return "chart 4.2.2 charting servlet";
/*     */   }
/*     */ 
/*     */   protected void handleRequest(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  72 */     String s = request.getParameter("chartType");
/*  73 */     if (s == null)
/*  74 */       s = "barApp";
/*     */     Bean chartBean;
/*     */     try {
/*  77 */       Class beanClass = Class.forName("javachart.servlet." + s);
/*  78 */       chartBean = (Bean)beanClass.newInstance();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       try
/*     */       {
/*     */         Bean chartBean;
/*  81 */         Class beanClass = Class.forName(s);
/*  82 */         chartBean = (Bean)beanClass.newInstance();
/*     */       }
/*     */       catch (Exception e2)
/*     */       {
/*     */         Bean chartBean;
/*  84 */         log("chart servlet can't load requested chart class.  Using BarChart instead");
/*  85 */         chartBean = new barApp();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  90 */     s = request.getParameter("properties");
/*  91 */     if (s != null) {
/*  92 */       chartBean.loadProperties(s);
/*     */     }
/*     */ 
/*  95 */     Enumeration en = request.getParameterNames();
/*  96 */     while (en.hasMoreElements()) {
/*  97 */       String param = (String)en.nextElement();
/*  98 */       chartBean.setProperty(param, request.getParameter(param));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 114 */       chartBean.isChartServlet = true;
/*     */ 
/* 116 */       response.setContentType(getContentType(chartBean.getParameter("imageType")));
/* 117 */       ServletOutputStream out = response.getOutputStream();
/* 118 */       byte[] bytes = chartBean.getImageBytes();
/*     */ 
/* 121 */       response.setContentLength(bytes.length);
/* 122 */       out.write(bytes);
/*     */     }
/*     */     catch (Exception e) {
/* 125 */       response.setContentType("text/html");
/* 126 */       response.getWriter().println("<p>problem generating image<p>");
/*     */     }
/*     */   }
/*     */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.ChartServlet
 * JD-Core Version:    0.6.2
 */