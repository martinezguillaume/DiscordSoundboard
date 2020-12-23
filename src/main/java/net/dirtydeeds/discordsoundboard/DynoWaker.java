package net.dirtydeeds.discordsoundboard;

import org.apache.commons.logging.impl.SimpleLog;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DynoWaker {
  private static final SimpleLog LOG = new SimpleLog("DynoWaker");

  private static void setTimeout(Runnable runnable, int delay){
    new Thread(() -> {
      try {
        Thread.sleep(delay);
        runnable.run();
      }
      catch (Exception e){
        System.err.println(e);
      }
    }).start();
  }


  public static void start(String urlStr, Integer interval) {
    Integer intervalMs = interval * 60000;
    setTimeout(() -> {
      try {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        Integer responseCode = con.getResponseCode();
        LOG.info(urlStr + "responded with a " + responseCode);
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        start(urlStr, interval);
      }
    }, intervalMs);
  };
}
