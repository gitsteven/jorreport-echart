/*    */ package jatools.component.chart.servlet;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class ChartStream extends HttpServlet
/*    */ {
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */   {
/*    */     try
/*    */     {
/* 25 */       String streamName = request.getParameter("sn");
/* 26 */       if (streamName != null) {
/* 27 */         String contentType = streamName.substring(streamName.length() - 3);
/*    */ 
/* 29 */         Bean bean = (Bean)getServletContext().getAttribute(streamName);
/* 30 */         if (bean != null) {
/* 31 */           response.setContentType("image/" + contentType);
/* 32 */           OutputStream out = response.getOutputStream();
/* 33 */           byte[] bytes = bean.getImageBytes();
/*    */ 
/* 35 */           response.setContentLength(bytes.length);
/* 36 */           out.write(bytes);
/* 37 */           getServletContext().removeAttribute(streamName);
/*    */         }
/*    */         else
/*    */         {
/* 46 */           log("no chart bean found");
/*    */         }
/*    */       }
/*    */     } catch (Exception ex) {
/* 50 */       ex.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           G:\pilotproject\EnergyResourcesManager\jatools\lib\chart.jar
 * Qualified Name:     jatools.component.chart.servlet.ChartStream
 * JD-Core Version:    0.6.2
 */