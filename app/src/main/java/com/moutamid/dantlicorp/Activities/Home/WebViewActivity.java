package com.moutamid.dantlicorp.Activities.Home;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.dantlicorp.R;


public class WebViewActivity extends AppCompatActivity {
    TextView policy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        policy= (TextView) findViewById(R.id.policy);
        policy.setText(Html.fromHtml("<p style=\"text-align: center;\"><strong><span style=\"font-size:40pt;\">Privacy Policy</span></strong></p>\n" +
                "<p><span style=\"font-size:11pt;\">The Dantli Corp App is provided at no cost and is intended for use as is.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">The Privacy Policy is provided to inform users about our policies on the collection, use, and disclosure of Personal Information.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:11pt;\">If you choose to use the Dantli Corp App, then you agree to the collection and use of information in relation to this policy. The Personal Information that is collected is used for providing and improving the Dantli Corp App experience. Dantli Corp will not use or share your information with anyone except as described in this Privacy Policy.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><strong><span style=\"font-size:14pt;\">Information Collection and Use</span></strong></p>\n" +
                "<p><span style=\"font-size:11pt;\">For a better experience, while using the Dantli Corp App, Dantli Corp may require you to provide certain personally identifiable information. The Dantli Corp App does use third-party services that may collect information used to identify you.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:11pt;\">Link to the privacy policy of third-party service providers used by the app:</span></p>\n" +
                "<ul style=\"list-style-type: circle;\">\n" +
                "    <li><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:6.999999999999999pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span><span style=\"font-size:11pt;\">Google Play Services</span></li>\n" +
                "    <li><span style=\"font-size:6.999999999999999pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span><span style=\"font-size:11pt;\">Google Analytics for Firebase</span></li>\n" +
                "    <li><span style=\"font-size:6.999999999999999pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span><span style=\"font-size:11pt;\">Firebase Crashlytics</span></li>\n" +
                "    <li><span style=\"font-size:6.999999999999999pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span><span style=\"font-size:11pt;\">Log Data</span></li>\n" +
                "</ul>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:11pt;\">In the case of an error in the Dantli Corp App, Dantli Corp will collect data and information (through third-party products) on your phone called Log Data.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:11pt;\">The Log Data may include information such as your device Internet Protocol (&ldquo;IP&rdquo;) address, device name, operating system version, the configuration of the app when utilizing my Service, the time and date of your use of the Service, and other statistics.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><strong><span style=\"font-size:14pt;\">Cookies:</span></strong></p>\n" +
                "<p><span style=\"font-size:11pt;\">Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device&apos;s internal memory.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:11pt;\">This Service does not use these &ldquo;cookies&rdquo; explicitly. However, the Dantli Corp App may use third-party code and libraries that use &ldquo;cookies&rdquo; to collect information and improve their services. You can either accept or refuse these cookies and know when a cookie is being sent to your device.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:11pt;\">If you choose to refuse the cookies, you may not be able to use some portions of the Dantli Corp App.</span></p>\n" +
                "<p><span style=\"color:#222222;font-size:12pt;\">&nbsp;</span><strong><span style=\"font-size:14pt;\">Service Providers</span></strong></p>\n" +
                "<p><span style=\"font-size:11pt;\">Dantli Corp may employ third-party companies and individuals due to the following reasons:</span></p>\n" +
                "<ol>\n" +
                "    <li><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:10pt;\">&middot;</span><span style=\"font-size:6.999999999999999pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span><span style=\"font-size:11pt;\">To create the Dantli Corp App;</span></li>\n" +
                "    <li><span style=\"font-size:10pt;\">&middot;</span><span style=\"font-size:6.999999999999999pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span><span style=\"font-size:11pt;\">To provide the Dantli Corp App on our behalf;</span></li>\n" +
                "    <li><span style=\"font-size:10pt;\">&middot;</span><span style=\"font-size:6.999999999999999pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span><span style=\"font-size:11pt;\">To perform maintenance related services; or</span></li>\n" +
                "    <li><span style=\"font-size:10pt;\">&middot;</span><span style=\"font-size:6.999999999999999pt;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span><span style=\"font-size:11pt;\">To assist us in analyzing how the Dantli Corp App is used.</span></li>\n" +
                "</ol>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:11pt;\">Dantli Corp wants to inform users of this Service that these third parties have access to their Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><strong><span style=\"font-size:14pt;\">Security:</span></strong></p>\n" +
                "<p><span style=\"font-size:11pt;\">Dantli Corp values your trust in providing us with your Personal Information, thus we are striving to use commercially acceptable means of protecting it.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:11pt;\">But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and Dantli Corp cannot guarantee its absolute security.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><strong><span style=\"font-size:14pt;\">Links to Other Sites:</span></strong></p>\n" +
                "<p><span style=\"font-size:11pt;\">The Dantli Corp App may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that Dantli Corp does not operate these external sites. Therefore, Dantli Corp strongly advises users to review the Privacy Policy of the third-party websites.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:11pt;\">Dantli Corp has no control over and assumes no responsibility for the content, privacy policies, or practices of any third-party sites or services.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><strong><span style=\"font-size:14pt;\">Children&rsquo;s Privacy</span></strong></p>\n" +
                "<p><span style=\"font-size:11pt;\">The Dantli Corp App does not address anyone under the age of 13. Dantli Corp does not knowingly collect personally identifiable information from children under 13 years of age.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><span style=\"font-size:11pt;\">If Dantli Corp discovers that a child under 13 has provided us with personal information, Dantli Corp will immediately delete their information from our servers. If you are a parent or guardian and you are aware that your child has provided Dantli Corp with personal information, please contact us so that we will be able to take the necessary actions.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><strong><span style=\"font-size:14pt;\">Changes to This Privacy Policy</span></strong></p>\n" +
                "<p><span style=\"font-size:11pt;\">Dantli Corp may update our Privacy Policy from time to time. Thus, users are advised to review this page periodically for any changes. Dantli Corp will notify users of any changes by posting the new Privacy Policy on this page.</span></p>\n" +
                "<p><span style=\"font-size:11pt;\">&nbsp;</span><strong><span style=\"font-size:14pt;\">Contact Us</span></strong></p>\n" +
                "<p><span style=\"font-size:11pt;\">If you have any questions or suggestions about the Dantli Corp App Privacy Policy, please do not hesitate to contact us at h</span><span style=\"font-size:11pt;\">r@dantlicorp.com.&rdquo;&nbsp;</span></p>"));
//        WebView browser = (WebView) findViewById(R.id.webview);
//        browser.getSettings().setLoadsImagesAutomatically(true);
//        browser.setWebViewClient(new MyBrowser());
////      browser.getSettings().setJavaScriptEnabled(true);
////      browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        browser.loadUrl("https://docs.google.com/document/d/10WnhJNgkaGDGTkeqyubE6nbeCbJJfJmGVkCfCp5qDKE/edit?usp=sharing");

    }

    public void backPress(View view) {
        onBackPressed();
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}