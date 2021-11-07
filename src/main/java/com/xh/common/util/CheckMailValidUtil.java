package com.xh.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import java.io.IOException;

/**
 * CheckMailValidUtil -- 检查邮箱是否有效
 * 需要maven:
 * <!-- https://mvnrepository.com/artifact/commons-net/commons-net -->
 * <dependency>
 * <groupId>commons-net</groupId>
 * <artifactId>commons-net</artifactId>
 * <version>3.6</version>
 * </dependency>
 * <!-- https://mvnrepository.com/artifact/dnsjava/dnsjava -->
 * <dependency>
 * <groupId>dnsjava</groupId>
 * <artifactId>dnsjava</artifactId>
 * <version>3.0.1</version>
 * </dependency>
 *
 * @author xh
 * @date 2021/3/17
 */

@Slf4j
public class CheckMailValidUtil {

    /**
     * 校验
     *
     * @param email 待校验的邮箱地址
     * @return true 表示邮箱可以连通
     */
    public static boolean isEmailValid(String email) {

        String host ;
        String hostName = email.split("@")[1];
        //Record: A generic DNS resource record. The specific record types 
        //extend this class. A record contains a name, type, class, ttl, and rdata.
        Record[] result;
        SMTPClient client = new SMTPClient();
        try {
            // 查找DNS缓存服务器上为MX类型的缓存域名信息
            Lookup lookup = new Lookup(hostName, Type.MX);
            lookup.run();
            if (lookup.getResult() != Lookup.SUCCESSFUL) {
                //查找失败
                log.error("邮箱(" + email + ")校验未通过，未找到对应的MX记录!");
                return false;
            } else {
                //查找成功
                result = lookup.getAnswers();
            }
            // 尝试和SMTP邮箱服务器建立Socket连接
            for (Record record : result) {
                host = record.getAdditionalName().toString();
                log.info("SMTPClient try connect to host:" + host);

                // 此connect()方法来自SMTPClient的父类:org.apache.commons.net.SocketClient
                // 继承关系结构：org.apache.commons.net.smtp.SMTPClient-->org.apache.commons.net.smtp.SMTP-->org.apache.commons.net.SocketClient
                // Opens a Socket connected to a remote host at the current default port and 
                // originating from the current host at a system assigned port. Before returning,
                // _connectAction_() is called to perform connection initialization actions. 
                // 尝试Socket连接到SMTP服务器
                client.connect(host);
                //Determine if a reply code is a positive completion response（查看响应码是否正常）. 
                //All codes beginning with a 2 are positive completion responses（所有以2开头的响应码都是正常的响应）. 
                //The SMTP server will send a positive completion response on the final successful completion of a command. 
                if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {
                    //断开socket连接
                    client.disconnect();
                    // continue;
                } else {
                    log.info("找到MX记录:" + hostName);
                    log.info("建立链接成功：" + hostName);
                    break;
                }
            }
            log.info("SMTPClient ReplyString:" + client.getReplyString());
            String emailSuffix = "163.com";
            String emailPrefix = "ranguisheng";
            String fromEmail = emailPrefix + "@" + emailSuffix;
            // Login to the SMTP server by sending the HELO command with the given hostname as an argument. 
            // Before performing any mail commands, you must first login. 
            // 尝试和SMTP服务器建立连接,发送一条消息给SMTP服务器
            client.login(emailPrefix);
            log.info("SMTPClient login:" + emailPrefix + "...");
            log.info("SMTPClient ReplyString:" + client.getReplyString());

            //Set the sender of a message using the SMTP MAIL command, 
            //specifying a reverse relay path. 
            //The sender must be set first before any recipients may be specified, 
            //otherwise the mail server will reject your commands. 
            //设置发送者，在设置接受者之前必须要先设置发送者
            client.setSender(fromEmail);
            log.info("设置发送者 :" + fromEmail);
            log.info("SMTPClient ReplyString:" + client.getReplyString());

            //Add a recipient for a message using the SMTP RCPT command, 
            //specifying a forward relay path. The sender must be set first before any recipients may be specified, 
            //otherwise the mail server will reject your commands. 
            //设置接收者,在设置接受者必须先设置发送者，否则SMTP服务器会拒绝你的命令
            client.addRecipient(email);
            log.info("设置接收者:" + email);
            log.info("SMTPClient ReplyString:" + client.getReplyString());
            log.info("SMTPClient ReplyCode：" + client.getReplyCode() + "(250表示正常)");
            if (250 == client.getReplyCode()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isEmailValid("dreamofxh@gmail.com"));
    }
}
