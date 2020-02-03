package com.sht.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016102000727228";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCud0Xwc1EMsG3VrD+Bu56nMlqHar+tA18lULgp8NXX0FGqLeFiBWAtNB0N0CuijGiukNNkkziEP4SgrLgLaCxRSxMWhSSasQnCi2xUOV4zynZMUGslwvk+Ish6hg177CyFQ4eKTezb8Uil4kvkpWn1OjTAcOVTreGRiNKv14MLol6kLOgNKi4w4T4cHKKjTvq8Y3q+lq7MNcPZdsZCGSpRp00tLdjJVK7DWTivqDCirqmO7E6yJHqNkgzFt53K1h00Wucms/1+ar0sta2ACvL+5/afEAuqUsbjFfhyvxBPDrFueq9YVLTJHQrXDhNBuoaqYN/UsMvACF43mqb5SDH/AgMBAAECggEABC3zTPlWPbmUSAQiWVdHS7YXGRtjkB9+jgRLBSYx2UOyUSNcPN7oGO0grQ4JnZFDiLsLdWNZfUGQSdEZb5yFQ0VLJcT2xYXsN+7jH3loWlFFWDqs9nCfS+/FYboIZKmZv2+ItbV8MSCsnty2+LlFuow0a0/JDqIhvzzJ31CIYSTuaLDDD1yipMerQxlA+PhwI47s+R7jbth0VsQTG3zMmnYFyP2cw/90HYCujYOJzWsKtPz8OkXoKmjbweTPm75FgyAv7IefsSWmt9NZegiRX3mFATWX3CrWi4jl7xEQHfXV6olamHXcOllUlOzhny6o6qqcHlmiZ7ITVG6Img7O0QKBgQDz3sFsJ8+HrX0s6QuZyT9NeIEf/MEDJ6cSwUqmeOftR1a3ylHF0qDWIWryMJLpzuP9sdkqxOhP2FMuRe17Ok610OmL3MsI78IhaudXTjUf9XQYZ0xjMtEdBKd+rSALvHcYs6SKYkTMSXw9GhdEbW3etNPMXzHj6JpCVwFRdrTitQKBgQC3JMfQqmCFBM6zJz4G28m1F69k2/JHPiz9iEnSPfTkdS2mLc0cz4h0XAMjPn9gG7mxZxJKM/rfUusHyweGl2Wb/b55gB5m5dac0hnIb3ayF6f33K5JR8epe2IrgprpVphE25EmS8GHSEK7G5/440E516AwU5694cmUdQDetTguYwKBgQCcGMBcF1tqMC1Iet8XD876KqEUNXfiKhZyFdFPn2Js6dXjDJis9DZI+dj3rwnKqMgckiM8kg9VVFVh8BajYt4Q2Ok0wh4f2T+8ni34HzI4A6fNGwm1EUjytxDzL6Osf3UIFazDe0UoMf1ZKwMrpJPIEpzjWyeXB0L+JazNVRtRZQKBgA+zFXXrxQ5iFR4CdSy9VpMxjpWEzUYSLGUnc7yUFxQmf11Je9XGbJoc8R/qUZz5g3eTsFuwDQdr4j3ux5xgChPPMq7Trsd/WGBlGlS2SWSG5p02y7H4d0MuEoJMVd8+zxrH/69auyJPrXjAq3v4bzT4KQE7idYZLuCNDyAIROKlAoGBAI5snw66311kxlW0F6whzvIpcqeC1mbS03STFWhV0y/9u2QfZBAOkAryCLUlhJyd4l/UbsxHW1XrQVpZwmhl3QzVjUKzEolwUL4PQ2SWtjX2Z9GpYUHkAywgof0gBwRK1xRMC0lkGzalv3GfSbHxBiLfZodlj7uMnzMKC0DDxIAb";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArndF8HNRDLBt1aw/gbuepzJah2q/rQNfJVC4KfDV19BRqi3hYgVgLTQdDdArooxorpDTZJM4hD+EoKy4C2gsUUsTFoUkmrEJwotsVDleM8p2TFBrJcL5PiLIeoYNe+wshUOHik3s2/FIpeJL5KVp9To0wHDlU63hkYjSr9eDC6JepCzoDSouMOE+HByio076vGN6vpauzDXD2XbGQhkqUadNLS3YyVSuw1k4r6gwoq6pjuxOsiR6jZIMxbedytYdNFrnJrP9fmq9LLWtgAry/uf2nxALqlLG4xX4cr8QTw6xbnqvWFS0yR0K1w4TQbqGqmDf1LDLwAheN5qm+Ugx/wIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/sht/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/sht/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

