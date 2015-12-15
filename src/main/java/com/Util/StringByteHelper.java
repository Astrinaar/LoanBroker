package com.Util;

import com.Model.LoanObject;

import java.io.*;

public class StringByteHelper {

    public static byte[] fromObjectToByteArray (Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;
            try {
              out = new ObjectOutputStream(bos);
              out.writeObject(object);
              return bos.toByteArray();
            } finally {
              try {
                if (out != null) {
                  out.close();
                }
              } catch (IOException ex) {
                // ignore close exception
              }
              try {
                bos.close();
              } catch (IOException ex) {
                // ignore close exception
              }
            }
    }

    public static Object fromByteArrayToObject(byte[] byteArray) throws IOException, ClassNotFoundException {

        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        ObjectInput in = null;
        try {
          in = new ObjectInputStream(bis);
          return in.readObject();
        } finally {
          try {
            bis.close();
          } catch (IOException ex) {
            // ignore close exception
          }
          try {
            if (in != null) {
              in.close();
            }
          } catch (IOException ex) {
            // ignore close exception
          }
        }

    }

    public static String fromByteArrayToString(byte[] byteArray) throws IOException, ClassNotFoundException {

        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            return (String) in.readObject();
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }

    }



}
