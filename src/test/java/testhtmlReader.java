import com.gargoylesoftware.htmlunit.WebClient;
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
            final WebClient webClient = new WebClient();

            final HtmlPage page = webClient.getPage("http://localhost:8000/line2.html");
            final HtmlDivision div = page.getHtmlElementById("text2");


            try {
                String fileStr = "";
                String test = div.getTextContent();
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
