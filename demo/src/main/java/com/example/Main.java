package com.example;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.apache.tomcat.util.net.SSLHostConfigCertificate.Type;
import org.w3c.dom.Document;

public class Main {
   public static void main(String[] args) {
    try {
      // Create Tomcat instance
      Tomcat tomcat = new Tomcat();
      tomcat.setPort(8081); // Set server port

      // Configure HTTPS connector
      Connector httpsConnector = new Connector();
      httpsConnector.setPort(8443); // HTTPS Port
      httpsConnector.setSecure(true);
      httpsConnector.setScheme("https");

      Http11NioProtocol protocol = (Http11NioProtocol) httpsConnector.getProtocolHandler();
      protocol.setSSLEnabled(true);

      // Create SSLHostConfig
      SSLHostConfig sslHostConfig = new SSLHostConfig();
      sslHostConfig.setHostName("_default_"); // Required in newer Tomcat versions
      sslHostConfig.setProtocols("TLSv1.2,TLSv1.3"); // Specify allowed TLS versions
      sslHostConfig.setCertificateVerification(
            "optional"); // Can be "none", "optional", or "required"


      File file = new File("certificateConfig.xml");
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                      .newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse(file);
      String keystore = document.getElementsByTagName("keystore").item(0).getTextContent();
      String keystorePassword = document.getElementsByTagName("keystorePassword").item(0).getTextContent();
      String alias = document.getElementsByTagName("alias").item(0).getTextContent();
      

      // Add certificate information
      SSLHostConfigCertificate certificate = new SSLHostConfigCertificate(sslHostConfig, Type.RSA);
      certificate.setCertificateKeystoreFile(keystore); // Path to your keystore
      certificate.setCertificateKeystorePassword(keystorePassword); // Keystore password
      certificate.setCertificateKeyAlias(alias); // Alias inside keystore

      // Add certificate to SSLHostConfig
      sslHostConfig.addCertificate(certificate);

      // Register SSLHostConfig with the connector
      protocol.addSslHostConfig(sslHostConfig);
      
      // Add HTTPS connector to Tomcat
      tomcat.getService().addConnector(httpsConnector);

      // Ensure a base directory for Tomcat
      tomcat.setBaseDir("temp");

      // Create and configure context
      Context ctx = tomcat.addWebapp("", new File("webapps/ROOT").getAbsolutePath());
      System.out.println("JSP root: " + new File("webapps/ROOT").getAbsolutePath());

      
      File rootDir = new File("webapps/ROOT");
      if (rootDir.exists() && rootDir.isDirectory()) {
        for (String fileName : rootDir.list()) {
          System.out.println("Found: " + fileName);
        }
      } else {
        System.out.println("Directory does not exist or is not a directory.");
      }

      if (ctx == null) {
        throw new RuntimeException("Tomcat context initialization failed!");
      }

      // Add a servlet
      tomcat.addServlet("", "LogonServlet", new LogonServlet());
      tomcat.addServlet("", "AddStudentServlet", new AddStudentServlet());

      // Map the servlet
      ctx.addServletMappingDecoded("/logon", "LogonServlet");
      ctx.addServletMappingDecoded("/addStudent", "AddStudentServlet");

      // Start Tomcat
      tomcat.getConnector();
      tomcat.start();
      System.out.println("Tomcat started successfully.");
      tomcat.getServer().await();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

 