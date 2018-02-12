using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Diagnostics;
using System.IO;

namespace saveImage
{
    public partial class Form1 : Form
    {
        private string htmlurl = "http://localhost.:8000/line-simple.html?random=";
        private string imageGuid = Guid.NewGuid().ToString();


        public Form1()
        {
            init();
        }

        public Form1(string[] args)
        {
            //0 html路径， 1 存放生成图片uuid
            htmlurl = args[0];
            imageGuid = args[1];
            init();
        }

        private void init()
        {
            InitializeComponent();
            CleanTempFiles();
            webBrowser1.AllowWebBrowserDrop = false;
            webBrowser1.WebBrowserShortcutsEnabled = false;
            webBrowser1.IsWebBrowserContextMenuEnabled = false;
            webBrowser1.Navigate(htmlurl + "?random=" + DateTime.Now.ToString("yyyyMMddHHmmss"), null, null, null);
        }
        private void webBrowser1_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            try
            {
                var doc = this.webBrowser1.Document;
                var ele = doc.GetElementById("text2");
                Console.WriteLine(ele.InnerText);
                // 读取base64 ， 然后转换为图片，保存
                string base64 = ele.InnerText.Split(',')[1];
                byte[] arr = Convert.FromBase64String(base64);
                MemoryStream ms = new MemoryStream(arr);
                Bitmap bmp = new Bitmap(ms);

                //bmp.Save(txtFileName + ".jpg", System.Drawing.Imaging.ImageFormat.Jpeg);
                //bmp.Save(txtFileName + ".bmp", ImageFormat.Bmp);
                //bmp.Save(txtFileName + ".gif", ImageFormat.Gif);
                //bmp.Save(txtFileName + ".png", ImageFormat.Png);
               // bmp.Save(imageGuid + ".jpg", System.Drawing.Imaging.ImageFormat.Jpeg);
                bmp.Save(imageGuid + ".png", System.Drawing.Imaging.ImageFormat.Png);
                ms.Close();

            }catch(Exception exp)
            {
                Console.WriteLine(exp.ToString());
            }
            finally
            {
                Application.Exit();
            }
        }

        /// <summary>
        /// 清除文件夹
        /// </summary>
        /// <param name="path">文件夹路径</param>
         void FolderClear(string path)
        {
            System.IO.DirectoryInfo diPath = new System.IO.DirectoryInfo(path);
            foreach (System.IO.FileInfo fiCurrFile in diPath.GetFiles())
            {
                FileDelete(fiCurrFile.FullName);

            }
            foreach (System.IO.DirectoryInfo diSubFolder in diPath.GetDirectories())
            {
                FolderClear(diSubFolder.FullName); // Call recursively for all subfolders
            }
        }

        void FileDelete(string fileFullName) {
            try
            {
                System.IO.File.Delete(fileFullName);
            }catch(Exception exp)
            {

            }
        }

        /// <summary>
        /// 执行命令行
        /// </summary>
        /// <param name="cmd"></param>
         void RunCmd(string cmd)
        {
            ProcessStartInfo p = new ProcessStartInfo();
            p.FileName = "cmd.exe";
            p.Arguments = "/c " + cmd;
            p.WindowStyle = ProcessWindowStyle.Hidden;  // Use a hidden window
            Process.Start(p);
        }

        /// <summary>
        /// 删除临时文件
        /// </summary>
        public  void CleanTempFiles()
        {
            FolderClear(Environment.GetFolderPath(Environment.SpecialFolder.InternetCache));
            RunCmd("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 8");
        }
    }
}
