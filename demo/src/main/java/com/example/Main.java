package com.example;

import java.io.File;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.apache.tomcat.util.net.SSLHostConfigCertificate.Type;

public class Main {
   public static void main(String[] args) {
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

    // Add certificate information
    SSLHostConfigCertificate certificate = new SSLHostConfigCertificate(sslHostConfig, Type.RSA);
    certificate.setCertificateKeystoreFile("keystore.jks"); // Path to your keystore
    certificate.setCertificateKeystorePassword("P@ssword"); // Keystore password
    certificate.setCertificateKeyAlias("tomcat"); // Alias inside keystore

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
    try {
      tomcat.getConnector();
      tomcat.start();
      System.out.println("Tomcat started successfully.");
      tomcat.getServer().await();
    } catch (LifecycleException e) {
      e.printStackTrace();
    }
  }
}

 