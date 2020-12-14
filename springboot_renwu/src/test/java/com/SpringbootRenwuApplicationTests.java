package com;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.mapper.ObjectMapper;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@SpringBootTest
class SpringbootRenwuApplicationTests {


    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    public void testMail() throws MessagingException {
//        //创建简单的消息邮件
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setSubject("邮件标题");
//        message.setText("邮件正文");
//        message.setTo("目标邮箱");
//        message.setFrom("发件人邮箱");
        //创建一个复杂的消息邮件
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject("邮件标题");
        helper.setText("<b style='color:red'>邮件正文</b>",true);
        helper.setTo("目标邮箱");
        helper.setFrom("发件人邮箱");
        helper.addAttachment("上传文件名",new File("上传文件路径"));

        mailSender.send(message);
    }

}
