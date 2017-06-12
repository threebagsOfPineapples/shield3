package com.tachyon5.spectrum.utils;

import java.io.InputStream;

import com.zhy.http.okhttp.OkHttpUtils;

import android.app.Application;
import android.os.Vibrator;
import okio.Buffer;

public class BaseApplation extends Application {
	private static BaseApplation context;
	public Vibrator mVibrator;
	private String CER=	"-----BEGIN CERTIFICATE-----\n"+
			"MIICkTCCAfoCCQCOVljopdR53DANBgkqhkiG9w0BAQUFADCBjDELMAkGA1UEBhMCODYxCzAJBgNV\n"+
			"BAgMAkJKMQswCQYDVQQHDAJCSjERMA8GA1UECgwITWFnaXNwZWMxDDAKBgNVBAsMA0RldjEWMBQG\n"+
			"A1UEAwwNMTIzLjU2LjIyOS41MDEqMCgGCSqGSIb3DQEJARYbbWluZ3hpbmcuemhhbmdAbWFnaXNw\n"+
			"ZWMuY29tMB4XDTE2MDMxMTA4MzYxN1oXDTE3MDMxMTA4MzYxN1owgYwxCzAJBgNVBAYTAjg2MQsw\n"+
			"CQYDVQQIDAJCSjELMAkGA1UEBwwCQkoxETAPBgNVBAoMCE1hZ2lzcGVjMQwwCgYDVQQLDANEZXYx\n"+
			"FjAUBgNVBAMMDTEyMy41Ni4yMjkuNTAxKjAoBgkqhkiG9w0BCQEWG21pbmd4aW5nLnpoYW5nQG1h\n"+
			"Z2lzcGVjLmNvbTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA4ZtLhVDDVqXqBijQgpvfupv0\n"+
			"yqy9Vu9QYR2bJ/PfBpfdlxeyUnt7dLEDT+YsSPwEBydWvvTjtDPvDq+lzci1HK9m0Q453Uo1w8SN\n"+
			"KX9bDyxhf2Hdkt3udvuvy8x7qVyBGlHs2aLs40CFaZW9cX1s9DodJtPJQy10vXkl8LoKqt8CAwEA\n"+
			"ATANBgkqhkiG9w0BAQUFAAOBgQCzYbmZQTZfTdoOMj9kQyxa+MDBC9tJWo2VLSwuyxucT7xBQ8CQ\n"+
			"iBswDX7zwi+eZ9rSS/I1bXHcuseDMrP5koUk2NiunxU8Izz6L1RCrvXlqxm0Md1M6leY8c70W0wf\n"+
			"wRY2AAmWAEBXRCCBXUjbBVOZpDXTu5odnOYbTMjI+UYeKg==\n"+
			"-----END CERTIFICATE-----";
	
	
    @Override
    public void onCreate(){
        super.onCreate();
        context = this;
        OkHttpUtils.getInstance().setCertificates(new InputStream[]{
                new Buffer()
                .writeUtf8(CER)
                .inputStream()});
    }    
    public synchronized static BaseApplation getInstance() {
		return context;
	}
	public static BaseApplation getAppContext() {
		return context;
	}
}
