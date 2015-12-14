package com.Util;

import com.Model.LoanObject;

import java.io.*;

public class StringByteHelper {

    public static byte[] fromObjectToByteArray (LoanObject loanObject) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;
            try {
              out = new ObjectOutputStream(bos);
              out.writeObject(loanObject);
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

    public static LoanObject fromByteArrayToObject(byte[] byteArray) throws IOException, ClassNotFoundException {

        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        ObjectInput in = null;
        try {
          in = new ObjectInputStream(bis);
          return (LoanObject) in.readObject();
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
