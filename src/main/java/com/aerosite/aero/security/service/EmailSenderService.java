package com.aerosite.aero.security.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailSenderService")
public class EmailSenderService {

	@Value("${spring.mail.from.email}")
	private String senderEmail;

	private JavaMailSender javaMailSender;

	@Autowired
	public EmailSenderService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Async
	public void sendEmail(SimpleMailMessage email) {
		javaMailSender.send(email);
	}

	@Async
	public String sendEmail(String to, String subject, String body,String link) {
//		SimpleMailMessage mailMessage = new SimpleMailMessage();
//		mailMessage.setTo(to);
//		mailMessage.setSubject(subject);
//		mailMessage.setFrom(senderEmail);
//		mailMessage.setText(makeBody(body));
//		this.sendEmail(mailMessage);
	    String result =null;
	    MimeMessage message =javaMailSender.createMimeMessage();
	    try {

	        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
	        String htmlMsg = makeBody(body,link);
	        message.setContent(htmlMsg, "text/html");
	        helper.setTo(to);
	        helper.setSubject(subject);
	        result="success";
	        javaMailSender.send(message);
	    } catch (MessagingException e) {
	        throw new MailParseException(e);
	    }finally {
	        if(result !="success"){
	            result="fail";
	        }
	    }
	    return result;
	}
	
