<Query Kind="Program">
  <Namespace>System</Namespace>
  <Namespace>System.Text</Namespace>
  <Namespace>System.Text.RegularExpressions</Namespace>
</Query>


void Main()
{
	string[] alllines =  File.ReadAllLines(@"F:\pilotproject\EnergyResourcesManager\jatools\src\main\java\jatools\resources\resources_zh_TW.properties");
	
	//alllines.Dump();
	for(int j = 0 ;j< alllines.Length; j ++ ) {
	      string str = alllines[j];
            string outStr = "";  
	if (!string.IsNullOrEmpty(str))  
		if(str.IndexOf("\\u") != -1) {
			string[] res = str.Split('=');
            (res[0] + "="+testout(str)).Dump();
		}
		else 
			str.Dump();
	}
}


string  testout(string input) {
MatchCollection matchs = new Regex(@"\\u?(\w{4})", RegexOptions.IgnoreCase).Matches(input);
                if (matchs.Count == 0)
                {
                    //MessageBox.Show("请输入正确的Unicode(css格式)！");
					return "";
                }
                else
                {
                    string str = "";
                    foreach (Match match in matchs)
                    {
                        byte[] bytes = new byte[] { (byte) HexToInt(match.Groups[1].Value.Substring(2)), (byte) HexToInt(match.Groups[1].Value.Substring(0, 2)) };
                        str = str + Encoding.Unicode.GetString(bytes);
                    }
                   //str.Dump();
				   return str;
                }
			
}
// Define other methods and classes here
int HexToInt(string h)
   {
   int res = 0;
    try {
	 res =  Convert.ToInt32(h, 0x10);
	}catch(Exception exp) {
	   res  = 0;
	 }
       return res;
   }