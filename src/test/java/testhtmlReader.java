import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.*;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
/**
 * Created by yijx on 2018/2/11.
 */
public class testhtmlReader {

    @org.junit.Test
    public void  testHTML() {
        try {
            final WebClient webClient = new WebClient(BrowserVersion.CHROME);

            final HtmlPage page = webClient.getPage("http://localhost:8000/line2.html");



            System.out.println(" // 1 启动JS ");
            webClient.getOptions().setJavaScriptEnabled(true);
            System.out.println("// 2 禁用Css，可避免自动二次请求CSS进行渲染 ");
            webClient.getOptions().setCssEnabled(false);
            System.out.println("// 3 启动客户端重定向 ");
            webClient.getOptions().setRedirectEnabled(true);
            System.out.println("// 4 js运行错误时，是否抛出异常");
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            System.out.println("// 5 设置超时 ");
            webClient.getOptions().setTimeout(50000);
            System.out.println("  允许绕过SSL认证 ");
            webClient.getOptions().setUseInsecureSSL(true);
            System.out.println("  允许启动注册组件 ");
            webClient.getOptions().setActiveXNative(true);

            System.out.println(" //等待JS驱动dom完成获得还原后的网页 ");
            webClient.waitForBackgroundJavaScript(5000);




            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getCookieManager().setCookiesEnabled(true);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());

            webClient.addWebWindowListener( new WebWindowListener() {
                @Override
                public void webWindowOpened(WebWindowEvent webWindowEvent) {
                 System.out.println("windows opened");
                }

                @Override
                public void webWindowContentChanged(WebWindowEvent webWindowEvent) {
                    System.out.println("windows changed");
                }

                @Override
                public void webWindowClosed(WebWindowEvent webWindowEvent) {
                    System.out.println("windows closed");
                }
            });

            try {
                Thread.sleep(10000);
            }catch (Exception exp) {

            }
            final HtmlDivision div = page.getHtmlElementById("text2");

            //执行按钮出发的js事件
            ScriptResult sr = page.executeJavaScript("javascript:getData();");



            try {
                String fileStr = "";
                String test = sr.getJavaScriptResult().toString();
                byte[] b = new BASE64Decoder().decodeBuffer(test);

                // 生成图片
                OutputStream out = new FileOutputStream(new File(fileStr + "\\test.png"));
                out.write(b);
                out.flush();
                out.close();
            }catch (Exception exp) {
                exp.printStackTrace();
            }


        }catch (Exception exp) {
            exp.printStackTrace();
        }

    }

}
