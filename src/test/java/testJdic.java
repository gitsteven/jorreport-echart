import org.junit.*;

import java.io.File;
import java.net.URL;
import org.jdesktop.jdic.browser.*;
import org.jdesktop.jdic.browser.WebBrowser;
import org.jdesktop.jdic.browser.WebBrowserEvent;
import org.jdesktop.jdic.browser.WebBrowserListener;

import javax.swing.*;

/**
 * Created by yijx on 2018/2/12.
 * @author yijx
 * jdic 没有64 为，放弃
 */
public class testJdic {

    @org.junit.Test
    public void testHtml() throws Exception {
        //BrowserEngineManager bem = BrowserEngineManager.instance();
       // bem.setActiveEngine(BrowserEngineManager.IE);
        //IBrowserEngine be = bem.getActiveEngine();

        //URL url = new URL("http://www.hao123.com");
        URL url = new File("http://localhost.:8000/line2.html").toURI().toURL();
        final  WebBrowser browser = new WebBrowser();
        //browser = be.getWebBrowser();//new WebBrowser();
        browser.addWebBrowserListener(new WebBrowserListener() {
            public void downloadStarted(WebBrowserEvent event) {
                System.out.println("27");
            }
            public void downloadCompleted(WebBrowserEvent event) {
                System.out.println("30");
            }
            public void downloadProgress(WebBrowserEvent event) {
                System.out.println("33");
            }
            public void downloadError(WebBrowserEvent event) {
                System.out.println("36");
            }
            public void documentCompleted(WebBrowserEvent event) {
                System.out.println("39");
                browser.executeScript("alert('文档下载完毕！')");
                String res = browser.executeScript("getData");
                System.out.println(res);
            }
            public void titleChange(WebBrowserEvent event) {
                System.out.println("43");
            }
            public void statusTextChange(WebBrowserEvent event) {
                System.out.println("46");
            }
            public void windowClose(WebBrowserEvent webBrowserEvent) {
                System.out.println("49");
            }
            public void initializationCompleted(WebBrowserEvent arg0) {
                System.out.println("52");
            } });
        browser.setURL(url);

        JFrame f = new JFrame();
        f.setTitle("浏览器");
        f.setSize(800,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.getContentPane().add(browser1);
        f.getContentPane().add(browser);
        f.setVisible(true);
    }
}