	public String makeBody(String message1,String link) {
		String message2 = "<a href=\""+link+"\">Activate</a> \n";
		if(message1.equals("OTP for resetting password"))
			message2  ="<strong>"+link+"</strong>";
		String body ="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + 
				"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + 
				"<head>\n" + 
				"<meta name=\"viewport\" content=\"width=device-width\" />\n" + 
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" + 
				"<title>Aero</title>\n" + 
				"<style type=\"text/css\">\n" + 
				"* { \n" + 
				"	margin:0;\n" + 
				"	padding:0;\n" + 
				"}\n" + 
				"* { font-family: \"Helvetica Neue\", \"Helvetica\", Helvetica, Arial, sans-serif; }\n" + 
				"\n" + 
				"img { \n" + 
				"	max-width: 100%; \n" + 
				"}\n" + 
				".collapse {\n" + 
				"	margin:0;\n" + 
				"	padding:0;\n" + 
				"}\n" + 
				"body {\n" + 
				"	-webkit-font-smoothing:antialiased; \n" + 
				"	-webkit-text-size-adjust:none; \n" + 
				"	width: 100%!important; \n" + 
				"	height: 100%;\n" + 
				"}\n" + 
				"a { color: #2BA6CB;}\n" + 
				".btn {\n" + 
				"	text-decoration:none;\n" + 
				"	color: #FFF;\n" + 
				"	background-color: #666;\n" + 
				"	padding:10px 16px;\n" + 
				"	font-weight:bold;\n" + 
				"	margin-right:10px;\n" + 
				"	text-align:center;\n" + 
				"	cursor:pointer;\n" + 
				"	display: inline-block;\n" + 
				"}\n" + 
				"p.callout {\n" + 
				"	padding:15px;\n" + 
				"	background-color:#ECF8FF;\n" + 
				"	margin-bottom: 15px;\n" + 
				"}\n" + 
				".callout a {\n" + 
				"	font-weight:bold;\n" + 
				"	color: #2BA6CB;\n" + 
				"}\n" + 
				"table.social {\n" + 
				"	background-color: #ebebeb;\n" + 
				"}\n" + 
				".social .soc-btn {\n" + 
				"	padding: 3px 7px;\n" + 
				"	font-size:12px;\n" + 
				"	margin-bottom:10px;\n" + 
				"	text-decoration:none;\n" + 
				"	color: #FFF;font-weight:bold;\n" + 
				"	display:block;\n" + 
				"	text-align:center;\n" + 
				"}\n" + 
				"a.fb { background-color: #3B5998!important; }\n" + 
				"a.tw { background-color: #1daced!important; }\n" + 
				"a.gp { background-color: #DB4A39!important; }\n" + 
				"a.ms { background-color: #000!important; }\n" + 
				".sidebar .soc-btn { \n" + 
				"	display:block;\n" + 
				"	width:100%;\n" + 
				"}\n" + 
				"table.head-wrap { width: 100%;}\n" + 
				"\n" + 
				".header.container table td.logo { padding: 15px; }\n" + 
				".header.container table td.label { padding: 15px; padding-left:0px;}\n" + 
				"table.body-wrap { width: 100%;}\n" + 
				"table.footer-wrap { width: 100%;	clear:both!important;\n" + 
				"}\n" + 
				".footer-wrap .container td.content  p { border-top: 1px solid rgb(215,215,215); padding-top:15px;}\n" + 
				".footer-wrap .container td.content p {\n" + 
				"	font-size:10px;\n" + 
				"	font-weight: bold;\n" + 
				"	\n" + 
				"}\n" + 
				"h1,h2,h3,h4,h5,h6 {\n" + 
				"font-family: \"HelveticaNeue-Light\", \"Helvetica Neue Light\", \"Helvetica Neue\", Helvetica, Arial, \"Lucida Grande\", sans-serif; line-height: 1.1; margin-bottom:15px; color:#000;\n" + 
				"}\n" + 
				"h1 small, h2 small, h3 small, h4 small, h5 small, h6 small { font-size: 60%; color: #6f6f6f; line-height: 0; text-transform: none; }\n" + 
				"\n" + 
				"h1 { font-weight:200; font-size: 44px;}\n" + 
				"h2 { font-weight:200; font-size: 37px;}\n" + 
				"h3 { font-weight:500; font-size: 27px;}\n" + 
				"h4 { font-weight:500; font-size: 23px;}\n" + 
				"h5 { font-weight:900; font-size: 17px;}\n" + 
				"h6 { font-weight:900; font-size: 14px; text-transform: uppercase; color:#444;}\n" + 
				".collapse { margin:0!important;}\n" + 
				"p, ul { \n" + 
				"	margin-bottom: 10px; \n" + 
				"	font-weight: normal; \n" + 
				"	font-size:14px; \n" + 
				"	line-height:1.6;\n" + 
				"}\n" + 
				"p.lead { font-size:17px; }\n" + 
				"p.last { margin-bottom:0px;}\n" + 
				"ul li {\n" + 
				"	margin-left:5px;\n" + 
				"	list-style-position: inside;\n" + 
				"}\n" + 
				"ul.sidebar {\n" + 
				"	background:#ebebeb;\n" + 
				"	display:block;\n" + 
				"	list-style-type: none;\n" + 
				"}\n" + 
				"ul.sidebar li { display: block; margin:0;}\n" + 
				"ul.sidebar li a {\n" + 
				"	text-decoration:none;\n" + 
				"	color: #666;\n" + 
				"	padding:10px 16px;\n" + 
				"	margin-right:10px;\n" + 
				"	cursor:pointer;\n" + 
				"	border-bottom: 1px solid #777777;\n" + 
				"	border-top: 1px solid #FFFFFF;\n" + 
				"	display:block;\n" + 
				"	margin:0;\n" + 
				"}\n" + 
				"ul.sidebar li a.last { border-bottom-width:0px;}\n" + 
				"ul.sidebar li a h1,ul.sidebar li a h2,ul.sidebar li a h3,ul.sidebar li a h4,ul.sidebar li a h5,ul.sidebar li a h6,ul.sidebar li a p { margin-bottom:0!important;}\n" + 
				".container {\n" + 
				"	display:block!important;\n" + 
				"	max-width:600px!important;\n" + 
				"	margin:0 auto!important;\n" + 
				"	clear:both!important;\n" + 
				"}\n" + 
				".content {\n" + 
				"	padding:15px;\n" + 
				"	max-width:600px;\n" + 
				"	margin:0 auto;\n" + 
				"	display:block; \n" + 
				"}\n" + 
				".content table { width: 100%; }\n" + 
				".column {\n" + 
				"	width: 300px;\n" + 
				"	float:left;\n" + 
				"}\n" + 
				".column tr td { padding: 15px; }\n" + 
				".column-wrap { \n" + 
				"	padding:0!important; \n" + 
				"	margin:0 auto; \n" + 
				"	max-width:600px!important;\n" + 
				"}\n" + 
				".column table { width:100%;}\n" + 
				".social .column {\n" + 
				"	width: 280px;\n" + 
				"	min-width: 279px;\n" + 
				"	float:left;\n" + 
				"}\n" + 
				".clear { display: block; clear: both; }\n" + 
				"@media only screen and (max-width: 600px) {\n" + 
				"	\n" + 
				"	a[class=\"btn\"] { display:block!important; margin-bottom:10px!important; background-image:none!important; margin-right:0!important;}\n" + 
				"\n" + 
				"	div[class=\"column\"] { width: auto!important; float:none!important;}\n" + 
				"	\n" + 
				"	table.social div[class=\"column\"] {\n" + 
				"		width:auto!important;\n" + 
				"	}\n" + 
				"}\n" + 
				"</style>\n" + 
				"</head> \n" + 
				"<body bgcolor=\"#FFFFFF\">\n" + 
				"<table class=\"body-wrap\">\n" + 
				"	<tr>\n" + 
				"		<td></td>\n" + 
				"		<td class=\"container\" bgcolor=\"#FFFFFF\">\n" + 
				"\n" + 
				"			<div class=\"content\">\n" + 
				"			<table>\n" + 
				"				<tr>\n" + 
				"					<td>\n" + 
				"						\n" + 
				"						<h3>Aero mail</h3>\n" + 
				"						<p class=\"lead\">This is a system generated email. Do not reply</p>\n" + 
				"						<p><img src=\"https://raw.githubusercontent.com/amit586/aero-site/gh-pages/assets/backgrounds/background1.jpg\" style=\"width: 600px\" /></p>\n" + 
				"						<p class=\"callout\">\n" + 
											message1+" :  "+ message2 + 
				"						</p>\n" + 
				"					</td>\n" + 
				"				</tr>\n" + 
				"			</table>\n" + 
				"			</div>\n" + 
				"		</td>\n" + 
				"		<td></td>\n" + 
				"	</tr>\n" + 
				"</table>\n" + 
				"<table class=\"footer-wrap\">\n" + 
				"	<tr>\n" + 
				"		<td></td>\n" + 
				"		<td class=\"container\">\n" + 
				"				<div class=\"content\">\n" + 
				"				<table>\n" + 
				"				<tr>\n" + 
				"					<td align=\"center\">\n" + 
				"						<p>\n" + 
				"							<a href=\"#\">Terms</a> |\n" + 
				"							<a href=\"#\">Privacy</a> |\n" + 
				"							<a href=\"#\"><unsubscribe>Unsubscribe</unsubscribe></a>\n" + 
				"						</p>\n" + 
				"					</td>\n" + 
				"				</tr>\n" + 
				"			</table>\n" + 
				"			</div>\n" + 
				"		</td>\n" + 
				"		<td></td>\n" + 
				"	</tr>\n" + 
				"</table>\n" + 
				"</body>\n" + 
				"</html>" ;
		return body;
	}
}
